package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateQues extends AppCompatActivity {
    Spinner s1,s2;
    String arr1[] = {"None"};
    EditText question,a,b,c,d,ans;
    Beans beans=new Beans();
    ArrayAdapter ad1,ad2;
    int flag=0,flag2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ques);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

        question=(EditText)findViewById(R.id.Uquestion);
        a=(EditText)findViewById(R.id.UA);
        b=(EditText)findViewById(R.id.UB);
        c=(EditText)findViewById(R.id.UC);
        d=(EditText)findViewById(R.id.UD);
        ans=(EditText)findViewById(R.id.UAns);

        Alerts.progressDialogStart(UpdateQues.this,"Loading");
        retriveProductsList();

        s1 = (Spinner) findViewById(R.id.spinner_quesp);
        s2 = (Spinner) findViewById(R.id.UpdateQues_Spinner);


         ad1= new ArrayAdapter(getApplicationContext(), R.layout.textlayout, arr1); // get the layout
        s1.setAdapter(ad1); // set the layout for all the items

         ad2 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout, arr1); // get the layout
        s2.setAdapter(ad2); // set the layout for all the items


        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(flag == 1&&(!s1.getSelectedItem().toString().equals("None"))) {
//                    Alerts.progressDialogStart(getApplicationContext(),"Loading");
                    ArrayList id=beans.getProductIDList();
                    ArrayList k = beans.getProductKeyList();

                    beans.setPID(id.get(s1.getSelectedItemPosition()).toString());
                    beans.setKey(k.get(s1.getSelectedItemPosition()).toString());
                    beans.setProduct(s1.getSelectedItem().toString());
                    Alerts.progressDialogStart(UpdateQues.this,"Loading");
                    retriveQestionsList();

                }
                else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(flag2==1&&(!s1.getSelectedItem().toString().equals("None")))
                {
                    JSONArray jsonArray=beans.getJsonArray();
                    try {
                        JSONObject jsonObject=(JSONObject)jsonArray.get(s2.getSelectedItemPosition());
                        beans.setQID(jsonObject.getString("QID"));
                        beans.setQuestion(jsonObject.getString("Question"));
                        beans.setA(jsonObject.getString("A"));
                        beans.setB(jsonObject.getString("B"));
                        beans.setC(jsonObject.getString("C"));
                        beans.setD(jsonObject.getString("D"));
                        beans.setANS(jsonObject.getString("Ans"));
                        setQuestionText();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //////////////////////////////////Retrive Products/////////////////////////////
    public void retriveProductsList()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url= Constant.URL+"RetriveProducts.php";
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("", response.toString());

                        if(response.length()==0)
                        {
                            Alerts.progressDialogStop();
                            Alerts.toast(getApplicationContext(),"Please Add Some Products First");
                            finish();
                        }
                        else {
                            beans.setJsonArray(response);
                            setproductlist();
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



    public void setproductlist()
    {
        JSONArray response=beans.getJsonArray();
        ArrayList<String> p=new ArrayList<>();
        ArrayList<String> k=new ArrayList<>();
        ArrayList<String> id=new ArrayList<>();

        p.add("None");
        k.add("None");
        id.add("None");
        try {
            // Parsing json array response
            // loop through each json object
            for (int i = 0; i < response.length(); i++) {

                JSONObject product = (JSONObject) response
                        .get(i);

                p.add(product.getString("ProductName"));
                k.add(product.getString("ProductKey"));
                id.add(product.getString("PID"));
            }

            beans.setProductNameList(p);
            beans.setProductKeyList(k);
            beans.setProductIDList(id);
            flag=1;
            ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout,beans.getProductNameList()); // get the layout
            s1.setAdapter(ad1); // set the layout for all the items
            Alerts.progressDialogStop();

        } catch (JSONException e) {
            e.printStackTrace();
            Alerts.toast(this,"Json parsing exception");
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////Retrive Questions List////////////////////////////
    public void retriveQestionsList()
    {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url= Constant.URL+"QuestionsFetch.php?PID="+beans.getPID();
        JsonArrayRequest req = null;

        req = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ARRAYRESPONSE", response.toString());

                        if(response.length()==0)
                        {
                            Alerts.toast(getApplicationContext(), "Questions Not Available");
                            Alerts.progressDialogStop();
                        }
                        else {
                            beans.setJsonArray(response);
                            setQuestionsOnSpinner();
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
    //////////////////////////////////////////////////////////////////////////////////////

    void setQuestionsOnSpinner()
    {
        JSONArray jsonArray=beans.getJsonArray();
        ArrayList<String> ques=new ArrayList<String>();
        for(int i=0;i<jsonArray.length();i++)
        {
            try {
                JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                ques.add(jsonObject.getString("Question"));

                if(i==0) {

                    beans.setQID(jsonObject.getString("QID"));
                    beans.setQuestion(jsonObject.getString("Question"));
                    beans.setA(jsonObject.getString("A"));
                    beans.setB(jsonObject.getString("B"));
                    beans.setC(jsonObject.getString("C"));
                    beans.setD(jsonObject.getString("D"));
                    beans.setANS(jsonObject.getString("Ans"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        ad2 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout,ques); // get the layout
        s2.setAdapter(ad2);
        flag2=1;
        Alerts.progressDialogStop();
    }

    public void setQuestionText()
    {
        question.setText(beans.getQuestion());
        a.setText(beans.getA());
        b.setText(beans.getB());
        c.setText(beans.getC());
        d.setText(beans.getD());
        ans.setText(beans.getANS());
    }


    public void clkUpdate(View view)
    {


        if(s1.getSelectedItem().toString().equals("None"))
        {
            Alerts.toast(this,"Please select a valid product");
        }
        else if(question.getText().toString().equals(""))
        {
            question.setError("Please enter your question");
        }
        else if(a.getText().toString().equals(""))
        {
            a.setError("Please enter an option");

        }
        else if(b.getText().toString().equals(""))
        {
            b.setError("Please enter an option");

        }
        else if(c.getText().toString().equals(""))
        {
            c.setError("Please enter an option");

        }
        else if(d.getText().toString().equals(""))
        {
            d.setError("Please enter an option");

        }
        else if(ans.getText().toString().equals(""))
        {
            ans.setError("Please enter an one correct ans from the above options");

        }
        else if((!ans.getText().toString().equals(a.getText().toString()))&&(!ans.getText().toString().equals(b.getText().toString()))&&(!ans.getText().toString().equals(c.getText().toString()))&&(!ans.getText().toString().equals(d.getText().toString())))
        {
            ans.setError("Please enter an one correct ans from the above options");

        }
        else
        {
            beans.setQuestion(question.getText().toString());
            beans.setA(a.getText().toString());
            beans.setB(b.getText().toString());
            beans.setC(c.getText().toString());
            beans.setD(d.getText().toString());
            beans.setANS(ans.getText().toString());
            Alerts.progressDialogStart(UpdateQues.this,"Loading");
           updateQuestion();
        }
    }

//////////////////////////////////Update Question///////////////////////////////////////////////////
    public void updateQuestion()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL+"QuestionUpdate.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if(valleyResponse(response))
                        {
                            Alerts.toast(UpdateQues.this,"Question Inserted Sucessfully");

                            forwardActivity();
                        }
                        else
                        {
                            Alerts.progressDialogStop();
                            Alerts.toast(getApplicationContext(),"Response not ok"+response);

                            //error
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error volley api
                Alerts.toast(getApplicationContext(),"Response Error");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constant.QID,beans.getQID());
                params.put(Constant.QUESTION,beans.getQuestion());
                params.put(Constant.OPTA,beans.getA());
                params.put(Constant.OPTB,beans.getB());
                params.put(Constant.OPTC,beans.getC());
                params.put(Constant.OPTD,beans.getD());
                params.put(Constant.ANS,beans.getANS());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    public boolean valleyResponse(String response) {

        if (response.trim().equals("1")) {
            return true;
        }
        return false;
    }

    public void forwardActivity() {

        question.setText("");
        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        ans.setText("");
        Alerts.progressDialogStop();

        ad1= new ArrayAdapter(getApplicationContext(), R.layout.textlayout, arr1); // get the layout
        s1.setAdapter(ad1); // set the layout for all the items

        ad2 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout, arr1); // get the layout
        s2.setAdapter(ad2); // set the layout for all the items


        Alerts.progressDialogStart(UpdateQues.this,"Loading");
        retriveProductsList();

    }
///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
