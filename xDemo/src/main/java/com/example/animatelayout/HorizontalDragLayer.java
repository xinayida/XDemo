package com.example.animatelayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;

/**
 * Created by Stefan on 2020/6/30.
 */
public class HorizontalDragLayer extends HorizontalScrollView {
    private int subChildCount = 0;
    private ViewGroup firstChild = null;
    private int downX = 0;
    private int currentPage = 0;
    private boolean isDragging = false;

    public HorizontalDragLayer(@NonNull Context context) {
        super(context);
        init();
    }

    public HorizontalDragLayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalDragLayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        receiveChildInfo();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d("Stefan", "onScrollChanged " + l);
    }

    private void receiveChildInfo() {
        firstChild = (ViewGroup) getChildAt(0);
        if (firstChild != null) {
            subChildCount = firstChild.getChildCount();
//            for (int i = 0; i < subChildCount; i++) {
//                View child = firstChild.getChildAt(i);
//                if (child.getWidth() > 0) {
//                    pointList.add(child.getLeft());
//                }
//            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isDragging = super.onInterceptTouchEvent(ev);
        return isDragging;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float disX = event.getX() - downX;
//                if (isDragging) {
                if (Math.abs(disX) > getWidth() / 4) {
                    if (disX > 0) {
                        smoothScrollToPrePage();
                    } else {
                        smoothScrollToNextPage();
                    }
                } else {
                    smoothScrollToCurrent();
                }
                return true;
//                }
        }

        return super.onTouchEvent(event);
    }

    private void smoothScrollToCurrent() {
        smoothScrollTo(firstChild.getChildAt(currentPage).getLeft(), 0);
    }

    private void smoothScrollToNextPage() {
        if (currentPage < subChildCount - 1) {
            currentPage++;
            smoothScrollTo(firstChild.getChildAt(currentPage).getLeft(), 0);
        }
    }

    private void smoothScrollToPrePage() {
        if (currentPage > 0) {
            currentPage--;
            smoothScrollTo(firstChild.getChildAt(currentPage).getLeft(), 0);
        }
    }
}
