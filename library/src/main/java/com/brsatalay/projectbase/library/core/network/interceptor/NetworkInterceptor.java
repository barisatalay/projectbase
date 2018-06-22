package com.brsatalay.projectbase.library.core.network.interceptor;

import android.content.Context;
import android.util.Log;


import com.brsatalay.projectbase.library.core.data.constant.ErrorConstant;
import com.brsatalay.projectbase.library.core.util.UtilsNetwork;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by barisatalay on 2.11.2017.
 */

public class NetworkInterceptor implements Interceptor {
    private String TAG = this.getClass().getSimpleName();

    public Context mContext;

    public NetworkInterceptor(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!UtilsNetwork.networkControl(mContext)){
            Log.d(TAG, ErrorConstant.NullNetwork);
            throw new IOException(ErrorConstant.NullNetwork);
        }

        return chain.proceed(request);
    }
}
