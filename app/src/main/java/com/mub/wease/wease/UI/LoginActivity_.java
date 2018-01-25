package com.mub.wease.wease.UI;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// Importing Google GMS Auth API Libraries.
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.BuildConfig;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mub.wease.wease.R;

//FCBK
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Andymub on 16/01/2018.
 */

public class LoginActivity_  extends AppCompatActivity  {
    private View mProgressView;
    private FirebaseAuth mAuth;
    public LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;
    private ImageView imageViewWease;

    // TAG is for show some tag logs in LOG screen.
    public static final String TAG = "MainActivity";

    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;

    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;

    // Google API Client object.
    public GoogleApiClient googleApiClient;

    // Sing out button.
    Button SignOutButton;

    // Google Sign In button .
    com.google.android.gms.common.SignInButton signInButton;

    // TextView to Show Login User Email and Name.
    TextView LoginUserName, LoginUserEmail;

    LoginButton loginButton;
    CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //initialize Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        setContentView(R.layout.activity_login);
        imageViewWease=findViewById(R.id.imageView_login_Wease);

        //TODO This one is to be deleted when app is done , right Mub ?
        imageViewWease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTo_myPDF= new Intent(LoginActivity_.this,Oder_Item_Activity.class);
                startActivity(intentTo_myPDF);
                finish();
            }
        });
        //animation bckgrnd
        getSupportActionBar().hide();

        // init constraintLayout
        linearLayout = findViewById(R.id.linearLayout_login);

        // initializing animation drawable by getting background from constraint layout
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        // setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(5000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);
        //end animation


//fcbk
        // Check if user is signed in (non-null) and update UI accordingly.
       // FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);



        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("mylog", "Successful: " + loginResult.getAccessToken());
                Log.d("mylog", "User ID: " + Profile.getCurrentProfile().getId());
                Log.d("mylog", "User Profile Pic Link: " + Profile.getCurrentProfile().getProfilePictureUri(500, 500));
                startActivity(new Intent(LoginActivity_.this,MyPDFListActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("mylog", "Successful: " + exception.toString());
            }
        });





    //fcbk


//                //MY SHA KEY
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("wease.mub.com", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        //SHA END
        // Set up the login form.signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        SignOutButton= (Button) findViewById(R.id.sign_out);

        LoginUserName = (TextView) findViewById(R.id.textViewName);

        LoginUserEmail = (TextView) findViewById(R.id.textViewEmail);

        signInButton = (com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        // Hiding the TextView on activity start up time.
        LoginUserEmail.setVisibility(View.GONE);
        LoginUserName.setVisibility(View.GONE);

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(LoginActivity_.this)
                .enableAutoManage(LoginActivity_.this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

            }
        });

        // Adding Click Listener to User Sign Out button.
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignOutFunction();

            }
        });

    }

    //fcbk
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity_.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    //fcbk


    // Sign In function Starts From Here.
    public void UserSignInMethod(){

        // Passing Google Api Client into Intent.
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, RequestSignInCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestSignInCode){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }

        }
        else //fcbk
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void updateUI (FirebaseUser user){

        //TODO GET USER data from facebook login ..
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(LoginActivity_.this,""+ authCredential.getProvider(),Toast.LENGTH_LONG).show();

        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(LoginActivity_.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {

                        if (AuthResultTask.isSuccessful()){


                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            // Showing Log out button.
                            SignOutButton.setVisibility(View.VISIBLE);

                            // Hiding Login in button.
                            signInButton.setVisibility(View.GONE);

                            // Showing the TextView.
                            LoginUserEmail.setVisibility(View.VISIBLE);
                            LoginUserName.setVisibility(View.VISIBLE);

                            // Setting up name into TextView.
                            LoginUserName.setText("NAME =  "+ firebaseUser.getDisplayName().toString());

                            // Setting up Email into TextView.
                            LoginUserEmail.setText("Email =  "+ firebaseUser.getEmail().toString());
                            Toast.makeText(LoginActivity_.this,"OOOOOOOOOOOOK",Toast.LENGTH_LONG).show();

                            startActivity(new Intent(LoginActivity_.this,MyPDFListActivity.class));

                        }else {
                            Toast.makeText(LoginActivity_.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void UserSignOutFunction() {

        // Sing Out the User.
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback() {
                    @Override
                    public void onResult(@NonNull Result result) {

                        // Write down your any code here which you want to execute After Sign Out.

                        // Printing Logout toast message on screen.
                        Toast.makeText(LoginActivity_.this, "Logout Successfully", Toast.LENGTH_LONG).show();

                    }
                });

        // After logout Hiding sign out button.
        SignOutButton.setVisibility(View.GONE);

        // After logout setting up email and name to null.
        LoginUserName.setText(null);
        LoginUserEmail.setText(null);

        // After logout setting up login button visibility to visible.
        signInButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }

}