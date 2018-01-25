package com.mub.wease.wease.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.mub.wease.wease.Adapter.CustomAdapterOder_item;
import com.mub.wease.wease.R;

public class Oder_Item_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridview;
    public static String[] osNameList = {
            "Module x",
            "Module y",
            "Module z",
            "Module i",
            "Module j",
            "Module u",
            "Module x10",
            "Module x XP",
    };
    public static int[] osImages = {
            R.mipmap.ic_pdf,
            R.mipmap.ic_pdf,
            R.mipmap.ic_pdf,
            R.mipmap.ic_photo,
            R.mipmap.ic_word,
            R.mipmap.ic_photo,
            R.mipmap.ic_word,
            R.mipmap.ic_photo,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder__item_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new CustomAdapterOder_item(this, osNameList, osImages));

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

        } else if (id == R.id.nav_my_backet) {

        } else if (id == R.id.nav_mail_us) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
