package com.brsatalay.projectbase.library.core.mvp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;

import com.brsatalay.projectbase.library.core.data.model.SessionManager;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by baris on 20.07.2017.
 *
 * T: View Model
 * R: Repository Model
 * SM: Extend from SessionManager
 */
public abstract class BasePresenter<T extends BaseView, R extends Repository, SM extends SessionManager> implements Presenter<T, R, SM>{
    public String TAG = this.getClass().getSimpleName();
    private T view;
    private R repository;
    private SM manager;
    private CompositeDisposable compositeDisposable = null;
    private Activity mActivity;
    private SharedPreferences preferences;

    public BasePresenter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void attachRepository(R repository) {
        this.repository = repository;
    }

    @Override
    public void detachRepository() {
        repository = null;
    }

    @Override
    public void detachAll() {
        if (getRepository() != null)
            getRepository().cancalAllQueue();
        detachSessionManager();
        detachRepository();
        detachView();
        onDestroy();
    }

    @Override
    public void attachSessionManager(SM manager) {
        this.manager = manager;
    }

    @Override
    public void detachSessionManager() {
        manager = null;
    }

    public SM getSessionManager() {
        return manager;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public T getView() {
        return view;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public R getRepository() {
        return repository;
    }

    public CompositeDisposable getCompositeDisposable() {
        if(compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        return compositeDisposable;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(view) before" +
                    " requesting data to the Presenter");
        }
    }

    public void showLoading(){
        if (view != null)
            view.showLoading();
    }

    public void showLoading(@StringRes int stringResId){
        if (view != null)
            view.showLoading(stringResId);
    }

    public void hideLoading(){
        if (view != null)
            view.hideLoading();
    }

    public void postEvent(@StringRes int resourceId){
        if (getView() != null)
            getView().postEvent(resourceId);
    }

    public SharedPreferences getSharedPreference(){
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        return preferences;
    }

    public void showLoadingWithDelay(Runnable runnable){
        if (getView() != null)
            getView().showLoadingWithDelay(runnable);
        else
            runnable.run();
    }

    protected void showLoadingWithCancelable(){
        if (view != null)
            view.showLoadingWithCancelable();
    }

    public abstract void onDestroy();
}