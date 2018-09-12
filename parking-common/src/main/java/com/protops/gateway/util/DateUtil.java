package com.protops.gateway.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DateUtil {

	public static final String FORMAT_FOR_APP = "yyyyMMdd HH:mm:ss";

	public static final String DATE_FMT_YMDHMS = "yyyyMMddHHmmss";

	public static final String DATE_FMT_DISPLAY = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_FMT_DISPLAY1 = "yyyyMMdd";

    public static final String DATE_FMT_DISPLAY2 = "yyyy-MM-dd";

	public static final String DATE_FMT_DISPLAY3 = "yyyy-MM-dd HH";

	public static final String DATE_FMT_DISPLAY4 = "yyyy-MM";
	public static final String DATE_FMT_DISPLAY5 = "yyyy-M";
	public static final String DATE_FMT_DISPLAY6 = "yyyyMM";

	public static final String DATE_FMT_DISPLAY7 = "HH:mm:ss";

	public static final String DATE_FMT_DISPLAY_HH = "HH";

	public static final String DATE_FORMAT_COOKIE = "EEE, dd-MMM-yyyy HH:mm:ss 'GMT'";

	public static String currentTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String currentTimeForApp() {
		return currentTime(FORMAT_FOR_APP);
	}

	/**
	 * 将 {@link java.util.Date} 格式化成特定格式。
	 * 
	 * @param date
	 *            {@link java.util.Date}
	 * @param format
	 *            参见 {@link java.text.SimpleDateFormat}
	 * @return 格式化后的字符串
	 */
	public static String getDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	public static void main(String[] args){
//		System.out.println(DateUtil.getDate();

	}

	/**
	 * 将 {@link java.util.Date} 格式化成格式 "yyyyMMddHHmmss"。
	 * 
	 * @param date
	 *            {@link java.util.Date}
	 * @return 格式化后的字符串
	 */
	public static String getDateTime(Date date) {
		return getDate(date, DATE_FMT_YMDHMS);
	}

	/**
	 * 将yyyyMMddHHmmss时间转换成yyyyMMdd HH:mm:ss
	 * 
	 * @param date
	 *            yyyyMMddHHmmss
	 * @return yyyyMMdd HH:mm:ss
	 */
	public static String convertToAppDate(String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(date.substring(0, 8)).append(" ").append(date.substring(8, 10)).append(":").append(date.substring(10, 12))
		        .append(":").append(date.substring(12));
		return sb.toString();
	}

	public static String convertToJiaofeiDate(String date) {
		Date parsedDate = parseDate(date, FORMAT_FOR_APP);
		return getDateTime(parsedDate);
	}

	public static String convertFormat(String date, String formatSrc, String formatDest){
		Date parsedDate = parseDate(date, formatSrc);
		return getDate(parsedDate, formatDest);
	}

	public static Date parseDate(String time, String format) {
		try {
			SimpleDateFormat s = new SimpleDateFormat(format);
			s.setLenient(false);
			return s.parse(time);
		} catch (Exception e) {
			// Ignore
		}
		return null;
	}
	
	public static Date getAddedTime(Date time, int field, int amount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(field, amount);
		Date addedOrderTime = calendar.getTime();
		return addedOrderTime;
	}
	
	public static String resolveDisplayTime(String date) {
		Date resovledDate = parseDate(date, DATE_FMT_YMDHMS);
		if(resovledDate == null){
			return StringUtils.EMPTY;
		}
		return getDate(resovledDate, DATE_FMT_DISPLAY);
    }

    public static String displayChinaYMD(Date date){
        StringBuilder sb = new StringBuilder();
        String resolvedDate = DateUtil.getDate(date, DateUtil.DATE_FMT_DISPLAY1);
        sb.append(resolvedDate.substring(0,4)).append("年").append(resolvedDate.substring(4,6)).append("月").append(resolvedDate.substring(6)).append("日");
        return sb.toString();
    }

    public static String displayChinaYMDHMS(Date date){
        StringBuilder sb = new StringBuilder();
        String resolvedDate = DateUtil.getDate(date, DateUtil.DATE_FMT_YMDHMS);
        sb.append(resolvedDate.substring(0,4)).append("年").append(resolvedDate.substring(4,6)).append("月").append(resolvedDate.substring(6,8)).append("日 ");
        sb.append(resolvedDate.substring(8,10)).append("时").append(resolvedDate.substring(10,12)).append("分").append(resolvedDate.substring(12)).append("秒");
        return sb.toString();
    }

	/**
	 * just like :201444，表示2014年第44周
	 * @param date
	 * @return
	 */
	public static String getYearWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static String getYearMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH);
	}

    public static Date getYMDDisplay(){
        return new Date();
//        String endDateStr = DateUtil.getDate(new Date(), DateUtil.DATE_FMT_DISPLAY2);
//        return DateUtil.parseDate(endDateStr, DateUtil.DATE_FMT_DISPLAY2);
    }

	public static Integer getDayOfWeek(Date date){
		Calendar g = Calendar.getInstance();
		g.setTime(date);
		return g.get(java.util.Calendar.DAY_OF_WEEK) - 1;
	}

	public static Integer getDayOfYear(Date date){
		Calendar g = Calendar.getInstance();
		g.setTime(date);
		return g.get(Calendar.DAY_OF_YEAR) - 1;
	}

	public static Integer getDayOfWeek(){
		return getDayOfWeek(new Date());
	}

	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getHour() {
		return getHour(new Date());
	}

    public static Integer getMin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

	public static Date getYesterday() {

		Date date = DateUtil.getAddedTime(new Date(), Calendar.DATE, -1);

		String endDateStr = DateUtil.getDate(date, DateUtil.DATE_FMT_DISPLAY2);

		date = DateUtil.parseDate(endDateStr, DateUtil.DATE_FMT_DISPLAY2);

		return date;
	}

	public static String formatDate4Cookie(int seconds) {
		Calendar calendar = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
		calendar.add(Calendar.SECOND, seconds);
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_COOKIE);
		dateFormat.setCalendar(calendar);
		return dateFormat.format(calendar.getTime());
	}
}