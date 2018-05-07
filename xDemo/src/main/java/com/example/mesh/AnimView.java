package com.example.mesh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by ww on 2017/9/21.
 */

public class AnimView extends ImageView {
    private static final boolean DEBUG_MODE = false;
    private float[] debugPoint;

    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int MAX_STEP = 50;
    private static final int NUM_OF_POINT = 8;
    private static final int DURATION = 200;//ms
    private Point offset;
    private Paint mPaint;
    private MeshCalculator mesh;
    private Matrix mMatrix;
    private int imgWidth;
    private int imgHeight;

    private boolean slideUp;//向上翻页或者向下翻


//        private Matrix mShadowGradientMatrix;
//        private LinearGradient mShadowGradientShader;
//        private Paint mShadowPaint;

    public AnimView(Context context) {
        super(context);
        init();
    }

    public AnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
    }

    private void setup() {
        if (getDrawable() == null) {
            return;
        }
        imgWidth = getDrawable().getIntrinsicWidth();
        imgHeight = getDrawable().getIntrinsicHeight();
        initMesh();
        initMatrix();
    }

    private void initMesh() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenw = outMetrics.widthPixels;
        int screenh = outMetrics.heightPixels;
        mPaint = new Paint();
        debugPoint = new float[2];
        offset = new Point((screenw - imgWidth) / 2, (screenh - imgHeight) / 3);
        mesh = new MeshCalculator(offset, WIDTH, HEIGHT, MAX_STEP);
        mesh.setBitmapSize(imgWidth, imgHeight);

        buildPaths(screenw - 20, 20);
        mesh.buildMeshes(imgWidth, imgHeight);
    }

    private void buildPaths(float endX, float endY) {
        debugPoint[0] = endX;
        debugPoint[1] = endY;
        mesh.buildPaths(endX, endY);
    }

    private void initMatrix() {
        mMatrix = new Matrix();
//            mShadowPaint = new Paint();
//            mShadowPaint.setStyle(Paint.Style.FILL);
//            mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0,
//                    Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
//            mShadowPaint.setShader(mShadowGradientShader);
//
//            mShadowGradientMatrix = new Matrix();
//            mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
//            mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
//            mShadowPaint.setAlpha((int) (0.9 * 255));
    }


    private void stepMatrix(float step) {
        double radian = step * Math.PI / MAX_STEP;
        float deltaX = (float) (imgHeight * Math.sin(radian) * Math.tan(Math.PI / 8));
        float deltaY = (float) (imgHeight * Math.cos(radian));
//            Log.d("Stefan", "step: " + step + " deltaX:" + deltaX + " deltaY:" + deltaY);
        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];
        src[0] = 0;
        src[1] = 0;
        src[2] = imgWidth;
        src[3] = 0;
        src[4] = 0;
        src[5] = imgHeight;
        src[6] = imgWidth;
        src[7] = imgHeight;

        dst[0] = -deltaX;
        dst[1] = imgHeight - deltaY;
        dst[2] = imgWidth + deltaX;
        dst[3] = imgHeight - deltaY;
        dst[4] = 0;
        dst[5] = imgHeight;
        dst[6] = imgWidth;
        dst[7] = imgHeight;
        mMatrix.setPolyToPoly(src, 0, dst, 0, NUM_OF_POINT >> 1);

    }

    private ValueAnimator vanim = ValueAnimator.ofFloat(0, 42, MAX_STEP);

    public boolean startAnim(boolean slideup, boolean reverse) {
        if (vanim.isStarted() || vanim.isRunning()) return false;
        slideUp = slideup;
        vanim.setDuration(DURATION);
        vanim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (slideUp) {
                    mesh.buildMeshes((float) animation.getAnimatedValue());
                } else {
                    stepMatrix((float) animation.getAnimatedValue());
                }
                invalidate();
            }
        });
        if (reverse) {
            vanim.reverse();
        } else {
            vanim.start();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            canvas.save();
            if (vanim.isStarted() || vanim.isRunning()) {
                if (slideUp) {
//                    canvas.drawColor(0xFFCCCCCC);
                    canvas.drawBitmapMesh(bitmap,
                            mesh.getWidth(),
                            mesh.getHeight(),
                            mesh.getVertices(),
                            0, null, 0, mPaint);

                    // Draw the target point.
                    if (DEBUG_MODE) {
                        mPaint.setColor(Color.RED);
                        mPaint.setStrokeWidth(2);
                        mPaint.setAntiAlias(true);
                        mPaint.setStyle(Paint.Style.FILL);
                        canvas.drawCircle(debugPoint[0], debugPoint[1], 5, mPaint);
                        // Draw the mesh vertices.
                        canvas.drawPoints(mesh.getVertices(), mPaint);
                        // Draw the paths
                        mPaint.setColor(Color.BLUE);
                        mPaint.setStyle(Paint.Style.STROKE);
                        Path[] paths = mesh.getPaths();
                        for (Path path : paths) {
                            canvas.drawPath(path, mPaint);
                        }
                    }
                } else {
                    canvas.translate(offset.x, offset.y);
                    canvas.concat(mMatrix);
                    canvas.drawBitmap(bitmap, 0, 0, null);
                    //            canvas.drawRect(0, 0, 130, mBitmap.getHeight(), mShadowPaint);
                }
            } else {
                canvas.translate(offset.x, offset.y);
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
    }

//    private int mLastPointX = 0;
//    private int mLastPointY = 0;
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float[] pt = {event.getX(), event.getY()};
//
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            int x = (int) pt[0];
//            int y = (int) pt[1];
//            if (mLastPointX != x || mLastPointY != y) {
//                mLastPointX = x;
//                mLastPointY = y;
//                buildPaths(pt[0], pt[1]);
//                invalidate();
//            }
//        }
//        return true;
//    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        setup();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
//        setup();
    }

//    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
//    private static final int COLORDRAWABLE_DIMENSION = 2;
//
//    private Bitmap getBitmapFromDrawable(Drawable drawable) {
//        if (drawable == null) {
//            return null;
//        }
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable) drawable).getBitmap();
//        }
//        try {
//            Bitmap bitmap;
//
//            if (drawable instanceof ColorDrawable) {
//                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
//                        COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
//            } else {
//                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
//            }
//            Canvas canvas = new Canvas(bitmap);
//            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//            drawable.draw(canvas);
//            return bitmap;
//        } catch (OutOfMemoryError e) {
//            return null;
//        }
//    }
}
