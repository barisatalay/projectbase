package com.brsatalay.projectbase.library.core.helper.fcm;

import android.content.Context;

import com.brsatalay.projectbase.library.core.data.model.fcm.FCMData;
import com.brsatalay.projectbase.library.core.util.UtilsNotification;

import static java.security.AccessController.getContext;


/**
 * Created by barisatalay on 3.05.2018.
 * {"description":"", "title":""} gibi
 */

public class FCMNotification extends FCMData {
    private String title;
    private String icon;
    private String sound;
    private String tag;
    private String color;
    private int id;

    public FCMNotification(Context mContext) {
        super(mContext);
        id = 9998;
    }

    @Override
    public void run() {
        if (getContext() != null)
            UtilsNotification.sendNotification(getContext(), "FCMNotification", getId(), getTitle(), getDescription(), null );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public int getId() {
        return id;
    }
}
