package com.mub.wease.wease.Connexion;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Andymub on 16/02/2018.
 */

public class CheckConnection {


    public static boolean isConnectedToInternet(Context context) {


        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (((connectivityManager != null ? connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) : null) != null &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!= null &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN)!= null &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_VPN).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH)!= null &&
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH).getState() == NetworkInfo.State.CONNECTED))

        {
            Toast.makeText(context,"Connected",Toast.LENGTH_LONG);
            return true;
        } else {

            Toast.makeText(context,"Vérifier votre connection",Toast.LENGTH_LONG);
            return false;
        }
    }
}
