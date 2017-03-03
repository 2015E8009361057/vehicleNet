package com.cit.its.messageStruct;

public class VehicleLogout {
	
	private String vehicleVIN;			// 车辆VIN码
	
	private String logoutTime;			// 登出时间
	
	private int logoutSerialNumber;		// 登出流水号
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	
	public String getLogoutTime() {
		return logoutTime;
	}
	
	public void setLogoutSerialNumber(int logoutSerialNumber) {
		this.logoutSerialNumber = logoutSerialNumber;
	}
	
	public int getLogoutSerialNumber() {
		return logoutSerialNumber;
	}
	
	
	// 重写toString()方法
	public String toString() {
		return "车辆VIN码 : " + vehicleVIN + "\n" + 
			   "登出时间 : " + logoutTime + "\n" + 
			   "登出流水号 : " + logoutSerialNumber;
	}

}
