package com.example.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.R
import kotlinx.android.synthetic.main.activity_onepass.*
import com.netease.nis.quicklogin.QuickLogin
import com.netease.nis.quicklogin.listener.QuickLoginPreMobileListener


/**
 * Created by Stefan on 2019-08-08.
 */
class OnePassActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onepass)

        val login = QuickLogin.getInstance(applicationContext, "86ed63a6ce234cd98d1135d626094776")
        one_pass.setOnClickListener {
            login.prefetchMobileNumber(object : QuickLoginPreMobileListener() {
                override fun onGetMobileNumberSuccess(YDToken: String?, mobileNumber: String?) {
                    Log.d("Stefan", "onSuccess")
                }

                override fun onGetMobileNumberError(YDToken: String?, msg: String?) {
                    Log.d("Stefan", "onError: $msg")
                }

            })
        }
    }
}
