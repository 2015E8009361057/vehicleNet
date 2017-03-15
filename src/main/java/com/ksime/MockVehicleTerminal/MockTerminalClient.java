package com.ksime.MockVehicleTerminal;

import java.nio.ByteOrder;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.cit.its.server.StickpackageHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class MockTerminalClient {
	
	public void connect0(int port, String host) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			 .option(ChannelOption.TCP_NODELAY, true)
			 .handler(new ChannelInitializer<SocketChannel>() {
				 @Override
				 public void initChannel(SocketChannel ch) throws Exception {
					 ch.pipeline().addLast(new StickpackageHandler(ByteOrder.BIG_ENDIAN, 2048, 22, 2, 1, 0,false));
					 ch.pipeline().addLast(new MockTerminalClientHandler());
				 }
			 });
			
			System.out.println("connect");
			
			// 发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();
			
			System.out.println("client bind port : " + port);
			
			// 等待客户端链路关闭
			f.channel().closeFuture().sync();
		} finally {
			// 优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		int port = 10004;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}
		
		Properties props = new Properties();
		props.load(MockTerminalClient.class.getClassLoader().getResourceAsStream("log4j.properties"));
	    PropertyConfigurator.configure(props);
		
		new MockTerminalClient().connect0(port, "127.0.0.1");
		
	}

}
