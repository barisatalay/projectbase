package com.brsatalay.projectbase.library.core;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class GlobalBus {
    private static EventBus eventBus;

    public static EventBus getBus() {
        if (eventBus == null)
            eventBus = EventBus.getDefault();
        return eventBus;
    }
}
