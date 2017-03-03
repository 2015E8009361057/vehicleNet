package com.cit.its.messageStruct;

public class VehicleInfo {
	
	private String vehicleVIN;			// 车辆VIN码
	
	private short vehicleState;			// 车辆状态
	
	private short chargingState;		// 充电状态
	
	private short runState;				// 运行模式
	
	private int vehicleSpeed;			// 车速
	
	private int accumulatedMileage;		// 累计里程
	
	private int totalVoltage;			// 总电压
	
	private int totalCurrent;			// 总电流
	
	private short SOC;					// SOC
	
	private short DC_DC_State;			// DC/DC状态
	
	private byte gear;					// 挡位
	
	private int insulationResistance;	// 绝缘电阻
	
	private short accelerationPedalTravelValue;			// 加速踏板行程值
	
	private short brakePedalStatus;		// 制动踏板状态
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setVehicleState(short vehicleState) {
		this.vehicleState = vehicleState;
	}
	
	public short getVehicleState() {
		return vehicleState;
	}
	
	public void setChargingState(short chargingState) {
		this.chargingState = chargingState;
	}
	
	public short getChargingState() {
		return chargingState;
	}
	
	public void setRunState(short runState) {
		this.runState = runState;
	}
	
	public short getRunState() {
		return runState;
	}
	
	public void setVehicleSpeed(int vehicleSpeed) {
		this.vehicleSpeed = vehicleSpeed;
	}
	
	public int getVehicleSpeed() {
		return vehicleSpeed;
	}
	
	public void setAccumulatedMileage(int accumulatedMileage) {
		this.accumulatedMileage = accumulatedMileage;
	}
	
	public int getAccumulatedMileage() {
		return accumulatedMileage;
	}
	
	public void setTotalVoltage(int totalVoltage) {
		this.totalVoltage = totalVoltage;
	}
	
	public int getTotalVoltage() {
		return totalVoltage;
	}
	
	public void setTotalCurrent(int totalCurrent) {
		this.totalCurrent = totalCurrent;
	}
	
	public int getTotalCurrent() {
		return totalCurrent;
	}
	
	public void setSOC(short SOC) {
		this.SOC = SOC;
	}
	
	public short getSOC() {
		return SOC;
	}
	
	public void setDC_DC_State(short DC_DC_State) {
		this.DC_DC_State = DC_DC_State;
	}
	
	public short getDC_DC_State() {
		return DC_DC_State;
	}
	
	public void setGear(byte gear) {
		this.gear = gear;
	}
	
	public byte getGear() {
		return gear;
	}
	
	public void setInsulationResistance(int insulationResistance) {
		this.insulationResistance = insulationResistance;
	}
	
	public int getInsulationResistance() {
		return insulationResistance;
	}
	
	public void setAccelerationPedalTravelValue(short accelerationPedalTravelValue) {
		this.accelerationPedalTravelValue = accelerationPedalTravelValue;
	}
	
	public short getAccelerationPedalTravelValue() {
		return accelerationPedalTravelValue;
	}
	
	public void setBrakePedalStatus(short brakePedalStatus) {
		this.brakePedalStatus = brakePedalStatus;
	}
	
	
	
	// 重写toString()方法
	public String toString() {
		return 	"车辆VIN码 : " + vehicleVIN + "\n" +
				"车辆状态 : " + vehicleState + "\n" + 
				"充电状态 : " + chargingState + "\n" + 
				"运行模式 : " + runState + "\n" + 
				"车速 : " + vehicleSpeed + "\n" +
				"累计里程 : " + accumulatedMileage + "\n" +
				"总电压 : " + totalVoltage + "\n" +
				"总电流 : " + totalCurrent + "\n" + 
				"SOC : " + SOC + "\n" +
				"DC/DC状态 : " + DC_DC_State + "\n" + 
				"挡位 : " + gear + "\n" + 
				"绝缘电阻 : " + insulationResistance + "\n" + 
				"加速踏板行程值 : " + accelerationPedalTravelValue + "\n" +
				"制动踏板状态 : " + brakePedalStatus;
	}

}
