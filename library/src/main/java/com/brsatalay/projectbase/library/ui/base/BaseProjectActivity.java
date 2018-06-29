package com.brsatalay.projectbase.library.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akexorcist.localizationactivity.LocalizationDelegate;
import com.akexorcist.localizationactivity.OnLocaleChangedListener;
import com.brsatalay.projectbase.library.R;
import com.brsatalay.projectbase.library.core.GlobalBus;
import com.brsatalay.projectbase.library.core.data.model.LoadingType;
import com.brsatalay.projectbase.library.core.data.model.event.BaseEvent;
import com.brsatalay.projectbase.library.core.data.model.event.DialogEvent;
import com.brsatalay.projectbase.library.core.mvp.BaseView;
import com.brsatalay.projectbase.library.core.util.UtilsDialog;
import com.brsatalay.projectbase.library.core.util.UtilsJson;
import com.brsatalay.projectbase.library.core.util.UtilsKeyboard;
import com.brsatalay.projectbase.library.core.util.UtilsLoading;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.Locale;

public abstract class BaseProjectActivity extends AppCompatActivity implements BaseView, OnLocaleChangedListener {
    private LocalizationDelegate localizationDelegate = new LocalizationDelegate(this);
    public final String TAG = this.getClass().getSimpleName();
    private UtilsLoading utilsLoading;
    private boolean isUiRendered = false;
    private boolean isDrawerActivity = false;
    /**
     * Application Language Code
     * */
    private String activeLangCode;
    /**
     * Bu sayı sıfırlanmadıkça ekranda ki loading gözükmeye devam edecektir.
     * */
    private int loadingCounter;

    @Override
    protected void onCreate(Bundle bundle){
        if (isEnableMultiLanguage()) {
            localizationDelegate.addOnLocaleChengedListener(this);
            localizationDelegate.onCreate(bundle);
        }

        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
        loadingCounter = 0;

        onRunBeforeonCreate();
        super.onCreate(bundle);

        if (isEnableMultiLanguage()) {
            activeLangCode = catchLanguage();

            setLanguage(activeLangCode);
        }
        if (!isDrawerActivity) {
            setContentView(getContentView());
            initUi();
            initPresenter();
            applyClickListener();
        }

        prepareFabricCrashlytics();
    }

    /**
     * Fabric için Aktif Dilin json objesini hazırlar
     * */
    public String prepareLanguageInfos() {
        try{
            JSONObject languageInfo = new JSONObject();

            UtilsJson.setParam(languageInfo,"Language", Locale.getDefault().getLanguage());
            UtilsJson.setParam(languageInfo,"ISO3Language",Locale.getDefault().getISO3Language());
            UtilsJson.setParam(languageInfo,"Country",Locale.getDefault().getCountry());
            UtilsJson.setParam(languageInfo,"ISO3Country",Locale.getDefault().getISO3Country());
            UtilsJson.setParam(languageInfo,"DisplayCountry",Locale.getDefault().getDisplayCountry());
            UtilsJson.setParam(languageInfo,"DisplayName",Locale.getDefault().getDisplayName());
            UtilsJson.setParam(languageInfo,"toString",Locale.getDefault().toString());
            UtilsJson.setParam(languageInfo,"DisplayLanguage",Locale.getDefault().getDisplayLanguage());

            return languageInfo.toString();
        }catch (Exception e){
            return "";
        }
    }

    public UtilsLoading getUtilsLoading() {
        if(utilsLoading == null)
            utilsLoading = new UtilsLoading(getActivity());
        return utilsLoading;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isEnableMultiLanguage())
            localizationDelegate.onResume();
        if (!GlobalBus.getBus().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }

        if (!isUiRendered){
            renderUi();
        }
    }

    private void renderUi() {
        onCreateView();
        new Thread(this::onAsynchronousLoad).start();

        isUiRendered = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (GlobalBus.getBus().isRegistered(this)) {
            GlobalBus.getBus().unregister(this);
        }
    }

    @Override
    public void onDestroy(){
        if(utilsLoading != null)
            this.utilsLoading.dispose();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    @Override
    public void closeActivity(){
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    public void finishNoTransition() {
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.activity_slide_from_right, R.anim.activity_slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.activity_slide_from_left, R.anim.activity_slide_to_right);
    }

    protected Activity getActivity(){
        return this;
    }
    /**
     * Run after onPause
     * */
    public abstract void onCreateView();
    public abstract @LayoutRes
    int getContentView();
    public abstract void initUi();
    public abstract void initPresenter();
    /** onCreateCustom işlemlerini bitirince işlemlerin devam edeceği farklı bir thread*/
    public abstract void onAsynchronousLoad();
    public abstract void prepareFabricCrashlytics();
    public abstract void onRunBeforeonCreate();
    /** When returned null or empty it's mean devices default language */
    public abstract String getLanguageCode();
    public abstract boolean isEnableMultiLanguage();

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());
    }

    @Override
    public void showToast(int resourceId) {
        showToast(getString(resourceId));
    }
    /**
     * Ekran bazlı loading animasyonunu gösterir
     * */
    @Override
    public void showLoading() {
        if (loadingCounter == 0)
            getActivity().runOnUiThread(() -> getUtilsLoading().show(getActivity(), "", LoadingType.BallPulseIndicator));
        loadingCounter++;
        Log.i(TAG, "Loading Counter: " + loadingCounter);
    }
    /**
     * Ekran bazlı loading animasyonunu gösterir
     * */
    @Override
    public void showLoading(@StringRes int stringResId) {
        if (loadingCounter == 0)
            getActivity().runOnUiThread(() -> getUtilsLoading().show(getActivity(), getString(stringResId), LoadingType.BallPulseIndicator));
        loadingCounter++;
        Log.i(TAG, "Loading Counter: " + loadingCounter);
    }
    /**
     *
     * */
    @Override
    public void showLoadingWithDelay(Runnable runnable){
        showLoading();
        if (runnable != null)
            runnable.run();
        getActivity().runOnUiThread(() -> new Handler().postDelayed(() -> hideLoading(), 2000));
    }
    /**
     * Ekran bazlı loading animasyonunu gizler
     * */
    @Override
    public void hideLoading() {
        if (loadingCounter > 0)
            loadingCounter--;
        Log.i(TAG, "Loading Counter: " + loadingCounter);
        if (loadingCounter==0)
            getActivity().runOnUiThread(() -> getUtilsLoading().hide());
    }

    @Override
    public void postEvent(String message) {
        GlobalBus.getBus().post(new DialogEvent(message));
    }
    /**
     * Ekranda dialog göstermek için bu method kullanılır.
     * Mesajı özelleştirilebilir
     * */
    @Override
    public void postEvent(int messageResourceId) {
        GlobalBus.getBus().post(new DialogEvent(messageResourceId));
    }
    /**
     * Ekranda dialog göstermek için bu method kullanılır.
     * Başlığı özelleştirilebilir
     * Mesajı özelleştirilebilir
     * */
    @Override
    public void postEvent(int messageResourceId, int titleResourceId) {
        GlobalBus.getBus().post(new DialogEvent(messageResourceId).setTitleResourceId(titleResourceId));
    }

    private void applyClickListener() {
        View back = findViewById(R.id.toolbar_back);
        if(back != null)
            back.setOnClickListener(view -> onBackPressed());
    }

    @Subscribe
    public void onException(BaseEvent exception) {
        String logString = UtilsDialog.showPermanentMessage(getActivity(), exception);
        Log.e(TAG, logString);
    }

    public void setIsDrawerActivity() {
        this.isDrawerActivity = true;
    }

    @Override
    public void hideKeyboard(){
        UtilsKeyboard.hideSoftKeyboard(getActivity());
    }

    @Override
    public void runWithUI(Runnable runnable){
        if (runnable != null)
            getActivity().runOnUiThread(runnable);
    }

    public String catchLanguage() {
        String displayLanguage = Locale.getDefault().getDisplayLanguage();
        String languageCode = getLanguageCode();

        if (!TextUtils.isEmpty(languageCode) || displayLanguage.equalsIgnoreCase(languageCode)){
            return languageCode;
        }else
            return displayLanguage;
    }

    @Override
    public void onBeforeLocaleChanged() {}

    @Override
    public void onAfterLocaleChanged() {}

    public void setLanguage(String language) {
        localizationDelegate.setLanguage(language);
    }

    public final void setLanguage(Locale locale) {
        localizationDelegate.setLanguage(locale);
    }

    public final String getLanguage() {
        return localizationDelegate.getLanguage();
    }

    public final Locale getLocale() {
        return localizationDelegate.getLocale();
    }

}
