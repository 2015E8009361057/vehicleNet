package com.cit.its.messageStruct;

public class AlarmData {
	
	private String vehicleVIN;					// 车辆VIN码
	
	private short highestAlarmLevel;			// 最高报警等级
	
	private int genAlarmSigns;					// 通用报警标志
	
	private short totalNumFailOfRecharge;		// 可充电储能装置故障总数
	
	private int[] recharESDFaultCodeList;		// 可充电储能装置故障代码列表
	
	private short totalNumOfDriveMotFail;		// 驱动电机故障总数
	
	private int[] driveMotFaultCodeList;		// 驱动电机故障代码列表
	
	private short totalNumOfEngineFail;			// 发动机故障总数
	
	private int[] engineFaulCodeList;			// 发动机故障代码列表
	
	private short totalNumOfOtherFail;			// 其他故障总数
	
	private int[] otherFaultCodeList;			// 其他故障列表
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
/*	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	*/
	public void setHighestAlarmLevel(short highestAlarmLevel) {
		this.highestAlarmLevel = highestAlarmLevel;
	}
	
	public short getHighestAlarmLevel() {
		return highestAlarmLevel;
	}
	
	public void setGenAlarmSigns(int genAlarmSigns) {
		this.genAlarmSigns = genAlarmSigns;
	}
	
	public int getGenAlarmSigns() {
		return genAlarmSigns;
	}
	
	public void setTotalNumFailOfRecharge(short totalNumFailOfRecharge) {
		this.totalNumFailOfRecharge = totalNumFailOfRecharge;
	}
	
	public short getTotalNumFailOfRecharge() {
		return totalNumFailOfRecharge;
	}
	
	public void setRecharESDFaultCodeList(int[] recharESDFaultCodeList) {
		this.recharESDFaultCodeList = recharESDFaultCodeList;
	}
	
	public int[] getRecharESDFaultCodeList() {
		return recharESDFaultCodeList;
	}
	
	public void setTotalNumOfDriveMotFail(short totalNumOfDriveMotFail) {
		this.totalNumOfDriveMotFail = totalNumOfDriveMotFail;
	}
	
	public short getTotalNumOfDriveMotFail() {
		return totalNumOfDriveMotFail;
	}
	
	public void setDriveMotFaultCodeList(int[] driveMotFaultCodeList) {
		this.driveMotFaultCodeList = driveMotFaultCodeList;
	}
	
	public int[] getDriveMotFaultCodeList() {
		return driveMotFaultCodeList;
	}
	
	public void setTotalNumOfEngineFail(short totalNumOfEngineFail) {
		this.totalNumOfEngineFail = totalNumOfEngineFail;
	}
	
	public short getTotalNumOfEngineFail() {
		return totalNumOfEngineFail;
	}
	
	public void setEngineFaulCodeList(int[] engineFaulCodeList) {
		this.engineFaulCodeList = engineFaulCodeList;
	}
	
	public int[] getEngineFaulCodeList() {
		return engineFaulCodeList;
	}
	
	public void setTotalNumOfOtherFail(short totalNumOfOtherFail) {
		this.totalNumOfOtherFail = totalNumOfOtherFail;
	}
	
	public short getTotalNumOfOtherFail() {
		return totalNumOfOtherFail;
	}
	
	public void setOtherFaultCodeList(int[] otherFaultCodeList) {
		this.otherFaultCodeList = otherFaultCodeList;
	}
	
	public int[] getOtherFaultCodeList() {
		return otherFaultCodeList;
	}
	
	
	// 重写toString()方法
	public String toString() {
		System.out.println("车辆VIN码 : " + vehicleVIN + "\n" + 
						 //  "时间 : " + time + "\n" +
						   "最高报警等级 : " + highestAlarmLevel + "\n" + 
						   "通用报警标志 : " + genAlarmSigns + "\n" + 
						   "可充电储能装置故障总数 : " + totalNumFailOfRecharge);
		System.out.println("可充电储能装置故障代码列表 : ");
		for (int i = 0; i < totalNumFailOfRecharge; i++) {
			System.out.println(recharESDFaultCodeList[i]);
		}
		
		System.out.println("驱动电机故障总数 : " + totalNumOfDriveMotFail);
		System.out.println("驱动电机故障代码列表 : ");
		for (int i = 0; i < totalNumOfDriveMotFail; i++) {
			System.out.println(driveMotFaultCodeList[i]);
		}
		
		System.out.println("发动机故障总数 : " + totalNumOfEngineFail);
		System.out.println("发动机故障代码列表 : ");
		for (int i = 0; i < totalNumOfEngineFail; i++) {
			System.out.println(engineFaulCodeList[i]);
		}
		
		System.out.println("其他故障总数 : " + totalNumOfOtherFail);
		System.out.println("其他故障列表 : ");
		for (int i = 0; i < totalNumOfOtherFail; i++) {
			System.out.println(otherFaultCodeList[i]);
		}
		
		return "Alarm Data is Over !";
	}

}
