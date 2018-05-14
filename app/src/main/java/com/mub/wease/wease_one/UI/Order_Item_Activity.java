package com.mub.wease.wease_one.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mub.wease.wease_one.Adapter.CustomAdapterOder_item;
import com.mub.wease.wease_one.Data.Constants;
import com.mub.wease.wease_one.Data.GridState;
import com.mub.wease.wease_one.Data.Upload;
import com.mub.wease.wease_one.R;

import java.util.ArrayList;
import java.util.List;

public class Order_Item_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //database reference to get uploads data
    DatabaseReference mDatabaseReference;

    StorageReference mStorageReference;

    //list to store uploads data
    List<Upload> uploadList;
    //

    GridView gridview;
    int countG =0;
GridState [] gridStates;
    public TextView optionsTxtV;
    public Menu groupeMath;
    public  String[] osNameList = {
            "Module x",
            "Module y",
            "Module z",
            "Module i",
            "Module j",
            "Module u",
            "Module x10",
            "Module x XP",
            "Module y",
            "Module z",
            "Module i",};
    public  int[] osImages = {
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
            R.mipmap.ic_folderlogo1,
    };
    public  String [] result_anne={"2016","2016","2014","2016","2016","2014","2016","2016","2014","2016","2016"};
    public  String [] result_prix={"FREE","1$","FREE","1$","1$","FREE","1$","1$","FREE","FREE","1$","1$",};
    public  String [] result_version={"V1","V3","V1","V2","V3","V1","V2","V3","V1","V1","V4","V2"};
    public  String [] result_links;
    public final static String selectedOption="selectedOption";
    public ConstraintLayout constraintOrderItem;
    private SwipeRefreshLayout swipeContainer;
    public String optionSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder__item_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get intent here
        Intent intentSelectedOption =getIntent();
         optionSelected = intentSelectedOption.getStringExtra(selectedOption);
        optionsTxtV=findViewById(R.id.os_texts_option_order_activity);
        constraintOrderItem = findViewById(R.id.constraintOrderItem);

        changeConstrainLytBckgnd(constraintOrderItem);
        new LoardListOfItems(Order_Item_Activity.this).execute();
        int y=5;

        // Lookup the swipe container view

        swipeContainer = findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                // Your code to refresh the list here.

                // Make sure you call swipeContainer.setRefreshing(false)

                // once the network request has completed successfully.

                fetchTimelineAsync(0);

            }

        });

        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

        //set optiontxt
        optionsTxtV.setText(optionSelected);



        //
        Constants.setNameOfExam(optionSelected);
       Constants.setDatabasePathUploads(Constants.getDatabasePathUploads()); //Todo se exam type over here
       // Constants.setDatabasePathUploads("Bio-Chimie"); //Todo se exam type over here
       // Constants.setDatabasePathUploads("CULTURE"); //Todo se exam type over here
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Ces Modules sont gratuits ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        int h=4;


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //GridView
        gridview = findViewById(R.id.customgridforItems);
        // gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridview.setVisibility(View.INVISIBLE);
        gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
        finish();
        Intent intentBackToOptionESPS =new Intent(getApplicationContext(),OptionsEPSPActivity.class);
        startActivity(intentBackToOptionESPS);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        String g=optionsTxtV.getText().toString();
        String gf="Math-Physique";
        //set menu
        Boolean s="Math-Physique".trim().equals(Constants.getNameOfExam().trim());
        if (s)
        {
            int h=4;
            getMenuInflater().inflate(R.menu.oder_item_math , menu);
            // groupeMath.setGroupVisible(R.id.groupeMath,true);
        }
        else if("Bio-Chimie".trim().equals(Constants.getNameOfExam().trim())){
            getMenuInflater().inflate(R.menu.oder_item_biochimie , menu);
        }
        else if("Littéraire".trim().equals(Constants.getNameOfExam().trim())){
            getMenuInflater().inflate(R.menu.oder_item_litteraire , menu);
        }
        else if("Commerciale Admi".trim().equals(Constants.getNameOfExam().trim())){
            getMenuInflater().inflate(R.menu.oder_item_commercial , menu);
        }
        else if("Pedagogie".trim().equals(Constants.getNameOfExam().trim())){
            getMenuInflater().inflate(R.menu.oder_item_pedagogie , menu);
        }else if("Sociale".trim().equals(Constants.getNameOfExam().trim())){
            getMenuInflater().inflate(R.menu.oder_item_sociale, menu);
        }
        else
        {
            int h=4;
            getMenuInflater().inflate(R.menu.oder__item_ , menu);
            //groupeMath.setGroupVisible(R.id.Gmath,false);
        }


            int t =10;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_culture_general_for_all) {

            //CULTURE GENERAL FOR
            //Humanité Pédagogique
            if (optionSelected.trim().equals(("Humanité Pédagogique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Sociale
            else if(optionSelected.trim().equals(("Sociale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction
            else if(optionSelected.trim().equals(("Construction").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Agriculture générale
            else if(optionSelected.trim().equals(("Agriculture générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Commerciale informatique
            else if(optionSelected.trim().equals(("Commerciale informatique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction métallique
            else if(optionSelected.trim().equals(("Construction métallique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Electricité
            else if(optionSelected.trim().equals(("Electricité").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Eléctronique industrielle
            else if(optionSelected.trim().equals(("Eléctronique industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Coupe et couture
            else if(optionSelected.trim().equals(("Coupe et couture").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Industries agricoles
            else if(optionSelected.trim().equals(("Industries agricoles").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Informatique de gestion
            else if(optionSelected.trim().equals(("Informatique de gestion").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Menuiserie
            else if(optionSelected.trim().equals(("Menuiserie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mines et géologie
            else if(optionSelected.trim().equals(("Mines et géologie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Musique
            else if(optionSelected.trim().equals(("Musique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Nutrition
            else if(optionSelected.trim().equals(("Nutrition").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pêche et navigation
            else if(optionSelected.trim().equals(("Pêche et navigation").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pétrochimie
            else if(optionSelected.trim().equals(("Pétrochimie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Vétérinaire
            else if(optionSelected.trim().equals(("Vétérinaire").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Chimie industrielle
            else if(optionSelected.trim().equals(("Chimie industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }

        }
        else if (id == R.id.action_options_for_all) {
            //OPTIONS FOR
            //Humanité Pédagogique
            if (optionSelected.trim().equals(("Humanité Pédagogique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Sociale
            else if(optionSelected.trim().equals(("Sociale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction
            else if(optionSelected.trim().equals(("Construction").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Agriculture générale
            else if(optionSelected.trim().equals(("Agriculture générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Commerciale informatique
            else if(optionSelected.trim().equals(("Commerciale informatique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction métallique
            else if(optionSelected.trim().equals(("Construction métallique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Electricité
            else if(optionSelected.trim().equals(("Electricité").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Eléctronique industrielle
            else if(optionSelected.trim().equals(("Eléctronique industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Coupe et couture
            else if(optionSelected.trim().equals(("Coupe et couture").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Industries agricoles
            else if(optionSelected.trim().equals(("Industries agricoles").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Informatique de gestion
            else if(optionSelected.trim().equals(("Informatique de gestion").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Menuiserie
            else if(optionSelected.trim().equals(("Menuiserie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mines et géologie
            else if(optionSelected.trim().equals(("Mines et géologie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Musique
            else if(optionSelected.trim().equals(("Musique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Nutrition
            else if(optionSelected.trim().equals(("Nutrition").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pêche et navigation
            else if(optionSelected.trim().equals(("Pêche et navigation").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pétrochimie
            else if(optionSelected.trim().equals(("Pétrochimie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Vétérinaire
            else if(optionSelected.trim().equals(("Vétérinaire").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Chimie industrielle
            else if(optionSelected.trim().equals(("Chimie industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
        }
        else if (id == R.id.action_Sciences_for_all) {
            //SCIENCES FOR
            //Humanité Pédagogique
            if (optionSelected.trim().equals(("Humanité Pédagogique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Sociale
            else if(optionSelected.trim().equals(("Sociale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction
            else if(optionSelected.trim().equals(("Construction").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Agriculture générale
            else if(optionSelected.trim().equals(("Agriculture générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Commerciale informatique
            else if(optionSelected.trim().equals(("Commerciale informatique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction métallique
            else if(optionSelected.trim().equals(("Construction métallique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Electricité
            else if(optionSelected.trim().equals(("Electricité").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Eléctronique industrielle
            else if(optionSelected.trim().equals(("Eléctronique industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Coupe et couture
            else if(optionSelected.trim().equals(("Coupe et couture").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Industries agricoles
            else if(optionSelected.trim().equals(("Industries agricoles").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Informatique de gestion
            else if(optionSelected.trim().equals(("Informatique de gestion").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Menuiserie
            else if(optionSelected.trim().equals(("Menuiserie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mines et géologie
            else if(optionSelected.trim().equals(("Mines et géologie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Musique
            else if(optionSelected.trim().equals(("Musique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Nutrition
            else if(optionSelected.trim().equals(("Nutrition").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pêche et navigation
            else if(optionSelected.trim().equals(("Pêche et navigation").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pétrochimie
            else if(optionSelected.trim().equals(("Pétrochimie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Vétérinaire
            else if(optionSelected.trim().equals(("Vétérinaire").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Chimie industrielle
            else if(optionSelected.trim().equals(("Chimie industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
        }
        else if (id == R.id.action_Langues_for_all) {
            //Langue FOR
            //Humanité Pédagogique
            if (optionSelected.trim().equals(("Humanité Pédagogique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Sociale
            else if(optionSelected.trim().equals(("Sociale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction
            else if(optionSelected.trim().equals(("Construction").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Agriculture générale
            else if(optionSelected.trim().equals(("Agriculture générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Commerciale informatique
            else if(optionSelected.trim().equals(("Commerciale informatique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Construction métallique
            else if(optionSelected.trim().equals(("Construction métallique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Electricité
            else if(optionSelected.trim().equals(("Electricité").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Eléctronique industrielle
            else if(optionSelected.trim().equals(("Eléctronique industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Coupe et couture
            else if(optionSelected.trim().equals(("Coupe et couture").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Industries agricoles
            else if(optionSelected.trim().equals(("Industries agricoles").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Informatique de gestion
            else if(optionSelected.trim().equals(("Informatique de gestion").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Menuiserie
            else if(optionSelected.trim().equals(("Menuiserie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mécanique générale
            else if(optionSelected.trim().equals(("Mécanique générale").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Mines et géologie
            else if(optionSelected.trim().equals(("Mines et géologie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Musique
            else if(optionSelected.trim().equals(("Musique").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Nutrition
            else if(optionSelected.trim().equals(("Nutrition").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pêche et navigation
            else if(optionSelected.trim().equals(("Pêche et navigation").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Pétrochimie
            else if(optionSelected.trim().equals(("Pétrochimie").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Vétérinaire
            else if(optionSelected.trim().equals(("Vétérinaire").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
            //Chimie industrielle
            else if(optionSelected.trim().equals(("Chimie industrielle").trim())){
                Toast.makeText(getApplicationContext(),""+optionSelected.trim(),Toast.LENGTH_SHORT ).show();
            }
        }
        else if (id == R.id.action_math) {
            Constants.setDatabasePathUploads("Math".trim());
            CustomAdapterOder_item customAdapterOder_item= new CustomAdapterOder_item();
            //gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));
            Intent intentOder_Item_activity= new Intent(this, Order_Item_Activity.class);
            String option = optionsTxtV.getText().toString().trim();
            intentOder_Item_activity.putExtra(selectedOption,option);
            startActivity(intentOder_Item_activity);

        }
        else if(id == R.id.action_mathphys_Langue) {
            languageMenuForAllItem();

        }else if(id == R.id.action_bio_chimie_Langue) {
            languageMenuForAllItem();

        }else if(id == R.id.action_pada_Langue) {
            languageMenuForAllItem();

        }else if(id == R.id.action_litteraire_Langue) {
            languageMenuForAllItem();

        }
        else if(id == R.id.action_commerciale_Langue) {
            languageMenuForAllItem();

        }else if(id == R.id.action_sociale_Langue) {
            languageMenuForAllItem();

        }else if(id == R.id.action_bio_chimie_cours_options) {
//            Constants.setDatabasePathUploads("Bio-Chimie".trim());
//            CustomAdapterOder_item customAdapterOder_item= new CustomAdapterOder_item();
//            //gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));
//            Intent intentOder_Item_activity= new Intent(this, Order_Item_Activity.class);
//            String option = optionsTxtV.getText().toString().trim();
//            intentOder_Item_activity.putExtra(selectedOption,option);
//            startActivity(intentOder_Item_activity);
            cultureMenuForAllItem();

        }else if(id == R.id.action_bio_chimie_math) {
            Constants.setDatabasePathUploads("BioChimieMath".trim());
            CustomAdapterOder_item customAdapterOder_item= new CustomAdapterOder_item();
            //gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));
            Intent intentOder_Item_activity= new Intent(this, Order_Item_Activity.class);
            String option = optionsTxtV.getText().toString().trim();
            intentOder_Item_activity.putExtra(selectedOption,option);
            startActivity(intentOder_Item_activity);

        }else if(id == R.id.action_mathphys_culture) {
//            Constants.setDatabasePathUploads("CULTURE".trim());
//            CustomAdapterOder_item customAdapterOder_item= new CustomAdapterOder_item();
//            //gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));
//            Intent intentOder_Item_activity= new Intent(this, Order_Item_Activity.class);
//            String option = optionsTxtV.getText().toString().trim();
//            intentOder_Item_activity.putExtra(selectedOption,option);
//            startActivity(intentOder_Item_activity);
            cultureMenuForAllItem();

        }
        else if(id == R.id.action_bio_chimie_culture) {
            cultureMenuForAllItem();
        }
        else if(id == R.id.action_pada_culture) {
            cultureMenuForAllItem();

        }else if(id == R.id.action_sociale_culture) {
            cultureMenuForAllItem();

        }else if(id == R.id.action_litteraire_culture) {
            cultureMenuForAllItem();

        }else if(id == R.id.action_commerciale_culture) {
            Toast.makeText(getApplicationContext(),"Holla",Toast.LENGTH_SHORT ).show();
            cultureMenuForAllItem();

        }
        else if(id == R.id.action_mathphys_sciences) {
            //todo
            allMenuCallForItem("MathPhys_sciences");

        }

        else if(id == R.id.action_sociale_cours_options) {
            //todo
            allMenuCallForItem("sociale_cours_options");
        }
        else if(id == R.id.action_sociale_sciences) {
            //todo
            allMenuCallForItem("sociale_sciences");

        }else if(id == R.id.action_litteraire_cours_options) {
            //todo
            allMenuCallForItem("litteraire_cours_options");

        }
        else if(id == R.id.action_litteraire_sciences) {
            //todo
            allMenuCallForItem("litteraire_sciences");

        }else if(id == R.id.action_commerciale_cours_options) {
            //todo
            allMenuCallForItem("commerciale_cours_options");

        }
        else if(id == R.id.action_commerciale_sciences) {
            //todo
            allMenuCallForItem("commerciale_sciences");

        }else if(id == R.id.action_pada_cours_options) {
            //todo
            allMenuCallForItem("pada_cours_options");

        }
        else if(id == R.id.action_pada_sciences) {
            //todo
            allMenuCallForItem("pada_sciences");

        }


        return super.onOptionsItemSelected(item);
    }

    public void languageMenuForAllItem (){
        allMenuCallForItem("langue");
    }
    public void cultureMenuForAllItem(){
        allMenuCallForItem("CULTURE");
    }
    public void allMenuCallForItem(String NameOfFileOnServer)
    {
        Constants.setDatabasePathUploads(NameOfFileOnServer.trim());
        CustomAdapterOder_item customAdapterOder_item= new CustomAdapterOder_item();
        //gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));
        Intent intentOder_Item_activity= new Intent(this, Order_Item_Activity.class);
        String option = optionsTxtV.getText().toString().trim();
        intentOder_Item_activity.putExtra(selectedOption,option);
        startActivity(intentOder_Item_activity);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mes_modules) {
//            Intent MyPdfIntent = new Intent(this,Order_Item_Activity.class);
//            startActivities(new Intent[]{MyPdfIntent});

        }  else if (id == R.id.nav_my_options) {
            Intent MyPdfIntent = new Intent(this,OptionsEPSPActivity.class);
            startActivities(new Intent[]{MyPdfIntent});

        } else if (id == R.id.nav_mail_us) {
            Intent intentSendMail =new Intent(getApplicationContext(),SendMailActivity.class);
            startActivity(intentSendMail);

        }else if (id == R.id.nav_shareapplink) {
            shareTextUrl();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // Method to share either text or URL.
    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Partagez Wease");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=wease_one.mub.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }


    public String findFileType(String s){
        String[] output = s.split("_");
        return "jpg";
    }

    public  void putDataInVariable (int position, String fullName, String[]osNameList, int[] osoImages
        , String[]result_anne, String[] result_prix, String[] result_version ){
        String[] output = fullName.split("_");
        osNameList[position]=output[0]+" "+output[1];

        result_anne[position]=output[2];
        result_prix[position]="FREE";
        result_version[position]=output[3];
        Log.i("DD",output[3]);
        if (output[3].trim().toString().equals("V0")){
            osoImages[position] = R.mipmap.ic_empty_folder;
        }else {osoImages[position] = R.mipmap.ic_folderlogo1;}

    }

    public class LoardListOfItems extends AsyncTask< String , Context, Void > {

        private ProgressDialog progressDialog ;
        private Context targetCtx ;
        private boolean needToShow;

        public LoardListOfItems ( Context context ) {
            this.targetCtx = context ;
            this.needToShow = true;
            progressDialog = new ProgressDialog ( targetCtx ) ;
            progressDialog.setCancelable ( false ) ;
            progressDialog.setMessage ( "Retrieving data..." ) ;
            progressDialog.setTitle ( "Please wait" ) ;
            progressDialog.setIndeterminate ( true ) ;
        }

        @ Override
        protected void onPreExecute ( ) {
            progressDialog.show ( ) ;
        }

        @ Override
        protected Void doInBackground ( String ... params ) {
            // Do Your WORK here

            //frebase retreive
            uploadList = new ArrayList<>();

            //getting the database reference
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.getDatabasePathUploads());
            mStorageReference= FirebaseStorage.getInstance().getReference();
            int r=4;
            //retrieving upload data from firebase database
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                       // String myValue = (String) postSnapshot.getValue();
                        String myValueKey= postSnapshot.getKey();
                        //myValue=addTypeToLink(myValueKey,myValue);
                        int t=0;
                        //Upload upload = postSnapshot.getValue(Upload.class);
                       // Upload upload = new Upload(myValueKey,myValue);
                        Upload upload = new Upload(myValueKey);
                        uploadList.add(upload);
                    }

                    String[] uploads = new String[uploadList.size()];
                    result_links =new String[uploadList.size()];
                    osNameList =new String[uploadList.size()];
                    osImages =new int[uploadList.size()];
                    result_anne =new String[uploadList.size()];
                    result_prix =new String[uploadList.size()];
                    result_version =new String[uploadList.size()];
                    for (int i = 0; i < uploads.length; i++) {
                        uploads[i] = uploadList.get(i).getName();
                        result_links[i] = uploadList.get(i).getUrl();
                        putDataInVariable(i, uploads[i],osNameList,osImages,result_anne,result_prix,result_version);
                    }
                    int u=1;
                    gridview.setVisibility(View.VISIBLE);
                    gridview.setAdapter(new CustomAdapterOder_item(Order_Item_Activity.this, osNameList, osImages,result_version,result_anne,result_prix,result_links));

                    //displaying it to list
                    // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                    //listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //
            return null ;
        }

        @ Override
        protected void onPostExecute ( Void result ) {
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss ( ) ;
            }
        }
    }

    public void changeConstrainLytBckgnd (ConstraintLayout contLyt)
    {
        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion <= Build.VERSION_CODES.M)//dded in API level 23/- 6-Marshmallow!
        {

                contLyt.setBackgroundResource(0);
//            Log.i("Bckgnd", "- Marshmallow! " );

        }
        else {
            contLyt.setBackgroundResource(R.drawable.grid_background);
//            Log.i("Bckgnd", "+ Marshmallow! " );

        }
    }
    public void fetchTimelineAsync(int page) {

        // Send the network request to fetch the updated data
        Intent intentOder_Item_activity= new Intent(this, Order_Item_Activity.class);
        String option = optionsTxtV.getText().toString().trim();
        intentOder_Item_activity.putExtra(selectedOption,option);
        swipeContainer.setRefreshing(false);
        startActivity(intentOder_Item_activity);
    }


}
