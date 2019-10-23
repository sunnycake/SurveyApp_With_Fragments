package com.mynuex.surveyapp_with_fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    private static final String ARG_NEW_QUESTION = "arg_new_question";
    private static final String ARG_OPTION_ONE = "arg_option_one";
    private static final String ARG_OPTION_TWO = "arg_option_two";

    String getQuestion;
    String getOptionOne;
    String getOptionTwo;

    Button mSaveButton;


    interface UpdateSurveyListener {
        void updateSurvey(String newQuestion, String newOptionOne, String newOptionTwo);
    }

    private UpdateSurveyListener mUpdateSurveyListener;

    public static ConfigurationFragment newInstance(String question, String optionOne, String optionTwo) {
        ConfigurationFragment cf = new ConfigurationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEW_QUESTION, question);
        args.putString(ARG_OPTION_ONE, optionOne);
        args.putString(ARG_OPTION_TWO, optionTwo);
        cf.setArguments(args);
        return cf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getQuestion = getArguments().getString(ARG_NEW_QUESTION);
            getOptionOne = getArguments().getString(ARG_OPTION_TWO);
            getOptionTwo = getArguments().getString(ARG_OPTION_TWO);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");

        // verify listener
        if (context instanceof UpdateSurveyListener) {
            mUpdateSurveyListener = (UpdateSurveyListener) context;
            Log.d(TAG, "on attach update survey Listener set");
        } else {
            throw new RuntimeException(context.toString() + " must implement UpdateSurveyListener");
        }
    }

    public ConfigurationFragment() {
        // Required empty public constructor
    }

    public static ConfigurationFragment newInstance() {
        return new ConfigurationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);

        final EditText newQuestion = view.findViewById(R.id.new_question_editText);
        final EditText newOptionOne = view.findViewById(R.id.option_one);
        final EditText newOptionTwo = view.findViewById(R.id.option_two);

        mSaveButton = view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if(mUpdateSurveyListener !=null) {
                mUpdateSurveyListener.updateSurvey(
                        newQuestion.getText().toString(),
                        newOptionOne.getText().toString(),
                        newOptionTwo.getText().toString());
                //mUpdateSurveyListener.updateSurvey();

                newQuestion.getText().clear();
                newOptionOne.getText().clear();
                newOptionTwo.getText().clear();
                // }

            }
        });

        return view;
    }

    public void setUpdateSurveyListener(UpdateSurveyListener mUpdateSurveyListener) {
        this.mUpdateSurveyListener = mUpdateSurveyListener;
    }
}