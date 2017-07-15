/*************************************************
  AsderStudio版权所有
  Copyright (C), 2009-2017, AsderStudio.

  File name: TimeClient.java  
  Author: Asder(yifei.wu) 
  Version: 1.0 
  Date: 2017-07-10 15:07:66
  Description: 
*************************************************/
package main.java.com.quail.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
  File name: TimeClient.java  
  Description: 
*/
public class TimeClient {

	public static void main(String[] args) {
		
		int port = 8080;
		if(args != null && args.length > 0) {
			
			try {
				port = Integer.valueOf(port);
			} catch (Exception e) {
				// 采用默认值
			}
		}
		
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			
			socket = new Socket("127.0.0.1", 8080);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("QUERY TIME ORDER");
			System.out.println("Send order 2 server succeed.");
			String resp = in.readLine();
			System.out.println("Now is : " + resp);
		} catch (Exception e) {
			// 不需要处理
		} finally {
			if(out != null) {
				out.close();
				out = null;
			}
			if(in != null) {
				try {
					in.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket = null;
			}
		}
	}
}
