package com.example.admin.exam;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class IA_AdminListview extends ArrayAdapter

{
    ArrayList<String> Name;
    ArrayList<String> Email;
    ArrayList<String> Product;
    ArrayList<String> Marks;
    ArrayList<String> Date;
    Activity context;
    TextView Username,UserEmail,UserMarks,ExamProduct,ExamDate;

    public  IA_AdminListview(Activity context, ArrayList<String> Name,ArrayList<String> Email,ArrayList<String> Marks,ArrayList<String> Product,ArrayList<String> Date)
    {
        super(context, R.layout.admin_custom_list,Name);
        this.Name=Name;
        this.Marks=Marks;
        this.Email=Email;
        this.Product=Product;
        this.Date=Date;
        this.context=context;


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = context.getLayoutInflater().inflate(R.layout.admin_custom_list, null);

       Username=(TextView)v.findViewById(R.id.User_name);
        UserEmail=(TextView)v.findViewById(R.id.User_email);
          UserMarks=(TextView)v.findViewById(R.id.User_Marks);
        ExamProduct=(TextView)v.findViewById(R.id.Exam_Product);
        ExamDate=(TextView)v.findViewById(R.id.Exam_Date);

         Username.setText(Name.get(position));
        UserEmail.setText(Email.get(position));
        UserMarks.setText(Marks.get(position));
        ExamProduct.setText(Product.get(position));
        ExamDate.setText(Date.get(position));


        return v;
    }
}
