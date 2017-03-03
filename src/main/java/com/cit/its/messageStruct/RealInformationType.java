package com.cit.its.messageStruct;

public enum RealInformationType {
	
	VEHICLE_INFO((short) 0x01),					// 整车数据，长度为20字节
	DRIVING_MOTOR_DATA((short) 0x02),			// 驱动电机数据
	FUEL_CELL_DATA((short) 0x03),				// 燃料电池数据     **文档中忽略
	ENGINE_DATA((short) 0x04),					// 发动机数据         **文档中忽略
	VEHICLE_POSITION((short) 0x05),				// 车辆位置
	EXTREME_VALUE_DATA((short) 0x06),			// 极值数据
	ALARM_DATA((short) 0x07),					// 报警数据
	
	// RESD: rechargeable energy storage devices
	VOLTAGE_DATA_FOR_RESD((short) 0x08),		// 可充电储能装置电压数据
	TEMPERATURE_DATA_FOR_RESD((short) 0x09),	// 可充电储能装置温度数据
	
	
	// 0x0A~0x2F	平台交换协议数据
	// 0x30~0x7F	预留
	// 0x80~0xFE	用户自定义
	
	// 0x80			逸卡车辆状态报文
	ECAR_VEHICLE_STATE_INFO((short) 0x80);
	
	
	private short value;
	
	private RealInformationType(short value) {
		this.value = value;
	}
	
	public short value() {
		return this.value;
	}
	
	public static RealInformationType getRealInformationTypeByValue(short value) {
		for (RealInformationType realInfomationType : RealInformationType.values()) {
			if (value == realInfomationType.value()) {
				return realInfomationType;
			}
		}
		return null;
	}

}
