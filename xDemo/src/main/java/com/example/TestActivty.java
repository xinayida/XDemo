package com.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Stefan on 2018/4/25.
 */

public class TestActivty extends Activity {

    EditText key;
    EditText value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        key = (EditText) findViewById(R.id.key);
        value = (EditText) findViewById(R.id.value);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Stefan","onNewIntent");
    }

    public void send(View v) {
        ArrayList<String> keys = new ArrayList<>(Arrays.asList(key.getText().toString().split(",")));
        ArrayList<String> values = new ArrayList<>(Arrays.asList(value.getText().toString().split(",")));
        Intent i = new Intent("com.puppy.ACTION_SEARCH_FITNESS_COURSE");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", keys);
        bundle.putStringArrayList("values", values);
        i.putExtra("fitness_intent", bundle);
        startActivity(i);
    }

    public void send2(View v) {
        ArrayList<String> keys = new ArrayList<>();
        keys.add("level");
        keys.add("body");
        keys.add("goal");
        ArrayList<String> values = new ArrayList<>();
        values.add("入门");
        values.add("腿部");
        values.add("放松");
        Intent i = new Intent("com.puppy.ACTION_SEARCH_FITNESS_COURSE");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", keys);
        bundle.putStringArrayList("values", values);
        i.putExtra("fitness_intent", bundle);
        startActivity(i);
    }

    public void send3(View v) {
        Intent i = new Intent("com.puppy.ACTION_FITNESS_TRAINING");
        startActivity(i);
    }
}
