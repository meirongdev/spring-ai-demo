package dev.meirong.showcase.spring_ai_demo.fortunetoday;


import java.time.LocalDate;
import java.time.MonthDay;

public class ZodiacUtils {
  private ZodiacUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String getZodiacSign(LocalDate date) {
    MonthDay md = MonthDay.from(date);
    Object[][] zodiacSigns = {
      {MonthDay.of(3, 21), MonthDay.of(4, 19), "白羊座"},
      {MonthDay.of(4, 20), MonthDay.of(5, 20), "金牛座"},
      {MonthDay.of(5, 21), MonthDay.of(6, 20), "双子座"},
      {MonthDay.of(6, 21), MonthDay.of(7, 22), "巨蟹座"},
      {MonthDay.of(7, 23), MonthDay.of(8, 22), "狮子座"},
      {MonthDay.of(8, 23), MonthDay.of(9, 22), "处女座"},
      {MonthDay.of(9, 23), MonthDay.of(10, 22), "天秤座"},
      {MonthDay.of(10, 23), MonthDay.of(11, 21), "天蝎座"},
      {MonthDay.of(11, 22), MonthDay.of(12, 21), "射手座"},
      {MonthDay.of(12, 22), MonthDay.of(1, 19), "摩羯座"},
      {MonthDay.of(1, 20), MonthDay.of(2, 18), "水瓶座"},
      {MonthDay.of(2, 19), MonthDay.of(3, 20), "双鱼座"}
    };

    for (Object[] zodiac : zodiacSigns) {
      MonthDay start = (MonthDay) zodiac[0];
      MonthDay end = (MonthDay) zodiac[1];
      String sign = (String) zodiac[2];
      if ((md.isAfter(start) || md.equals(start)) && (md.isBefore(end) || md.equals(end))) {
        return sign;
      }
    }
    return "";
  }
}
