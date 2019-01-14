package com.example;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.circleprogressbar.CircleMainActivity;
import com.example.component.CameraTest;
import com.example.component.ContactsLoaderActivity;
import com.example.draggridview.DragMainActivity;
import com.example.floatview.FloatActivity;
import com.example.httpserver.HttpServer;
import com.example.jobscheduler.JobSchedulerActiviy;
import com.example.mesh.MeshActivity;
import com.example.rotatelayer.RotateLayerActivity;
import com.example.silentinstall.SilentInstallActivity;
import com.example.wifi.WifiActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo extends ListActivity {
    private List<Map<String, Object>> demos = new ArrayList<>();
    private List<Map<String, Object>> tempDemos = new ArrayList<>();
//    private Handler handler = new Handler(Looper.getMainLooper(), null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDemos();
        setListAdapter(new SimpleAdapter(this, demos,
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
//		getListView().setTextFilterEnabled(true);
//        for (int i = 0; i < 100; i++) {
//            handler.post(new MyRunnable(i));
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(20);
//                    handler.removeCallbacksAndMessages(null);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
    }

//    private class MyRunnable implements Runnable {
//        private int num;
//
//        public MyRunnable(int number) {
//            num = number;
//        }
//
//        @Override
//        public void run() {
//            System.out.println("~~" + num + "~~");
//        }
//    }

    private void initDemos() {
//        Log.d("Stefan", getSysProps("ro.product.version","-1"));
        getSharedPreferences("SP_JWTTK", Context.MODE_PRIVATE).edit().putString("JWTTK","123123123123").apply();
        addItem("Test", TestActivty.class);
        addItem("Mesh", MeshActivity.class);
        addItem("Http server", HttpServer.class);
        addItem("Wifi transform", WifiActivity.class);
        addItem("Global float View", FloatActivity.class);
        addItem("Drag grid View", DragMainActivity.class);
        addItem("Circle Progress Bar", CircleMainActivity.class);
        addItem("Silent Install", SilentInstallActivity.class);
        addItem("Rotate Layoer", RotateLayerActivity.class);
        addItem("Components", new String[]{"Contacts Loader", "Camera", "JobScheduler"},
                new Class[]{ContactsLoaderActivity.class,
                        CameraTest.class, JobSchedulerActiviy.class});
    }

    public static String getSysProps(String key, String def) {
        String value = def;
        try {
            Class<?> mClassType = Class.forName("android.os.SystemProperties");
            Method mGetMethod = mClassType.getDeclaredMethod("get", String.class);
            value = (String) mGetMethod.invoke(mClassType, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private void addItem(String name, String[] titles, Class[] clazzs) {
        if (titles == null || clazzs == null || titles.length != clazzs.length) {
            throw new IllegalArgumentException("Classes and Names not match!");
        }
        int len = titles.length;
        tempDemos.clear();
        for (int i = 0; i < len; i++) {
            tempDemos.add(getMap(titles[i], clazzs[i]));
        }

        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", tempDemos);
        demos.add(temp);
    }

    private void addItem(String name, Class<? extends Activity> clazz) {
        demos.add(getMap(name, clazz));
    }

    private Map<String, Object> getMap(String name, Class<? extends Activity> clazz) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        temp.put("intent", intent);
        return temp;
    }

    private boolean isSub;

    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l
                .getItemAtPosition(position);
        Object obj = map.get("intent");
        if (obj instanceof Intent) {
            Intent intent = (Intent) map.get("intent");
            startActivity(intent);
        } else if (obj instanceof List) {
            isSub = true;
            setListAdapter(new SimpleAdapter(this, tempDemos,
                    android.R.layout.simple_list_item_1, new String[]{"title"},
                    new int[]{android.R.id.text1}));
        }
    }

    @Override
    public void onBackPressed() {
        if (isSub) {
            isSub = false;
            setListAdapter(new SimpleAdapter(this, demos,
                    android.R.layout.simple_list_item_1, new String[]{"title"},
                    new int[]{android.R.id.text1}));
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.d("Stefan","onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d("Stefan","onSaveInstanceState " + outState);
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
//        Log.d("Stefan","onStateNotSaved ");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("Stefan","onResume ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.d("Stefan","onRestart ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
//        Log.d("Stefan","onRestoreInstanceState " + state);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
}
