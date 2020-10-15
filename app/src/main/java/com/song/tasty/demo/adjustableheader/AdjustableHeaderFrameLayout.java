package com.song.tasty.demo.adjustableheader;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

import com.song.tasty.app.R;

/**
 * Created by liyongan on 19/3/5.
 */

public class AdjustableHeaderFrameLayout extends FrameLayout implements NestedScrollingParent2 {
    private View mHeaderView;
    private NestedScrollingParentHelper mScrollingParentHelper;
    private boolean mNeedHackDispatchTouch;
    private boolean mTouchDownOnHeader;
    private ValueAnimator mRevertAnimation;
    private int mMinHeaderHeight;
    private int mOldTop;
    boolean canScrollUp = true; // recyclerView现在loadMore需要特别处理

    private HeaderScrollListener mHeaderScrollListener;
    private boolean mNeedDragOver;
    private int mStickHeaderHeight;
    private int mMaxHeaderHeight;
    private OnClickListener mOnHeaderClickListener;

    public AdjustableHeaderFrameLayout(Context context) {
        this(context, null);
    }

    public AdjustableHeaderFrameLayout(Context context, @Nullable AttributeSet attrs) {
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
                    AdjustableHeaderFrameLayout.this.dispatchTouchEvent(e1);
                    AdjustableHeaderFrameLayout.this.dispatchTouchEvent(e2);
                }
                return mTouchDownOnHeader;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mOnHeaderClickListener != null) {
                    mOnHeaderClickListener.onClick(mHeaderView);
                    return true;
                }
                return super.onSingleTapConfirmed(e);
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public void reset() {
        scrollByOffsetTop(getTop());
    }

    public void setNeedDragOver(boolean needDragOver) {
        mNeedDragOver = needDragOver;
    }

    public void setHeaderScrollListener(HeaderScrollListener headerScrollListener) {
        mHeaderScrollListener = headerScrollListener;
    }

    public void setStickHeaderHeight(int height) {
        mStickHeaderHeight = height;
    }

    public void setMaxHeaderHeight(int maxHeaderHeight) {
        mMaxHeaderHeight = maxHeaderHeight;
        if (mMinHeaderHeight != 0) {
            onMaxHeaderHeightChange(maxHeaderHeight);
        }
    }

    public void setOnHeaderClickListener(OnClickListener onHeaderClickListener) {
        mOnHeaderClickListener = onHeaderClickListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mNeedHackDispatchTouch = false;
            if (mNeedDragOver && getScrollY() < 0) {
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

    private ObjectAnimator getRevertAnimation() {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofInt(this, "scrollY", getScrollY(), 0);
        animator.setDuration(300);
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

    private void onMaxHeaderHeightChange(int maxHeaderHeight) {
        mHeaderView.getLayoutParams().height = maxHeaderHeight;
        ((MarginLayoutParams) mHeaderView.getLayoutParams()).topMargin = mMinHeaderHeight - maxHeaderHeight;
        setHeaderViewPaddingTop(getMaxDragOverHeight());
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMinHeaderHeight == 0) {
            mHeaderView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mMinHeaderHeight = mHeaderView.getLayoutParams().height;
            if (mMaxHeaderHeight != 0 && mNeedDragOver) {
                onMaxHeaderHeightChange(mMaxHeaderHeight);
            }
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
        canScrollUp = true;
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    }

    @Override
    public void onNestedPreScroll(@NonNull final View target, int dx, int dy, int[] consumed, int type) {
        if (dy > 0) {
            if (getTop() > -getMaxNeedHideHeight()) {
                consumed[1] = dy;
                if (getScrollY() < 0) {
                    scrollBy(0, dy);
                } else {
                    scrollByOffsetTop(dy);
                }
            }
            if (!canScrollUp || !target.canScrollVertically(1)) {
                canScrollUp = false;
                consumed[1] = dy;
            }
        } else {
            canScrollUp = true;
            boolean canScrollDown = target.canScrollVertically(-1);
            if (!canScrollDown) {
                consumed[1] = dy;
                if (getTop() < 0) {
                    scrollByOffsetTop(dy);
                } else if (mNeedDragOver && type == ViewCompat.TYPE_TOUCH){
                    if (getScrollY() > -getMaxDragOverHeight()) {
                        scrollBy(0, dy);
                    }
                }
            }
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
            mHeaderScrollListener.onScroll(-newTop, -newTop * 1f / maxNeedHideHeight);
        }
        mOldTop = newTop;
        offsetTopAndBottom(-dy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t <= 0) {
            setHeaderViewPaddingTop(getMaxDragOverHeight() + t);
        }
    }

    private void setHeaderViewPaddingTop(int top) {
        mHeaderView.setPadding(mHeaderView.getPaddingLeft(), top, mHeaderView.getPaddingRight(), mHeaderView.getPaddingBottom());
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
        void onScroll(int dy, float fraction);

        void onHeaderTotalHide();

        void onHeaderTotalShow();
    }

    public static class SimpleHeaderScrollListener implements HeaderScrollListener {

        @Override
        public void onScroll(int dy, float fraction) {
        }

        @Override
        public void onHeaderTotalHide() {
        }

        @Override
        public void onHeaderTotalShow() {
        }
    }
}
