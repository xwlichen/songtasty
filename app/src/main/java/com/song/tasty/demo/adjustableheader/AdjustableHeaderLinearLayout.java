package com.song.tasty.demo.adjustableheader;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

import com.song.tasty.app.R;

/**
 * Created by liyongan on 19/1/29.
 */

public class AdjustableHeaderLinearLayout extends LinearLayout implements NestedScrollingParent2 {
    private static final String TAG = "Adjustable";
    private View mHeaderView;
    private NestedScrollingParentHelper mScrollingParentHelper;
    private boolean mNeedHackDispatchTouch;
    private boolean mTouchDownOnHeader;
    private ValueAnimator mRevertAnimation;
    private int mMinHeaderHeight;
    private int mOldTop;

    private HeaderScrollListener mHeaderScrollListener;
    private boolean mNeedDragOver;
    private View mHeadBackgroundView;
    private int mStickHeaderHeight;
    private int mMaxHeaderHeight;

    public AdjustableHeaderLinearLayout(Context context) {
        this(context, null);
    }

    public AdjustableHeaderLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AdjustableHeaderLinearLayout, 0, 0);
        mNeedDragOver = a.getBoolean(R.styleable.AdjustableHeaderLinearLayout_needDragOver, false);
        mStickHeaderHeight = a.getDimensionPixelSize(R.styleable.AdjustableHeaderLinearLayout_stickSectionHeight, 0);
        a.recycle();
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return mTouchDownOnHeader;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (mTouchDownOnHeader) {
                    mNeedHackDispatchTouch = true;
                    AdjustableHeaderLinearLayout.this.dispatchTouchEvent(e1);
                    AdjustableHeaderLinearLayout.this.dispatchTouchEvent(e2);
                }
                return mTouchDownOnHeader;
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public void setNeedDragOver(boolean needDragOver) {
        mNeedDragOver = needDragOver;
    }

    public void setHeaderBackground(View image) {
        mHeadBackgroundView = image;
        ((ViewGroup) mHeaderView).setClipChildren(image == null);
        setClipChildren(image == null);
    }

    public void setHeaderScrollListener(HeaderScrollListener headerScrollListener) {
        mHeaderScrollListener = headerScrollListener;
    }

    public void setMaxHeaderHeight(int maxHeaderHeight) {
        mMaxHeaderHeight = maxHeaderHeight;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mNeedHackDispatchTouch = false;
            if (mNeedDragOver && getContentTransY() > 0) {
                if (mRevertAnimation == null || !mRevertAnimation.isRunning()) {
                    mRevertAnimation = getRevertAnimation();
                    mRevertAnimation.start();
                }
                return true;
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchDownOnHeader = ev.getY() < getMaxNeedHideHeight();
        }
        if (mTouchDownOnHeader && mNeedHackDispatchTouch) {
            return super.dispatchTouchEvent(obtainNewMotionEvent(ev));
        }
        return super.dispatchTouchEvent(ev);
    }

    private ValueAnimator getRevertAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(getContentTransY(), 0);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                doOverScroll((Float) animation.getAnimatedValue());
            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

    private MotionEvent obtainNewMotionEvent(MotionEvent event) {
        MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[event.getPointerCount()];
        for (int i = 0; i < event.getPointerCount(); i++) {
            pointerProperties[i] = new MotionEvent.PointerProperties();
            event.getPointerProperties(i, pointerProperties[i]);
        }
        MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[event.getPointerCount()];
        for (int i = 0; i < event.getPointerCount(); i++) {
            pointerCoords[i] = new MotionEvent.PointerCoords();
            event.getPointerCoords(i, pointerCoords[i]);
            pointerCoords[i].y = pointerCoords[i].y + mMinHeaderHeight;
        }
        return MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), event.getPointerCount(), pointerProperties, pointerCoords, event.getMetaState(), event.getButtonState(), event.getXPrecision(), event.getYPrecision(), event.getDeviceId(), event.getEdgeFlags(), event.getSource(), event.getFlags());
    }

    private int getMaxNeedHideHeight() {
        return mMinHeaderHeight - mStickHeaderHeight;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mOldTop != 0) {
            offsetTopAndBottom(mOldTop - t);
        }
        Log.i(TAG, "onLayout: ");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.i(TAG, "invalidate: ");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.i(TAG, "dispatchDraw: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        if (mMinHeaderHeight == 0) {
            mHeaderView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mMinHeaderHeight = mHeaderView.getLayoutParams().height;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec + getMaxNeedHideHeight());
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < -getMaxDragOverHeight()) {
            y = -getMaxDragOverHeight();
        }
        if (y > 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRevertAnimation != null) {
            mRevertAnimation.cancel();
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return onStartNestedScroll(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        onNestedScrollAccepted(child, target, axes, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        onStopNestedScroll(target, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        onNestedPreScroll(target, dx, dy, consumed, ViewCompat.TYPE_TOUCH);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return getScrollingParentHelper().getNestedScrollAxes();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes, int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int nestedScrollAxes, int type) {
        getScrollingParentHelper().onNestedScrollAccepted(child, target, nestedScrollAxes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        getScrollingParentHelper().onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    }

    @Override
    public void onNestedPreScroll(@NonNull final View target, int dx, int dy, int[] consumed, int type) {
        if (dy > 0) {
            if (getTop() > -getMaxNeedHideHeight()) {
                consumed[1] = dy;
                if (getContentTransY() > 0) {
                    doOverScroll(getContentTransY() - dy);
                } else {
                    scrollByOffsetTop(dy);
                }
            }
        } else {
            boolean canScrollDown = target.canScrollVertically(-1);
            if (!canScrollDown) {
                consumed[1] = dy;
                if (getTop() < 0) {
                    scrollByOffsetTop(dy);
                } else if (mNeedDragOver && type == ViewCompat.TYPE_TOUCH){
                    if (getContentTransY() < getMaxDragOverHeight()) {
                        doOverScroll(getContentTransY() - dy);
                    }
                }
            }
        }
    }

    private float getContentTransY() {
        return mHeaderView.getTranslationY();
    }

    private void doOverScroll(float targetTransY) {
        if (targetTransY < 0) {
            targetTransY = 0;
        }
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setTranslationY(targetTransY);
        }
        if (mHeadBackgroundView != null) {
            int originHeight = mHeadBackgroundView.getMeasuredHeight();
            float scale = (originHeight + targetTransY) * 1f / originHeight;
            mHeadBackgroundView.setScaleX(scale);
            mHeadBackgroundView.setScaleY(scale);
        }
    }

    private void scrollByOffsetTop(int dy) {
        int oldTop = getTop();
        int maxNeedHideHeight = getMaxNeedHideHeight();
        int newTop = oldTop - dy;
        if (newTop > 0) {
            dy = oldTop;
        }
        if (newTop < -maxNeedHideHeight) {
            dy = maxNeedHideHeight + oldTop;
        }
        newTop = oldTop - dy;
        if (mHeaderScrollListener != null) {
            if (oldTop < 0 && newTop >= 0) {
                mHeaderScrollListener.onHeaderTotalShow();
            } else if (newTop == -maxNeedHideHeight) {
                mHeaderScrollListener.onHeaderTotalHide();
            }
            mHeaderScrollListener.onScroll(-newTop);
            if (mHeadBackgroundView != null) {
                mHeadBackgroundView.setTranslationY( -newTop / 2f);
            }
        }
        mOldTop = newTop;
        offsetTopAndBottom(-dy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private int getMaxDragOverHeight() {
        return mMaxHeaderHeight - mMinHeaderHeight;
    }

    private NestedScrollingParentHelper getScrollingParentHelper() {
        if (mScrollingParentHelper == null) {
            mScrollingParentHelper = new NestedScrollingParentHelper(this);
        }
        return mScrollingParentHelper;
    }

    public interface HeaderScrollListener {
        void onScroll(int dy);

        void onHeaderTotalHide();

        void onHeaderTotalShow();
    }

    public class SimpleHeaderScrollListener implements HeaderScrollListener {

        @Override
        public void onScroll(int dy) {
        }

        @Override
        public void onHeaderTotalHide() {
        }

        @Override
        public void onHeaderTotalShow() {
        }
    }

}
