package com.example.kunal.quizapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kunal.quizapp.Home.Quiz_Category_;
import com.example.kunal.quizapp.Login_and_Registration.Login_;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Quiz_Category_ quiz_category_ = new Quiz_Category_();
        fragmentTransaction.replace(R.id.fragment_container, quiz_category_);
        fragmentTransaction.commit();

    }

}