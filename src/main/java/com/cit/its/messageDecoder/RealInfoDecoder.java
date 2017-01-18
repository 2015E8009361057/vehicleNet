package com.cit.its.messageDecoder;

import java.io.UnsupportedEncodingException;

import com.cit.its.util.ByteUtil;
import com.cit.its.util.Unsigned;

public class RealInfoDecoder {
		
	public static int vehicleInfoHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 20;
		
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
		
		// 更新数据库
		
		return length;
	}


	public static int drivingMotorDataHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 0;
		
		short numberOfDriveMotors = Unsigned.getUnsignedByte(bytes[pos]);
		pos = pos + 1;
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
			// 更新数据库
			// 有没有一种方法，将所有的驱动电机的信息保存下来，最后再更新数据库，这样便可以减少操作数据库的次数，提高效率
			// 全部保存到一个int数组中？
		}
		return length;
	}


	public static int fuelCellDataHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 0;
		
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
		
		// 更新数据库
		
		return length;
	}
	
	public static int engineDateHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 该段数据单元长度
		int length = 0;
		
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
		
		// 更新数据库
		
		return length;
	}
	
	public static int vehiclePositionHandler(byte[] bytes, int pos, String vehicleVIN) {
		
		// 该段数据单元长度
		int length = 0;
		
		// 定位状态：每一位代表的意思：
		// 第0位：0：有效定位；1：无效定位(当数据通信正常，而不能获取定位信息时，发送最后一次有效定位信息，并将定位状态置为无效)
		// 第1位：0：北纬；1：南纬
		// 第2位：0：东经；1：西经
		// 第3~7位：保留
		byte positionStatus = bytes[pos];
		pos = pos + 1;
		
		// 经度：以度为单位的经度值乘以10^6，精确到百万分之一度
		long longitude = Unsigned.getUnsignedInt(ByteUtil.getInt(bytes, pos));
		pos = pos + 4;
		
		// 纬度：以度为单位的纬度值乘以10^6，精确到百万分之一度
		long latitude = Unsigned.getUnsignedInt(ByteUtil.getInt(bytes, pos));
		pos = pos + 4;
		
		length = length + 9;
		
		// 更新数据库
		
		return length;
	}
	
	
	public static int extremeValueDataHandler(byte[] bytes, int pos, String vehicleVIN) {
		// 也可以不传递车辆识别码
		// String vehicleVIN = HeaderDecoder.getVehicleVIN(bytes);
		// 该段数据单元长度
		int length = 0;
		
		
		
		return length;
	}

}
