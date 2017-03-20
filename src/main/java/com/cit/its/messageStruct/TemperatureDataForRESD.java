package com.cit.its.messageStruct;


public class TemperatureDataForRESD {
	
	private String vehicleVIN;						// 车辆VIN码
	
	private short numOfRechargeESS;					// 可充电储能子系统个数
	
	// Rechargeable energy storage subsystem temperature information list
	private TemperatureData[] tempInfoListOfRESD;	// 可充电储能子系统温度信息列表
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setNumOfRechargeESS(short numOfRechargeESS) {
		this.numOfRechargeESS = numOfRechargeESS;
	}
	
	public short getNumOfRechargeESS() {
		return numOfRechargeESS;
	}
	
	public void setTempInfoListOfRESD(TemperatureData[] tempInfoListOfRESD) {
		this.tempInfoListOfRESD = tempInfoListOfRESD;
	}
	
	public TemperatureData[] getTempInfoListOfRESD() {
		return tempInfoListOfRESD;
	}
	
	
	
	// 重写toString()方法
	public String toString() {
		System.out.println("车辆VIN码 : " + vehicleVIN);
		System.out.println("可充电储能子系统个数 : " + numOfRechargeESS);
		System.out.println("可充电储能子系统温度信息列表 : ");
		for (int i = 0; i < numOfRechargeESS; i++) {
			System.out.println(tempInfoListOfRESD[i]);
		}
		
		return "可充电储能装置温度数据如上";
	}
	
	
}
