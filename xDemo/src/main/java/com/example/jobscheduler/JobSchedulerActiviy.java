package com.example.jobscheduler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ww on 2017/9/7.
 */

public class JobSchedulerActiviy extends Activity {
    private JobScheduler mJobScheduler;
    private int jobId;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent=new Intent(this, JobSchedulerService.class);
//        Messenger messenger=new Messenger(handler);
//        intent.putExtra(MyJobService.MESSENGER_INTENT_KEY, messenger);
//        startService(intent);
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        JobSchedulerService.class.getName()));
//        builder.setPeriodic(3000);

        builder.setMinimumLatency(5000);
        builder.setPersisted(true);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobId = mJobScheduler.schedule(builder.build());
        if (jobId <= 0) {
            Log.d("Stefan", "error: " + jobId);
        } else {
            Log.d("Stefan", "mJobScheduler schedule success: " + jobId);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (jobId > 0) {
//            mJobScheduler.cancel(jobId);
//        }
    }
}
