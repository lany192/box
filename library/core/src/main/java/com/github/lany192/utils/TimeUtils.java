package com.github.lany192.utils;

import com.github.lany192.log.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    /**
     * 计算与当前的时间差相差多少时间
     */
    public static String formatTime(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (current.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            if (current.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
                if (current.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                    int hour = current.get(Calendar.HOUR) - calendar.get(Calendar.HOUR);
                    if (hour < 1) {
                        int minute = current.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                        if (minute < 1) {
                            return "刚刚";
                        } else {
                            return minute + "分钟前";
                        }
                    }
                    return hour + "小时前";
                } else if ((calendar.get(Calendar.DAY_OF_MONTH) + 1) == current.get(Calendar.DAY_OF_MONTH)) {
                    return "昨天";
                }
            }
            return new SimpleDateFormat("MM-dd", Locale.getDefault()).format(date);
        }
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
    }

    /**
     * 计算与当前的时间差相差多少时间
     */
    public static String formatTime(long timestamp) {
        return formatTime(new Date(timestamp));
    }

    /**
     * Date转成LocalDate
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转成Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDate转成时间戳
     */
    public static long localDate2Timestamp(LocalDate localDate) {
        Date date = localDate2Date(localDate);
        return date.getTime();
    }

    /**
     * 获得当前时间字符串
     * "yyyy-MM-dd HH:mm:ss" time string
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * 获得当前时间字符串
     * "yyyy年MM月dd日HH时mm分ss秒" time string
     */
    public static String getCurrentTimeCN() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(date);
    }

    public static Date parse(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.e("时间字符串 " + dateStr + " 转Date失败:" + e.getMessage());
        }
        return null;
    }

    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.e("时间字符串 " + dateStr + " 转Date失败:" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取指定日期
     *
     * @param dateStr "yyyy-MM-dd"
     */
    public static Date getDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.e("时间字符串 " + dateStr + " 转Date失败:" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取当前时区，例如：GMT+08:00
     */
    public static String getCurrentTimeZone() {
        return TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT);
    }

    /**
     * 将时间戳转换为日期字符串
     *
     * @param pattern   格式 yyyy-MM-dd HH:mm:ss
     * @param timestamp 秒
     */
    public static String timestamp2date(long timestamp, String pattern) {
        Date date = new Date(timestamp * 1000);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 将时间戳转换为星期
     *
     * @param timestamp 毫秒
     */
    public static int timestamp2week(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 是否当天
     *
     * @param timestamp 毫秒
     */
    public static boolean isToday(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Date curDate = new Date(System.currentTimeMillis());
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(curDate);
        int curYear = curCal.get(Calendar.YEAR);
        int curMonth = curCal.get(Calendar.MONTH);
        int curDay = curCal.get(Calendar.DAY_OF_MONTH);

        return year == curYear && month == curMonth && day == curDay;
    }

    /**
     * 是否当年
     *
     * @param timestamp 毫秒
     */
    public static boolean isSameYear(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);

        Date curDate = new Date(System.currentTimeMillis());
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(curDate);
        int curYear = curCal.get(Calendar.YEAR);

        return year == curYear;
    }

    /**
     * 是否同一周
     *
     * @param timestamp 毫秒
     */
    public static boolean isSameWeek(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        Date curDate = new Date(System.currentTimeMillis());
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(curDate);
        int curWeek = curCal.get(Calendar.WEEK_OF_YEAR);

        return week == curWeek;
    }

    /**
     * 是否上一周
     *
     * @param timestamp 毫秒
     */
    public static boolean isLastWeek(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        Date curDate = new Date(System.currentTimeMillis());
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(curDate);
        int curWeek = curCal.get(Calendar.WEEK_OF_YEAR);

        return week == curWeek - 1;
    }

    /**
     * 是否下一周
     *
     * @param timestamp 毫秒
     */
    public static boolean isNextWeek(long timestamp) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        Date curDate = new Date(System.currentTimeMillis());
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(curDate);
        int curWeek = curCal.get(Calendar.WEEK_OF_YEAR);

        return week == curWeek + 1;
    }

    /**
     * 是否同一天
     *
     * @param timestamp 毫秒
     */
    public static boolean isSameDay(long timestamp, long timestamp2) {
        Date date = new Date(timestamp);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        Date date2 = new Date(timestamp2);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH);
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);

        return year == year2 && month == month2 && day == day2;
    }
}
