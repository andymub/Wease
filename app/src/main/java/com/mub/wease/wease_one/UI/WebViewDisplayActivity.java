package com.mub.wease.wease_one.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mub.wease.wease_one.Adapter.RecyclerViewAdapter_web;
import com.mub.wease.wease_one.Data.Constants;
import com.mub.wease.wease_one.Data.Upload;
import com.mub.wease.wease_one.R;

import java.util.ArrayList;
import java.util.List;

public class WebViewDisplayActivity extends AppCompatActivity {


    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<Upload> list = new ArrayList<>();

    //count fullname receiver
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_display);
// Assign id to RecyclerView.
        recyclerView = findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(WebViewDisplayActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(WebViewDisplayActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading File-.");

        // Showing progress dialog.
        progressDialog.show();

        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        String name=Constants.getDatabaseFileName();
        int h=0;
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.getDatabasePathUploads()+"/"+Constants.getDatabaseFileName());
        //databaseReference = FirebaseDatabase.getInstance().getReference(Constants.getDatabasePathUploads()+"/Culture_Générale_2010_V1");

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //Upload imageUploadInfo = postSnapshot.getValue(Upload.class);
                    String data= postSnapshot.getValue().toString();
                    String datakey= postSnapshot.getKey();
                    datakey=EraseUnderScareInFullName(datakey);
                    if (i<1){
                        //Todo do not delete this .... /8\
                    }
                    else
                    {
                        datakey=" ";
                    }
                    //Upload imageUploadInfo = postSnapshot.getValue(Upload.class);
                    Upload imageUploadInfo = new Upload(datakey,data);

                    list.add(imageUploadInfo);
                    i++;
                }
                int u=5;
                adapter = new RecyclerViewAdapter_web(getApplicationContext(), list);

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });

    }

    private String EraseUnderScareInFullName(String datakey) {
        String[] name= datakey.split("_");
        return  name[0]+" "+name[1]+" "+name[3]+"/"+name[2];
    }
}