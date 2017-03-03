package com.cit.its.messageStruct;

public class EngineData {
	
	private String vehicleVIN;			// 车辆VIN码
	
	private short engineState;			// 发动机状态
	
	private int crankshaftSpeed;		// 曲轴转速
	
	private int fuelConsumptionRate;	// 燃料消耗率
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setEngineState(short engineState) {
		this.engineState = engineState;
	}
	
	public short getEngineState() {
		return engineState;
	}
	
	public void setCrankshaftSpeed(int crankshaftSpeed) {
		this.crankshaftSpeed = crankshaftSpeed;
	}
	
	public int getCrankshaftSpeed() {
		return crankshaftSpeed;
	}
	
	public void setFuelConsumptionRate(int fuelConsumptionRate) {
		this.fuelConsumptionRate = fuelConsumptionRate;
	}
	
	public int getFuelConsumptionRate() {
		return fuelConsumptionRate;
	}
	
	
	// 重写toString()方法
	public String toString() {
		return "车辆VIN码 : " + vehicleVIN + "\n" + 
			   "发动机状态 : " + engineState + "\n" +
			   "曲轴转速 : " + crankshaftSpeed + "\n" +
			   "燃料消耗率 : " + fuelConsumptionRate;
	}

}
