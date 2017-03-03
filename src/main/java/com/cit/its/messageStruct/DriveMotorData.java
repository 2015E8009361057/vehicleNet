package com.cit.its.messageStruct;

public class DriveMotorData {
	
	private String vehicleVIN;								// 车辆VIN码
	
	private String time;									// 时间
	
	private short numberOfDriveMotors;						// 驱动电机个数
	
	// Drive the motor assembly information list
	// 驱动电机总成信息列表
	// 存疑，是否使用list效率更佳？
	private DriveMotor[] driMotorInfoList;
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setNumberOfDriveMotors(short numberOfDriveMotors) {
		this.numberOfDriveMotors = numberOfDriveMotors;
	}
	
	public short getNumberOfDriveMotors() {
		return numberOfDriveMotors;
	}
	
	public void setDriMotorInfoList(DriveMotor[] driMotorInfoList) {
		this.driMotorInfoList = driMotorInfoList;
	}
	
	public DriveMotor[] getDriMotorInfoList() {
		return driMotorInfoList;
	}
	
	// 重写toString()方法
	public String toString() {
		System.out.println("车辆VIN码 : " + vehicleVIN + "\n" + "时间 : " + time);
		System.out.println("驱动电机个数 : " + numberOfDriveMotors);
		System.out.println("驱动电机总成信息列表 : ");
		for (int i = 0; i < numberOfDriveMotors; i++) {
			System.out.println(driMotorInfoList[i]);
		}
		return "Drive Motor Data is over !";
	}

}
