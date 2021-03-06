package psicanagrammer.gevapps.com.psicanagrammer.dto;

import org.xmlpull.v1.XmlSerializer;

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

    public void toXML(final XmlSerializer serializer) {
        try {
            serializer.startTag("","preguntas");
                for(String question:questions.keySet()) {
                    serializer.startTag("","pregunta");
                        serializer.startTag("","enunciado");
                            serializer.text(question);
                        serializer.endTag("","enunciado");
                        serializer.startTag("","respuesta");
                            serializer.text(String.valueOf(questions.get(question)));
                        serializer.endTag("","respuesta");
                    serializer.endTag("","pregunta");
                }
            serializer.endTag("","preguntas");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
