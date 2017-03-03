package com.cit.its.messageDecoder;

import com.cit.its.messageStruct.AlarmData;
import com.cit.its.messageStruct.DriveMotor;
import com.cit.its.messageStruct.DriveMotorData;
import com.cit.its.messageStruct.EcarVehicleStateInfo;
import com.cit.its.messageStruct.EngineData;
import com.cit.its.messageStruct.ExtremeValueData;
import com.cit.its.messageStruct.FuelCellData;
import com.cit.its.messageStruct.TemperatureData;
import com.cit.its.messageStruct.TemperatureDataForRESD;
import com.cit.its.messageStruct.VehicleInfo;
import com.cit.its.messageStruct.VehiclePosition;
import com.cit.its.messageStruct.VoltageData;
import com.cit.its.messageStruct.VoltageDataForRESD;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.Unsigned;

public class RealInfoDecoder {
		
	public static int vehicleInfoHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 20;
		
		VehicleInfo vehicleInfo = new VehicleInfo();
		
		// 车辆状态：0x01：车辆启动状态；0x02：熄火；0x03：其他状态；0xFE：异常；0xFF：无效
		short vehicleState = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 充点状态：0x01：停车充点；0x02：行驶充电；0x03：未充电状态；0x04：充电完成；0xFE：异常；0xFF：无效
		short chargingState = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
				
		// 运行模式：0x01：纯电；0x02：混动；0x03：燃油；0xFE：异常；0xFF：无效
		short runState = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 车速：有效值范围：0~2200（表示0km/h~220km/h），最小计量单元0.1km/h，0xFF，0xFE：异常；0xFF，0xFF：无效
		int vehicleSpeed = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 累积里程：有效值范围：0~9999999(表示0km~999999.9km)，最小计量单元：0.1km
		// 0xFF,0xFF,0xFF,0xFE：异常；0xFF,0xFF,0xFF,0xFF：无效
		int accumulatedMileage = ByteUtil.getInt(bytes, pos);
		pos = pos + 4;
		
		// 总电压：有效值范围：0~10000(表示0V~10000V)，最小计量单元：0.1V，0xFF,0xFE：异常；0xFF,0xFF：无效
		int totalVoltage = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 总电流：有效值范围：0~20000(偏移量1000A，表示-1000A ~ +1000A)，最小计量单元：0.1A
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		int totalCurrent = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// SOC：有效值范围：0~100(表示0% ~ 100%),最小计量单元：1%，0xFE：异常；0xFF：无效
		short SOC = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// DC/DC状态：0x01：工作；0x02：断开；0xFE：异常；0xFF：无效
		short DC_DC_State = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 挡位：bit7：预留；bit6：预留；bit5：1：有驱动力，0：无驱动力；bit4：1：有制动力，0：无制动力
		// bit3，bit2，bit1，bit0：0000：空挡；0001：1挡；0010：2挡；0011：3挡；0100：4挡
		// 0101：5挡；0110：6挡；...... 1101：倒挡；1110：自动D挡；1111：停车P挡
		byte gear = bytes[pos];
		pos = pos + 1;
		
		// 绝缘电阻：有效值范围：0~60000(表示0千瓯 ~ 60000千瓯)，最小计量单元：1千瓯
		int insulationResistance = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 加速踏板行程值：有效值范围：0~100(表示0% ~ 100%)，最小计量单元：1%，0xFE：异常；0xFF：无效
		short accelerationPedalTravelValue = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 制动踏板状态：有效值范围：0~100(表示0% ~ 100%)，最小计量单元：1%，“0”表示制动关；在无具体行程值的情况下，0x65表示制动有效；
		// 0xFE：异常；0xFF：无效
		short brakePedalStatus = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		vehicleInfo.setVehicleVIN(vehicleVIN);
		vehicleInfo.setVehicleState(vehicleState);
		vehicleInfo.setChargingState(chargingState);
		vehicleInfo.setRunState(runState);
		vehicleInfo.setVehicleSpeed(vehicleSpeed);
		vehicleInfo.setAccumulatedMileage(accumulatedMileage);
		vehicleInfo.setTotalVoltage(totalVoltage);
		vehicleInfo.setTotalCurrent(totalCurrent);
		vehicleInfo.setSOC(SOC);
		vehicleInfo.setDC_DC_State(DC_DC_State);
		vehicleInfo.setGear(gear);
		vehicleInfo.setInsulationResistance(insulationResistance);
		vehicleInfo.setAccelerationPedalTravelValue(accelerationPedalTravelValue);
		vehicleInfo.setBrakePedalStatus(brakePedalStatus);
		
		System.out.println(vehicleInfo);
		
		// 更新数据库
		
		return length;
	}


	public static int drivingMotorDataHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 0;
		
		DriveMotorData driveMotData = new DriveMotorData();
		
		// 驱动电机个数
		short numberOfDriveMotors = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		DriveMotor[] driveMotList = new DriveMotor[numberOfDriveMotors];
		
		length = length + 1;
		for (int i = 0; i < numberOfDriveMotors; i++) {
			// 驱动电机序号：有效值范围1~253
			short driveMotorSerialNumber = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 驱动电机状态：0x01：耗电；0x02：发电；0x03：关闭状态；0x04：准备状态；0xFE：异常；0xFF：无效
			short driveMotorState = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 驱动电机控制器温度：有效值范围：0~250(数值偏移量40度，表示-40度 ~ +210度)，最小计量单元：1度，0xFE：异常；0xFF：无效
			short driveMotorControllerTemperature = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 驱动电机转速：有效值范围：0 ~ 65531(数值偏移量20000表示-20000 r/min ~ 45531 r/min)
			// 最小计量单元：1 r/min，0xFF,0xFE：异常；0xFF,0xFF：无效
			int driveMotorSpeed = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 驱动电机转矩：有效值范围：0 ~ 65531(数值偏移量20000表示 -2000 N*m ~ 4553.1 N*m)，最小计量单元：0.1 N*m
			// 0xFF,0xFE：异常；0xFF,0xFF：无效
			int driveMotorTorque = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 驱动电机温度：有效值范围：0~250(数值偏移量40度，表示-40度 ~ +210度)，最小计量单元：1度
			// 0xFE：异常；0xFF：无效
			short driveMotorTemperature = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 电机控制器输入电压：有效值范围：0 ~ 60000(表示0V ~ 6000V)，最小计量单元：0.1V
			// 0xFF,0xFE：异常；0xFF,0xFF：无效
			int motorControllerInputVoltage = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 电机控制器直流母线电流：有效值范围：0 ~ 20000(数值偏移量1000A，表示-1000A ~ +1000A)，最小计量单元：0.1A
			// 0xFF,0xFE：异常；0xFF,0xFF：无效
			int motorControllerDCBusCurrent = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			length = length + 12;
			
			driveMotList[i].setDriveMotorSerialNumber(driveMotorSerialNumber);
			driveMotList[i].setDriveMotorState(driveMotorState);
			driveMotList[i].setDriveMotorControllerTemperature(driveMotorControllerTemperature);
			driveMotList[i].setDriveMotorSpeed(driveMotorSpeed);
			driveMotList[i].setDriveMotorTorque(driveMotorTorque);
			driveMotList[i].setDriveMotorTemperature(driveMotorTemperature);
			driveMotList[i].setMotorControllerInputVoltage(motorControllerInputVoltage);
			driveMotList[i].setMotorControllerDCBusCurrent(motorControllerDCBusCurrent);
						
		}
		
		driveMotData.setVehicleVIN(vehicleVIN);
		driveMotData.setNumberOfDriveMotors(numberOfDriveMotors);
		driveMotData.setDriMotorInfoList(driveMotList);
		
		System.out.println(driveMotData);
		// 更新数据库
		
		return length;
	}


	public static int fuelCellDataHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 0;
		
		FuelCellData fuelCellData = new FuelCellData();
		
		// 燃料电池电压：有效值范围：0~20000(表示0V ~ 2000V)，最小计量单元：0.1V，0xFF,0xFE：异常；0xFF,0xFF：无效
		int fuelCellVoltage = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 燃料电池电流：有效值范围：0~20000(表示0A ~ +2000A)，最小计量单元：0.1A，0xFF,0xFE：异常；0xFF,0xFF：无效
		int fuelCellCurrent = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 燃料消耗率：有效值范围：0~60000(表示0kg/100km ~ 600kg/100km)，最小计量单元：0.01kg/100km
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		int fuelConsumptionRate = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 燃料电池温度探针总数：N个燃料电池温度探针，有效值范围：0~65531，0xFF,0xFE：异常；0xFF,0xFF：无效
		// Fuel Cell Temperature Probes
		int totalNumberOfFCTP = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 探针温度值：有效值范围：0~240(数值偏移量40度，表示-40度 ~ +200度)，最小计量单元：1度
		short[] probeTemperatureValue = new short[totalNumberOfFCTP];
		for (int i = 0; i < totalNumberOfFCTP; i++) {
			probeTemperatureValue[i] = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
		}
		
		// 氢系统中最高温度：有效值范围：0~2400(偏移量40度，表示-40度 ~ 200度)，最小计量单元：0.1度
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		// The highest temperature in a hydrogen system
		int highestTempOfHydrogenSystem = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 氢系统中最高温度探针代号：有效值范围：1~252，0xFE：异常；0xFF：无效
		// The highest temperature probe code in a hydrogen system
		short highestTempProbeCodeOfHydSys = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 氢气最高浓度：有效值范围：0~60000(表示0mg/kg ~ 50000mg/kg)，最小计量单元：1mg/kg，0xFF,0xFE：异常；0xFF,0xFF：无效
		// The highest concentration of hydrogen
		int highestConOfHydrogen = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 氢气最高浓度传感器代号：有效值范围：1~252，0xFE：异常；0xFF：无效
		// The highest hydrogen concentration sensor code
		short highestHyConSensorCode = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 氢气最高压力：有效值范围：0~1000(表示0 MPa ~ 100 MPa)，最小计量单元：0.1 MPa
		// Hydrogen maximum pressure
		int hydrogenMaxPressure = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 氢气最高压力传感器代号：有效值范围：1~252，0xFE：异常；0xFF：无效
		// Hydrogen gas maximum pressure sensor code
		short hydrogenMaxPressureSensorCode = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 高压DC/DC状态： 0x01：工作；0x02：断开；0xFE：异常；0xFF：无效
		// High voltage DC/DC state
		short highVoltageDCState = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		length = length + 18 + totalNumberOfFCTP;
		
		fuelCellData.setVehicleVIN(vehicleVIN);
		fuelCellData.setFuelCellVoltage(fuelCellVoltage);
		fuelCellData.setFuelCellCurrent(fuelCellCurrent);
		fuelCellData.setFuelConsumptionRate(fuelConsumptionRate);
		fuelCellData.setTotalNumberOfFCTP(totalNumberOfFCTP);
		fuelCellData.setProbeTemperatureValue(probeTemperatureValue);
		fuelCellData.setHighestTempOfHydrogenSystem(highestTempOfHydrogenSystem);
		fuelCellData.setHighestTempProbeCodeOfHydSys(highestTempProbeCodeOfHydSys);
		fuelCellData.setHighestConOfHydrogen(highestConOfHydrogen);
		fuelCellData.setHighestHyConSensorCode(highestHyConSensorCode);
		fuelCellData.setHydrogenMaxPressure(hydrogenMaxPressure);
		fuelCellData.setHydrogenMaxPressureSensorCode(hydrogenMaxPressureSensorCode);
		fuelCellData.setHighVoltageDCState(highVoltageDCState);
		
		System.out.println(fuelCellData);
		// 更新数据库
		
		return length;
	}
	
	public static int engineDateHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 0;
		
		EngineData engineData = new EngineData();
		
		// 发动机状态：0x01：启动状态；0x02：关闭状态；0xFE：异常；0xFF：无效
		short engineState = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 曲轴转速：有效范围：0~60000(表示 0r/min ~ 60000r/min)，最小计量单元：1r/min
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		int crankshaftSpeed = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 燃料消耗率：有效值范围：0~60000(表示 0L/100km ~ 600L/100km)，最小计量单元：0.01L/100km
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		int fuelConsumptionRate = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		length = length + 5;
		
		
		engineData.setVehicleVIN(vehicleVIN);
		engineData.setEngineState(engineState);
		engineData.setCrankshaftSpeed(crankshaftSpeed);
		engineData.setFuelConsumptionRate(fuelConsumptionRate);
		
		System.out.println(engineData);
		
		// 更新数据库
		
		return length;
	}
	
	public static int vehiclePositionHandler(byte[] bytes, int pos, String vehicleVIN) {
		
		// 该段数据单元长度
		int length = 0;
		
		VehiclePosition vehiclePosition = new VehiclePosition();
		
		// 定位状态：每一位代表的意思：
		// 第0位：0：有效定位；1：无效定位(当数据通信正常，而不能获取定位信息时，发送最后一次有效定位信息，并将定位状态置为无效)
		// 第1位：0：北纬；1：南纬
		// 第2位：0：东经；1：西经
		// 第3~7位：保留
		byte positionStatus = bytes[pos];
		pos = pos + 1;
		
		// 经度：以度为单位的经度值乘以10^6，精确到百万分之一度
		long longitude = Unsigned.getUnsignedInt(ByteUtil.getInt(bytes, pos)) * 1000000;
		pos = pos + 4;
		
		// 纬度：以度为单位的纬度值乘以10^6，精确到百万分之一度
		long latitude = Unsigned.getUnsignedInt(ByteUtil.getInt(bytes, pos)) * 1000000;
		pos = pos + 4;
		
		int speed = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		int direction = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		length = length + 13;
		
		vehiclePosition.setVehicleVIN(vehicleVIN);
		vehiclePosition.setPositionStatus(positionStatus);
		vehiclePosition.setLongitude(longitude);
		vehiclePosition.setLatitude(latitude);
		vehiclePosition.setSpeed(speed);
		vehiclePosition.setDirection(direction);
		
		System.out.println(vehiclePosition);
		
		// 更新数据库
		
		return length;
	}
	
	
	public static int extremeValueDataHandler(byte[] bytes, int pos, String vehicleVIN){
		// 也可以不传递车辆识别码
		// String vehicleVIN = HeaderDecoder.getVehicleVIN(bytes);
		// 极值数据单元长度
		int length = 0;
		
		ExtremeValueData extremeValueData = new ExtremeValueData();
		
		// 最高电压电池子系统号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The highest voltage battery subsystem number
		short highestVBSN = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最高电压电池单体代号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The highest voltage battery monomer code
		short highestVBMC = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 电池单体电压最高值；有效值范围：0~15000（表示0V ~ 15V），最小计量单元：0.001V
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		// The highest cell voltage
		int highestCV = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 最低电压电池子系统号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The lowest voltage battery subsystem number
		short lowestVBSN = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最低电压电池单体代号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The lowest voltage battery monomer code
		short lowestVBMC = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 电池单体电压最低值；有效值范围：0~15000（表示0V ~ 15V），最小计量单元：0.001V
		// 0xFF,0xFE：异常；0xFF,0xFF：无效
		// The lowest cell voltage
		int lowestCV = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		// 最高温度子系统号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The highest temperature subsystem number
		short highestTemperSN = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最高温度探针序号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The probe number of maximum temperature
		short maxTemperProbNum = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最高温度值；有效值范围：0~250（数值偏移量40°C，表示 -40°C ~ +210°C）
		// 最小计量单元：1°C，0xFE：异常，0xFF：无效
		// The highest temperature value
		short highestTemperValue = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最低温度子系统号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The lowest temperature subsystem number
		short lowestTemperSN = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最低温度探针序号；有效值范围：1~250，0xFE：异常，0xFF：无效
		// The probe number of minimum temperature
		short minTemperProbNum = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 最低温度值；有效值范围：0~250（数值偏移量40°C，表示 -40°C ~ +210°C）
		// 最小计量单元：1°C，0xFE：异常，0xFF：无效
		// The lowest temperature value
		short lowestTemperValue = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		length = length + 14;
		
		extremeValueData.setVehicleVIN(vehicleVIN);
		
		extremeValueData.setHighestVBSN(highestVBSN);
		extremeValueData.setHighestVBMC(highestVBMC);
		extremeValueData.setHighestCV(highestCV);
		
		extremeValueData.setLowestVBSN(lowestVBSN);
		extremeValueData.setLowestVBMC(lowestVBMC);
		extremeValueData.setLowestCV(lowestCV);
		
		extremeValueData.setHighestTemperSN(highestTemperSN);
		extremeValueData.setMaxTemperProbNum(maxTemperProbNum);
		extremeValueData.setHighestTemperValue(highestTemperValue);
		
		extremeValueData.setLowestTemperSN(lowestTemperSN);
		extremeValueData.setMinTemperProbNum(minTemperProbNum);
		extremeValueData.setLowestTemperValue(lowestTemperValue);
		
		System.out.println(extremeValueData);
		// 更新数据库
		return length;
	}
	
	public static int alarmDataHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 报警数据单元长度
		int length = 0;
		
		AlarmData alarmData = new AlarmData();
		
		// 最高报警等级；为当前发生的故障中的最高等级值，有效值范围：0~3，0：无故障；1：1级故障，指代不影响车辆正常行驶的故障；
		// 2:2级故障，指代影响车辆性能，需驾驶员限制行驶的故障；3:3级故障，为最高级别故障，指代驾驶员应立即停车处理或请求救援的故障；
		// 具体等级对应的故障内容由厂商自行定义；0xFE：异常，0xFF：无效
		// The highest alarm level
		short highestAlarmLevel = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 通用报警标志
		// General alarm signs 通用报警标志位定义见文档的表18
		int genAlarmSigns = ByteUtil.getInt(bytes, pos);
		pos = pos + 4;
		
		// 可充电储能装置故障总数N
		// N个可充电储能装置故障，有效值范围：0~252,0xFE：异常，0xFF：无效
		// Total number of failures of rechargeable energy storage devices
		short totalNumFailOfRecharge = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 可充电储能装置故障代码列表
		// 扩展性数据，由厂商自行定义，可充电储能装置故障个数等于可充电储能装置故障总数
		// Rechargeable energy storage device fault code list
		// 可充电储能装置故障代码列表暂且解释为int类型
		int[] recharESDFaultCodeList = new int[totalNumFailOfRecharge];
		for (int i = 0; i < totalNumFailOfRecharge; i++) {
			recharESDFaultCodeList[i] = ByteUtil.getInt(bytes, pos);
			pos = pos + 4;
		}
		
		// 驱动电机故障总数N
		// N个驱动电机故障，有效值范围：0~252,0xFE：异常，0xFF：无效
		// Total number of drive motor failures
		short totalNumOfDriveMotFail = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 驱动电机故障代码列表；厂商自行定义，驱动电机故障个数等于驱动电机故障总数
		// Drive motor fault code list
		// 驱动电机故障代码列表也暂且解释为int类型
		int[] driveMotFaultCodeList = new int[totalNumOfDriveMotFail];
		for (int i = 0; i < totalNumOfDriveMotFail; i++) {
			driveMotFaultCodeList[i] = ByteUtil.getInt(bytes, pos);
			pos = pos + 4;
		}
		
		// 发动机故障总数N
		// N个发动机故障，有效值范围：0~252,0xFE：异常，0xFF：无效
		// Total number of engine failures
		short totalNumOfEngineFail = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 发动机故障代码列表；厂商自行定义，发动机故障个数等于发动机故障总数
		// Engine fault code list
		// 发动机故障代码列表暂且解释为int类型
		int[] engineFaulCodeList = new int[totalNumOfEngineFail];
		for (int i = 0; i < totalNumOfEngineFail; i++) {
			engineFaulCodeList[i] = ByteUtil.getInt(bytes, pos);
			pos = pos + 4;
		}
		
		// 其他故障总数N
		// N个其他故障，有效值范围：0~252,0xFE：异常，0xFF：无效
		// Total number of other failures
		short totalNumOfOtherFail = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		// 其他故障代码列表；厂商自行定义，故障个数等于故障总数
		// Other fault code list
		int[] otherFaultCodeList = new int[totalNumOfOtherFail];
		for (int i = 0; i < totalNumOfOtherFail; i++) {
			otherFaultCodeList[i] = ByteUtil.getInt(bytes, pos);
			pos = pos + 4;
		}
		
		length = length + 9 + 4 * ( totalNumFailOfRecharge + totalNumOfDriveMotFail + totalNumOfEngineFail + totalNumOfOtherFail);
		
		
		alarmData.setVehicleVIN(vehicleVIN);
		
		alarmData.setHighestAlarmLevel(highestAlarmLevel);
		alarmData.setGenAlarmSigns(genAlarmSigns);
		
		alarmData.setTotalNumFailOfRecharge(totalNumFailOfRecharge);
		alarmData.setRecharESDFaultCodeList(recharESDFaultCodeList);
		
		alarmData.setTotalNumOfDriveMotFail(totalNumOfDriveMotFail);
		alarmData.setDriveMotFaultCodeList(driveMotFaultCodeList);
		
		alarmData.setTotalNumOfEngineFail(totalNumOfEngineFail);
		alarmData.setEngineFaulCodeList(engineFaulCodeList);
		
		alarmData.setTotalNumOfOtherFail(totalNumOfOtherFail);
		alarmData.setOtherFaultCodeList(otherFaultCodeList);
		
		System.out.println(alarmData);
		// 更新数据库
		return length;
	}
	
	public static int voltageDataForResdHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 可充电储能装置电压数据单元长度
		int length = 0; 
		
		VoltageDataForRESD voltageDataForResd = new VoltageDataForRESD();
		
		// 可充电储能子系统个数；N个可充电储能子系统，有效值范围：1~250，0xFE：异常，0xFF：无效
		// Total number of rechargeable energy storage subsystems
		short numOfRechargeESS = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
		
		length = length + 1;
		
		VoltageData[] subsystemVolInfoList = new VoltageData[numOfRechargeESS];
		
		// 可充电储能子系统电压信息列表
		// Rechargeable energy storage subsystem voltage information list
		// 按可充电储能子系统序号依次排列，每个可充电储能子系统电压数据格式见如下的for循环
		for (int i = 0; i < numOfRechargeESS; i++) {
			// 可充电储能子系统号；有效值范围：1~250
			// Rechargeable energy storage subsystem number
			short rechargeESSNum = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 可充电储能装置电压；有效值范围：0~10000（表示0V ~ 1000V），最小计量单元：0.1V，0xFF,0xFE：异常，0xFF,0xFF：无效
			// Rechargeable energy storage device voltage
			int rechargeESDeviceVoltage = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 可充电储能装置电流；有效值范围：0~20000（数值偏移量1000A，表示-1000A ~ +1000A），最小计量单元：0.1A
			// 0xFF,0xFE：异常，0xFF,0xFF：无效
			// Rechargeable energy storage device current
			int rechargeESDeviceCurrent = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 单体电池总数；N个电池单体，有效值范围：1~65531，0xFF,0xFE：异常，0xFF,0xFF：无效
			// The total number of single batteries
			int totalNumOfSingleBattery = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 本帧起始电池序号；当本帧单体个数超过200时，应拆分成多帧数据进行传输，有效值范围：1~65531
			// The starting battery number of this frame
			int startBattNumOfFrame = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 本帧单体电池总数；本帧单体总数m，有效值范围1~200
			// The total number of single battery in this frame
			short totalNumOfSingleBattInFrame = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 单体电池电压；有效值范围：0~60000（表示0V~60.000V），最小计量单元：0.001V
			// 单体电池电压个数等于本帧单体电池总数m，0xFF,0xFE：异常，0xFF,0xFF：无效
			// Single battery voltage
			int[] singleBatteryVoltage = new int[totalNumOfSingleBattInFrame];
			for (int j = 0; j < totalNumOfSingleBattInFrame; j++) {
				singleBatteryVoltage[j] = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
				pos = pos + 2;
			}
			length = length + 10 + 2 * totalNumOfSingleBattInFrame;
			
			subsystemVolInfoList[i].setRechargeESSNum(rechargeESSNum);
			subsystemVolInfoList[i].setRechargeESDeviceVoltage(rechargeESDeviceVoltage);
			subsystemVolInfoList[i].setRechargeESDeviceCurrent(rechargeESDeviceCurrent);
			subsystemVolInfoList[i].setTotalNumOfSingleBattery(totalNumOfSingleBattery);
			subsystemVolInfoList[i].setStartBattNumOfFrame(startBattNumOfFrame);
			subsystemVolInfoList[i].setTotalNumOfSingleBattInFrame(totalNumOfSingleBattInFrame);
			subsystemVolInfoList[i].setSingleBatteryVoltage(singleBatteryVoltage);
			
			
		}
		
		voltageDataForResd.setVehicleVIN(vehicleVIN);
		voltageDataForResd.setNumOfRechargeESS(numOfRechargeESS);
		voltageDataForResd.setSubsystemVolInfoList(subsystemVolInfoList);
		
		System.out.println(voltageDataForResd);
		// 更新数据库
		
		return length;
	}
	
	public static int temperatureDataForResdHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 可充电储能装置温度数据
		int length = 0;
		
		TemperatureDataForRESD temperDataForResd = new TemperatureDataForRESD();
		
		// 可充电储能子系统个数；N个可充电储能子系统，有效值范围：1~250，0xFE：异常，0xFF：无效
		// Total number of rechargeable energy storage subsystems
		short numOfRechargeESS = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
				
		length = length + 1;
		
		TemperatureData[] tempInfoListOfRESD = new TemperatureData[numOfRechargeESS];
		
		// 可充电子系统温度信息列表
		for (int i = 0; i < numOfRechargeESS; i++) {
			// 可充电储能子系统号；有效值范围：1~250
			// Rechargeable energy storage subsystem number
			short rechargeESSNum = Unsigned.getUnsignedByte(bytes[pos]);
			pos = pos + 1;
			
			// 可充电储能温度探针个数；N个温度探针，有效值范围：1~65531，0xFF,0xFE：异常，0xFF,0xFF：无效
			// The number of rechargeable energy storage temperature probes
			int numOfRechargeESTempProbes = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
			pos = pos + 2;
			
			// 可充电储能子系统各温度探针检测到的温度值；有效值范围：0~250（数值偏移量40°C，表示 -40°C ~ +210°C）
			// 最小计量单元：1°C，0xFE：异常，0xFF：无效
			// The temperature value of each temperature probe detected of the rechargeable energy storage subsystem
			short[] tempValueOfProbe = new short[numOfRechargeESTempProbes];
			for (int j = 0; j < numOfRechargeESTempProbes; i++) {
				tempValueOfProbe[j] = Unsigned.getUnsignedByte(bytes[pos]);
				pos = pos + 1;
			}
		
			length = length + 3 + numOfRechargeESTempProbes;
			
			tempInfoListOfRESD[i].setRechargeESSNum(rechargeESSNum);
			tempInfoListOfRESD[i].setNumOfRechargeESTempProbes(numOfRechargeESTempProbes);
			tempInfoListOfRESD[i].setTempValueOfProbe(tempValueOfProbe);
			
		}
		
		temperDataForResd.setVehicleVIN(vehicleVIN);
		temperDataForResd.setNumOfRechargeESS(numOfRechargeESS);
		temperDataForResd.setTempInfoListOfRESD(tempInfoListOfRESD);
		
		System.out.println(temperDataForResd);
		
		//更新数据库
		
		return length;
	}
	
	public static int eCarVehicleStateInfoHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 逸卡车辆状态报文单元长度
		int length = 0;
		
		EcarVehicleStateInfo eCarVehicleInfo = new EcarVehicleStateInfo();
		eCarVehicleInfo.setVehicleVIN(vehicleVIN);
		
		// 逸卡这部分数据属于自定义数据，格式见标准 表19
		int dataLength = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
		pos = pos + 2;
		
		length = length + 2;
		
		if (dataLength == 14) {
			System.out.println("逸卡数据长度正确！");
		}
		else {
			System.out.println("逸卡数据长度错误！");
		}
		
		// 解析逸卡车辆状态报文
		
		// Car State 1
		byte gearOfPRND = bytes[pos];
		pos = pos + 1;
		
		byte gearOfL123 = bytes[pos];
		pos = pos + 1;
		
		byte DCU = bytes[pos];
		pos = pos + 1;
		
		byte fourOfCarState1 = bytes[pos];
		pos = pos + 1;
		
		byte fiveOfCarState1 = bytes[pos];
		pos = pos + 1;
		
		eCarVehicleInfo.setGearOfPRND(gearOfPRND);

		eCarVehicleInfo.setGearOfL123(gearOfL123);
		
		eCarVehicleInfo.setDCU_State((byte) (DCU & 0x0F));
		eCarVehicleInfo.setSpeed_Limit_Command((byte) ((DCU & 0x30) >> 4));
		// 这儿为什么没有用 (byte) ((DCU & 0xC0) >> 6),是因为偶数位一定为0，所以可用0x60，即：0110 0000
		eCarVehicleInfo.setDCU_Running_Mode((byte) ((DCU & 0x60) >> 6));
		
		eCarVehicleInfo.setMain_Circuit_State((byte) (fourOfCarState1 & 0x03));
		// 这儿用 0x0C，试试看，如果解析错误也能明白到底哪儿出错了
		eCarVehicleInfo.setCool_Liquid_State((byte) ((fourOfCarState1 & 0x0C) >> 2));
		eCarVehicleInfo.setVoice_Police_State((byte) ((fourOfCarState1 & 0x30) >> 4));
		// 这儿用 0xC0
		eCarVehicleInfo.setCooling_Water_Pump((byte) ((fourOfCarState1 & 0xC0) >> 6));		
				
		eCarVehicleInfo.setSecurity_Switch((byte) (fiveOfCarState1 & 0x03));
		eCarVehicleInfo.setLeakage_Elec_Info((byte) ((fiveOfCarState1 & 0x0C) >> 2));
		eCarVehicleInfo.setCooling_Fan((byte) ((fiveOfCarState1 & 0x30) >> 4));
		eCarVehicleInfo.setDoor_Enable_Open_State((byte) ((fiveOfCarState1 & 0xC0) >> 6));
		
		
		// Car State 2
		byte battery_Voltage = bytes[pos];
		pos = pos + 1;
		
		eCarVehicleInfo.setBattery_Voltage(battery_Voltage);
		
		
		// Car State 3
		byte single_Mileage = bytes[pos];
		pos = pos + 1;
		
		eCarVehicleInfo.setSingle_Mileage(single_Mileage);
		
		
		// Car State 5
		byte average_Battery_Imbalance = bytes[pos];
		pos = pos + 1;
		
		eCarVehicleInfo.setAverage_Battery_Imbalance(average_Battery_Imbalance);
		
		
		// Charging State
		byte chargeByte1 = bytes[pos];
		pos = pos + 1;
		
		eCarVehicleInfo.setCharging_Mode((byte) (chargeByte1 & 0x0F));
		eCarVehicleInfo.setCharger_Type((byte) ((chargeByte1 & 0x30) >> 4));
		eCarVehicleInfo.setCharging_State((byte) ((chargeByte1 & 0xC0) >> 6));
		
		byte chargeByte2 = bytes[pos];
		pos = pos + 1;
		
		eCarVehicleInfo.setCharging_Process((byte) (chargeByte2 & 0x03));
		eCarVehicleInfo.setCharging_Connection((byte) ((chargeByte2 & 0xC0) >> 2));
		eCarVehicleInfo.setCharging_Reserved((byte) 0);
		
		short charging_Amount = ByteUtil.getShort(bytes, pos);
		pos = pos + 2;
		
		eCarVehicleInfo.setCharging_Amount(charging_Amount);
		
		short charging_Time = ByteUtil.getShort(bytes, pos);
		pos = pos + 2;
		
		eCarVehicleInfo.setCharging_Time(charging_Time);
		
		length = length + 14;
		
		System.out.println(eCarVehicleInfo);
		
		return length;
	}

}
