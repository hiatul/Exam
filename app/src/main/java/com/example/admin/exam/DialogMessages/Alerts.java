package com.example.admin.exam.DialogMessages;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.exam.Question_pape;
import com.example.admin.exam.R;

/**
 * Created by Admin on 24-Jan-17.
 */
public class Alerts {
    static ProgressDialog pd;
    static AlertDialog.Builder builder;
    static AlertDialog aaaa;


    static Integer InternetDialog=0;

    public static void toast(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }

    public static void progressDialogStart(Context context,String message)
    {
        pd=new ProgressDialog(context);
        pd.setMessage(message);
        pd.show();
        pd.setCancelable(false);
    }

    public static void progressDialogStop()
    {
        pd.dismiss();
    }

     public static void InternetDialog(final Context context)
    {   InternetDialog=1;
        builder = new AlertDialog.Builder(context);

        builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.Internet_message)
                 .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Toast.makeText(context, "hiiiiiiii", Toast.LENGTH_SHORT).show();
                   Activity activity=(Activity)context;
                    activity.finish();
                }
            });
         aaaa=builder.create();
         aaaa.show();
        aaaa.setCancelable(false);
    }

    public static void InternetDialogstop()
    {
        if(InternetDialog==1)
        {aaaa.dismiss();
        InternetDialog=0;
        }


    }


    public  static void show_custom_Dialog(final Context c, final String PID)
    {
        final Dialog d = new Dialog(c);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.instructions);
        final Button b1 = (Button)d.findViewById(R.id.No);
        final Button b2 = (Button)d.findViewById(R.id.YES);




        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                d.dismiss();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(c.getApplicationContext(), Question_pape.class);
                i.putExtra("PID",PID);
                c.startActivity(i);
                ((Activity)(c)).finish();

            }
        });

        d.show();
    }

    public static AlertDialog dialog;


    public static void AlertBox(final Context cc)
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(cc);
        adb.setMessage("Do you want to exit test ?");
        adb.setTitle("");

        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ((Activity)(cc)).finish();

            }
        });

        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialog.dismiss();
            }
        });
       adb.setCancelable(false);
        dialog=adb.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

}
