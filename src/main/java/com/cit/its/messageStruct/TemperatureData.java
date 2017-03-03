package com.cit.its.messageStruct;

public class TemperatureData {
	
	private short rechargeESSNum;				// 可充电储能子系统号
	
	private int numOfRechargeESTempProbes;		// 可充电储能温度探针个数
	
	private short[] tempValueOfProbe;			// 可充电储能子系统各温度探针检测到的温度值
	
	
	// set, get方法
	public void setRechargeESSNum(short rechargeESSNum) {
		this.rechargeESSNum = rechargeESSNum;
	}
	
	public short getRechargeESSNum() {
		return rechargeESSNum;
	}
	
	public void setNumOfRechargeESTempProbes(int numOfRechargeESTempProbes) {
		this.numOfRechargeESTempProbes = numOfRechargeESTempProbes;
	}
	
	public int getNumOfRechargeESTempProbes() {
		return numOfRechargeESTempProbes;
	}
	
	public void setTempValueOfProbe(short[] tempValueOfProbe) {
		this.tempValueOfProbe = tempValueOfProbe;
	}
	
	public short[] getTempValueOfProbe() {
		return tempValueOfProbe;
	}
	
	
	
	// 重写toString()方法
	public String toString() {
		System.out.println("可充电储能子系统号 : " + rechargeESSNum);
		System.out.println("可充电储能温度探针个数 : " + numOfRechargeESTempProbes);
		System.out.println("可充电储能子系统各温度探针检测到的温度值 : ");
		for (int i = 0; i < numOfRechargeESTempProbes; i++) {
			System.out.println(tempValueOfProbe[i]);
		}
		return "";
	}

}
