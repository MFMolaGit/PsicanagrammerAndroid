package psicanagrammer.gevapps.com.psicanagrammer.dto;

import org.xmlpull.v1.XmlSerializer;

import java.text.SimpleDateFormat;
import java.util.Date;

import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 20/02/2015.
 */
public class Record implements Cloneable {

    private Date timestamp;
    private String anagram, response, solution;
    private int seconds;
    private State state;

    public Record() {}

    public Record(final String anagram, final String solution) {
        this.anagram = anagram;
        this.solution = solution;
    }

    @Override
    public String toString() {
        return (timestamp!=null? Constants.SIMPLE_DATE_FORMAT.format(timestamp):"")+","+anagram+","+solution+","+response+","+seconds+", " + state.value();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCorrect(State isCorrect) {
        this.state = isCorrect;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAnagram() {
        return anagram;
    }

    public void setAnagram(String anagram) {
        this.anagram = anagram;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Record clonedRecord = (Record) super.clone();
        clonedRecord.setSeconds(0);
        return clonedRecord;
    }

    public void toXML(XmlSerializer serializer) {
        try {
            serializer.startTag("","registro");
                serializer.startTag("","fecha");
                    serializer.text(timestamp!=null? Constants.SIMPLE_DATE_FORMAT.format(timestamp):"");
                serializer.endTag("", "fecha");
                serializer.startTag("","anagrama");
                    serializer.text(anagram);
                serializer.endTag("", "anagrama");
                serializer.startTag("","solucion");
                    serializer.text(solution);
                serializer.endTag("","solucion");
                serializer.startTag("","respuesta");
                    serializer.text(response);
                serializer.endTag("","respuesta");
                serializer.startTag("","tiempo");
                    serializer.text(String.valueOf(seconds));
                serializer.endTag("","tiempo");
                serializer.startTag("","estado");
                    serializer.text(state.value());
                serializer.endTag("","estado");
            serializer.endTag("", "registro");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
