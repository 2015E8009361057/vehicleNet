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


import java.text.ParseException;


import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.log4j.Logger;





import com.cit.its.messageDecoder.CheckCode;
import com.cit.its.messageDecoder.DataDecrypt;
import com.cit.its.messageDecoder.HeaderDecoder;
import com.cit.its.messageDecoder.IdentityVerification;
import com.cit.its.messageDecoder.ParameterMessageDecoder;
import com.cit.its.messageDecoder.RealInfoDecoder;
import com.cit.its.messageDecoder.ResponseTypeVerify;
import com.cit.its.messageStruct.CommandType;
import com.cit.its.messageStruct.DataEncryModeType;
import com.cit.its.messageStruct.RealInformationType;
import com.cit.its.messageStruct.VehicleLogin;
import com.cit.its.messageStruct.VehicleLogout;
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
		
		/* 
		 * 平台对收到的每一个报文都会先进行校验码校验。校验正确，进入相应的处理函数；
		 * 校验错误时，如果是实时信息上报报文、补发信息报文、终端校时报文，
		 * 则先进行身份校验，验证通过，发送错误应答；验证不通过，直接丢弃
		 */
		if (!CheckCode.verifyCheckCode(bytes, commandType, vehicleVIN)) {
			// 是实时信息上报报文或者补发信息上报报文
			if (commandType.equals(CommandType.REAL_INFORMATION_UPLOAD) || 
				commandType.equals(CommandType.REISSUED_INFORMATION_UPLOAD) || 
				commandType.equals(CommandType.TERMINAL_CORRECTION)) {
				// 身份验证通过，发送错误应答
				if (IdentityVerification.identityVerify(commandType, vehicleVIN)) {
					byte responseType = 0x02;
					sendResponseMessage(ctx, bytes, responseType);
				}
			}
			return;	
		}
		
		/*
		 * 校验码校验通过后，进行应答标志校验（之所以将应答标志校验放到身份校验前，是想避免 应答标志校验不通过的那部分的数据库查询）
		 * 校验错误时，如果是 实时、补发、终端校时报文，则进行身份验证，身份验证通过发送 错误应答；若是其他报文则丢弃
		 */
		if (!ResponseTypeVerify.verifyResponseType(bytes, commandType, vehicleVIN)) {
			if (commandType.equals(CommandType.REAL_INFORMATION_UPLOAD) || 
				commandType.equals(CommandType.REISSUED_INFORMATION_UPLOAD) || 
				commandType.equals(CommandType.TERMINAL_CORRECTION)) {
					
				// 身份验证通过，发送错误应答
				if (IdentityVerification.identityVerify(commandType, vehicleVIN)) {
					byte responseType = 0x02;
					sendResponseMessage(ctx, bytes, responseType);
				}
			}
			return;	
		} 
		
		
		/*
		 * 应答标志校验通过，进行身份验证，身份验证不通过，直接丢弃报文
		 */
		if (!IdentityVerification.identityVerify(commandType, vehicleVIN)) {
			return;
		}
		
		/*
		 * 异或校验、应答标志校验、身份校验均通过后，获取加密字段，根据相对于的加解密算法对数据
		 * 单元bytes[24~bytes.length-2]解密
		 */
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
	
	
	private void terminalControlCommandHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) {		
		
	
		// 报文正确
		logger.info(vehicleVIN + " terminal control command is succeed!");		
		
	}


	private void setCommandHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws Exception {
		
		String simpleDate = ByteUtil.getStringDateFromByteArray(bytes, 24);

		ParameterMessageDecoder.parameterDecoder(bytes, vehicleVIN, simpleDate);

		logger.info(vehicleVIN + " parameter is already update!");
		
	}


	/**
	 * 参数查询报文
	 * @param ctx
	 * @param bytes
	 * @param vehicleVIN
	 * @throws Exception
	 */
	private void queryCommandHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws Exception {
		// 解析报文
		// 取得返回查询参数时间
		String simpleDate = ByteUtil.getStringDateFromByteArray(bytes, 24);

		ParameterMessageDecoder.parameterDecoder(bytes, vehicleVIN, simpleDate);
	}

	/**
	 * 终端校时报文，平台收到该报文时，校验正确发送成功应该，校验错误发送修改错应答
	 * 进入该处理函数，说明校验正确
	 * @param ctx
	 * @param bytes
	 * @param vehicleVIN
	 * @throws ParseException
	 */
	private void terminalCorrectionHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws ParseException {
		// 更改应答标志
		byte responseTypeBack = 0x01;
		bytes[22] = (byte) 6;
		// 因为终端校时报文中没有时间，也就是数据单元为空
		// 所以返回的时候加上时间，并将数据单元的值设为6
		byte[] newBytes = new byte[bytes.length + 6];
		for (int i = 0; i < bytes.length; i++) {
			newBytes[i] = bytes[i];
		}
		sendResponseMessage(ctx, newBytes, responseTypeBack);
		logger.info(vehicleVIN + " Terminal Correction succeed!");
	}

	
	/**
	 * 平台接收到心跳报文时，校验正确发送成功应答；校验错误，忽略。
	 * @param ctx
	 * @param bytes
	 * @param vehicleVIN
	 */
	private void heartBeatHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) {
				
		// 更改应答标志
		byte responType = 0x01;
		bytes[3] = responType;
		// 计算校验位
		byte checkCode = CheckCode.calculateCheckCode(bytes);
		bytes[bytes.length-1] = checkCode;
		// 发送心跳成功应答消息
		ByteBuf responMessage = Unpooled.buffer(bytes.length);
		responMessage.writeBytes(bytes);
		ctx.writeAndFlush(responMessage);
	}


	/**
	 * 实时信息上报处理，与补发信息上报处理是同一个处理函数
	 * 实时信息上报数据格式：
	 * 数据采集时间(byte[6])、信息类型标志（1）(byte)、信息体（1）......信息类型标志（n）(byte)、信息体（n）
	 * 信息体：根据信息类型不同，长度和数据类型不同
	 * @param ctx
	 * @param bytes
	 * @param vehicleVIN
	 * @throws ParseException
	 */
	private void realInfoUploadHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws ParseException {
		 
		// 获取上报报文时间
		// 报文时间，应该需要加到每一个实时报文类里，数据库中时间也是很重要的一列
		// TO-DO: 从上至下，已修改到EcarVehicleStateInfo，其他的先暂时不修改
		String date = ByteUtil.getStringDateFromByteArray(bytes, 24);	
		System.out.println(date);
		int pos = 30;
		while (pos < bytes.length) {
			short realInfoValue = Unsigned.getUnsignedByte(bytes[pos]);
			RealInformationType realInfoType = RealInformationType.getRealInformationTypeByValue(realInfoValue);
			pos = pos + 1;
			
			/*
			 * 根据不同的实时信息报文类型，进入相应的处理函数
			 * 每个函数的返回值类型均为int，即该段报文的长度
			 */
			logger.info("Real Info Type is " + realInfoType);
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
			case ALARM_DATA:
				int lenOfAlarmData = RealInfoDecoder.alarmDataHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfAlarmData;
				break;
			case VOLTAGE_DATA_FOR_RESD:
				int lenOfVoltageDataForResd = RealInfoDecoder.voltageDataForResdHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfVoltageDataForResd;
				break;
			case TEMPERATURE_DATA_FOR_RESD:
				int lenOfTempDataForResd = RealInfoDecoder.temperatureDataForResdHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfTempDataForResd;
				break;
			case ECAR_VEHICLE_STATE_INFO:
				int lenOfECarVehicleInfo = RealInfoDecoder.eCarVehicleStateInfoHandler(bytes, pos, vehicleVIN);
				pos = pos + lenOfECarVehicleInfo;
				break;
			default:
				logger.info("Real Info Type is " + realInfoType + ", and it is not correct!");
				break;
			}
		}
		
	}
	
	/**
	 * 处理车辆登入消息 
	 * @param ctx
	 * @param bytes
	 * @param vehicleVIN
	 * @throws Exception
	 */
	private void vehicleLoginHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) throws Exception {
		VehicleLogin vehicleLogin = new VehicleLogin();		
		
		// 获取上线时间
		String simpleDate = ByteUtil.getStringDateFromByteArray(bytes, 24);
		
		// 获取登入流水号
		int loginSerialNumber = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, 30));
		
		// 获取SIM卡ICCID号
		String SIMCardICCIDNumber = ByteUtil.getStringFromByteArray(bytes, 32, 20);
		
		// 获取可充电储能子系统数
		short numberOfRESS = Unsigned.getUnsignedByte(bytes[52]);
		
		// 获取可充电储能系统编码长度
		byte codeLengthOfRESS = bytes[53];
		
		// 获取可充电储能系统编码
		int lengthOfRESS = numberOfRESS * codeLengthOfRESS;
		String codeForRESS = ByteUtil.getStringFromByteArray(bytes, 54, lengthOfRESS);
		
		vehicleLogin.setVehicleVIN(vehicleVIN);
		vehicleLogin.setDataCollectTime(simpleDate);
		vehicleLogin.setLoginSerialNumber(loginSerialNumber);
		vehicleLogin.setSimICCID(SIMCardICCIDNumber);
		vehicleLogin.setNumberOfRESS(numberOfRESS);
		vehicleLogin.setCodingLengthOfRESS(codeLengthOfRESS);
		vehicleLogin.setCodeForRESS(codeForRESS);
		
		System.out.println(vehicleLogin);
		// 更新数据库
		
		logger.info(vehicleVIN + "login in " + simpleDate + "succeed!" + " And its loginSerialNumber is " + loginSerialNumber);
		// 向客户端发送成功应答
		// 只需修改原报文中的应答标志，应答报文时间，并重新计算校验位
		byte responseTypeBack = 0x01;
		sendResponseMessage(ctx, bytes, responseTypeBack);
		
	}
	
	private void vehicleLogoutHandler(ChannelHandlerContext ctx, byte[] bytes, String vehicleVIN) {
		
		VehicleLogout vehicleLogout = new VehicleLogout();
		
		// 获得登出时间
		String logoutTime = ByteUtil.getStringDateFromByteArray(bytes, 24);
		
		// 获得登出流水号
		int logoutSerialNumber = Unsigned.getUnsignedShort(ByteUtil.getShort(bytes, 30));
		
		vehicleLogout.setVehicleVIN(vehicleVIN);
		vehicleLogout.setLogoutTime(logoutTime);
		vehicleLogout.setLogoutSerialNumber(logoutSerialNumber);
		
		System.out.println(vehicleLogout);
		// 更新数据库
		
		logger.info(vehicleVIN + "logout in " + logoutTime + "succeed!" + " And its logoutSerialNumber is " + logoutSerialNumber);
		//登出报文，平台不需要发送成功登出应答
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
