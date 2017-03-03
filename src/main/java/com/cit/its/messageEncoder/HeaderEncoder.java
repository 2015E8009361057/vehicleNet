package com.cit.its.messageEncoder;

/**
 * 头部结构, 单位为字节
 * 0~1: 起始符, '##'
 * 2: 命令标识
 * 3: 应答标志
 * 4~20: 唯一识别码
 * 21: 数据单元加密方式
 * 22~23: 数据单元长度
 */

import org.apache.log4j.Logger;

import com.cit.its.messageStruct.Header;
import com.cit.its.util.ByteUtil;




public class HeaderEncoder {
	
	static Logger logger = Logger.getLogger(HeaderEncoder.class);
	
	public static Header setHeader(String vehicleVIN, short commandIdentifier, int dataLength) {
		
		Header header = new Header();
		// 设置命令标识（参数查询、参数设置、车载终端控制）
		header.setCommandIdentifier(commandIdentifier);
		
		// 设置应答标志为0xFE，表示此包为命令包
		short responseFlag = 0xFE;
		header.setResponseFlag(responseFlag);
		
		// 设置唯一识别码
		header.setVehicleVIN(vehicleVIN);		
		
		// 设置数据单元加密方式（为不加密）
		short dataEncryptionMode = 0x01;
		header.setDataEncryptionMode(dataEncryptionMode);
		
		// 设置数据单元长度
		header.setDataLength(dataLength);
		
		return header;
		
	}
	
	public static byte[]  getByteArrayHeader(String vehicleVIN, short commandIdentifier, int dataLength) {
		
		Header header = setHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 头部编码24字节
		byte[] result = new byte[24];
		
		// 编码起始符
		byte[] startSymbol = header.getStartSymbol().getBytes();
		int pos = 0; 
		for (; pos < startSymbol.length; pos ++) {
			result[pos] = startSymbol[pos];
		}
		
		// 编码命令标识
		result[pos] = (byte) header.getCommandIdentifier();
		pos = pos + 1;
		
		// 编码应答标志
		result[pos] = (byte) header.getResponseFlag();
		pos = pos + 1;
		
		// 编码唯一识别码
		byte[] vehicVIN = header.getVehicleVIN().getBytes();
		
		for (int i = 0; i < vehicVIN.length; i++) {
			result[pos] = vehicVIN[i];
			pos = pos + 1;
		}
		
		// 编码数据单元加密方式
		result[pos] = (byte) header.getDataEncryptionMode();
		pos = pos + 1;
		
		// 编码数据单元长度
		short length = (short) header.getDataLength();
		ByteUtil.putShort(result, length, pos);
		pos = pos + 2;
		
		// 此时pos的值应为24，如果不是说明编码有误
		if (pos == 24) {
			logger.info(header.getVehicleVIN() + " " + header.getCommandIdentifier() + " encode succeed!");
			System.out.println(header.getVehicleVIN() + " " + header.getCommandIdentifier() + " encode succeed!");
		}
		else {
			logger.info(header.getVehicleVIN() + " " + header.getCommandIdentifier() + " encode failed!");
			System.out.println(header.getVehicleVIN() + " " + header.getCommandIdentifier() + " encode failed!");
		}
		
		return result;
		
	}

}
