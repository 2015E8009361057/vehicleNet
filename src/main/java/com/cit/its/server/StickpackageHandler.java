/**  
* @Title: StickpackageHandler.java 
* @Package com.cit.its.server 
* @Description: TODO 
* @author zxl   
* @date 2015年5月5日 下午3:47:35 
*/ 

package com.cit.its.server;

import java.nio.ByteOrder;
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
		// TODO Auto-generated constructor stub
	}


	

}
