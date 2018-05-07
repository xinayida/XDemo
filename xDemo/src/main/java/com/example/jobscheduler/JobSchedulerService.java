package com.example.jobscheduler;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ww on 2017/9/7.
 */

@SuppressLint("NewApi")
public class JobSchedulerService extends JobService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Stefan", "onStartCommand ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("Stefan", "onStartJob ");
        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.removeMessages(1);
        return false;
    }

    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(),
                    "JobService task running", Toast.LENGTH_SHORT)
                    .show();
            jobFinished((JobParameters) msg.obj, false);
            return true;
        }

    });
}
