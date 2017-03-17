package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.util.ByteUtil;

/**
 * 参数查询报文编码
 * 参数查询命令格式：参数查询时间byte[6]、参数总数byte、参数ID列表byte[参数总数]
 * 所以，数据单元长度为： 参数总数+6+1
 * @author QingXi
 *
 */
public class ParameterQueryMessageEncoder {
	
	static Logger logger = Logger.getLogger(ParameterQueryMessageEncoder.class);
	
	public static byte[] parameterQueryEncoder(String vehicleVIN, short totalNumOfPara, byte[] parameterID) throws ParseException {
		short commandIdentifier = (short) 0x80;
		int dataLength = totalNumOfPara + 7;
		byte[] header = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 编码后的报文长度应为：头部长度+数据单元长度+1个字节的校验码
		byte[] result = new byte[header.length + dataLength + 1];
		
		int pos = 0;
		for (; pos < header.length; pos++) {
			result[pos] = header[pos];
		}
		
		//此时 pos 应该等于24
		if (pos == 24) {
			logger.info(vehicleVIN + " parameter query header encode succeed!");
		}
		else {
			logger.info(vehicleVIN + " parameter query header encode failed!");
		}
		
		// 编码参数查询时间
		Date pareQueryTime = new Date();
		ByteUtil.getInstance().putDateToByteArray(pareQueryTime, result, pos);
		pos = pos + 6;
		
		// 编码参数总数
		result[pos] = (byte) totalNumOfPara;
		pos = pos + 1;
		
		// 编码参数ID列表
		for (int i = 0; i < parameterID.length; i++) {
			result[pos] = parameterID[i];
			pos = pos + 1;
		}
		
		// 此时pos应等于result.length-1
		if (pos == result.length - 1) {
			logger.info(vehicleVIN + " parameter query data encode succeed!");
		}
		else {
			logger.info(vehicleVIN + " parameter query data encode failed!");
		}
		
		// 最后计算校验码，将校验码放入数组
		byte checkCode = CheckCode.calculateCheckCode(result);
		result[result.length - 1] = checkCode;
		
		logger.info(vehicleVIN + " parameter query message encode succeed!");
		
		return result;
	}

}
