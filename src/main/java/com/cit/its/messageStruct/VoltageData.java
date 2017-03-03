package com.cit.its.messageStruct;

public class VoltageData {
	
	private short rechargeESSNum;						// 可充电储能子系统号
	
	private int rechargeESDeviceVoltage;				// 可充电储能装置电压
	
	private int rechargeESDeviceCurrent;				// 可充电储能装置电流
	
	private int totalNumOfSingleBattery;				// 单体电池总数
	
	private int startBattNumOfFrame;					// 本帧起始电池序号
	
	private short totalNumOfSingleBattInFrame;			// 本帧单体电池总数
	
	private int[] singleBatteryVoltage;					// 单体电池电压
	
	
	// set, get方法
	public void setRechargeESSNum(short rechargeESSNum) {
		this.rechargeESSNum = rechargeESSNum;
	}
	
	public short getRechargeESSNum() {
		return rechargeESSNum;
	}
	
	public void setRechargeESDeviceVoltage(int rechargeESDeviceVoltage) {
		this.rechargeESDeviceVoltage = rechargeESDeviceVoltage;
	}
	
	public int getRechargeESDeviceVoltage() {
		return rechargeESDeviceVoltage;
	}
	
	public void setRechargeESDeviceCurrent(int rechargeESDeviceCurrent) {
		this.rechargeESDeviceCurrent = rechargeESDeviceCurrent;
	}
	
	public int getRechargeESDeviceCurrent() {
		return rechargeESDeviceCurrent;
	}
	
	public void setTotalNumOfSingleBattery(int totalNumOfSingleBattery) {
		this.totalNumOfSingleBattery = totalNumOfSingleBattery;
	}
	
	public int getTotalNumOfSingleBattery() {
		return totalNumOfSingleBattery;
	}
	
	public void setStartBattNumOfFrame(int startBattNumOfFrame) {
		this.startBattNumOfFrame = startBattNumOfFrame;
	}
	
	public int getStartBattNumOfFrame() {
		return startBattNumOfFrame;
	}
	
	public void setTotalNumOfSingleBattInFrame(short totalNumOfSingleBattInFrame) {
		this.totalNumOfSingleBattInFrame = totalNumOfSingleBattInFrame;
	}
	
	public short getTotalNumOfSingleBattInFrame() {
		return totalNumOfSingleBattInFrame;
	}
	
	public void setSingleBatteryVoltage(int[] singleBatteryVoltage) {
		this.singleBatteryVoltage = singleBatteryVoltage;
	}
	
	public int[] getSingleBatteryVoltage() {
		return singleBatteryVoltage;
	}
	
	
	// 重写toString()方法
	public String toString() {
		System.out.println("可充电储能子系统号 : " + rechargeESSNum + "\n" + 
						   "可充电储能装置电压 : " + rechargeESDeviceVoltage + "\n" + 
						   "可充电储能装置电流 : " + rechargeESDeviceCurrent + "\n" + 
						   "单体电池总数 : " + totalNumOfSingleBattery + "\n" + 
						   "本帧起始电池序号 : " + startBattNumOfFrame + "\n" +
						   "本帧单体电池总数 : " + totalNumOfSingleBattInFrame + "\n" +
						   "单体电池电压 : " + "\n");
		for (int i = 0; i < totalNumOfSingleBattInFrame; i++) {
			System.out.println(singleBatteryVoltage[i]);
		}
		return "";
	}

}
