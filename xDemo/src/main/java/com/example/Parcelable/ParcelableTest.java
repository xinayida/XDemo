package com.example.Parcelable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Stefan on 2019-07-28.
 */
public class ParcelableTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MsgTip tip = new MsgTip();
        tip.setTips("123123");
        tip.setWarn("warnnnn");
        tip.setUrl("url");
        Log.d("Stefan", "tip: " + tip.toString());

        Log.d("Stefan", "tip copy: " + ParcelHelper.copy(tip).toString());

    }
}
