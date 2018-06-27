package com.brsatalay.projectbase.library.core.helper;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;
import com.birbit.android.jobqueue.scheduling.GcmJobSchedulerService;
import com.brsatalay.projectbase.library.core.jobqueue.MyGcmJobService;
import com.brsatalay.projectbase.library.core.jobqueue.MyJobService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class JobManager extends com.birbit.android.jobqueue.JobManager{
    private static JobManager instance;
    public JobManager(Context mContext) {
        super(prepareConfigure(mContext));
    }

    private static Configuration prepareConfigure(Context mContext) {
        Configuration.Builder builder = new Configuration.Builder(mContext)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
//                        UtilsLog.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                })
//                .minConsumerCount(1)//always keep at least one consumer alive
//                .maxConsumerCount(3)//up to 3 consumers at a time
//                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120);//wait 2 minute

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.scheduler(FrameworkJobSchedulerService.createSchedulerFor(mContext,
                    MyJobService.class), true);
        } else {
            int enableGcm = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);
            if (enableGcm == ConnectionResult.SUCCESS) {
                builder.scheduler(GcmJobSchedulerService.createSchedulerFor(mContext,
                        MyGcmJobService.class), true);
            }
        }

        return builder.build();
    }

    public static JobManager getInstance(@Nullable Context mContext) {
        if (instance == null)
            instance = new JobManager(mContext);
        return instance;
    }
}
