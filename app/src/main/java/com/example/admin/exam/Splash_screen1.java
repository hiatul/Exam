package com.example.admin.exam;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.exam.DialogMessages.Alerts;

public class Splash_screen1 extends AppCompatActivity {
     android.os.Handler handle = new android.os.Handler();
    boolean b;
static  int ff=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar


        runnable.run();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handle.removeCallbacks(runnable);

    }

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ConnectivityManager c = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo f = c.getActiveNetworkInfo();
            b = (f != null) && (f.isConnectedOrConnecting());

            if (!b) {

                Alerts.InternetDialog(Splash_screen1.this);


            } else if (b&&(ff==0)) {

                new Thread()
                {
                    public void run()
                    {
                        try {

                            Alerts.InternetDialogstop();
                            Thread.sleep(7000);
                            Intent i = new Intent(getApplicationContext(), Code_Activity.class);
                            startActivity(i);
                            ff=1;
                            finish();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                }.start();




            }
            handle.postDelayed(runnable,10000);
        }
    };

}
