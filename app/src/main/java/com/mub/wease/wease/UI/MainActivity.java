package com.mub.wease.wease.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

import com.mub.wease.wease.R;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //variables
    TextView weasetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        int splash_time_out = 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            Intent homeIntent = new Intent(MainActivity.this,LoginActivity_.class);
            startActivity(homeIntent);
            finish();
            }
        }, splash_time_out);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Offerings Regular.otf");
        weasetxt = findViewById(R.id.txt_wease);
        weasetxt.setTypeface(type);
        weasetxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_wease :
                Intent intentMyPDFListActivity=new Intent(this,LoginActivity_.class);
                MainActivity.this.startActivity(intentMyPDFListActivity);

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intentLogin =new Intent(getApplicationContext(),LoginActivity_.class);
        startActivity(intentLogin);
    }

}
