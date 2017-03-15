package com.cit.its.util;

public class Unsigned {
	
	public static short getUnsignedByte(byte data) {
		// 将byte字节型数据转换为0~255 (0xFF 即 BYTE)
		return (short) (data & 0xFF);
	}
	
	public static int getUnsignedShort(short data) {
		// 将short字节型数据转换为0~65535 (0xFFFF 即 WORD)
		return (int) (data & 0xFFFF);
	}
	
	public static long getUnsignedInt(int data) {
		// 将int字节型数据转换为0~2^32-1 (0xFFFFFFFF 即 DWORD)
		return (long) (data & 0xFFFFFFFF);
	}
	
/*	
	public static void main(String[] args) {
		byte data = (byte) ((short) 0x80);
		short data1 = (short) 65535;
		System.out.println(getUnsignedByte(data));
		System.out.println(getUnsignedShort(data1));
	}
	
*/

}
