package com.cit.its.messageDecoder;

import org.apache.log4j.Logger;

import com.cit.its.messageStruct.DataEncryModeType;


public class DataDecrypt {
	
	Logger logger = Logger.getLogger(DataDecrypt.class);
	
	public static void Decrypt(byte[] bytes, DataEncryModeType dataEncryModeType) {
		switch(dataEncryModeType) {
		// 没有使用加密算法
		case NO_ENCRY:
			break;
		// 使用了RSA加密算法
		case RSA_ENCRY:
		// 使用RSA算法对数据单元进行解密 
			RSA_Decrypt(bytes);
			break;
		case AES128_ENCRY:
		// 使用AES128算法对数据单元进行解密
			AES128_Decrypt(bytes);
			break;
		default:
			break;
		}
	}
	
	public static void RSA_Decrypt(byte[] bytes) {
		
	}
	
	public static void AES128_Decrypt(byte[] bytes) {
		
	}
	
	

}
