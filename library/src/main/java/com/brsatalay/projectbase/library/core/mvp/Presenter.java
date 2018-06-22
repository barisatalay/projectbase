package com.brsatalay.projectbase.library.core.mvp;

import com.brsatalay.projectbase.library.core.data.model.SessionManager;

/**
 * Created by baris on 20.07.2017.
 */

public interface Presenter<V extends BaseView, R extends Repository> {
    void attachView(V mvpView);
    void detachView();
    void attachRepository(R repository);
    void detachRepository();
    void detachAll();
    void attachSessionManager(SessionManager manager);
    void detachSessionManager();
}
