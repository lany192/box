package cn.smallplants.client.utils;


import com.github.lany192.time.PrettyTime;
import com.github.lany192.time.units.Century;
import com.github.lany192.time.units.Day;
import com.github.lany192.time.units.Decade;
import com.github.lany192.time.units.Millennium;
import com.github.lany192.time.units.Month;
import com.github.lany192.time.units.Week;
import com.github.lany192.time.units.Year;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化工具
 */
public class TimeUtils {
    public final static SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public final static SimpleDateFormat MDHM = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINESE);
    public final static SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
    private final static PrettyTime PRETTY_TIME = new PrettyTime(Locale.CHINESE);

    /**
     * 移除下面的时间单位，只格式化一天之内的时间
     */
    static {
        PRETTY_TIME.removeUnit(Day.class);
        PRETTY_TIME.removeUnit(Week.class);
        PRETTY_TIME.removeUnit(Month.class);
        PRETTY_TIME.removeUnit(Year.class);
        PRETTY_TIME.removeUnit(Decade.class);
        PRETTY_TIME.removeUnit(Century.class);
        PRETTY_TIME.removeUnit(Millennium.class);
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

    public static String getTime(long time) {
        if (System.currentTimeMillis() - time > (24 * 60 * 60 * 1000)) {//是否超过一天
            return YMDHMS.format(new Date(time));
        } else {
            return PRETTY_TIME.format(new Date(time));
        }
    }

    public static String getTime(Date date) {
        if (System.currentTimeMillis() - date.getTime() > (24 * 60 * 60 * 1000)) {//是否超过一天
            return YMDHMS.format(date);
        } else {
            return PRETTY_TIME.format(date);
        }
    }

    public static String getShortTime(long time) {
        if (System.currentTimeMillis() - time > (24 * 60 * 60 * 1000)) {//是否超过一天
            return MDHM.format(new Date(time));
        } else {
            return PRETTY_TIME.format(new Date(time));
        }
    }

    public static String getShortTime(Date date) {
        if (System.currentTimeMillis() - date.getTime() > (24 * 60 * 60 * 1000)) {//是否超过一天
            return MDHM.format(date);
        } else {
            return PRETTY_TIME.format(date);
        }
    }

    public static String getYMD(Date date) {
        return YMD.format(date);
    }

    public static String getCouponTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return format.format(new Date(time));
    }

    public static String time2string(long time) {
        return YMDHMS.format(new Date(time));
    }

    public static long localDate2time(LocalDate localDate) {
        return localDate.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

}
