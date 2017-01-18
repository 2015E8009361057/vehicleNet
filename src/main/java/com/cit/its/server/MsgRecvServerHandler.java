/**
 * MsgRecvServerHandler 对收到的数据包的处理逻辑：
 * 1. 异或校验（校验码校验），若通过则转2；若没有，如果是实时信息上报和补发信息上报报文，则转2；如果不是，则直接丢弃；
 * 2. 身份校验，根据不同的报文，校验的类型不同。如果是车辆登入报文，则查询两个表——车辆静态信息表，和车辆最新状态表；
 * 									若车辆静态信息表中有该车辆，及车辆最新状态表中该车辆为未登入状态，则验证通过，转3；
 * 									如果是其他报文，则查询车辆最新状态表，若车辆未登入状态，则验证通过，转3；
 * 3. 数据加解密，根据不同的加解密方法，对数据单元部分进行加解密；转4；
 * 4. 解析数据，根据不同的报文，进行相应的解析；
 * 5. 更新数据库，根据不同的报文，更新相应的数据表。
 */


/**  
 * @Title: MsgRecvServerHandler.java 
 * @Package Server 
 * @Description Handle messages
 * @author QingXi 
 * @date 2016年12月30日
 */

package com.cit.its.server;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageDecoder.DataDecrypt;
import com.cit.its.messageDecoder.HeaderDecoder;
import com.cit.its.messageDecoder.IdentityVerification;
import com.cit.its.messageDecoder.RealInfoDecoder;
import com.cit.its.messageStruct.CommandType;
import com.cit.its.messageStruct.DataEncryModeType;
import com.cit.its.messageStruct.RealInformationType;
import com.cit.its.messageStruct.ResponseType;
import com.cit.its.util.ByteBuf2ByteArray;
import com.cit.its.util.ByteUtil;
import com.cit.its.util.Unsigned;

/**
 * @Title: MsgRecvServerHandler
 * @Description: 获取链接后读取数据处理以及异常处理
 * @author QingXi
 * @date 2016年12月30日
 */

public class MsgRecvServerHandler extends ChannelInboundHandlerAdapter {

	
	Logger logger = Logger.getLogger(MsgRecvServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws NumberFormatException, Exception {
		byte[] bytes = ByteBuf2ByteArray.ByteBufToBA(msg);
		CommandType commandType = HeaderDecoder.getCommandType(bytes);
		String vehicleVIN = HeaderDecoder.getVehicleVIN(bytes);
		logger.info(vehicleVIN + " msg_type is : " + commandType);
		
		// 平台对收到的每一个报文都会先进行校验码校验。校验正确，进入相应的处理函数；
		// 校验错误，直接丢弃，除了实时信息上报报文，如果是实时信息上报报文或者补发信息报文，
		// 则先进行身份校验，验证通过，发送错误应答；验证不通过，直接丢弃
		if (!CheckCode.verifyCheckCode(bytes, commandType, vehicleVIN)) {
			// 是实时信息上报报文或者补发信息上报报文
			if (commandType.equals(CommandType.REAL_INFORMATION_UPLOAD) || 
					commandType.equals(CommandType.REISSUED_INFORMATION_UPLOAD)) {
				// 身份验证通过
				if (IdentityVerification.identityVerify(commandType, vehicleVIN)) {
					byte responseType = 0x02;
					sendResponseMessage(ctx, bytes, responseType);
				}
			}
			return;	
		}
		
		// 校验码校验通过，进行身份验证
		// 身份验证未通过
		if (!IdentityVerification.identityVerify(commandType, vehicleVIN)) {
			return;
		}
		
		// 校验码校验及身份校验都通过
		// 获取加密字段，看数据单元bytes[24~bytes.length-2]是否进行了加密
		DataEncryModeType dataEncryModeType = HeaderDecoder.getDataEncryModeType(bytes);
		logger.info(vehicleVIN + "its data cell encryption methods used " + dataEncryModeType);
		// 若数据字段为异常或者无效，则直接丢弃数据包
		if (dataEncryModeType.equals(DataEncryModeType.EXCEPTION_ENCRY) ||
				dataEncryModeType.equals(DataEncryModeType.INVALID_ENCRY)) {
			return;
		}
		// 否则，运用相应的解密算法对数据单元部分进行解密
		DataDecrypt.Decrypt(bytes, dataEncryModeType);
		
		// 解密完成，根据相应的报文类型，进行相应的数据解析
		switch(commandType) {
		case VEHICLE_LOGIN:
			vehicleLoginHandler(ctx, bytes, vehicleVIN);
			break;
		case REAL_INFORMATION_UPLOAD:
			realInfoUploadHandler(ctx, bytes, vehicleVIN);
			break;
		case REISSUED_INFORMATION_UPLOAD:
			realInfoUploadHandler(ctx, bytes, vehicleVIN);
			break;
		case VEHICLE_LOGOUT:
			vehicleLogoutHandler(ctx, bytes, vehicleVIN);
			break;
		case PLATFORM_LOGIN:
			// 先暂时不写
//			platformLoginHandler(ctx, bytes);
			break;
		case PLATFORM_LOGOUT:
			// 先暂时不写
//			platformLogoutHandler(ctx, bytes);
			break;
		case HEART_BEAT:
			heartBeatHandler(ctx, bytes, vehicleVIN);
			break;
		case TERMINAL_CORRECTION:
			terminalCorrectionHandler(ctx, bytes, vehicleVIN);
			break;
		case QUERY_COMMAND:
			queryCommandHandler(ctx, bytes, vehicleVIN);
			break;
		case SET_COMMAND:
			setCommandHandler(ctx, bytes, vehicleVIN);
			break;
		case TERMINAL_CONTROL_COMMAND:
			terminalControlCommandHandler(ctx, bytes, vehicleVIN);
			break;
		default:
			break;
		}
	}
	
	
	private void terminalControlCommandHandler(ChannelHandlerContext ctx,
			byte[] bytes, String vehicleVIN) {		
		
		// 取得应答标志
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		// 平台向终端发送设置命令，收到设置命令的应答报文，故应答标志应该为0x01，表明此报文为无错的应答报文
		if (responseType.value() != 0x01) {
			// 说明报文有误
			logger.info(vehicleVIN + " terminal control command response type is not correct!");
			return;
		}
		// 报文正确
		logger.info(vehicleVIN + " terminal control command response type is correct!");		
		
	}


	private void setCommandHandler(ChannelHandlerContext ctx, byte[] bytes,
			String vehicleVIN) {
		
		// 取得应答标志
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		// 平台向终端发送设置命令，收到设置命令的应答报文，故应答标志应该为0x01，表明此报文为无错的应答报文
		if (responseType.value() != 0x01) {
			// 说明报文有误
			logger.info(vehicleVIN + " set command response type is not correct!");
			return;
		}
		// 报文正确
		logger.info(vehicleVIN + " set command response type is correct!");
		
		// 此时，更新数据库
		logger.info(vehicleVIN + " parameter is already update!");
		
	}


	private void queryCommandHandler(ChannelHandlerContext ctx, byte[] bytes,
			String vehicleVIN) throws Exception {
		
		// 取得应答标志
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		// 因为平台收到的是查询命令的应答报文，故应答标志应该为0x01，表明该报文为应答报文
		if (responseType.value() != 0x01) {
			// 说明该报文有误
			logger.info(vehicleVIN + " query command response type is not correct!");
			return;
		}
		// 报文正确
		// 解析报文
		// 取得返回查询参数时间
		String simpleDate = ByteUtil.getStringDateFromByteArray(bytes, 24);
		short parameterNumber = Unsigned.getUnsignedByte(bytes[30]);
		if (parameterNumber == 0xFE) {
			logger.info(vehicleVIN + " query command parameter number is abnormal!");
			return;
		}
		else if (parameterNumber == 0xFF) {
			logger.info(vehicleVIN + " query command parameter number is invalid!");
			return;
		}
		else {
			logger.info(vehicleVIN + " query command parameter number is valid!");
			// 参数总数在有效值范围内
			// 获取参数项列表（参数项：参数ID，参数值）
			
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
					localStorageTimePeriod = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
					if (localStorageTimePeriod == 0xFFFE) {
						logger.info(vehicleVIN + " terminal local storage time period is abnormal!");
					}
					else if (localStorageTimePeriod == 0xFFFF) {
						logger.info(vehicleVIN + " terminal local storage time period is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal local storage time period is " + localStorageTimePeriod + " ms");
						// 再做其他事情，例如写入数据库
						
					}
					pos = pos + 2;
					break;
				case 0x02:
					// 正常时，信息上报周期，有效值范围：1~600（表示1s~600s），最小计量单元：1s，“0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
					infoReportPeriod_Normal = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
					if (infoReportPeriod_Normal == 0xFFFE) {
						logger.info(vehicleVIN + " terminal information report period of normal situation is abnormal!");
					}
					else if (infoReportPeriod_Normal == 0xFFFF) {
						logger.info(vehicleVIN + " terminal information report period of normal situation is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal information report period of normal situation is " + infoReportPeriod_Normal + " s");
						// 写入数据库？
						
					}
					pos = pos + 2;
					break;
				case 0x03:
					// 出现报警时，信息上报时间周期，有效值范围：0~60000（表示0ms~60000ms），最小计量单元：1ms
					// “0xFF，0xFE”表示异常，“0xFF，0xFF”表示无效
					infoReportPeriod_Alarm = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
					if (infoReportPeriod_Alarm == 0xFFFE) {
						logger.info(vehicleVIN + " terminal information report period of alarm situation is abnormal!");
					}
					else if (infoReportPeriod_Alarm == 0xFFFF) {
						logger.info(vehicleVIN + " terminal information report period of alarm situation is invalid!");
					}
					else {
						logger.info(vehicleVIN + " terminal information report period of alarm situation is " + infoReportPeriod_Alarm + " ms");
						// 写入数据库？
						
					}
					pos = pos + 2;
					break;
				case 0x04:
					lengthOfServiceAndManagePlatform = (int) Unsigned.getUnsignedShort(bytes[pos]);
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
						String domainName = new String(domainNameOfRSMP, "utf-8");
						logger.info(vehicleVIN + " its remote service and management platform domain name is " + domainName);
						
						pos = pos + lengthOfServiceAndManagePlatform;
					}
					break;
				case 0x06:
					portOfRSMP = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
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
					hardwareVersion = ByteUtil.getStringFromByteArray(bytes, pos, 5);
					logger.info(vehicleVIN + " hardware version is " + hardwareVersion);
					pos = pos + 5;
					break;
				case 0x08:
					firmwareVersion = ByteUtil.getStringFromByteArray(bytes, pos, 5);
					logger.info(vehicleVIN + " firmware version is " + firmwareVersion);
					pos = pos + 5;
					break;
				case 0x09:
					heartBeatPeriod = Unsigned.getUnsignedByte(bytes[pos]);
					logger.info(vehicleVIN + " heart beat period is " + heartBeatPeriod + "s");
					pos = pos + 1;
					break;
				case 0x0A:
					terminalResponseTimeout = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
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
					platformResponseTimeout = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
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
					logger.info(vehicleVIN + " domain name's length of public platform is " + lengthOfPublicPlatform);
					pos = pos + 1;
					break;
				case 0x0E:
					if (lengthOfPublicPlatform >= 0) {
						domainNameOfPublicPlatform = new byte[lengthOfPublicPlatform];
						for (int k = 0; k < lengthOfPublicPlatform; k++) {
							domainNameOfPublicPlatform[k] = bytes[pos + k];
						}
						String domainNameOfPP = new String(domainNameOfPublicPlatform, "utf-8");
						logger.info(vehicleVIN + " domain name of public platform is " + domainNameOfPP);
						pos = pos + lengthOfPublicPlatform;
					}
					break;
				case 0x0F:
					portOfPublicPlatform = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, pos));
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
					break;
				}
			}
			// 更新数据库
			
			logger.info(vehicleVIN + " receive query command message in " + simpleDate);
		}
		
	}


	private void terminalCorrectionHandler(ChannelHandlerContext ctx,
			byte[] bytes, String vehicleVIN) throws ParseException {
		
		// 取得应答标志
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		if (responseType.value() != 0xFE) {
			// 说明报文有误
			logger.info(vehicleVIN + " heart beat message responseType is not correct!");
			return;
		}
		// 应答标志正常
		logger.info(vehicleVIN + " heart beat message responseType is correct!");
		
		byte responseTypeBack = 0x01;
		bytes[22] = (byte) 6;
		// 因为终端校时报文中没有时间，也就是数据单元为空
		// 所以返回的时候加上时间，并将数据单元的值设为6
		byte[] newBytes = new byte[bytes.length + 6];
		for (int i = 0; i < bytes.length; i++) {
			newBytes[i] = bytes[i];
		}
		sendResponseMessage(ctx, newBytes, responseTypeBack);
	}


	private void heartBeatHandler(ChannelHandlerContext ctx, byte[] bytes,
			String vehicleVIN) {
		
		// 取得应答标志
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		if (responseType.value() != 0xFE) {
			// 说明报文有误
			logger.info(vehicleVIN + " heart beat message responseType is not correct!");
			return;
		}
		// 应答标志正常
		logger.info(vehicleVIN + " heart beat message responseType is correct!");
		
		// 更改应答标志
		byte responType = 0x01;
		bytes[3] = responType;
		// 计算校验位
		byte checkCode = CheckCode.calculateCheckCode(bytes);
		bytes[bytes.length-1] = checkCode;
		// 发送心跳应答消息
		ByteBuf responMessage = Unpooled.buffer(bytes.length);
		responMessage.writeBytes(bytes);
		ctx.writeAndFlush(responMessage);
	}


	// 实时信息上报处理，与补发信息上报处理一样
	// 校验正确时，平台不做应答
	// 校验错误时，平台做错误应答
	private void realInfoUploadHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws ParseException {
		// 实时信息上报数据格式
		// 数据采集时间(byte[6])、信息类型标志（1）(byte)、信息体（1）......信息类型标志（n）(byte)、信息体（n）
		// 信息体：根据信息类型不同，长度和数据类型不同
		// 获取上报报文时间
		String date = ByteUtil.getStringDateFromByteArray(bytes, 24);	
		int pos = 30;
		while (pos < bytes.length) {
			short realInfoValue = Unsigned.getUnsignedByte(bytes[pos]);
			RealInformationType realInfoType = RealInformationType.getRealInformationTypeByValue(realInfoValue);
			pos = pos + 1;
			switch (realInfoType) {
			case VEHICLE_INFO:
				int lenOfVeInfo = RealInfoDecoder.vehicleInfoHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfVeInfo;
				break;
			case DRIVING_MOTOR_DATA:
				int lenOfDriMoto = RealInfoDecoder.drivingMotorDataHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfDriMoto;
				break;
			case FUEL_CELL_DATA:
				// 函数返回值为int，就是报文的长度
				int lenOfFuelCell = RealInfoDecoder.fuelCellDataHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfFuelCell;
				break;
			case ENGINE_DATA:
				int lenOfEngineData = RealInfoDecoder.engineDateHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfEngineData;
				break;
			case VEHICLE_POSITION:
				int lenOfVehiclePosition = RealInfoDecoder.vehiclePositionHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfVehiclePosition;
				break;
			case EXTREME_VALUE_DATA:
				int lenOfExtremeValueData = RealInfoDecoder.extremeValueDataHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfExtremeValueData;
				break;
			}
		}
		
	}
	
	

	private void vehicleLoginHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws Exception {
		
		// 处理车辆登入消息
		// 获取应答标志，若不符合，直接丢弃该数据包
		ResponseType responseType = HeaderDecoder.getResponseType(bytes);
		if (responseType.value() != 0xFE){
			logger.info("Response Type is not correct!");
			System.out.println("Response Type is not correct!");
			return;
		}

		// 校验正确，进行身份验证，查询数据库，看数据库中是否含有该车辆
		// 写个返回类型为 boolean的函数
		// 现在先假装已通过数据库身份验证
		logger.info(vehicleVIN + " identified succeed!");
		System.out.println(vehicleVIN + " identified succeed!");
		// 查询该车辆是否已登录
		// 写个返回类型为boolean的函数
		// 先假装之前该车辆未登陆
		boolean login = false;
		if (login) {
			logger.info(vehicleVIN + " is already login!");
			System.out.println(vehicleVIN + " is already login!");
		}
		else {
			// 更新数据库
			logger.info(vehicleVIN + " login succeed!");
			System.out.println(vehicleVIN + " login succeed!");
		}
		
		
		
		// 对数据单元进行解析
		// 获取上线时间
		String simpleDate = ByteUtil.getStringDateFromByteArray(bytes, 24);
//		Date dateCollectionTime = DateFormat.getDateInstance().parse(simpleDate);
		// 获取登入流水号
		short loginNumber = ByteUtil.getShort(bytes, 30);
		int loginSerialNumber = Unsigned.getUnsignedShort(loginNumber);
		// 获取SIM卡ICCID号
		String SIMCardICCIDNumber = ByteUtil.getStringFromByteArray(bytes, 32, 20);
		// 获取可充电储能子系统数
		short numberOfRESS = Unsigned.getUnsignedByte(bytes[52]);
		byte codeLengthOfRESS = bytes[53];
		int lengthOfRESS = numberOfRESS * codeLengthOfRESS;
		String codeForRESS = ByteUtil.getStringFromByteArray(bytes, 54, lengthOfRESS);
		
		// 将车辆信息写入数据库
		System.out.println("车辆VIN码：" + vehicleVIN);
		System.out.println("上线时间：" + simpleDate);
		System.out.println("登入流水号：" + loginSerialNumber);
		System.out.println("SIM卡ICCID号：" + SIMCardICCIDNumber);
		System.out.println("可充电储能子系统数：" + numberOfRESS);
		System.out.println("可充电储能系统编码长度：" + lengthOfRESS);
		System.out.println("可充电储能系统编码：" + codeForRESS);
		
		
		// 向客户端发送成功应答
		// 只需修改原报文中的应答标志，应答报文时间，并重新计算校验位
		byte responseTypeBack = 0x01;
		sendResponseMessage(ctx, bytes, responseTypeBack);
		
	}
	
	private void vehicleLogoutHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) {
		
		// 校验正确
		// 获得登出时间
		String logoutTime = ByteUtil.getStringDateFromByteArray(bytes, 24);
		int logoutSerialNumber = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, 30));
		logger.info(vehicleVIN + "logout in " + logoutTime + "succeed!" + " And its logoutSerialNumber is " + logoutSerialNumber);
		// 更新数据库
		
		System.out.println(vehicleVIN + " logout in " + logoutTime);
		System.out.println("and its logoutSerialNumber is " + logoutSerialNumber);
	}
	
	
	private void sendResponseMessage(ChannelHandlerContext ctx, byte[] bytes, byte responseType) throws ParseException {
		bytes[3] = responseType;
		Date responTime = new Date();
		ByteUtil.putDateToByteArray(responTime, bytes, 24);
		byte checkCode = CheckCode.calculateCheckCode(bytes);
		bytes[bytes.length-1] = checkCode;
		
		ByteBuf responMessage = Unpooled.buffer(bytes.length);
		responMessage.writeBytes(bytes);
		ctx.writeAndFlush(responMessage);
	}

	@Override
	public void channelInactive(final ChannelHandlerContext ctx) {
		logger.info("connect break");
		System.out.println("connnect break");
	
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		logger.warn(cause.getMessage());
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		logger.info("a device online");
	}

}
