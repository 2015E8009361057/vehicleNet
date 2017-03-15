package com.cit.its.messageDecoder;

import org.apache.log4j.Logger;

import com.cit.its.messageStruct.CommandType;
import com.cit.its.messageStruct.ResponseType;



public class ResponseTypeVerify {
	
	static Logger logger = Logger.getLogger(ResponseTypeVerify.class);
	
	public static boolean verifyResponseType(byte[] bytes, CommandType commandType, String vehicleVIN) {
		boolean result = true;
		
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		
		// 上行数据, 平台收到的应答标志应该为 0xFE, 表示此包非应答包
		if (commandType.equals(CommandType.VEHICLE_LOGIN) ||
			commandType.equals(CommandType.REAL_INFORMATION_UPLOAD) ||
			commandType.equals(CommandType.REISSUED_INFORMATION_UPLOAD) ||
			commandType.equals(CommandType.TERMINAL_CORRECTION) || 
			commandType.equals(CommandType.HEART_BEAT) || 
			commandType.equals(CommandType.VEHICLE_LOGOUT)) {
			
			if (responseType.equals(ResponseType.ORDER)) {
				result = true;
			}
			else {
				result = false;
			}
		}
		// 如果为 平台登入登出报文，暂时都假定为true
		else if (commandType.equals(CommandType.PLATFORM_LOGIN) || 
				 commandType.equals(CommandType.PLATFORM_LOGOUT)) {
			result = true;
		}
		// 其他的就是，参数查询、参数设置、车载终端控制报文，为下行报文，平台收到的一定是应答包
		else {
			if (responseType.equals(ResponseType.ORDER)) {
				result = false;
			}
			else {
				result = true;
			}
		}
		
		if (result == true) {
			logger.info(vehicleVIN + " CommandType : " + commandType + " ResponseType : " + responseType + " is correct!");
			System.out.println(vehicleVIN + " CommandType : " + commandType + " ResponseType : " + responseType + " is correct!");
		}
		else {
			logger.info(vehicleVIN + " CommandType : " + commandType + " ResponseType : " + responseType + " is not correct!");
			System.out.println(vehicleVIN + " CommandType : " + commandType + " ResponseType : " + responseType + " is not correct!");
		}
		
		return result;
	}

}
