package com.mohakchavan.abbacus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DataBaseHelper(MainActivity.this);
        helper.copyDataBase();
        List<QuestionModel> questionModels = helper.getAllData();
        Toast.makeText(MainActivity.this, questionModels.get(0).getQuestionText(), Toast.LENGTH_SHORT).show();
    }
}