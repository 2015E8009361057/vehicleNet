package com.cit.its.messageStruct;



// 使用到的数据结构定义
// 分为三个部分：消息头，消息体，消息尾（也就是校验码）
// 这是对消息头的定义
public final class Header {
	
	private String startSymbol = "##";	// 起始符
	
	private short commandIdentifier;	// 命令标识
	
	private short responseFlag;	// 应答标志
	
	private String vehicleVIN;	// 车辆识别码
	
	private short dataEncryptionMode;	// 数据加密方式
	
	// 数据单元长度应该是Unsigned short 类型，有效值范围0~65531
	// 存储数据时，用short 关键字强制类型转换，而后取出时，用 Unsigned 类的 getUnsignedShort方法
	private int dataLength;	// 数据单元长度
	
	
	
	/**
	 * @return the startSymbol
	 */
	public final String getStartSymbol() {
		return startSymbol;
	}
	
/*	
	// 起始符是确定的，所以这个函数不需要
	public final void setStartSymbol(String startSymbol) {
		this.startSymbol = startSymbol;
	}
	
	*/
	
	/**
	 * @return the commandIdentifier
	 */
	public final short getCommandIdentifier() {
		return commandIdentifier;
	}
	
	/**
	 * @param commandIdentifier
	 * the commandIdentifier to set
	 */
	public final void setCommandIdentifier(short commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
	}
	
	/**
	 * @return the responseFlag
	 */
	public final short getResponseFlag() {
		return responseFlag;
	}
	
	
	/**
	 * @param responseFlag
	 * the responseFlag to set
	 */
	public final void setResponseFlag(short responseFlag) {
		this.responseFlag = responseFlag;
	}
	
	/**
	 * @return the vehicleVIN
	 */
	public final String getVehicleVIN() {
		return vehicleVIN;
	}
	
	/**
	 * @param vehicleVIN
	 * the vehicleVIN to set
	 */
	public final void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	/**
	 * @return the dataEncryptionMode
	 */
	public final short getDataEncryptionMode() {
		return dataEncryptionMode;
	}
	
	/**
	 * @param dataEncryptionMode
	 * the dataEncryptionMode to set
	 */
	public final void setDataEncryptionMode(short dataEncryptionMode) {
		this.dataEncryptionMode = dataEncryptionMode;
	}
	
	/**
	 * @return the dataLength
	 */
	public final int getDataLength() {
		return dataLength;
	}
	
	/**
	 * @param dataLength
	 * the dataLength to set
	 */
	public final void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	
	@Override
	public String toString() {
		return vehicleVIN;
	}
}
