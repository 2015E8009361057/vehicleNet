package com.cit.its.messageStruct;


public class Parameter {
	
	private byte localStorageTimePeriod_ID = (byte) 0x01;				// 车载终端本地存储时间周期 ID
	
	private byte infoReportPeriod_Normal_ID = (byte) 0x02;				// 正常时，信息上报周期 ID
	
	private byte infoReportPeriod_Alarm_ID = (byte) 0x03;				// 出现报警时，信息上报时间周期 ID
	
	private byte lengthOfServiceAndManagePlatform_ID = (byte) 0x04;		// 远程服务与管理平台域名长度 ID
	
	private byte domainNameOfRSMP_ID = (byte) 0x05;						// 远程服务与管理平台域名 ID
	
	private byte portOfRSMP_ID = (byte) 0x06;							// 远程服务与管理平台端口 ID
	
	private byte hardwareVersion_ID = (byte) 0x07;						// 硬件版本 ID
	
	private byte firmwareVersion_ID = (byte) 0x08;						// 固件版本 ID
	
	private byte heartBeatPeriod_ID = (byte) 0x09;						// 车载终端心跳发送周期 ID
	
	private byte terminalResponseTimeout_ID = (byte) 0x0A;				// 终端应答超时时间 ID
	
	private byte platformResponseTimeout_ID = (byte) 0x0B;				// 平台应答超时时间 ID
	
	private byte loginIntervalTime_ID = (byte) 0x0C;					// 连续三次登入失败后，到下一次登入的间隔时间 ID
	
	private byte lengthOfPublicPlatform_ID = (byte) 0x0D;				// 公共平台域名长度 ID
	
	private byte domainNameOfPublicPlatform_ID = (byte) 0x0E;			// 公共平台域名 ID
	
	private byte portOfPublicPlatform_ID = (byte) 0x0F;					// 公共平台端口 ID
	
	private byte samplingValue_ID = (byte) 0x10;						// 是否处于抽样检测中 ID
	
	
	// 车载终端本地存储时间周期,有效值范围：0~60000（表示0ms~60000ms）, 最小计量单元1ms，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	// 
	private int localStorageTimePeriod;
	
	// 正常时，信息上报周期,有效值范围：1~600（表示1s~600s）, 最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	private int infoReportPeriod_Normal;
	
	// 出现报警时，信息上报时间周期,有效值范围：0~60000（表示0ms~60000ms）, 最小计量单元1ms，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	private int infoReportPeriod_Alarm;
	
	// 远程服务与管理平台域名长度M
	private int lengthOfServiceAndManagePlatform;
	
	// 远程服务与管理平台域名, RSMP: Remote Service And Manage Platform
	private byte[] domainNameOfRSMP;
	
	// 远程服务与管理平台端口,有效值范围：0~65531，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	private int portOfRSMP;
	
	// 硬件版本，车载终端厂商自行定义
	private String hardwareVersion;
	
	// 固件版本，车载终端厂商自行定义
	private String firmwareVersion;
	
	// 车载终端心跳发送周期，有效值范围：1~240（表示1s~240s），最小计量单元：1s，“0xFE”表示异常，“0xFF”表示无效
	private short heartBeatPeriod;
	
	// 终端应答超时时间，有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	private int terminalResponseTimeout;
	
	// 平台应答超时时间，有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	private int platformResponseTimeout;
	
	// 连续三次登入失败后，到下一次登入的间隔时间。有效值范围：1~240（表示1min~240min），最小计量单元：1min, “0xFE”表示异常，“0xFF”表示无效
	private short loginIntervalTime;
	
	// 公共平台域名长度
	private int lengthOfPublicPlatform;
	
	// 公共平台域名
	private byte[] domainNameOfPublicPlatform;
	
	// 公共平台端口,有效值范围：0~65531，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
	private int portOfPublicPlatform;
	
	// 是否处于抽样检测中,“0x01”表示是，“0x02”表示否，“0xFE”表示异常，“0xFF”表示无效
	private short samplingValue;
	
	// 参数ID：0x11~0x7F 预留
	// 参数ID：0x80~0xFE 用户自定义
	
	public Parameter() {
		localStorageTimePeriod = -1;
		infoReportPeriod_Normal = -1;
		infoReportPeriod_Alarm = -1;
		lengthOfServiceAndManagePlatform = -1;
		portOfRSMP = -1;
		hardwareVersion = "";
		firmwareVersion = "";
		heartBeatPeriod = -1;
		terminalResponseTimeout = -1;
		platformResponseTimeout = -1;
		loginIntervalTime = -1;
		lengthOfPublicPlatform = -1;
		portOfPublicPlatform = -1;
		samplingValue = -1;
	}
	
	
	// ID不可变，所以对ID来说，只有get方法
	public byte getLocalStorageTimePeriod_ID() {
		return localStorageTimePeriod_ID;
	}
	
	public byte getInfoReportPeriod_Normal_ID() {
		return infoReportPeriod_Normal_ID;
	}
	
	public byte getInfoReportPeriod_Alarm_ID() {
		return infoReportPeriod_Alarm_ID;
	}
	
	public byte getLengthOfServiceAndManagePlatform_ID() {
		return lengthOfServiceAndManagePlatform_ID;
	}
	
	public byte getDomainNameOfRSMP_ID() {
		return domainNameOfRSMP_ID;
	}
	
	public byte getPortOfRSMP_ID() {
		return portOfRSMP_ID;
	}
	
	public byte getHardwareVersion_ID() {
		return hardwareVersion_ID;
	}
	
	public byte getFirmwareVersion_ID() {
		return firmwareVersion_ID;
	}
	
	public byte getHeartBeatPeriod_ID() {
		return heartBeatPeriod_ID;
	}
	
	public byte getTerminalResponseTimeout_ID() {
		return terminalResponseTimeout_ID;
	}
	
	public byte getPlatformResponseTimeout_ID() {
		return platformResponseTimeout_ID;
	}
	
	public byte getLoginIntervalTime_ID() {
		return loginIntervalTime_ID;
	}
	
	public byte getLengthOfPublicPlatform_ID() {
		return lengthOfPublicPlatform_ID;
	}
	
	public byte getDomainNameOfPublicPlatform_ID() {
		return domainNameOfPublicPlatform_ID;
	}
	
	public byte getPortOfPublicPlatform_ID() {
		return portOfPublicPlatform_ID;
	}
	
	public byte getSamplingValue_ID() {
		return samplingValue_ID;
	}
	
	
	// set, get方法
	public void setLocalStorageTimePeriod(int localStorageTimePeriod) {
		this.localStorageTimePeriod = localStorageTimePeriod;
	}
	
	public int getLocalStorageTimePeriod() {
		return localStorageTimePeriod;
	}
	
	public void setInfoReportPeriod_Normal(int infoReportPeriod_Normal) {
		this.infoReportPeriod_Normal = infoReportPeriod_Normal;
	}
	
	public int getInfoReportPeriod_Normal() {
		return infoReportPeriod_Normal;
	}
	
	public void setInfoReportPeriod_Alarm(int infoReportPeriod_Alarm) {
		this.infoReportPeriod_Alarm = infoReportPeriod_Alarm;
	}
	
	public int getInfoReportPeriod_Alarm() {
		return infoReportPeriod_Alarm;
	}
	
	public void setLengthOfServiceAndManagePlatform(int lengthOfServiceAndManagePlatform) {
		this.lengthOfServiceAndManagePlatform = lengthOfServiceAndManagePlatform;
	}
	
	public int getLengthOfServiceAndManagePlatform() {
		return lengthOfServiceAndManagePlatform;
	}
	
	public void setDomainNameOfRSMP(byte[] domainNameOfRSMP) {
		this.domainNameOfRSMP = domainNameOfRSMP;
	}
	
	public byte[] getDomainNameOfRSMP() {
		return domainNameOfRSMP;
	}
	
	public void setPortOfRSMP(int portOfRSMP) {
		this.portOfRSMP = portOfRSMP;
	} 
	
	public int getPortOfRSMP() {
		return portOfRSMP;
	}
	
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	
	public void setHeartBeatPeriod(short heartBeatPeriod) {
		this.heartBeatPeriod = heartBeatPeriod;
	}
	
	public short getHeartBeatPeriod() {
		return heartBeatPeriod;
	}
	
	public void setTerminalResponseTimeout(int terminalResponseTimeout) {
		this.terminalResponseTimeout = terminalResponseTimeout;
	}
	
	public int getTerminalResponseTimeout() {
		return terminalResponseTimeout;
	}
	
	public void setPlatformResponseTimeout(int platformResponseTimeout) {
		this.platformResponseTimeout = platformResponseTimeout;
	}
	
	public int getPlatformResponseTimeout() {
		return platformResponseTimeout;
	}
	
	public void setLoginIntervalTime(short loginIntervalTime) {
		this.loginIntervalTime = loginIntervalTime;
	}
	
	public short getLoginIntervalTime() {
		return loginIntervalTime;
	}

	public void setLengthOfPublicPlatform(int lengthOfPublicPlatform) {
		this.lengthOfPublicPlatform = lengthOfPublicPlatform;
	}
	
	public int getLengthOfPublicPlatform() {
		return lengthOfPublicPlatform;
	}
	
	public void setDomainNameOfPublicPlatform(byte[] domainNameOfPublicPlatform) {
		this.domainNameOfPublicPlatform = domainNameOfPublicPlatform;
	}
	
	public byte[] getDomainNameOfPublicPlatform() {
		return domainNameOfPublicPlatform;
	}
	
	public void setPortOfPublicPlatform(int portOfPublicPlatform) {
		this.portOfPublicPlatform = portOfPublicPlatform;
	}
	
	public int getPortOfPublicPlatform() {
		return portOfPublicPlatform;
	}
	
	public void setSamplingValue(short samplingValue) {
		this.samplingValue = samplingValue;
	}
	
	public short getSamplingValue() {
		return samplingValue;
	}
	
	
	// 将数值转换为字符串类型
	public String intToString(int data) {
		String result = "";
		if (data == 0xFFFE) {
			result = result + "异常";
		}
		else if (data == 0xFFFF) {
			result = result + "无效";
		}
		else {
			result = result + data;
		}
		return result;
	}
	
	public String shortToString(short data) {
		String result = "";
		if (data == 0xFE) {
			result = result + "异常";
		}
		else if (data == 0xFF) {
			result = result + "无效";
		}
		else {
			result = result + data;
		}
		return result;
	}
	
	public String samplingToString(int data) {
		String result = "";
		if (data == 0xFE) {
			result = result + "异常";
		}
		else if (data == 0xFF) {
			result = result + "无效";
		}
		else if (data == 0x01) {
			result = result + "是";
		}
		else {
			result = result + "否";
		}
		return result;
	}
	
	// byte[] 转 String
	public String byteArrayToString(byte[] arr) {
		return new String(arr);
	}
	
	// 重写toString()方法
	public String toString() {
		return "车载终端本地存储时间周期 : " + intToString(localStorageTimePeriod) + "\n" + 
			   "正常信息上报时间周期 : " + intToString(infoReportPeriod_Normal) + "\n" + 
			   "报警信息上报时间周期 : " + intToString(infoReportPeriod_Alarm) + "\n" + 
			   "远程服务与管理平台域名长度 : " + lengthOfServiceAndManagePlatform + "\n" +
			   "远程服务与管理平台域名 : " + byteArrayToString(domainNameOfRSMP) + "\n" +
			   "远程服务与管理平台端口 : " + intToString(portOfRSMP) + "\n" +
			   "硬件版本 : " + hardwareVersion + "\n" + 
			   "固件版本 : " + firmwareVersion + "\n" +
			   "车载终端心跳发送周期 : " + shortToString(heartBeatPeriod) + "\n" + 
			   "终端应答超时时间 : " + intToString(terminalResponseTimeout) + "\n" + 
			   "平台应答超时时间 : " + intToString(platformResponseTimeout) + "\n" + 
			   "连续三次登入失败后，到下一次登入的间隔时间 : " + shortToString(loginIntervalTime) + "\n" +
			   "公共平台域名长度 : " + lengthOfPublicPlatform + "\n" + 
			   "公共平台域名 :" + byteArrayToString(domainNameOfPublicPlatform) + "\n" + 
			   "公共平台端口 : " + intToString(portOfPublicPlatform) + "\n" + 
			   "是否处于抽样检测中 : " + samplingToString(samplingValue);
	}
	
}
