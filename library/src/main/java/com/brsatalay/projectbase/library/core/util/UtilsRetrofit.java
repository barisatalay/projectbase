package com.brsatalay.projectbase.library.core.util;

import android.content.Context;

import com.brsatalay.projectbase.library.core.helper.HttpClientHelper;
import com.brsatalay.projectbase.library.core.network.interceptor.NetworkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsRetrofit {
    public static OkHttpClient createClient(Context mContext, Dispatcher mDispatcher, Interceptor... args){
        OkHttpClient.Builder builder = new HttpClientHelper(mContext).newBuilder()
                .addInterceptor(new NetworkInterceptor(mContext));
        if (args != null)
            for(Interceptor interceptor : args){
                builder.addInterceptor(interceptor);
            }
        OkHttpClient result = builder
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .dispatcher(mDispatcher)
                .build();

        return result;
    }

    public static Retrofit createRetrofitAdapter(Context mContext, Dispatcher mDispatcher, String baseUrl, Interceptor... args){
        return new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(UtilsGson.createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(UtilsRetrofit.createClient(mContext, mDispatcher, args))
                .build();

    }
}
