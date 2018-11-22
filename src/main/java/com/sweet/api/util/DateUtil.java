package com.sweet.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title: 时间操作工具
 * </p>
 * <p>
 * Description: SOC基础技术平台
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author sbruan
 * @version 1.0
 */
public class DateUtil {
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static Date parseDate(java.sql.Date date) {
        return date;
    }

    public static java.sql.Date parseSqlDate(Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }

    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                java.text.DateFormat df = new SimpleDateFormat(format);
                result = df.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    public static String format1(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String format3(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.SECOND);
    }

    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        //int minute = c.get(Calendar.MINUTE);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    public static String getDate(Date date, String formatStr) {
        return format(date, formatStr);
    }

    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String addMINUTE(Date date, Integer interval) {
        interval = interval == null ? 0 : interval;
        date = (date == null) ? new Date() : date;
        Calendar c = Calendar.getInstance();
        int minute = c.get(Calendar.MINUTE);
        date.setMinutes(minute + interval);
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期相加
     *
     * @param date Date
     * @param day  int
     * @return Date
     */
    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    /**
     * 日期相减
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 日期相减(返回秒值)
     *
     * @param date  Date
     * @param date1 Date
     * @return int
     * @author
     */
    public static Long diffDateTime(Date date, Date date1) {
        return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
    }

    public static Date getdate(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static Date getdate1(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date);
    }

    public static Date getMaxTimeByStringDate(String date) throws Exception {
        String maxTime = date + " 23:59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(maxTime);
    }

    public static Date getMinTimeByStringDate(String date) throws Exception {
        String maxTime = date + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(maxTime);
    }

    /**
     * 获得当前时间
     *
     * @return
     */
    public static Date getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = DateUtil.getDateTime(date);
        try {
            return sdf.parse(result);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public static String getCurrentDateTimeToStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(getCurrentDateTime());
    }

    public static String getCurrentDateTimeToStr2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(getCurrentDateTime());
    }

    public static Long getWmsupdateDateTime() {
        Calendar cl = Calendar.getInstance();

        return cl.getTimeInMillis();
    }

    /**
     * 根据字符串返回指定格式的日期
     *
     * @param dateStr 日期(字符串)
     * @param format  日期格式
     * @return 日期(Date)
     * @throws ParseException
     */
    public static Date convertDate(String dateStr, String format) throws ParseException {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        date = simpleDateFormat.parse(dateStr);
        return date;
    }

    public static Integer getLeftSeconds(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date condition = sdf.parse(date);
        long n = condition.getTime();
        long s = sdf.parse(getCurrentDateTimeToStr2()).getTime();
        // System.out.println("开始时间:"+date+"-->"+(int)((s-n)/1000));
        return (int) ((s - n) / 1000);
    }

    public static void main(String[] args) {
        try {

            Date date = new Date();
//			date.setMinutes(date.getMinutes() + 1);
//			date.setHours(date.getHours() + 120);

//			Date myDate = addDate(date, -1);
//	    	System.out.println(getDateTime(myDate));

//			String timeStr = format(date, "yyyy.MM.dd");
//			System.out.println(timeStr);

            date.setMinutes(date.getMinutes() + 1);
            date.setHours(date.getHours() + 120);

            System.out.println(DateUtil.getDateTime(date));

            Date date2 = new Date();
            Date endTime = DateUtil.addDate(date2, -5);//专题结束时间
            System.out.println(DateUtil.getDateTime(endTime));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
