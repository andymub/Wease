package com.mub.wease.wease.Background_HeavyWork;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Andymub on 16/02/2018.
 */

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

            return null ;
        }

        @ Override
        protected void onPostExecute ( Void result ) {
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss ( ) ;
            }
        }
    }