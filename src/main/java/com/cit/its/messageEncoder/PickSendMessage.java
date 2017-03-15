package com.cit.its.messageEncoder;

import java.text.ParseException;

public class PickSendMessage {
	
	private static String vehicleVIN;
	private static int count = 0;
	
	public void setVehicleVIN(String vehicleVIN) {
		PickSendMessage.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
	
	public static String getVehicleVINByCount() {
		if (count % 13 == 0) {
			vehicleVIN = Pick.pickVehicleVIN();
		}
		count = (count + 1 ) % 13;
		return vehicleVIN;
	}
	
	public static byte[] messageBytes(int num) throws ParseException {
		byte[] result;
		// 获得车辆VIN码
		getVehicleVINByCount();
		
		result = VehicleLoginMessageEncoder.getVehicleLoginBytes(vehicleVIN);
/*		
		if (num == 1) {
			result = VehicleLoginMessageEncoder.getVehicleLoginBytes(vehicleVIN);
		}else if (num == 2) {
			result = TerminalCorrectionMessageEncoder.getTerminalCorrectionBytes(vehicleVIN);
		}else if (num == 3) {
			result = HeartBeatMessageEncoder.getHeartBeatBytes(vehicleVIN);
		}else if (num > 3 && num <= 13) {
			result = RealInfoMessageEncoder.getRealInfoBytesSerial(vehicleVIN, num - 4);
		}else {
			result = VehicleLogoutMessageEncoder.getVehicleLogoutBytes(vehicleVIN);
		}
*/		
		return result;
	}

}
