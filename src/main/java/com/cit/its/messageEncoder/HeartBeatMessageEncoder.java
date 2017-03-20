package com.cit.its.messageEncoder;

import java.util.Date;

import com.cit.its.messageDecoder.CheckCode;

public class HeartBeatMessageEncoder {
	
	public static byte[] getHeartBeatBytes(String vehicleVIN) {
		// 心跳报文数据单元为空
		int dataLength = 0;
		// 心跳报文命令编码 为 0x07
		short commandIdentifier = (short) 0x07;
		// 获得首部编码
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		// 构造心跳报文
		byte[] heartBeatBytes = new byte[headerBytes.length + 1];
		// 放入首部编码
		for (int i = 0; i < headerBytes.length; i++) {
			heartBeatBytes[i] = headerBytes[i];
		}
		// 计算并放入校验码
		byte checkCode = CheckCode.calculateCheckCode(heartBeatBytes);
		heartBeatBytes[heartBeatBytes.length - 1] = checkCode;
		
//		System.out.println("心跳报文 : " + new Date());
		return heartBeatBytes;
	} 

}
