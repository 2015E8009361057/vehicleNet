package com.cit.its.messageStruct;

public class VehicleLogin {
	// 车辆VIN码
	private String vehicleVIN;
	
	// 数据采集时间
	private String dataCollectTime;
	
	// 登入流水号
	private int loginSerialNumber;
	
	// SIM卡ICCID号
	private String SimICCID;
	
	// RESS: rechargeable Energy Storage Subsystem
	// 可充电储能子系统：当车辆存在多套可充电储能系统混合
	// 使用时，每套可充电储能系统为一个可充电储能子系统
	private short  numberOfRESS;						// 可充电储能子系统数n,有效值范围：0~250
		
	private byte codingLengthOfRESS;					// 可充电储能系统编码长度m,有效值范围：0~50，“0”表示不上传该编码
		
	private String codeForRESS;							// 可充电储能系统编码（终端从车辆获取的值）
	
	

	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setDataCollectTime(String dataCollectTime) {
		this.dataCollectTime = dataCollectTime;
	}
	
	public String getDataCollectTime() {
		return dataCollectTime;
	}
	
	public void setLoginSerialNumber(int loginSerialNumber) {
		this.loginSerialNumber = loginSerialNumber;
	}
	
	public int getLoginSerialNumber() {
		return loginSerialNumber;
	}
	
	public void setSimICCID(String SimICCID) {
		this.SimICCID = SimICCID;
	}
	
	public String getSimICCID() {
		return SimICCID;
	}
	
	public void setNumberOfRESS(short numberOfRESS) {
		this.numberOfRESS = numberOfRESS;
	}
	
	public short getNumberOfRESS() {
		return numberOfRESS;
	}
	
	public void setCodingLengthOfRESS(byte codingLengthOfRESS) {
		this.codingLengthOfRESS = codingLengthOfRESS;
	}
	
	public byte getCodingLengthOfRESS() {
		return codingLengthOfRESS;
	}
	
	public void setCodeForRESS(String codeForRESS) {
		this.codeForRESS = codeForRESS;
	}
	
	public String getCodeForRESS() {
		return codeForRESS;
	}
	
	// 重写toString()方法
	public String toString() {
		return 	"车辆VIN码 : " + vehicleVIN + "\n" + 
				"数据采集时间 : " + dataCollectTime + "\n" + 
				"登入流水号 : " + loginSerialNumber + "\n" +  
				"SIM卡ICCID号 : " + SimICCID + "\n" + 
				"可充电储能子系统数 : " + numberOfRESS + "\n" + 
				"可充电储能系统编码长度 : " + codingLengthOfRESS + "\n" +
				"可充电储能系统编码 : " + codeForRESS;
	}

}
