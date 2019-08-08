package com.example.largeimage;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Stefan on 2019/1/29.
 */
public class LargeImageViewActivity extends Activity {

    private MoveGestureDetector gestureDetector;
    private static final int FLIP_DISTANCE = 80;
    private ArrayList<Rect> positions = new ArrayList<>();
    private HomeImageView largeImageView;
    private int pos = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new MoveGestureDetector(this, new MoveGestureDetector.SimpleMoveGestureDetector() {
//            @Override
//            public boolean onMove(MoveGestureDetector detector) {
//                int moveX = (int) detector.getMoveX();
//                int moveY = (int) detector.getMoveY();
//                largeImageView.move(moveX, moveY);
//                return true;
//            }

            @Override
            public void onMoveEnd(MoveGestureDetector detector) {
                changePos();
            }
        });
        setContentView(R.layout.activity_large_view);
        largeImageView = (HomeImageView) findViewById(R.id.largeImageView);
//        try {
//            InputStream inputStream = getAssets().open("largebg.png");
        largeImageView.setInputStream(BitmapFactory.decodeResource(getResources(), R.drawable.large_bg), null);

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        positions.add(new Rect(100, 100, 200, 200));
//        positions.add(new Rect(200, 200, 400, 400));
//        positions.add(new Rect(300, 100, 400, 200));
//        positions.add(new Rect(0, 300, 200, 500));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onToucEvent(event);
    }

    //    @Override
//    public boolean onDown(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        float disX = e2.getX() - e1.getX();
//        if (Math.abs(disX) >= FLIP_DISTANCE) {
//            changePos();
//            return true;
//        }
//        return false;
//    }
    private void changePos() {
        pos++;
        if (pos >= 4) {
            pos = 0;
        }
        largeImageView.animToPos(pos);
    }

}
