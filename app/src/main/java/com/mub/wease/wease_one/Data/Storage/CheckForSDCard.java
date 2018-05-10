package com.mub.wease.wease_one.Data.Storage;

import android.os.Environment;

/**
 * Created by Andymub on 19/02/2018.
 */

public class CheckForSDCard {
    //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(

                    Environment.MEDIA_MOUNTED)) {
                return false;
                /*todo get return  true if the sdCard existe *
                ill just put a false to get data stored in the device/
                 */
            }
            return false;
        }
}