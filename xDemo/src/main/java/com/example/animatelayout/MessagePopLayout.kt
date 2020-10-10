package com.example.animatelayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.example.R
import java.util.*

/**
 * Created by Stefan on 2020/6/29.
 */
class MessagePopLayout : FrameLayout {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private var msgList = Collections.synchronizedList(mutableListOf<View>())
    private val minGapY = convert(20)
    private val FPS = 15L
    private val UPDATE_MSG = 1
    private val handler = object : android.os.Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            var lastBottom = 0f
            for (i in 0 until childCount) {
                val child = getChildAt(i)
//                if (childCount > 1 && i > 0) {
//                    val lastChild = getChildAt(i - 1)
//                    lastBottom = lastChild.y + lastChild.height
//                    if (child.y - minGapY > lastBottom) {
//                        child.y -= speed
//                    }
//                } else {
                child.y -= speed
//                }

                if (child.y <= endY && child.alpha == 1f) {
                    end(child)
                }
                if (msgList.size > 0 && i == childCount - 1 && (msgList[0].y - child.y - child.height) > minGapY) {
                    Log.d("Stefan", " pendingY (${msgList[0].y}) childY(${child.y}) childH(${child.height}) minGapY(${minGapY})")
                    start(msgList.removeAt(0))
                }
            }
            if (childCount > 0) {
                sendEmptyMessageDelayed(UPDATE_MSG, FPS)
            }
        }
    }
    private val speed: Float by lazy {
        height / (1000 * 3 / FPS.toFloat())
    }

    private val endY: Float by lazy {
        speed * (300 / FPS) + minGapY
    }

    fun addMsg(text: CharSequence) {
        if (childCount == 0) {
            start(genMsgView(text))
            handler.sendEmptyMessage(UPDATE_MSG)
        } else {
            msgList.add(genMsgView(text))
        }

    }

    private fun start(view: View) {
        Log.d("Stefan", "Start ~~")
        val lp = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(view, lp)
        val alphaStart = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(300)
        alphaStart.start()
    }

    private fun end(view: View) {
        Log.d("Stefan", "end ~~")
        view.alpha = 0.99f
        val alphaEnd = ObjectAnimator.ofFloat(view, "alpha", 0.99f, 0f).setDuration(300)
        alphaEnd.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                removeView(view)
            }
        })
        alphaEnd.start()
    }


    private fun genMsgView(text: CharSequence): View {
        val view = LayoutInflater.from(context).inflate(R.layout.msg_layout, null)
        view.findViewById<TextView>(R.id.msg).text = text
        view.measure(0, 0)
        view.y = (height - view.measuredHeight - minGapY).toFloat()
        return view
    }

    private fun convert(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

}