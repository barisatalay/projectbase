package com.brsatalay.projectbase.library.core.helper.fcm;

import android.content.Context;
import android.content.SharedPreferences;

import com.brsatalay.projectbase.library.core.data.model.fcm.FCMData;


/**
 * Created by barisatalay on 3.05.2018.
 * {"description":"", "version":""} gibi
 */

public class FCMVersion extends FCMData implements FCMEngine{
    private String version;

    public FCMVersion(Context mContext) {
        super(mContext);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void run() {
        SharedPreferences.Editor edit = getPrefManager().edit();
        edit.putString("Version_Build", version);
        edit.putString("Version_Description", getDescription());
        edit.apply();

//        UtilsGeneral.checkNewVersion(mContext);TODO Geliştirmesi yapılınca açılacak.
    }
}
