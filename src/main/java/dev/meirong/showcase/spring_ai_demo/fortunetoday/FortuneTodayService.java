package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import reactor.core.publisher.Flux;

public interface FortuneTodayService {
  Flux<String> getFortuneToday(UserInfo userInfo);
}
