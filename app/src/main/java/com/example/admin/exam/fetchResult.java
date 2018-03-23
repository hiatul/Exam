package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.exam.Constants.Constant;
import com.example.admin.exam.DialogMessages.Alerts;
import com.example.admin.exam.Model.Beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fetchResult extends AppCompatActivity {
    ArrayList<String> name,email,product,marks,Date,ProductName;
    Beans beans=new Beans();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_result);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();

        name=new ArrayList<>();
        email=new ArrayList<>();
        product=new ArrayList<>();
        marks=new ArrayList<>();
        Date=new ArrayList<>();
        ProductName=new ArrayList<>();

        Alerts.progressDialogStart(this,"Loading");
        makeJsonArrayRequest();// actionbar
    }

    private void makeJsonArrayRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url= Constant.URL+"UserResult.php";
        JsonArrayRequest req = null;

        req = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ARRAYRESPONSE", response.toString());

                        if(response.length()==0)
                        {
                            Alerts.toast(getApplicationContext(), "Result Not Available");
                            Intent intent=new Intent(getApplicationContext(),AdminChoicePanel.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            beans.setJsonArray(response);
                            setResult();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                Alerts.toast(getApplicationContext(),"Volley Exception");
                Alerts.progressDialogStop();
            }
        });


        // Adding request to request queue
        queue.add(req);

    }



    public void setResult()
    {
        try{

            JSONArray jsonArray=beans.getJsonArray();


            //Alerts.toast(fetchResult.this,""+jsonArray.length());
            for(int i=0;i<jsonArray.length()-1;i++) {
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                name.add(jsonObject.getString("Name"));
                email.add(jsonObject.getString("Email"));
                marks.add(jsonObject.getString("Marks"));
                product.add(jsonObject.getString("PID"));
                Date.add(jsonObject.getString("Date"));
                ProductName.add(jsonObject.getString("ProductName"));

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
            Alerts.progressDialogStop();
            Alerts.toast(fetchResult.this,"Json Exception");
        }


        Alerts.progressDialogStop();
        Intent i=new Intent(this,UserResult.class);
        i.putExtra("Name",name);
        i.putExtra("Email",email);
        i.putExtra("Marks",marks);
        i.putExtra("Product",product);
        i.putExtra("ProductName",ProductName);
        i.putExtra("Date",Date);
        startActivity(i);
        finish();

    }





}
