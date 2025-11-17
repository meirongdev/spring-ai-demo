## Spring AI Demo

Demo application for Spring AI features.


## Prerequisites

- Java25 or higher
- Gradle 9.2.0

## Running the Application

To run the application, use the following command:

```bash
./gradlew bootRun
```

## Ollama Setup

1. Install Ollama from [https://ollama.com/download](https://ollama.com/download).

2. Download the `mistral:7b` model by running:

   ```bash
   ollama pull mistral:7b
   ```

3. Start the Ollama server with:

   ```bash
   ollama serve
   ```
4. Verify the server is running by executing:

   ```bash
    ollama list
    ```
5. You can also check the available models via the API:


`http http://localhost:11434/api/tags -b `
```json
{
    "models": [
        {
            "details": {
                "families": [
                    "llama"
                ],
                "family": "llama",
                "format": "gguf",
                "parameter_size": "7.2B",
                "parent_model": "",
                "quantization_level": "Q4_K_M"
            },
            "digest": "6577803aa9a036369e481d648a2baebb381ebc6e897f2bb9a766a2aa7bfbc1cf",
            "model": "mistral:7b",
            "modified_at": "2025-11-17T09:24:15.891442602+08:00",
            "name": "mistral:7b",
            "size": 4372824384
        }
    ]
}
```

### ChatClient with Ollama

add dependency:

```kotlin
implementation("org.springframework.ai:spring-ai-starter-model-ollama")
```

apply configuration:

```yaml
spring:
  ai:
    ollama:
      chat:
        # http http://localhost:11434/api/tags -b to see available models
        model: mistral:7b
```
Not like `openai` or `gemini`, Ollama model don't need API key.

### Testing with Curl

```bash
curl -X POST http://localhost:8080/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "What is the capital of Singapore?"}' 
{"answer":" The capital city of Singapore is not Singapore, but rather it is a parliamentary republic. However, Singapore itself serves as both its political and economic center, making it the de facto capital. This unique arrangement is due to Singapore's constitutional structure modelled after Westminster system in the United Kingdom."}%
```
