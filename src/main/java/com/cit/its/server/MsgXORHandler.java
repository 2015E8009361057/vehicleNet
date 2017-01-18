/**  
* @Title: MsgXORHandler.java 
* @Package com.cit.its.server 
* @Description: TODO 
* @author zxl   
* @date 2015年4月3日 上午9:34:00 
*/ 

package com.cit.its.server;
import org.apache.log4j.Logger;

import com.cit.its.util.ByteBuf2ByteArray;
import com.cit.its.util.IntByteArrayUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/** 
 * @Title: MsgXORHandler
 * @Description: TODO 
 * @author zxl 
 * @date 2015年4月3日 上午9:34:00  
 */
public class MsgXORHandler extends ChannelInboundHandlerAdapter{
	
	Logger logger  = Logger.getLogger(MsgXORHandler.class);
	 @Override
		public void channelActive(final ChannelHandlerContext ctx){
		logger.info("Get a conncection!");
		ChannelGroupPool.channels.add(ctx.channel());
		logger.info("channels'size is :  "+ChannelGroupPool.channels.size());		
	    }
	 @Override
	 public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{	
//	    	logger.info("The Server has received msg");	    	
	    	byte[] bytes=ByteBuf2ByteArray.ByteBufToBA(msg);
//	    	logger.info(IntByteArrayUtil.getHexString(bytes));
		    if(!(bytes[3]==IntByteArrayUtil.byteXOR(bytes))){
/*		    	logger.info("Wrong bytes[3] is "+bytes[3]);
		    	logger.info("the received msg was wrong "+IntByteArrayUtil.byteXOR(bytes));*/
		    	return;
		    }else{
//		    	logger.info("XOR success");
		    	ctx.fireChannelRead(msg);
		    }
		    
	 }
	 
		@Override
		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
			if(IdleStateEvent.class.isAssignableFrom(evt.getClass())){  
	            IdleStateEvent event = (IdleStateEvent) evt;  
	            if(event.state() == IdleState.READER_IDLE) { 
	                logger.info("read Idle ");
	                ctx.channel().close();
	            } 
	        }  
	    }  

	
}
