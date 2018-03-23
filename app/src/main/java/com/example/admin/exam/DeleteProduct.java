package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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

public class DeleteProduct extends AppCompatActivity {
   Spinner s1;
    String arr1[] = {"None"};
    Beans beans=new Beans();
    ArrayAdapter ad1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        retriveProductsList();
        Alerts.progressDialogStart(this,"Loading");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar
        s1 = (Spinner) findViewById(R.id.spinner_Delete_Product);
        ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout, arr1); // get the layout
        s1.setAdapter(ad1); // set the layout for all the items
    }

///////////////////////////////////Retrive Products/////////////////////////////
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
                            Alerts.toast(getApplicationContext(),"Please Add Some Prodcts First");
                            Intent i=new Intent(getApplicationContext(),ManageProducts.class);
                            startActivity(i);
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

            ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout,beans.getProductNameList()); // get the layout
            s1.setAdapter(ad1); // set the layout for all the items
            Alerts.progressDialogStop();

        } catch (JSONException e) {
            e.printStackTrace();
            Alerts.toast(this,"Json parsing exception");
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////Delete Product/////////////////////////////////////////

    public void deleteProduct()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL + "DeleteProduct.php";

       // Alerts.toast(this, "volley start");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (valleyResponse(response)) {
                           // Alerts.toast(getApplicationContext(), "Response ok");
                            Alerts.toast(getApplicationContext(),"Product Deleted");
                            forwardActivity();
                            deleteQuestions();
                        } else {
                           // Alerts.toast(getApplicationContext(), "Response not ok" + response);

                            //error
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error volley api
               // Alerts.toast(getApplicationContext(), "Response Error");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.PID, beans.getPID());
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



        retriveProductsList();
        Alerts.progressDialogStart(this,"Loading");
    }

///////////////////////////////////////////////////////////////////////////////
    public void DeletePro(View V)
    {
        if(s1.getSelectedItem().toString().equals("None"))
        {
            Alerts.toast(this,"Please Select a valid Product");
        }
        else {
            ArrayList pid=beans.getProductIDList();
            beans.setPID(pid.get(s1.getSelectedItemPosition()).toString());
            deleteProduct();
        }

    }

    //////////////////////////////////////Delete Questions/////////////////////////

    public void deleteQuestions()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL + "DeleteProductQuestion.php";

        // Alerts.toast(this, "volley start");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (valleyResponse(response)) {
                             Alerts.toast(getApplicationContext(), "Response ok");
                        } else {
                             Alerts.toast(getApplicationContext(), "Response not ok" + response);
                            //error
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error volley api
                // Alerts.toast(getApplicationContext(), "Response Error");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.PID, beans.getPID());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    /////////////////////////////////////////////////////////////////////


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
