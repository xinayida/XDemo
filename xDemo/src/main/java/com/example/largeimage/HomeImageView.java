package com.example.largeimage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.R;

import java.util.ArrayList;

/**
 * Created by Stefan on 2019/1/29.
 */
public class HomeImageView extends LargeImageView {
    public HomeImageView(Context context) {
        super(context);
        init();
    }

    public HomeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ArrayList<IconView> icons;
    private ArrayList<Rect> tarRects;

    private void init() {
        icons = new ArrayList<>();
        icons.add(new IconView(getContext(), R.drawable.home_my_train, 114, 232, 0.678571428571429f));
        icons.add(new IconView(getContext(), R.drawable.home_ai_action, 406, 288, 0.821428571428571f));
        icons.add(new IconView(getContext(), R.drawable.home_plan, 626, 240, 0.595238095238095f));
        icons.add(new IconView(getContext(), R.drawable.home_recommend, 892, 296, 0.821428571428571f));
        tarRects = new ArrayList<>();
        int oriTop = 88;
        tarRects.add(new Rect(0, 358 - oriTop, 451, 358 + 254 - oriTop));//我的训练//358
        tarRects.add(new Rect(164, 269 - oriTop, 164 + 844, 269 + 475 - oriTop));//智能动作库
        tarRects.add(new Rect(469, 337 - oriTop, 469 + 452, 337 + 254 - oriTop));//训练计划
        tarRects.add(new Rect(649, 322 - oriTop, 649 + 724, 322 + 407 - oriTop));//每日推荐

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (IconView icon : icons) {
            icon.onDraw(canvas);
        }
    }

    @Override
    protected void onAnimUpdate(float value) {
        for (IconView icon : icons) {
            icon.onAnimationUpdate(value);
        }
    }

    public void animToPos(int i) {
        if (i >= 0 && i <= 3) {
            icons.get(i).scale(true);
            for (int j = 0; j < 4; j++) {
                if (j != i) {
                    icons.get(j).scale(false);
                }
            }
        }

        animToRegion(tarRects.get(i));
    }


}
