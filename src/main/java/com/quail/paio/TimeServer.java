/*************************************************
  AsderStudio版权所有
  Copyright (C), 2009-2017, AsderStudio.

  File name: TimeServer.java  
  Author: Asder(yifei.wu) 
  Version: 1.0 
  Date: 2017-07-10 13:07:08
  Description: 
*************************************************/
package main.java.com.quail.paio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import main.java.com.quail.bio.TimeServerHandler;

/**
 * File name: TimeServer.java Description:
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {

		int port = 8080;
		if (args != null && args.length > 0) {

			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}
		ServerSocket server = null;
		try {

			server = new ServerSocket(port);
			System.out.println("The time server is start in port : " + port);
			Socket socket = null;

			while (true) {
				socket = server.accept();
				// 将业务处理线程加入线程池(与BIO版本的区别)
				new Thread(new TimeServerHandler(socket)).start();
			}
		} finally {
			if (server != null) {
				System.out.println("The time server close");
				server.close();
				server = null;
			}
		}

	}
}
