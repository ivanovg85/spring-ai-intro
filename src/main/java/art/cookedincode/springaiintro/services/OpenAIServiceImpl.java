package art.cookedincode.springaiintro.services;

import art.cookedincode.springaiintro.model.Answer;
import art.cookedincode.springaiintro.model.GetCapitalRequest;
import art.cookedincode.springaiintro.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Georgi Ivanov.
 */
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoPrompt;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String getAnswer(String question) {
        return chatClient.prompt(question).call().content();
    }

    @Override
    public Answer getAnswer(Question question) {
        return new Answer(chatClient.prompt(question.question()).call().content());
    }

    @Override
    public Answer getCapital(GetCapitalRequest capitalRequest) {
        Prompt prompt = PromptTemplate.builder()
                .resource(getCapitalPrompt)
                .build()
                .create(Map.of("stateOrCountry", capitalRequest.stateOrCountry()));
        String response = chatClient.prompt(prompt).call().content();
        System.out.println(response);
        String responseString;
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            responseString = jsonNode.get("answer").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Answer(responseString);
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest capitalRequest) {
        Prompt prompt = PromptTemplate.builder()
                .resource(getCapitalWithInfoPrompt)
                .build()
                .create(Map.of("stateOrCountry", capitalRequest.stateOrCountry()));
        return new Answer(chatClient.prompt(prompt).call().content());
    }
}
