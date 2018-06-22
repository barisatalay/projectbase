package com.brsatalay.projectbase.library.core.network;

import android.util.Log;

import com.brsatalay.projectbase.library.core.data.constant.ErrorConstant;
import com.brsatalay.projectbase.library.core.data.model.EventType;
import com.brsatalay.projectbase.library.core.data.model.event.BaseEvent;
import com.brsatalay.projectbase.library.core.data.model.event.DialogEvent;
import com.brsatalay.projectbase.library.core.data.model.event.Http503Event;

import io.reactivex.observers.DisposableSingleObserver;

public abstract class BaseObserver<T> extends DisposableSingleObserver<T> {
    private final boolean showErrorInfo;
    private String TAG = this.getClass().getSimpleName();

    public BaseObserver() {
        this.showErrorInfo = true;
    }

    public BaseObserver(boolean showErrorInfo) {
        this.showErrorInfo = showErrorInfo;
    }

    @Override
    public void onSuccess(T response) {
        if(response == null){
            onError(new DialogEvent(ErrorConstant.JsonParse));
        }else{
            onResponse(response);
        }
    }
    @Override
    public void onError(Throwable e) {
        Log.e(TAG, e.getMessage());

        if (e instanceof DialogEvent) {
            DialogEvent event = (DialogEvent) e;
            if (event.getLocalizedMessage().equalsIgnoreCase(ErrorConstant.DBConnectErrorText)){
                event = (DialogEvent) new DialogEvent(ErrorConstant.DBConnectError, e).setType(EventType.Warning);
            }
            onResponseError(event);
            onAlert(event);
        }else {
            BaseEvent e1 = prepareSpecificDialog(e);
            onResponseError(e1);
            onAlert(e1);
        }
    }

    private BaseEvent prepareSpecificDialog(Throwable e) {
        BaseEvent dialogEvent;
        if (e.getMessage().equals(ErrorConstant.HTTP503)){
            dialogEvent = new Http503Event(e.getMessage(), e);
            dialogEvent.setType(EventType.Warning);
        }else{
            dialogEvent = new DialogEvent(e.getMessage(), e);
            dialogEvent.setType(EventType.Warning);
        }
        return dialogEvent;
    }

    private void onAlert(BaseEvent event){
        if (showErrorInfo)
            if (RestApiService.getListener() != null) {
                RestApiService.getListener().onAlert(event);
            }
    }

    public abstract void onResponse(T response);
    public abstract void onResponseError(Throwable e);
}
