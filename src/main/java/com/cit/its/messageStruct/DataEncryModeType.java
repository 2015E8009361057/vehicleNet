package com.cit.its.messageStruct;

public enum DataEncryModeType {
	
	NO_ENCRY((short) 0x01),					// 数据不加密
	RSA_ENCRY((short) 0x02),				// 数据经过RSA算法加密
	AES128_ENCRY((short) 0x03),				// 数据经过AES128算法加密
	EXCEPTION_ENCRY((short) 0xFE),			// 异常
	INVALID_ENCRY((short) 0xFF);			// 无效
	
	private short value;
	
	private DataEncryModeType(short value) {
		this.value = value;
	}
	
	public short value() {
		return this.value;
	}
	
	public static DataEncryModeType getDataEncryModeTypeByValue(short value) {
		for (DataEncryModeType dataEncryModeType : DataEncryModeType.values()) {
			if (value == dataEncryModeType.value()) {
				return dataEncryModeType;
			}
		}
		return null;
	}

}
