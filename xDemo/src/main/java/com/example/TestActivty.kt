package com.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.test.*

import java.util.ArrayList
import java.util.Arrays

/**
 * Created by Stefan on 2018/4/25.
 */

class TestActivty : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
        long_click_btn.setOnLongClickListener { v ->
            Toast.makeText(this, "long click", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("Stefan", "onNewIntent")
    }

    fun send(v: View) {
        val keys = ArrayList(Arrays.asList(*key.text.toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        val values = ArrayList(Arrays.asList(*value.text.toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        val i = Intent("com.puppy.ACTION_SEARCH_FITNESS_COURSE")
        val bundle = Bundle()
        bundle.putStringArrayList("keys", keys)
        bundle.putStringArrayList("values", values)
        i.putExtra("fitness_intent", bundle)
        startActivity(i)
    }

    fun send2(v: View) {
        val keys = ArrayList<String>()
        keys.add("level")
        keys.add("body")
        keys.add("goal")
        val values = ArrayList<String>()
        values.add("入门")
        values.add("腿部")
        values.add("放松")
        val i = Intent("com.puppy.ACTION_SEARCH_FITNESS_COURSE")
        val bundle = Bundle()
        bundle.putStringArrayList("keys", keys)
        bundle.putStringArrayList("values", values)
        i.putExtra("fitness_intent", bundle)
        startActivity(i)
    }

    fun send3(v: View) {
//        val i = Intent("com.puppy.ACTION_FITNESS_TRAINING")
//        startActivity(i)
        long_click_btn.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING or HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING)
    }
}
