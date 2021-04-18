package learn.base.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Set;

/**
 * description: --类描述
 *
 * @author lujianrong
 */

public class LocalDateTimeTest {

    /**
     * LocalDateTime 与 Date 转换
     */
    @Test
    public void test10() {

        Date date = new Date();

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date date2 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2));

    }

    /**
     * ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
     */
    @Test
    public void test7() {

        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }

    @Test
    public void test6() {

        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    /**
     * DateTimeFormatter : 解析和格式化日期或时间
      */
    @Test
    public void test5() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);

        System.out.println(strDate);

        LocalDateTime newLdt = ldt.parse(strDate, dtf);
        System.out.println(newLdt);
    }

    /**
     * TemporalAdjuster : 时间校正器
     */
    @Test
    public void test4() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;

            DayOfWeek dow = ldt4.getDayOfWeek();

            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2);
            } else {
                return ldt4.plusDays(1);
            }
        });

        System.out.println(ldt5);

    }

    /**
     * 计算两个“时间”间隔
     */
    @Test
    public void test3() {
        Instant ins1 = Instant.now();

        System.out.println("--------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        Instant ins2 = Instant.now();

        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));
        System.out.println("----------------------------------");
        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2011, 1, 1);

        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());
        System.out.println(pe.getMonths());
        System.out.println(pe.getDays());
    }

    /**
     * Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
     */
    @Test
    public void test2() {
        //默认使用 UTC 时区
        Instant ins = Instant.now();
        System.out.println(ins);

        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        System.out.println(ins.getNano());

        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);
    }

    //1. LocalDate、LocalTime、LocalDateTime
    @Test
    public void test1() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = ldt.format(formatter);

        System.out.println(formattedDateTime);

        LocalDateTime ld2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
        System.out.println(ld2);

        LocalDateTime ldt3 = ld2.plusYears(20);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ld2.minusMonths(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }


    @Test
    public void test0() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDateTime = ldt.format(formatter);
        System.out.println(formattedDateTime);
    }


}