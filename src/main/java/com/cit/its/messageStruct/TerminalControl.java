package com.cit.its.messageStruct;

import java.text.ParseException;
import java.util.Date;

import com.cit.its.util.ByteUtil;

/**
 * 车载终端控制的数据定义
 * 时间、命令ID、命令参数
 * @author QingXi
 *
 */
public class TerminalControl {
	
	private byte[] time;				// 时间
	
	private byte orderID;				// 命令ID
	
	private String orderPermeter;		// 命令参数
	
	
	public TerminalControl() throws ParseException {
		Date date = new Date();
		time = new byte[6];
		ByteUtil.getInstance().putDateToByteArray(date, time, 0);
	}
	
	public void setTime(byte[] time) {
		this.time = time;
	}
	
	public byte[] getTime() {
		return time;
	}
	
	public void setOrderID(byte orderID) {
		this.orderID = orderID;
	}
	
	public byte getOrderID() {
		return orderID;
	}
	
	public void setOrderPermeter(String orderPermeter) {
		this.orderPermeter = orderPermeter;
	}
	
	public String getOrderPermeter() {
		return orderPermeter;
	}
	
	

}
