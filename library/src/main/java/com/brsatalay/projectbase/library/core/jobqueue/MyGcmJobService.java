package com.brsatalay.projectbase.library.core.jobqueue;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.brsatalay.projectbase.library.core.helper.JobManager;

public class MyGcmJobService extends GcmJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return JobManager.getInstance(getApplicationContext());
    }
}
