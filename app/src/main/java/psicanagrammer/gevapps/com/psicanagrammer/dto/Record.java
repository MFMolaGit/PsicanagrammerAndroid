package psicanagrammer.gevapps.com.psicanagrammer.dto;

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
}
