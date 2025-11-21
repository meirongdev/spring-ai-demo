package dev.meirong.showcase.spring_ai_demo.fortunetoday;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotBlank;

public record UserInfo(
  @NonNull @NotBlank(message = "姓名不能为空")
  String fullName,
  @NonNull @NotBlank(message = "生日不能为空")
  String birthDate) {
}
