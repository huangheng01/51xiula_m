package com.trq.aliwey.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class TimeUtil {
	
	//定义常量
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_LONG_STR = "yyyy-MM-dd kk:mm:ss.SSS";
	public static final String DATE_YMDHM = "yyyy-MM-dd HH";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_All_KEY_STR = "yyyyMMddHHmmss";
    public static final String DATE_HMS = "HH:mm:ss";
    public static final String DATE_HM = "HH:mm";
    
	//获取时间戳
	public static long getUnixTimestamp(){
		long unix = System.currentTimeMillis() / 1000;
		return unix;
	}
	
	//时间戳转普通时间
	public static String UnixToDate(long timestamp){
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp * 1000));
		return date;
	}
	
	//返回两个日期的时间差
    public static int getBetween(Date begin, Date end, String type) {
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;

		if(type.equals("d")){
			return (int)day;
		}
		if(type.equals("h")){
			return (int)hour;
		}
		if(type.equals("m")){
			return (int)minute;
		}
		if(type.equals("s")){
			return (int)second;
		}
		return 0;
	}
    
    //修改时间
    public static Date changeTime(Date date, String type, int time){
    	if(type.equals("M")){
    		return DateUtils.addMonths(date, time);
    	}else if(type.equals("d")){
    		return DateUtils.addDays(date, time);
    	}else if(type.equals("h")){
    		return DateUtils.addHours(date, time);
    	}else if(type.equals("m")){
    		return DateUtils.addMinutes(date, time);
    	}
		return null;
    }
    
    //格式化指定格式日期字符串
    public static String parseStr(String date, String pattern) {
		SimpleDateFormat df1 = new SimpleDateFormat(DATE_FULL_STR);
		SimpleDateFormat df2 = new SimpleDateFormat(pattern);
		try {
			return df2.format(df1.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    public static String parseStr(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
    
    public static Date parseDate(String date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
    
    //判断两个时间的大小
    public static boolean compare_date(String DATE1, String DATE2) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
    }
    
    /**  
    * 取得两个时间段的时间间隔  
    * return t2 与t1的间隔天数  
    * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常  
    */   
	public static int getBetweenDays(String t1, String t2) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			betweenDays += countDays(c1.get(Calendar.YEAR));
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
		}
		return betweenDays;
	}

	public static int countDays(int year) {
		int n = 0;
		for (int i = 1; i <= 12; i++) {
			n += countDays(i, year);
		}
		return n;
	}

	public static int countDays(int month, int year) {
		int count = -1;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			count = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			count = 30;
			break;
		case 2:
			if (year % 4 == 0)
				count = 29;
			else
				count = 28;
			if ((year % 100 == 0) & (year % 400 != 0))
				count = 28;
		}
		return count;
	}
}
