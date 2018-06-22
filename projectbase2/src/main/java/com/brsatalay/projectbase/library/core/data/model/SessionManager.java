package com.brsatalay.projectbase.library.core.data.model;

public class SessionManager<T> {
    private T activeUser;

    public void setActiveUser(T activeUser) {
        this.activeUser = activeUser;
    }

    public T getActiveUser() {
        return activeUser;
    }
}
