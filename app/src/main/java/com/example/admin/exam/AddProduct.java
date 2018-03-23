package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.exam.Constants.Constant;
import com.example.admin.exam.DialogMessages.Alerts;
import com.example.admin.exam.Model.Beans;

import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    EditText product,key;
    Beans b=new Beans();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar

        product=(EditText)findViewById(R.id.newProduct);
        key=(EditText)findViewById(R.id.newkey);
    }

    public void volleyDataSend()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL+"Product.php";
       // Alerts.toast(this, "volley start");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if(valleyResponse(response))
                        {
                           // Alerts.toast(getApplicationContext(),"Response ok");
                            forwardActivity();
                        }
                        else
                        {
                           // Alerts.toast(getApplicationContext(),"Response not ok"+response);

                            //error
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error volley api
                //Alerts.toast(getApplicationContext(),"Response Error");

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constant.PRODUCT,b.getProduct());
                params.put(Constant.KEY,b.getKey());
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
    Alerts.toast(this,"Product Inserted with Product Name:"+b.getProduct()+" And Product Key:"+b.getKey());
        product.setText("");
        key.setText("");
    }

    public void InsertProduct(View v)
    {
        b.setProduct(product.getText().toString());
        b.setKey(key.getText().toString());

        if((b.getKey().equals("")))
        {
         key.setError("Please Insert Valid Input");
        }
        else if(b.getProduct().equals(""))
        {
           product.setError("Please Insert Valid Input");
        }
        else{

            volleyDataSend();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
