package com.example.kunal.quizapp.Quiz;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.icu.util.TimeUnit;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.TimeUtils;
import android.icu.util.TimeUnit;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kunal.quizapp.Home.Quiz_Category_;
import com.example.kunal.quizapp.R;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quiz_start extends Fragment {

    TextView startTime, time, done, left;
    MyCountDownTimer myCountDownTimer;
    //ListView listview;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Quiz_Data quiz_data;
    ArrayList<Quiz_Data> arrayList;
    RadioButton radioButton = null;
    SparseIntArray sia = new SparseIntArray(1);
    String answers[] = new String[10];
    int right_ans = 0;
    int wrong_ans = 0;
    int notAttempt = 0;
    int question_no = 10;
    int left_question = 10;
    int id = 0;

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.questions, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            Quiz_Data data = arrayList.get(position);
            String ques_no = data.getQuestion_no();
            String ques = data.getQuestion();
            String ans1 = data.getAns1();
            String ans2 = data.getAns2();
            String ans3 = data.getAns3();
            String ans4 = data.getAns4();
            final String right_answer = data.getRight_ans();

            holder.question.setText("Q." + ques_no + ". " + ques);

            ((RadioButton) holder.radioGroup.getChildAt(0)).setText(ans1);
            ((RadioButton) holder.radioGroup.getChildAt(1)).setText(ans2);
            ((RadioButton) holder.radioGroup.getChildAt(2)).setText(ans3);
            ((RadioButton) holder.radioGroup.getChildAt(3)).setText(ans4);

            holder.radioGroup.setOnCheckedChangeListener(null);


            if(sia.indexOfKey(position) > -1){
                holder.radioGroup.check(sia.get(position));
            }else {
                holder.radioGroup.clearCheck();
            }

            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {

                   // left.setText(String.valueOf(left_question-=1));

                    radioButton = radioGroup.findViewById(i);

                    if(i > -1){

                        sia.put(position, i);

                        answers[position] = radioButton.getText().toString();

                        calculateAns(answers, position, right_answer);

                    }else{
                        if(sia.indexOfKey(position) > -1){
                            sia.removeAt(sia.indexOfKey(position));
                        }
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView question;
            RadioGroup radioGroup;
            public ViewHolder(View itemView) {
                super(itemView);
                question = itemView.findViewById(R.id.question);
                radioGroup = itemView.findViewById(R.id.radioGroup);
            }
        }
    }

    private void calculateAns(String[] answers, int position, String right_answer) {

        if (answers[position].equals(right_answer)){

            right_ans += 1;

            if(right_ans > 1 && position == id){
                right_ans -= 1;
            }else if(wrong_ans > 0 && position == id){
                wrong_ans -= 1;
            }

        } else if(!answers[position].equals(right_answer)) {

            wrong_ans += 1;

            if(wrong_ans > 1 && position == id){
                wrong_ans -= 1;
            }else if(right_ans > 0 && position == id){
                right_ans -= 1;
            }

        }

        //Toast.makeText(getActivity(), "" + notAttempt , Toast.LENGTH_SHORT).show();

        id = position;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_start, container, false);

        startTime = view.findViewById(R.id.startTime);
        done = view.findViewById(R.id.done);
       // left = view.findViewById(R.id.left);
        time = view.findViewById(R.id.timeRemaning);
        //   listview = view.findViewById(R.id.questions);
        recyclerView = view.findViewById(R.id.questions);
        myAdapter = new MyAdapter();
        arrayList = new ArrayList<>();

       // left.setText("10");

        final Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        questions();

        time.setText("05:00");

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v.vibrate(100);

                recyclerView.setVisibility(View.VISIBLE);

                myCountDownTimer = new MyCountDownTimer(59999, 1000);
                myCountDownTimer.start();

                startTime.setClickable(false);
                startTime.setTextColor(Color.parseColor("#17e414"));

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                v.vibrate(100);

                if(right_ans == 0 && wrong_ans == 0){
                    notAttempt = 10;
                }else{
                    notAttempt = right_ans + wrong_ans;
                    notAttempt = 10 - notAttempt;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("right_ans", right_ans);
                bundle.putInt("wrong_ans", wrong_ans);
                bundle.putInt("not_attempt", notAttempt);
                Fragment fragment = new Result_();
                fragment.setArguments(bundle);
                fragment_required(fragment);

                if(myCountDownTimer != null){
                    myCountDownTimer.cancel();
                }

            }
        });


        return view;
    }

    private void questions() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        //linearLayoutManager.setStackFromEnd(false);
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setVisibility(View.GONE);

        try{

            AssetManager assetsManager = getActivity().getAssets();
            InputStream is = assetsManager.open("questions.xls");
            Workbook workBook = Workbook.getWorkbook(is);
            Sheet sheet = workBook.getSheet(0);

            int row = sheet.getRows();
            int col = sheet.getColumns();

            String data[] = new String[]{"", "", "", "", "", ""};

            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    Cell cell = sheet.getCell(j, i);
                    data[j] = data[j] + cell.getContents();
                }
                quiz_data = new Quiz_Data(String.valueOf(question_no), data[0], data[1], data[2], data[3], data[4], data[5]);
                arrayList.add(0,quiz_data);
                myAdapter.notifyDataSetChanged();
                data[5] = data[4] = data[3] = data[2] = data[1] = data[0] = "";
                question_no -= 1;
            }

        }catch (Exception e){
            Toast.makeText(getActivity(), "Exception : " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if(i == keyEvent.KEYCODE_BACK){

                    if(myCountDownTimer != null){
                        myCountDownTimer.cancel();
                        Fragment fragment = new Quiz_Category_();
                        fragment_required(fragment);
                    }
                    return true;

                }
                return false;
            }
        });
    }

    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

            long ll = l;
            String hms = String.format(/*%02d:*/"%02d:%02d", /*java.util.concurrent.TimeUnit.MILLISECONDS.toHours(ll),*/
                    java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(ll) -
                            java.util.concurrent.TimeUnit.HOURS.toMinutes(java.util.concurrent.TimeUnit.MILLISECONDS.toHours(ll)),
                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(ll) -
                            java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(ll)));

            time.setText(hms);

        }

        @Override
        public void onFinish() {

            time.setText("Time up !");

            if(time.getText().toString().equals("Time up !")){

                if(right_ans == 0 && wrong_ans == 0){
                    notAttempt = 10;
                }else{
                    notAttempt = right_ans + wrong_ans;
                    notAttempt = 10 - notAttempt;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("right_ans", right_ans);
                bundle.putInt("wrong_ans", wrong_ans);
                Fragment fragment = new Result_();
                fragment.setArguments(bundle);
                fragment_required(fragment);

            }

        }
    }

    void fragment_required(Fragment fragment){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}


/*for (int i = 10; i > 0; i--) {
            quiz_data = new Quiz_Data("Q." + i + ". Sign  > is belongs to ?", "Less than", "Greater than", "Less than equal to", "Greater than equal to", "Greater than");
            arrayList.add(0, quiz_data);
        }*/

