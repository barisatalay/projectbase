package com.brsatalay.projectbase.library.core.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brsatalay.projectbase.library.BuildConfig;
import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.data.model.LoadingType;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsLoading {
    private AlertDialog dialog;
//    private AlertDialog dialog;
    private boolean isActive;
    private TextView message;
    private AVLoadingIndicatorView indicator;
    private String messageText;
    private Activity activeScreen;
    /**
     * Bu tanım eğer sıfıra eşitse ekranda loading gözükmeyecek.
     * */
    private int visibleCounter;

    public UtilsLoading(Activity mActivity){
        this.activeScreen = mActivity;
        prepareDialog();
    }

    private void prepareDialog() {
        counterIncrement();
        if(activeScreen == null)
            new Throwable("Loading activity not found!");
        LayoutInflater inflater = activeScreen.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_loading, null);
        AlertDialog.Builder builder = UtilsDialog.createAlertDialog(activeScreen)
                .setCancelable(BuildConfig.DEBUG);
        builder.setView(dialogView);

        message = dialogView.findViewById(R.id.loading_text);
        indicator = dialogView.findViewById(R.id.loading_indicator);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public UtilsLoading show(Activity mActivity, String messageText, LoadingType loadingType){
        activeScreen = mActivity;

        this.messageText = messageText;

        if(dialog == null || !dialog.isShowing())
            prepareScreen(loadingType);
        else
            dialog.show();
        return this;
    }

    public UtilsLoading show(Activity mActivity, int resourceId, LoadingType loadingType){
        activeScreen = mActivity;
        messageText = activeScreen.getString(resourceId);

        prepareScreen(loadingType);
        return this;
    }

    /*
    * Default false
    * */
    public UtilsLoading setCancelable(boolean value){
        if(dialog != null)
            dialog.setCancelable(value);

        return this;
    }

    private void prepareScreen(LoadingType loadingType){
        if (message == null) return;
        isActive = true;

        if (message != null)
            message.setText(messageText);

        if (indicator != null)
            indicator.setIndicator(loadingType.toString());

        if(activeScreen.isDestroyed() || activeScreen.isFinishing()){
            return;
        }
        if (dialog != null)
            dialog.show();
    }

    public void hide(){
        if (counterDecrement())
            if (dialog != null)
                dialog.dismiss();
    }
    /**
     * Dönen değer animasyonun gizlenmesine karar verir
     * */
    private boolean counterDecrement() {
        visibleCounter--;
        return visibleCounter < 1;
    }

    private void counterIncrement() {
        visibleCounter++;
    }

    public void dispose(){
        clear();
    }

    private void clear() {
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

        if(message != null)
            message = null;

        isActive = false;
        activeScreen = null;
    }

    public boolean isActive() {
        return isActive;
    }

    public void changeText(String messageText) {
        if(isActive)
            if(message != null)
                message.setText(messageText);
    }

    public void changeText(int resourceId) {
        if(isActive)
            if(message != null)
                message.setText(activeScreen.getString(resourceId));
    }
}
