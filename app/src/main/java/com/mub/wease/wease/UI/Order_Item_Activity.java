package com.mub.wease.wease.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mub.wease.wease.Adapter.CustomAdapterOder_item;
import com.mub.wease.wease.Data.Constants;
import com.mub.wease.wease.Data.GridState;
import com.mub.wease.wease.Data.Upload;
import com.mub.wease.wease.R;

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
            "Module i",
    };
    public  int[] osImages = {
            R.mipmap.ic_pdf,
            R.mipmap.ic_pdf,
            R.mipmap.ic_pdf,
            R.mipmap.ic_photo,
            R.mipmap.ic_word,
            R.mipmap.ic_photo,
            R.mipmap.ic_word,
            R.mipmap.ic_word,
            R.mipmap.ic_photo,
            R.mipmap.ic_word,
            R.mipmap.ic_photo,};
    public  String [] result_anne={"2016","2016","2014","2016","2016","2014","2016","2016","2014","2016","2016"};
    public  String [] result_prix={"FREE","1$","FREE","1$","1$","FREE","1$","1$","FREE","FREE","1$","1$",};
    public  String [] result_version={"V1","V3","V1","V2","V3","V1","V2","V3","V1","V1","V4","V2"};
    public  String [] result_links;
    public final static String selectedOption="selectedOption";
    public ConstraintLayout constraintOrderItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder__item_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get intent here
        Intent intentSelectedOption =getIntent();
        final String optionSelected = intentSelectedOption.getStringExtra(selectedOption);
        TextView optionsTxtV=findViewById(R.id.os_texts_option_order_activity);
        constraintOrderItem = findViewById(R.id.constraintOrderItem);
        changeConstrainLytBckgnd(constraintOrderItem);
        new LoardListOfItems(Order_Item_Activity.this).execute();
        int y=5;
        //        //frebase retreive
//        uploadList = new ArrayList<>();
//
//        //getting the database reference
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
//        mStorageReference= FirebaseStorage.getInstance().getReference();
//        int r=4;
//        //retrieving upload data from firebase database
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    String myValue = (String) postSnapshot.getValue();
//                    String myValueKey= postSnapshot.getKey();
//                    myValue=addTypeToLink(myValueKey,myValue);
//                    int t=0;
//                    //Upload upload = postSnapshot.getValue(Upload.class);
//                    Upload upload = new Upload(myValueKey,myValue);
//                    uploadList.add(upload);
//                }
//
//                String[] uploads = new String[uploadList.size()];
//                result_links =new String[uploadList.size()];
//                osNameList =new String[uploadList.size()];
//                osImages =new int[uploadList.size()];
//                result_anne =new String[uploadList.size()];
//                result_prix =new String[uploadList.size()];
//                result_version =new String[uploadList.size()];
//                for (int i = 0; i < uploads.length; i++) {
//                    uploads[i] = uploadList.get(i).getName();
//                    result_links[i] = uploadList.get(i).getUrl();
//                    int u=1;
//                    putDataInVariable(i, uploads[i],osNameList,osImages,result_anne,result_prix,result_version);
//                }
//
//                //displaying it to list
//               // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
//                //listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        //

        //set optiontxt
        optionsTxtV.setText(optionSelected);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Vous Avez X Module(s) dans votre panier d'achat-Prix de x $ ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //GridView
        gridview = (GridView) findViewById(R.id.customgridforItems);
        // gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridview.setVisibility(View.INVISIBLE);
        gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages,result_version,result_anne,result_prix,result_links));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.oder__item_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            CustomAdapterOder_item customAdapterOder_item= new CustomAdapterOder_item();
            List<String> getSelectedItemch = customAdapterOder_item.getSelectedItem();
            int y=0;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mes_modules) {
            Intent MyPdfIntent = new Intent(this,MyPDFListActivity.class);
            startActivities(new Intent[]{MyPdfIntent});

        } else if (id == R.id.nav_special_shopping) {

        }  else if (id == R.id.nav_share) {
            Intent intent= new Intent(getApplicationContext(),ViewUploadsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_my_backet) {

        } else if (id == R.id.nav_mail_us) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public String findFileType(String s){
        String result=s;
        String[] output = s.split("_");
        return "jpg";
    }

    public  void putDataInVariable (int position, String fullName, String[]osNameList, int[] osoImages
        , String[]result_anne, String[] result_prix, String[] result_version ){
        String[] output = fullName.split("_");
        osNameList[position]=output[0]+" "+output[1];
        osoImages[position] = R.drawable.folderlogo;
        result_anne[position]=output[2];
        result_prix[position]="FREE";
        result_version[position]=output[3];

    }
    public String addTypeToLink(String mkey,String mValue)
    {
        String type = findFileType( mkey);
        return mValue+"."+type;
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
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
            mStorageReference= FirebaseStorage.getInstance().getReference();
            int r=4;
            //retrieving upload data from firebase database
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String myValue = (String) postSnapshot.getValue();
                        String myValueKey= postSnapshot.getKey();
                        myValue=addTypeToLink(myValueKey,myValue);
                        int t=0;
                        //Upload upload = postSnapshot.getValue(Upload.class);
                        Upload upload = new Upload(myValueKey,myValue);
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
        if (currentVersion < Build.VERSION_CODES.M)//dded in API level 23/- 6-Marshmallow!
        {

                contLyt.setBackgroundResource(0);
            Log.i("Bckgnd", "- Marshmallow! " );

        }
        else {
            contLyt.setBackgroundResource(R.drawable.grid_background);
            Log.i("Bckgnd", "+ Marshmallow! " );

        }
    }

}
