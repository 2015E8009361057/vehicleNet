package com.cit.its.messageStruct;

import com.cit.its.util.Unsigned;

//import io.vehicleNet.test.Unsigned;

public enum CommandType {
	
	VEHICLE_LOGIN((short) 0x01),					// 车辆登入
	REAL_INFORMATION_UPLOAD((short) 0x02),			// 实时信息上报
	REISSUED_INFORMATION_UPLOAD((short) 0x03),		// 补发信息上报
	VEHICLE_LOGOUT((short) 0x04),					// 车辆登出
	
	// 0x05~0x06 是平台向平台传输数据占用
	PLATFORM_LOGIN((short) 0x05),					// 平台登入
	PLATFORM_LOGOUT((short) 0x06),					// 平台登出
	
	// 0x07~0x08 终端数据预留 （上行）
	HEART_BEAT((short) 0x07),						// 心跳
	TERMINAL_CORRECTION((short) 0x08), 				// 终端校时
	
	// 0x09~0x7F 上行数据系统预留
	
	// 0x80~0x82终端数据预留 （下行）
	QUERY_COMMAND((short) 0x80),					// 查询命令
	SET_COMMAND((short) 0x81),						// 设置命令
	TERMINAL_CONTROL_COMMAND((short) 0x82);			// 车载终端控制命令
	
	// 0x83~0xBF 下行数据系统预留
	
	// 0xC0~0xFE 平台交换自定义数据
	
	private short value;
	
	private CommandType(short value) {
		this.value = value;
	}
	
	public short value() {
		return this.value;
	}
	
	public static CommandType getCommandTypeByValue(short value) {
		for (CommandType commandType : CommandType.values()) {
			if (value == commandType.value()) {
				return commandType;
			}
		}
		return null;
	}
	
	
	
/*	
	public static void main(String[] args) {
		CommandType login = CommandType.VEHICLE_LOGIN;
		short num = login.value();
		byte b = (byte) login.value();
		
		CommandType query = CommandType.QUERY_COMMAND;
		System.out.println(query.equals(CommandType.QUERY_COMMAND));
		byte bt = (byte) query.value();
		short sh = Unsigned.getUnsignedByte(bt);
		
		CommandType type = CommandType.getCommandTypeByValue(sh);
		System.out.println(type);
		System.out.println(login);
		System.out.println(num);
		System.out.println(b);
		System.out.println(query);
		System.out.println(query.value());
		System.out.println(bt);
		System.out.println(sh);
	}*/
}
