package com.cit.its.util;

import java.util.Date;

public class TestBigEndian {
	
	public static void main(String[] args) throws Exception {
		short s = (short) 65532;
		byte[] bytes = new byte[2];
		System.out.println("s :" + s);
		ByteUtil.getInstance().putShort(bytes, s, 0);
		short temp = ByteUtil.getInstance().getShort(bytes, 0);
		int len = Unsigned.getUnsignedShort(temp);
		System.out.println("temp : " + temp);
		System.out.println("len : " + len);
		byte[] intbytes = new byte[4];
		ByteUtil.getInstance().putInt(intbytes, len, 0);
		int tempLen = ByteUtil.getInstance().getInt(intbytes, 0);
		System.out.println("tempLen : " + tempLen);
		String str = "abcdefghij1234567890";
		byte[] strBytes = new byte[str.length()];
		ByteUtil.getInstance().putStringToByteArray(str, strBytes, 0);
		String reString = ByteUtil.getInstance().getStringFromByteArray(strBytes, 0, strBytes.length);
		System.out.println("reString : " + reString);
		
		Date date = new Date();
		byte[] dateBytes = new byte[6];
		ByteUtil.getInstance().putDateToByteArray(date, dateBytes, 0);
		String dateStr = ByteUtil.getInstance().getStringDateFromByteArray(dateBytes, 0);
		System.out.println("dateStr : " + dateStr);
	}

}
