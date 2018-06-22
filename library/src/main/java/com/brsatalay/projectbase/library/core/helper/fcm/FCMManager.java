package com.brsatalay.projectbase.library.core.helper.fcm;

import android.content.Context;


import com.brsatalay.projectbase.library.core.data.model.fcm.FCMResponse;
import com.brsatalay.projectbase.library.core.data.model.fcm.FCMType;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by barisatalay on 3.05.2018.
 */

public class FCMManager {
    private Context mContext;
    private FCMResponse data;

    public FCMManager(Context mContext, RemoteMessage remoteMessage){
        this.mContext = mContext;

        applyFCMParser(remoteMessage);
    }

    private void applyFCMParser(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;

        data = new FCMResponse();

        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0)
            applyData(remoteMessage.getData());

        if (remoteMessage.getNotification() != null)
            applyNotification(remoteMessage.getNotification());
    }

    private void applyData(Map<String, String> remoteData) {
        data.setType(FCMType.valueOf(remoteData.get("type")));
        data.setStringToData(remoteData.get("data"));
        if (data.getData() != null)
            data.getData().setContext(mContext);
    }

    private void applyNotification(RemoteMessage.Notification remoteMessageNotify) {
        data.setType(FCMType.FCMNotification);
        FCMNotification notification = new FCMNotification(mContext);
        notification.setDescription(remoteMessageNotify.getBody());
        notification.setTitle(remoteMessageNotify.getTitle());

        notification.setIcon(remoteMessageNotify.getIcon());
        notification.setSound(remoteMessageNotify.getSound());
        notification.setTag(remoteMessageNotify.getTag());
        notification.setColor(remoteMessageNotify.getColor());
        this.data.setData(notification);
    }

    public void run() {
        if (data != null && data.getData() != null)
            data.getData().run();
    }
}
