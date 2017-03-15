package com.cit.its.messageEncoder;

import java.util.Date;

import com.cit.its.messageDecoder.CheckCode;

public class TerminalCorrectionMessageEncoder {
	
	public static byte[] getTerminalCorrectionBytes(String vehicleVIN) {
		byte[] correctionBytes;
		// 终端校时报文为空，所以数据单元长度为空
		int dataLength = 0;
		// 终端校时报文命令编码 为 0x08
		short commandIdentifier = (short) 0x08;
		// 获得首部编码
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		// 获得终端校时报文编码
		correctionBytes = new byte[headerBytes.length + 1];
		for (int i = 0; i < headerBytes.length; i++) {
			correctionBytes[i] = headerBytes[i];
		}
		// 计算并放入校验码
		byte checkCode = CheckCode.calculateCheckCode(correctionBytes);
		correctionBytes[correctionBytes.length - 1] = checkCode;
		System.out.println("终端校时报文 : " + new Date());
		return correctionBytes;
	}

}
