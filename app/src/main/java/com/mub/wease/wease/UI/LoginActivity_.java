package com.mub.wease.wease.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.BuildConfig;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mub.wease.wease.R;

// Importing Google GMS Auth API Libraries.
//FCBK

/**
 * Created by Andymub on 16/01/2018.
 */

public class LoginActivity_  extends AppCompatActivity  {
    private View mProgressView;
    private FirebaseAuth mAuth;
    public LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;
    private Boolean exit = false;

    // TAG is for show some tag logs in LOG screen.
    public static final String TAG = "MainActivity";

    // Request sing in code. Could be anything as you required.
    public static final int RequestSignInCode = 7;

    // Firebase Auth Object.
   // public FirebaseAuth firebaseAuth;

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
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
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
        ImageView imageViewWease = findViewById(R.id.imageView_login_Wease);


        // Manually checking internet connection
        boolean connc =isConnected();

        //TODO This one is to be deleted when app is done , right Mub ?
        imageViewWease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intentTo_myPDF= new Intent(LoginActivity_.this,Order_Item_Activity.class);
                Intent intentTo_myPDF= new Intent(LoginActivity_.this,OptionsEPSPActivity.class);
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
       // loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("email", "public_profile");
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
                // App code
//                Log.d("mylog", "Successful: " + loginResult.getAccessToken());
               // String s= Profile.getCurrentProfile().getFirstName();
                //Log.d("mylog", "User ID: " + Profile.getCurrentProfile().getId());
               // Log.d("mylog", "User Profile Pic Link: " + Profile.getCurrentProfile().getProfilePictureUri(500, 500));
                startActivity(new Intent(LoginActivity_.this,OptionsEPSPActivity.class));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
//                Log.d("mylog", "Successful: " + exception.toString());
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

        SignOutButton= findViewById(R.id.sign_out);

        LoginUserName = findViewById(R.id.textViewName);

        LoginUserEmail = findViewById(R.id.textViewEmail);

        signInButton = findViewById(R.id.sign_in_button);

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
                // Manually checking internet connection
                if (isConnected()){
                    UserSignInMethod();
                }

            }
        });

        // Adding Click Listener to User Sign Out button.
        SignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isConnected()){
                    UserSignOutFunction();
                }

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListner = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                int i=4;
                if(user != null){
                    String userId = user.getUid();
                    String userEmail = user.getEmail();
                    goMainScreen();
                }
            }
        };

    }
    //check connection
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
//            Log.e("Connectivity Exception", e.getMessage());
        }
        showSnack(connected);
        return connected;
    }

    // Showing the status in Snackbar
    private int showSnack(boolean isConnected) {
        int conn;
        if (isConnected) {
            conn=0;

        } else {
            Toast.makeText(getApplicationContext(),""+R.string.noconnection,Toast.LENGTH_SHORT).show();
            conn=1;
        }

      return  conn;
    }

    //end check conn



    //fcbk

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"firebase_error_login", Toast.LENGTH_LONG).show();
                }
                //
                if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity_.this, "Hey !",
                                    Toast.LENGTH_SHORT).show();
                        }
                //
            }
        });
    }

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
        {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void updateUI (FirebaseUser user){

        //TODO GET USER data from facebook login ..
        String name =user.getDisplayName();
        String Email = user.getEmail();
    }
    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(LoginActivity_.this,"Welcome",Toast.LENGTH_SHORT).show();

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
                            LoginUserEmail.setVisibility(View.INVISIBLE);
                            LoginUserName.setVisibility(View.VISIBLE);

                            // Setting up name into TextView.
                            LoginUserName.setText("Hi! "+ firebaseUser.getDisplayName().toString());

                            // Setting up Email into TextView.
                            LoginUserEmail.setText("Email =  "+ firebaseUser.getEmail().toString());
                            //Toast.makeText(LoginActivity_.this,"OOOOOOOOOOOOK",Toast.LENGTH_LONG).show();

                           // startActivity(new Intent(LoginActivity_.this,OptionsEPSPActivity.class));
                            String [] userN= new String[]{LoginUserName.getText().toString(),LoginUserEmail.getText().toString()};

                            Intent activityOptionsEpsPIntent = new Intent(getApplicationContext(), OptionsEPSPActivity.class);
                            activityOptionsEpsPIntent.putExtra("Id_User",userN);
                            startActivity(activityOptionsEpsPIntent);

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
    protected void onDestroy() {
        // This method should be called in the parent Activity's onPause() method.
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }
        // register connection status listener
//        MyApplication.getInstance().setConnectivityListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(firebaseAuthListner);
    }
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListner);
    }

    @Override
    protected void onPause() {
        // This method should be called in the parent Activity's onPause() method.
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }


    public void goMainScreen(){
        Intent intent = new Intent(this, LoginActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //fcbk
    //    }
//                });
//                    }
//                        // ...
//
//                        }
//                            updateUI(null);
//                                    Toast.LENGTH_SHORT).show();
//                            Toast.makeText(LoginActivity_.this, "Authentication failed.",
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            // If sign in fails, display a message to the user.
//                        } else {
//                                    Toast.LENGTH_SHORT).show();
//                            Toast.makeText(LoginActivity_.this, "eyhhhhhhhhhhhhhh",
//                            updateUI(user);
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Log.d(TAG, "signInWithCredential:success");
//                            // Sign in success, update UI with the signed-in user's information
//                        if (task.isSuccessful()) {
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                    @Override
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//        mAuth.signInWithCredential(credential)
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//    private void handleFacebookAccessToken(AccessToken token) {
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        // Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
        if (exit) {
            finish(); // finish activity

        } else {
            Toast.makeText(this,R.string.backToExitt,
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }


    }

}