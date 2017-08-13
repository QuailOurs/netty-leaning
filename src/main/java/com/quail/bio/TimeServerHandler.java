/*************************************************
  AsderStudio版权所有
  Copyright (C), 2009-2017, AsderStudio.

  File name: TimeServerHandler.java  
  Author: Asder(yifei.wu) 
  Version: 1.0 
  Date: 2017-07-10 12:07:89
  Description: 
*************************************************/
package com.quail.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import javax.net.ssl.SSLException;

/**
  File name: TimeServerHandler.java  
  Description: 
*/
public class TimeServerHandler implements Runnable{

	private Socket socket = null;
	
	public TimeServerHandler(Socket socket) {
		
		this.socket = socket;
	}

	public void run() {

		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String currentTime = null;
			String body = null;
			while(true) {
				
				body = in.readLine();
				if(body == null)
					break;
				System.out.println("The time server receive irder : " + body);
				currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
				out.println(currentTime);
				Thread.sleep(11000);
			}
		} catch (Exception e) {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			if(out != null) {
				out.close();
				out = null;
			}
			
			if(this.socket != null) {
				try {
					this.socket.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				this.socket = null;
			}
		}
	}
	
	
}
