package com.util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间的工具类
 */
public class DateUtil {
	
	public static final String DATE_SIMPLE_STR = "yyyyMMdd";
	
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	
	public static final String DATE_FULL_STR = "yyyy-MM-dd hh:mm:ss";
	
	public static final String DATE_24FULL_STR = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_US_STR = "yyyy/MM/dd";
	
	public static final String DATE_KEY_STR = "yyyyMMddhhmmss";
	
	public static final String DATE_FULLKEY_STR = "yyyyMMddhhmmssSSS";
	
	public static final String DATE_SHORT_CN = "yyyy年MM月dd日";
	
	public static final String DATE_FULL_CN = "yyyy年MM月dd日 hh时mm分ss秒";
	
	public enum DateTypeEnum{
		FULL, MEDIUM, SHORT
	}
	
	public static int getDayOfYear(){ //今天是今年的第几天
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}
	
	public static int getDayOfWeek(){ //这周的第几天
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	public static int getDayOfMonth(){ //本月的第几天
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前时间的时间戳
	 */
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		System.out.println(getCurrentDate(DateUtil.DATE_FULL_CN));
	}
	
	/**
	 * 获取当前日期时间的字符串形式
	 * yyyy-MM-dd --> 2015-10-15
	 * yyyy-MM-dd hh:mm:ss --> hh:mm:ss 2015-10-15 15:10:30
	 */
	public static String getCurrentDate(String datePattern){
		Date date = new Date();
		String currentTimeStr = new SimpleDateFormat(datePattern).format(date);
		return currentTimeStr;
	}
	
	/**
	 * 将指定的时间戳转换为指定格式的字符串
	 * 应用场景：通常用于数据库查询出来的时间戳转换为字符串
	 * example:
	 *   formatSpecifyTimestamp(new Timestamp(System.currentTimeMillis()), "yyy-MM-dd hh:mm:ss));
	 *   output: 2015-10-16 09:22:34
	 */
	public static String formatSpecifyTimestamp(Timestamp timestamp, String datePattern){
		Format format = new SimpleDateFormat(datePattern);
		return format.format(timestamp);
	}
	
	/**
	 * 将指定的时间格式的字符串转换为另一种格式的 字符串
	 * example: 
	 *   formatDateToOtherType("20151016", "yyyyMMdd", "yyyy-MM-dd");
	 *   output---> 2015-10-16
	 */
	public static String formatDateToOtherType(String oldDateStr, String oldType, String newType){
		String result = null;
		
		SimpleDateFormat oldFormat = new SimpleDateFormat(oldType);
		SimpleDateFormat newFormat = new SimpleDateFormat(newType);
		
		try {
			result = newFormat.format(oldFormat.parse(oldDateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将日期转换为指定格式的字符串
	 * example:
	 *  transferDateToStr(new Date(), DateUtils.DATE_FULL_STR);
	 *  output-->2015-10-16 09:50:35
	 */
	public static String transferDateToStr(Date date, String datePattern){
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		return sdf.format(date);
	}
	
	/**
	 * 获取指定毫秒之前的时间
	 * example:
	 *    getPreTimeOfSpecifyMillis(12342145664L, DateUtils.DATE_FULL_STR);
	 *    output --> 2015-05-26 01:47:46
	 */
	public static String getPreTimeOfSpecifyMillis(long millis, String datePattern){
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		long currentMillis = System.currentTimeMillis();
		long spaceMillis = currentMillis - millis;
		return sdf.format(spaceMillis);
	}
	
	/**
	 * 将指定格式的字符串转按照既定格式转换为时间 Date. 
	 * 注：dateStr与datePattern格式必须相同
	 * example:
	 *    strToDate("2015-10-16 00:00:00", DateUtils.DATE_FULL_STR);
	 */
	public static Date strToDate(String dateStr, String datePattern){
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		if(!"".equals(dateStr) && null != dateStr){
			try {
				return sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 将日期格式的字符串转换为时间戳.
	 * 注：dateStr与datePattern的格式必须相同
	 * 应用场景：通常需要将时间格式的字符串转换为时间戳,然后插入数据库
	 * exapmle:
	 *    transferStrToTimstamp("2015-10-16", DateUtils.DATE_SMALL_STR);
	 */
	public static Timestamp transferDateStrToTimstamp(String dateStr, String datePattern){
		Timestamp timestamp = null;
		Format format = new SimpleDateFormat(datePattern);
		
		try {
			Date date = (Date)format.parseObject(dateStr);
			timestamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
	
	/**
	 * 得到两个给定时间相差的毫秒数
	 */
	public static long getSpaceMillisOfTwoTime(String strDate1, String strDate2, String datePattern){
		
		Date start = strToDate(strDate1, datePattern);
		Date end = strToDate(strDate2, datePattern);
		
		return end.getTime() - start.getTime();
	}
	
	/**
	 * 得到两个时间间隔的秒数
	 */
	public static long getSecondsOfTwoTime(String strDate1, String strDate2, String datePattern){
		return getSpaceMillisOfTwoTime(strDate1, strDate2, datePattern) / 1000;
	}
	
	/**
	 * 计算两个时间之间间隔的分钟
	 */
	public static long intervelMinutesOfTwoDate(String strDate1, String strDate2, String datePattern){
		return getSecondsOfTwoTime(strDate1, strDate2, datePattern) / 60; 
	}
	
	/**
	 * 计算来两个时间之间间隔的小时
	 */
	public static long intervelHoursOfTwoDate(String strDate1, String strDate2, String datePattern){
		return getSecondsOfTwoTime(strDate1, strDate2, datePattern) / 3600;
	}
	
	/**
	 * 计算两个时间之间间隔的天数
	 */
	public static long intervalDayOfTwoDate(String strDate1, String strDate2, String datePattern){
		return getSecondsOfTwoTime(strDate1, strDate2, datePattern) / (3600 * 24);
	}
	
	/**
	 * 获得今天几天前或者几天后的日期
	 */
	public static String getDateOfSpecifyDays(int count, String datePattern){
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, count);
		
		Date date = calendar.getTime();
		
		return transferDateToStr(date, datePattern);
	}
	
	/**
	 * 获得指定日期之前后者之后的几天的日期
	 */
	public static String getDateOfSpecifyDays(String dateStr, int count, String datePattern){
		Calendar calendar = Calendar.getInstance();
		
		Date date = strToDate(dateStr, datePattern);
		calendar.setTime(date);
		calendar.add(Calendar.DATE, count);
		
		date = calendar.getTime();
		
		return transferDateToStr(date, datePattern);
	}
	
	/**
	 * 获取当前日期加amount天后的日期
	 */
	public static Date getCurrDateAddDay(int amount){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}
	
	/**
	 * 获取日期加amount天后的日期
	 */
	public static Date getCurrDateAddDay(Date date, int amount){
		if(date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}
	
	/**
	 * 获取由年月日时分秒毫秒组成的字符串
	 * 应用场景：一般用于生成数据库唯一id
	 * example:
	 *    getDateStr(new Date());
	 *    output ---> 20151016113004826
	 */
	public static String getDateStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FULLKEY_STR);
		return sdf.format(date);
	}
	
	/**
	 * 获得指定日期的星期
	 */
	public static String getWeekOfDate(Date date){
		String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(index < 0) index = 0;
		
		return weeks[index];
	}
	
	/**
	 * 获取字符串形式的日期的 星期
	 * example:
	 *    getWeekOfDate("2015-09-12", DATE_SMALL_STR);
	 *    output ---> 星期六
	 */
	public static String getWeekOfDate(String dateStr, String datePattern){
		Date date = strToDate(dateStr, datePattern);
		return getWeekOfDate(date);
	}
	
	/**
	 * 获取指定类型的日期格式
	 * type的值为枚举值：
	 *    SHORT--> 15-10-16
	 *    MEDIUM --> 2015-10-16
	 *    FULL --> 2015年10月16日 星期五
	 */
	public static String getSpecifyTypeOfDateStr(DateTypeEnum type, Date date){
		
		String dateStr = null;
		DateFormat dateFormat = null;
		
		if(DateTypeEnum.SHORT.equals(type)){
			dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
			dateStr = dateFormat.format(date);
		}else if(DateTypeEnum.MEDIUM.equals(type)){
			dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
			dateStr = dateFormat.format(date);
		}else if(DateTypeEnum.FULL.equals(type)){
			dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}
	
	/**
	 * 获取当天开始时间，包含年、月、日、时、分、秒
	 */
	public static String getBeginDateOfToday(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		return year + "-" + month + "-" + day + " 00:00:00";
	} 
	
	/**
	 * 获取当天结束的时间
	 */
	public static String getEndDateOfToday(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day + " 23:59:59";
	}
	
	/**
	 * 获得当前的年
	 */
	public static int getCurrentYear(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获得当前的月
	 */
	public static int getCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 是否为今天
	 */
	public static boolean isToday(Date date){
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DAY_OF_MONTH);
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return y == year && m == month && d == day;
	}
	
	/**
	 * 是否为昨天
	 */
	public static boolean isYesterday(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return isToday(c.getTime());
	}
	
	/**
	 * 是否为前天 
	 */
	public static boolean isTheDayBeforeYesterday(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 2);
		return isToday(c.getTime());
	}
	
	/**
     * 得到指定格式的月份的第一天
     */
    public static String getFirstDayOfMonth(String dateStr){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		StringBuffer sb = null;
		try {
			Date date = sdf.parse(dateStr);
			calendar.setTime(date);
			GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
			gcLast.setTime(date);
			gcLast.set(Calendar.DAY_OF_MONTH, 1);
			String firstDayOfMonth = sdf.format(gcLast.getTime());
			sb = new StringBuffer().append(firstDayOfMonth).append(" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sb.toString();
    }
    
    /**
     * 得到指定日期格式的这个月的最后一天. 传入的日期格式必须为yyyy-MM-dd
     * 2015-10-31 23:59:59
     */
    public static String getLastDayOfMonth(String dateStr){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		StringBuffer sb = null;
		try {
			Date date = sdf.parse(dateStr);
			
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1); //当前月份退后一个月
			calendar.set(Calendar.DATE, 1);  //日期设置为推后一个月的第一天
			calendar.add(Calendar.DATE, -1); //将修改后的日期往前推一天
			
			String lastDayOfMonth = sdf.format(calendar.getTime());
			sb = new StringBuffer().append(lastDayOfMonth).append(" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sb.toString();
    }
    
    /**
     * 将日期转换为时间戳
     */
    public static Timestamp dateToTimestamp(Date date){
    	return new Timestamp(date.getTime());
    }
}
