package com.survey.methods;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Parser {

	public String fileToHashMap(String filename){
		StringBuilder str=new StringBuilder();
		try
		{
			BufferedReader text = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = text.readLine()) != null)
			{
				line = line.trim();
				if(line.startsWith(">", 0)){
					continue;
				}
				else{
					str.append(line);
				}

			}
			text.close();
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
		}
		return str.toString();
	}
}
