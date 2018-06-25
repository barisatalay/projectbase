package com.brsatalay.projectbase.library.core.network;

import android.content.Context;

import com.brsatalay.projectbase.library.core.data.interfaces.HttpListener;
import com.brsatalay.projectbase.library.core.util.UtilsRetrofit;

import io.reactivex.annotations.Nullable;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

/**
 * Created by barisatalay on 20.03.2018.
 */

public class RestApiService<T> {
    //region Private
    private Dispatcher dispatcher;
    private Retrofit restAdapter;
    private Context mContext;
    private String apiBaseUrl;
    private T restInterface;
    private Interceptor[] interceptors;
    private static HttpListener listener;
    //endregion

    public RestApiService(Context mContext, String apiBaseUrl){
        this.mContext = mContext;
        this.apiBaseUrl = apiBaseUrl;
    }

    public static HttpListener getListener() {
        return listener;
    }

    public void setListener(HttpListener listener) {
        RestApiService.listener = listener;
    }

    public void prepareConfig(final Class<T> service, @Nullable Interceptor... args) {
        applyInterceptors(args);

        dispatcher = createNewDispatcher();

        restAdapter = createRestAdapter();

        restInterface = restAdapter.create(service);
    }

    private Retrofit createRestAdapter() {
        return UtilsRetrofit.createRetrofitAdapter(mContext, dispatcher, apiBaseUrl, interceptors);
    }

    private void applyInterceptors(@Nullable Interceptor... args){
        interceptors = args;
    }

    private Dispatcher createNewDispatcher() {
        return new Dispatcher();
    }

    public T getRestInterface() {
        return restInterface;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void cancelAll() {
        getDispatcher().cancelAll();
    }

    public Context getContext() {
        return mContext;
    }
}
