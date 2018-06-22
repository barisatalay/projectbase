package com.brsatalay.projectbase.library.core.mvp;

import android.support.annotation.StringRes;

/**
 * Created by baris on 20.07.2017.
 */

public interface BaseView {
    void showToast(String message);
    void showToast(int resourceId);
    void showLoading();
    void showLoading(@StringRes int stringResId);
    void showLoadingWithDelay(Runnable runnable);
    void hideLoading();
    void postEvent(String message);
    void postEvent(@StringRes int messageResourceId);
    void postEvent(@StringRes int messageResourceId, @StringRes int titleResourceId);
    void hideKeyboard();
    void closeActivity();
    void runWithUI(Runnable runnable);
}
