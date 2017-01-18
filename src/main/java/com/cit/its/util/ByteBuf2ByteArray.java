/**  
* @Title: ByteBuf2ByteArray.java 
* @Package com.itrc.vehiclemanage.util 
* @Description: TODO 
* @author zxl   
* @date 2015年3月12日 下午1:41:28 
*/ 

package com.cit.its.util;

import io.netty.buffer.ByteBuf;

/** 
 * @Title: ByteBuf2ByteArray
 * @Description: TODO 
 * @author zxl 
 * @date 2015年3月12日 下午1:41:28  
 */
public class ByteBuf2ByteArray {
	public static byte[] ByteBufToBA(Object msg){
		ByteBuf buf=(ByteBuf)msg;
    	byte[] bytes = new byte[buf.readableBytes()];
    	int readerIndex = buf.readerIndex();
    	buf.getBytes(readerIndex, bytes);	
		return bytes;
		
	}

}
