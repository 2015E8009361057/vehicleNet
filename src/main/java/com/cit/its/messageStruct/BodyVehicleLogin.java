package com.cit.its.messageStruct;

import java.util.Date;


/**
 * 车辆登入报文消息体
 * @author QingXi
 *
 */
public final class BodyVehicleLogin {
	
	private Date dataCollectionTime;					// 数据采集时间
	
	// 车载终端每登入一次，登入流水号自动加1，
	// 从1开始循环累加，最大值为65531，循环周期为天
	private int loginSerialNumber;						// 登入流水号
	
	private String SIMCardICCIDNumber;					// SIM卡ICCID号（终端从SIM卡获取的值，不应认为填写或修改）
	
	// RESS: rechargeable Energy Storage Subsystem
	// 可充电储能子系统：当车辆存在多套可充电储能系统混合
	// 使用时，每套可充电储能系统为一个可充电储能子系统
	private short  numberOfRESS;						// 可充电储能子系统数n,有效值范围：0~250
	
	private byte codingLengthOfRESS;					// 可充电储能系统编码长度m,有效值范围：0~50，“0”表示不上传该编码
	
	private String codeForRESS;							// 可充电储能系统编码（终端从车辆获取的值）
	
	public BodyVehicleLogin() {
		dataCollectionTime = new Date();
		loginSerialNumber = 65531;
		SIMCardICCIDNumber = "01234567899876543210";
		numberOfRESS = 20;
		codingLengthOfRESS = 2;
		codeForRESS = "0123456789987654321001234567899876543210";
		
	}
	
	public int getBodyVehicleLoginLength() {
		return 30 + numberOfRESS * codingLengthOfRESS;
	}
	
	
	/**
	 * @return the dataCollectionTime
	 */
	public final Date getDataCollectionTime() {
		return dataCollectionTime;
	}
	
	/**
	 * @param dateCollectionTime
	 * the dateCollectionTime to set
	 */
	public final void setDataCollectionTime(Date dateCollectionTime) {
		this.dataCollectionTime = dateCollectionTime;
	}
	
	/**
	 * @return the loginSerialNumber
	 */
	public final int getLoginSerialNumber() {
		return loginSerialNumber;
	}
	
	/**
	 * @param loginSerialNumber
	 * the loginSerialNumber to set
	 */
	public final void setLoginSerialNumber(int loginSerialNumber) {
		this.loginSerialNumber = loginSerialNumber;
	}
	
	/**
	 * @return the SIMCardICCIDNumber
	 */
	public final String getSIMCardICCIDNumber() {
		return SIMCardICCIDNumber;
	}
	
	/**
	 * @param SIMCardICCIDNumber
	 * the SIMCardICCIDNumber to set
	 */
	public final void setSIMCardICCIDNumber(String SIMCardICCIDNumber) {
		this.SIMCardICCIDNumber = SIMCardICCIDNumber;
	}
	
	/**
	 * @return the numberOfRESS
	 */
	public final short getNumberOfRESS() {
		return numberOfRESS;
	}
	
	/**
	 * @param numberOfRESS
	 * the numberOfRESS to set
	 */
	public final void setNumberOfRESS(short numberOfRESS) {
		this.numberOfRESS = numberOfRESS;
	}
	
	
	/**
	 * @return the codingLengthOfRESS
	 */
	public final byte getCodingLengthOfRESS() {
		return codingLengthOfRESS;
	}
	
	/**
	 * @param codingLengthOfRESS
	 * the codingLengthOfRESS to set
	 */
	public final void setCodingLengthOfRESS(byte codingLengthOfRESS) {
		this.codingLengthOfRESS = codingLengthOfRESS;
	}
	
	/**
	 * @return the codeForRESS
	 */
	public final String getCodeForRESS() {
		return codeForRESS;
	}
	
	/**
	 * @param codeForRESS
	 * the codeForRESS to set
	 */
	public final void setCodeForRESS(String codeForRESS) {
		this.codeForRESS = codeForRESS;
	}
	
	

}
