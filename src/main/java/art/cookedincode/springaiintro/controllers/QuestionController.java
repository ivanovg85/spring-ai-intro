package art.cookedincode.springaiintro.controllers;

import art.cookedincode.springaiintro.model.Answer;
import art.cookedincode.springaiintro.model.GetCapitalRequest;
import art.cookedincode.springaiintro.model.Question;
import art.cookedincode.springaiintro.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Georgi Ivanov.
 */
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest capitalRequest){
        return openAIService.getCapital(capitalRequest);
    }

    @PostMapping("/capitalWithInfo")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest capitalRequest){
        return openAIService.getCapitalWithInfo(capitalRequest);
    }
}
