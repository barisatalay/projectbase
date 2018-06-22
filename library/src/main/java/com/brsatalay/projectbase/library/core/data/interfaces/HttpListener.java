package com.brsatalay.projectbase.library.core.data.interfaces;


import com.brsatalay.projectbase.library.core.data.model.event.BaseEvent;

/**
 * Created by barisatalay on 20.03.2018.
 */

public interface HttpListener {
    void onAlert(BaseEvent exception);
}
