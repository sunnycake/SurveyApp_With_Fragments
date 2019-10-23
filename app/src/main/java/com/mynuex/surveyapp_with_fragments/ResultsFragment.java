package com.mynuex.surveyapp_with_fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


public class ResultsFragment extends Fragment {

    private static final String ARG_YES_COUNT = "arg_yes";
    private static final String ARG_NO_COUNT = "arg_no";

    private int mYesCount = 0;
    private int mNoCount = 0;

    private TextView yesView;
    private TextView noView;
    Button mResetButton;

    interface ResetListener {
        void resetResults(int resetYes, int resetNo);
    }

    private ResetListener mResetListener;


    public ResultsFragment() {
        // Required empty public constructor
    }

//    public static ResultsFragment newInstance() {
//        return new ResultsFragment();
//    }

    public static ResultsFragment newInstance(int yes, int no) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YES_COUNT, yes);
        args.putInt(ARG_NO_COUNT, no);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYesCount = getArguments().getInt(ARG_YES_COUNT);
            mNoCount = getArguments().getInt(ARG_NO_COUNT);
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Log.d(TAG, "onAttach");

        // verify listener
        if (context instanceof ResultsFragment.ResetListener){
            mResetListener = (ResultsFragment.ResetListener) context;
            Log.d(TAG, "on attach results reset Listener set");
        } else  {
            throw new RuntimeException(context.toString() + " must implement ResetListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        yesView = view.findViewById(R.id.yes_vote_textView);
        setYesResults(mYesCount);
        noView = view.findViewById(R.id.no_vote_textView);
        setNoResults(mNoCount);

        mResetButton = view.findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYesCount = 0;
                mNoCount = 0;
                yesView.setText(mYesCount);
                noView.setText(mNoCount);
                mResetListener.resetResults(mYesCount, mNoCount);
            }
        });

        return view;
    }

    public void setYesResults(int yesCountTotal) {
        // Display votes
        mYesCount = yesCountTotal;
        yesView.setText(mYesCount);
    }

    public void setNoResults(int noCountTotal) {
        mNoCount = noCountTotal;
        noView.setText(mNoCount);
    }
}
