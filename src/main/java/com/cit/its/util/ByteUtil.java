package com.cit.its.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

public class ByteUtil {
	
	/**
	 * 将short转换为byte
	 * @param b 要转换进的byte数组
	 * @param s 需转换的short
	 * @param pos 放入数组的起始位置(下标)
	 */
	public static void putShort(byte[] b, short s, int pos) {
		b[pos + 1] = (byte) (s >> 8);
		b[pos + 0] = (byte) (s >> 0);
	}
	
	/**
	 * byte数组转换为short
	 * @param b 待转换的byte数组
	 * @param pos 转换开始位置
	 * @return 转换后的short
	 */
	public static short getShort(byte[] b, int pos) {
		return (short) (((b[pos + 1] << 8) | b[pos + 0] & 0xff));
	}
	
	/**
	 * 将String放入byte数组
	 * @param str	待放入的字符串
	 * @param bytes	目标数组
	 * @param pos	开始放入的位置
	 * @return		pos应该后移的长度
	 */
	public static int putStringToByteArray(String str, byte[] bytes, int pos) {
		byte[] bStr = str.getBytes();
		int length = bStr.length;
		for (int i = 0; i < length; i++) {
			bytes[pos + i] = bStr[i];
		}
		return length;
	}
	
	/**
	 * 将byte数组转换为String
	 * @param bytes		待转换byte数组
	 * @param pos     	转换开始的位置（下标）
	 * @param length	转换的String的长度
	 * @return			转换后的结果
	 * @throws Exception
	 */
	public static String getStringFromByteArray(byte[] bytes, int pos, int length) throws Exception{
		return new String(bytes, pos, length, "utf-8");
	}
	
	/**
	 * 将日期放入byte数组中
	 * @param dateTime
	 * @param bytes
	 * @param pos
	 * @throws ParseException
	 */
	public static void putDateToByteArray(Date dateTime, byte[] bytes, int pos) throws ParseException {
		String date = DateTool.formatDate(dateTime);
		byte year = Byte.valueOf(date.substring(2,4));
		byte month = Byte.valueOf(date.substring(5,7));
		byte day = Byte.valueOf(date.substring(8,10));
		byte hour = Byte.valueOf(date.substring(11,13));
		byte minute = Byte.valueOf(date.substring(14,16));
		byte second = Byte.valueOf(date.substring(17));
		bytes[pos + 0] = year;
		bytes[pos + 1] = month;
		bytes[pos + 2] = day;
		bytes[pos + 3] = hour;
		bytes[pos + 4] = minute;
		bytes[pos + 5] = second;
	}
	
	
	/**
	 * 从byte数组中取得String日期
	 * @param bytes
	 * @param pos
	 * @return 转换后的String日期
	 */
	public static String getStringDateFromByteArray(byte[] bytes, int pos) {
		String simpleDate = "20" + bytes[pos] + "-" + bytes[pos + 1] + "-" + bytes[pos + 2] + " ";
		simpleDate = simpleDate + bytes[pos + 3] + ":" + bytes[pos + 4] + ":" + bytes[pos + 5];
		return simpleDate;
	}
	
	
	
	/**
	 * int转byte数组
	 * @param b   转换进的目标byte数组
	 * @param x   待转换的int
	 * @param pos 放入数组的起始位置
	 */
	public static void putInt(byte[] b, int x, int pos) {
		b[pos + 3] = (byte) (x >> 24);
		b[pos + 2] = (byte) (x >> 16);
		b[pos + 1] = (byte) (x >> 8);
		b[pos + 0] = (byte) (x >> 0);
	}

	/**
	 * byte数组转int
	 * @param b   待转换的byte数组
	 * @param pos 转换开始位置
	 * @return    转换后的int
	 */
	public static int getInt(byte[] b, int pos) {
		return (int) ((((b[pos + 3] & 0xff) << 24) | ((b[pos + 2] & 0xff) << 16) | ((b[pos + 1] & 0xff) << 8) | ((b[pos + 0] & 0xff) << 0)));
	}
	
	
	// 参考 http://blog.csdn.net/leetcworks/article/details/7390731
	 /** 
     * 转换long型为byte数组 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putLong(byte[] bb, long x, int index) {  
        bb[index + 7] = (byte) (x >> 56);  
        bb[index + 6] = (byte) (x >> 48);  
        bb[index + 5] = (byte) (x >> 40);  
        bb[index + 4] = (byte) (x >> 32);  
        bb[index + 3] = (byte) (x >> 24);  
        bb[index + 2] = (byte) (x >> 16);  
        bb[index + 1] = (byte) (x >> 8);  
        bb[index + 0] = (byte) (x >> 0);  
    }  
  
    /** 
     * 通过byte数组取到long 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static long getLong(byte[] bb, int index) {  
        return ((((long) bb[index + 7] & 0xff) << 56)  
                | (((long) bb[index + 6] & 0xff) << 48)  
                | (((long) bb[index + 5] & 0xff) << 40)  
                | (((long) bb[index + 4] & 0xff) << 32)  
                | (((long) bb[index + 3] & 0xff) << 24)  
                | (((long) bb[index + 2] & 0xff) << 16)  
                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));  
    }  
  
    /** 
     * 字符到字节转换 
     *  
     * @param ch 
     * @return 
     */  
    public static void putChar(byte[] bb, char ch, int index) {  
        int temp = (int) ch;  
        // byte[] b = new byte[2];  
        for (int i = 0; i < 2; i ++ ) {  
            bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位  
            temp = temp >> 8; // 向右移8位  
        }  
    }  
  
    /** 
     * 字节到字符转换 
     *  
     * @param b 
     * @return 
     */  
    public static char getChar(byte[] b, int index) {  
        int s = 0;  
        if (b[index + 1] > 0)  
            s += b[index + 1];  
        else  
            s += 256 + b[index + 0];  
        s *= 256;  
        if (b[index + 0] > 0)  
            s += b[index + 1];  
        else  
            s += 256 + b[index + 0];  
        char ch = (char) s;  
        return ch;  
    }  
  
    /** 
     * float转换byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putFloat(byte[] bb, float x, int index) {  
        // byte[] b = new byte[4];  
        int l = Float.floatToIntBits(x);  
        for (int i = 0; i < 4; i++) {  
            bb[index + i] = new Integer(l).byteValue();  
            l = l >> 8;  
        }  
    }  
  
    /** 
     * 通过byte数组取得float 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static float getFloat(byte[] b, int index) {  
        int l;  
        l = b[index + 0];  
        l &= 0xff;  
        l |= ((long) b[index + 1] << 8);  
        l &= 0xffff;  
        l |= ((long) b[index + 2] << 16);  
        l &= 0xffffff;  
        l |= ((long) b[index + 3] << 24);  
        return Float.intBitsToFloat(l);  
    }  
  
    /** 
     * double转换byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static void putDouble(byte[] bb, double x, int index) {  
        // byte[] b = new byte[8];  
        long l = Double.doubleToLongBits(x);  
        for (int i = 0; i < 4; i++) {  
            bb[index + i] = new Long(l).byteValue();  
            l = l >> 8;  
        }  
    }  
  
    /** 
     * 通过byte数组取得float 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public static double getDouble(byte[] b, int index) {  
        long l;  
        l = b[0];  
        l &= 0xff;  
        l |= ((long) b[1] << 8);  
        l &= 0xffff;  
        l |= ((long) b[2] << 16);  
        l &= 0xffffff;  
        l |= ((long) b[3] << 24);  
        l &= 0xffffffffl;  
        l |= ((long) b[4] << 32);  
        l &= 0xffffffffffl;  
        l |= ((long) b[5] << 40);  
        l &= 0xffffffffffffl;  
        l |= ((long) b[6] << 48);  
        l &= 0xffffffffffffffl;  
        l |= ((long) b[7] << 56);  
        return Double.longBitsToDouble(l);  
    }  
    
    
    /** 
     * 将一个单字节的byte转换成32位的int 
     *  
     * @param b 
     *            byte 
     * @return convert result 
     */  
    public static int unsignedByteToInt(byte b) {  
        return (int) b & 0xFF;  
    }  
  
    /** 
     * 将一个单字节的Byte转换成十六进制的数 
     *  
     * @param b 
     *            byte 
     * @return convert result 
     */  
    public static String byteToHex(byte b) {  
        int i = b & 0xFF;  
        return Integer.toHexString(i);  
    }  
  
    /** 
     * 将一个4byte的数组转换成32位的int 
     *  
     * @param buf 
     *            bytes buffer 
     * @param byte[]中开始转换的位置 
     * @return convert result 
     */  
    public static long unsigned4BytesToInt(byte[] buf, int pos) {  
        int firstByte = 0;  
        int secondByte = 0;  
        int thirdByte = 0;  
        int fourthByte = 0;  
        int index = pos;  
        firstByte = (0x000000FF & ((int) buf[index]));  
        secondByte = (0x000000FF & ((int) buf[index + 1]));  
        thirdByte = (0x000000FF & ((int) buf[index + 2]));  
        fourthByte = (0x000000FF & ((int) buf[index + 3]));  
        index = index + 4;  
        return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;  
    }  
  
    /** 
     * 将16位的short转换成byte数组 
     *  
     * @param s 
     *            short 
     * @return byte[] 长度为2 
     * */  
    public static byte[] shortToByteArray(short s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }  
  
    /** 
     * 将32位整数转换成长度为4的byte数组 
     *  
     * @param s 
     *            int 
     * @return byte[] 
     * */  
    public static byte[] intToByteArray(int s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 4; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }  
  
    /** 
     * long to byte[] 
     *  
     * @param s 
     *            long 
     * @return byte[] 
     * */  
    public static byte[] longToByteArray(long s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 8; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }  
  
    /**32位int转byte[]*/  
    public static byte[] int2byte(int res) {  
        byte[] targets = new byte[4];  
        targets[0] = (byte) (res & 0xff);// 最低位  
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位  
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位  
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。  
        return targets;  
    }  
  
    /** 
     * 将长度为2的byte数组转换为16位int 
     *  
     * @param res 
     *            byte[] 
     * @return int 
     * */  
    public static int byte2int(byte[] res) {  
        // res = InversionByte(res);  
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000  
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或  
        return targets;  
    }  
    
    public static byte[] int2binaryBytes(int data) throws UnsupportedEncodingException {
    	String binaryStr = java.lang.Integer.toBinaryString(data);
    	System.out.println("The result is : " + binaryStr);
    	byte[] result = binaryStr.getBytes("utf8");
    	for (int i = 0; i < result.length; i++) {
    		result[i] = (byte) (result[i] - '0');
    	}
    	return result;
    }
    
    public static byte[] byte2binaryBytesArray(byte data) throws UnsupportedEncodingException {
    	String binaryStr = java.lang.Byte.toString(data);
    	System.out.println("The result is : " + binaryStr);
    	byte[] result = binaryStr.getBytes("utf8");
    	for (int i = 0;  i < result.length; i++) {
    		result[i] = (byte) (result[i] - '0');
    	}
    	return result;
    }
    
}
