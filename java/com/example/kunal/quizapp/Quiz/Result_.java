package com.example.kunal.quizapp.Quiz;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunal.quizapp.Home.Quiz_Category_;
import com.example.kunal.quizapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Result_ extends Fragment {

    Button ok, restart;
    Fragment fragment;
    TextView right_answer, wrong_answer, not_attempt, performance;

    public Result_() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_, container, false);

        int right_ans = getArguments().getInt("right_ans");
        int wrong_ans = getArguments().getInt("wrong_ans");
        int notAttempt = getArguments().getInt("not_attempt");

        ok = view.findViewById(R.id.re_ok);
        restart = view.findViewById(R.id.restart);
        right_answer = view.findViewById(R.id.right_answer);
        wrong_answer = view.findViewById(R.id.wrong_answer);
        not_attempt = view.findViewById(R.id.not_attempt);
        performance = view.findViewById(R.id.perform);

        if(right_ans == 10){
            performance.setText("Excellent Performance");
        }else if(right_ans >= 7){
            performance.setText("Best Performance");
        }else if(right_ans >= 4){
            performance.setText("Can Do Better");
        }else if(right_ans <= 2){
            performance.setText("Poor Performance");
        }

        right_answer.setText("Right Answers : " + right_ans);
        wrong_answer.setText("Wrong Answers : " + wrong_ans);
        not_attempt.setText("Not Attempt : " + notAttempt);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new Quiz_Category_();
                fragment_required(fragment);

            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Quiz_start();
                fragment_required(fragment);
            }
        });

        return view;
    }

    public void fragment_required(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
