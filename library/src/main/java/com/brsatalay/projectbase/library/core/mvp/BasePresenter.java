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
 * U: User Model
 */
public abstract class BasePresenter<T extends BaseView, R extends Repository, U> implements Presenter<T, R, U>{
    public String TAG = this.getClass().getSimpleName();
    private T view;
    private R repository;

    private SessionManager<U> manager;
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
    public void attachSessionManager(SessionManager manager) {
        this.manager = manager;
    }

    @Override
    public void detachSessionManager() {
        manager = null;
    }

    public SessionManager getSessionManager() {
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

    protected void showLoading(){
        if (view != null)
            view.showLoading();
    }

    protected void showLoading(@StringRes int stringResId){
        if (view != null)
            view.showLoading(stringResId);
    }

    protected void hideLoading(){
        if (view != null)
            view.hideLoading();
    }

    protected void postEvent(@StringRes int resourceId){
        if (getView() != null)
            getView().postEvent(resourceId);
    }

    protected SharedPreferences getSharedPreference(){
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        return preferences;
    }
    public abstract void onDestroy();
}