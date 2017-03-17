/**  
* @Title: MsgRecvServerInitializer.java 
* @Package Server 
* @Description: TODO 
* @author QingXi
* @date 2016年12月30日
*/ 

package com.cit.its.server;
import java.nio.ByteOrder;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/** 
 * @Title: MsgRecvServerInitializer
 * @Description: InitChannel 
 * @author QingXi
 * @date 2016年12月30日
 */

public class MsgRecvServerInitializer extends ChannelInitializer<SocketChannel> {

	/** 
	*  
	* @param arg0
	* @throws Exception 
	* @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel) 
	*/
	static final EventExecutorGroup  executor = new DefaultEventExecutorGroup(200);
	
	// Idle Time, 空闲时间，单位为秒
	private int readIdleTime;
	private int writeIdleTime;
	private int allIdleTime;
	
	@Override
	//当新连接accept的时候，这个方法会调用 
	protected void initChannel(SocketChannel ch) throws Exception {
		
		Properties props = new Properties();
	    props.load(MsgRecvServerInitializer.class.getClassLoader().getResourceAsStream("IdleConfig.properties"));
	    readIdleTime = Integer.valueOf(props.getProperty("readIdleTimeSeconds"));
	    writeIdleTime = Integer.valueOf(props.getProperty("writeIdleTimeSeconds"));
	    allIdleTime = Integer.valueOf(props.getProperty("allIdleTimeSeconds"));
		
	    
		ChannelPipeline pipeline = ch.pipeline();	
		pipeline.addLast(new IdleStateHandler(readIdleTime, writeIdleTime, allIdleTime, TimeUnit.SECONDS));	
		pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 16383 , 22, 2, 1, 0, false));
        pipeline.addLast(executor, new MsgRecvServerHandler());
        

	}

}
