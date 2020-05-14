package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private TextView question, noIndicator;
    private FloatingActionButton bookmarkbtn;
    private LinearLayout optionsContainer;
    private Button shareBtn, nextBtn;
    private int count=0,position=0;
    private List<QuestionModel> list;
    private int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        question = findViewById(R.id.question);
        noIndicator = findViewById(R.id.no_indicator);
        bookmarkbtn = findViewById(R.id.bookmark_btn);
        optionsContainer = findViewById(R.id.options_container);
        shareBtn = findViewById(R.id.share_btn);
        nextBtn = findViewById(R.id.next_btn);

        list = new ArrayList<>();
        list.add(new QuestionModel("question1", "a", "b", "c", "d", "b"));
        list.add(new QuestionModel("question2", "a", "b", "c", "d", "c"));
        list.add(new QuestionModel("question3", "a", "b", "c", "d", "d"));
        list.add(new QuestionModel("question4", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("question5", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("question6", "a", "b", "c", "d", "d"));
        list.add(new QuestionModel("question7", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("question8", "a", "b", "c", "d", "b"));
        list.add(new QuestionModel("question9", "a", "b", "c", "d", "c"));


        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }

        playAnim(question,0,list.get(position).getQuestion());

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setEnabled(false);
                nextBtn.setAlpha(0.7f);
                enableOption(true);
                position++;
                if (position == list.size()){
                    ///score activity
                    return;
                }
                count=0;
                playAnim(question,0,list.get(position).getQuestion());
            }
        });

    }

    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value==0 && count < 4){
                    String option="";
                    if (count==0){
                        option = list.get(position).getOptionA();
                    }else if (count==1){
                        option = list.get(position).getOptionB();
                    }else if (count==2){
                        option = list.get(position).getOptionC();
                    }else if (count==3){
                        option = list.get(position).getOptionD();
                    }
                    playAnim(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (value==0){
                    try {
                        ((TextView)view).setText(data);
                        noIndicator.setText(position + 1 + "/" + list.size());

                    }catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectANS())) {
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }else
        {
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption =(Button) optionsContainer.findViewWithTag(list.get(position).getCorrectANS());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }


    }
    private void enableOption(boolean enable){

        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setEnabled(enable);
            if (enable){
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));


            }
        }
    }
}
