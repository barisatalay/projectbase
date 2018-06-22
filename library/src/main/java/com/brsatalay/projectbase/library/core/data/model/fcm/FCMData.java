package com.brsatalay.projectbase.library.core.data.model.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.brsatalay.projectbase.library.core.helper.fcm.FCMEngine;

/**
 * Created by barisatalay on 3.05.2018.
 * {"description":""}
 */

public class FCMData implements FCMEngine {
    private String description;

    public Context mContext;

    public FCMData(Context mContext) {
        this.mContext = mContext;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void run() {}

    @Override
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    protected SharedPreferences getPrefManager(){
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

}