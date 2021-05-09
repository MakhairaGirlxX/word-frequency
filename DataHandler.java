package WordFreqPack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DataHandler {
	List<String> words = new ArrayList<String>();
	
	public DataHandler(ArrayList<String> words)
	{
		this.words = words;
	}

	public void getData(String connStr) throws IOException{
        String line = "";
        try {
            FileReader fileReader = new FileReader(connStr);            
            BufferedReader bufferedReader = new BufferedReader(fileReader);//Wrap FileReader in BufferedReader.

            while((line = bufferedReader.readLine()) != null ) {
            	String[] parts = line.split(" ");
            	for(int i = 0; i < parts.length; i++)
            	{
            		words.add(parts[i]);  
            	}           	
            } 
            bufferedReader.close();                   
        }
        catch(IOException ex) {
            System.out.println("Error in reading file"); 
            ex.printStackTrace();                  
        }

	}

}
