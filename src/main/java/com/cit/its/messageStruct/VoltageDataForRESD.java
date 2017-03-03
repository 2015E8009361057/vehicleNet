package com.cit.its.messageStruct;

public class VoltageDataForRESD {
	
	private String vehicleVIN;						// 车辆VIN码
	
	private short numOfRechargeESS;					// 可充电储能子系统个数
	
	private VoltageData[] subsystemVolInfoList;		// 可充电储能子系统电压信息列表
	
	
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
	
	public void setSubsystemVolInfoList(VoltageData[] subsystemVolInfoList) {
		this.subsystemVolInfoList = subsystemVolInfoList;
	}
	
	public VoltageData[] getSubsystemVolInfoList() {
		return subsystemVolInfoList;
	}
	
	
	
	// 重写toString()方法
	public String toString() {
		System.out.println("车辆VIN码 : " + vehicleVIN);
		System.out.println("可充电储能子系统个数 : " + numOfRechargeESS);
		System.out.println("可充电储能子系统电压信息列表 :");
		for (int i = 0; i < numOfRechargeESS; i++) {
			System.out.println(subsystemVolInfoList[i]);
		}
		return "";
	}

}
