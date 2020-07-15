package com.mohakchavan.abbacus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String score = getIntent().getStringExtra("TotalScore");
        String availableScore = getIntent().getStringExtra("AvailableScore");
        TextView tv_score = findViewById(R.id.totalScore);
        tv_score.setText("Your score is: " + score + "/" + availableScore);

        Button startOver = findViewById(R.id.startOver);
        startOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
}