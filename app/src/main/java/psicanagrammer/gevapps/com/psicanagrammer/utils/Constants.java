package psicanagrammer.gevapps.com.psicanagrammer.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Geva on 21/02/2015.
 */
public class Constants {

    public static final String SINGLE_GROUP = "group";
    public static final String SINGLE_SOLUTION = "solution";
    public static final String SINGLE_PHASE = "phase";
    public static final String SINGLE_ANAGRAM = "anagram";
    public static final String SINGLE_ID = "Id";
    public static final String COMPLEX_PHASE = "phases";
    public static final String COMPLEX_ANAGRAM = "anagrams";
    public static final String JUNCTION = "_";
    public static final int DEFAULT_TIMELIMIT = 30;
    public static final int MAX_TIMELIMIT = 120;
    public static final int MIN_TIMELIMIT = 10;
    public static final int CHARACTERS_LIMIT = 5;
    public static final int MIN_POINTS = 1;
    public static final String END = "End";
    //Criterial training
    public static final int CORRECT_TIMES_CRITERIAL = 3;
    public static final int TIMERATE_CRITERIAL = 15;

    public static final String FILE_PATH = "/sdcard/PsicanagrammerReports";
    public static final String FILE_EXT = "/*.txt";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

}
