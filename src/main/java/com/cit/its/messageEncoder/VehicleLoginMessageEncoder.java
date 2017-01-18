package com.cit.its.messageEncoder;



import java.text.ParseException;
import java.util.Date;


import com.cit.its.message.VehicleLoginMessage;
import com.cit.its.messageStruct.BodyVehicleLogin;
import com.cit.its.messageStruct.Footer;
import com.cit.its.messageStruct.Header;
import com.cit.its.util.DateTool;


public class VehicleLoginMessageEncoder {
	
	private byte[] bytes;
	
	public VehicleLoginMessageEncoder() throws ParseException {
		VehicleLoginMessage vehicleLoginMessage = new VehicleLoginMessage();
		Header header = vehicleLoginMessage.getHeader();
		BodyVehicleLogin vehicleLoginInfo = new BodyVehicleLogin();
		Footer checkCode = new Footer();
		// 数据单元长度表示的是数据单元的总字节数
		// 前面的22字节是消息头的长度
		// 最后一个字节是校验码
		int byteSize = header.getDataLength() + 25;
		System.out.println("bytesize : " + byteSize);
		// 申请空间
		bytes = new byte[byteSize];
		
		// 将header信息放入byte数组
		byte[] startSymbol = header.getStartSymbol().getBytes();
		
		int pos = 0;
		for (; pos < startSymbol.length; pos++) {
			bytes[pos] = startSymbol[pos];
		}
		
		bytes[pos++] = (byte) header.getCommandIdentifier();
		
		bytes[pos++] = (byte) header.getResponseFlag();
		
		
		byte[] vehicleVIN = header.getVehicleVIN().getBytes();
		
		for (int i = 0; i < vehicleVIN.length; i++) {
			bytes[pos++] = vehicleVIN[i];
		}
		
		bytes[pos++] = (byte) header.getDataEncryptionMode();
		
		short dataLength = (short) header.getDataLength();
		
		bytes[pos+2] = (byte) (dataLength >> 8);
		bytes[pos+1] = (byte) (dataLength >> 0);
		
		pos = pos + 2;
		
		// 将消息体放入byte数组
		Date dateCollectionTime = vehicleLoginInfo.getDataCollectionTime();
		String date = DateTool.formatDate(dateCollectionTime);
		byte year = Byte.valueOf(date.substring(2,4));
		byte month = Byte.valueOf(date.substring(5,7));
		byte day = Byte.valueOf(date.substring(8,10));
		byte hour = Byte.valueOf(date.substring(11,13));
		byte minute = Byte.valueOf(date.substring(14,16));
		byte second = Byte.valueOf(date.substring(17));
		
		bytes[pos++] = year;
		bytes[pos++] = month;
		bytes[pos++] = day;
		bytes[pos++] = hour;
		bytes[pos++] = minute;
		bytes[pos++] = second;
		
		short loginSerialNumber = (short) vehicleLoginInfo.getLoginSerialNumber();
		System.out.println("pos : " + pos);
		bytes[pos+1] = (byte) (loginSerialNumber >> 8);
		bytes[pos] = (byte) (loginSerialNumber >> 0);
		
		pos = pos + 2;
		
		
		byte[] SIMCardICCIDNumber = vehicleLoginInfo.getSIMCardICCIDNumber().getBytes();
		for (int i = 0; i < SIMCardICCIDNumber.length; i++) {
			bytes[pos++] = SIMCardICCIDNumber[i];
		}
		
		bytes[pos++] = (byte) vehicleLoginInfo.getNumberOfRESS();
		bytes[pos++] = vehicleLoginInfo.getCodingLengthOfRESS();
		
		byte[] codeForRESS = vehicleLoginInfo.getCodeForRESS().getBytes();
		
		System.out.println("codeForRESS.length : " + codeForRESS.length);
		System.out.println("pos : " + pos);
		for (int i = 0; i < codeForRESS.length; i++) {
			System.out.println("pos : " + pos);
			bytes[pos++] = codeForRESS[i];
		}
		
		if (pos <= bytes.length - 1) {
			System.out.println("编码正常");
		}
		else {
			System.out.println("编码有误");
		}
		
		// 计算校验码
		byte xorCode = bytes[0];
		for (int i = 1; i <= pos; i++) {
			xorCode ^= bytes[i]; 
		}
		bytes[bytes.length - 1] = xorCode;
		
		// 赋值
		checkCode.setCheckCode(xorCode);
	}
	
	public byte[] getEncodeBytes() {
		return this.bytes;
	}

}
