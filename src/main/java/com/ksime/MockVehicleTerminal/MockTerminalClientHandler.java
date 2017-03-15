package com.ksime.MockVehicleTerminal;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.cit.its.messageDecoder.HeaderDecoder;
import com.cit.its.messageEncoder.PickSendMessage;
import com.cit.its.messageStruct.CommandType;
import com.cit.its.messageStruct.ResponseType;
import com.cit.its.util.ByteBuf2ByteArray;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MockTerminalClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = Logger.getLogger(MockTerminalClientHandler.class);
	private byte[] req; 
	
/*	public MockTerminalClientHandler() throws ParseException {
		req = PickSendMessage.messageBytes(0);
	}
	*/
	/**
	 * 模拟终端收到消息接收服务器的应答消息
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		byte[] bytes = ByteBuf2ByteArray.ByteBufToBA(msg);
		CommandType commandType = HeaderDecoder.getCommandType(bytes);
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		String vehicleVIN = HeaderDecoder.getVehicleVIN(bytes);
		System.out.println(vehicleVIN + " msg_type is : " + commandType + ";" + "response type is : " + responseType);
		logger.info(vehicleVIN + " msg_type is : " + commandType + ";" + "response type is : " + responseType);
	}
	
	
	@Override
	public void channelInactive(final ChannelHandlerContext ctx) {
		logger.info("connect break");
		System.out.println("connnect break");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		logger.warn(cause.getMessage());
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws ParseException {
		System.out.println("client channelActive!");
		ByteBuf message = null;
		for (int i = 0; i < 10; i++) {
			req = PickSendMessage.messageBytes(i);
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
/*			
			ChannelFuture cf=ctx.writeAndFlush(message);
			cf.addListener(new ChannelFutureListener(){

				public void operationComplete(ChannelFuture cf) throws Exception {
					// TODO Auto-generated method stub
					if(!(cf.isDone() && cf.isSuccess())){
						System.out.println("发送消息失败");
						ctx.channel().close();
					}else{
						System.out.println("发送消息成功");
					}
				}
			});
			*/

		}
		/*for(int i = 0; i < 2; i++) {
			for (int j = 1; j <= 14; j++) {
				req = PickSendMessage.messageBytes(j);
				message = Unpooled.buffer(req.length);
				message.writeBytes(req);
				ctx.writeAndFlush(message);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}*/
	}

}
