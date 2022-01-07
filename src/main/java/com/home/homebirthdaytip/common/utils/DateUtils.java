package com.home.homebirthdaytip.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Title: DateUtil</p>
 * <p>Description: 提供日期有关的相关操作</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * @author echoice
 * @version 1.0
 */
public class DateUtils
{
	public final static String PATTERN_24TIME = "yyyy-MM-dd HH:mm:ss"; 
	public final static String PATTERN_DATE = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    /**
     * 根据传入的模式参数返回当天的日期
     * @param pattern 传入的模式
     * @return 按传入的模式返回一个字符串
     */
    public static String getToday(String pattern)
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    /**
     * 根据传入的模式参数返回当天的日期
     * @return 按传入的模式返回一个字符串
     */
    public static String getToday()
    {
        return getToday("yyyy-MM-dd");
    }
    
    /**
     * 根据传入的模式参数返回当天的日期
     * @return 按传入的模式返回一个字符串
     */
    public static String getNow()
    {
    	return getToday("yyyy-MM-dd HH:mm:ss");
    }   
    
    /**
     * 比较两个日期大小
     * @param date1 日期字符串
     * @param pattern1 日期格式
     * @param date2 日期字符串
     * @param pattern2 日期格式
     * @return boolean 若是date1比date2小则返回true
     * @throws ParseException
     */
    public static boolean compareMinDate(String date1, String pattern1,
                                         String date2, String pattern2) throws ParseException
    {
        Date d1 = convertToCalendar(date1, pattern1).getTime();
        Date d2 = convertToCalendar(date2, pattern2).getTime();
        return d1.before(d2);
    }

    /**
     * 比较两个日期大小
     * @param date1 Date
     * @param date2 Date
     * @return boolean 若是date1比date2小则返回true
     */
    public static boolean compareMinDate(Date date1, Date date2)
    {
        try
        {
            return DateUtils.compareMinDate(DateUtils.formatDate(date1, "yyyy-MM-dd HH:mm:ss"),
                                           "yyyy-MM-dd HH:mm:ss",
                                           DateUtils.formatDate(date2, "yyyy-MM-dd HH:mm:ss"),
                                           "yyyy-MM-dd HH:mm:ss");
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    /**
     * 根据传入的日期字符串以及格式，产生一个Calendar对象
     * @param date 日期字符串
     * @param pattern 日期格式
     * @return Calendar
     * @throws ParseException 当格式与日期字符串不匹配时抛出该异常
     */
    public static Calendar convertToCalendar(String date, String pattern) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = sdf.parse(date);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        return calendar;
    }

    /**
     * 用途：以指定的格式格式化日期字符串
     * @param pattern 字符串的格式
     * @param currentDate 被格式化日期
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     */
    public static String formatDate(Calendar currentDate, String pattern)
    {
        if(currentDate == null || pattern == null)
        {
            throw new NullPointerException("The arguments are null !");
        }
        Date date = currentDate.getTime();
        return formatDate(date, pattern);
    }

    /**
     * 用途：以指定的格式格式化日期字符串
     * @param pattern 字符串的格式
     * @param currentDate 被格式化日期
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     */
    public static String formatDate(Date currentDate, String pattern)
    {
        if(currentDate == null || pattern == null)
        {
            throw new NullPointerException("The arguments are null !");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(currentDate);
    }

    /**
     * 用途：以指定的格式格式化日期字符串
     * @param currentDate 被格式化日期字符串 必须为yyyymmdd
     * @param pattern 字符串的格式
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     * @throws ParseException 若被格式化日期字符串不是yyyymmdd形式时抛出
     */
    public static String formatDate(String currentDate, String pattern) throws ParseException
    {
        if(currentDate == null || pattern == null)
        {
            throw new NullPointerException("The arguments are null !");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(currentDate);
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    /**
     * 用途：以指定的格式格式化日期字符串
     * @param strDate 被格式化日期字符串 必须为yyyymmdd
     * @param formator 格式字符串
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     * @throws ParseException 若被格式化日期字符串不是yyyymmdd形式时抛出
     */
    public static Calendar strToDate(String strDate, String formator)
    {
        if(strDate == null || formator == null)
        {
            throw new NullPointerException("The arguments are null !");
        }

        Calendar date = Calendar.getInstance();
        date.setTime(java.sql.Date.valueOf(strDate));
        return date;
    }

    /**
     * 判断当前时间是否在参数时间内（当开始时间大于结束时间表示时间段的划分从begin到第二天的end时刻）
     *  例如当前时间在12：00 传入参数为（12,12,0,1）返回true
     *  例如当前时间在12：00 传入参数为（12,12,1,0）返回true
     * @param beginHour int 开始的小时值
     * @param endHour int   结束的小时值
     * @param beginMinu int 开始的分钟值
     * @param endMinu int   结束的分钟值
     * @return boolean
     */
    public static boolean isInTime(int beginHour, int endHour,
                                   int beginMinu,
                                   int endMinu)
    {
        Date date1 = new Date();
        Date date2 = new Date();
        Date nowDate = new Date();
        date1.setHours(beginHour);
        date2.setHours(endHour);
        date1.setMinutes(beginMinu);
        date2.setMinutes(endMinu);
        if(date1 == date2)
        {
            return false;
        }
        //yyyy-MM-dd HH:mm:ss
        if(
                DateUtils.compare(date2, date1))
        {
            if(!DateUtils.compare(nowDate, date1)
               || DateUtils.compare(nowDate, date2))
            {
                return true;
            }
        }
        else
        {
            if(
                    !DateUtils.compare(nowDate, date1) &&
                    DateUtils.compare(nowDate, date2)
                    )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 开始时间小于结束时间返回true，否则返回false
     * @param beginDate Date
     * @param endDate Date
     * @return boolean
     */
    private static boolean compare(Date beginDate, Date endDate)
    {
        try
        {
            
            return DateUtils.compareMinDate(DateUtils.formatDate(beginDate,
                    "yyyy-MM-dd HH:mm:ss"),
                                           "yyyy-MM-dd HH:mm:ss",
                                           DateUtils.formatDate(endDate,
                    "yyyy-MM-dd HH:mm:ss"),
                                           "yyyy-MM-dd HH:mm:ss");

        }
        catch(Exception ex)
        {
//            log.error ( "时间格式转换错误" + ex ) ;
            return false;
        }
    }

    /**
     * 将指定格式的时间String转为Date类型
     * @param dateStr String 待转换的时间String
     * @throws ParseException
     * @return Date
     */
    public static Date convertStringToDate(String dateStr,String patternner) throws ParseException
    {
        String pattern =patternner;

        if(dateStr == null)
        {
            return null;
       // }else if (dateStr.length()<10&&(pattern.contains("HH")||pattern.contains("hh"))) {
        }else if (dateStr.length()<pattern.length()) {
        	SimpleDateFormat sdf = new SimpleDateFormat(pattern.substring(0, dateStr.length()-1));
            return sdf.parse(dateStr);
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	        return sdf.parse(dateStr);
		}
        
    }

    public static String convertDateToString(Date date) throws ParseException
    {
        if(date == null)
        {
            return "";
        }
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    
//    public static void main(String[] args){
//    	try {
//			String sdate = formatDate("2006-03-01","yyyy-MM-dd");
//			System.out.println(">>>>>>>>>>>>>>>"+sdate);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//
    /**
     * 格式化时间 yyyy-MM-dd
     * @param date
     * @return
     */
	public static String getDate(Date date){
		//时间
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
		if(date != null) {
			return sdf.format(date);
		}
		
		return "";
	}

    public static Date moveTime(Date date,int day){
        Calendar   calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        return date=calendar.getTime();   //这个时间就是日期往后推一天的结果
    }



//    public static void main(String[] args) {
//        try {
//            System.out.println(DateUtils.formatDate(DateUtils.moveTime(DateUtils.convertStringToDate("2021-09-30",DateUtils.PATTERN_DATE),1), DateUtils.PATTERN_DATE));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }
}
