package com.survey.methods;

import java.util.*;
public class BruteForce
{
	public static void main(String[] args) {
		LRS l = new LRS();
		Parser p = new Parser();
		String sequence = p.fileToHashMap("database3.txt");
	    long startTime = System.currentTimeMillis();
		System.out.println(l.getLRS(sequence));
	    long endTime = System.currentTimeMillis();
	    System.out.println("Brute force total time: " + (endTime - startTime));
	}
}

class LRS
{
       public String getLRS(String str){
    	   long startTime_buildarray = System.currentTimeMillis();
            List<String> substrs = new ArrayList<>();
            for(int i=0;i<str.length();i++){
                for(int j=i;j<str.length(); j++){
                    substrs.add(str.substring(i,j));
                }
            }
    	    long endTime_buildarray = System.currentTimeMillis();
    	    System.out.println("Brute force - time taken to generate all substrings: " + (endTime_buildarray - startTime_buildarray));
    	    
    	    long startTime_getrepeat = System.currentTimeMillis();
    	    int max = Integer.MIN_VALUE;
        	String s = new String("");
        	for(int i=0; i<substrs.size();i++){
        		int temp = helper(substrs.get(i),str);
        		if(temp>1){
        			if(substrs.get(i).length()>s.length()){
        				s = substrs.get(i);
        			}
        		}
        	}
        	long endTime_getrepeat = System.currentTimeMillis();
    	    System.out.println("Brute force - time taken to find tandem repeat: " + (endTime_getrepeat - startTime_getrepeat));
        	return s;
       }
       
    int helper(String pat, String txt) {         
        int M = pat.length();         
        int N = txt.length();         
        int res = 0; 
  
        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++) { 
            /* For current index i, check for  
        pattern match */
            int j;             
            for (j = 0; j < M; j++) { 
                if (txt.charAt(i + j) != pat.charAt(j)) { 
                    break; 
                } 
            } 
  
            // if pat[0...M-1] = txt[i, i+1, ...i+M-1]  
            if (j == M) {                 
                res++;                 
                j = 0;                 
            }             
        }         
        return res;         
    }
}