package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;

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
import java.util.jar.Attributes;

public class UserResult extends AppCompatActivity {
    ListView l;
    Beans beans=new Beans();
    ArrayList<String> s1 =new ArrayList<>();
    String s2[],s3[],s4[];
    ArrayList<String> name,email,product,marks,Date,ProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_result);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar


        Bundle b=getIntent().getExtras();
        name=b.getStringArrayList("Name");
        email=b.getStringArrayList("Email");
        marks=b.getStringArrayList("Marks");
        product=b.getStringArrayList("Product");
        Date=b.getStringArrayList("Date");
        ProductName=b.getStringArrayList("ProductName");


        l= (ListView)findViewById(R.id.User_list);
        IA_AdminListview an=new IA_AdminListview(this,name,email,marks,ProductName,Date);
        l.setAdapter(an);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }

}
