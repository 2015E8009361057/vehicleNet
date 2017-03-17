package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageStruct.VehicleLogin;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.DateTool;

public class TestVehicleLoginMessage {
	
	public static byte[] getLoginBytes() throws ParseException {
		VehicleLogin vehicleLogin = new VehicleLogin();
		Date loginTime = new Date();
		String simpleDate = DateTool.formatDate(loginTime);
		vehicleLogin.setVehicleVIN("12345678901234567");
		vehicleLogin.setLoginSerialNumber(65500);
		vehicleLogin.setDataCollectTime(simpleDate);
		vehicleLogin.setSimICCID("abcdefghij1234567890");
		vehicleLogin.setNumberOfRESS((short) 5);
		vehicleLogin.setCodingLengthOfRESS((byte) 5);
		int length = vehicleLogin.getNumberOfRESS() * vehicleLogin.getCodingLengthOfRESS();
		String codeForRESS = "1234567890abcdefghij12345";
		vehicleLogin.setCodeForRESS(codeForRESS);
		int dataLength = length + 30;
		short commandIdentifier = (short) 0x01;
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleLogin.getVehicleVIN(), commandIdentifier, dataLength);
		
		byte[] loginBytes = new byte[headerBytes.length + dataLength + 1];
		int pos = 0;
		for (int i = 0; i < headerBytes.length; i++) {
			loginBytes[pos] = headerBytes[i];
			pos = pos + 1;
		}
		
		ByteUtil.getInstance().putDateToByteArray(loginTime, loginBytes, pos);
		pos = pos + 6;
		
		ByteUtil.getInstance().putShort(loginBytes, (short) vehicleLogin.getLoginSerialNumber(), pos);
		pos = pos + 2;
		
		ByteUtil.getInstance().putStringToByteArray(vehicleLogin.getSimICCID(), loginBytes, pos);
		pos = pos + vehicleLogin.getSimICCID().length();
		
		loginBytes[pos] = (byte) vehicleLogin.getNumberOfRESS();
		pos = pos + 1;
		
		loginBytes[pos] = vehicleLogin.getCodingLengthOfRESS();
		pos = pos + 1;
		
		ByteUtil.getInstance().putStringToByteArray(codeForRESS, loginBytes, pos);
		pos = pos + codeForRESS.length();
		
		if (pos == loginBytes.length - 1) {
			System.out.println("登入消息编码正常");
		}
		
		byte checkCode = CheckCode.calculateCheckCode(loginBytes);
		loginBytes[loginBytes.length - 1] = checkCode;
		
		System.out.println("车辆登入消息 : " + vehicleLogin);
		
		return loginBytes;
	}
	
}
