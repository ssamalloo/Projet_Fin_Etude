package com.example.introductionscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    AppCompatButton ansA, ansB;
    Button submitBtn;

    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_survey);

        totalQuestionsTextView = findViewById(R.id.total_questions);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total Questions - "+totalQuestion);

        loadNewQuestion();

    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundResource(R.drawable.btn_opt);
        ansB.setBackgroundResource(R.drawable.btn_opt);

        Button clickedButton = (Button) view;
        if (clickedButton.getId()==R.id.submit_btn){

            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){

                score++;

            }

            currentQuestionIndex++;
            loadNewQuestion();


        }else {

            //choices button clicked
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundResource(R.drawable.survey_response);

        }

    }

    void loadNewQuestion(){

        if (currentQuestionIndex == totalQuestion) {

            finishQuiz();
            return;

        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);

    }

    void finishQuiz(){

        String passStatus = "";
        if (score >= totalQuestion*0.60) {

            passStatus = "Passed";

        }else {

            passStatus = "Failed";

        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your score is "+score+" out of "+totalQuestion+" !")
                .setNegativeButton("Restart",(dialogInterface, i) -> restartQuiz())
                .setPositiveButton("Quit",(dialogInterface, i) -> quitQuiz())
                .setCancelable(false)
                .show();
    }

    void quitQuiz() {

        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);

    }

    void restartQuiz(){

        score=0;
        currentQuestionIndex=0;
        loadNewQuestion();

    }

}