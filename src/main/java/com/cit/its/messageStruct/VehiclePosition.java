package com.cit.its.messageStruct;

public class VehiclePosition {
	
	private String vehicleVIN;			// 车辆VIN码
	
	private byte positionStatus;		// 定位状态
	
	private long longitude;				// 经度
	
	private long latitude;				// 纬度
	
	// 下面的两项标准里没有，但逸卡车辆状态中有
	private int speed;					// 速度 
	
	private int direction;				// 方向
	
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public void setPositionStatus(byte positionStatus) {
		this.positionStatus = positionStatus;
	}
	
	public byte getPositionStatus() {
		return positionStatus;
	}
	
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	
	public long getLongitude() {
		return longitude;
	}
	
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	
	public long getLatitude() {
		return latitude;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public String getStrSpeed() {
		return speed / 10.0 + " km/h";
	}
	
	public String getStrDirection() {
		String strDir;
		if (direction == 0 || direction == 359) {
			strDir = "正北";
		}
		else if (direction > 0 && direction < 90) {
			strDir = "西北";
		}
		else if (direction == 90) {
			strDir = "正西";
		}
		else if (direction > 90 && direction < 180) {
			strDir = "西南";
		}
		else if (direction == 180) {
			strDir = "正南";
		}
		else if (direction > 180 && direction < 270) {
			strDir = "东南";
		}
		else if (direction == 270) {
			strDir = "正东";
		}
		else if (direction > 270 && direction < 359) {
			strDir = "东北";
		}
		else {
			strDir = "方向有误";
		}
		return strDir;
	}
	
	public String getStrPositionStatus() {
		String strPositionStat;
		if (positionStatus % 2 == 0) {
			strPositionStat = "有效定位\n";	
			switch(positionStatus) {
				case 0: 
					strPositionStat = strPositionStat + "东经 : " + longitude + ", " + "北纬 : " + latitude;
					break;
				case 2:
					strPositionStat = strPositionStat + "东经 : " + longitude + ", " + "南纬 : " + latitude;
					break;
				case 4:
					strPositionStat = strPositionStat + "西经 : " + longitude + ", " + "北纬 : " + latitude;
					break;
				case 6:
					strPositionStat = strPositionStat + "西经 : " + longitude + ", " + "南纬 : " + latitude;
					break;
				default:
					strPositionStat = strPositionStat + "解析有误 ！";
					break;
			}
		}
		else {
			strPositionStat = "无效定位";
		}
		
		return strPositionStat;
	}
	// 重写toString()方法
	public String toString() {
		return "车辆VIN码 : " + vehicleVIN + "\n" + 
			   "定位状态 : " + getStrPositionStatus() + "\n" +
			   "速度 : " + getStrSpeed() + "\n" +
			   "方向 : " + getStrDirection();
	}

}
