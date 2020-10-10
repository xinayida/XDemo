package com.example.floatview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.DemoApp;

import java.util.ArrayList;
import java.util.List;

public class FloatView extends ImageView {
    private static final int R = 150;
    private float mTouchX;
    private float mTouchY;
    private float mStartX;
    private float mStartY;
    private OnClickListener mClickListener;
    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams windowManagerParams = ((DemoApp) getContext()
            .getApplicationContext()).getWindowParams();
    private List<RectF> imgPosList = new ArrayList<>();
    private List<Integer> colorList = new ArrayList<>();

    public FloatView(Context context) {
        super(context);
        imgPosList.add(getRect(0, 0));
        imgPosList.add(getRect(1, 0));
        imgPosList.add(getRect(1, 1));
        imgPosList.add(getRect(0, 1));
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.YELLOW);
        colorList.add(0xFFCCCCCC);
        createImage();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取到状态栏的高度
        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        float x = event.getRawX();
        float y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchX = event.getX();
                mTouchY = event.getY();
                mStartX = x;
                mStartY = y;
                break;
            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                updateViewPosition(x, y);
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                updateViewPosition(x, y);
                mTouchX = mTouchY = 0;
                if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                }
                break;
        }
        return true;
    }

    public void createImage() {
        Bitmap bitmap = Bitmap.createBitmap(R, R, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        Canvas canvas = new Canvas(bitmap);

        for (int i = 1; i < 4; i++) {
            RectF destRect = imgPosList.get(i);
            paint.setColor(colorList.get(i));
            canvas.drawOval(destRect, paint);
        }
        RectF destRect = imgPosList.get(0);
        RectF srcRect = imgPosList.get(1);
        paint.setColor(colorList.get(0));
        int sc = canvas.saveLayer(destRect, null,
                Canvas.ALL_SAVE_FLAG);
        canvas.drawOval(destRect, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawOval(srcRect, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
        setImageBitmap(bitmap);
    }

    private RectF getRect(int column, int row) {
        float diameter = (float) (Math.sqrt(2) * Math.tan(Math.PI / 8));
        RectF rect = new RectF();
        float left = column * (1 - diameter) * R;
        float top = row * (1 - diameter) * R;
        rect.set(left, top, left + diameter * R, top + diameter * R);
        return rect;
    }


    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition(float x, float y) {
        // 更新浮动窗口位置参数
        windowManagerParams.x = (int) (x - mTouchX);
        windowManagerParams.y = (int) (y - mTouchY);
        windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
    }
}
