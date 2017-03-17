package com.cit.its.messageEncoder;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageStruct.Parameter;
import com.cit.its.util.ByteUtil;

public class ParameterSetMessageEncoder {
	
	static Logger logger = Logger.getLogger(ParameterSetMessageEncoder.class);
	
	public static byte[] parameterSetEncoder(String vehicleVIN, short totalNumOfPara, Parameter parameter) throws ParseException {
		// 校验参数总数是否有效
		if (totalNumOfPara == 0xFE) {
			logger.info(vehicleVIN + " parameter set message encode failed because of the total number of parameter is abnormal!");
			return null;
		}
		else if (totalNumOfPara == 0xFF) {
			logger.info(vehicleVIN + " parameter set message encode failed because of the total number of parameter is invalid!");
			return null;
		}
		
		// 通过格式要求计算可知，参数项列表长度最大为291字节
		byte[] dataTemp = new byte[292];
		int tmpPos = 0;
		// 计算数据单元长度
		
		short totalNumOfParameter = 0;
		
		// 下面为 将参数项列表放入dataTemp数组
		// 处理逻辑：逐个判断parameter的各个参数是否被改动，若被改动，则与初始值不一样
		// 被改动的个数  == 参数总数
		if (parameter.getLocalStorageTimePeriod() >= 0) {
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getLocalStorageTimePeriod_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short)(parameter.getLocalStorageTimePeriod()), tmpPos);
			tmpPos = tmpPos + 2;
		}
		if (parameter.getInfoReportPeriod_Normal() >= 1) {
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getInfoReportPeriod_Normal_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short)(parameter.getInfoReportPeriod_Normal()), tmpPos);
			tmpPos = tmpPos + 2;
		}
		if (parameter.getInfoReportPeriod_Alarm() >= 0) {
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getInfoReportPeriod_Alarm_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short) (parameter.getInfoReportPeriod_Alarm()), tmpPos);
			tmpPos = tmpPos + 2;
		}
		if (parameter.getLengthOfServiceAndManagePlatform() > 0) {
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getLengthOfServiceAndManagePlatform_ID();
			tmpPos++;
			dataTemp[tmpPos] = (byte) (parameter.getLengthOfServiceAndManagePlatform());
			tmpPos = tmpPos + 1;
		}
		if (parameter.getDomainNameOfRSMP().length > 0) {
			byte[] domainName = parameter.getDomainNameOfRSMP();
			int len = domainName.length;
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getDomainNameOfRSMP_ID();
			tmpPos++;
			for (int i = 0; i < len; i++) {
				dataTemp[tmpPos] = domainName[i];
				tmpPos++;
			}
		}
		if (parameter.getPortOfRSMP() >= 0) {
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getPortOfRSMP_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short) (parameter.getPortOfRSMP()), tmpPos);
			tmpPos = tmpPos + 2;
		}

		/*
		// 其实，硬件版本和固件版本可以不做解析，因为参数设置的参数项定义中不包含硬件版本和固件版本
		if (!parameter.getHardwareVersion().equals("")) {
			dataLength = dataLength + 5;
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getHardwareVersion_ID();
			tmpPos++;
			byte[] temp = parameter.getHardwareVersion().getBytes();
			for (int i = 0; i < 5; i++) {
				dataTemp[tmpPos] = temp[i];
				tmpPos++;
			}
		}
		if (!parameter.getFirmwareVersion().equals("")) {
			dataLength = dataLength + 5;
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getFirmwareVersion_ID();
			tmpPos++;
			byte[] temp = parameter.getFirmwareVersion().getBytes();
			for (int i = 0; i < 5; i++) {
				dataTemp[tmpPos] = temp[i];
				tmpPos++;
			}
		}
		*/
		if (parameter.getHeartBeatPeriod() > 0) {
			
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getHeartBeatPeriod_ID();
			tmpPos++;
			dataTemp[tmpPos] = (byte) (parameter.getHeartBeatPeriod());
			tmpPos = tmpPos + 1;
		}
		
		if (parameter.getTerminalResponseTimeout() > 0) {
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getTerminalResponseTimeout_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short) (parameter.getTerminalResponseTimeout()), tmpPos);
			tmpPos = tmpPos + 2;
		}
		
		if (parameter.getPlatformResponseTimeout() > 0) {
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getPlatformResponseTimeout_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short) (parameter.getPlatformResponseTimeout()), tmpPos);
			tmpPos = tmpPos + 2;
		}
		
		if (parameter.getLoginIntervalTime() > 0) {
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getLoginIntervalTime_ID();
			tmpPos++;
			dataTemp[tmpPos] = (byte) (parameter.getLoginIntervalTime());
			tmpPos = tmpPos + 1;
		}
		
		if (parameter.getLengthOfPublicPlatform() > 0) {
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getLengthOfPublicPlatform_ID();
			tmpPos++;
			dataTemp[tmpPos] = (byte) (parameter.getLengthOfPublicPlatform());
			tmpPos = tmpPos + 1;
		}
		
		if (parameter.getDomainNameOfPublicPlatform().length > 0) {
			totalNumOfParameter++;
			byte[] temp = parameter.getDomainNameOfPublicPlatform();
			int len = temp.length;
			dataTemp[tmpPos] = parameter.getDomainNameOfPublicPlatform_ID();
			tmpPos++;
			for (int i = 0; i < len; i++) {
				dataTemp[tmpPos] = temp[i];
				tmpPos++;
			}
		}
		
		if (parameter.getPortOfPublicPlatform() > 0) {
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getPortOfPublicPlatform_ID();
			tmpPos++;
			ByteUtil.getInstance().putShort(dataTemp, (short) (parameter.getPortOfPublicPlatform()), tmpPos);
			tmpPos = tmpPos + 2;
		}
		
		if (parameter.getSamplingValue() != -1) {
			totalNumOfParameter++;
			dataTemp[tmpPos] = parameter.getSamplingValue_ID();
			tmpPos++;
			dataTemp[tmpPos] = (byte) (parameter.getSamplingValue());
			tmpPos++;
		}
		
		// 检验是否与传入的参数总数一致
		if (totalNumOfParameter == totalNumOfPara) {
			logger.info(vehicleVIN + " total number of parameter is correct!");
		}
		else {
			logger.info(vehicleVIN + " total number of parameter is wrong!");
		}
		
		// 数据单元部分长度应等于当前的tmpPos值加7
		int dataLength = tmpPos + 7;
		// 设置当前命令类型为 参数设置
		short commandIdentifier = (short) 0x81;
		byte[] header = HeaderEncoder.getByteArrayHeader(vehicleVIN, commandIdentifier, dataLength);
		
		
		// 编码后的总长度为 头部长度+数据单元长度+1字节校验码
		byte[] result = new byte[header.length + dataLength + 1];
		
		int pos = 0;
		// 将编码后的header放入结果数组
		for (int i = 0; i < header.length; i++) {
			result[pos] = header[i];
			pos++;
		}
		
		// 将参数设置时间编码放入结果数组
		Date parameterSetTime = new Date();
		ByteUtil.getInstance().putDateToByteArray(parameterSetTime, result, pos);
		pos = pos + 6;
		
		// 将参数总数放入结果数组
		result[pos] = (byte) (totalNumOfPara);
		pos = pos + 1;
		
		// 将编码后的数据单元（除了参数设置时间和参数总数）放入结果数组
		for (int j = 0; j < tmpPos; j++) {
			result[pos] = dataTemp[j];
			pos++;
		}
		
		// 此时，pos值应等于result.length-1
		if (pos == result.length - 1) {
			logger.info(vehicleVIN + " parameter set data encode succeed!");
		}
		else {
			logger.info(vehicleVIN + " parameter set data encode failed!");
		}
		
		// 最后计算校验码，将校验码放入数组
		byte checkCode = CheckCode.calculateCheckCode(result);
		result[result.length - 1] = checkCode;
		
		logger.info(vehicleVIN + " parameter set message encode succeed!");
		
		return result;
	}

}
