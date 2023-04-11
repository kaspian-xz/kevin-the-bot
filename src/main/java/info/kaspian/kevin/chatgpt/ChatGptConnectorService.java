package info.kaspian.kevin.chatgpt;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import info.kaspian.kevin.ChatModelConnectorService;
import info.kaspian.kevin.ChatRequest;
import info.kaspian.kevin.ChatResponse;
import info.kaspian.kevin.ChatRole;
import info.kaspian.kevin.HistoryItem;
import io.quarkus.runtime.Startup;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Startup
@ApplicationScoped
public class ChatGptConnectorService implements ChatModelConnectorService {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGptConnectorService.class);

    @ConfigProperty(name = "openai.apiKey")
    String apiKey;


    @ConfigProperty(name = "openai.timeout", defaultValue = "20")
    Long apiTimeoutSeconds;

    @ConfigProperty(name = "openai.model", defaultValue = "gpt-3.5-turbo")
    String model;

    private OpenAiService service;

    @PostConstruct
    void init() {
        LOG.info("Starting ChatGPT client");
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(apiTimeoutSeconds));
    }

    @Override
    public ChatResponse request(ChatRequest request) {
        return new ChatGtpResponse(service.streamChatCompletion(buildRequest(request.getItems()))
                .filter(chatCompletionChunk -> chatCompletionChunk.getChoices().stream().findFirst().map(ChatCompletionChoice::getMessage).map(ChatMessage::getContent).isPresent())
                .doOnError(e -> LOG.error("Error while processing result", e))
                .map(chatCompletionChunk -> chatCompletionChunk.getChoices().stream().findFirst().orElseThrow())
                .map(ChatCompletionChoice::getMessage)
                .map(ChatMessage::getContent));
    }

    private ChatCompletionRequest buildRequest(List<HistoryItem> history) {
        final List<ChatMessage> messages = history.stream()
                .map(item -> new ChatMessage(from(item.role()).value(), item.message()))
                .collect(toList());

        return ChatCompletionRequest
                .builder()
                .model(model)
                .messages(messages)
                .n(1)
                .build();
    }

    private ChatMessageRole from(ChatRole role) {
        return switch (role) {
            case USER -> ChatMessageRole.USER;
            case SYSTEM -> ChatMessageRole.SYSTEM;
            case ASSISTANT -> ChatMessageRole.ASSISTANT;
        };
    }
}
