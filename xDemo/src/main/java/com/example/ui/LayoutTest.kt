package com.example.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.R
import kotlinx.android.synthetic.main.activity_layout_test.*

/**
 * Created by Stefan on 2019-12-21.
 */
class LayoutTest: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_layout_test)
        test_btn.setOnClickListener {
            test_btn.visibility = View.GONE
        }
        for (i in 0 ..0){
            break
        }
    }
}