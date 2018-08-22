package com.ssitcloud.business.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class StreamGobbler extends Thread {  
    InputStream is;  
    String type;  
    OutputStream os;  
          
    public StreamGobbler(InputStream is, String type) {  
        this(is, type, null);  
    }  
  
    public StreamGobbler(InputStream is, String type, OutputStream redirect) {  
        this.is = is;  
        this.type = type;  
        this.os = redirect;  
    }  
      
    public void run(){  
        InputStreamReader isr = null;  
        BufferedReader br = null;  
        try {  
            isr = new InputStreamReader(is);  
            br = new BufferedReader(isr);  
            String line=null;  
            while ( (line = br.readLine()) != null) {
            	//if(type.equals("ERROR"));
                System.out.println(type + ">" + line);      
            }  
              
        } catch (Exception ioe) {  
            ioe.printStackTrace();    
        } finally{ 
        	try {
	        	if(br != null)br.close();
	        	if(isr != null)isr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        }  
    }
}   
