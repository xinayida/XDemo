package com.example.rotatelayer;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的类
 */

public class RotateLayer extends ViewGroup {
    // 当前的屏幕视图
    private int mCurScreen = 1;
    // 滑动的速度
    private static final int SNAP_VELOCITY = 600;

    private static final String TAG = "ScrollLayout";
    // 无事件的状态
    private static final int TOUCH_STATE_REST = 0;
    // 处于拖动的状态
    private static final int TOUCH_STATE_SCROLLING = 1;

    private float mLastMotionX;
    // 用于滑动的类
    private Scroller mScroller;

    private int mTouchSlop;

    private int mTouchState = TOUCH_STATE_REST;
    // 用来跟踪触摸速度的类
    private VelocityTracker mVelocityTracker;

    private int mWidth;

    // 用来处理立体效果的类
    private Camera mCamera;
    private Matrix mMatrix;
    // 旋转的角度，可以进行修改来观察效果
    private float angle = 60;

    public RotateLayer(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    // 在构造器中初始化
    public RotateLayer(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        // mContext = (MainActivity) context;

        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        mCamera = new Camera();
        mMatrix = new Matrix();

    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    protected void attachViewToParent(View child, int index, LayoutParams params) {

        super.attachViewToParent(child, index, params);
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }

    }

    /*
     * 当进行View滑动时，会导致当前的View无效，该函数的作用是对View进行重新绘制 调用drawScreen函数
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // super.dispatchDraw(canvas);
        final long drawingTime = getDrawingTime();
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            drawScreen(canvas, i, drawingTime);
        }
    }

    /*
     * 处理循环 立体效果的实现函数 ,screen为哪一个子View
     */
    private void drawScreen(Canvas canvas, int screen, long drawingTime) {
        // 得到当前子View的宽度
        final int width = mWidth;//getWidth();
        final int screenW = getWidth();
        final int scrollWidth = screen * screenW;
        final int scrollX = this.getScrollX();
        // 偏移量不足的时候直接
//        if (scrollWidth > scrollX + screenW || scrollWidth + screenW < scrollX) {
//            return;
//        }
        final View child = getChildAt(screen);
        final int faceIndex = screen;
        final float currentDegree = getScrollX() * (angle / screenW);
        final float faceDegree = currentDegree - faceIndex * angle;
        if (faceDegree > angle || faceDegree < -angle) {
            return;
        }
        final float centerX = (scrollWidth < scrollX) ? scrollWidth + screenW : scrollWidth;
        final float centerY = getHeight() / 2;
        final Camera camera = mCamera;
        final Matrix matrix = mMatrix;
        canvas.save();
        camera.save();
//        camera.translate(width / 2, 0, width / 2);
        camera.rotateY(-faceDegree);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.concat(matrix);
        drawChild(canvas, child, drawingTime);
        canvas.restore();
    }

    // @Override
    // public void dispatchWindowFocusChanged(boolean hasFocus) {
    //
    // Log.d("Windows",
    // "dispatchWindowFocusChanged -- >"
    // + getChildAt(mCurScreen).toString());
    // super.dispatchWindowFocusChanged(hasFocus);
    // }

    // @Override
    // public void dispatchWindowVisibilityChanged(int visibility) {
    // Log.d("Windows",
    // "dispatchWindowVisibilityChanged -- >"
    // + getChildAt(mCurScreen).toString());
    //
    // super.dispatchWindowVisibilityChanged(visibility);
    // }

    public View getCurScreen() {
        return this.getChildAt(mCurScreen);

    }

    // @Override
    // protected void onAttachedToWindow() {
    //
    // Log.d("Windows", "onAttachedToWindow -- >"
    // + getChildAt(mCurScreen).toString());
    //
    // super.onAttachedToWindow();
    // }

    // @Override
    // protected void onDetachedFromWindow() {
    //
    // Log.d("Windows", "onDetachedFromWindow -- >"
    // + getChildAt(mCurScreen).toString());
    //
    // super.onDetachedFromWindow();
    // }

    // @Override
    // public boolean onInterceptTouchEvent(MotionEvent ev) {
    //
    // // Log.d(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);
    //
    // final int action = ev.getAction();
    // if ((action == MotionEvent.ACTION_MOVE)
    // && (mTouchState != TOUCH_STATE_REST)) {
    // return true;
    // }
    // final float x = ev.getX();
    // switch (action) {
    // case MotionEvent.ACTION_MOVE:
    // final int xDiff = (int) Math.abs(mLastMotionX - x);
    // if (xDiff > mTouchSlop) {
    // mTouchState = TOUCH_STATE_SCROLLING;
    // }
    // break;
    //
    // case MotionEvent.ACTION_DOWN:
    // mLastMotionX = x;
    // mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
    // : TOUCH_STATE_SCROLLING;
    // break;
    //
    // case MotionEvent.ACTION_CANCEL:
    //
    // case MotionEvent.ACTION_UP:
    // mTouchState = TOUCH_STATE_REST;
    // break;
    //
    // }
    //
    // return mTouchState != TOUCH_STATE_REST;
    //
    // }

    /*
     *
     * 为子View指定位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childLeft = 0;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(childLeft, 0, childLeft + mWidth,
                        childView.getMeasuredHeight());
                childLeft += mWidth;
            }

        }
    }

    // 重写此方法用来计算高度和宽度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        mWidth = width;
        // Log.e(TAG, "onMeasure width = " + width);
        // Exactly：width代表的是精确的尺寸
        // AT_MOST：width代表的是最大可获得的空间
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(
                    "ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException(
                    "ScrollLayout only can run at EXACTLY mode!");
        }

        // The children are given the same width and height as the scrollLayout
        // 得到多少页(子View)并设置他们的宽和高
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        // Log.e(TAG, "moving to screen "+mCurScreen);

        scrollTo(mCurScreen * width, 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mVelocityTracker == null) {
            // 使用obtain方法得到VelocityTracker的一个对象
            mVelocityTracker = VelocityTracker.obtain();
        }
        // 将当前的触摸事件传递给VelocityTracker对象
        mVelocityTracker.addMovement(event);
        // 得到触摸事件的类型
        final int action = event.getAction();
        final float x = event.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Log.d(TAG, "event down!");
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                break;

            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) (mLastMotionX - x);

                mLastMotionX = x;

                scrollBy(deltaX, 0);

                break;

            case MotionEvent.ACTION_UP:

                // if (mTouchState == TOUCH_STATE_SCROLLING) {

                final VelocityTracker velocityTracker = mVelocityTracker;
                // 计算当前的速度
                velocityTracker.computeCurrentVelocity(1000);
                // 获得当前的速度
                int velocityX = (int) velocityTracker.getXVelocity();
                // Log.d(TAG, "velocityX:" + velocityX + "; event : up");
                if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
                    // Fling enough to move left
                    // Log.d(TAG, "snap left");
                    snapToScreen(mCurScreen - 1);
                } else if (velocityX < -SNAP_VELOCITY
                        && mCurScreen < getChildCount() - 1) {
                    // Fling enough to move right
                    // Log.d(TAG, "snap right");
                    snapToScreen(mCurScreen + 1);
                } else {
                    snapToDestination();
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }

        return true;

    }

//	private void setMWidth() {
//		if (mWidth == 0) {
//			mWidth = getWidth();
//		}
//	}

    /**
     * According to the position of current layout
     * <p>
     * scroll to the destination page.
     */

    private void snapToDestination() {
//		setMWidth();
        // 根据View的宽度以及滑动的值来判断是哪个View
        final int destScreen = (getScrollX() + mWidth / 2) / mWidth;
        snapToScreen(destScreen);

    }

    public void snapToScreen(int whichScreen) {
        // 简单的移到目标屏幕，可能是当前屏或者下一屏幕
        // 直接跳转过去，不太友好
        // scrollTo(mLastScreen * getWidth(), 0);
        // 为了友好性，我们在增加一个动画效果
        // 需要再次滑动的距离 屏或者下一屏幕的继续滑动距离
        mCurScreen = whichScreen;
        if (mCurScreen > getChildCount() - 1)
            mCurScreen = getChildCount() - 1;
        int dx = mCurScreen * getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);
        // 此时需要手动刷新View 否则没效果
        invalidate();
    }

    // private void startCurrentView() {
    //
    // String viewTag = (String) getChildAt(mCurScreen).getTag();
    //
    // Message message = new Message();
    //
    // if (TextUtils.equals(viewTag, MainActivity.FIRST_INTENT_TAG)) {
    // mContext.mLocalActivityManager.startActivity(
    // MainActivity.FIRST_INTENT_TAG, new Intent(mContext,
    // FirstActivity.class));
    // message.what = MainActivity.FIRST_VIEW;
    // } else if (TextUtils.equals(viewTag, MainActivity.SECOND_INTENT_TAG)) {
    // mContext.mLocalActivityManager.startActivity(
    // MainActivity.SECOND_INTENT_TAG, new Intent(mContext,
    // SecondActivity.class));
    // message.what = MainActivity.SECOND_VIEW;
    // } else {
    // mContext.mLocalActivityManager.startActivity(
    // MainActivity.THIRD_INTENT_TAG, new Intent(mContext,
    // ThirdActivity.class));
    // message.what = MainActivity.THIRD_VIEW;
    // }
    //
    // mContext.mHandler.sendMessage(message);
    // }

}
