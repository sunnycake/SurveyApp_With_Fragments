package com.mynuex.surveyapp_with_fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements
        SurveyFragment.SurveyVoteListener,
        ResultsFragment.ResetListener, ConfigurationFragment.UpdateSurveyListener {

    private static final String TAG_SURVEY_FRAG = "SURVEY FRAGMENT";
    private static final String TAG_RESULTS_FRAG = "RESULTS FRAGMENT";
    private static final String TAG_CONFIG_FRAG = "CONFIGURATION FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Adding fragments to hosting activity
        SurveyFragment surveyFragment = SurveyFragment.newInstance();
        surveyFragment.setSurveyVoteListener(this);

        ResultsFragment resultsFragment = ResultsFragment.newInstance(0, 0);
        ConfigurationFragment configurationFragment = ConfigurationFragment.newInstance();
        configurationFragment.setUpdateSurveyListener(this);

        // Fragment manager to add, remove, or replace a fragment and transaction
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        // add, remove, or replace fragments
        ft.add(R.id.survey_container, surveyFragment, TAG_SURVEY_FRAG);
        ft.add(R.id.results_container, resultsFragment, TAG_RESULTS_FRAG);
        ft.add(R.id.configuration_container, configurationFragment, TAG_CONFIG_FRAG);
        // Commit to display the fragment transactions to the container
        ft.commit();

    }

    @Override
    public void voteListener(int yes, int no) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ResultsFragment resultsFragment = ResultsFragment.newInstance(yes, no);
        ft.replace(R.id.results_container, resultsFragment);
        ft.commit();
    }

    @Override
    public void resetResults(int resetYes, int resetNo) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SurveyFragment resultFragment = SurveyFragment.newInstance();
        ft.replace(R.id.survey_container, resultFragment);
        ft.commit();
    }


    @Override
    public void updateSurvey(String newQuestion, String newOptionOne, String newOptionTwo) {

        FragmentManager fm = getSupportFragmentManager();
        SurveyFragment surveyFragment = (SurveyFragment) fm.findFragmentById(R.id.survey_container);
        surveyFragment.newSurvey(newQuestion, newOptionOne, newOptionTwo);
    }
}