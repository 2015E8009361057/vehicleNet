package com.cit.its.messageStruct;

public class FuelCellData {
	
	private String vehicleVIN;				// 车辆VIN码
	
	private int fuelCellVoltage;			// 燃料电池电压
	
	private int fuelCellCurrent;			// 燃料电池电流
	
	private int fuelConsumptionRate;		// 燃料消耗率
	
	private int totalNumberOfFCTP;			// 燃料电池温度探针总数
	
	private short[] probeTemperatureValue;			// 探针温度值
	
	private int highestTempOfHydrogenSystem;		// 氢系统中最高温度
	
	private short highestTempProbeCodeOfHydSys;		// 氢系统中最高温度探针代号
	
	private int highestConOfHydrogen;				// 氢气最高浓度
	
	private short highestHyConSensorCode;			// 氢气最高浓度传感器代号
	
	private int hydrogenMaxPressure;				// 氢气最高压力
	
	private short hydrogenMaxPressureSensorCode;	// 氢气最高压力传感器代号
	
	private short highVoltageDCState;				// 高压DC/DC状态
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setFuelCellVoltage(int fuelCellVoltage) {
		this.fuelCellVoltage = fuelCellVoltage;
	}
	
	public int getFuelCellVoltage() {
		return fuelCellVoltage;
	}
	
	public void setFuelCellCurrent(int fuelCellCurrent) {
		this.fuelCellCurrent = fuelCellCurrent;
	}
	
	public int getFuelCellCurrent() {
		return fuelCellCurrent;
	}
	
	public void setFuelConsumptionRate(int fuelConsumptionRate) {
		this.fuelConsumptionRate = fuelConsumptionRate;
	}
	
	public int getFuelConsumptionRate() {
		return fuelConsumptionRate;
	}
	
	public void setTotalNumberOfFCTP(int totalNumberOfFCTP) {
		this.totalNumberOfFCTP = totalNumberOfFCTP;
	}
	
	public int getTotalNumberOfFCTP() {
		return totalNumberOfFCTP;
	}
	
	public void setProbeTemperatureValue(short[] probeTemperatureValue) {
		this.probeTemperatureValue = probeTemperatureValue;
	}
	
	public short[] getProbeTemperatureValue() {
		return probeTemperatureValue;
	}
	
	public void setHighestTempOfHydrogenSystem(int highestTempOfHydrogenSystem) {
		this.highestTempOfHydrogenSystem = highestTempOfHydrogenSystem;
	}
	
	public int getHighestTempOfHydrogenSystem() {
		return highestTempOfHydrogenSystem;
	}
	
	public void setHighestTempProbeCodeOfHydSys(short highestTempProbeCodeOfHydSys) {
		this.highestTempProbeCodeOfHydSys = highestTempProbeCodeOfHydSys;
	}
	
	public short getHighestTempProbeCodeOfHydSys() {
		return highestTempProbeCodeOfHydSys;
	}
	
	public void setHighestConOfHydrogen(int highestConOfHydrogen) {
		this.highestConOfHydrogen = highestConOfHydrogen;
	}
	
	public int getHighestConOfHydrogen() {
		return highestConOfHydrogen;
	}
	
	public void setHighestHyConSensorCode(short highestHyConSensorCode) {
		this.highestHyConSensorCode = highestHyConSensorCode;
	}
	
	public short getHighestHyConSensorCode() {
		return highestHyConSensorCode;
	}
	
	public void setHydrogenMaxPressure(int hydrogenMaxPressure) {
		this.hydrogenMaxPressure = hydrogenMaxPressure;
	}
	
	public int getHydrogenMaxPressure() {
		return hydrogenMaxPressure;
	}
	
	public void setHydrogenMaxPressureSensorCode(short hydrogenMaxPressureSensorCode) {
		this.hydrogenMaxPressureSensorCode = hydrogenMaxPressureSensorCode;
	}
	
	public short getHydrogenMaxPressureSensorCode() {
		return hydrogenMaxPressureSensorCode;
	}
	
	public void setHighVoltageDCState(short highVoltageDCState) {
		this.highVoltageDCState = highVoltageDCState;
	}
	
	public short getHighVoltageDCState() {
		return highVoltageDCState;
	}
	
	
	// 重写toString()方法
	public String toString() {
		System.out.println("车辆VIN码 : " + vehicleVIN + "\n" + 
						   "燃料电池电压 : " + fuelCellVoltage + "\n" +
						   "燃料电池电流 : " + fuelCellCurrent + "\n" + 
						   "燃料消耗率 : " + fuelConsumptionRate + "\n" +
						   "燃料电池温度探针总数 : " + totalNumberOfFCTP);
		System.out.println("探针温度值 : ");
		for (int i = 0; i < totalNumberOfFCTP; i++) {
			System.out.println(probeTemperatureValue[i]);
		}
		
		System.out.println("氢系统中最高温度 : " + highestTempOfHydrogenSystem + "\n" +
						   "氢系统中最高温度探针代号 : " + highestTempProbeCodeOfHydSys + "\n" +
						   "氢气最高浓度 : " + highestConOfHydrogen + "\n" + 
						   "氢气最高浓度传感器代号 : " + highestHyConSensorCode + "\n" + 
						   "氢气最高压力 : " + hydrogenMaxPressure + "\n" + 
						   "氢气最高压力传感器代号 : " + hydrogenMaxPressureSensorCode + "\n" +
						   "高压DC/DC状态 : " + highVoltageDCState);
		return "Fuel Cell Data is over !";
	}

}
