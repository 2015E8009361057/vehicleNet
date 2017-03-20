package com.cit.its.messageDecoder;

import org.apache.log4j.Logger;

import com.cit.its.messageStruct.CommandType;


public class CheckCode {
	
	static Logger logger = Logger.getLogger(CheckCode.class);
	
	public static byte calculateCheckCode(byte[] bytes) {
		int length = bytes.length;
		byte checkCode = bytes[2];
		for (int i = 3; i < length - 1; i++) {
			checkCode ^= bytes[i];
		}
		return checkCode;
	}
	
	public static boolean verifyCheckCode(byte[] bytes, CommandType commandType, String vehicleVIN) {
		boolean result = true;
		byte checkCode = calculateCheckCode(bytes);
		
		if (checkCode != bytes[bytes.length - 1]) {
			result = false;
			logger.info(vehicleVIN + " " + commandType + " checkCode is not correct!");
			System.out.println(vehicleVIN + " " + commandType + " checkCode is not correct!");
		}
		else {
			result = true;
			logger.info(vehicleVIN + " " + commandType + " checkCode is correct!");
			System.out.println(vehicleVIN + " " + commandType + " checkCode is correct!");
		}
		return result;
	}

}
