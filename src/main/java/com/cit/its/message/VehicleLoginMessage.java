package com.cit.its.message;

import com.cit.its.messageStruct.BodyVehicleLogin;
import com.cit.its.messageStruct.Footer;
import com.cit.its.messageStruct.Header;

public final class VehicleLoginMessage {
	
	private Header header;						// 消息头
	
	private BodyVehicleLogin vehicleLoginInfo;	// 消息体
	
	private Footer checkCode;					// 消息尾，也就是校验码
	
	/**
	 * @return the header
	 */
	public final Header getHeader() {
		return header;
	}
	
	/**
	 * @param header
	 * the header to set
	 */
	public final void setHeader(Header header) {
		this.header = header;
	}
	
	/**
	 * @return the vehicleLoginInfo
	 */
	public final BodyVehicleLogin getVehicleLoginInfo() {
		return vehicleLoginInfo;
	}
	
	/**
	 * @param vehicleLoginInfo
	 * the vehicleLoginInfo to set
	 */
	public final void setVehicleLoginInfo(BodyVehicleLogin vehicleLoginInfo) {
		this.vehicleLoginInfo = vehicleLoginInfo;
	}
	
	/**
	 * @return the checkCode
	 */
	public final Footer getCheckCode() {
		return checkCode;
	}
	
	/**
	 * @param checkCode
	 * the checkCode to set
	 */
	public final void setCheckCode(Footer checkCode) {
		this.checkCode = checkCode;
	}

}
