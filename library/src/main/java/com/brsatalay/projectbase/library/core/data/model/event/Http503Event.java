package com.brsatalay.projectbase.library.core.data.model.event;


import com.brsatalay.projectbase.library.core.data.model.EventType;

public class Http503Event extends BaseEvent {

    public Http503Event(int messageId) {
        super(messageId);
        setType(EventType.Warning);
    }

    public Http503Event(String message) {
        super(message);
        setType(EventType.Warning);
    }

    public Http503Event(int messageId, int titleId) {
        super(messageId, titleId);
        setType(EventType.Warning);
    }

    public Http503Event(String message, Throwable e) {
        super(message, e);
    }
}
