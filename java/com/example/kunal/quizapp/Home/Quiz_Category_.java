package com.example.kunal.quizapp.Home;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.kunal.quizapp.MainActivity;
import com.example.kunal.quizapp.Quiz.Quiz_start;
import com.example.kunal.quizapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quiz_Category_ extends Fragment {

    Button c;

    public Quiz_Category_() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz__category_, container, false);

        c = view.findViewById(R.id.lan_c);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = Required("notice");
                final View view1 = Layout(dialog, "notice");

                Button ok, back;

                ok = view1.findViewById(R.id.ok);
                back = view1.findViewById(R.id.back);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Fragment fragment = new Quiz_start();
                        fragment_required(fragment);
                        dialog.dismiss();

                    }
                });

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        return view;
    }

    public void fragment_required(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public Dialog Required(String rlayout){

        // Make Dialog
        Dialog dialog = new Dialog(getActivity());
        int rsId = this.getResources().getIdentifier(rlayout, "layout", getActivity().getPackageName());
        dialog.setContentView(rsId);

        // Giving Gravity
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlm = window.getAttributes();
        wlm.gravity = Gravity.CENTER;
        window.setAttributes(wlm);
        dialog.show();

        return dialog;

    }
    public View Layout(Dialog dialog, String activity){

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int rsId = this.getResources().getIdentifier(activity, "layout", getActivity().getPackageName());
        View view1 = layoutInflater.inflate(rsId, null);
        dialog.setContentView(view1);
        return view1;

    }


}
