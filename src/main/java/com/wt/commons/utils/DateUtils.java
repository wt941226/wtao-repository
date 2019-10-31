package com.wt.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * 时间工具类
 *
 * @author wtao
 */
public class DateUtils {

    public static String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static String yyyy_MM_dd = "yyyy-MM-dd";
    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.yyyyMMddHHmmss);
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat(DateUtils.yyyy_MM_dd_hh_mm_ss);
    private static SimpleDateFormat dateFormat3 = new SimpleDateFormat(DateUtils.yyyy_MM_dd);
    private static SimpleDateFormat dateFormat4;

    public static LocalDateTime paseStr(String string) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.parse(string, df);
            return ldt;
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime ldt = LocalDateTime.parse(string, df);
                return ldt;
            } catch (DateTimeParseException er) {
                throw new RuntimeException("时间格式错误");
            }
        }
    }

    public static LocalDate paseStrtoLocalDate(String string) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ldt = LocalDate.parse(string, df);
            return ldt;
        } catch (DateTimeParseException er) {
            try {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDate ldt = LocalDate.parse(string, df);
                return ldt;
            } catch (DateTimeParseException e) {
                throw new RuntimeException("时间格式错误");
            }
        }
    }


    /**
     * 时间戳转LocalDatetime
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * Date转LocalDate
     */
    public static LocalDate getDateOfTimestamp(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return instant.atZone(zone).toLocalDate();
    }


    /**
     * LocalDateTime转时间戳
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * LocalDateTime转字符串
     */
    public static String timeToString(LocalDateTime time, String pattern) {
        if (null == pattern) {
            pattern = yyyy_MM_dd_hh_mm_ss;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        String localTime = df.format(time);
        return localTime;
    }

    /**
     * LocalDateTime转字符串
     */
    public static String timeToString(LocalDate time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(yyyy_MM_dd);
        String localTime = df.format(time);
        return localTime;
    }

    public static void main(String[] args) {
        String s = timeToString(LocalDateTime.now(), "HH:mm");
        System.out.println(s);
    }

    /**
     * 字符串转LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String time, String pattern) {
        if (null == time) {
            return null;
        }
        if (null == pattern) {
            pattern = yyyy_MM_dd_hh_mm_ss;
        }
        Date date = string2date(time, pattern);
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }


    /**
     * 获取当前时间
     */
    public static String now() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        return localTime;
    }

    /**
     * 获取格式化时间
     *
     * @param date   时间
     * @param format 时间格式
     * @return 时字符串
     */
    public static String getDateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return sdf.format(date);
    }

    /**
     * 时间转字符串
     *
     * @param date   时间
     * @param format 时间格式
     * @return 时间
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat formatter = getDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 字符串转long
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 时间
     * @throws ParseException 异常
     */
    public static long string2long(String time, String format) throws ParseException {
        Date date = string2date(time, format);
        return date.getTime();
    }

    /**
     * 字符串转时间
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 时间
     * @throws ParseException 异常
     */
    public static Date string2date(String time, String format) {
        SimpleDateFormat formatter = getDateFormat(format);
        try {
            return formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("时间转换错误");
        }
    }

    /**
     * 按照指定格式返回 SimpleDateFormat 对象
     *
     * @param format 指定返回 SimpleDateFormat 对象的格式
     * @return 指定格式的 SimpleDateFormat 对象
     */
    private static SimpleDateFormat getDateFormat(String format) {

        // 如果参数指定的格式属于默认格式的一种，直接返回对应的 SimpleDateFormat 对象
        if (DateUtils.yyyyMMddHHmmss.equals(format)) {
            return dateFormat;
        } else if (DateUtils.yyyy_MM_dd_hh_mm_ss.equals(format)) {
            return dateFormat2;
        } else if (DateUtils.yyyy_MM_dd.equals(format)) {
            return dateFormat3;
        } else {

            // 不属于默认格式，此时先判断是否与现有对象格式相同，不相同才创建新对象
            if (dateFormat4 == null || !format.equals(dateFormat4.toPattern())) {
                dateFormat4 = new SimpleDateFormat(format);
            }
            return dateFormat4;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 时间1
     * @param date2 时间2
     * @return 天数
     */
    public static Integer daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays;
        if ((time2 - time1) % (1000 * 3600 * 24L) != 0) {
            betweenDays = (time2 - time1) / (1000 * 3600 * 24L) + 1;
        } else {
            betweenDays = (time2 - time1) / (1000 * 3600 * 24L);
        }

        return Integer.parseInt(String.valueOf(betweenDays));

    }

    /**
     * 获取两个日期之间的天份集合
     *
     * @param minDate 小日期
     * @param maxDate 大日期
     * @throws ParseException 异常
     */
    public static List<String> getDayBetween(Date minDate, Date maxDate)
            throws ParseException {
        ArrayList<String> result = new ArrayList<>();
        // 格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(sdf.format(minDate)));
        max.setTime(sdf.parse(sdf.format(maxDate)));

        Calendar curr = min;
        while (curr.before(max) || curr.equals(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.DATE, 1);
        }

        return result;
    }

    /**
     * 获取两个日期之间的月份集合
     *
     * @param minDate 小日期
     * @param maxDate 大日期
     * @throws ParseException 异常
     */
    public static List<String> getMonthBetween(Date minDate, Date maxDate)
            throws ParseException {
        ArrayList<String> result = new ArrayList<>();
        // 格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar max = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        Calendar curr = min;
        max.setTime(sdf.parse(sdf.format(maxDate)));
        min.setTime(sdf.parse(sdf.format(minDate)));

        while (curr.before(max) || curr.equals(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 获取两个日期之间的年份集合
     *
     * @param minDate 小日期
     * @param maxDate 大日期
     * @throws ParseException 异常
     */
    public static List<String> getYearBetween(Date minDate, Date maxDate)
            throws ParseException {
        ArrayList<String> result = new ArrayList<>();
        // 格式化为年
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        max.setTime(sdf.parse(sdf.format(maxDate)));
        min.setTime(sdf.parse(sdf.format(minDate)));
        Calendar curr = min;
        while (curr.before(max) || curr.equals(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.YEAR, 1);
        }

        return result;
    }

    /**
     * 年加n
     *
     * @param date 日期
     * @param num  num
     * @return Date
     */
    public static Date getYearAdd(Date date, Integer num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);

        return calendar.getTime();
    }

    /**
     * 月份加n
     *
     * @param date 日期
     * @param num  num
     * @return Date
     */
    public static Date getMonthAdd(Date date, Integer num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);

        return calendar.getTime();
    }

    /**
     * 天n
     *
     * @param date 日期
     * @param num  num
     * @return Date
     */
    public static Date getDayAdd(Date date, Integer num) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);

        return calendar.getTime();
    }

    /**
     * 获取当前日期是星期几
     *
     * @param date 日期
     * @return String
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获得一个月最后一天
     *
     * @param date 日期(yyyy-MM-dd)
     * @return String
     */
    public static String getLastDayOfMonth(String date) {
        Integer year = Integer.valueOf(date.substring(0, 4));
        Integer month = Integer.valueOf(date.substring(6, 7));
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
}