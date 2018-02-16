package com.mub.wease.wease.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mub.wease.wease.Data.Constants;
import com.mub.wease.wease.Data.Upload;
import com.mub.wease.wease.R;

import java.util.ArrayList;
import java.util.List;
public class ViewUploadsActivity extends AppCompatActivity {

    //the listview
    ListView listView;

    //database reference to get uploads data
    DatabaseReference mDatabaseReference;

    StorageReference mStorageReference;

    //list to store uploads data
    List<Upload> uploadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploads);

        uploadList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);


        //adding a clicklistener on listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the upload
                Upload upload = uploadList.get(i);

                //Opening the upload file in browser using the upload url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);
            }
        });


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

                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadList.get(i).getName();
                    int u=1;
                }

                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String findFileType(String s){
        String result=s;
        String[] output = s.split("_");
        return output[4];
    }
    public String addTypeToLink(String mkey,String mValue)
    {
        String type = findFileType( mkey);
        return mValue+"."+type;
    }

}
