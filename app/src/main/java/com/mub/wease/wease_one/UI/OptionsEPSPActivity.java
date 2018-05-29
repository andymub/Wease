package com.mub.wease.wease_one.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.mub.wease.wease_one.Adapter.CustomAdapterOptions;
import com.mub.wease.wease_one.R;

import java.io.File;

public class OptionsEPSPActivity extends AppCompatActivity {
    private AdView mAdView;

    GridView gridview;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "Weasepref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";


//    HERE WE GO TO SAVE LOGIN INFO IN CASH FILE
    private String filename = "WeaseFile.txt";
    private String filepath = "WeaseCash";
    private String TAG_WRITE_READ_FILE = "TAG_WRITE_READ_FILE";
    File weaseCash;
    String dataToSend = "";
    String myData = "";
    private Boolean exit = false;


//
    public static String[] osNameList_options = {
            "Math-Physique",
            "Bio-Chimie",
            "Littéraire",
        "Commerciale Admi",
        "Humanité Pédagogique",
        "Sociale",
        "Construction",
        "Agriculture générale",
        "Commerciale informatique",
        "Construction métallique",
        "Electricité",
        "Eléctronique industrielle",
        "Coupe et couture",
        "Industries agricoles",
        "Informatique de gestion",
        "Menuiserie",
        "Mécanique générale",
        "Mécanique Machines et outils",
        "Mines et géologie",
        "Musique",
        "Nutrition",
        "Pêche et navigation",
        "Pétrochimie",
        "Vétérinaire",
        "Chimie industrielle",


};
    public static int[] osImages_options = {
            R.mipmap.ic_math_phys,
            R.mipmap.ic_bio_chimie,
            R.mipmap.ic_litteraire,
            R.mipmap.ic_commercial,
            R.mipmap.ic_pedagogie_true,
            R.mipmap.ic_pedagogie,
            R.mipmap.ic_construction,
            R.mipmap.ic_agriculture,
            R.mipmap.ic_commerciale_info,
            R.mipmap.ic_construction_metallique,
            R.mipmap.ic_electricite,
            R.mipmap.ic_electronique_industrielle,
            R.mipmap.ic_coupe_et_coutur,
            R.mipmap.ic_industries_agricoles,
            R.mipmap.ic_informatique_de_gestion,
            R.mipmap.ic_menuiserie,
            R.mipmap.ic_mecanique_generale,
            R.mipmap.ic_mecanique_machines_outils,
            R.mipmap.ic_mines_et_geologie,
            R.mipmap.ic_musique,
            R.mipmap.ic_nutrition,
            R.mipmap.ic_peche_et_navigation,
            R.mipmap.ic_petrochimie,
            R.mipmap.ic_veterinaire,
            R.mipmap.ic_chimie_industrielle,

    }
           ;
    public String[] res;
    public int introwView;
    public TextView userName,userEmail;
    // Firebaase Auth Object.
    public FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_epsp);

        MobileAds.initialize(this,
                "ca-app-pub-2935537081813551~2797110112");

        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                // Check the LogCat to get your test device ID
//                .addTestDevice("C04B1BFFB0774708339BC273F8A43708")
//                .build();
//        mAdView.loadAd(adRequest);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        FirebaseAuth Auth =FirebaseAuth.getInstance();
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userName=findViewById(R.id.txtuser_name);
        userEmail=findViewById(R.id.txtuser_email);
        gridview = findViewById(R.id.customgrid_options_rdc);
        gridview.setAdapter(new CustomAdapterOptions(this, osNameList_options, osImages_options));
       // rowView=gridview.setAdapter(new CustomAdapterOptions(this, osNameList_options, osImages_options).getView());
       // rowView = findViewById(R.layout.sample_gridlayout_options);


        if(getIntent().getStringArrayExtra("Id_User") != null) {
            String []user =getIntent().getStringArrayExtra("Id_User");
          //  String[] nom= user[0].split("!");
            String[] eMail= user[1].split("=");

            userName.setText(user[0]);
            userEmail.setText(eMail[1]);

            String n = user[0];
            String e = user[1];
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Name, n);
            editor.putString(Email, e);
            editor.commit();
            dataToSend=userName.getText().toString().trim()+"-"+userEmail.getText().toString().trim();


        }
        else if (sharedpreferences.contains(Name)== false){
//            userName.setText("wease");
//            userEmail.setText("WeaseEmail");

                Intent intentBackToLogin =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentBackToLogin);
                finish();

        }
        else {


            if (sharedpreferences.contains(Name)) {
                String d = (sharedpreferences.getString(Name, ""));
                String[] Nd = d.split("!");
                userName.setText(Nd[1]);
            }
            if (sharedpreferences.contains(Email)) {
                String d = (sharedpreferences.getString(Email, ""));
                String[] Nd = d.split("=");
                userEmail.setText(Nd[1]);

            }
        }




        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               introwView=introwView;
//               Log.i("DDDD",""+introwView);

           }
       });

//        CustomAdapterOptions customAdapterOptions = new CustomAdapterOptions(this, osNameList_options, osImages_options);
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "You 2 Clicked 2 "+position, Toast.LENGTH_SHORT).show();
//            }
//        });
//        String [] res = customAdapterOptions.getItemClicked();
//        int y;
//        Toast.makeText(getApplicationContext(), "You Clicked 2 "+res[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int p=7;

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //todo create file here
        int p=7;

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        // Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show();
        if (exit) {
            sharedpreferences.edit().clear().commit();
            finish();
            moveTaskToBack(true);
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



//        Intent intentBackToLogin =new Intent(getApplicationContext(),LoginActivity_.class);
//        startActivity(intentBackToLogin);
//        finish();
    }





}
