package com.brsatalay.projectbase.library.core.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Barış ATALAY on 4.12.2017.
 */

public class UtilsJson {

    public static void setParam(JSONObject source, String key, JSONArray value) {
        try {
            source.put(key,value == null?JSONObject.NULL:value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setParam(JSONObject source, String key, JSONObject value) {
        try {
            source.put(key,value == null?JSONObject.NULL:value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setParam(JSONObject source, String key, Object value) {
        try {
            source.put(key,value == null?JSONObject.NULL:value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setParam(JSONObject source, String key, String value) {
        try {
            source.put(key,value == null?JSONObject.NULL:value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setParam(JSONObject source, String key, int value) {
        try {
            source.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
