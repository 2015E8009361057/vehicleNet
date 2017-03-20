package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageStruct.AlarmData;
import com.cit.its.messageStruct.DriveMotor;
import com.cit.its.messageStruct.DriveMotorData;
import com.cit.its.messageStruct.EcarVehicleStateInfo;
import com.cit.its.messageStruct.EngineData;
import com.cit.its.messageStruct.ExtremeValueData;
import com.cit.its.messageStruct.FuelCellData;
import com.cit.its.messageStruct.RealInformationType;
import com.cit.its.messageStruct.TemperatureData;
import com.cit.its.messageStruct.TemperatureDataForRESD;
import com.cit.its.messageStruct.VehicleInfo;
import com.cit.its.messageStruct.VehiclePosition;
import com.cit.its.messageStruct.VoltageData;
import com.cit.its.messageStruct.VoltageDataForRESD;
import com.cit.its.util.ByteUtil;

public class RealInfoMessageEncoder {
	
	public static byte[] getRealInfoBytesSerial(String vehicleVIN, int num) throws ParseException {
		byte[] realInfoBytes;
		switch(num) {
			case 0:
				realInfoBytes = getVehicleInfoBytes(vehicleVIN);
				break;
			case 1:
				realInfoBytes = getDrivingMotorDataBytes(vehicleVIN);
				break;
			case 2:
				realInfoBytes = getFuelCellDataBytes(vehicleVIN);
				break;
			case 3:
				realInfoBytes = getEngineDataBytes(vehicleVIN);
				break;
			case 4:
				realInfoBytes = getVehiclePositionBytes(vehicleVIN);
				break;
			case 5:
				realInfoBytes = getExtremeValueDataBytes(vehicleVIN);
				break;
			case 6:
				realInfoBytes = getAlarmDataBytes(vehicleVIN);
				break;
			case 7:
				realInfoBytes = getVoltageDataBytes(vehicleVIN);
				break;
			case 8:
				realInfoBytes = getTemperatureDataBytes(vehicleVIN);
				break;
			case 9:
				realInfoBytes = getEcarDataBytes(vehicleVIN);
				break;
			default:
				realInfoBytes = getEcarDataBytes(vehicleVIN); 
				break;
		}
		
		// 获取首部编码
		// 数据部分长度等于 信息体长度+数据采集时间6字节
		int dataLength = realInfoBytes.length + 6;
		short commandIdentifier = (short) 0x02;
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 构造最后编码
		byte[] realBytes = new byte[headerBytes.length + dataLength + 1];
		
		// 放入首部编码
		int position = 0;
		for (int i = 0; i < headerBytes.length; i++) {
			realBytes[position] = headerBytes[i];
			position = position + 1;
		}
		// 放入数据部分的 数据采集时间
		Date dataCollectionTime = new Date();
		ByteUtil.getInstance().putDateToByteArray(dataCollectionTime, realBytes, position);
		position = position + 6;
		
		// 放入信息类型标志及信息体
		for (int i = 0; i < realInfoBytes.length; i++) {
			realBytes[position] = realInfoBytes[i];
			position = position + 1;
		}
		
		if (position == realBytes.length - 1) {
			System.out.println("实时信息编码正常");
		}
		
		// 计算并放入校验码
		byte checkCode = CheckCode.calculateCheckCode(realBytes);
		realBytes[realBytes.length - 1] = checkCode;
		
		return realBytes;
	}

	/*
	public static byte[] getRandomRealInfoBytes(String vehicleVIN) {
		// 产生随机组合种类数,最多2种一起发
		int typeNum = (int) (Math.random() * 2 + 0.5);
		int[] typeArr = new int[10];
		for (int i = 0; i < typeArr.length; i++) {
			typeArr[i] = 0;
		}
		// 随机选择发的信息种类
		if (typeNum == 10) {
			for (int i = 0; i < typeArr.length; i++) {
				typeArr[i] = 1;
			}
		}else {
			int count = 0;
			while (count < typeNum) {
				int temp = (int) (Math.random() * 10 + 0.5);
				if (temp >= 1 && typeArr[temp - 1] == 0) {
					typeArr[temp - 1] = 1;
					count++;
				}
			}
		}
		
		int length = 0;
		byte[][] realByteArr = new byte[10][];
		for (int i = 0; i < typeArr.length; i++) {
			if (typeArr[i] == 1) {
				switch(i) {
					case 0:
						realByteArr[i] = getVehicleInfoBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 1:
						realByteArr[i] = getDrivingMotorDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 2:
						realByteArr[i] = getFuelCellDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 3:
						realByteArr[i] = getEngineDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 4:
						realByteArr[i] = getVehiclePositionBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 5:
						realByteArr[i] = getExtremeValueDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 6:
						realByteArr[i] = getAlarmDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 7:
						realByteArr[i] = getVoltageDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 8:
						realByteArr[i] = getTemperatureDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
					case 9:
						realByteArr[i] = getEcarDataBytes(vehicleVIN);
						length = length + realByteArr[i].length;
						break;
				}
			}
		}
		byte[] realInfoBytes = new byte[length];
		int pos = 0;
		for (int i = 0; i < realByteArr.length; i++) {
			if (realByteArr[i] != null) {
				for (int j = 0; j < realByteArr[i].length; j++) {
					realInfoBytes[pos] = realByteArr[i][j];
					pos = pos + 1;
				}
			}
		}
		
		
		// 获取首部编码
		int dataLength = realInfoBytes.length;
		short commandIdentifier = (short) 0x80;
		byte[] headerBytes = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		// 构造最后编码
		byte[] realBytes = new byte[headerBytes.length + dataLength + 1];
		int position = 0;
		for (int i = 0; i < headerBytes.length; i++) {
			realBytes[position] = headerBytes[i];
			position = position + 1;
		}
		
		for (int i = 0; i < realInfoBytes.length; i++) {
			realBytes[position] = realInfoBytes[i];
			position = position + 1;
		}
		
		if (position == realBytes.length - 1) {
			System.out.println("实时信息编码正常");
		}
		
		// 计算并放入校验码
		byte checkCode = CheckCode.calculateCheckCode(realBytes);
		realBytes[realBytes.length - 1] = checkCode;
		
		return realBytes;
	}
*/
	public static byte[] getVehicleInfoBytes(String vehicleVIN) {
		short[] state = {0x01, 0x02, 0x03, 0x04, 0xFE, 0xFF};
		short vehicleState = state[(int) (Math.random() * state.length)];
		short chargingState = state[(int) (Math.random() * state.length)];
		short runState = state[(int) (Math.random() * state.length)];
		int vehicleSpeed = (int) (Math.random() * 2200);
		int accumuMileage = (int) (Math.random() * 10000000);
		int totalVoltage = (int) (Math.random() * 10000);
		int totalCurrent = (int) (Math.random() * 20000);
		short soc = (short) (Math.random() * 100);
		short dcState = state[(int) (Math.random() * state.length)];
		byte gear = (byte)(Math.random() * 15);
		int insulationResistance = (int) (Math.random() * 60000);
		short accelerationPedalTravelValue = (short) (Math.random() * 100);
		short brakePedalStatus = (short) (Math.random() * 100);
		// 赋值，便于打印信息
		VehicleInfo vehicleInfo = new VehicleInfo();
		vehicleInfo.setVehicleVIN(vehicleVIN);
		vehicleInfo.setVehicleState(vehicleState);
		vehicleInfo.setChargingState(chargingState);
		vehicleInfo.setRunState(runState);
		vehicleInfo.setVehicleSpeed(vehicleSpeed);
		vehicleInfo.setAccumulatedMileage(accumuMileage);
		vehicleInfo.setTotalVoltage(totalVoltage);
		vehicleInfo.setTotalCurrent(totalCurrent);
		vehicleInfo.setSOC(soc);
		vehicleInfo.setDC_DC_State(dcState);
		vehicleInfo.setGear(gear);
		vehicleInfo.setInsulationResistance(insulationResistance);
		vehicleInfo.setAccelerationPedalTravelValue(accelerationPedalTravelValue);
		vehicleInfo.setBrakePedalStatus(brakePedalStatus);
		
		// 将信息放入byte[]数组, 首先放入信息类型标志
		byte[] vehicleInfoBytes = new byte[21];
		int pos = 0;
		vehicleInfoBytes[pos] = (byte) (RealInformationType.VEHICLE_INFO.value());
		pos = pos + 1;
		vehicleInfoBytes[pos] = (byte) vehicleState;
		pos = pos + 1;
		vehicleInfoBytes[pos] = (byte) chargingState;
		pos = pos + 1;
		vehicleInfoBytes[pos] = (byte) runState;
		pos = pos + 1;
		ByteUtil.getInstance().putShort(vehicleInfoBytes, (short) vehicleSpeed, pos);
		pos = pos + 2;
		ByteUtil.getInstance().putInt(vehicleInfoBytes, vehicleSpeed, pos);
		pos = pos + 4;
		ByteUtil.getInstance().putShort(vehicleInfoBytes, (short) totalVoltage, pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(vehicleInfoBytes, (short) totalCurrent, pos);
		pos = pos + 2;
		vehicleInfoBytes[pos] = (byte) soc;
		pos = pos + 1;
		vehicleInfoBytes[pos] = (byte) dcState;
		pos = pos + 1;
		vehicleInfoBytes[pos] = gear;
		pos = pos + 1;
		ByteUtil.getInstance().putShort(vehicleInfoBytes, (short) insulationResistance, pos);
		pos = pos + 2;
		vehicleInfoBytes[pos] = (byte) accelerationPedalTravelValue;
		pos = pos + 1;
		vehicleInfoBytes[pos] = (byte) brakePedalStatus;
		pos = pos + 1;
		if (pos == vehicleInfoBytes.length) {
			System.out.println("车辆整车数据编码正常");
		}
//		System.out.println("车辆整车数据信息 : " + vehicleInfo);
		return vehicleInfoBytes;
	}
	
	public static byte[] getDrivingMotorDataBytes(String vehicleVIN) {
		// 构造数据
		DriveMotorData driveMotorData = new DriveMotorData();
		
//		short number = (short) (Math.random() * 252 + 1);
		short number = (short) (Math.random() * 25 + 1);
		short[] driveMotorState = {0x01, 0x02, 0x03, 0x04, 0xFE, 0xFF};
		driveMotorData.setVehicleVIN(vehicleVIN);
		driveMotorData.setNumberOfDriveMotors(number);
		DriveMotor[] driveMotorList = new DriveMotor[number];
		for (int i = 0; i < number; i++) {
			driveMotorList[i] = new DriveMotor();
			driveMotorList[i].setDriveMotorSerialNumber((short) (i + 1));
			driveMotorList[i].setDriveMotorState(driveMotorState[(int) (Math.random() * driveMotorState.length)]);
			driveMotorList[i].setDriveMotorControllerTemperature((short) (Math.random() * 250));
			driveMotorList[i].setDriveMotorSpeed((int) (Math.random() * 65531));
			driveMotorList[i].setDriveMotorTorque((int) (Math.random() * 65531));
			driveMotorList[i].setDriveMotorTemperature((short) (Math.random() * 250));
			driveMotorList[i].setMotorControllerInputVoltage((int) (Math.random() * 60000));
			driveMotorList[i].setMotorControllerDCBusCurrent((int) (Math.random() * 20000));
		}
		driveMotorData.setDriMotorInfoList(driveMotorList);
		// 编码
		byte[] driveMotorBytes = new byte[1 + 12 * number + 1];
		int pos = 0;
		// 放入信息类型标志
		driveMotorBytes[pos] = (byte) (RealInformationType.DRIVING_MOTOR_DATA.value());
		pos = pos + 1;
		// 放入驱动电机个数
		driveMotorBytes[pos] = (byte) number;
		pos = pos + 1;
		for (int i = 0; i < number; i++) {
			driveMotorBytes[pos] = (byte) driveMotorList[i].getDriveMotorSerialNumber();
			pos = pos + 1;
			driveMotorBytes[pos] = (byte) driveMotorList[i].getDriveMotorState();
			pos = pos + 1;
			driveMotorBytes[pos] = (byte) driveMotorList[i].getDriveMotorControllerTemperature();
			pos = pos + 1;
			ByteUtil.getInstance().putShort(driveMotorBytes, (short) driveMotorList[i].getDriveMotorSpeed(), pos);
			pos = pos + 2;
			ByteUtil.getInstance().putShort(driveMotorBytes, (short) driveMotorList[i].getDriveMotorTorque(), pos);
			pos = pos + 2;
			driveMotorBytes[pos] = (byte) driveMotorList[i].getDriveMotorTemperature();
			pos = pos + 1;
			ByteUtil.getInstance().putShort(driveMotorBytes, (short) driveMotorList[i].getMotorControllerInputVoltage(), pos);
			pos = pos + 2;
			ByteUtil.getInstance().putShort(driveMotorBytes, (short) driveMotorList[i].getMotorControllerDCBusCurrent(), pos);
			pos = pos + 2;
		}
		if (pos == driveMotorBytes.length) {
			System.out.println("驱动电机数据编码正常");
		}
//		System.out.println("驱动电机数据信息 : " + driveMotorData);
		return driveMotorBytes;
	}
	
	public static byte[] getFuelCellDataBytes(String vehicleVIN) {
		// 构造随机数据
		FuelCellData fuelCellData = new FuelCellData();
		fuelCellData.setVehicleVIN(vehicleVIN);
		fuelCellData.setFuelCellVoltage((int) (Math.random() * 20000));
		fuelCellData.setFuelCellCurrent((int) (Math.random() * 20000));
		fuelCellData.setFuelConsumptionRate((int) (Math.random() * 60000));
		int number = (int) (Math.random() * 60);
		fuelCellData.setTotalNumberOfFCTP(number);
		short[] probeTemValue = new short[number];
		for (int i = 0; i < number; i++) {
			probeTemValue[i] = (short) (Math.random() * 240);
		}
		fuelCellData.setProbeTemperatureValue(probeTemValue);
		fuelCellData.setHighestTempOfHydrogenSystem((int) (Math.random() * 2400));
		fuelCellData.setHighestTempProbeCodeOfHydSys((short) (Math.random() * 251 + 1));
		fuelCellData.setHighestConOfHydrogen((int) (Math.random() * 60000));
		fuelCellData.setHighestHyConSensorCode((short) (Math.random() * 251 + 1));
		fuelCellData.setHydrogenMaxPressure((int) (Math.random() * 1000));
		fuelCellData.setHydrogenMaxPressureSensorCode((short) (Math.random() * 251 + 1));
		short[] highVoltageDCState = {0x01, 0x02, 0xFE, 0xFF};
		fuelCellData.setHighVoltageDCState(highVoltageDCState[(int) (Math.random() * highVoltageDCState.length)]);
		// 编码
		byte[] fuelCellBytes = new byte[18 + number + 1];
		int pos = 0;
		// 放入信息类型标志
		fuelCellBytes[pos] = (byte) (RealInformationType.FUEL_CELL_DATA.value());
		pos = pos + 1;
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getFuelCellVoltage(), pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getFuelCellCurrent(), pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getFuelConsumptionRate(), pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getTotalNumberOfFCTP(), pos);
		pos = pos + 2;
		for (int i = 0; i < number; i++) {
			fuelCellBytes[pos] = (byte) probeTemValue[i];
			pos = pos + 1;
		}
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getHighestTempOfHydrogenSystem(), pos);
		pos = pos + 2;
		fuelCellBytes[pos] = (byte) fuelCellData.getHighestTempOfHydrogenSystem();
		pos = pos + 1;
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getHighestConOfHydrogen(), pos);
		pos = pos + 2;
		fuelCellBytes[pos] = (byte) fuelCellData.getHighestHyConSensorCode();
		pos = pos + 1;
		ByteUtil.getInstance().putShort(fuelCellBytes, (short) fuelCellData.getHydrogenMaxPressure(), pos);
		pos = pos + 2;
		fuelCellBytes[pos] = (byte) fuelCellData.getHydrogenMaxPressureSensorCode();
		pos = pos + 1;
		fuelCellBytes[pos] = (byte) fuelCellData.getHighVoltageDCState();
		pos = pos + 1;
		
		if (pos == fuelCellBytes.length) {
			System.out.println("燃料电池数据编码正常");
		}
		
//		System.out.println("燃料电池数据信息 : " + fuelCellData);
		
		return fuelCellBytes;
	}
	
	public static byte[] getEngineDataBytes(String vehicleVIN) {
		// 构造随机数据
		EngineData engineData = new EngineData();
		engineData.setVehicleVIN(vehicleVIN);
		short[] engineState = {0x01, 0x02, 0xFE, 0xFF};
		engineData.setEngineState(engineState[(int) (Math.random() * engineState.length)]);
		engineData.setCrankshaftSpeed((int) (Math.random() * 60000));
		engineData.setFuelConsumptionRate((int) (Math.random() * 60000));
		// 编码
		byte[] engineBytes = new byte[5 + 1];
		int pos = 0;
		// 放入信息类型标志
		engineBytes[pos] = (byte) (RealInformationType.ENGINE_DATA.value());
		pos = pos + 1;
		engineBytes[pos] = (byte) engineData.getEngineState();
		pos = pos + 1;
		ByteUtil.getInstance().putShort(engineBytes, (short) engineData.getCrankshaftSpeed(), pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(engineBytes, (short) engineData.getFuelConsumptionRate(), pos);
		pos = pos + 2;
		
		if (pos == engineBytes.length) {
			System.out.println("发动机数据编码正常");
		}
		
//		System.out.println("发动机数据信息 : " + engineData);
		
		return engineBytes;
	}
	
	public static byte[] getVehiclePositionBytes(String vehicleVIN) {
		// 构造随机数据
		VehiclePosition vehiclePosition = new VehiclePosition();
		vehiclePosition.setVehicleVIN(vehicleVIN);
		vehiclePosition.setPositionStatus((byte) (Math.random() * 7));
		vehiclePosition.setLongitude((long) (Math.random() * 360 * 1000000));
		vehiclePosition.setLatitude((long) (Math.random() * 360 * 1000000));
		// 编码
		byte[] vehiclePositionBytes = new byte[9 + 1];
		int pos = 0;
		vehiclePositionBytes[pos] = (byte) (RealInformationType.VEHICLE_POSITION.value());
		pos = pos + 1;
		vehiclePositionBytes[pos] = vehiclePosition.getPositionStatus();
		pos = pos + 1;
		ByteUtil.getInstance().putInt(vehiclePositionBytes, (int) vehiclePosition.getLongitude(), pos);
		pos = pos + 4;
		ByteUtil.getInstance().putInt(vehiclePositionBytes, (int) vehiclePosition.getLatitude(), pos);
		pos = pos + 4;
		
		if (pos == vehiclePositionBytes.length) {
			System.out.println("车辆位置数据编码正常");
		}
		
//		System.out.println("车辆位置数据信息 : " + vehiclePosition);
		
		return vehiclePositionBytes;
	}
	
	public static byte[] getExtremeValueDataBytes(String vehicleVIN) {
		// 构造随机极值数据
		ExtremeValueData extremeValueData = new ExtremeValueData();
		extremeValueData.setVehicleVIN(vehicleVIN);
		
		extremeValueData.setHighestVBSN((short) (Math.random() * 249 + 1));
		extremeValueData.setHighestVBMC((short) (Math.random() * 249 + 1));
		extremeValueData.setHighestCV((int) (Math.random() * 15000));
		
		extremeValueData.setLowestVBSN((short) (Math.random() * 249 + 1));
		extremeValueData.setLowestVBMC((short) (Math.random() * 249 + 1));
		extremeValueData.setLowestCV((int) (Math.random() * 15000));
		
		extremeValueData.setHighestTemperSN((short) (Math.random() * 249 + 1));
		extremeValueData.setMaxTemperProbNum((short) (Math.random() * 249 + 1));
		extremeValueData.setHighestTemperValue((short) (Math.random() * 100 + 100));
		
		extremeValueData.setLowestTemperSN((short) (Math.random() * 249 + 1));
		extremeValueData.setMinTemperProbNum((short) (Math.random() * 249 + 1));
		extremeValueData.setLowestTemperValue((short) (Math.random() * 100));
		// 编码
		byte[] extremeValueBytes = new byte[14 + 1];
		int pos = 0;
		// 放入信息类型标志
		extremeValueBytes[pos] = (byte) (RealInformationType.EXTREME_VALUE_DATA.value());
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getHighestVBSN();
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getHighestVBMC();
		pos = pos + 1;
		ByteUtil.getInstance().putShort(extremeValueBytes, (short) extremeValueData.getHighestCV(), pos);
		pos = pos + 2;
		
		extremeValueBytes[pos] = (byte) extremeValueData.getLowestVBSN();
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getLowestVBMC();
		pos = pos + 1;
		ByteUtil.getInstance().putShort(extremeValueBytes, (short) extremeValueData.getLowestCV(), pos);
		pos = pos + 2;
		
		extremeValueBytes[pos] = (byte) extremeValueData.getHighestTemperSN();
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getMaxTemperProbNum();
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getHighestTemperValue();
		pos = pos + 1;
		
		extremeValueBytes[pos] = (byte) extremeValueData.getLowestTemperSN();
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getMinTemperProbNum();
		pos = pos + 1;
		extremeValueBytes[pos] = (byte) extremeValueData.getLowestTemperValue();
		pos = pos + 1;
		
		if (pos == extremeValueBytes.length) {
			System.out.println("极值数据编码正常");
		}
		
//		System.out.println("极值数据信息 : " + extremeValueData);
		
		return extremeValueBytes;
		
	}
	
	public static byte[] getAlarmDataBytes(String vehicleVIN) {
		// 构造随机报警数据
		AlarmData alarmData = new AlarmData();
		alarmData.setVehicleVIN(vehicleVIN);
		short[] highestAlarmLevel = {0x01, 0x02, 0x03, 0xFE, 0xFF};
		alarmData.setHighestAlarmLevel(highestAlarmLevel[(int) (Math.random() * highestAlarmLevel.length)]);
		
		alarmData.setGenAlarmSigns((int) (Math.random() * 131072));
		
		short totalNumFailOfRecharge = (short) (Math.random() * 20);
		int[] recharESDFaultCodeList = new int[totalNumFailOfRecharge];
		for (int i = 0; i < totalNumFailOfRecharge; i++) {
			recharESDFaultCodeList[i] = i + 1;
		}
		alarmData.setTotalNumFailOfRecharge(totalNumFailOfRecharge);
		alarmData.setRecharESDFaultCodeList(recharESDFaultCodeList);
		
		short totalNumOfDriveMotFail = (short) (Math.random() * 20);
		int[] driveMotFaultCodeList = new int[totalNumOfDriveMotFail];
		for (int i = 0; i < totalNumOfDriveMotFail; i++) {
			driveMotFaultCodeList[i] = i + 1;
		}
		alarmData.setTotalNumOfDriveMotFail(totalNumOfDriveMotFail);
		alarmData.setDriveMotFaultCodeList(driveMotFaultCodeList);
		
		short totalNumOfEngineFail = (short) (Math.random() * 20);
		int[] engineFaulCodeList = new int[totalNumOfEngineFail];
		for (int i = 0; i < totalNumOfEngineFail; i++) {
			engineFaulCodeList[i] = i + 1;
		}
		alarmData.setTotalNumOfEngineFail(totalNumOfEngineFail);
		alarmData.setEngineFaulCodeList(engineFaulCodeList);
		
		short totalNumOfOtherFail = (short) (Math.random() * 20);
		int[] otherFaultCodeList = new int[totalNumOfOtherFail];
		for (int i = 0; i < totalNumOfOtherFail; i++) {
			otherFaultCodeList[i] = i + 1;
		}
		alarmData.setTotalNumOfOtherFail(totalNumOfOtherFail);
		alarmData.setOtherFaultCodeList(otherFaultCodeList);
		
		// 编码
		byte[] alarmBytes = new byte[1 + 9 + 4 * (totalNumFailOfRecharge + totalNumOfDriveMotFail + totalNumOfEngineFail + totalNumOfOtherFail)];
		int pos = 0;
		// 放入信息类型标志
		alarmBytes[pos] = (byte) (RealInformationType.ALARM_DATA.value());
		pos = pos + 1;
		alarmBytes[pos] = (byte) alarmData.getHighestAlarmLevel();
		pos = pos + 1;
		ByteUtil.getInstance().putInt(alarmBytes, alarmData.getGenAlarmSigns(), pos);
		pos = pos + 4;
		alarmBytes[pos] = (byte) alarmData.getTotalNumFailOfRecharge();
		pos = pos + 1;
		for (int i = 0; i < totalNumFailOfRecharge; i++) {
			ByteUtil.getInstance().putInt(alarmBytes, recharESDFaultCodeList[i], pos);
			pos = pos + 4;
		}
		alarmBytes[pos] = (byte) alarmData.getTotalNumOfDriveMotFail();
		pos = pos + 1;
		for (int i = 0; i < totalNumOfDriveMotFail; i++) {
			ByteUtil.getInstance().putInt(alarmBytes, driveMotFaultCodeList[i], pos);
			pos = pos + 4;
		}
		alarmBytes[pos] = (byte) alarmData.getTotalNumOfEngineFail();
		pos = pos + 1;
		for (int i = 0; i < totalNumOfEngineFail; i++) {
			ByteUtil.getInstance().putInt(alarmBytes, engineFaulCodeList[i], pos);
			pos = pos + 4;
		}
		alarmBytes[pos] = (byte) alarmData.getTotalNumOfOtherFail();
		pos = pos + 1;
		for (int i = 0; i < totalNumOfOtherFail; i++) {
			ByteUtil.getInstance().putInt(alarmBytes, otherFaultCodeList[i], pos);
			pos = pos + 4;
		}
		
		if (pos == alarmBytes.length) {
			System.out.println("报警数据编码正常");
		}
		
//		System.out.println("报警数据信息 : " + alarmData);
		
		return alarmBytes;
	}
	
	public static byte[] getVoltageDataBytes(String vehicleVIN) {
		// 构造随机可充电储能装置电压数据
		VoltageDataForRESD voltageData = new VoltageDataForRESD();
		voltageData.setVehicleVIN(vehicleVIN);
		short number = (short) (Math.random() * 24 + 1);
		voltageData.setNumOfRechargeESS(number);
		int length = 0;
		length = length + 1;
		VoltageData[] subsystemVolInfoList = new VoltageData[number];
		for (int i = 0; i < number; i++) {
			subsystemVolInfoList[i] = new VoltageData();
			subsystemVolInfoList[i].setRechargeESSNum((short)(i + 1));
			subsystemVolInfoList[i].setRechargeESDeviceVoltage((int) (Math.random() * 10000));
			subsystemVolInfoList[i].setRechargeESDeviceCurrent((int) (Math.random() * 20000));
			int num = (int) (Math.random() * 60 + 1);
			subsystemVolInfoList[i].setTotalNumOfSingleBattery(num);
			subsystemVolInfoList[i].setStartBattNumOfFrame(1);
			short m = (short) (Math.random() * 19 + 1);
			subsystemVolInfoList[i].setTotalNumOfSingleBattInFrame(m);
			int[] singleBatteryVoltage = new int[m];
			for (int j = 0; j < m; j++) {
				singleBatteryVoltage[j] = (int) (Math.random() * 60000);
			}
			subsystemVolInfoList[i].setSingleBatteryVoltage(singleBatteryVoltage);
			length = length + 10 + 2 * m;
		}
		voltageData.setSubsystemVolInfoList(subsystemVolInfoList);
		
		// 编码
		byte[] voltageBytes = new byte[length + 1];
		int pos = 0;
		// 放入信息类型标志
		voltageBytes[pos] = (byte) (RealInformationType.VOLTAGE_DATA_FOR_RESD.value());
		pos = pos + 1;
		voltageBytes[pos] = (byte) voltageData.getNumOfRechargeESS();
		pos = pos + 1;
		for (int i = 0; i < number; i++) {
			voltageBytes[pos] = (byte) voltageData.getSubsystemVolInfoList()[i].getRechargeESSNum();
			pos = pos + 1;
			ByteUtil.getInstance().putShort(voltageBytes, (short) voltageData.getSubsystemVolInfoList()[i].getRechargeESDeviceVoltage(), pos);
			pos = pos + 2;
			ByteUtil.getInstance().putShort(voltageBytes, (short) voltageData.getSubsystemVolInfoList()[i].getRechargeESDeviceCurrent(), pos);
			pos = pos + 2;
			ByteUtil.getInstance().putShort(voltageBytes, (short) voltageData.getSubsystemVolInfoList()[i].getTotalNumOfSingleBattery(), pos);
			pos = pos + 2;
			ByteUtil.getInstance().putShort(voltageBytes, (short) voltageData.getSubsystemVolInfoList()[i].getStartBattNumOfFrame(), pos);
			pos = pos + 2;
			short m = voltageData.getSubsystemVolInfoList()[i].getTotalNumOfSingleBattInFrame();
			voltageBytes[pos] = (byte) m;
			pos = pos + 1;
			int[] singleBatteryVoltage = voltageData.getSubsystemVolInfoList()[i].getSingleBatteryVoltage();
			for (int j = 0; j < m; j++) {
				ByteUtil.getInstance().putShort(voltageBytes, (short)singleBatteryVoltage[j], pos);
				pos = pos + 2;
			}
		}
		
		if (pos == voltageBytes.length) {
			System.out.println("可充电储能装置电压数据编码正常");
		}
		
//		System.out.println("可充电储能装置电压数据 : " + voltageData);
		
		return voltageBytes;
	}
	
	public static byte[] getTemperatureDataBytes(String vehicleVIN) {
		// 构造随机可充电储能装置温度数据
		TemperatureDataForRESD temperData = new TemperatureDataForRESD();
		temperData.setVehicleVIN(vehicleVIN);
		
		short number = (short) (Math.random() * 24 + 1);
		temperData.setNumOfRechargeESS(number);
		
		// 记录该随机数据的长度
		int length = 0;
		length = length + 1;
		
		TemperatureData[] tempInfoListOfRESD = new TemperatureData[number];
		// 赋值
		for (int i = 0; i < number; i++) {
			
			tempInfoListOfRESD[i] = new TemperatureData();
			
			tempInfoListOfRESD[i].setRechargeESSNum((short) (i + 1));
			
			int n = (int) (Math.random() * 60 + 1);
			tempInfoListOfRESD[i].setNumOfRechargeESTempProbes(n);
			
			short[] tempValueOfProbe = new short[n];
			for (int j = 0; j < n; j++) {
				tempValueOfProbe[j] = (short) (Math.random() * 250);
			}
			
			tempInfoListOfRESD[i].setTempValueOfProbe(tempValueOfProbe);
			length = length + 3 + n;
		}
		temperData.setTempInfoListOfRESD(tempInfoListOfRESD);
		
		// 编码
		byte[] tempBytes = new byte[length + 1];
		int pos = 0;
		// 放入信息类型标志
		tempBytes[pos] = (byte) (RealInformationType.TEMPERATURE_DATA_FOR_RESD.value());
		pos = pos + 1;
		
		tempBytes[pos] = (byte) number;
		pos = pos + 1;
		
		for (int i = 0; i < number; i++) {
			tempBytes[pos] = (byte) (tempInfoListOfRESD[i].getRechargeESSNum());
			pos = pos + 1;
			
			int n = tempInfoListOfRESD[i].getNumOfRechargeESTempProbes();
			ByteUtil.getInstance().putShort(tempBytes, (short) n, pos);
			pos = pos + 2;
			
			short[] tempValueOfProbe = tempInfoListOfRESD[i].getTempValueOfProbe();
			for (int j = 0; j < n; j++) {
				tempBytes[pos] = (byte) tempValueOfProbe[j];
				pos = pos + 1;
			}
		}
		
		if (pos == tempBytes.length) {
			System.out.println("可充电储能子系统温度数据编码正常");
		}
		
//		System.out.println("可充电储能子系统温度数据信息 : " + temperData);
		
		return tempBytes;
	}
	
	public static byte[] getEcarDataBytes(String vehicleVIN) {
		// 构造随机数据
		EcarVehicleStateInfo eCarInfo = new EcarVehicleStateInfo();
		eCarInfo.setVehicleVIN(vehicleVIN);
		byte[] arr = {1, 4, 16, 64};
		eCarInfo.setGearOfPRND(arr[(int) (Math.random() * arr.length)]);
		eCarInfo.setGearOfL123(arr[(int) (Math.random() * arr.length)]);
		eCarInfo.setGearOfL123_Reserved((byte) 0);
		byte[] DCU_State = {1, 2, 3, 4};
		eCarInfo.setDCU_State(DCU_State[(int) (Math.random() * DCU_State.length)]);
		byte[] speed = {0, 1, 2};
		eCarInfo.setSpeed_Limit_Command(speed[(int) (Math.random() * speed.length)]);
		byte[] dcuMode = {0, 1};
		eCarInfo.setDCU_Running_Mode(dcuMode[(int) (Math.random() * dcuMode.length)]);
		
		eCarInfo.setMain_Circuit_State(speed[(int) (Math.random() * speed.length)]);
		eCarInfo.setCool_Liquid_State(dcuMode[(int) (Math.random() * dcuMode.length)]);
		eCarInfo.setVoice_Police_State(dcuMode[(int) (Math.random() * dcuMode.length)]);
		eCarInfo.setCooling_Water_Pump(dcuMode[(int) (Math.random() * dcuMode.length)]);
		
		eCarInfo.setSecurity_Switch(dcuMode[(int) (Math.random() * dcuMode.length)]);
		eCarInfo.setLeakage_Elec_Info(dcuMode[(int) (Math.random() * dcuMode.length)]);
		byte[] coolingFan = {0, 1, 2, 3};
		eCarInfo.setCooling_Fan(coolingFan[(int) (Math.random() * coolingFan.length)]);
		eCarInfo.setDoor_Enable_Open_State(dcuMode[(int) (Math.random() * dcuMode.length)]);
		
		eCarInfo.setBattery_Voltage((float) (Math.random() * 25 + 10));
		eCarInfo.setSingle_Mileage((float) (Math.random() * 600));
		eCarInfo.setAverage_Battery_Imbalance((float) (Math.random() * 250));
		
		// Charging State
		eCarInfo.setCharging_Mode(DCU_State[(int) Math.random() * DCU_State.length]);
		eCarInfo.setCharger_Type(dcuMode[(int) (Math.random() * dcuMode.length)]);
		eCarInfo.setCharging_State(dcuMode[(int) (Math.random() * dcuMode.length)]);
		
		eCarInfo.setCharging_Process(speed[(int) (Math.random() * speed.length)]);
		eCarInfo.setCharging_Connection(dcuMode[(int) (Math.random() * dcuMode.length)]);
		eCarInfo.setCharging_Reserved((byte) 0);
		
		eCarInfo.setCharging_Amount((short) (Math.random() * 1000));
		eCarInfo.setCharging_Time((short) (Math.random() * 6000));
		
		eCarInfo.setSpeed((int) (Math.random() * 2200));
		eCarInfo.setDirection((int) (Math.random() * 359));
		
		// 这属于自定义数据，格式为 自定义数据长度 2字节+ 自定义数据18字节 
		byte[] eCarBytes = new byte[20 + 1];
		int length = 18;
		int pos = 0;
		// 放入信息类型标志
		eCarBytes[pos] = (byte) (RealInformationType.ECAR_VEHICLE_STATE_INFO.value());
		pos = pos + 1;
		ByteUtil.getInstance().putShort(eCarBytes, (short) length, pos);
		pos = pos + 2;
		
		eCarBytes[pos] = eCarInfo.getGearOfPRND();
		pos = pos + 1;
		byte gearL123 = (byte) (eCarInfo.getGearOfL123() ^ eCarInfo.getGearOfL123_Reserved());
		eCarBytes[pos] = gearL123;
		pos = pos + 1;
		byte dcu = (byte) (eCarInfo.getDCU_State() ^ (eCarInfo.getSpeed_Limit_Command() << 4) ^ (eCarInfo.getDCU_Running_Mode() << 6));
		eCarBytes[pos] = dcu;
		pos = pos + 1;
		byte state = (byte) (eCarInfo.getMain_Circuit_State() ^ (eCarInfo.getCool_Liquid_State() << 2) ^ (eCarInfo.getVoice_Police_State() << 4) ^ (eCarInfo.getCooling_Water_Pump() << 6));
		eCarBytes[pos] = state;
		pos = pos + 1;
		
		byte slcd = (byte) (eCarInfo.getSecurity_Switch() ^ (eCarInfo.getLeakage_Elec_Info() << 2) ^ (eCarInfo.getCooling_Fan() << 4) ^ (eCarInfo.getDoor_Enable_Open_State() << 6));
		eCarBytes[pos] = slcd;
		pos = pos + 1;
		
		eCarBytes[pos] = (byte) eCarInfo.getBattery_Voltage();
		pos = pos + 1;
		eCarBytes[pos] = (byte) eCarInfo.getSingle_Mileage();
		pos = pos + 1;
		eCarBytes[pos] = (byte) eCarInfo.getAverage_Battery_Imbalance();
		pos = pos + 1;
		
		byte chargingState = (byte) (eCarInfo.getCharging_Mode() ^ (eCarInfo.getCharger_Type() << 4) ^ (eCarInfo.getCharging_State() << 6));
		eCarBytes[pos] = chargingState;
		pos = pos + 1;
		
		byte chargingProcess = (byte) (eCarInfo.getCharging_Process() ^ (eCarInfo.getCharging_Connection() << 2) ^ (eCarInfo.getCharging_Reserved() << 4));
		eCarBytes[pos] = chargingProcess;
		pos = pos + 1;
		
		ByteUtil.getInstance().putShort(eCarBytes, eCarInfo.getCharging_Amount(), pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(eCarBytes, eCarInfo.getCharging_Time(), pos);
		pos = pos + 2;
		
		ByteUtil.getInstance().putShort(eCarBytes, (short) eCarInfo.getSpeed(), pos);
		pos = pos + 2;
		ByteUtil.getInstance().putShort(eCarBytes, (short) eCarInfo.getDirection(), pos);
		pos = pos + 2;
		
		if (pos == eCarBytes.length) {
			System.out.println("逸卡车辆数据编码正常");
		}
		
//		System.out.println("逸卡车辆数据信息 : " + eCarInfo);
		
		return eCarBytes;
	}

}
