package com.cit.its.messageStruct;

public class ExtremeValueData {
	
	private String vehicleVIN;				// 车辆VIN码
	
	private short highestVBSN;				// 最高电压电池子系统号
	
	private short highestVBMC;				// 最高电压电池单体代号
	
	private int highestCV;					// 电池单体电压最高值		
	
	private short lowestVBSN;				// 最低电压电池子系统号
	
	private short lowestVBMC;				// 最低电压电池单体代号
	
	private int lowestCV;					// 电池单体电压最低值
	
	private short highestTemperSN;			// 最高温度子系统号
	
	private short maxTemperProbNum;			// 最高温度探针序号
	
	private short highestTemperValue;		// 最高温度值
	
	private short lowestTemperSN;			// 最低温度子系统号
	
	private short minTemperProbNum;			// 最低温度探针序号
	
	private short lowestTemperValue;		// 最低温度值
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setHighestVBSN(short highestVBSN) {
		this.highestVBSN = highestVBSN;
	}
	
	public short getHighestVBSN() {
		return highestVBSN;
	}
	
	public void setHighestVBMC(short highestVBMC) {
		this.highestVBMC = highestVBMC;
	}
	
	public short getHighestVBMC() {
		return highestVBMC;
	}
	
	public void setHighestCV(int highestCV) {
		this.highestCV = highestCV;
	}
	
	public int getHighestCV() {
		return highestCV;
	}
	
	public void setLowestVBSN(short lowestVBSN) {
		this.lowestVBSN = lowestVBSN;
	}
	
	public short getLowestVBSN() {
		return lowestVBSN;
	}
	
	public void setLowestVBMC(short lowestVBMC) {
		this.lowestVBMC = lowestVBMC;
	}
	
	public short getLowestVBMC() {
		return lowestVBMC;
	}
	
	public void setLowestCV(int lowestCV) {
		this.lowestCV = lowestCV;
	}
	
	public int getLowestCV() {
		return lowestCV;
	}
	
	public void setHighestTemperSN(short highestTemperSN) {
		this.highestTemperSN = highestTemperSN;
	}
	
	public short getHighestTemperSN() {
		return highestTemperSN;
	}
	
	public void setMaxTemperProbNum(short maxTemperProbNum) {
		this.maxTemperProbNum = maxTemperProbNum;
	}
	
	public short getMaxTemperProbNum() {
		return maxTemperProbNum;
	}
	
	public void setHighestTemperValue(short highestTemperValue) {
		this.highestTemperValue = highestTemperValue;
	}
	
	public short getHighestTemperValue() {
		return highestTemperValue;
	}
	
	public void setLowestTemperSN(short lowestTemperSN) {
		this.lowestTemperSN = lowestTemperSN;
	}
	
	public short getLowestTemperSN() {
		return lowestTemperSN;
	}
	
	public void setMinTemperProbNum(short minTemperProbNum) {
		this.minTemperProbNum = minTemperProbNum;
	}
	
	public short getMinTemperProbNum() {
		return minTemperProbNum;
	}
	
	public void setLowestTemperValue(short lowestTemperValue) {
		this.lowestTemperValue = lowestTemperValue;
	}
	
	public short getLowestTemperValue() {
		return lowestTemperValue;
	}
	
	
	// 重写toString()方法
	
	public String toString() {
		return "车辆VIN码 : " + vehicleVIN + "\n" + 
			   "最高电压电池子系统号 : " + highestVBSN + "\n" + 
			   "最高电压电池单体代号 : " + highestVBMC + "\n" + 
			   "电池单体电压最高值 : " + highestCV + "\n" + 
			   "最低电压电池子系统号 : " + lowestVBSN + "\n" + 
			   "最低电压电池单体代号 : " + lowestVBMC + "\n" + 
			   "电池单体电压最低值 : " + lowestCV + "\n" + 
			   "最高温度子系统号 : " + highestTemperSN + "\n" + 
			   "最高温度探针序号 : " + maxTemperProbNum + "\n" + 
			   "最高温度值 : " + highestTemperValue + "\n" +
			   "最低温度子系统号 : " + lowestTemperSN + "\n" + 
			   "最低温度探针序号 : " + minTemperProbNum + "\n" + 
			   "最低温度值 : " + lowestTemperValue;
	}

}
