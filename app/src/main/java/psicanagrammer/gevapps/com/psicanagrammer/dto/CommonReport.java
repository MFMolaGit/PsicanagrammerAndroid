package psicanagrammer.gevapps.com.psicanagrammer.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geva on 21/02/2015.
 */
public class CommonReport<T extends IRegisters> implements IRegisters<T> {

    private List<T> registerList;
    private int seconds;

    public CommonReport() {
        registerList = new ArrayList<T>();
        seconds = 0;
    }

    public void addRegister(final T register){
        registerList.add(register);
        seconds += register.getSeconds();
    }

    @Override
    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        StringBuilder report = new StringBuilder(String.valueOf(seconds)).append("\n\n\t");

        for(T register: registerList) {
            report.append(register).append("\n\t");
        }

        return report.toString();
    }

}
