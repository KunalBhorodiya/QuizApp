package com.example.kunal.quizapp.Quiz;

/**
 * Created by Kunal on 8/27/2017.
 */
public class Quiz_Data {

    String question_no, question, ans1, ans2, ans3, ans4, right_ans;

    public Quiz_Data(String question_no,String question, String ans1, String ans2, String ans3, String ans4, String right_ans) {
        this.question_no = question_no;
        this.question = question;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.right_ans = right_ans;
    }

    public String getQuestion_no() {
        return question_no;
    }

    public String getQuestion() {
        return question;
    }

    public String getAns1() {
        return ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public String getRight_ans() {
        return right_ans;
    }
}
