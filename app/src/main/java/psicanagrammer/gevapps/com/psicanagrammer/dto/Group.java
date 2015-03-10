package psicanagrammer.gevapps.com.psicanagrammer.dto;

import java.util.ArrayList;
import java.util.List;

import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 21/02/2015.
 */
public class Group implements Cloneable {

    private String name;
    private List<Phase> registerList;
    private int seconds;
    private int correctSeconds, failSeconds;
    private int correctsCount;
    private int failsCount;
    private int timeoutsCount;
    private int totalLoaded, total;
    private int generalCriterialTraining;
    private float okPercent, koPercent, toPercent;
    private float ok2Percent, to2Percent;
    private float correctResponseLatency, responseLatency;

    public Group(final String name) {
        registerList = new ArrayList<Phase>();
        seconds = generalCriterialTraining = 0;
        this.name = name;
    }

    public void addRegister(final Phase register){
        registerList.add(register);
        seconds += register.getSeconds();
        totalLoaded += register.getSizeAnagramsByPhase();
        register.setTotalLoaded(register.getSizeAnagramsByPhase());
    }

    public void addRegister(final int actualPhase, final Record register){
        registerList.get(actualPhase).addRegister(register);

        State state = register.getState();
        if( state == State.OK || state == State.TIMEOUT)
            seconds += register.getSeconds();

        total++;

        if(registerList.get(actualPhase).isGoalCriterialTraining()) {
            generalCriterialTraining++;
        }

        if(state != null) {
            switch (state) {
                case OK: correctsCount++;
                    correctSeconds += register.getSeconds();
                    break;
                case KO: failsCount++;
                    failSeconds += register.getSeconds();
                    break;
                case TIMEOUT: timeoutsCount++;
                    break;
            }
        }
    }

    public String getAnagram(final int phase, final int anagram) {
        return registerList.get(phase).getAnagram(anagram);
    }

    public Record getRegister(final int phase, final int anagram) {
        return registerList.get(phase).getRegister(anagram);
    }

    public String getSolution(final int phase, final String anagram) {
        Phase actualPhase = registerList.get(phase);
        return actualPhase.getSolution(anagram);
    }

    public int getSizePhasesByGroup() {
        return registerList.size();
    }

    public int getSizeAnagramsByPhase(final int phase) {
        return registerList.get(phase).getSizeAnagramsByPhase();
    }

    public List<Phase> getRegisterList() {
        return registerList;
    }

    private void setRegisterList(final List<Phase> registerList) {
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

    public int getGeneralCriterialTraining() {
        return generalCriterialTraining;
    }

    public void setGeneralCriterialTraining(int generalCriterialTraining) {
        this.generalCriterialTraining = generalCriterialTraining;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Group clonedGroup = (Group) super.clone();
        List<Phase> clonedRegisterList = new ArrayList<>();

        clonedGroup.setSeconds(0);
        clonedGroup.setCorrectsCount(0);
        clonedGroup.setFailsCount(0);
        clonedGroup.setTimeoutsCount(0);

        for(Phase phase:clonedGroup.getRegisterList()) {
            Phase clonedPhase = (Phase)phase.clone();
            clonedRegisterList.add(clonedPhase);
        }

        clonedGroup.setRegisterList(clonedRegisterList);

        return clonedGroup;
    }

    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();

        if(seconds != 0) {
            float okPercent = ((float)(correctsCount*100))/total;
            float koPercent = ((float)(failsCount*100))/total;
            float toPercent = ((float)(timeoutsCount*100))/total;

            float ok2Percent = ((float)(correctsCount*100))/(total - failsCount);
            float to2Percent = ((float)(timeoutsCount*100))/(total - failsCount);

            float correctResponseLatency = ((float)correctSeconds)/correctsCount;
            float responseLatency = ((float)(correctSeconds + failSeconds))/(correctsCount + failsCount);
               report.append("\n---- RESULTADO DE GRUPO ----")
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
                   .append("\nRatio: ").append(generalCriterialTraining)
                    .append("\n---- RESULTADOS POR FASE ----\n");

            for (Phase register : registerList) {
                report.append(register);
            }
        }
        return report.toString();
    }
}
