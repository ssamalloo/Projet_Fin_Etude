package com.example.introductionscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.introductionscreen.databinding.ActivityQuestionsBinding;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    ArrayList<QuestionModel> list = new ArrayList<>();
    private int count = 0;
    private int position = 0;
    private int score = 0;
    CountDownTimer timer;

    ActivityQuestionsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        resetTimer();

        timer.start();

        String setName = getIntent().getStringExtra("set");

        if (setName.equals("SET-1")){

            setOne();

        }

        else if(setName.equals("SET-2")){

            setTwo();

        }

        for (int i=0; i<4; i++){
            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkAnswer((Button) view);

                }
            });
        }

        playAnimation(binding.questions, 0, list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (timer !=null){
                  timer.cancel();
                };
                timer.start();
                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha((float) 0.3);
                enableOption(true);
                position++;

                if (position == list.size()){

                    Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("total", list.size());
                    startActivity(intent);
                    finish();
                    return;

                }

                count = 0;

                playAnimation(binding.questions,0,list.get(position).getQuestion());

            }

        });

    }

    private void resetTimer() {

        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {

                binding.timer.setText(String.valueOf(l/1000));

            }

            @Override
            public void onFinish() {

                Dialog dialog = new Dialog(QuestionsActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(QuestionsActivity.this,SetsActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

                dialog.show();

            }
        };

    }

    private void playAnimation(View view, int value, String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                        if (value ==0 && count <4){

                            String option = "";

                            if (count ==0){
                                option = list.get(position).getOptionA();
                            }
                            else if (count ==1){
                                option = list.get(position).getOptionB();
                            }
                            else if (count ==2){
                                option = list.get(position).getOptionC();
                            }
                            if (count ==3){
                                option = list.get(position).getOptionD();
                            }

                            playAnimation(binding.optionContainer.getChildAt(count),0,option);
                            count++;

                        }

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        if (value==0){

                            try {

                                ((TextView)view).setText(data);
                                binding.totalQuestion.setText(position+1+"/"+list.size());

                            } catch (Exception e) {

                                ((Button)view).setText(data);

                            }

                            view.setTag(data);
                            playAnimation(view,1,data);

                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

    }

    private void enableOption(boolean enable) {

        for (int i=0; i<4; i++){

            binding.optionContainer.getChildAt(i).setEnabled(enable);

            if (enable){

                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);

            }

        }



    }

    private void checkAnswer(Button selectedOption) {

        if (timer !=null){
            timer.cancel();
        };

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);

        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){
            score++;
            selectedOption.setBackgroundResource(R.drawable.right_answer);
        }

        else{

            selectedOption.setBackgroundResource(R.drawable.wrong_answer);

            Button correctOption = (Button) binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.right_answer);

        }

    }

    private void setTwo() {

        list.add(new QuestionModel("Have you ever taken credit for someone else's work or ideas?",
                "Never","Rarely","Occasionally","Always", "Never"));
        list.add(new QuestionModel("Do you respect the confidentiality and privacy of others by not sharing their personal information without permission?",
                "Never","Rarely","Occasionally","Always", "Always"));
        list.add(new QuestionModel("Have you ever cheated or engaged in dishonest behavior to gain an unfair advantage?",
                "Never","Rarely","Occasionally","Always", "Never"));
        list.add(new QuestionModel("Are you truthful about your abilities and qualifications, or do you exaggerate or misrepresent them?",
                "Never","Rarely","Occasionally","Always", "Always"));
        list.add(new QuestionModel("Have you ever cheated or plagiarized in an academic or professional setting?",
                "Never","Rarely","Occasionally","Always", "Never"));

    }

    private void setOne() {

        list.add(new QuestionModel("Are you honest with yourself about your own strengths and weaknesses?",
                "Never","Rarely","Occasionally","Always", "Always"));
        list.add(new QuestionModel("Are you transparent and open in your communication with others?",
                "Never","Rarely","Occasionally","Always", "Always"));
        list.add(new QuestionModel("Do you consistently tell the truth, even when it is difficult or uncomfortable?",
                "Never","Rarely","Occasionally","Always", "Always"));
        list.add(new QuestionModel("Do you admit and take responsibility for your mistakes and shortcomings?",
                "Never","Rarely","Occasionally","Always", "Always"));
        list.add(new QuestionModel("Have you ever lied or deceived someone to avoid getting in trouble or to protect yourself?",
                "Never","Rarely","Occasionally","Always", "Never"));

    }
}