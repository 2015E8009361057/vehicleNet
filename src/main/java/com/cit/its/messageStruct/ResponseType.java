package com.cit.its.messageStruct;

public enum ResponseType {
	
	SUCCESS((short) 0x01),				// 成功，接收到的信息正确
	MODIFY_ERROR((short) 0x02),			// 错误，设置未成功
	VIN_REPEAT((short) 0x03),			// VIN重复，VIN重复错误(即车辆重复登录)
	ORDER((short) 0xFE);				// 命令，表示数据包为命令包，而非应答包
	
	private short value;
	
	private ResponseType(short value) {
		this.value = value;
	}
	
	public short value() {
		return this.value;
	}
	
	public static ResponseType getResponseTypeByValue(short value) {
		for (ResponseType responseType : ResponseType.values()) {
			if (value == responseType.value()) {
				return responseType;
			}
		}
		return null;
	}
}
