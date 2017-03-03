package com.cit.its.messageStruct;

public class DriveMotor {
	
	private short driveMotorSerialNumber;				// 驱动电机序号
	
	private short driveMotorState;						// 驱动电机状态
	
	private short driveMotorControllerTemperature;		// 驱动电机控制器温度
	
	private int driveMotorSpeed;						// 驱动电机转速
	
	private int driveMotorTorque;						// 驱动电机转矩
	
	private short driveMotorTemperature;				// 驱动电机温度
	
	private int motorControllerInputVoltage;			// 电机控制器输入电压
	
	private int motorControllerDCBusCurrent;			// 电机控制器直流母线电流
	
	
	// set, get方法
	public void setDriveMotorSerialNumber(short driveMotorSerialNumber) {
		this.driveMotorSerialNumber = driveMotorSerialNumber;
	} 
	
	public short getDriveMotorSerialNumber() {
		return driveMotorSerialNumber;
	}
	
	public void setDriveMotorState(short driveMotorState) {
		this.driveMotorState = driveMotorState;
	}
	
	public short getDriveMotorState() {
		return driveMotorState;
	}
	
	public void setDriveMotorControllerTemperature(short driveMotorControllerTemperature) {
		this.driveMotorControllerTemperature = driveMotorControllerTemperature;
	}
	
	public short getDriveMotorControllerTemperature() {
		return driveMotorControllerTemperature;
	}
	
	public void setDriveMotorSpeed(int driveMotorSpeed) {
		this.driveMotorSpeed = driveMotorSpeed;
	}
	
	public int getDriveMotorSpeed() {
		return driveMotorSpeed;
	}
	
	public void setDriveMotorTorque(int driveMotorTorque) {
		this.driveMotorTorque = driveMotorTorque;
	}
	
	public int getDriveMotorTorque() {
		return driveMotorTorque;
	}
	
	public void setDriveMotorTemperature(short driveMotorTemperature) {
		this.driveMotorTemperature = driveMotorTemperature;
	}
	
	public short getDriveMotorTemperature() {
		return driveMotorTemperature;
	}
	
	public void setMotorControllerInputVoltage(int motorControllerInputVoltage) {
		this.motorControllerInputVoltage = motorControllerInputVoltage;
	}
	
	public int getMotorControllerInputVoltage() {
		return motorControllerInputVoltage;
	}
	
	public void setMotorControllerDCBusCurrent(int motorControllerDCBusCurrent) {
		this.motorControllerDCBusCurrent = motorControllerDCBusCurrent;
	}
	
	public int getMotorControllerDCBusCurrent() {
		return motorControllerDCBusCurrent;
	}
	

	// 重写toString()方法
	public String toString() {
		return "驱动电机序号 : " + driveMotorSerialNumber + "\n" +
			   "驱动电机状态 : " + driveMotorState + "\n" +
			   "驱动电机控制器温度 : " + driveMotorControllerTemperature + "\n" +
			   "驱动电机转速 : " + driveMotorSpeed + "\n" +
			   "驱动电机转矩 : " + driveMotorTorque + "\n" + 
			   "驱动电机温度 : " + driveMotorTemperature + "\n" +
			   "电机控制器输入电压 : " + motorControllerInputVoltage + "\n" +
			   "电机控制器直流母线电流 : " + motorControllerDCBusCurrent;
			   
	}

}
