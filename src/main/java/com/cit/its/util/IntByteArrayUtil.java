/**  
* @Title: IntByteArray.java 
* @Package util 
* @Description: TODO 
* @author zxl   
* @date 2015年3月8日 下午2:22:40 
*/ 

package com.cit.its.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * @Title: IntByteArray
 * @Description: TODO 
 * @author zxl 
 * @date 2015年3月8日 下午2:22:40  
 */
public class IntByteArrayUtil {

	public static byte[] int2ByteArray(int num) {
		//由低位到高位
		byte[] bLocalArr = new byte[4];
		for (int i = 0; i < 4; i++) {
			bLocalArr[i] = (byte) (num >> 8 * i & 0xFF);
		}
		return bLocalArr;
	}

	public final static short getShort(byte[] buf, boolean asc) 
	{
	    if (buf == null) 
	    {
	      throw new IllegalArgumentException("byte array is null!");
	    }
	    if (buf.length > 2) 
	    {
	      throw new IllegalArgumentException("byte array size > 2 !");
	    }
	    short r = 0;
	    if (asc)
	      for (int i = buf.length - 1; i >= 0; i--) 
	        {
	        r <<= 8;
	        r |= (buf[i] & 0x00ff);
	      }
	    else
	      for (int i = 0; i < buf.length; i++) 
	        {
	        r <<= 8;
	        r |= (buf[i] & 0x00ff);
	      }
	    return r;
	}
	
	public final static int getInt(byte[] buf, boolean asc) 
	{
	    if (buf == null) 
	    {
	      throw new IllegalArgumentException("byte array is null!");
	    }
	    if (buf.length > 4) 
	    {
	      throw new IllegalArgumentException("byte array size > 4 !");
	    }
	    int r = 0;
	    if (asc)
	      for (int i = buf.length - 1; i >= 0; i--) 
	        {
	        r <<= 8;
	        r |= (buf[i] & 0x000000ff);
	      }
	    else
	      for (int i = 0; i < buf.length; i++) 
	        {
	        r <<= 8;
	        r |= (buf[i] & 0x000000ff);
	      }
	    return r;
	}

	public static byte byteXOR(byte[] parameter) {
		byte ByteXOR = parameter[4];

		for (int i = 5; i < parameter.length; i++) {
			ByteXOR ^= parameter[i];
		}
		return ByteXOR;
	}
	//byteArray转换成16进制字符串
	public static String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	public static void List2Map(List<String> list,ConcurrentHashMap<String,Boolean> map){
		if(list.size()==0){
			return;
		}
		int size=list.size();
		String deviceId="";
		for(int i=0;i<size;i++){
			deviceId=list.get(i);
			if(deviceId !=null){
				map.put(deviceId, false);
			}
			
		}		
	}
/*  public static void main(String[] args){
	String s="1533";
	System.out.println(hexString2Bytes(s)[0]);
  }*/
  public static byte[] hexString2Bytes(String src) {  
      int l = src.length() / 2;  
      byte[] ret = new byte[l];  
      for (int i = 0; i < l; i++) {  
          ret[i] = (byte) Integer  
                  .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();  
      }  
      return ret;  
  }  
  private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	} 
  public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
}
