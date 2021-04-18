package learn.base.myutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具
 *
 * @author MuggleStar
 * @date 2020/12/13 20:38
 */
public class DateUtil {

    public static String dateToString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    public static String dateToString(LocalDateTime localDateTime, String format) {
        return DateTimeFormatter.ofPattern(format).format(localDateTime);
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date stringToDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date stringToDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime stringToLocalDate(String dateStr) {
        try {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getDayBegin(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getDayEnd(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDayBegin(date));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    public static Date getWeekBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.WEEK_OF_MONTH, -1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getWeekBegin(date));
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    public static Date getMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDayBegin(calendar.getTime());
    }

    public static Date getMonthEnd(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getMonthBegin(date));
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }


    public static Date getYearBegin(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        return getMonthBegin(calendar.getTime());
    }


    public static Date getYearEnd(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getYearBegin(date));
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.MILLISECOND, -1);

        return calendar.getTime();
    }

    public static LocalDateTime getDayBegin(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    public static LocalDateTime getDayEnd(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }


    public static LocalDateTime getWeekBegin(LocalDateTime localDateTime) {

        return LocalDateTime.of(localDateTime.toLocalDate().with(DayOfWeek.MONDAY), LocalTime.MIN);
    }

    public static LocalDateTime getWeekEnd(LocalDateTime localDateTime) {

        return LocalDateTime.of(localDateTime.toLocalDate().with(DayOfWeek.SUNDAY), LocalTime.MAX);
    }


    public static LocalDateTime getMonthBegin(LocalDateTime localDateTime) {

        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    public static LocalDateTime getMonthEnd(LocalDateTime localDateTime) {

        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }


    public static LocalDateTime getYearBegin(LocalDateTime localDateTime) {

        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    public static LocalDateTime getYearEnd(LocalDateTime localDateTime) {

        return LocalDateTime.of(localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }

    public static Date plusSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static Date plusMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date plusHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date plusDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static Date plusMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date plusYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

}
