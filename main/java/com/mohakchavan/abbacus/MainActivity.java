package com.mohakchavan.abbacus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DataBaseHelper helper;
    TextView questionTimer, questionText, option1, option2, option3, option4;
    List<QuestionModel> questionModels;
    int totalScore, questionIndex, availableScore;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTimer = findViewById(R.id.questionTimer);
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        helper = new DataBaseHelper(MainActivity.this);
        helper.copyDataBase();
        questionModels = helper.getAllData();
        availableScore = questionModels.size() * 20;
        totalScore = 0;
        questionIndex = -1;

        startNextQuestion();
    }

    private void startNextQuestion() {
        ++questionIndex;
        if (questionIndex < questionModels.size()) {
            changeQuestionNext(questionIndex);
            resetTimer();
            timer.start();
        } else {
            //goto next intent
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("TotalScore", String.valueOf(totalScore));
            intent.putExtra("AvailableScore", String.valueOf(availableScore));
            startActivity(intent);
        }
    }

    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = getQuestionTimer();
    }

    private void changeQuestionNext(int questionIndex) {
        questionText.setText(questionModels.get(questionIndex).getQuestionId() + ". " + questionModels.get(questionIndex).getQuestionText());
        List<String> options = new ArrayList<>();
        options.add(questionModels.get(questionIndex).getOption1());
        options.add(questionModels.get(questionIndex).getOption2());
        options.add(questionModels.get(questionIndex).getOption3());
        options.add(questionModels.get(questionIndex).getOption4());
        Collections.shuffle(options);
        option1.setText("A. " + options.get(0));
        option2.setText("B. " + options.get(1));
        option3.setText("C. " + options.get(2));
        option4.setText("D. " + options.get(3));

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
    }

    private CountDownTimer getQuestionTimer() {
        return new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                questionTimer.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                totalScore -= 5;
                startNextQuestion();
            }
        };
    }

    @Override
    public void onClick(View view) {
        if (((TextView) view).getText().equals(questionModels.get(questionIndex).getAnswer())) {
            totalScore += 20;
        } else {
            totalScore -= 10;
        }
        startNextQuestion();
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}