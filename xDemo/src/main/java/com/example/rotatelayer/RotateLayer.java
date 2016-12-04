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
 * ��Launcher�е�WorkSapce���������һ����л���Ļ����
 */

public class RotateLayer extends ViewGroup {
	// ��ǰ����Ļ��ͼ
	private int mCurScreen = 1;
	// �������ٶ�
	private static final int SNAP_VELOCITY = 600;

	private static final String TAG = "ScrollLayout";
	// ���¼���״̬
	private static final int TOUCH_STATE_REST = 0;
	// �����϶���״̬
	private static final int TOUCH_STATE_SCROLLING = 1;

	private float mLastMotionX;
	// ���ڻ�������
	private Scroller mScroller;

	private int mTouchSlop;

	private int mTouchState = TOUCH_STATE_REST;
	// �������ٴ����ٶȵ���
	private VelocityTracker mVelocityTracker;

	private int mWidth;

	// ������������Ч������
	private Camera mCamera;
	private Matrix mMatrix;
	// ��ת�ĽǶȣ����Խ����޸����۲�Ч��
	private float angle = 90;

	public RotateLayer(Context context, AttributeSet attrs) {

		this(context, attrs, 0);

	}

	// �ڹ������г�ʼ��
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
	 * ������View����ʱ���ᵼ�µ�ǰ��View��Ч���ú����������Ƕ�View�������»��� ����drawScreen����
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
	 * ����ѭ�� ����Ч����ʵ�ֺ��� ,screenΪ��һ����View
	 */
	private void drawScreen(Canvas canvas, int screen, long drawingTime) {
		// �õ���ǰ��View�Ŀ��
		final int width = getWidth();
		final int scrollWidth = screen * width;
		final int scrollX = this.getScrollX();
		// ƫ���������ʱ��ֱ��
		if (scrollWidth > scrollX + width || scrollWidth + width < scrollX) {
			return;
		}
		final View child = getChildAt(screen);
		final int faceIndex = screen;
		final float currentDegree = getScrollX() * (angle / getMeasuredWidth());
		final float faceDegree = currentDegree - faceIndex * angle;
		if (faceDegree > 90 || faceDegree < -90) {
			return;
		}
		final float centerX = (scrollWidth < scrollX) ? scrollWidth + width
				: scrollWidth;
		final float centerY = getHeight() / 2;
		final Camera camera = mCamera;
		final Matrix matrix = mMatrix;
		canvas.save();
		camera.save();
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
	 * Ϊ��Viewָ��λ��
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int childLeft = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}

		}
	}

	// ��д�˷�����������߶ȺͿ��
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// Log.e(TAG, "onMeasure width = " + width);
		// Exactly��width������Ǿ�ȷ�ĳߴ�
		// AT_MOST��width����������ɻ�õĿռ�
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
		// �õ�����ҳ(��View)���������ǵĿ�͸�
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
			// ʹ��obtain�����õ�VelocityTracker��һ������
			mVelocityTracker = VelocityTracker.obtain();
		}
		// ����ǰ�Ĵ����¼����ݸ�VelocityTracker����
		mVelocityTracker.addMovement(event);
		// �õ������¼�������
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
			// ���㵱ǰ���ٶ�
			velocityTracker.computeCurrentVelocity(1000);
			// ��õ�ǰ���ٶ�
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

	private void setMWidth() {
		if (mWidth == 0) {
			mWidth = getWidth();
		}
	}

	/**
	 * 
	 * According to the position of current layout
	 * 
	 * scroll to the destination page.
	 */

	private void snapToDestination() {
		setMWidth();
		// ����View�Ŀ���Լ�������ֵ���ж����ĸ�View
		final int destScreen = (getScrollX() + mWidth / 2) / mWidth;
		snapToScreen(destScreen);

	}

	public void snapToScreen(int whichScreen) {
		// �򵥵��Ƶ�Ŀ����Ļ�������ǵ�ǰ��������һ��Ļ
		// ֱ����ת��ȥ����̫�Ѻ�
		// scrollTo(mLastScreen * getWidth(), 0);
		// Ϊ���Ѻ��ԣ�����������һ������Ч��
		// ��Ҫ�ٴλ����ľ��� ��������һ��Ļ�ļ�����������
		mCurScreen = whichScreen;
		if (mCurScreen > getChildCount() - 1)
			mCurScreen = getChildCount() - 1;
		int dx = mCurScreen * getWidth() - getScrollX();
		mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);
		// ��ʱ��Ҫ�ֶ�ˢ��View ����ûЧ��
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
