//package com.example.RecycleViewItemAnimation
//
//import android.app.Activity
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import com.example.R
//import kotlinx.android.synthetic.main.activity_recycler_animation.*
//import java.util.*
//
//
///**
// * Created by Stefan on 2019/4/12.
// */
//class RecycleViewAnimationActivity : Activity() {
//
//    private var adapter: MyRecyclerViewAdapter? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_recycler_animation)
//        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        adapter = MyRecyclerViewAdapter(this, initData())
//        recycler_view.adapter = adapter
//
//        val fireAnimator = FireAnimator()
//        fireAnimator.moveDuration = 500
//        recycler_view.itemAnimator = fireAnimator
//        add_btn.setOnClickListener {
//            adapter?.addData(1)
//        }
//        del_btn.setOnClickListener {
//            adapter?.removeData(1)
//        }
//    }
//
////    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
////        menuInflater.inflate(R.menu.menu_rv_animation, menu)
////        super.onCreateContextMenu(menu, v, menuInfo)
////    }
////
////    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        when (item.itemId) {
////            R.id.action_add ->
////            R.id.action_remove -> adapter?.removeData(1)
////        }
////        return true
////    }
//
//    private fun initData(): ArrayList<String> {
//        val mDatas = ArrayList<String>()
//        var i: Int = 'A'.toInt()
//        while (i < 'Z'.toInt()) {
//            mDatas.add("" + i.toChar())
//            i++
//        }
//        return mDatas
//    }
//
//
//}
