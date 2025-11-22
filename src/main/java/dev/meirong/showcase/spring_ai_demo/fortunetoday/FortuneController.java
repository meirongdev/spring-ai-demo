package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

@RestController
public class FortuneController {

  private final FortuneTodayService fortuneTodayService;

  public FortuneController(FortuneTodayService fortuneTodayService) {
    this.fortuneTodayService = fortuneTodayService;
  }

  // curl "http://localhost:8080/fortune-today?fullName=zhangsan&birthDate=1990-05-15"
  @GetMapping(value="/fortune-today", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> getFortuneToday(@Valid UserInfo userInfo) {
    return fortuneTodayService.getFortuneToday(userInfo);
  }
}
