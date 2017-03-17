package com.cit.its.messageStruct;

import com.cit.its.util.ByteUtil;

/**
 * 参数设置报文
 * 参数设置时间、参数总数、参数项列表（用Parameter类表示）
 * 其中参数项列表中，可同时设置多个参数项，当其中一个
 * 参数项值错误时，全局设置否定
 * @author QingXi
 *	
 */
public class ParameterSet {
	
	private byte[] parameterSetTime;			// 参数设置时间
	
	private short totalNumOfParameter;			// 参数总数
	
	private Parameter parameter;				// 参数项列表
	
	
	// set, get方法
	public void setParameterSetTime(byte[] parameterSetTime) {
		this.parameterSetTime = parameterSetTime;
	}
	
	public byte[] getParameterSetTime() {
		return parameterSetTime;
	}
	
	public void setTotalNumOfParameter(short totalNumOfParameter) {
		this.totalNumOfParameter = totalNumOfParameter;
	}
	
	public short getTotalNumOfParameter() {
		return totalNumOfParameter;
	}
	
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	
	public Parameter getParameter() {
		return parameter;
	}
	
	
	// 重写toString()方法
	public String toString() {
		return "参数设置时间 : " + ByteUtil.getInstance().getStringDateFromByteArray(parameterSetTime, 0) + "\n" +
			   "参数总数 : " + totalNumOfParameter + "\n" + 
			   "参数项列表 : " + parameter;
	}
}
