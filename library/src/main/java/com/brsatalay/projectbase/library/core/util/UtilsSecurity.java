package com.brsatalay.projectbase.library.core.util;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by barisatalay on 3.05.2018.
 */

public class UtilsSecurity {
    public static String getDeviceUnique(Context mContext){
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
