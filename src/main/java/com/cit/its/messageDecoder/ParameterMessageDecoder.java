package com.cit.its.messageDecoder;

import org.apache.log4j.Logger;

import com.cit.its.messageStruct.CommandType;
import com.cit.its.messageStruct.Parameter;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.Unsigned;

/**
 * 参数查询应答报文和参数设置应答报文格式一样，所以解析函数一样
 * 平台收到这两个报文后，解析后更新数据库
 * @author QingXi
 *
 */

public class ParameterMessageDecoder {
	
	static Logger logger = Logger.getLogger(ParameterMessageDecoder.class);
	
	public static void parameterDecoder(byte[] bytes, String vehicleVIN, String simpleDate) throws Exception {
	
		CommandType commandType = HeaderDecoder.getCommandType(bytes);
		logger.info(vehicleVIN + " receive " + commandType + " message in " + simpleDate);
		
		// 取得参数数目，并验证是否正确，正确则进入相应的处理逻辑，否则丢弃报文
		short parameterNumber = Unsigned.getUnsignedByte(bytes[30]);
		if (parameterNumber == 0xFE) {
			logger.info(vehicleVIN +  commandType + " parameter number is abnormal!");
			return;
		}
		else if (parameterNumber == 0xFF) {
			logger.info(vehicleVIN + commandType + " parameter number is invalid!");
			return;
		}
		else {
			logger.info(vehicleVIN + commandType + " parameter number is valid!");
			// 参数总数在有效值范围内
			// 获取参数项列表（参数项：参数ID，参数值）
			
			Parameter parameterList = new Parameter();
			
			// 车载终端本地存储时间周期,有效值范围：0~60000（表示0ms~60000ms）
			// 最小计量单元1ms，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x01
			int localStorageTimePeriod = -1;
			
			// 正常时，信息上报周期,有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x02
			int infoReportPeriod_Normal = -1;
			
			// 出现报警时，信息上报时间周期,有效值范围：0~60000（表示0ms~60000ms），最小计量单元：1ms
			// “0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x03
			int infoReportPeriod_Alarm = -1;
			
			// 远程服务与管理平台域名长度M
			// 参数ID：0x04
			int lengthOfServiceAndManagePlatform = -1;
			
			// 远程服务与管理平台域名
			// RSMP: Remote Service And Manage Platform
			// 参数ID：0x05
			byte[] domainNameOfRSMP;
			
			// 远程服务与管理平台端口,有效值范围：0~65531，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x06
			int portOfRSMP = -1;
			
			// 硬件版本，车载终端厂商自行定义
			// 参数ID：0x07
			String hardwareVersion = "";
			// 固件版本，车载终端厂商自行定义
			// 参数ID：0x08
			String firmwareVersion = "";
			
			// 车载终端心跳发送周期，有效值范围：1~240（表示1s~240s），最小计量单元：1s，“0xFE”表示异常，“0xFF”表示无效
			// 参数ID：0x09
			short heartBeatPeriod = -1;
			
			// 终端应答超时时间，有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x0A
			int terminalResponseTimeout = -1;
			
			// 平台应答超时时间，有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x0B
			int platformResponseTimeout = -1;
			
			// 连续三次登入失败后，到下一次登入的间隔时间。有效值范围：1~240（表示1min~240min），最小计量单元：1min
			// “0xFE”表示异常，“0xFF”表示无效
			// 参数ID：0x0C
			short loginIntervalTime = -1;
			
			// 公共平台域名长度
			// 参数ID：0x0D
			int lengthOfPublicPlatform = -1;
			// 公共平台域名
			// 参数ID：0x0E
			byte[] domainNameOfPublicPlatform;
			
			// 公共平台端口,有效值范围：0~65531，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
			// 参数ID：0x0F
			int portOfPublicPlatform = -1;
			
			// 是否处于抽样检测中,“0x01”表示是，“0x02”表示否，“0xFE”表示异常，“0xFF”表示无效
			// 参数ID：0x10
			short samplingValue = -1;
			
			// 参数ID：0x11~0x7F 预留
			// 参数ID：0x80~0xFE 用户自定义
			
			int pos = 31;
			for (int i = 0; i < parameterNumber; i++) {
				short parameterID = Unsigned.getUnsignedByte(bytes[pos]);
				pos = pos + 1;
				switch (parameterID) {
				case 0x01: 
					// 车载终端本地存储时间周期，有效值范围：0~60000（表示0ms~60000ms）
					// 最小计量单元1ms，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
					localStorageTimePeriod = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setLocalStorageTimePeriod(localStorageTimePeriod);
					if (localStorageTimePeriod == 0xFFFE) {
						logger.info(vehicleVIN + " terminal local storage time period is abnormal!");
					}
					else if (localStorageTimePeriod == 0xFFFF) {
						logger.info(vehicleVIN + " terminal local storage time period is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal local storage time period is " + localStorageTimePeriod + " ms");
						
					}
					pos = pos + 2;
					break;
				case 0x02:
					// 正常时，信息上报周期，有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
					infoReportPeriod_Normal = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setInfoReportPeriod_Normal(infoReportPeriod_Normal);
					if (infoReportPeriod_Normal == 0xFFFE) {
						logger.info(vehicleVIN + " terminal information report period of normal situation is abnormal!");
					}
					else if (infoReportPeriod_Normal == 0xFFFF) {
						logger.info(vehicleVIN + " terminal information report period of normal situation is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal information report period of normal situation is " + infoReportPeriod_Normal + " s");
					}
					pos = pos + 2;
					break;
				case 0x03:
					// 出现报警时，信息上报时间周期，有效值范围：0~60000（表示0ms~60000ms），最小计量单元：1ms
					// “0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
					infoReportPeriod_Alarm = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setInfoReportPeriod_Alarm(infoReportPeriod_Alarm);
					if (infoReportPeriod_Alarm == 0xFFFE) {
						logger.info(vehicleVIN + " terminal information report period of alarm situation is abnormal!");
					}
					else if (infoReportPeriod_Alarm == 0xFFFF) {
						logger.info(vehicleVIN + " terminal information report period of alarm situation is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal information report period of alarm situation is " + infoReportPeriod_Alarm + " ms");
				
					}
					pos = pos + 2;
					break;
				case 0x04:
					lengthOfServiceAndManagePlatform = (int) Unsigned.getUnsignedShort(bytes[pos]);
					parameterList.setLengthOfServiceAndManagePlatform(lengthOfServiceAndManagePlatform);
					if (lengthOfServiceAndManagePlatform > 0) {
						logger.info(vehicleVIN + " length of service and manage platform is " + lengthOfServiceAndManagePlatform);
					}
					else {
						logger.info(vehicleVIN + " length of service and manage platform is " + lengthOfServiceAndManagePlatform
								+ ", and it's abnormal!");
					}
					pos = pos + 1;
					break;
				case 0x05:
					if (lengthOfServiceAndManagePlatform >= 0) {
						domainNameOfRSMP = new byte[lengthOfServiceAndManagePlatform];
						for (int j = 0; j < lengthOfServiceAndManagePlatform; j++) {
							domainNameOfRSMP[j] = bytes[pos + j];
						}
						parameterList.setDomainNameOfRSMP(domainNameOfRSMP);
						String domainName = new String(domainNameOfRSMP, "utf-8");
						logger.info(vehicleVIN + " its remote service and management platform domain name is " + domainName);
						pos = pos + lengthOfServiceAndManagePlatform;
					}
					else {
						logger.info(vehicleVIN + " its remote service and management platform domain name parameter is wrong!");
					}
					break;
				case 0x06:
					portOfRSMP = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setPortOfRSMP(portOfRSMP);
					if (portOfRSMP == 0xFFFE) {
						logger.info(vehicleVIN + " port of remote service and manage platform is " + portOfRSMP
								+ "! And its abnormal!");
					}
					else if (portOfRSMP == 0xFFFF) {
						logger.info(vehicleVIN + " port of remote service and manage platform is " + portOfRSMP
								+ "! And its invalid!");
					}
					else {
						logger.info(vehicleVIN + " port of remote service and manage platform is " + portOfRSMP + "!");
					}
					pos = pos + 2;
					break;
				case 0x07:
					hardwareVersion = ByteUtil.getInstance().getStringFromByteArray(bytes, pos, 5);
					parameterList.setHardwareVersion(hardwareVersion);
					logger.info(vehicleVIN + " hardware version is " + hardwareVersion);
					pos = pos + 5;
					break;
				case 0x08:
					firmwareVersion = ByteUtil.getInstance().getStringFromByteArray(bytes, pos, 5);
					parameterList.setFirmwareVersion(firmwareVersion);
					logger.info(vehicleVIN + " firmware version is " + firmwareVersion);
					pos = pos + 5;
					break;
				case 0x09:
					heartBeatPeriod = Unsigned.getUnsignedByte(bytes[pos]);
					parameterList.setHeartBeatPeriod(heartBeatPeriod);
					logger.info(vehicleVIN + " heart beat period is " + heartBeatPeriod + "s");
					pos = pos + 1;
					break;
				case 0x0A:
					terminalResponseTimeout = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setTerminalResponseTimeout(terminalResponseTimeout);
					if (terminalResponseTimeout == 0xFFFE) {
						logger.info(vehicleVIN + " terminal response timeout is abnormal!");
					}
					else if (terminalResponseTimeout == 0xFFFF) {
						logger.info(vehicleVIN + " terminal response timeout is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal response timeout is " + terminalResponseTimeout + "s");
					}
					pos = pos + 2;
					break;
				case 0x0B:
					platformResponseTimeout = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setPlatformResponseTimeout(platformResponseTimeout);
					if (platformResponseTimeout == 0xFFFE) {
						logger.info(vehicleVIN + " platform response timeout is abnormal!");
					}
					else if (platformResponseTimeout == 0xFFFF) {
						logger.info(vehicleVIN + " platform response timeout is invalid!");
					}
					else {
						logger.info(vehicleVIN + " platform response timeout is " + platformResponseTimeout + "s");
					}
					pos = pos + 2;
					break;
				case 0x0C:
					loginIntervalTime = Unsigned.getUnsignedByte(bytes[pos]);
					parameterList.setLoginIntervalTime(loginIntervalTime);
					if (loginIntervalTime == 0xFE) {
						logger.info(vehicleVIN + " login interval time is abnormal!"); 
					}
					else if (loginIntervalTime == 0xFF) {
						logger.info(vehicleVIN + " login interval time is invalid!");
					}
					else {
						logger.info(vehicleVIN + " login interval time is " + loginIntervalTime + "s");
					}
					pos = pos + 1;
					break;
				case 0x0D:
					lengthOfPublicPlatform = (int) Unsigned.getUnsignedShort(bytes[pos]);
					parameterList.setLengthOfPublicPlatform(lengthOfPublicPlatform);
					logger.info(vehicleVIN + " domain name's length of public platform is " + lengthOfPublicPlatform);
					pos = pos + 1;
					break;
				case 0x0E:
					if (lengthOfPublicPlatform >= 0) {
						domainNameOfPublicPlatform = new byte[lengthOfPublicPlatform];
						for (int k = 0; k < lengthOfPublicPlatform; k++) {
							domainNameOfPublicPlatform[k] = bytes[pos + k];
						}
						parameterList.setDomainNameOfPublicPlatform(domainNameOfPublicPlatform);
						String domainNameOfPP = new String(domainNameOfPublicPlatform, "utf-8");
						logger.info(vehicleVIN + " domain name of public platform is " + domainNameOfPP);
						pos = pos + lengthOfPublicPlatform;
					}
					else {
						logger.info(vehicleVIN + " domain name of public platform parameter is wrong! ");
					}
					break;
				case 0x0F:
					portOfPublicPlatform = Unsigned.getUnsignedShort(ByteUtil.getInstance().getShort(bytes, pos));
					parameterList.setPortOfPublicPlatform(portOfPublicPlatform);
					if (portOfPublicPlatform == 0xFFFE) {
						logger.info(vehicleVIN + " port of public platform is abnormal!");
					}
					else if (portOfPublicPlatform == 0xFFFF) {
						logger.info(vehicleVIN + " port of public platform is invalid!");
					}
					else {
						logger.info(vehicleVIN + " port of public platform is " + portOfPublicPlatform);
					}
					pos = pos + 2;
					break;
				case 0x10:
					samplingValue = Unsigned.getUnsignedByte(bytes[pos]);
					parameterList.setSamplingValue(samplingValue);
					if (samplingValue == 0xFE) {
						logger.info(vehicleVIN + " sampling value is abnormal!");
					}
					else if (samplingValue == 0xFF) {
						logger.info(vehicleVIN + " sampling value is invalid!");
					}
					else if (samplingValue == 0x01) {
						logger.info(vehicleVIN + " sampling value is " + samplingValue
								+ ". And its in sampling!");
					}
					else if (samplingValue == 0x02) {
						logger.info(vehicleVIN + " sampling value is " + samplingValue
								+ ". And its not in sampling!");
					}
					pos = pos + 1;
					break;
				default:
					logger.info(vehicleVIN + "'s parameter is invalid!");
					break;
				}
			}
			
			System.out.println(parameterList);
			// 更新数据库
			
		}
	}

}
