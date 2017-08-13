/*************************************************
  AsderStudio版权所有
  Copyright (C), 2009-2017, AsderStudio.

  File name: TimeServerHandler.java  
  Author: Asder(yifei.wu) 
  Version: 1.0 
  Date: 2017-07-10 12:07:89
  Description: 
*************************************************/
package com.quail.netty5;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
  File name: TimeServerHandler.java  
  Description: 
*/
public class TimeServerHandler extends ChannelHandlerAdapter {

	private int counter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		// 前面已经定义了接收为字符串，这里直接接收字符串就可以
		String body = (String) msg;
		System.out.println("The time server receive order : " + body + " ; the counter is : " + ++counter);
		// 服务端给客户端的响应(当前时间)
		String response = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		response = response + System.getProperty("line.separator");
		// 发送必须还是ByteBuf类型
		ByteBuf resp = Unpooled.copiedBuffer(response.getBytes());
		ctx.writeAndFlush(resp);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
