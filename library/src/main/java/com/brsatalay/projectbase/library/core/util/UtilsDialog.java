package com.brsatalay.projectbase.library.core.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.brsatalay.projectbase.library.BuildConfig;
import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.data.model.event.BaseEvent;
import com.brsatalay.projectbase.library.ui.activity.DialogProjectActivity;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsDialog {
    public static AlertDialog.Builder createAlertDialog(Activity mActivity){
        return new AlertDialog.Builder(mActivity);//, R.style.AppTheme_AlertDialog);
//        return new AlertDialog.Builder(mActivity);//, R.style.AppTheme_AlertDialog);
    }
//
//    public static AlertDialog.Builder createAlertDialog(Activity mActivity, @StyleRes int themeResId){
//        if (themeResId == -1)
//            return new AlertDialog.Builder(mActivity);
//        else
//            return new AlertDialog.Builder(mActivity, R.style.AppTheme_AlertDialog);
//    }

    public static AlertDialog.Builder confirmationDialog(Activity mActivity, String title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener){
        return createAlertDialog(mActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(mActivity.getString(R.string.yes), positiveListener)
                .setNegativeButton(mActivity.getString(R.string.no), negativeListener);
    }

    public static void showPermanentMessage(Activity mActivity, int resourceTitleId, int resourceDescriptionId){
        showPermanentMessage(mActivity, mActivity.getString(resourceTitleId), mActivity.getString(resourceDescriptionId), false);
    }

    public static void showPermanentMessage(Activity mActivity, String title, String description){
        showPermanentMessage(mActivity, title, description, false);
    }

    public static void showPermanentMessage(Activity mActivity, String title, String description, boolean isHtml){
        mActivity.runOnUiThread(() -> {
            mActivity.startActivity(DialogProjectActivity.newInstance(mActivity, title, description, mActivity.getString(R.string.okey), isHtml));
            mActivity.overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        });
    }

    public static String showPermanentMessage(Activity mActivity, BaseEvent event){
        //Tip belirlilmemiş ise gösterim yapmayacağız.
        if (event.getType() == null) return "";

        //region ..:: Mesaj Başlığı ::..
        StringBuilder titleBuilder = new StringBuilder();
        if (event.getTitleResourceId() != -1){
            titleBuilder.append(mActivity.getString(event.getTitleResourceId()));
        }else{
            titleBuilder.append(event.getType().name());
        }
        //endregion

        //region ..:: Mesaj içeriği ::..
        StringBuilder debugInfo = new StringBuilder();
        if (BuildConfig.DEBUG ){
            if (event.getId() > -1)
                debugInfo.append("[")
                        .append(event.getId())
                        .append("] ");

            if (!event.getCode().isEmpty())
                debugInfo
                        .append(event.getCode())
                        .append("\n\n");
        }

        if (event.getMessage() != null)
            debugInfo.append(event.getMessage());
        else
            debugInfo.append(mActivity.getString(event.getMessageResourceId()));

        if (BuildConfig.DEBUG && !TextUtils.isEmpty(event.getDescription()))
            debugInfo.append("\n\n")
                    .append("[***Have Detail***]")
                    .append("\n\n")
                    .append(event.getDescription());
        //endregion

        showPermanentMessage(mActivity, event.getType().name(), debugInfo.toString());

        return event.getType().name() + " : " + debugInfo.toString();
    }
}
