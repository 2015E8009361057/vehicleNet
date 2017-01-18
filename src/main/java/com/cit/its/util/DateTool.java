/**  
* @Title: DateUtil.java 
* @Package com.itrc.vehiclemanage.util 
* @Description: TODO 
* @author zxl   
* @date 2015年3月13日 下午3:03:00 
*/ 

package com.cit.its.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/** 
 * @Title: DateUtil
 * @Description: TODO 
 * @author zxl 
 * @date 2015年3月13日 下午3:03:00  
 */
public class DateTool {
	
	public static  String formatDate(Date date)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sdf.format(date);
   }
   
   public static Date parse(String strDate) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return sdf.parse(strDate);
   }
   public static long getDistance(String endDate,String startDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
		return (sdf.parse(endDate).getTime()-sdf.parse(startDate).getTime())/(604800000/7);
		}
		catch (Exception e) {
			return 0;
		}
	}
	public static String getTomorrow()
	{
		Calendar tomorrow=Calendar.getInstance();
		tomorrow.setTimeInMillis(tomorrow.getTimeInMillis()+604800000/7);
		int Year=tomorrow.getTime().getYear()+1900;
		String Month="";
		String Day="";
		if (tomorrow.getTime().getMonth()<9)
		{
			Month ="0"+(tomorrow.getTime().getMonth()+1);
		}
		else
			Month=(tomorrow.getTime().getMonth()+1)+"";
		if (tomorrow.getTime().getDate()<10)
		{
			Day ="0"+tomorrow.getTime().getDate();
		}
		else
		{
			Day=tomorrow.getTime().getDate()+"";
		}
		String Tomorrow=Year+"-"+Month+"-"+Day;
		return Tomorrow;
	}
	public static String getToday()
	{
		Calendar rightNow=Calendar.getInstance();
		int Year=rightNow.getTime().getYear()+1900;
		String Month="";
		String Day="";
		if (rightNow.getTime().getMonth()<9)
		{
			Month ="0"+(rightNow.getTime().getMonth()+1);
		}
		else
			Month=(rightNow.getTime().getMonth()+1)+"";
		if (rightNow.getTime().getDate()<10)
		{
			Day ="0"+rightNow.getTime().getDate();
		}
		else
		{
			Day=rightNow.getTime().getDate()+"";
		}
		String today=Year+"-"+Month+"-"+Day;
		return today;
	}
	public static String getLastweek()
	{
		Calendar lastweek=Calendar.getInstance();
		lastweek.setTimeInMillis(lastweek.getTimeInMillis()-604800000);
		int Year=lastweek.getTime().getYear()+1900;
		String Month="";
		String Day="";
		if (lastweek.getTime().getMonth()<9)
		{
			Month ="0"+(lastweek.getTime().getMonth()+1);
		}
		else
			Month=(lastweek.getTime().getMonth()+1)+"";
		if (lastweek.getTime().getDate()<10)
		{
			Day ="0"+lastweek.getTime().getDate();
		}
		else
		{
			Day=lastweek.getTime().getDate()+"";
		}
		String Lastweek=Year+"-"+Month+"-"+Day;
		return Lastweek;
	}
	
	public static String getLastmonth()
	{
		Calendar lastmonth=Calendar.getInstance();
		lastmonth.setTimeInMillis(lastmonth.getTime().getTime()-1296000000);
		lastmonth.setTimeInMillis(lastmonth.getTime().getTime()-1296000000);
		int Year=lastmonth.getTime().getYear()+1900;
		String Month="";
		String Day="";
		if (lastmonth.getTime().getMonth()<9)
		{
			Month ="0"+(lastmonth.getTime().getMonth()+1);
		}
		else
			Month=(lastmonth.getTime().getMonth()+1)+"";
		if (lastmonth.getTime().getDate()<10)
		{
			Day ="0"+lastmonth.getTime().getDate();
		}
		else
		{
			Day=lastmonth.getTime().getDate()+"";
		}
		String Lastmonth=Year+"-"+Month+"-"+Day;
		return Lastmonth;
	}

}

