package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageStruct.VehicleLogout;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.DateTool;

public class VehicleLogoutMessageEncoder {
	
	public static byte[] getVehicleLogoutBytes(String vehicleVIN) throws ParseException {
		// 消息体
		VehicleLogout vehicleLogout = new VehicleLogout();
		vehicleLogout.setVehicleVIN(vehicleVIN);
		Date logout = new Date();
		String logoutTime = DateTool.formatDate(logout);
		vehicleLogout.setLogoutTime(logoutTime);
		int logoutSerialNumber = Pick.pickLogoutSerialNumber();
		vehicleLogout.setLogoutSerialNumber(logoutSerialNumber);
		
		// 数据单元长度为 登出时间6字节+登出流水号2字节
		int dataLength = 8;
		// 车辆登出报文命令编码为 0x04
		short commandIdentifier = (short) 0x04;
		// 获取首部编码
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 车辆登出编码
		byte[] vehicleLogoutBytes = new byte[headerBytes.length + dataLength + 1];
		// 放入首部编码
		for (int i = 0; i < headerBytes.length; i++) {
			vehicleLogoutBytes[i] = headerBytes[i];
		}
		
		int pos = headerBytes.length;
		
		// 放入登出时间
		ByteUtil.putDateToByteArray(logout, vehicleLogoutBytes, pos);
		pos = pos + 6;
		// 放入流水号
		ByteUtil.putShort(vehicleLogoutBytes, (short)logoutSerialNumber, pos);
		pos = pos + 2;
		
		// 此时，pos的值应等于 vehicleLogoutBytes.length - 1
		if (pos == vehicleLogoutBytes.length) {
			System.out.println("车辆登出编码正常");
		}
		
		// 计算并放入校验码
		byte checkCode = CheckCode.calculateCheckCode(vehicleLogoutBytes);
		vehicleLogoutBytes[vehicleLogoutBytes.length - 1] = checkCode;
		
		System.out.println("车辆登出信息 : " + vehicleLogout);
		
		return vehicleLogoutBytes;
	}

}
