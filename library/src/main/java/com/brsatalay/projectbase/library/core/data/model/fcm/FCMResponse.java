package com.brsatalay.projectbase.library.core.data.model.fcm;


import android.util.Log;

import com.brsatalay.projectbase.library.core.helper.fcm.FCMEngine;
import com.brsatalay.projectbase.library.core.util.UtilsGson;

import java.lang.reflect.Type;
import java.util.Random;

/**
 * Created by barisatalay on 3.05.2018.
 */

public class FCMResponse<T extends FCMEngine> {
    private int id;

    private FCMType type;

    private T data;

    public FCMResponse() {
        setId(new Random().nextInt(80 - 65) + 65);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FCMType getType() {
        return type;
    }

    public void setType(FCMType type) {
        this.type = type;
    }

    public void setStringToData(String data) {
        try {//TODO
            this.data = UtilsGson.createGson().fromJson(data, (Type) Class.forName("com.barisatalay.alikocsounds.core.helper.fcm." + type.name()));
        } catch (ClassNotFoundException e) {
            Log.e("FCMResponse", "String to class converter error. Detail: " + e.getMessage());
        }
        Log.i("FCMResponse", "Type: " + type.name());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
