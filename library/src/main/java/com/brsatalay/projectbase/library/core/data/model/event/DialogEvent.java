package com.brsatalay.projectbase.library.core.data.model.event;

import com.brsatalay.projectbase.library.core.data.model.EventType;

/**
 * Created by barisatalay on 20.03.2018.
 */

public class DialogEvent extends BaseEvent {
    public DialogEvent(int messageId) {
        super(messageId);
        setType(EventType.Warning);
    }

    public DialogEvent(String message) {
        super(message);
        setType(EventType.Warning);
    }

    public DialogEvent(int messageId, int titleId) {
        super(messageId, titleId);
        setType(EventType.Warning);
    }

    public DialogEvent(String message, Throwable e) {
        super(message, e);
    }
}
