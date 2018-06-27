package com.brsatalay.projectbase.library.core.jobqueue;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.brsatalay.projectbase.library.core.helper.JobManager;

public class MyJobService extends FrameworkJobSchedulerService {
    @NonNull
    @Override
    protected JobManager getJobManager() {
        return JobManager.getInstance(getApplicationContext());
    }
}