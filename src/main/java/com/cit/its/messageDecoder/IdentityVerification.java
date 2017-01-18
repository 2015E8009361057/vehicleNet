package com.cit.its.messageDecoder;

import org.apache.log4j.Logger;

import com.cit.its.messageStruct.CommandType;


public class IdentityVerification {
	
	Logger logger = Logger.getLogger(IdentityVerification.class);
	
	public static boolean identityVerify(CommandType commandType, String vehicleVIN) {
		// 连接数据库
		
		
		boolean result = true;
		// 如果是车辆登入报文
		if (commandType.equals(CommandType.VEHICLE_LOGIN)) {
			// 查询车辆静态信息表，如果有，则继续查询车辆最新状态表
			// 若车辆最新状态表中该车辆为未登入，则身份验证通过
		}
		// 其他报文，因为其他报文都是在车辆已经登入的情况下进行的，所以，此时只需查询车辆最新状态表
		else {
			// 查询车辆最新状态表，若该车辆为已登入，则身份验证通过
		}
		
		return result;
	}

}
