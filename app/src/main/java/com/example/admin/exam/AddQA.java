package com.example.admin.exam;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.handle;

public class AddQA extends AppCompatActivity {

    Spinner s1;
    Beans beans=new Beans();
    boolean check;
    EditText question,a,b,c,d,ans;
    ArrayAdapter ad1;
    String s[]={"None"};
    android.os.Handler handle = new android.os.Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_q);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

        retriveProductsList();


        Alerts.progressDialogStart(this,"Loading");

        s1 = (Spinner) findViewById(R.id.spinner_quesp);

        ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout,s); // get the layout
        s1.setAdapter(ad1); // set the layout for all the items

        question=(EditText)findViewById(R.id.question);
        a=(EditText)findViewById(R.id.A);
        b=(EditText)findViewById(R.id.B);
        c=(EditText)findViewById(R.id.C);
        d=(EditText)findViewById(R.id.D);
        ans=(EditText)findViewById(R.id.Ans);

    }

    ////////////////////////////////////////////////INsert Question//////////////////////////////////////////

    public void volleyDataSend()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL+"Questions.php";

        Alerts.toast(this, "volley start");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(valleyResponse(response))
                        {
                            Alerts.toast(getApplicationContext(),"Response ok");

                            forwardActivity();
                        }
                        else
                        {
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
                params.put(Constant.PID,beans.getPID());
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
        Alerts.toast(this,"Question Inserted Sucessfully");
        question.setText("");
        a.setText("");
        b.setText("");
        c.setText("");
        d.setText("");
        ans.setText("");


    }

    public void InsertQuestion(View view)
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
            ArrayList id=beans.getProductIDList();
            beans.setPID(id.get(s1.getSelectedItemPosition()).toString());
            beans.setQuestion(question.getText().toString());
            beans.setA(a.getText().toString());
            beans.setB(b.getText().toString());
            beans.setC(c.getText().toString());
            beans.setD(d.getText().toString());
            beans.setANS(ans.getText().toString());
            volleyDataSend();
        }
    }
    //////////////////////////////////////////////////////////////////////
    ///////////////////////////Retrive list of Products in Json////////////////////////////

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
                Alerts.progressDialogStop();
                runnable.run();

            }
        });

        // Adding request to request queue
        queue.add(req);
    }



    public void setproductlist()
    {
        JSONArray response=beans.getJsonArray();
        ArrayList<String> p=new ArrayList<>();
        ArrayList<String> id=new ArrayList<>();
        try {
            // Parsing json array response
            // loop through each json object
            for (int i = 0; i < response.length(); i++) {

                JSONObject product = (JSONObject) response
                        .get(i);

                p.add(product.getString("ProductName"));
                id.add(product.getString("PID"));

            }

            beans.setProductNameList(p);
            beans.setProductIDList(id);

            ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout,beans.getProductNameList()); // get the layout
            s1.setAdapter(ad1); // set the layout for all the items
            Alerts.progressDialogStop();

        } catch (JSONException e) {
            e.printStackTrace();
           Alerts.toast(this,"Json parsing exception");
        }

    }
    ///////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Alerts.progressDialogStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Alerts.progressDialogStop();
        handle.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ConnectivityManager c = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo f = c.getActiveNetworkInfo();
            check = (f != null) && (f.isConnectedOrConnecting());

            if(!check)
            {
                Alerts.InternetDialog(AddQA.this);

            }
            else if (check) {

               Alerts.InternetDialogstop();
                retriveProductsList();
            }

            handle.postDelayed(runnable,5000);
        }
    };

}

