package com.iteaj.util;

import java.text.*;
import java.util.*;

public class TimeUtil {
	public TimeUtil() {
	}

	// 日期时间的输出样式字符串
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIME_PATTERN = "HH:mm:ss";

	// 时间日期输出是样的初始化对象
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(
			DATE_TIME_PATTERN);
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			DATE_PATTERN);
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			TIME_PATTERN);

	/**
	 * 获取当前时间指定偏移类的时间对象
	 * 
	 * @param field
	 *            偏移的域， 以Calendar常量取值， 如 Calendar.DATE
	 * @param offset
	 *            正负数偏移量
	 * @return
	 */
	public static Date getOffsetDate(int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(field, offset);
		return cal.getTime();
	}

	/**
	 * 获取日期对象的指定域值
	 * 
	 * @param dt
	 *            日期对象，如果为空将使用当前时间
	 * @param field
	 *            指定域， 以Calendar常量取值
	 * @param offset
	 *            正负数偏移量，不偏移取0
	 * @return
	 */
	public static int getDateField(Date dt, int field, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt == null ? new Date() : dt);
		if (offset != 0)
			;
		cal.add(field, offset);
		return cal.get(field);
	}

	public static int getDateField(Date dt, int field) {
		return getDateField(dt, field, 0);
	}

	public static int getDateField(int field) {
		return getDateField(null, field, 0);
	}

	public static int getDateField(int field, int offset) {
		return getDateField(null, field, offset);
	}

	/**
	 * 格式化SQL中的字串参数(将'号改为'')
	 * 
	 * @param str
	 * @return
	 */
	public static String formatSQLString(String str) {
		if (str == null)
			return "";
		return str.replaceAll("'", "''");
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd HH:mm:ss)的日期+时间字符串
	 * 
	 * @param date
	 *            Date
	 * @param pattern
	 *            String 指定的格式字符串
	 * @return String date或pattern为空,返回空串
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null || pattern == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd)的日期字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return DATE_FORMAT.format(date);
	}

	/**
	 * 格式化为默认格式(yyyy-MM-dd HH:mm:ss)的日期+时间字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		if (date == null)
			return "";
		return DATE_TIME_FORMAT.format(date);
	}

	/**
	 * 格式化为默认格式(HH:mm:ss)的时间字符串
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatTime(Date date) {
		if (date == null)
			return "";
		return TIME_FORMAT.format(date);
	}

	/**
	 * 将字符串转化为日期对象,应用格式 yyyy-MM-dd
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对象,应用指定的格式
	 * 
	 * @param dateStr
	 *            String 日期字符串
	 * @param pattern
	 *            String 格式字符串
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateStr);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对象,应用格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateString
	 *            String
	 * @return Date
	 */
	public static Date parseDateTime(String dateStr) {
		try {
			return DATE_TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 将字符串转化为日期对象,应用格式 HH:mm:ss
	 * 
	 * @param dateStr
	 *            String
	 * @return Date
	 */
	public static Date parseTime(String dateStr) {
		try {
			return TIME_FORMAT.parse(dateStr);
		} catch (ParseException ex) {
			return null;
		}
	}

	/**
	 * 获取日期 中年份
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDateYear(Date date) {
		return String.valueOf(date.getYear() + 1900);
	}

	/**
	 * 获取日期中月份
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDateMonth(Date date) {
		return String.valueOf(date.getMonth());
	}

	/**
	 * 获取日期中日
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getDate(Date date) {
		return String.valueOf(date.getDate());
	}

	public static String getAppendixFilename() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.getTimeInMillis());
	}

}
