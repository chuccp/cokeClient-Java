package com.coke.km;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Url {

	public static void main(String[] args) {
	
		long stat= System.currentTimeMillis();
		Runtime run = Runtime.getRuntime();
		try {
			
			
			Process pro = run.exec("D:\\movie\\a.bat");
			
			InputStream ips = pro.getErrorStream();
		
			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
			String line;
			while(( line = br.readLine())!=null) {
				System.out.println(line);
			}
			long end= System.currentTimeMillis();
			System.out.println(end-stat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
		
	}
	
}
