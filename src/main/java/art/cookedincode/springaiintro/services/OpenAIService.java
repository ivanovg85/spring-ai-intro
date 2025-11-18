package art.cookedincode.springaiintro.services;

import art.cookedincode.springaiintro.model.Answer;
import art.cookedincode.springaiintro.model.GetCapitalRequest;
import art.cookedincode.springaiintro.model.Question;

/**
 * Created by Georgi Ivanov.
 */
public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapital(GetCapitalRequest capitalRequest);

    Answer getCapitalWithInfo(GetCapitalRequest capitalRequest);
}
