package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.util.ByteUtil;

public class TerminalControlMessageEncoder {
	
	static Logger logger = Logger.getLogger(TerminalControlMessageEncoder.class);
	
	public static byte[] terminalControlEncode(String vehicleVIN, byte orderID, String orderParameter) throws ParseException {
		// 根据命令ID的不同，计算数据单元部分的长度 = orderParameter.length() + 7
		int dataLength = 7 + orderParameter.length();
		short commandIdentifier = (short) 0x82;
		byte[] header = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 将头部放入结果数组
		int pos = 0;
		byte[] result = new byte[header.length + dataLength + 1];
		for (int i = 0; i < header.length; i++) {
			result[pos] = header[i];
			pos++;
		}
		
		// 将命令时间放入结果数组
		Date controlTime = new Date();
		ByteUtil.putDateToByteArray(controlTime, result, pos);
		pos = pos + 6;
		
		// 将命令ID放入结果数组
		result[pos] = orderID;
		pos = pos + 1;
		
		
		// 将命令参数放入结果数组
		byte[] temp = orderParameter.getBytes();
		for (int j = 0; j < temp.length; j++) {
			result[pos] = temp[j];
			pos++;
		}
		
		// 此时，pos值应等于result.length-1
		if (pos == result.length - 1) {
			logger.info(vehicleVIN + " terminal control data encode succeed!");
		}
		else {
			logger.info(vehicleVIN + " terminal control data encode failed!");
		}
		
		// 最后计算校验码，将校验码放入数组
		byte checkCode = CheckCode.calculateCheckCode(result);
		result[result.length - 1] = checkCode;
		
		logger.info(vehicleVIN + " terminal control message encode succeed!");
		
		return result;
	}

}
