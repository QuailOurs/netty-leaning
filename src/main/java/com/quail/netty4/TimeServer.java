/*************************************************
  AsderStudio版权所有
  Copyright (C), 2009-2017, AsderStudio.

  File name: TimeServer.java  
  Author: Asder(yifei.wu) 
  Version: 1.0 
  Date: 2017-07-10 13:07:08
  Description: 
*************************************************/
package com.quail.netty4;

import java.io.IOException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * File name: TimeServer.java
 * Description:
 */
public class TimeServer {

	public void bind(int port) {
		// 配置服务端NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			// 绑定两个线程池
			bootstrap.group(bossGroup, workerGroup)
					// 指定NIO的模式，如果是客户端就是NioSocketChannel
					.channel(NioServerSocketChannel.class)
					//TCP的缓冲区设置
					.option(ChannelOption.SO_BACKLOG, 1024)
					// 保持连续
					.option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChildChannelHandler());
			// 绑定端口, 同步等待成功
			ChannelFuture future = bootstrap.bind(port).sync();
			// 等待服务端监听端口关闭
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			// 优雅退出, 释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel sc) throws Exception {

			// 拆包粘包定义结束字符串（第一种解决方案）
//			ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
			// 在管道中加入结束字符串
//			sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
			// 第二种定长
// 			sc.pipeline().addLast(new FixedLengthFrameDecoder(200));
			sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
			// 定义接收类型为字符串把ByteBuf转成String
			sc.pipeline().addLast(new StringDecoder());
			// 在这里配置具体数据接收方法的处理
			sc.pipeline().addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args) throws IOException {

		int port = 8080;
		if (args != null && args.length > 0) {

			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}

		new TimeServer().bind(port);

	}
}
