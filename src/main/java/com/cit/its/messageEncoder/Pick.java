package com.cit.its.messageEncoder;

public class Pick {
	
	private static int count = 0;
	private static int loginSerialNumber = 0;
	private static int logoutSerialNumber = 0;
	
	private static String[] vehicleVIN = {"abcdefg1234567890",
								   		  "abcdefg1234567891",
								   		  "abcdefg1234567892",
								   		  "abcdefg1234567893",
								   		  "abcdefg1234567894",
								   		  "abcdefg1234567895",
								   		  "abcdefg1234567896",
								   		  "abcdefg1234567897",
								   		  "abcdefg1234567898",
						   		   		  "abcdefg1234567899"};
	private static String[] simICCID = {"898600MFSSYYG123456P",
										"898600MFSSYYG123457P",
										"898600MFSSYYG123458P",
										"898600MFSSYYG123459P",
										"898601YYMHAAA123456P",
										"898601YYMHAAA123457P",
										"898601YYMHAAA123458P",
										"898601YYMHAAA123459P",
										"898603MYYHHH12345678",
										"898603MYYHHH12345679"};
	public static String pickVehicleVIN() {
		String veVINAndICCID = vehicleVIN[count % 10];
		count++;
		return veVINAndICCID;
	}
	
	public static String pickSimICCID(String vehicle) {
		for (int i = 0; i < vehicleVIN.length; i++) {
			if (vehicleVIN[i].equals(vehicle)) {
				return simICCID[i];
			}
		}
		return simICCID[0];
	}
	
	public static int pickLoginSerialNumber() {
		loginSerialNumber = loginSerialNumber % 65532;
		loginSerialNumber++;
		return loginSerialNumber;
	}
	
	public static int pickLogoutSerialNumber() {
		logoutSerialNumber = logoutSerialNumber % 65532;
		logoutSerialNumber++;
		return logoutSerialNumber;
	}
}
