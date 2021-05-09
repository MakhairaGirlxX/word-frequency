package WordFreqPack;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIWordFreq {	
	
	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>();		
		DataHandler handler = new DataHandler(words);
		JFrame myframe = new AppFrame("first frame");

	}

}

class AppFrame extends JFrame
{
	public AppFrame(String title)
	{
		super(title);
		
		ArrayList<String> words = new ArrayList<String>();		
		
		this.setLayout(new GridLayout(1, 1));
		this.add(new RightPnl(words));
		this.setSize(800, 600);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class RightPnl extends JPanel
{
	JTextArea rightPnl;
	JTextArea leftPnl;
	JTextArea wordSearchFreq;
	
	JButton getFileBtn;
	JButton sortWordsBtn;
	
	DataHandler handler;

	ArrayList<String> words;
	
	JTextField file;
	JTextField wordSearch;	
	
	boolean isFileThere = false;
	
	 HashMap<String,Integer> hMap = new HashMap<>();
	
	public RightPnl(ArrayList<String> words)
	{		
		this.words = words;
		handler = new DataHandler(words);
		this.setLayout(new BorderLayout());		
		
		organizeComponents();		
		actionListeners();
	}

	private void organizeComponents()
	{
	
	JPanel pnlContainer1 = new JPanel();
	JPanel pnlContainer = new JPanel();
	JPanel pnlContainer2 = new JPanel();
	
	rightPnl = new JTextArea();
	leftPnl = new JTextArea();
	
	getFileBtn = new JButton("Get File & Analyze");		
	file = new JTextField();
	file.setBorder(BorderFactory.createTitledBorder("type file name"));
	
	sortWordsBtn = new JButton("Sort");
	wordSearchFreq = new JTextArea();
	wordSearch = new JTextField();
	wordSearch.setBorder(BorderFactory.createTitledBorder("type a word here"));
	wordSearchFreq.setBorder(BorderFactory.createTitledBorder("word frequency"));
	
	
	GridLayout layout = new GridLayout(1, 2);
	layout.setHgap(20);
	
	GridLayout layout2 = new GridLayout(1, 3);
	layout2.setHgap(20);
	
	
	pnlContainer1.setLayout(layout);
	pnlContainer.setLayout(layout);
	pnlContainer2.setLayout(layout2);
	
	pnlContainer1.add(rightPnl);
	pnlContainer1.add(leftPnl);

	pnlContainer.add(file);
	pnlContainer.add(getFileBtn);
	
	pnlContainer2.add(wordSearch);
	pnlContainer2.add(wordSearchFreq);
	pnlContainer2.add(sortWordsBtn);
	
	this.add(pnlContainer1, "Center");
	this.add(pnlContainer, "North");
	this.add(pnlContainer2, "South");
}

	private void actionListeners() {
		
		getFileBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String input = file.getText();
			
				try {
					getFile(input);
				} catch (IOException e1) {
					e1.printStackTrace();
					leftPnl.append("File not found");
				}
			
			}
			
			});		
		
		sortWordsBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(isFileThere)
				{
				leftPnl.setText(null);
				sortWords();	
				}
				else
				{
					leftPnl.append("No File Found");
				}
			}
			
			});	
		
		wordSearch.addKeyListener(new KeyListener()
				{

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						String input = wordSearch.getText();
						String newFile = file.getText();
						
						if(e.getKeyCode() == KeyEvent.VK_ENTER)
						{
							wordSearchFreq.append(null);
							getWord(newFile, input);
						}
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
			
				});
			
	}
	
	public void getFile(String input) throws IOException
	{
		isFileThere = true;
		handler.getData(input);
		
		for(String count : words)
		{
			rightPnl.append(" " + count);
		}
		
		rightPnl.setLineWrap(true);
		rightPnl.setWrapStyleWord(true);

		leftPnl.append(frequency());
		leftPnl.setLineWrap(true);
		leftPnl.setWrapStyleWord(true);
	}
	
	public String frequency()
	{	
		 
	      for(int i= 0 ; i< words.size() ; i++) 
	      {
	         if(hMap.containsKey(words.get(i))) 
	         {
	            int count = hMap.get(words.get(i));
	            String temp = words.get(i);
	            hMap.put(words.get(i),count + 1);
	         } 
	         
	         else 
	         {
	            hMap.put(words.get(i) , 1);
	         }
	      }
	      String str = hMap.toString();
	     return str;	
	}
	
	public void sortWords()
	{		
	    List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hMap.entrySet()); 
	    
	    SortedCompareMap sorted = new SortedCompareMap();
	    Collections.sort(list, sorted);
	
	    HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
	    
	    for (Map.Entry<String, Integer> aa : list) 
	    { 
         temp.put(aa.getKey(), aa.getValue()); 
	    } 
	    String returnedTemp = temp.toString();
     
	    leftPnl.append(returnedTemp);		
		
	}
	
	public void getWord(String file, String input)
	{
		boolean wordFound = false;
		File f = new File(file);
		Scanner sc;
		try {
			sc = new Scanner(f);
			sc.useDelimiter("[^a-zA-Z']+");
			
			while(sc.hasNext())
			{
				if(input.equals(sc.next()))
				{
					wordFound = true;
				
				}
			}
			
			if(wordFound)
			{
				wordSearchFreq.append(input + " = " + hMap.get(input));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}
	
	

class SortedCompareMap implements Comparator<Map.Entry<String, Integer>>
{
	@Override
	public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
		 return (o1.getValue()).compareTo(o2.getValue()); 
	} 
}

