package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageStruct.VehicleLogin;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.DateTool;

public class VehicleLoginMessageEncoder {
	
	
	public static byte[] getVehicleLoginBytes(String vehicleVIN) throws ParseException {
		VehicleLogin vehicleLogin = new VehicleLogin();
		// 获得车辆登入信息
		String simICCID = Pick.pickSimICCID(vehicleVIN);
		Date dataCollectionTime = new Date();
		String simpleDate = DateTool.formatDate(dataCollectionTime);
		int loginSerialNumber = Pick.pickLoginSerialNumber();
		short numberOfRESS = (short) (Math.random() * 250);
		byte codingLengthOfRESS = (byte) (Math.random() * 50);
		int length = numberOfRESS * codingLengthOfRESS;
		byte[] code = new byte[length];
		
		byte[] chArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				  		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
				  		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		for (int i = 0; i < length; i++) {
			code[i] = chArr[(int)(Math.random() * chArr.length)];
		}
		String codeForRESS = new String(code);
		// 赋值
		vehicleLogin.setDataCollectTime(simpleDate);
		vehicleLogin.setVehicleVIN(vehicleVIN);
		vehicleLogin.setLoginSerialNumber(loginSerialNumber);
		vehicleLogin.setSimICCID(simICCID);
		vehicleLogin.setNumberOfRESS(numberOfRESS);
		vehicleLogin.setCodingLengthOfRESS(codingLengthOfRESS);
		vehicleLogin.setCodeForRESS(codeForRESS);
		
		// 获取首部编码
		int dataLength = length + 30;
		short commandIdentifier = (short) 0x01;
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 构造车辆登入编码
		byte[] vehicleLoginBytes = new byte[headerBytes.length + dataLength + 1];
		// 将首部编码放入车辆登入编码中
		int pos = 0;
		for (int i = 0; i < headerBytes.length; i++) {
			vehicleLoginBytes[pos] = headerBytes[i];
			pos++;
		}
		// 依据车辆登入数据格式依次放入数据
		// 放入采集时间
		ByteUtil.getInstance().putDateToByteArray(dataCollectionTime, vehicleLoginBytes, pos);
		pos = pos + 6;
		// 放入登入流水号
		ByteUtil.getInstance().putShort(vehicleLoginBytes, (short) loginSerialNumber, pos);
		pos = pos + 2;
		
		// 放入SIM卡ICCID号
		ByteUtil.getInstance().putStringToByteArray(simICCID, vehicleLoginBytes, pos);
		pos = pos + simICCID.length();
		
		// 放入可充电储能子系统数
		vehicleLoginBytes[pos] = (byte) numberOfRESS;
		pos = pos + 1;
		
		// 放入可充电储能系统编码长度
		vehicleLoginBytes[pos] = (byte) codingLengthOfRESS;
		pos = pos + 1;
		
		// 放入可充电系统编码
		for (int i = 0; i < code.length; i++) {
			vehicleLoginBytes[pos] = code[i];
			pos++;
		}
		
		// 此时，pos值应等于vehicleLoginBytes.length - 1
		if (pos == vehicleLoginBytes.length - 1) {
			System.out.println(vehicleVIN + " 登入消息编码正常!");
		}
		
		// 计算校验值并放入
		byte checkCode = CheckCode.calculateCheckCode(vehicleLoginBytes);
		vehicleLoginBytes[pos] = checkCode;
		
		System.out.println("车辆登入信息 : " + vehicleLogin);
		
		return vehicleLoginBytes;
	}

}
