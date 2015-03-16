package psicanagrammer.gevapps.com.psicanagrammer.dto;

import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 21/02/2015.
 */
public class Phase implements Cloneable {

    private String name;
    private List<Record> registerList;
    private int seconds;
    private int correctSeconds, failSeconds, criterialSeconds;
    private int correctsCount;
    private int failsCount;
    private int timeoutsCount;
    private int total, totalLoaded;
    private int criterialTraining, criterialControl;
    private boolean goalCriterialTraining;
    private float okPercent, koPercent, toPercent;
    private float ok2Percent, to2Percent;
    private float correctResponseLatency, responseLatency;

    private Map<String,String> solutions;

    public Phase() {}

    public Phase(final String name) {
        registerList = new ArrayList<Record>();
        seconds = 0;
        this.name = name;
        solutions = new HashMap<String,String>();
        correctSeconds = failSeconds = criterialSeconds = criterialControl = criterialTraining = 0;
        goalCriterialTraining = false;
    }

    public void addRegister(final Record register){
        registerList.add(register);

        State state = register.getState();
        if( state == State.OK || state == State.TIMEOUT)
            seconds += register.getSeconds();

        if(state != null) {
            switch(state) {
                case OK: correctsCount++;
                    correctSeconds += register.getSeconds();
                    criterialSeconds += register.getSeconds();

                    if((++criterialControl) == Constants.CORRECT_TIMES_CRITERIAL
                            && criterialSeconds <= Constants.TIMERATE_CRITERIAL) {
                        goalCriterialTraining = true;
                        criterialTraining++;
                    }

                    break;
                case KO: failsCount++;
                    failSeconds += register.getSeconds();
                    criterialSeconds = criterialControl = 0;
                    goalCriterialTraining = false;
                    break;
                case TIMEOUT: timeoutsCount++;
                    criterialSeconds = criterialControl = 0;
                    goalCriterialTraining = false;
                    break;
            }
        }
    }

    public int getCriterialTraining() {
        return criterialTraining;
    }

    public boolean isGoalCriterialTraining() {
        boolean isGoal = goalCriterialTraining;
          goalCriterialTraining = false;
        return isGoal;
    }

    public int getTotalLoaded() {
        return totalLoaded;
    }

    public void setTotalLoaded(int totalLoaded) {
        this.totalLoaded = totalLoaded;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAnagram(final int anagram) {
       return registerList.get(anagram).getAnagram();
    }

    public Record getRegister(final int anagram) {
        return registerList.get(anagram);
    }

    public void addSolution(final String anagram, final String solution) {
        solutions.put(anagram,solution);
    }

    public String getSolution(final String anagram) {
        return solutions.get(anagram);
    }

    public int getSizeAnagramsByPhase() {
        return registerList.size();
    }

    public List<Record> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(List<Record> registerList) {
        this.registerList = registerList;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(final int seconds) {
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCorrectsCount() {
        return correctsCount;
    }

    public void setCorrectsCount(int correctsCount) {
        this.correctsCount = correctsCount;
    }

    public int getFailsCount() {
        return failsCount;
    }

    public void setFailsCount(int failsCount) {
        this.failsCount = failsCount;
    }

    public int getTimeoutsCount() {
        return timeoutsCount;
    }

    public void setTimeoutsCount(int timeoutsCount) {
        this.timeoutsCount = timeoutsCount;
    }

    public int getCorrectSeconds() {
        return correctSeconds;
    }

    public void setCorrectSeconds(int correctSeconds) {
        this.correctSeconds = correctSeconds;
    }

    public int getFailSeconds() {
        return failSeconds;
    }

    public void setFailSeconds(int failSeconds) {
        this.failSeconds = failSeconds;
    }

    public int getCriterialSeconds() {
        return criterialSeconds;
    }

    public void setCriterialSeconds(int criterialSeconds) {
        this.criterialSeconds = criterialSeconds;
    }

    public void setCriterialTraining(int criterialTraining) {
        this.criterialTraining = criterialTraining;
    }

    public float getOkPercent() {
        return okPercent;
    }

    public void setOkPercent(float okPercent) {
        this.okPercent = okPercent;
    }

    public float getKoPercent() {
        return koPercent;
    }

    public void setKoPercent(float koPercent) {
        this.koPercent = koPercent;
    }

    public float getToPercent() {
        return toPercent;
    }

    public void setToPercent(float toPercent) {
        this.toPercent = toPercent;
    }

    public float getOk2Percent() {
        return ok2Percent;
    }

    public void setOk2Percent(float ok2Percent) {
        this.ok2Percent = ok2Percent;
    }

    public float getTo2Percent() {
        return to2Percent;
    }

    public void setTo2Percent(float to2Percent) {
        this.to2Percent = to2Percent;
    }

    public float getCorrectResponseLatency() {
        return correctResponseLatency;
    }

    public void setCorrectResponseLatency(float correctResponseLatency) {
        this.correctResponseLatency = correctResponseLatency;
    }

    public float getResponseLatency() {
        return responseLatency;
    }

    public void setResponseLatency(float responseLatency) {
        this.responseLatency = responseLatency;
    }

    public int getCriterialControl() {
        return criterialControl;
    }

    public void setCriterialControl(int criterialControl) {
        this.criterialControl = criterialControl;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Phase clonedPhase = (Phase) super.clone();

        clonedPhase.setRegisterList(new ArrayList<Record>());
        clonedPhase.setSeconds(0);
        clonedPhase.setCorrectsCount(0);
        clonedPhase.setFailsCount(0);
        clonedPhase.setTimeoutsCount(0);

        return clonedPhase;
    }

    private void calculateStatistics() {
        total = registerList.size();
        okPercent = ((float)(correctsCount*100))/total;
        koPercent = ((float)(failsCount*100))/total;
        toPercent = ((float)(timeoutsCount*100))/total;

        ok2Percent = ((float)(correctsCount*100))/(total - failsCount);
        to2Percent = ((float)(timeoutsCount*100))/(total - failsCount);

        correctResponseLatency = ((float)correctSeconds)/correctsCount;
        responseLatency = ((float)(correctSeconds + failSeconds))/(correctsCount + failsCount);
    }

    @Override
    public String toString() {

        calculateStatistics();

        StringBuilder report = new StringBuilder();
                report.append("\n---- RESULTADO DE FASE ----")
                .append("\nNombre: ").append(name)
                .append("\nTiempo: ").append(seconds)
                .append("\n---- SOBRE TOTAL DE RESPUESTAS ----")
                .append("\nAciertos: ").append(String.valueOf(correctsCount))
                .append("/").append(String.valueOf(total)).append("(").append(okPercent).append("%").append(")")
                .append("\nFallos: ").append(String.valueOf(failsCount))
                .append("/").append(String.valueOf(total)).append("(").append(koPercent).append("%").append(")")
                .append("\nFuera de tiempo: ").append(String.valueOf(timeoutsCount))
                .append("/").append(String.valueOf(total)).append("(").append(toPercent).append("%").append(")")
                .append("\n---- SOBRE TOTAL DE PALABRAS ----")
                .append("\nAciertos: ").append(String.valueOf(correctsCount))
                .append("/").append(String.valueOf(totalLoaded)).append("(").append(ok2Percent).append("%").append(")")
                .append("\nFuera de tiempo: ").append(String.valueOf(timeoutsCount))
                .append("/").append(String.valueOf(totalLoaded)).append("(").append(to2Percent).append("%").append(")")
                .append("\n---- LATENCIAS ----")
                .append("\nLatencia de respuesta correcta: ").append(correctResponseLatency)
                .append("\nLatencia de respuesta: ").append(responseLatency)
                .append("\n---- ENSAYO DE CRITERIO ----")
                .append("\nCriterio veces: ").append(Constants.CORRECT_TIMES_CRITERIAL)
                .append("\nCriterio tiempo: ").append(Constants.TIMERATE_CRITERIAL)
                .append("\nRatio: ").append(criterialTraining).append(" veces")
                .append("\n---- RESULTADOS ----");
        for(Record register: registerList) {
            report.append(register).append("\n");
        }

        return report.toString();
    }

    public void toXML(XmlSerializer serializer) {

        calculateStatistics();

        try {
            serializer.startTag("", "fase");
                serializer.startTag("","nombre");
                    serializer.text(name);
                serializer.endTag("","nombre");
                serializer.startTag("","tiempo");
                    serializer.text(String.valueOf(seconds));
                serializer.endTag("","tiempo");
                serializer.startTag("","sobreRespuestas");
                    serializer.startTag("","total");
                        serializer.text(String.valueOf(total));
                    serializer.endTag("","total");
                    serializer.startTag("","aciertos");
                        serializer.startTag("","valor");
                            serializer.text(String.valueOf(correctsCount));
                        serializer.endTag("","valor");
                        serializer.startTag("","porcentaje");
                            serializer.text(String.valueOf(okPercent));
                        serializer.endTag("","porcentaje");
                    serializer.endTag("","aciertos");
                    serializer.startTag("","fallos");
                        serializer.startTag("","valor");
                            serializer.text(String.valueOf(failsCount));
                        serializer.endTag("","valor");
                        serializer.startTag("","porcentaje");
                            serializer.text(String.valueOf(koPercent));
                        serializer.endTag("","porcentaje");
                    serializer.endTag("","fallos");
                    serializer.startTag("","fueraTiempo");
                        serializer.startTag("","valor");
                            serializer.text(String.valueOf(timeoutsCount));
                        serializer.endTag("","valor");
                        serializer.startTag("","porcentaje");
                            serializer.text(String.valueOf(toPercent));
                        serializer.endTag("","porcentaje");
                    serializer.endTag("","fueraTiempo");
                serializer.endTag("","sobreRespuestas");
                    serializer.startTag("","sobrePalabras");
                    serializer.startTag("","total");
                        serializer.text(String.valueOf(totalLoaded));
                    serializer.endTag("","total");
                    serializer.startTag("","aciertos");
                        serializer.startTag("","valor");
                            serializer.text(String.valueOf(correctsCount));
                        serializer.endTag("","valor");
                        serializer.startTag("","porcentaje");
                            serializer.text(String.valueOf(ok2Percent));
                        serializer.endTag("","porcentaje");
                    serializer.endTag("","aciertos");
                    serializer.startTag("","fueraTiempo");
                        serializer.startTag("","valor");
                            serializer.text(String.valueOf(timeoutsCount));
                        serializer.endTag("","valor");
                        serializer.startTag("","porcentaje");
                            serializer.text(String.valueOf(to2Percent));
                        serializer.endTag("","porcentaje");
                    serializer.endTag("","fueraTiempo");
                serializer.endTag("","sobrePalabras");
                serializer.startTag("","latencia");
                    serializer.startTag("","correcta");
                        serializer.text(String.valueOf(correctResponseLatency));
                    serializer.endTag("","correcta");
                    serializer.startTag("","general");
                        serializer.text(String.valueOf(responseLatency));
                    serializer.endTag("","general");
                serializer.endTag("","latencia");
                serializer.startTag("","ensayoCriterio");
                    serializer.startTag("","veces");
                        serializer.text(String.valueOf(Constants.CORRECT_TIMES_CRITERIAL));
                    serializer.endTag("","veces");
                    serializer.startTag("","tiempo");
                        serializer.text(String.valueOf(Constants.TIMERATE_CRITERIAL));
                    serializer.endTag("","tiempo");
                    serializer.startTag("","ratio");
                        serializer.text(String.valueOf(criterialTraining));
                    serializer.endTag("","ratio");
                serializer.endTag("","ensayoCriterio");
                serializer.startTag("","registros");
                for(Record register: registerList) {
                    register.toXML(serializer);
                }
                serializer.endTag("","registros");
            serializer.endTag("","fase");
        } catch(Exception e) {
                System.out.println(e.getMessage());
        }
    }
}