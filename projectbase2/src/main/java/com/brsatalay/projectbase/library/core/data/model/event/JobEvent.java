package com.brsatalay.projectbase.library.core.data.model.event;

import android.support.annotation.StringRes;

import com.brsatalay.projectbase.library.core.data.model.JobStatus;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class JobEvent {
    private Long queueId;
    private JobStatus status;
    private @StringRes
    int errorResourceId = 0;
    private String errorMessage;

    public JobEvent(JobStatus status) {
        this.status = status;
    }

    public JobStatus getStatus() {
        return status;
    }

    public Long getQueueId() {
        return queueId;
    }

    public JobEvent setQueueId(Long queueId) {
        this.queueId = queueId;

        return this;
    }

    public int getErrorResourceId() {
        return errorResourceId;
    }

    public JobEvent setErrorResourceId(int errorResourceId) {
        this.errorResourceId = errorResourceId;

        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public JobEvent setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;

        return this;
    }
}
