package com.example.admin.exam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class ManageProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar


    }

    public void AddProducts(View v)
    {
        Intent i = new Intent(getApplicationContext(),AddProduct.class);
        startActivity(i);

    }
    public void DeleteProducts(View v)
    {
        Intent i = new Intent(getApplicationContext(),DeleteProduct.class);
        startActivity(i);

    }
    public void UpdateProducts(View v)
    {
        Intent i = new Intent(getApplicationContext(),UpdateProduct.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }
}
