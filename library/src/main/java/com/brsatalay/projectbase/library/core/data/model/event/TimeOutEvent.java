package com.brsatalay.projectbase.library.core.data.model.event;

import com.brsatalay.projectbase.library.core.data.model.EventType;

public class TimeOutEvent extends BaseEvent {

    public TimeOutEvent(int messageId) {
        super(messageId);
        setType(EventType.Warning);
    }

    public TimeOutEvent(String message) {
        super(message);
        setType(EventType.Warning);
    }

    public TimeOutEvent(int messageId, int titleId) {
        super(messageId, titleId);
        setType(EventType.Warning);
    }

    public TimeOutEvent(String message, Throwable e) {
        super(message, e);
    }
}
