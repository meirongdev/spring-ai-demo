package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SpringAiFortuneTodayService implements FortuneTodayService {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SpringAiFortuneTodayService.class);
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

    // 定义结构化输出转换器
    var outputConverter = new BeanOutputConverter<>(FortuneTodayResponse.class);

    var responseSpec = chatClient.prompt()
        .user(userSpec -> userSpec.text(templateResource)
            .param("name", userInfo.fullName())
            .param("birthday", userInfo.birthDate())
            .param("zodiac", zodiacSign)
            .param("today_date", Objects.requireNonNull(String.valueOf(LocalDate.now())))
            .param("format", outputConverter.getFormat())) // 注入格式指令
        .call();

    var chatResponse = responseSpec.chatResponse();
    if (chatResponse == null) {
        throw new RuntimeException("AI response is null");
    }
    if (chatResponse.getMetadata() != null) {
        String model = chatResponse.getMetadata().getModel();
        logger.info("Model used for generating fortune today: {}", model);
    }

    var content = chatResponse.getResult().getOutput().getText();

    if (content == null) {
        throw new RuntimeException("AI response content is null");
    }
    // 将文本响应转换为对象
    return outputConverter.convert(content);
  }
}
