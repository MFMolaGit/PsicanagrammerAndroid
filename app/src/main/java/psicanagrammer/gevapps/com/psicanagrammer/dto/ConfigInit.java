package psicanagrammer.gevapps.com.psicanagrammer.dto;

import java.io.Serializable;

/**
 * Created by Geva on 01/03/2015.
 */
public class ConfigInit implements Serializable{

    private static final long serialVersionUID = 1L;

    private int timeLimit;
    private String pacientName;
    private int groupSelected;
    private boolean totaltimeEnabled;
    private boolean timeleftEnabled;
    private boolean correctsEnabled;
    private boolean correctsOverTotalEnabled;
    private boolean failsEnabled;
    private boolean failsOverTotalEnabled;

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getPacientName() {
        return pacientName;
    }

    public void setPacientName(String pacientName) {
        this.pacientName = pacientName;
    }

    public int getGroupSelected() {
        return groupSelected;
    }

    public void setGroupSelected(int groupSelected) {
        this.groupSelected = groupSelected;
    }

    public boolean isTotaltimeEnabled() {
        return totaltimeEnabled;
    }

    public void setTotaltimeEnabled(boolean totaltimeEnabled) {
        this.totaltimeEnabled = totaltimeEnabled;
    }

    public boolean isTimeleftEnabled() {
        return timeleftEnabled;
    }

    public void setTimeleftEnabled(boolean timeleftEnabled) {
        this.timeleftEnabled = timeleftEnabled;
    }

    public boolean isCorrectsEnabled() {
        return correctsEnabled;
    }

    public void setCorrectsEnabled(boolean correctsEnabled) {
        this.correctsEnabled = correctsEnabled;
    }

    public boolean isCorrectsOverTotalEnabled() {
        return correctsOverTotalEnabled;
    }

    public void setCorrectsOverTotalEnabled(boolean correctsOverTotalEnabled) {
        this.correctsOverTotalEnabled = correctsOverTotalEnabled;
    }

    public boolean isFailsEnabled() {
        return failsEnabled;
    }

    public void setFailsEnabled(boolean failsEnabled) {
        this.failsEnabled = failsEnabled;
    }

    public boolean isFailsOverTotalEnabled() {
        return failsOverTotalEnabled;
    }

    public void setFailsOverTotalEnabled(boolean failsOverTotalEnabled) {
        this.failsOverTotalEnabled = failsOverTotalEnabled;
    }
}
