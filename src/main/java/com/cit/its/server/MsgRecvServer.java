/**  
* @Title: MsgRecvServer.java 
* @Package Server 
* @Description: Everything is start from here.
* @author QingXi
* @date 2016年12月30日
*/ 

package com.cit.its.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Properties;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
	
	
	/**
	 * Load configuration file
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {	
		 Properties props = new Properties();
		 props.load(MsgRecvServer.class.getClassLoader().getResourceAsStream("config.properties"));
	     port=Integer.valueOf(props.getProperty("netty.port"));
	     brokerURL=props.getProperty("activemq.brokerURL");
	     factory=new ActiveMQConnectionFactory(brokerURL);
	     props.load(MsgRecvServer.class.getClassLoader().getResourceAsStream("log4j.properties"));
	     PropertyConfigurator.configure(props);
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
            
			logger.info("bind port "+ port);
			
			System.out.println("bind port : " + port);
			
			ChannelFuture f = b.bind(port).sync();
			
			System.out.println("Start the server : OK");
			// Wait until the server socket is closed.
			logger.info("Start the Server: OK");
			
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();
			System.out.println("Close the server!");
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();	
		}
	}
	
	
	public static ActiveMQConnectionFactory getFactory() {
		return factory;
	}
	
	public static void setFactory(ActiveMQConnectionFactory factory) {
		MsgRecvServer.factory = factory;
	}
}
