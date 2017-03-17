/**  
* @Title: StickpackageHandler.java 
* @Package com.cit.its.server 
* @Description: TODO 
* @author zxl   
* @date 2015年5月5日 下午3:47:35 
*/ 

package com.cit.its.server;

import java.nio.ByteOrder;

import com.cit.its.util.ByteBuf2ByteArray;
import com.cit.its.util.IntByteArrayUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/** 
 * @Title: StickpackageHandler
 * @Description: TODO 
 * @author zxl 
 * @date 2015年5月5日 下午3:47:35  
 */
public class StickpackageHandler extends LengthFieldBasedFrameDecoder{

	/** 
	* @Description: 处理粘包问题
	* @param TODO 
	* @author zxl   
	* @date 2015年5月5日 下午5:10:23  
	*/ 
	
	public StickpackageHandler(ByteOrder byteOrder, int maxFrameLength,
			int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip, boolean failFast) {
		
		super(ByteOrder.BIG_ENDIAN, maxFrameLength, lengthFieldOffset,lengthFieldLength,
				lengthAdjustment, initialBytesToStrip, failFast);
		System.out.println("StickpackageHandler");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("stick package channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("stick package channelActive");
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("stick package channelRead");
		
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("stick package channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytes = ByteBuf2ByteArray.ByteBufToBA(in);
		System.out.println("stick package decode" + IntByteArrayUtil.getHexString(bytes));
		return super.decode(ctx, in);
	}
}
