package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class SpringAiFortuneTodayService implements FortuneTodayService {
  private static final Logger logger = LoggerFactory.getLogger(SpringAiFortuneTodayService.class);

  private final ChatClient chatClient;
  private final @NonNull Resource templateResource;

  public SpringAiFortuneTodayService(ChatClient.Builder chatClientBuilder,
      @Value("classpath:/promptTemplates/fortune_today.st") Resource templateResource) {
    this.chatClient = chatClientBuilder.build();
    this.templateResource = Objects.requireNonNull(templateResource, "templateResource must not be null");
  }

  @Override
  public Flux<String> getFortuneToday(UserInfo userInfo) {
    LocalDate birthDate = LocalDate.parse(userInfo.birthDate());
    String zodiacSign = Objects.requireNonNull(ZodiacUtils.getZodiacSign(birthDate), "zodiacSign must not be null");

    var outputConverter = new BeanOutputConverter<>(FortuneTodayResponse.class);

    return chatClient.prompt()
        .user(userSpec -> userSpec.text(templateResource)
            .param("name", userInfo.fullName())
            .param("birthday", userInfo.birthDate())
            .param("zodiac", zodiacSign)
            .param("today_date", Objects.requireNonNull(String.valueOf(LocalDate.now())))
            .param("format", outputConverter.getFormat()))
        .stream()
        .chatResponse() // 1. 改用 chatResponse() 获取完整对象流
        .doOnNext(response -> { // 2. 使用 doOnNext 偷看元数据 (副作用)
            if (response.getMetadata() != null && response.getMetadata().getUsage() != null) {
                var usage = response.getMetadata().getUsage();
                // 注意：在流式传输中，Usage 信息通常只在最后一个或最后几个 chunk 中出现
                // 或者是累积的，具体取决于模型提供商
                if (usage.getTotalTokens() > 0) {
                    logger.info("Token Usage -> Prompt: {}, Generation: {}, Total: {}",
                        usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());
                }
            }
        })
        .map(response -> { // 3. 将 ChatResponse 映射回 String 内容
            String content = response.getResult().getOutput().getText();
            // 某些 chunk (如最后一个包含 usage 的 chunk) content 可能为 null
            return content != null ? content : "";
        });
  }
}
