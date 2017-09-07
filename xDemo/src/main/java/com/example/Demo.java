package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.circleprogressbar.CircleMainActivity;
import com.example.component.CameraTest;
import com.example.component.ContactsLoaderActivity;
import com.example.draggridview.DragMainActivity;
import com.example.floatview.FloatActivity;
import com.example.httpserver.HttpServer;
import com.example.rotatelayer.RotateLayerActivity;
import com.example.silentinstall.SilentInstallActivity;
import com.example.wifi.WifiActivity;

public class Demo extends ListActivity {
    private List<Map<String, Object>> demos = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> tempDemos = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDemos();
        setListAdapter(new SimpleAdapter(this, demos,
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
//		getListView().setTextFilterEnabled(true);
    }

    private void initDemos() {
        addItem("Http server", HttpServer.class);
        addItem("Wifi transform", WifiActivity.class);
        addItem("Global float View", FloatActivity.class);
        addItem("Drag grid View", DragMainActivity.class);
        addItem("Circle Progress Bar", CircleMainActivity.class);
        addItem("Silent Install", SilentInstallActivity.class);
        addItem("Rotate Layoer", RotateLayerActivity.class);
        addItem("Components", new String[]{"Contacts Loader", "Camera"},
                new Class[]{ContactsLoaderActivity.class,
                        CameraTest.class});
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
}
