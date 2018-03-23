package com.example.admin.exam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Code_Activity extends AppCompatActivity {
  EditText cd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar


        cd1=(EditText)findViewById(R.id.cd1);

        SharedPreferences s = getSharedPreferences("Details_of_user", MODE_PRIVATE);
          final Integer flag=s.getInt("flag",0);

        cd1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {




              if((s.length()==4))
                {
                    if(s.toString().equals("1111"))
                    {
                        Intent i = new Intent(getApplicationContext(),AdminChoicePanel.class);
                        startActivity(i);
                        cd1.setText("");

                    }
                    else if(s.toString().equals("exam")) {
                      //  Toast.makeText(Code_Activity.this, flag, Toast.LENGTH_SHORT).show();
                        if (flag == 0) {
                            Intent i = new Intent(getApplicationContext(), MainActivity1.class);
                            startActivity(i);
                            cd1.setText("");
                          //  Toast.makeText(Code_Activity.this, " i am 0", Toast.LENGTH_SHORT).show();
                        }
                        if(flag==1)
                        {
                            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(i);
                            cd1.setText("");
                        }
                    }
                    else
                    {
                        cd1.setError("Invalid code");
                    }
                }
                else if(s.length()>4)
              {

                  cd1.setError("Invalid code");
              }
            }
        });

    }
}
