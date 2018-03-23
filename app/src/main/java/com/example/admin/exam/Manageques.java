package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Manageques extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageques);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

    }

    public void AddQuestions(View v)
    {
        Intent i = new Intent(getApplicationContext(),AddQA.class);
        startActivity(i);

    }

    public void UpdateQuestions(View v)
    {
        Intent i = new Intent(getApplicationContext(),UpdateQues.class);
        startActivity(i);

    }

    public void DeleteQuestions(View v)
    {
        Intent i = new Intent(getApplicationContext(),DeleteQuestion.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
