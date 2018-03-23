package com.example.admin.exam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.google.android.gms.common.SignInButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Button start_test;
    EditText e1;
    Spinner s1;
    Beans beans=new Beans();
    ArrayAdapter ad1;
    //private SignInButton btnSignIn;
    String arr2[] = {"None"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        e1 = (EditText) findViewById(R.id.key);
        s1 = (Spinner) findViewById(R.id.sp1);

        Alerts.progressDialogStart(this,"Loading");
        retriveProductsList();

        start_test=(Button)findViewById(R.id.start_test);
        ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout1, arr2); // get the layout
        s1.setAdapter(ad1); // set the layout for all the items

                s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (!s1.getSelectedItem().toString().equals("None")) {
                            ArrayList k = beans.getProductKeyList();
                            ArrayList id=beans.getProductIDList();

                            beans.setPID(id.get(s1.getSelectedItemPosition()).toString());
                            beans.setKey(k.get(s1.getSelectedItemPosition()).toString());
                            beans.setProduct(s1.getSelectedItem().toString());
                        }
                        if (s1.getSelectedItem().toString().equals("None"))
                        {
                            e1.setText("");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.item1)
        {
            Intent i = new Intent(getApplicationContext(), UserDetail.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }


    ///////////////////////////Retrive Products//////////////////////////
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
                            Alerts.toast(getApplicationContext(),"App is not available for use right now");
                            Alerts.progressDialogStop();
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

                JSONObject product = (JSONObject) response.get(i);

                p.add(product.getString("ProductName"));
                k.add(product.getString("ProductKey"));
                id.add(product.getString("PID"));
            }

            beans.setProductNameList(p);
            beans.setProductKeyList(k);
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
    }




    public void startTest(View view)
    {
        if(!e1.getText().toString().equals(beans.getKey())) {
            e1.setError("Invalid Key");
        }
        else {
            Alerts.show_custom_Dialog(this,beans.getPID());

        }
    }


}
