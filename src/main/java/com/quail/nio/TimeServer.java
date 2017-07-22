/*************************************************
  AsderStudio版权所有
  Copyright (C), 2009-2017, AsderStudio.

  File name: TimeServer.java  
  Author: Asder(yifei.wu) 
  Version: 1.0 
  Date: 2017-07-10 13:07:08
  Description: 
*************************************************/
package com.quail.nio;

import java.io.IOException;

/**
 * File name: TimeServer.java Description:
 */
public class TimeServer {

	public static void main(String[] args) throws IOException {

		int port = 9124;
		if (args != null && args.length > 0) {

			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}

		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();

	}
}
