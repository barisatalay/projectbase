package com.brsatalay.projectbase.library.core.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by barisatalay on 20.03.2018.
 */

public class UtilsNetwork {
    public static boolean networkControl(Context ctx) {
        ConnectivityManager conMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();

        return i != null && i.isConnected() && i.isAvailable();
    }
}
