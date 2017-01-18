package com.cit.its.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Properties;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cit.its.messageEncoder.VehicleLoginMessageEncoder;
import com.cit.its.server.MsgRecvServer;
import com.cit.its.server.SpringContainer;

public class MsgSendClient {
	
	static Logger logger = Logger.getLogger(MsgSendClient.class);
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
	     props.load(MsgRecvServer.class.getClassLoader().getResourceAsStream("nettyAndActiveMQConfig.properties"));
	     port=Integer.valueOf(props.getProperty("netty.port"));
	     brokerURL=props.getProperty("activemq.brokerURL");
	     factory=new ActiveMQConnectionFactory(brokerURL);
	     PropertyConfigurator.configure("src/log4j.properties" );
	     SpringContainer.getInstance().loadContextByXMLPath("spring-context.xml");
		 new MsgSendClient().run();	
	}
	
	public void run() throws Exception {
		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			 .option(ChannelOption.TCP_NODELAY, true)
			 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			 .handler(new ChannelInitializer<SocketChannel>() {
				 @Override
				 public void initChannel(SocketChannel ch) throws Exception {
					 
				 }
			 });
		}
		finally {
			
		}
	}

	

}
