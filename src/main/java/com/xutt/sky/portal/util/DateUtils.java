package com.xutt.sky.portal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtils {
	/**
	 * 将日期 转换为 yyyy-MM-dd HH:mm:ss的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getLongDateText(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 将日期 转换为 yyyy-MM-dd的字符串 但是将
	 * 
	 * @param date
	 * @return
	 */
	public static String getNewDateText(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static String getNextDateText(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		Date d = c.getTime();
		return format.format(d);
	}

	/**
	 * 去除日期中的时分秒，只保留年月日
	 */
	public static String formate(String date) {
		String formatedDate = "";
		if (date != null && date.length() >= 10) {
			formatedDate = date.substring(0, 10);
		}
		return formatedDate;
	}

	/**
	 * 获得当前时间的时间戳字符串：“20170309120835”
	 */
	public static String getTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * 将时间字符串解析为日期对象（yyyy/MM/dd）
	 * 
	 * @param dateStr
	 */
	public static Date parseDate(String dateStr) throws ParseException {
		Date date = null;
		if (dateStr != null && !"".equals(dateStr)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			date = format.parse(dateStr);
		}
		return date;
	}

	/**
	 * datetype: day.过去一天；week.获取当前时间减7天的时间；
	 * 
	 * month.获取当前时间减一个月的时间； quarter.获取当前时间减三个月的时间；
	 * 
	 * halfYear.获取当前时间减6个月的时间； year获取当前时间减1年的时间
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 * @throws Exception
	 */
	public static String formatDate(Date date, String dateType) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		if (dateType.equals("minute")) {
			c.add(Calendar.MINUTE, -3);
		}
		// 过去一天
		if (dateType.equals("day")) {
			c.add(Calendar.DATE, -1);
		}
		// 过去七天
		if (dateType.equals("week")) {
			c.add(Calendar.DATE, -7);
		}
		// 过去一月
		if (dateType.equals("month")) {
			c.add(Calendar.MONTH, -1);
		}
		// 过去三个月 季度
		if (dateType.equals("quarter")) {
			c.add(Calendar.MONTH, -3);
		}
		// 过去六个月 half a year
		if (dateType.equals("halfYear")) {
			c.add(Calendar.MONTH, -6);
		}
		// 过去一年
		if (dateType.equals("year")) {
			c.add(Calendar.YEAR, -1);
		}
		Date d = c.getTime();
		return format.format(d);
	}

	/**
	 * 获取每月的第一天或者最后一天
	 * 
	 * firstOrLast first 表示第一天；last表示最后一天
	 * 
	 * @param firstOrLast
	 * @return
	 */
	public static String getMonthFirstDayOrLastDay(Date date, String firstOrLast) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		String dateDay = "";
		if (firstOrLast.equals("first")) {
			cale.add(Calendar.MONTH, 0);
			cale.set(Calendar.DAY_OF_MONTH, 1);
			dateDay = format.format(cale.getTime());
		} else {
			cale.add(Calendar.MONTH, 1);
			cale.set(Calendar.DAY_OF_MONTH, 0);
			dateDay = format.format(cale.getTime());
		}
		return dateDay;
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);

		String firstday = format.format(cale.getTime());
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastday = format.format(cale.getTime());
		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

		String s = "2017/08/23";
		String date = formatDate(new Date(), "5");
		System.out.println(date);
		System.out.println(firstday);
		System.out.println(getNextDateText(new Date()));
	}

}
