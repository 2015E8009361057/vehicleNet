package com.cit.its.messageDecoder;

import java.io.UnsupportedEncodingException;

import com.cit.its.messageStruct.CommandType;
import com.cit.its.messageStruct.DataEncryModeType;
import com.cit.its.messageStruct.ResponseType;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.Unsigned;

public class HeaderDecoder {
	/**
	 * 取得命令标识
	 * @param bytes
	 * @return 命令标识
	 */
	public static CommandType getCommandType(byte[] bytes) {
		return CommandType.getCommandTypeByValue(Unsigned.getUnsignedByte(bytes[2]));
	}
	
	/**
	 * 取得应答标志
	 * @param bytes
	 * @return 应答标志
	 */
	public static ResponseType getResponseType(byte[] bytes) {
		return ResponseType.getResponseTypeByValue(Unsigned.getUnsignedByte(bytes[3]));
	}
	
	/**
	 * 取得车辆唯一识别码
	 * @param bytes
	 * @return 车辆识别码
	 * @throws UnsupportedEncodingException
	 */
	public static String getVehicleVIN(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, 4, 17, "utf-8");
	}
	
	public static DataEncryModeType getDataEncryModeType(byte[] bytes) {
		return DataEncryModeType.getDataEncryModeTypeByValue(Unsigned.getUnsignedByte(bytes[21]));
	}
	
	public static int getDataLength(byte[] bytes) {
		short dataLength = ByteUtil.getInstance().getShort(bytes, 22);
		return Unsigned.getUnsignedShort(dataLength);
	}
	

}
