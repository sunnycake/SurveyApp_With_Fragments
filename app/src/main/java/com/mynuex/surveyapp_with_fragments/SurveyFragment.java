package com.mynuex.surveyapp_with_fragments;



import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private int mYesCount = 0;
    private int mNoCount = 0;

    TextView mQuestion ;
    Button mNoButton ;
    Button mYesButton ;

    interface SurveyVoteListener {
        void voteListener(int yes, int no);
    }

    private SurveyVoteListener mVoteListener;

    public SurveyFragment() {
        // Required empty public constructor
    }

    public static SurveyFragment newInstance() {
        return new SurveyFragment();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");
        // verify listener
        if(context instanceof SurveyVoteListener){ //attach listener
            mVoteListener = (SurveyVoteListener) context;
            Log.d(TAG, "On attach survey vote listener set " + mVoteListener);
        } else {
            throw new RuntimeException(context.toString() + " must implement SurveyVoteListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        mQuestion = view.findViewById(R.id.survey_question_textView);
        mNoButton = view.findViewById(R.id.no_button);
        mYesButton = view.findViewById(R.id.yes_button);

        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYesCount++;
                mVoteListener.voteListener(mYesCount, mNoCount);
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoCount++;
                mVoteListener.voteListener(mYesCount, mNoCount);
            }
        });

        return view;
    }


    public void newSurvey(String newQuestion, String newOptionOne, String newOptionTwo) {

        mQuestion.setText(newQuestion);
        mYesButton.setText(newOptionOne);
        mNoButton.setText(newOptionTwo);
    }

    public void setSurveyVoteListener(SurveyVoteListener mVoteListener) {
        this.mVoteListener = mVoteListener;
    }
}

