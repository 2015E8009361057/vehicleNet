/**  
* @Title: MsgRecvServer.java 
* @Package Server 
* @Description: Everything is start from here.
* @author QingXi
* @date 2016年12月30日
*/ 

package com.cit.its.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/** 
 * @Title: MsgRecvServer
 * @Description: 启动服务器 
 * @author QingXi
 * @date 2016年12月30日
 */

public class MsgRecvServer {

	static Logger logger = Logger.getLogger(MsgRecvServer.class);
	private static  int port;
	private static  String brokerURL ;
	public static ActiveMQConnectionFactory factory;
	
	public MsgRecvServer() throws IOException {
		
	}
	
	/**
	 * Load configuration file
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {	
		 Properties props = new Properties();
	     props.load(MsgRecvServer.class.getClassLoader().getResourceAsStream("nettyAndActiveMQConfig.properties"));
	     port=Integer.valueOf(props.getProperty("netty.port"));
	     brokerURL=props.getProperty("activemq.brokerURL");
	     factory=new ActiveMQConnectionFactory(brokerURL);
	     PropertyConfigurator.configure("src/log4j.properties" );
	     SpringContainer.getInstance().loadContextByXMLPath("spring-context.xml");
		 new MsgRecvServer().run();	
	}

	public void run() throws Exception {
		
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap b=new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class); 
			b.childHandler(new MsgRecvServerInitializer());
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			b.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
            b.childOption(ChannelOption.TCP_NODELAY,true);
            b.childOption(ChannelOption.SO_REUSEADDR, true);
            
			logger.info("bind port"+ port);
			
			System.out.println("bind port : " + port);
			
			ChannelFuture f = b.bind(port).sync();
			
			// Wait until the server socket is closed.
			logger.info("Start the Server: OK");
			
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	
	public static ActiveMQConnectionFactory getFactory() {
		return factory;
	}
	
	public static void setFactory(ActiveMQConnectionFactory factory) {
		MsgRecvServer.factory = factory;
	}
}
