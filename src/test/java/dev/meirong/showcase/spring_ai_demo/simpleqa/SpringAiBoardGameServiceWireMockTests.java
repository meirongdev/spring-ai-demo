package dev.meirong.showcase.spring_ai_demo.simpleqa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
// Seems GoogleGenAiConnectionProperties can't be mocked properly with WireMock, so using Mockito to mock ChatModel directly
@SpringBootTest
class SpringAiBoardGameServiceWireMockTests {

    @MockitoBean
    private ChatModel chatModel;

    @Autowired
    ChatClient.Builder chatClientBuilder;

    @Test
    void testAskQuestion() {
        // Mock the ChatModel to return "Paris"
        var assistantMessage = new AssistantMessage("Paris");
        var generation = new Generation(assistantMessage);
        var chatResponse = new ChatResponse(java.util.List.of(generation));
        when(chatModel.call(any(Prompt.class))).thenReturn(chatResponse);

        var boardGameService = new SpringAiBoardGameService(chatClientBuilder);
        var answer = boardGameService.askQuestion(
            new Question("What is the capital of France?"));

        Assertions.assertThat(answer).isNotNull();
        Assertions.assertThat(answer.answer()).isEqualTo("Paris");
    }
}
