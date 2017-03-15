package com.cit.its.messageStruct;

/**
 * 
 * @author QingXi
 * 逸卡公司车辆的车辆状态报文与标准中不同的，其他的信息已在标准报文中传送
 * 这部分属于自定义数据，格式参见 标准表19
 */

public class EcarVehicleStateInfo {
	
	private String vehicleVIN;					// 车辆VIN码
	
//	private String time;						// 时间
	
	// Car State 1
	
	// byte 1[1-0]：P 挡，00：无效，01：有效 
	// byte 1[3-2]：R 挡，00：无效，01：有效 
	// byte 1[5-4]：N 挡，00：无效，01：有效 
	// byte 1[7-6]：D 挡，00：无效，01：有效 
	private byte gearOfPRND;					// byte 1, PRND挡 
												
	// byte 2[1-0]：L1 挡，00：无效，01：有效 
	// byte 2[3-2]：L2 挡，00：无效，01：有效 
	// byte 2[5-4]：L3 挡，00：无效，01：有效 
	private byte gearOfL123;					// byte 2 [5-0], L挡
	// byte 2[7-6]：保留
	private byte gearOfL123_Reserved;			// byte 2 [7-6], L挡 保留
	
	
	private byte DCU_State;						// byte 3 [3-0], 驱动单元（DCU）工作状态, 1：正转，2：反转，3：待机，4：故障中
	private byte speed_Limit_Command;			// byte 3 [5-4], 限速命令, 00：全速，01：50%，10：25% 
	private byte DCU_Running_Mode;				// byte 3 [7-6], 驱动单元（DCU）运行模式, 00：普通，01：应急（开环）
	
	private byte main_Circuit_State;			// byte 4 [1-0], 主电路状态, 00：全断开，01：上电过程中，10：主电路正常
	private byte cool_Liquid_State;				// byte 4 [3-2], 冷却液位状态, 00：正常，01：冷却液不足
	private byte voice_Police_State;			// byte 4 [5-4], 声音报警, 00：关闭，01：打开
	private byte cooling_Water_Pump;			// byte 4 [7-6], 散热水泵, 00：停止，01：启动
	
	
	private byte security_Switch;				// byte 5 [1-0], 安全开关标志，00：断开，01：闭合
	private byte leakage_Elec_Info;				// byte 5 [3-2], 漏电信息，00：无漏电，01：漏电
	private byte cooling_Fan;					// byte 5 [5-4], 散热风机：00：停止，01：风机 1 启动，10：风机 2 启动，11：风机 1/2 启动
	private byte door_Enable_Open_State;		// byte 5 [7-6], 开门使能，00：禁止开门，01：允许开门
	
	
	
	// Car State 2
	private float battery_Voltage;				// 蓄电池电压, 0.1V/位，正偏 10.0V，范围 10.0V~35.0V 
	
	// Car State 3
	private float single_Mileage;				// 单次里程, 0.01Km/位，0 偏，范围 0.00~600.00Km
	
	// Car State 5
	private float average_Battery_Imbalance;	// 平均电池不平衡度, 0.001/位，范围 0-250
	
	// Charging State
	private byte charging_Mode;					// byte 1 [3-0], 充电模式，1：恒压式，2：恒流式，3：脉冲式，4 恒流限压式
	private byte charger_Type;					// byte 1 [5-4], 充电机类型，00：非车载式，01：车载式 
	private byte charging_State;				// byte 1 [7-6], 充电状态，00：正常，01：故障
	
	private byte charging_Process;				// byte 2 [1-0], 充电过程，00：停止，01：充电过程中，10：充电完成
	private byte charging_Connection;			// byte 2 [3-2], 充电连接，00：未连接，01：已连接（硬线连接状态）
	private byte charging_Reserved;				// byte 2 [7-4], 保留
	
	private short charging_Amount;				// byte 4-3：本次充电量（1kW.h/位，0 偏，范围 0~1000kW.h） 
	private short charging_Time;				// byte 6-5：本次充电时间（1min/位，范围 0-6000）, 应该是充电时长
//	private int insulation_Resistance;			// byte 8-7：绝缘电阻值（无效为 0xFF 0xFF） 整车数据中已包含绝缘电阻
	
	// Car State
	private int speed;							// 速度 ： 有效值范围 0 ~ 2200
	
	private int direction;						// 方向 : 有效值范围 0 ~ 359， 正北为0，顺时针
	
	// set, get方法
	public void setVehicleVIN(String vehicleVIN) {
		this.vehicleVIN = vehicleVIN;
	}
	
	public String getVehicleVIN() {
		return vehicleVIN;
	}
/*	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}
	*/
	// Car State 1
	public void setGearOfPRND(byte gearOfPRND) {
		this.gearOfPRND = gearOfPRND;
	}
	
	public byte getGearOfPRND() {
		return gearOfPRND;
	}
	
	public void setGearOfL123(byte gearOfL123) {
		this.gearOfL123 = gearOfL123;
	}
	
	public byte getGearOfL123() {
		return gearOfL123;
	}
	
	public void setGearOfL123_Reserved(byte gearOfL123_Reserved) {
		this.gearOfL123_Reserved = gearOfL123_Reserved;
	}
	
	public byte getGearOfL123_Reserved() {
		return gearOfL123_Reserved;
	}
	
	public void setDCU_State(byte DCU_State) {
		this.DCU_State = DCU_State;
	}
	
	public byte getDCU_State() {
		return DCU_State;
	}
	
	public void setSpeed_Limit_Command(byte speed_Limit_Command) {
		this.speed_Limit_Command = speed_Limit_Command;
	}
	
	public byte getSpeed_Limit_Command() {
		return speed_Limit_Command;
	}
	
	public void setDCU_Running_Mode(byte DCU_Running_Mode) {
		this.DCU_Running_Mode = DCU_Running_Mode;
	}
	
	public byte getDCU_Running_Mode() {
		return DCU_Running_Mode;
	}
	
	public void setMain_Circuit_State(byte main_Circuit_State) {
		this.main_Circuit_State = main_Circuit_State;
	}
	
	public byte getMain_Circuit_State() {
		return main_Circuit_State;
	}
	
	public void setCool_Liquid_State(byte cool_Liquid_State) {
		this.cool_Liquid_State = cool_Liquid_State;
	}
	
	public byte getCool_Liquid_State() {
		return cool_Liquid_State;
	}
	
	public void setVoice_Police_State(byte voice_Police_State) {
		this.voice_Police_State = voice_Police_State;
	}
	
	public byte getVoice_Police_State() {
		return voice_Police_State;
	}
	
	public void setCooling_Water_Pump(byte cooling_Water_Pump) {
		this.cooling_Water_Pump = cooling_Water_Pump;
	}
	
	public byte getCooling_Water_Pump() {
		return cooling_Water_Pump;
	}
	
	public void setSecurity_Switch(byte security_Switch) {
		this.security_Switch = security_Switch;
	}
	
	public byte getSecurity_Switch() {
		return security_Switch;
	}
	
	public void setLeakage_Elec_Info(byte leakage_Elec_Info) {
		this.leakage_Elec_Info = leakage_Elec_Info;
	}
	
	public byte getLeakage_Elec_Info() {
		return leakage_Elec_Info;
	}
	
	public void setCooling_Fan(byte cooling_Fan) {
		this.cooling_Fan = cooling_Fan;
	}
	
	public byte getCooling_Fan() {
		return cooling_Fan;
	}
	
	public void setDoor_Enable_Open_State(byte door_Enable_Open_State) {
		this.door_Enable_Open_State = door_Enable_Open_State;
	}
	
	public byte getDoor_Enable_Open_State() {
		return door_Enable_Open_State;
	}
	
	
	// Car State 2
	public void setBattery_Voltage(float battery_Voltage) {
		this.battery_Voltage = battery_Voltage;
	}
	
	public float getBattery_Voltage() {
		return battery_Voltage;
	}
	
	
	// Car State 3
	public void setSingle_Mileage(float single_Mileage) {
		this.single_Mileage = single_Mileage;
	}
	
	public float getSingle_Mileage() {
		return single_Mileage;
	}
	
	
	// Car State 5
	public void setAverage_Battery_Imbalance(float average_Battery_Imbalance) {
		this.average_Battery_Imbalance = average_Battery_Imbalance;
	}
	
	public float getAverage_Battery_Imbalance() {
		return average_Battery_Imbalance;
	}
	
	
	// Charging State
	public void setCharging_Mode(byte charging_Mode) {
		this.charging_Mode = charging_Mode;
	}
	
	public byte getCharging_Mode() {
		return charging_Mode;
	}
	
	public void setCharger_Type(byte charger_Type) {
		this.charger_Type = charging_Mode;
	}
	
	public byte getCharger_Type() {
		return charger_Type;
	}
	
	public void setCharging_State(byte charging_State) {
		this.charging_State = charging_State;
	}
	
	public byte getCharging_State() {
		return charging_State;
	}
	
	
	public void setCharging_Process(byte charging_Process) {
		this.charging_Process = charging_Process;
	}
	
	public byte getCharging_Process() {
		return charging_Process;
	}
	
	public void setCharging_Connection(byte charging_Connection) {
		this.charging_Connection = charging_Connection;
	}
	
	public byte getCharging_Connection() {
		return charging_Connection;
	}
	
	public void setCharging_Reserved(byte charging_Reserved) {
		this.charging_Reserved = charging_Reserved;
	}
	
	public byte getCharging_Reserved() {
		return charging_Reserved;
	}
	
	
	public void setCharging_Amount(short charging_Amount) {
		this.charging_Amount = charging_Amount;
	}
	
	public short getCharging_Amount() {
		return charging_Amount;
	}
	
	public void setCharging_Time(short charging_Time) {
		this.charging_Time = charging_Time;
	}
	
	public short getCharging_Time() {
		return charging_Time;
	}
/*	
	public void setInsulation_Resistance(int insulation_Resistance) {
		this.insulation_Resistance = insulation_Resistance;
	}
	
	public int getInsulation_Resistance() {
		return insulation_Resistance;
	}
	
	*/
	
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
	
	
	// Translate the code to String
	public String getPRND() {
		String pRND;
		switch(gearOfPRND) {
			case 0:
				pRND = "PRND挡无效";
				break;
			case 1:
				pRND = "P挡";
				break;
			case 4:
				pRND = "R挡";
				break;
			case 16:
				pRND = "N挡";
				break;
			case 64:
				pRND = "D挡";
				break;
			default:
				pRND = "PRND挡有误";
				break;
		}
		return pRND;
	}
	
	public String getL123() {
		String l123;
		switch(gearOfL123) {
			case 0:
				l123 = "L挡无效";
				break;
			case 1:
				l123 = "L1挡";
				break;
			case 4:
				l123 = "L2挡";
				break;
			case 16:
				l123 = "L3挡";
				break;
			default:
				l123 = "L挡有误";
				break;
		}
		return l123;
	}
	
	public String getDCUState() {
		String dcuState;
		switch(DCU_State) {
			case 1:
				dcuState = "正转";
				break;
			case 2:
				dcuState = "反转";
				break;
			case 3:
				dcuState = "待机";
				break;
			case 4:
				dcuState = "故障中";
				break;
			default:
				dcuState = "DCU状态有误";
				break;
		}
		return dcuState;
	}
	
	public String getSpeedLimit() {
		String speedLimit;
		switch(speed_Limit_Command) {
			case 0:
				speedLimit = "全速";
				break;
			case 1:
				speedLimit = "50%";
				break;
			case 2:
				speedLimit = "25%";
				break;
			default:
				speedLimit = "限度命令有误";
				break;
		}
		return speedLimit;
	}
	
	public String getDCURunningMode() {
		String dcuRunningMode;
		switch(DCU_Running_Mode) {
			case 0:
				dcuRunningMode = "普通";
				break;
			case 1:
				dcuRunningMode = "应急（开环）";
				break;
			default:
				dcuRunningMode = "DCU运行模式有误";
				break;
		}
		return dcuRunningMode;
	}
	
	public String getMainCircuitState() {
		String mainCirState;
		switch(main_Circuit_State) {
			case 0:
				mainCirState = "全断开";
				break;
			case 1:
				mainCirState = "上电过程中";
				break;
			case 2:
				mainCirState = "主电路正常";
				break;
			default:
				mainCirState = "主电路状态有误";
				break;
		}
		return mainCirState;
	}
	
	public String getCoolLiquidState() {
		String coolLiqState;
		switch(cool_Liquid_State) {
			case 0:
				coolLiqState = "正常";
				break;
			case 1:
				coolLiqState = "冷却液不足";
				break;
			default:
				coolLiqState = "冷却液状态有误";
				break;
		}
		return coolLiqState;
	}
	
	public String getVoicePoliceState() {
		String voicePolState;
		switch(voice_Police_State) {
			case 0:
				voicePolState = "关闭";
				break;
			case 1:
				voicePolState = "打开";
				break;
			default:
				voicePolState = "状态有误";
				break;
		}
		return voicePolState;
	}
	
	public String getCoolingWaterPump() {
		String coolWaterPump;
		switch(cooling_Water_Pump) {
			case 0:
				coolWaterPump = "停止";
				break;
			case 1:
				coolWaterPump = "打开";
				break;
			default:
				coolWaterPump = "状态有误";
				break;
		}
		return coolWaterPump;
	}
	
	
	public String getSecuritySwitch() {
		String securSwitch;
		switch(security_Switch) {
			case 0:
				securSwitch = "断开";
				break;
			case 1:
				securSwitch = "闭合";
				break;
			default:
				securSwitch = "状态有误";
				break;
		}
		return securSwitch;
	}
	
	public String getLeakageElecInfo() {
		String leakEleInfo;
		switch(leakage_Elec_Info) {
			case 0:
				leakEleInfo = "无漏电";
				break;
			case 1:
				leakEleInfo = "漏电";
				break;
			default:
				leakEleInfo = "状态有误";
				break;
		}
		return leakEleInfo;
	}
	
	public String getCoolingFan() {
		String coolFan;
		switch(cooling_Fan) {
			case 0:
				coolFan = "停止";
				break;
			case 1:
				coolFan = "风机1启动";
				break;
			case 2:
				coolFan = "风机2启动";
				break;
			case 3:
				coolFan = "风机1/2启动";
				break;
			default:
				coolFan = "风机状态有误";
				break;
		}
		return coolFan;
	}
	
	public String getDoorEnableOpenState() {
		String doorEnableOpen;
		switch(door_Enable_Open_State) {
			case 0:
				doorEnableOpen = "禁止开门";
				break;
			case 1:
				doorEnableOpen = "允许开门";
				break;
			default:
				doorEnableOpen = "开门使能状态有误";
				break;
		}
		return doorEnableOpen;
	}
	
	
	// Car State 2
	public String getBatteryVoltage() {
		return battery_Voltage + " V";
	}
	
	
	// Car State 3
	public String getSingleMileage() {
		return single_Mileage + " km";
	}
	
	
	// Car State 5 不需要转换
	
	
	// Charging State
	public  String getChargingMode() {
		String chargeMode;
		switch(charging_Mode) {
			case 1:
				chargeMode = "恒压式";
				break;
			case 2:
				chargeMode = "恒流式";
				break;
			case 3:
				chargeMode = "脉冲式";
				break;
			case 4:
				chargeMode = "恒流限压式";
				break;
			default:
				chargeMode = "充电模式有误";
				break;
		}
		return chargeMode;
	}
	
	public String getChargerType() {
		String chargerTy;
		switch(charger_Type) {
			case 0:
				chargerTy = "非车载式";
				break;
			case 1:
				chargerTy = "车载式";
				break;
			default:
				chargerTy = "充电机类型有误";
				break;
		}
		return chargerTy;
	}
	
	public String getChargingState() {
		String chargeState;
		switch(charging_State) {
			case 0:
				chargeState = "正常";
				break;
			case 1:
				chargeState = "故障";
				break;
			default:
				chargeState = "充电状态有误";
				break;
		}
		return chargeState;
	}
	
	public String getChargingProcess() {
		String chargeProcess;
		switch(charging_Process) {
			case 0:
				chargeProcess = "停止";
				break;
			case 1:
				chargeProcess = "充电过程中";
				break;
			case 2:
				chargeProcess = "充电完成";
				break;
			default:
				chargeProcess = "充电过程状态有误";
				break;
		}
		return chargeProcess;
	}
	
	public String getChargingConnection() {
		String chargeConnec;
		switch(charging_Connection) {
			case 0:
				chargeConnec = "未连接";
				break;
			case 1:
				chargeConnec = "已连接（硬线连接状态）";
				break;
			default:
				chargeConnec = "充电连接状态有误";
				break;
		}
		return chargeConnec;
	}
	
	public String getChargingAmount() {
		return charging_Amount + " kw.h";
	}
	
	public String getChargingTime() {
		return charging_Time + " min";
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
	
	// 重写toString()方法
	public String toString() {
		System.out.println("车辆VIN码 : " + vehicleVIN + "\n" + 
					//	   "时间 : " + time + "\n" + 
						   "PRND挡 : " + getPRND() + "\n" + 
						   "L挡 : " + getL123() + "\n" + 
						   "驱动单元（DCU）工作状态 : " + getDCUState() + "\n" + 
						   "限速命令 : " + getSpeedLimit() + "\n" +
						   "驱动单元（DCU）运行模式 : " + getDCURunningMode() + "\n" + 
						   "主电路状态 : " + getMainCircuitState() + "\n" + 
						   "冷却液位状态 : " + getCoolLiquidState() + "\n" +
						   "声音报警 : " + getVoicePoliceState() + "\n" +
						   "散热水泵 : " + getCoolingWaterPump() + "\n" + 
						   "安全开关标志 :" + getSecuritySwitch() + "\n" + 
						   "漏电信息 : " + getLeakageElecInfo() + "\n" + 
						   "散热风机 : " + getCoolingFan() + "\n" +
						   "开门使能 : " + getDoorEnableOpenState() + "\n" +
						   "蓄电池电压 : " + getBatteryVoltage() + "\n" + 
						   "单次里程 : " + getSingleMileage() + "\n" + 
						   "平均电池不平衡度 : " + average_Battery_Imbalance + "\n" +
						   "充电模式 : " + getChargingMode() + "\n" + 
						   "充电机类型 : " + getChargerType() + "\n" + 
						   "充电状态 : " + getChargingState() + "\n" + 
						   "充电过程 : " + getChargingProcess() + "\n" + 
						   "充电连接 : " + getChargingConnection() + "\n" + 
						   "本次充电量 : " + getChargingAmount() + "\n" + 
						   "本次充电时间 : " + getChargingTime() + "\n" + 
						   "速度 : " + getStrSpeed() + "\n" + 
						   "方向 : " + getStrDirection());
		return "";
	}

}
