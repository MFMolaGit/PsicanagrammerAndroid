package psicanagrammer.gevapps.com.psicanagrammer.dto;

/**
 * Created by Geva on 27/02/2015.
 */
public enum State {

    OK("CORRECTO"), KO("INCORRECTO"), TIMEOUT("FUERA DE TIEMPO");

    private String value;

    private State(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
