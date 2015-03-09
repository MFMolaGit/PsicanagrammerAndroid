package psicanagrammer.gevapps.com.psicanagrammer.engine;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Phase;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Record;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

/**
 * Created by Geva on 20/02/2015.
 */
public class Loader {
    private Activity activity;
    private List<Group> groupsLoaded;


    //Data structure initial lenghts
    private int sizePhasesByGroup;
    private int sizeAnagramsByPhase;
    private int sizeGroups;

    private int configuredCounterGroup;
    private int actualCounterPhase;
    private int actualCounterAnagram;
    private String actualAnagram;
    private String actualSolution;
    private boolean endAnagrams, endPhases;

    public Loader(final Activity activity, final int configuredCounterGroup) {
        this.activity = activity;
        this.configuredCounterGroup = configuredCounterGroup;
        setup();
    }

    private void setup() {

        actualCounterPhase = actualCounterAnagram = 0;
        groupsLoaded = new ArrayList<>();

        //Groups size unknown -> do...while
        StringBuilder sIdGroup = new StringBuilder(Constants.SINGLE_GROUP);
        boolean moreGroups;
        int i = 1;

            do {
                sIdGroup.append(i);
                int idGroup = ActivityUtils.getIdentifier(activity, "string", sIdGroup.toString());
                StringBuilder sIdComplexGroup = new StringBuilder(sIdGroup);
                sIdComplexGroup.append(Constants.JUNCTION).append(Constants.COMPLEX_PHASE);
                int idArrayPhases = ActivityUtils.getIdentifier(activity, "array", sIdComplexGroup.toString());

                if(idGroup != 0) {
                    String groupName = activity.getString(idGroup);
                    Group group = new Group(groupName);
                    String[] phasesArray = activity.getResources().getStringArray(idArrayPhases);
                    StringBuilder sIdPhase = new StringBuilder(Constants.SINGLE_PHASE);

                    //Phase size known -> for
                    for(int j = 0; j < phasesArray.length; j++) {
                        String phaseName = phasesArray[j];
                        int idPhase = ActivityUtils.getIdentifier(activity,"string",phaseName);
                        int numPhase = Integer.parseInt(activity.getString(idPhase));
                        sIdPhase.append(numPhase);
                        StringBuilder sIdComplexPhase = new StringBuilder(sIdPhase);
                        sIdComplexPhase.append(Constants.JUNCTION).append(Constants.COMPLEX_ANAGRAM);
                        int idArrayAnagrams = ActivityUtils.getIdentifier(activity, "array", sIdComplexPhase.toString());
                        //obtain the referenced array elements

                        Phase phase = new Phase(phaseName);
                        String[] anagramsArray = activity.getResources().getStringArray(idArrayAnagrams);
                        StringBuilder sIdAnagramSolution = new StringBuilder(Constants.SINGLE_SOLUTION);
                            sIdAnagramSolution.append(Constants.JUNCTION).append(sIdPhase);
                            sIdAnagramSolution.append(Constants.JUNCTION).append(Constants.SINGLE_ANAGRAM);

                        for(int z = 0; z < anagramsArray.length; z++) {
                            sIdAnagramSolution.append(z+1);
                            String anagram = anagramsArray[z];
                            int idSolution = ActivityUtils.getIdentifier(activity, "string", sIdAnagramSolution.toString());
                            String anagramSolution = activity.getString(idSolution);
                                for(int m = 0; m < numberDigits(z+1); m++) {
                                    sIdAnagramSolution.deleteCharAt(sIdAnagramSolution.length() - 1);
                                }
                            phase.addRegister(new Record(anagram,anagramSolution));
                            phase.addSolution(anagram,anagramSolution);
                        }
                        sIdPhase.deleteCharAt(sIdPhase.length()-numberDigits(j+1));
                        group.addRegister(phase);
                    }

                    groupsLoaded.add(group);

                    i++;
                    moreGroups = true;
                } else {
                    moreGroups = false;
                }

                sIdGroup.deleteCharAt(sIdGroup.length()-numberDigits(i+1));

            } while(moreGroups);

        actualAnagram = groupsLoaded.get(configuredCounterGroup-1).getAnagram(actualCounterPhase,actualCounterAnagram++);
        actualSolution = groupsLoaded.get(configuredCounterGroup-1).getSolution(actualCounterPhase, actualAnagram);
        sizeGroups = groupsLoaded.size();
    }

    private int numberDigits(final int number) {
        int division = number/10;

        if(division == 0) {
            return 1;
        } else {
            return 1 + numberDigits(division);
        }
    }

    public String nextAnagram() {

        Group configuredGroup = groupsLoaded.get(configuredCounterGroup - 1);
        int sizePhases = configuredGroup.getSizePhasesByGroup();
        int sizeAnagrams = -1;

        if (actualCounterPhase < sizePhases) {
            sizeAnagrams = configuredGroup.getSizeAnagramsByPhase(actualCounterPhase);
        }

        if(actualCounterAnagram < sizeAnagrams) {
            actualAnagram = configuredGroup.getAnagram(actualCounterPhase, actualCounterAnagram++);
            actualSolution = configuredGroup.getSolution(actualCounterPhase, actualAnagram);
            endAnagrams = actualCounterAnagram > sizeAnagrams;
        } else if(++actualCounterPhase < sizePhases) {
            actualCounterAnagram = 0;
            actualAnagram = configuredGroup.getAnagram(actualCounterPhase, actualCounterAnagram);
            actualSolution = configuredGroup.getSolution(actualCounterPhase, actualAnagram);
            actualCounterAnagram++;
            endAnagrams = false;
        } else {
            actualAnagram = null;
            endAnagrams = true;
            endPhases = true;
        }

        return actualAnagram;
    }

    public boolean isEndAnagrams() {
        return endAnagrams;
    }

    public boolean isEndPhases() {
        return endPhases;
    }

    public String getActualAnagram() {
        return actualAnagram;
    }

    public String getLetterActualAnagram(final int numLetter) {
        return String.valueOf(actualAnagram.charAt(numLetter - 1));
    }

    public String getSolution() {
        return actualSolution;
    }

    public Group getActualGroup() {
        return groupsLoaded.get(configuredCounterGroup-1);
    }

    public int getActualCounterGroup() {
        return configuredCounterGroup;
    }

    public int getActualCounterPhase() {
        return actualCounterPhase;
    }

    public int getActualCounterAnagram() {
        return actualCounterAnagram;
    }

}
