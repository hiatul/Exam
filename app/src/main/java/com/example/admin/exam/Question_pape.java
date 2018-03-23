package com.example.admin.exam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.exam.Constants.Constant;
import com.example.admin.exam.DialogMessages.Alerts;
import com.example.admin.exam.Model.Beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Question_pape extends AppCompatActivity {
    Button reset,next;
    RadioGroup rg;
    RadioButton rb1,rb2,rb3,rb4;
    TextView qt,timer;
    Beans beans=new Beans();
    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_pape);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

        Bundle bundle=getIntent().getExtras();
        beans.setPID(bundle.getString("PID"));
        //Alerts.toast(this, beans.getPID() + "Norm");


        beans.setMarks(0);
        beans.setQNo(0);


        beans.setTimeOut(0);
        reset=(Button)findViewById(R.id.reset);
        next=(Button)findViewById(R.id.next);
        qt=(TextView) findViewById(R.id.qt);
        rg=(RadioGroup)findViewById(R.id.rg);
        rb1=(RadioButton) findViewById(R.id.rb1);
        rb2=(RadioButton) findViewById(R.id.rb2);
        rb3=(RadioButton) findViewById(R.id.rb3);
        rb4=(RadioButton) findViewById(R.id.rb4);
        timer=(TextView)findViewById(R.id.timer);
        SharedPreferences s = getSharedPreferences("Details_of_user", MODE_PRIVATE);
        beans.setEmail(s.getString("Email",""));

        cdt=  new CountDownTimer(61000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left: " + millisUntilFinished / 1000+"sec");
            }

            public void onFinish() {
                beans.setTimeOut(1);
                checkans();
                setQuestions();
            }
        };

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rg.clearCheck();
            }
        });

        Alerts.progressDialogStart(this,"Loading");
        makeJsonArrayRequest();

    }

    private void makeJsonArrayRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url= Constant.URL+"QuestionsFetch.php?PID="+beans.getPID();
        JsonArrayRequest req = null;

            req = new JsonArrayRequest(Request.Method.POST,url,null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("ARRAYRESPONSE", response.toString());

                            if(response.length()==0)
                            {
                                Alerts.toast(getApplicationContext(), "Exam Not Available");
                                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                beans.setJsonArray(response);
                                setQuestions();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error", "Error: " + error.getMessage());
                    //Alerts.toast(getApplicationContext(),"Volley Exception");
                    Alerts.progressDialogStop();
                }
            });


        // Adding request to request queue
        queue.add(req);

    }


    public void setQuestions()
    {
        try{
        JSONArray jsonArray=beans.getJsonArray();


            if(beans.getQNo()<jsonArray.length()) {

                beans.setTimeOut(0);
                rg.clearCheck();
                JSONObject jsonObject = (JSONObject) jsonArray.get(beans.getQNo());
                if(beans.getQNo()==jsonArray.length()-1)
                {
                    next.setText(R.string.Submit);
                }

                if(jsonObject.getString("PID").equals(beans.getPID())){
                qt.setText(jsonObject.getString("Question"));
                rb1.setText(jsonObject.getString("A"));
                rb2.setText(jsonObject.getString("B"));
                rb3.setText(jsonObject.getString("C"));
                rb4.setText(jsonObject.getString("D"));
                beans.setSol(jsonObject.getString("Ans"));


                cdt.start();


            }
                else{}
            }
            else {
                Alerts.progressDialogStart(Question_pape.this,"Loading");
                UpdateResult();
                cdt.cancel();
               // Alerts.toast(this,"No More Qestions Available");
                //exam end here
            }
            beans.setQNo(beans.getQNo()+1);
        }catch (Exception e)
        {
            Log.d("JSONException",""+e);
            Alerts.toast(this,"JSON Exception");
        }

        Alerts.progressDialogStop();

    }


    public void checkans() {

        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked())
        {
            beans.setChecked(1);
        }
        if(rb1.isChecked()&&rb1.getText().toString().equals(beans.getSol()))
        {

            Marks();
        }
        else if(rb2.isChecked()&&rb2.getText().toString().equals(beans.getSol()))
        {

            Marks();
        }
        else if (rb3.isChecked()&&rb3.getText().toString().equals(beans.getSol()))
        {
            Marks();
        }
        else if(rb4.isChecked()&&rb4.getText().toString().equals(beans.getSol()))
        {
            Marks();
        }
        else if(!(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked())&&(beans.getTimeOut()==0))
        {
            beans.setChecked(0);
            Alerts.toast(this,"Please select a valid option");
        }

    }

    public void Marks()
    {

        beans.setMarks(beans.getMarks()+Constant.Mark);

    }

    public void clkNext(View view)
    {

        checkans();
        if(beans.getChecked()==1) {
            cdt.cancel();
            setQuestions();

        }
    }
/////////////////////////////////////////Updating Marks After Exam////////////////////////////////////////////////////////
    public void UpdateResult()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL + "UpdateResult.php";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (valleyResponse(response)) {
                        //    Alerts.toast(getApplicationContext(), "Response ok");

                            forwardActivity();
                        } else {
                         //   Alerts.toast(getApplicationContext(), "Response not ok" + response);

                            //error
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error volley api
                Alerts.toast(getApplicationContext(), "Response Error marks");
                Alerts.progressDialogStop();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.PID, beans.getPID());
                params.put(Constant.EMAIL, beans.getEmail());
                params.put(Constant.MARKS, ""+beans.getMarks());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean valleyResponse(String response) {

        if (response.trim().equals("1")) {
            return true;
        }
        return false;
    }

    public void forwardActivity() {
        Alerts.progressDialogStop();
        Alerts.toast(Question_pape.this,"Test Completed");

        Intent i=new Intent(this,TestCompleted.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
       Alerts.AlertBox(Question_pape.this);
    }
}
