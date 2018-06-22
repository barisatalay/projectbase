package com.brsatalay.projectbase.library.core.network;

import android.content.Context;

import com.brsatalay.projectbase.library.core.data.interfaces.HttpListener;
import com.brsatalay.projectbase.library.core.util.UtilsRetrofit;

import okhttp3.Dispatcher;
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
    private static HttpListener listener;
    /**
     * Eğer uygulama hata ayıklama modunda çalıştırılıyorsa burası true'dir
     * */
    private static boolean debugMode = false;
    //endregion

    public RestApiService(Context mContext, String apiBaseUrl, Class<T> restInterface){
        this.mContext = mContext;
        this.apiBaseUrl = apiBaseUrl;
        createRestConfig(restInterface);
    }

    public static HttpListener getListener() {
        return listener;
    }

    public void setListener(HttpListener listener) {
        RestApiService.listener = listener;
    }

    private void createRestConfig(final Class<T> service) {
        dispatcher = createNewDispatcher();

        restAdapter = createRestAdapter();

        restInterface = restAdapter.create(service);
    }

    private Retrofit createRestAdapter() {
        return UtilsRetrofit.createRetrofitAdapter(mContext, dispatcher, apiBaseUrl);
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