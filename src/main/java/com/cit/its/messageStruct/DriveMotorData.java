package com.cit.its.messageStruct;

public class DriveMotorData {
	
	private String vehicleVIN;								// 车辆VIN码
	
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
		System.out.println("车辆VIN码 : " + vehicleVIN);
		System.out.println("驱动电机个数 : " + numberOfDriveMotors);
		System.out.println("驱动电机总成信息列表 : ");
		for (int i = 0; i < numberOfDriveMotors; i++) {
			System.out.println(driMotorInfoList[i]);
		}
		return "Drive Motor Data is over !";
	}

	/*
	public static void main(String[] args) {
		DriveMotorData driveMotorData = new DriveMotorData();
		short number = (short) (Math.random() * 25 + 1);
		short[] driveMotorState = {0x01, 0x02, 0x03, 0x04, 0xFE, 0xFF};
		driveMotorData.setVehicleVIN("12345678901234567");
		driveMotorData.setNumberOfDriveMotors(number);
		DriveMotor[] driveMotorList = new DriveMotor[number];
		for (int i = 0; i < number; i++) {
			driveMotorList[i] = new DriveMotor();
		}
		for (int i = 0; i < number; i++) {
			driveMotorList[i].setDriveMotorSerialNumber((short) (i+1));
			driveMotorList[i].setDriveMotorState(driveMotorState[(int) (Math.random() * driveMotorState.length)]);
			driveMotorList[i].setDriveMotorControllerTemperature((short) (Math.random() * 250));
			driveMotorList[i].setDriveMotorSpeed((int) (Math.random() * 65531));
			driveMotorList[i].setDriveMotorTorque((int) (Math.random() * 65531));
			driveMotorList[i].setDriveMotorTemperature((short) (Math.random() * 250));
			driveMotorList[i].setMotorControllerInputVoltage((int) (Math.random() * 60000));
			driveMotorList[i].setMotorControllerDCBusCurrent((int) (Math.random() * 20000));
		}
		
		for (int i = 0; i < number; i++) {
			System.out.println("driveMotorList[" + i + "]" + driveMotorList[i]);
		}
		
		driveMotorData.setDriMotorInfoList(driveMotorList);
	
		System.out.println(driveMotorData);
		
		DriveMotor driveMotor = new DriveMotor();
		driveMotor.setDriveMotorSerialNumber((short) (1));
		driveMotor.setDriveMotorState(driveMotorState[(int) (Math.random() * driveMotorState.length)]);
		driveMotor.setDriveMotorControllerTemperature((short) (Math.random() * 250));
		driveMotor.setDriveMotorSpeed((int) (Math.random() * 65531));
		driveMotor.setDriveMotorTorque((int) (Math.random() * 65531));
		driveMotor.setDriveMotorTemperature((short) (Math.random() * 250));
		driveMotor.setMotorControllerInputVoltage((int) (Math.random() * 60000));
		driveMotor.setMotorControllerDCBusCurrent((int) (Math.random() * 20000));
		
		System.out.println(driveMotor);

	}
*/

}
