package com.brsatalay.projectbase.library.core.data.model.event;

import com.brsatalay.projectbase.library.core.data.model.JobStatus;

/**
 * Created by barisatalay on 20.04.2018.
 */

public class SyncEvent extends JobEvent {
    private String syncName;

    public SyncEvent(JobStatus status) {
        super(status);
    }

    public String getSyncName() {
        return syncName;
    }

    public SyncEvent setSyncName(String syncName) {
        this.syncName = syncName;

        return this;
    }
}
