package com.brsatalay.projectbase.library.core.mvp;

/**
 * Created by baris on 20.07.2017.
 *
 * T: View Model
 * R: Repository Model
 * SM: Extend from SessionManager
 */

public interface Presenter<V extends BaseView, R extends Repository, SM> {
    void attachView(V mvpView);
    void detachView();
    void attachRepository(R repository);
    void detachRepository();
    void detachAll();
    void attachSessionManager(SM manager);
    void detachSessionManager();
}
