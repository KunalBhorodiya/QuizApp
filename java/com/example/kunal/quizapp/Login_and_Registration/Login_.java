package com.example.kunal.quizapp.Login_and_Registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kunal.quizapp.Home.Quiz_Category_;
import com.example.kunal.quizapp.MainActivity;
import com.example.kunal.quizapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login_ extends Fragment {

    Button login, registration;

    public Login_() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_, container, false);

        login = view.findViewById(R.id.login);
        registration = view.findViewById(R.id.registration);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Quiz_Category_();
                Fragment_required(fragment);

            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Registraion_();
                Fragment_required(fragment);


            }
        });

        return view;
    }

    void Fragment_required(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
