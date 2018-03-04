package com.mub.wease.wease.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mub.wease.wease.Data.Storage.CheckForSDCard;
import com.mub.wease.wease.Data.Utils;
import com.mub.wease.wease.R;

import java.io.File;
import java.util.ArrayList;

public class MyPDFListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pdflist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnPdfFolder =findViewById(R.id.btnpdffolder);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent Oder_Itent;
//                Oder_Itent = new Intent(this, Order_Item_Activity.class);

            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.add_new_pdf,Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        btnPdfFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDownloadedFolder();

//                Intent intent=new Intent(MyPDFListActivity.this,WebViewDisplayActivity.class);
//                startActivity(intent);

            }
        });
    }
    //Open downloaded folder
    private void openDownloadedFolder() {
        //First check if SD Card is present or not
        if (new CheckForSDCard().isSDCardPresent()) {

            //Get Download Directory File
            File apkStorage = new File(
                    Environment.getExternalStorageDirectory() + "/"
                            + Utils.downloadDirectory);

            //If file is not present then display Toast
            if (!apkStorage.exists()) {
                Toast.makeText(this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();
                apkStorage.mkdir();
            }
            else {

                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/
                GetFolders(apkStorage);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/" + Utils.downloadDirectory);
                intent.setDataAndType(uri, "file/*");
                startActivity(Intent.createChooser(intent, "Open Download Folder"));
            }

        } else
            Toast.makeText(this, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

    }

    public void Search_Dir(File dir) {
        String pdfPattern = ".pdf";

        File FileList[] = dir.listFiles();

        if (FileList != null) {
            for (int i = 0; i < FileList.length; i++) {

                if (FileList[i].isDirectory()) {
                    Search_Dir(FileList[i]);
                } else {
                    if (FileList[i].getName().endsWith(pdfPattern)){
                        //here you have that file.
                        Toast.makeText(this,FileList[i].getName(),Toast.LENGTH_SHORT);

                    }
                }
            }
        }
    }
    public ArrayList<String> GetFolders(File f) {

        ArrayList<String> MyFiles = new ArrayList<>();
       // f.mkdirs();
        File[] files = f.listFiles();
        int y=84;
        for (int i=0; i<files.length; i++)
        {  MyFiles.add(files[i].getName());
        Toast.makeText(this,files[i].getName(),Toast.LENGTH_SHORT);
        }
        return MyFiles;
    }

}
