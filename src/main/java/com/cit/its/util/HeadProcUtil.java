/**  
* @Title: HeadMsgProc.java 
* @Package com.cit.its.msgprecess 
* @Description: TODO 
* @author zxl   
* @date 2015年3月19日 下午12:10:33 
*/ 

package com.cit.its.util;


import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cit.its.util.IntByteArrayUtil;

/** 
 * @Title: HeadMsgProc
 * @Description: TODO 
 * @author zxl 
 * @date 2015年3月19日 下午12:10:33  
 */
public class HeadProcUtil{

	private static Logger logger  = LoggerFactory.getLogger(HeadProcUtil.class);	

	public static String  getDeviceId(byte[] bytes){
		    if(bytes[12] == 3){     //如果终端是逸卡终端
		    	char[] deviceId=new char[8];
		    	System.arraycopy(bytes, 13, deviceId, 0, 8);
				String s=new String(deviceId);
				return s;
		    }else if(bytes[12] == 4){
		    	byte[] temp=new byte[8];
				int[] result=new int[16];
				System.arraycopy(bytes,13,temp,0,8);
				for(int i=0,j=0;i<8 && j<16;i++,j++){
					int t=temp[i] & 0x000000FF;
					result[j]=(t >>> 4);
					result[++j]=(temp[i] & 0x0f);
				}
				String s="";
				for(int i=0;i<16;i++){
					s+=result[i];
				}
				return s;
		    }	
		    return "";
		}

	public static String  getDeviceIdBack(byte[] bytes){	
		char[] deviceId=new char[8];
		for(int i=0,j=13;i<8;i++,j++){
			deviceId[i]=(char)bytes[j];
		}
		String s=new String(deviceId);
		return s;
	}	
	public static short getDataLength(byte[] bytes){
		byte[] DataLength=new byte[2];
		System.arraycopy(bytes, 6, DataLength, 0, 2);
		short data_length=IntByteArrayUtil.getShort(DataLength, true);
		logger.info("datalength"+data_length);
		return data_length; 		
	}

	
	public static int getCsmType(byte[] bytes) throws NumberFormatException, Exception{
		byte[] csm_type=new byte[2];
		csm_type[0]=bytes[4];
		csm_type[1]=bytes[5];
		int msg_type=Integer.parseInt(IntByteArrayUtil.getHexString(csm_type));
		return msg_type;		
	}
	public static int getMsgId(byte[] bytes){
		byte[] csm_id=new byte[4];
		System.arraycopy(bytes, 8, csm_id, 0, 4);
		int msg_id=IntByteArrayUtil.getInt(csm_id, true);
		return msg_id;
		
	}
	
	public static byte getDeviceType(byte[] bytes){
		byte csm_type=bytes[12];
		return csm_type;		
	}
	
	public static byte getModels(byte[] bytes){
		byte b=bytes[23];
		return b;	
	}
	public static short getStartPos(byte[] bytes){
		byte[] temp=new byte[2];
		temp[0]=bytes[24];
		temp[1]=bytes[25];
		short start_pos=IntByteArrayUtil.getShort(temp, true);
		return start_pos;
		
	}
	public static short getDataNum(byte[] bytes){
		byte[] temp=new byte[2];
		temp[0]=bytes[27];
		temp[1]=bytes[28];
		short data_num=IntByteArrayUtil.getShort(temp, true);
		return data_num;
		
	}
	public static Date getTime(byte[] can){
		Calendar c=Calendar.getInstance();
		c.clear();
		c.set(can[49],can[50]-1,can[51],can[52],can[53],can[54]);
		Date time=c.getTime();
		return time;
		
	}

	public static String  getVIN(byte[] bytes,int j){	
		char[] deviceId=new char[17];
		for(int i=0;i<17;i++,j++){
			deviceId[i]=(char)bytes[j];
		}
		String s=new String(deviceId);
		return s;
	}

}
