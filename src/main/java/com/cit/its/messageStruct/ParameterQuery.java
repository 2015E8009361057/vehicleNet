package com.cit.its.messageStruct;

import java.text.ParseException;
import java.util.Date;

import com.cit.its.util.ByteUtil;

public class ParameterQuery {
	
	private byte[] queryParaTime;				// 参数查询时间
	
	private byte totalNumOfParameter;			// 参数总数
	
	private byte[] parameterID;					// 参数ID
	
	
	public ParameterQuery() throws ParseException {
		Date date = new Date();
		queryParaTime = new byte[6];
		ByteUtil.putDateToByteArray(date, queryParaTime, 0);
	}
	
	// set, get方法
	public void setQueryParaTime(byte[] queryParaTime) {
		this.queryParaTime = queryParaTime;
	} 
	
	public byte[] getQueryParaTime() {
		return queryParaTime;
	}
	
	public void setTotalNumOfParameter(byte totalNumOfParameter) {
		this.totalNumOfParameter = totalNumOfParameter;
	}
	
	public byte getTotalNumOfParameter() {
		return totalNumOfParameter;
	}
	
	public void setParameterID(byte[] parameterID) {
		this.parameterID = parameterID;
	}
	
	public byte[] getParameterID() {
		return parameterID;
	}
	
	
	// 将byte[]转换为String
	public String timeToString() {
		return ByteUtil.getStringDateFromByteArray(queryParaTime, 0);
	}
	
	public String paraIDToString() {
		String result = "";
		for (int i = 0; i < parameterID.length; i++) {
			result = result + "\n" + parameterID[i];
		}
		return result;
	}
	
	// 重写toString()方法
	public String toString() {
		return "参数查询时间 : " + timeToString() + "\n" + 
			   "参数总数 : " + totalNumOfParameter + "\n" + 
			   "参数ID列表 : " + paraIDToString();
	}

}
