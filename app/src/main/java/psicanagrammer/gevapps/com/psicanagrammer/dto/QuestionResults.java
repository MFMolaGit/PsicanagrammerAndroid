package psicanagrammer.gevapps.com.psicanagrammer.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geva on 01/03/2015.
 */
public class QuestionResults implements Serializable {

    private Map<String,Integer> questions = new HashMap<>();

    public QuestionResults() {
        questions = new HashMap<>();
    }

    public void addResponse(final String question, final int response){
        questions.put(question, response);
    }

    public Map<String,Integer> getQuestions() {
        return questions;
    }

    @Override
    public String toString() {
        StringBuilder questionsResults = new StringBuilder();

        if(questions != null) {
            questionsResults.append("\n---- PREGUNTAS FINALES ----");

            for(String question:questions.keySet()) {
                questionsResults.append("\nPregunta: ").append(question);
                questionsResults.append("\nRespuesta: ").append(questions.get(question));
            }
        }

        return questionsResults.toString();
    }

}
