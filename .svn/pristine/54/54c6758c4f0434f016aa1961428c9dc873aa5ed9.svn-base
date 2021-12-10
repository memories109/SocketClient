package com.etbs.realtime.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
	
	
	public static String getCurrentDate() {
		return getDateString(new Date(), "yyyy-MM-dd");
	}
	
	public static String getCurrentDateString() {
		return getDateString(new Date(), "yyyy-MM-dd");
	}
	
	public static String getCurrentDateTimeString() {
		return getDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	
	public static String getDateString(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	public static Date getDateTime(String date, String format) {
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			System.out.println("Date 변경에 오류가 발생했습니다. >> "+date+" : "+format);
			e.printStackTrace();
			return null;
		}
	}
	
	public static long getDateDiffMin(Date date1, Date date2) {
		
		long diff = (date1.getTime() - date2.getTime()); 
		return (diff/1000/60);
	}
}
