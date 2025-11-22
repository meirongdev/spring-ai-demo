package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;
import java.util.Date;

import cn.hutool.core.date.ChineseDate;

public class ZodiacUtils {
  private ZodiacUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static String getZodiacSign(LocalDate date) {
    MonthDay md = MonthDay.from(date);
    Object[][] zodiacSigns = {
        { MonthDay.of(3, 21), MonthDay.of(4, 19), "白羊座" },
        { MonthDay.of(4, 20), MonthDay.of(5, 20), "金牛座" },
        { MonthDay.of(5, 21), MonthDay.of(6, 20), "双子座" },
        { MonthDay.of(6, 21), MonthDay.of(7, 22), "巨蟹座" },
        { MonthDay.of(7, 23), MonthDay.of(8, 22), "狮子座" },
        { MonthDay.of(8, 23), MonthDay.of(9, 22), "处女座" },
        { MonthDay.of(9, 23), MonthDay.of(10, 22), "天秤座" },
        { MonthDay.of(10, 23), MonthDay.of(11, 21), "天蝎座" },
        { MonthDay.of(11, 22), MonthDay.of(12, 21), "射手座" },
        { MonthDay.of(12, 22), MonthDay.of(1, 19), "摩羯座" },
        { MonthDay.of(1, 20), MonthDay.of(2, 18), "水瓶座" },
        { MonthDay.of(2, 19), MonthDay.of(3, 20), "双鱼座" }
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

  public static String getChineseZodiac(LocalDate date) {
    // 1. 将 LocalDate 转为 java.util.Date (Hutool 5.x 需要)
    Date utilDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

    // 2. 构建农历日期对象
    ChineseDate chineseDate = new ChineseDate(utilDate);

    // 3. 获取生肖 (Hutool 会自动处理春节界限)
    return chineseDate.getChineseZodiac();
  }
}
