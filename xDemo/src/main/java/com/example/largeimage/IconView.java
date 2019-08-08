package com.example.largeimage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Stefan on 2019/1/29.
 */
public class IconView {

    private Bitmap bitmap;
    private boolean scale = true;
    private Rect srcRect, scaledRect, oriRect, drawingRect;
    private int imgW, imgH;

    public IconView(Context context, int resId, int x, int y, float scale) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        imgW = bitmap.getWidth();
        imgH = bitmap.getHeight();
        srcRect = new Rect(0, 0, imgW, imgH);
        Point DST_POINT = new Point(468, 248);
        scaledRect = new Rect(DST_POINT.x, DST_POINT.y, DST_POINT.x + imgW, DST_POINT.y + imgH);
        init(new Point(x, y), scale);
    }

    public void init(Point pos, float scale) {
        oriRect = new Rect(pos.x, pos.y, (int) (pos.x + imgW * scale), (int) (pos.y + imgH * scale));
        drawingRect = new Rect(oriRect);

    }

    public void scale(boolean flag) {
        scale = flag;
    }

    public void onAnimationUpdate(float value) {
        int oriW = oriRect.right - oriRect.left;
        int oriH = oriRect.bottom - oriRect.top;
        if (scale) {
            int disx = scaledRect.left - oriRect.left;
            int disy = scaledRect.top - oriRect.top;
            int left = oriRect.left + (int) (disx * value / 100);
            int top = oriRect.top + (int) (disy * value / 100);
            int right = left + oriW + (int) ((imgW - oriW) * value / 100);
            int bottom = top + oriH + (int) ((imgH - oriH) * value / 100);
            drawingRect.set(left, top, right, bottom);
        } else {
            int disx = oriRect.left - scaledRect.left;
            int disy = oriRect.top - scaledRect.top;
            int left = scaledRect.left + (int) (disx * value / 100);
            int top = scaledRect.top + (int) (disy * value / 100);
            int right = left + imgW + (int) ((oriW - imgW) * value / 100);
            int bottom = top + imgH + (int) ((oriH - imgH) * value / 100);
            drawingRect.set(left, top, right, bottom);
        }
    }

    public void onDraw(Canvas canvas) {
        if (scale) {
            canvas.drawBitmap(bitmap, srcRect, drawingRect, null);
        }
    }
}
