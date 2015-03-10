package psicanagrammer.gevapps.com.psicanagrammer.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import psicanagrammer.gevapps.com.psicanagrammer.R;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Group;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Phase;
import psicanagrammer.gevapps.com.psicanagrammer.dto.Record;
import psicanagrammer.gevapps.com.psicanagrammer.utils.ActivityUtils;
import psicanagrammer.gevapps.com.psicanagrammer.utils.Constants;

public class ReviewTabbedActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tabbed);
        StringBuilder absoluteName = new StringBuilder(Constants.FILE_PATH);
            absoluteName.append(getIntent().getExtras().getString("fileName"));

        File reviewFile = new File(absoluteName.toString());

            if(reviewFile.exists()) {
                FileReader fr = null;
                try {
                    fr = new FileReader(reviewFile);

                    BufferedReader br = new BufferedReader(fr);

                    String reviewName;
                    Date reviewTimestamp;
                    Group group;
                    Phase phase;
                    Record record;

                    boolean inHeader = false;
                    boolean inGroup = false;
                    boolean inPhase = false;
                    boolean inRecord = false;
                    boolean inFinalQuestions = false;
                    boolean inResults = false;
                    int tipoCampos = 0;

                    String line;

                    do {
                        line = br.readLine();
                        if(!line.isEmpty()) {

                            if (line.matches("---- .* -----")) {
                                if(line.contains("INFORME")) {
                                    inHeader = true;
                                } else if(line.contains("RESULTADO POR GRUPO")) {
                                    inGroup = true;
                                } else if(line.contains("SOBRE TOTAL DE RESPUESTAS")) {
                                    tipoCampos = 1;
                                } else if(line.contains("SOBRE TOTAL DE PALABRAS")) {
                                    tipoCampos = 2;
                                } else if(line.contains("LATENCIAS")) {
                                    tipoCampos = 3;
                                } else if(line.contains("ENSAYO DE CRITERIO")) {
                                    tipoCampos = 4;
                                } else if(line.contains("RESULTADOS POR FASE")) {
                                    inGroup = false;
                                } else if(line.contains("RESULTADO DE FASE")) {
                                    inPhase = true;
                                } else if(line.contains("RESULTADOS")) {
                                    inPhase = false;
                                    inResults = true;
                                } else if(line.contains("PREGUNTAS FINALES")) {
                                    inFinalQuestions = true;
                                }
                            } else if(inHeader) {
                                if(line.contains("Nombre: ")) {
                                    reviewName = line.substring(line.lastIndexOf("Nombre: "));
                                } else if(line.contains("Fecha: ")) {
                                    try {
                                        reviewTimestamp = Constants.SIMPLE_DATE_FORMAT.parse(line.substring(line.lastIndexOf("Fecha: ")));
                                    } catch(ParseException e) {
                                        System.out.println("Fallo de parseo de fecha al leer fichero");
                                    }
                                }
                            } else if(inGroup) {
                                switch(tipoCampos) {
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        break;
                                }
                            } else if(inPhase) {
                                switch(tipoCampos) {
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 4:
                                        break;
                                }
                            } else if(inFinalQuestions) {

                            }
                        }

                    } while(line != null);

                } catch (FileNotFoundException e) {
                    ActivityUtils.showMessageInToast("No se pudo abrir el fichero", this, getResources().getColor(R.color.red));
                } catch (IOException e) {
                    ActivityUtils.showMessageInToast("Se produjo un fallo al leer el fichero", this, getResources().getColor(R.color.red));
                }
            }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_review_tabbed, container, false);
            return rootView;
        }
    }

}
