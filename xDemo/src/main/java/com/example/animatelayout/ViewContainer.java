package com.example.animatelayout;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan on 2020/6/28.
 */
public class ViewContainer extends FrameLayout {
    public static final long ANIM_DURATION = 500L;
    int w, h;

    public ViewContainer(@NonNull Context context) {
        super(context);
    }

    public ViewContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private List<View> pendingAddView = new ArrayList<>();

    @Override
    public void addView(final View child) {
        int count = getChildCount();
        if (count > 0) {
            synchronized (pendingAddView) {
                pendingAddView.add(child);
            }
            for (int i = 0; i < count; i++) {
                Rect rect = getRect(i, count + pendingAddView.size());
                animTo(getChildAt(i), rect);
            }
            getHandler().removeCallbacks(addViewTask);
            getHandler().postDelayed(addViewTask, ANIM_DURATION);
        } else {
            addView(child, getRect(0, 1));
        }
    }

    private Runnable addViewTask = new Runnable() {
        @Override
        public void run() {
            synchronized (pendingAddView) {
                int oldCount = getChildCount();
                int count = oldCount + pendingAddView.size();
                for (int i = 0; i < pendingAddView.size(); i++) {
                    addView(pendingAddView.get(i), getRect(oldCount + i, count));
                }
                pendingAddView.clear();
            }
        }
    };

    private Runnable removeViewAnimTask = new Runnable() {
        @Override
        public void run() {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                Rect rect = getRect(i, count);
                animTo(getChildAt(i), rect);
            }
        }
    };

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        int count = getChildCount();
        if (count > 0) {
            getHandler().removeCallbacks(removeViewAnimTask);
            getHandler().postDelayed(removeViewAnimTask, ANIM_DURATION);
        }
    }

    @Override
    public void removeView(View view) {
        int index = indexOfChild(view);
        if (index >= 0) {
            removeViewAt(index);
        }
    }

    private void addView(View view, Rect rect) {
        FrameLayout.LayoutParams layoutParams = new LayoutParams(rect.right - rect.left, rect.bottom - rect.top);
        view.setX(rect.left);
        view.setY(rect.top);
        addView(view, layoutParams);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getWidth();
        h = getHeight();
    }

    private Rect getRect(int pos, int total) {
        Rect rect = new Rect();
        int rw, rh, top = 0, left = 0;
        if (total == 1) {
            rw = w;
            rh = h;
        } else {
            rw = w / 2;
            rh = h / 2;
            if (total == 2) {
                top = h / 4;
                left = pos == 0 ? 0 : rw;
                rect.set(left, top, left + rw, top + rh);
            } else if (total == 3) {
                if (pos == 0) {
                    left = w / 4;
                } else {
                    top = h / 2;
                    if (pos == 2) {
                        left = w / 2;
                    }
                }
            } else if (total == 4) {
                if (pos == 1 || pos == 3) {
                    left = w / 2;
                }
                if (pos >= 2) {
                    top = h / 2;
                }
            }
        }
        rect.set(left, top, left + rw, top + rh);
        return rect;
    }

    private void animTo(View v, Rect rect) {
        FrameLayout.LayoutParams layoutParams = (LayoutParams) v.getLayoutParams();
        layoutParams.width = rect.right - rect.left;
        layoutParams.height = rect.bottom - rect.top;
        v.setLayoutParams(layoutParams);
        v.animate().x(rect.left).y(rect.top).setDuration(ANIM_DURATION).start();
    }
}
