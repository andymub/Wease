package com.mub.wease.wease.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.mub.wease.wease.R;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //variables
    TextView weasetxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weasetxt = findViewById(R.id.txt_wease);
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


}
