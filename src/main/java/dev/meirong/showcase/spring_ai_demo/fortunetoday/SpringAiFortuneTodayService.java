package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SpringAiFortuneTodayService implements FortuneTodayService {

  private final ChatClient chatClient;

  private final @NonNull Resource templateResource;

  public SpringAiFortuneTodayService(ChatClient.Builder chatClientBuilder,
      @Value("classpath:/promptTemplates/fortune_today.st") Resource templateResource) {
    this.chatClient = chatClientBuilder.build();
    this.templateResource = Objects.requireNonNull(templateResource, "templateResource must not be null");
  }

  @Override
  public FortuneTodayResponse getFortuneToday(UserInfo userInfo) {
    // 解析日期
    LocalDate birthDate = LocalDate.parse(userInfo.birthDate());
    // 计算星座
    String zodiacSign = Objects.requireNonNull(ZodiacUtils.getZodiacSign(birthDate), "zodiacSign must not be null");

    var fortuneText = chatClient.prompt()
        .user(userSpec -> userSpec.text(templateResource)
        .param("name", userInfo.fullName())
        .param("birthday", userInfo.birthDate())
        .param("zodiac", zodiacSign)// 传入计算好的星座
        .param("today_date", Objects.requireNonNull(String.valueOf(LocalDate.now()))))
        .call()
        .content();

    return new FortuneTodayResponse(fortuneText);
  }
}
