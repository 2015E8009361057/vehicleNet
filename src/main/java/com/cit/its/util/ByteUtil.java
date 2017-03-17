package com.cit.its.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

public abstract class ByteUtil {
	
	/**
	 * 将short转换为byte
	 * @param b 要转换进的byte数组
	 * @param s 需转换的short
	 * @param pos 放入数组的起始位置(下标)
	 */
	public abstract void putShort(byte[] b, short s, int pos) ;
	
	
	/**
	 * byte数组转换为short
	 * @param b 待转换的byte数组
	 * @param pos 转换开始位置
	 * @return 转换后的short
	 */
	public abstract short getShort(byte[] b, int pos) ;
	
	/**
	 * 将String放入byte数组
	 * @param str	待放入的字符串
	 * @param bytes	目标数组
	 * @param pos	开始放入的位置
	 * @return		pos应该后移的长度
	 */
	public abstract int putStringToByteArray(String str, byte[] bytes, int pos) ;
	
	/**
	 * 将byte数组转换为String
	 * @param bytes		待转换byte数组
	 * @param pos     	转换开始的位置（下标）
	 * @param length	转换的String的长度
	 * @return			转换后的结果
	 * @throws Exception
	 */
	public abstract String getStringFromByteArray(byte[] bytes, int pos, int length) throws Exception;
	
	/**
	 * 将日期放入byte数组中
	 * @param dateTime
	 * @param bytes
	 * @param pos
	 * @throws ParseException
	 */
	public  abstract void putDateToByteArray(Date dateTime, byte[] bytes, int pos) throws ParseException;
	
	
	/**
	 * 从byte数组中取得String日期
	 * @param bytes
	 * @param pos
	 * @return 转换后的String日期
	 */
	public abstract String getStringDateFromByteArray(byte[] bytes, int pos);
	
	
	
	/**
	 * int转byte数组
	 * @param b   转换进的目标byte数组
	 * @param x   待转换的int
	 * @param pos 放入数组的起始位置
	 */
	public abstract void putInt(byte[] b, int x, int pos);

	/**
	 * byte数组转int
	 * @param b   待转换的byte数组
	 * @param pos 转换开始位置
	 * @return    转换后的int
	 */
	public  abstract int getInt(byte[] b, int pos);
	
	
	// 参考 http://blog.csdn.net/leetcworks/article/details/7390731
	 /** 
     * 转换long型为byte数组 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public abstract  void putLong(byte[] bb, long x, int index);
  
    /** 
     * 通过byte数组取到long 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public abstract long getLong(byte[] bb, int index);
    /** 
     * 字符到字节转换 
     *  
     * @param ch 
     * @return 
     */  
    public abstract void putChar(byte[] bb, char ch, int index) ;
  
    /** 
     * 字节到字符转换 
     *  
     * @param b 
     * @return 
     */  
    public abstract char getChar(byte[] b, int index);
  
    /** 
     * float转换byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public abstract void putFloat(byte[] bb, float x, int index) ;
  
    /** 
     * 通过byte数组取得float 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public abstract float getFloat(byte[] b, int index) ;
  
    /** 
     * double转换byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public abstract void putDouble(byte[] bb, double x, int index);
  
    /** 
     * 通过byte数组取得float 
     *  
     * @param bb 
     * @param index 
     * @return 
     */  
    public abstract double getDouble(byte[] b, int index) ;
    
    
    /** 
     * 将一个单字节的byte转换成32位的int 
     *  
     * @param b 
     *            byte 
     * @return convert result 
     */  
    public abstract  int unsignedByteToInt(byte b);
  
    /** 
     * 将一个单字节的Byte转换成十六进制的数 
     *  
     * @param b 
     *            byte 
     * @return convert result 
     */  
    public abstract String byteToHex(byte b) ;
  
    /** 
     * 将一个4byte的数组转换成32位的int 
     *  
     * @param buf 
     *            bytes buffer 
     * @param byte[]中开始转换的位置 
     * @return convert result 
     */  
    public abstract long unsigned4BytesToInt(byte[] buf, int pos);
  
    /** 
     * 将16位的short转换成byte数组 
     *  
     * @param s 
     *            short 
     * @return byte[] 长度为2 
     * */  
    public abstract byte[] shortToByteArray(short s) ;
  
    /** 
     * 将32位整数转换成长度为4的byte数组 
     *  
     * @param s 
     *            int 
     * @return byte[] 
     * */  
    public abstract byte[] intToByteArray(int s) ;
  
    /** 
     * long to byte[] 
     *  
     * @param s 
     *            long 
     * @return byte[] 
     * */  
    public abstract byte[] longToByteArray(long s);
  
    /**32位int转byte[]*/  
    public abstract byte[] int2byte(int res);
    /** 
     * 将长度为2的byte数组转换为16位int 
     *  
     * @param res 
     *            byte[] 
     * @return int 
     * */  
    public abstract int byte2int(byte[] res);
    
    public abstract byte[] int2binaryBytes(int data) throws UnsupportedEncodingException;
    
    public abstract byte[] byte2binaryBytesArray(byte data) throws UnsupportedEncodingException;
    static ByteUtil bigEndianByteUtil = new BigEndianByteUtil();
    static ByteUtil smallEndianByteUtil = new SmallEndianByteUtil();
    public static ByteUtil getInstance(){
    	return bigEndianByteUtil;
    }
}
