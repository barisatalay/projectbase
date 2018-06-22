package com.brsatalay.projectbase.library.core.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;


import com.brsatalay.projectbase.library.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by barisatalay on 3.05.2018.
 */

public class UtilsNotification {
    public static void sendNotification(Context mContext, String channelId, int notiId, String bigTitle, String title, Intent notificationIntent){
        NotificationCompat.Builder mBuilder = null;

        Intent intent = notificationIntent;

        if(intent == null){
//            intent = new Intent(mContext, MainActivity.class);
//            intent.putExtra("FcmType", response.getType());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(mContext, channelId==null?"":channelId)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_bell_ring)
                .setContentText(title)
                .setLights(Color.parseColor("#6cb1b8"),300, 100)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(contentIntent);

        if(TextUtils.isEmpty(bigTitle))
            mBuilder.setContentTitle(bigTitle);

        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        mNotificationManager.notify(notiId, mBuilder.build());
    }
}
