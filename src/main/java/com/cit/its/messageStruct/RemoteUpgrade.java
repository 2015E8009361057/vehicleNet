package com.cit.its.messageStruct;

public class RemoteUpgrade {
	
	private String addOfUpgradeURL;					// 升级URL地址
	
	private String dailPointName;					// 拨号点名称
	
	private String dailUserName;					// 拨号用户名
	
	private String dailPassword;					// 拨号密码
	
	private String address;							// 地址
	
	private int port;								// 端口
	
	private String terminalManufacturerID;			// 车载终端制造商ID
	
	private String hardwareVersion;					// 硬件版本
	
	private String firmwareVersion;					// 固件版本
	
	private int timeLimitOfConToUpgradeServer;		// 连接到升级服务器时限
	
	
	
	// set, get方法
	public void setAddOfUpgradeURL(String addOfUpgradeURL) {
		this.addOfUpgradeURL = addOfUpgradeURL;
	}
	
	public String getAddOfUpgradeURL() {
		return addOfUpgradeURL;
	}
	
	public void setDailPointName(String dailPointName) {
		this.dailPointName = dailPointName;
	}
	
	public String getDailPointName() {
		return dailPointName;
	}
	
	public void setDailUserName(String dailUserName) {
		this.dailUserName = dailUserName;
	}
	
	public String getDailUserName() {
		return dailUserName;
	}
	
	public void setDailPassword(String dailPassword) {
		this.dailPassword = dailPassword;
	}
	
	public String getDailPassword() {
		return dailPassword;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setTerminalManufacturerID(String terminalManufacturerID) {
		this.terminalManufacturerID = terminalManufacturerID;
	}
	
	public String getTerminalManufacturerID() {
		return terminalManufacturerID;
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
	
	public void setTimeLimitOfConToUpgradeServer(int timeLimitOfConToUpgradeServer) {
		this.timeLimitOfConToUpgradeServer = timeLimitOfConToUpgradeServer;
	}
	
	public int getTimeLimitOfConToUpgradeServer() {
		return timeLimitOfConToUpgradeServer;
	}
	
	
	// 获取String类型变量
	public String getStringVariable() {
		return addOfUpgradeURL + ";" + dailPointName + ";" + dailUserName + ";" + dailPassword + ";" + 
			   address + ";" + port + ";" + terminalManufacturerID + ";" + hardwareVersion + ";" + 
			   firmwareVersion + ";" + timeLimitOfConToUpgradeServer;
	}
	
	
	
	// 重写toString()方法
	public String toString() {
		return "升级URL地址 : " + addOfUpgradeURL + "\n" + 
			   "拨号点名称 : " + dailPointName + "\n" + 
			   "拨号用户名 : " + dailUserName + "\n" + 
			   "拨号密码 : " + dailPassword + "\n" + 
			   "地址 : " + address + "\n" + 
			   "端口 : " + port + "\n" + 
			   "车载终端制造商ID : " + terminalManufacturerID + "\n" + 
			   "硬件版本 : " + hardwareVersion + "\n" +
			   "固件版本 : " + firmwareVersion + "\n" + 
			   "连接到升级服务器时限 : " + timeLimitOfConToUpgradeServer;
	}

}
