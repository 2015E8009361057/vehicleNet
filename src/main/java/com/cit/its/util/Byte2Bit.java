/**  
* @Title: Byte2Bit.java 
* @Package util 
* @Description: TODO 
* @author zxl   
* @date 2015年3月9日 下午4:07:06 
*/ 

package com.cit.its.util;


/** 
 * @Title: Byte2Bit
 * @Description: TODO 
 * @author zxl 
 * @date 2015年3月9日 下午4:07:06  
 */
public class Byte2Bit {
	
	/** 
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit array[7]代表第0个bit
     */ 
	public static byte[] getBooleanArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  

}
