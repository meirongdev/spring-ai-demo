package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class FortuneController {

  private final FortuneTodayService fortuneTodayService;

  public FortuneController(FortuneTodayService fortuneTodayService) {
    this.fortuneTodayService = fortuneTodayService;
  }

  // curl "http://localhost:8080/fortune-today?fullName=zhangsan&birthDate=1990-05-15"
  @GetMapping("/fortune-today")
  public FortuneTodayResponse getFortuneToday(@Valid UserInfo userInfo) {
    return fortuneTodayService.getFortuneToday(userInfo);
  }
}
