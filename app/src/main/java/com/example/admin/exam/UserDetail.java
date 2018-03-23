package com.example.admin.exam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class UserDetail extends AppCompatActivity {
EditText e11,e22,e33,profile1;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

        e11 = (EditText) findViewById(R.id.e11);
        e22 = (EditText) findViewById(R.id.e22);
        e33 = (EditText) findViewById(R.id.e33);
       profile1=(EditText)findViewById(R.id.profile1);
        back=(Button)findViewById(R.id.back);

        SharedPreferences s = getSharedPreferences("Details_of_user",MODE_PRIVATE);
        final Integer flag=s.getInt("flag", 0);

       String name= s.getString("Name", null);
       String email= s.getString("Email", null);
        String phone= s.getString("phone", null);
        String pro=s.getString("profession", null);


        if(flag==1)
        {
            e11.setText(name);
            e22.setText(email);
            e33.setText(phone);
            profile1.setText(pro);
            e11.setEnabled(false);
            e22.setEnabled(false);
            e33.setEnabled(false);
            profile1.setEnabled(false);


        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}
