package com.brsatalay.projectbase.library.core.mvp;

import android.content.Context;

import com.brsatalay.projectbase.library.core.GlobalBus;
import com.brsatalay.projectbase.library.core.network.RestApiService;

public class BaseRepository<T> {
    public final String TAG = this.getClass().getSimpleName();
    private RestApiService<T> restApiService;

    public BaseRepository(RestApiService restApiService) {
        this.restApiService = restApiService;
        if (this.restApiService != null)
            this.restApiService.setListener((exception) -> GlobalBus.getBus().post(exception));
    }

    public T getRestApiService() {
        return restApiService.getRestInterface();
    }

    protected void cancelAll(){
        restApiService.cancelAll();
    }

    public void setRestApiService(RestApiService restApiService) {
        this.restApiService = restApiService;
    }

    public Context getContext(){
        return restApiService.getContext();
    }
}
