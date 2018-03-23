package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AdminChoicePanel extends AppCompatActivity {
   Button b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choice_panel);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

        b1=(Button)findViewById(R.id.AdminPanelUserDetail);
        b2=(Button)findViewById(R.id.ManageProduct);
        b3=(Button)findViewById(R.id.ManageQA);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),fetchResult.class);
                startActivity(i);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),ManageProducts.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Manageques.class);
                startActivity(i);

            }
        });

    }


}
