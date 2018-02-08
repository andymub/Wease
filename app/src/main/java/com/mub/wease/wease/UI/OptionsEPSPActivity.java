package com.mub.wease.wease.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mub.wease.wease.Adapter.CustomAdapterOptions;
import com.mub.wease.wease.R;

public class OptionsEPSPActivity extends AppCompatActivity {

    GridView gridview;


//    public static String[] osNameList_options = {
//            R.string.Math_physique,
//            String.valueOf(R.string.bio_chimie),
//            String.valueOf(R.string.Litteraire),
//            String.valueOf(R.string.commercial),
//            String.valueOf(R.string.pedagogie),
//    };
    public static String[] osNameList_options = {
            "Math-Physique",
            "Bio-Chimie",

           " Littéraire",

            "Commerciale",

            "Pedagogie",

    };
    public static int[] osImages_options = {
            R.mipmap.ic_math_phys,
            R.mipmap.ic_bio_chimie,
            R.mipmap.ic_litteraire,
            R.mipmap.ic_commercial,
            R.mipmap.ic_pedagogie,}
           ;
    public String[] res;
    public int introwView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_epsp);
        gridview = (GridView) findViewById(R.id.customgrid_options_rdc);
        gridview.setAdapter(new CustomAdapterOptions(this, osNameList_options, osImages_options));
       // rowView=gridview.setAdapter(new CustomAdapterOptions(this, osNameList_options, osImages_options).getView());
       // rowView = findViewById(R.layout.sample_gridlayout_options);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               introwView=introwView;
               Log.i("DDDD",""+introwView);

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


}
