package com.brsatalay.projectbase.library.core.helper;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class HttpClientHelper extends OkHttpClient {
    private Context mContext;

    public HttpClientHelper(Context mContext){
        this.mContext = mContext;
    }
}
