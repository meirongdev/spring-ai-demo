package dev.meirong.showcase.spring_ai_demo.simpleqa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// The similar [bug issue](https://github.com/spring-projects/spring-ai/issues/4902) make the evaluator tests fail
// Can check again after v1.1.0
@SpringBootTest
class SpringAiBoardGameServiceTests {
  @Autowired
  private BoardGameService boardGameService;
  @Autowired
  private ChatClient.Builder chatClientBuilder;
  private RelevancyEvaluator relevancyEvaluator;
  private FactCheckingEvaluator factCheckingEvaluator;

  @BeforeEach
  void setup() {
    this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
    this.factCheckingEvaluator = FactCheckingEvaluator.forBespokeMinicheck(chatClientBuilder);
  }

  @Test
  void evaluateRelevancy() {
    String userText = "Where is the capital of France?";
    Question question = new Question(userText);
    Answer answer = boardGameService.askQuestion(question);
    EvaluationRequest evaluationRequest = new EvaluationRequest(
        userText, answer.answer());
    EvaluationResponse response = relevancyEvaluator
        .evaluate(evaluationRequest);

    Assertions.assertThat(response.isPass())
        .withFailMessage("""
            ========================================
            The answer "%s"
            is not considered relevant to the question
            "%s".
            ========================================
            """, answer.answer(), userText)
        .isTrue();
  }

  @Test
  void evaluateFactualAccuracy() {
    var userText = "Where is the capital of France?";
    var question = new Question(userText);
    var answer = boardGameService.askQuestion(question);
    var evaluationRequest = new EvaluationRequest(userText, answer.answer());
    var response = factCheckingEvaluator.evaluate(evaluationRequest);
    Assertions.assertThat(response.isPass())
        .withFailMessage("""
            ========================================
            The answer "%s"
            is not considered correct for the question
            "%s".
            ========================================
            """, answer.answer(), userText)
        .isTrue();
  }
}
