package com.example.ragnar.walli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;


/**
 * Created by ragnar on 8/12/17.
 */

public class Login extends AppCompatActivity {

    EditText number;
    Button login,button8;
    GifImageView gifImageView;
    boolean a;
    private static final String TAG = "PhoneAuthActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        gifImageView=(GifImageView)findViewById(R.id.gifImageView);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Welcome</font>"));
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#fe104d"));
        actionBar.setBackgroundDrawable(colorDrawable);




        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

//Log.d("M","first");
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(this,Front.class);
            startActivity(intent);
            finish();
        }
        button8=(Button)findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println();
                //Date currentTime = Calendar.getInstance().getTime();

                Snackbar.make(findViewById(android.R.id.content), "Hey,How r u?", Snackbar.LENGTH_SHORT).show();

            }
        });

        number=(EditText)findViewById(R.id.editText4);
        login=(Button)findViewById(R.id.button7);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                attemptlogin();
            }


        });
        mAuth=FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                gifImageView.setVisibility(View.GONE);
                number.setVisibility(View.VISIBLE);
                Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(Login.this,"Retry ",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(Login.this,"Retry",Toast.LENGTH_SHORT).show();

                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(Login.this,"Quota exceeded,Try Again later",Toast.LENGTH_SHORT).show();
                    // ...
                }

                // Show a message and update the UI
                // ...
                login.setClickable(true);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);

                gifImageView.setVisibility(View.GONE);
                number.setVisibility(View.VISIBLE);
                login.setClickable(true);
                Toast.makeText(Login.this,"Please Check the Number or try again",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(Login.this,"Code sent",Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

    }



    private void attemptlogin() {
        String a=number.getText().toString();
        if (a.equals("")) {
            Snackbar.make(findViewById(android.R.id.content), "Invalid Input", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(Login.this,"Invalid Input",Toast.LENGTH_SHORT).show();
        } else if (a.length() !=10) {
            Snackbar.make(findViewById(android.R.id.content), "Invalid number", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(Login.this,"Invalid number",Toast.LENGTH_SHORT).show();
        } else

        {
            number.setVisibility(View.GONE);
            gifImageView.setVisibility(View.VISIBLE);
            Snackbar.make(findViewById(android.R.id.content),"Will be verified in 60 seconds",Snackbar.LENGTH_LONG).show();
            Toast.makeText(Login.this,"Verification on process",Toast.LENGTH_SHORT).show();
            login.setClickable(false);
            startPhoneNumberVerification("+91"+a);
        }




    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    public PhoneAuthProvider.ForceResendingToken getmResendToken() {
        return mResendToken;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Toast.makeText(Login.this,"Welcome",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,Front.class));
                             finish();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI

                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                            login.setClickable(true);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }

                        }



                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();  a=haveNetworkConnection();
        if(!a)
        {
           /* View CustomToast=Front.this.getLayoutInflater().inflate(R.layout.toast,null);
            Toast toast=new Toast(getApplicationContext());
            toast.setView(CustomToast);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();*/
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(" ").append(" ");
            builder.setSpan(new ImageSpan(Login.this, R.drawable.error), builder.length() - 1, builder.length(), 0);
            builder.append(" \t   No active internet or Slow speed");
            Snackbar.make(findViewById(android.R.id.content), builder, Snackbar.LENGTH_LONG).show();
        }
    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
