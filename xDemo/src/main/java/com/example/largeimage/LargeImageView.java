package com.example.largeimage;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Stefan on 2019/1/29.
 */
public class LargeImageView extends View {
    /**
     * 图片的宽度和高度
     */
    private int mImageWidth, mImageHeight;
    /**
     * 绘制的区域
     */
    private volatile Rect mRect, mScreenRect;
    private Bitmap bgBtimp;
    private Rect mDstRect, originRect;

    private ValueAnimator animator;

    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    static {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public LargeImageView(Context context) {
        super(context);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRect != null) {
            canvas.drawBitmap(bgBtimp, mRect, mScreenRect, null);
        }
    }

    private void checkWidth() {
        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect.right > imageWidth) {
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
        }
    }


    private void checkHeight() {
        Rect rect = mRect;
        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        if (rect.bottom > imageHeight) {
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mScreenRect = new Rect(0, 0, width, height);
        if (mRect == null) {
            mRect = new Rect(mScreenRect);
        }
    }

    public void setInputStream(Bitmap bitmap, Rect defaultRect) {
        mRect = defaultRect;
        bgBtimp = bitmap;
        mImageHeight = bgBtimp.getHeight();
        mImageWidth = bgBtimp.getWidth();
        requestLayout();
        invalidate();
    }

    public void move(int x, int y) {
        boolean moved = false;
        if (mImageWidth > getWidth()) {
            mRect.offset(-x, 0);
            checkWidth();
            moved = true;
        }
        if (mImageHeight > getHeight()) {
            mRect.offset(0, -y);
            checkHeight();
            moved = true;
        }
        if (moved) {
            invalidate();
        }
    }


    private int disLeft, disTop, disRight, disBottom;

    public void animToRegion(Rect rect) {
        mDstRect = rect;
        originRect = new Rect(mRect);

        Log.d("Stefan", " ori: " + originRect.toString() + " dst: " + rect.toString());
        disLeft = mDstRect.left - originRect.left;
        disTop = mDstRect.top - originRect.top;
        disRight = mDstRect.right - originRect.right;
        disBottom = mDstRect.bottom - originRect.bottom;
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0, 100f);
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();
                    onAnimUpdate(value);
//                    Log.d("Stefan", "value: " + value);
                    int left = (int) (originRect.left + disLeft * value / 100f);
                    int right = (int) (originRect.right + disRight * value / 100f);
//                    int right = left + mScreenRect.right;
                    int top = (int) (originRect.top + disTop * value / 100);
//                    int bottom = top + mScreenRect.bottom;
                    int bottom = (int) (originRect.bottom + disBottom * value / 100f);
                    mRect.set(left, top, right, bottom);
                    invalidate();
//                    postInvalidate();
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.d("Stefan", " end: " + mRect.toString());
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            animator.cancel();
        }
        animator.start();
    }

    protected void onAnimUpdate(float value) {

    }

}
