package com.example.admin.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.exam.Constants.Constant;
import com.example.admin.exam.DialogMessages.Alerts;
import com.example.admin.exam.Model.Beans;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity1 extends AppCompatActivity implements

        GoogleApiClient.OnConnectionFailedListener {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TAG = MainActivity1.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    Beans b1=new Beans();
    //    private SignInButton btnSignIn;
////////////////////////////////////////////////////////////////////////////////////////////////
    String arr1[] = {"None", "Distributer", "Dealer", "Sales", "Participant"};
    Spinner s1;
    LinearLayout partition;
    EditText e1, e2, e3;
    Button b, google;
    Integer flag = 0;

    //////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);// for full screen
        getSupportActionBar().hide();  // actionbar
        b = (Button) findViewById(R.id.b1);
        google = (Button) findViewById(R.id.google);
        e1 = (EditText) findViewById(R.id.e1);
        e2 = (EditText) findViewById(R.id.e2);
        e3 = (EditText) findViewById(R.id.e3);
        s1 = (Spinner) findViewById(R.id.sp1);
        partition=(LinearLayout) findViewById(R.id.partition);

        ArrayAdapter ad1 = new ArrayAdapter(getApplicationContext(), R.layout.textlayout, arr1); // get the layout
        s1.setAdapter(ad1); // set the layout for all the items


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        // Customizing G+ button
//        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
//        btnSignIn.setScopes(gso.getScopeArray());


        //////////////////////////////////////////////////////////////////////////////////


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Integer k = s1.getSelectedItemPosition();
                boolean a = validPhone(e3.getText().toString());
                boolean bb = isValidEmail(e2.getText().toString());


                if((e1.getText().toString().equals(""))||Character.isDigit(e1.getText().toString().charAt(0)))
                {
                    e1.setError("Field Incorrect");
                }

               else if (!bb) {
                    e2.setError("Field Incorrect");
                }

                else if ((!a) || (e3.length() != 10)) {
                    e3.setError("Field Incorrect");
                }

                else if (k == 0) {
                    Toast.makeText(MainActivity1.this, "Select Profile", Toast.LENGTH_SHORT).show();
                }


                else if (a && bb && (e3.length() == 10) && k != 0&&(!e1.getText().toString().equals(""))) {
                    setData();


                }


            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                signOut();
            }
        });

    }


    private boolean validPhone(String phone) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        btnSignIn.setVisibility(View.GONE);
                        google.setVisibility(View.GONE);
                        partition.setVisibility(View.GONE);
                    }
                });
    }


    private void handleSignInResult(GoogleSignInResult result) { // getting the info
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

//
//            personPhotoUrl = acct.getPhotoUrl().toString();
//            FamilyName=acct.getFamilyName();
//             id=acct.getId();
//          un= acct.getGivenName();


//            Log.e(TAG, "Name: " + personName + ", email: " + email
//                    + ", Image: " + personPhotoUrl);

            //Alerts.toast(MainActivity1.this,"Name:"+acct.getDisplayName()+" Email:"+acct.getEmail());
            e1.setText(acct.getDisplayName());
            e2.setText(acct.getEmail());
            e2.setEnabled(false);


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void setData() {

        b1.setName(e1.getText().toString());
        b1.setEmail(e2.getText().toString());
        b1.setPhone(e3.getText().toString());
        b1.setProfile(arr1[s1.getSelectedItemPosition()]);

//        Alerts.toast(this, "Data  Set");
        vallleyDataSend();
    }

    public void vallleyDataSend() {
        Alerts.progressDialogStart(MainActivity1.this,"Loading");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constant.URL + "ExamUser.php";

        //Alerts.toast(this, "volley start");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        if (valleyResponse(response)) {
                           // Alerts.toast(getApplicationContext(), "Response ok");

                            forwardActivity();
                        } else {
                           // Alerts.toast(getApplicationContext(), "Response not ok" + response);

                            //error
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error volley api
                //Alerts.toast(getApplicationContext(), "Response Error");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constant.NAME, b1.getName());
                params.put(Constant.EMAIL, b1.getEmail());
                params.put(Constant.PHONE, b1.getPhone());
                params.put(Constant.PROFILE, b1.getProfile());
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
        sharedprefData();
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(i);
        finish();

    }


    public void sharedprefData() {

        flag = 1;
        SharedPreferences s = getSharedPreferences("Details_of_user", MODE_PRIVATE);
        SharedPreferences.Editor ed = s.edit();
        ed.clear();
        ed.putString("Name", b1.getName());
        ed.putString("Email", b1.getEmail());
        ed.putString("phone", b1.getPhone());
        ed.putString("profession", b1.getProfile());
        ed.putInt("flag", flag);
        ed.commit();
        Alerts.progressDialogStop();
    }

}
