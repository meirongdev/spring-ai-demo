package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import java.util.List;

public record FortuneTodayResponse(
    String name,
    String zodiac,
    String chineseZodiac,
    String overallRating,
    String overallSummary,
    String loveFortune,
    String careerFortune,
    String wealthFortune,
    String healthFortune,
    String luckyColor,
    String luckyNumber,
    String luckyDirection,
    List<String> advice) {
}
