package com.song.tasty.demo.coordinate;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.ViewCompat;

import com.smart.utils.LogUtils;
import com.song.tasty.app.R;
import com.song.tasty.demo.adjustableheader.MyRecycleView;
import com.song.tasty.demo.adjustableheader.MyRelativeLayout;


/**
 * Created by liyongan on 19/3/13.
 */

public class HeaderBehavior extends ViewOffsetBehavior<View> {
    private static final int INVALID_POINTER = -1;

    private boolean mIsBeingDragged;
    private int mActivePointerId = INVALID_POINTER;
    private int mLastMotionY;
    private int mTouchSlop = -1;
    private MotionEvent mCurrentDownEvent;
    private boolean mNeedDispatchDown;
    private ValueAnimator mRevertAnimator;

    private int mStickSectionHeight;
    private int mMaxOverDragHeight;

    public HeaderBehavior() {}

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeaderBehavior);
        mStickSectionHeight = a.getDimensionPixelSize(R.styleable.HeaderBehavior_stickHeight, 0);
        mMaxOverDragHeight = a.getDimensionPixelSize(R.styleable.HeaderBehavior_maxOverDragHeight, 0);
        a.recycle();
    }

    public int getStickSectionHeight() {
        return mStickSectionHeight;
    }

    public int getOverScrollOffset(View view) {
        return mMaxOverDragHeight;
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        flag=false;
//        LogUtils.e("xw","onInterceptTouchEvent");
        if (mTouchSlop < 0) {
            mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }

        final int action = ev.getAction();

        // Shortcut since we're being dragged
        if (action == MotionEvent.ACTION_MOVE && mIsBeingDragged) {
            return true;
        }



        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mIsBeingDragged = false;
                mNeedDispatchDown = true;
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, x, y)) {
                    LogUtils.e("xw","onInterceptTouchEvent  ACTION_DOWN isPointInChildBounds is ture");
                    mLastMotionY = y;
                    mActivePointerId = ev.getPointerId(0);
                    if (mCurrentDownEvent != null) {
                        mCurrentDownEvent.recycle();
                    }
                    mCurrentDownEvent = MotionEvent.obtain(ev);
                }else{
                    LogUtils.e("xw","onInterceptTouchEvent  ACTION_DOWN isPointInChildBounds is false");

                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                LogUtils.e("xw","onInterceptTouchEvent  ACTION_MOVE");

                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) {
                    break;
                }

                final int y = (int) ev.getY(pointerIndex);
                final int yDiff = Math.abs(y - mLastMotionY);
                if (yDiff > mTouchSlop) {
                    mIsBeingDragged = true;
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                LogUtils.e("xw","onInterceptTouchEvent  ACTION_UP");

                mIsBeingDragged = false;
                mNeedDispatchDown = true;
                mActivePointerId = INVALID_POINTER;
                break;
            }
        }
        return mIsBeingDragged;
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }


    boolean isUpOrCancel = false;
    boolean isDown=false;
    boolean flag=false;
    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {


        if (mTouchSlop < 0) {
            mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }

        flag=false;
        int x1 = (int) ev.getRawX();
        int y1 = (int) ev.getRawY();
        flag=isTouchPointInView(child,x1,y1);


        isUpOrCancel = false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                isDown=true;
                LogUtils.e("xw","onTouchEvent  ACTION_DOWN");
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();

                if (parent.isPointInChildBounds(child, x, y)) {
                    mLastMotionY = y;
                    mActivePointerId = ev.getPointerId(0);
                    if (mCurrentDownEvent != null) {
                        mCurrentDownEvent.recycle();
                    }
                    mCurrentDownEvent = MotionEvent.obtain(ev);
                } else {
                    return false;
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                isDown=false;
                LogUtils.e("xw","onTouchEvent  ACTION_MOVE");

                final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    return false;
                }

                final int y = (int) ev.getY(activePointerIndex);

                if (!mIsBeingDragged && Math.abs(mLastMotionY - y) > mTouchSlop) {
                    mIsBeingDragged = true;
                }
                break;
            }

            case MotionEvent.ACTION_UP:
                LogUtils.e("xw","onTouchEvent  ACTION_UP");
                isDown=false;

                isUpOrCancel = true;
                mActivePointerId = INVALID_POINTER;
                startRevertAnimator(child);
                break;
            case MotionEvent.ACTION_CANCEL: {
                LogUtils.e("xw","onTouchEvent  ACTION_CANCEL");
                isDown=false;

                isUpOrCancel = true;
                mActivePointerId = INVALID_POINTER;
//                startRevertAnimator(child);
                break;
            }
            default:
                isDown=false;
                break;
        }
        if (mIsBeingDragged) {
            for (int i = 0, c = parent.getChildCount(); i < c; i++) {
                View sibling = parent.getChildAt(i);
                final CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) sibling.getLayoutParams()).getBehavior();
                if (behavior != null && behavior instanceof BelowHeaderBehavior) {
                    final int offset = child.getHeight() - child.getBottom();
                    LogUtils.e("xw","onTouchEvent offset:"+offset+",child.getHeight:"+child.getHeight()+",child.getBottom:"+child.getBottom());
                    if (mNeedDispatchDown) {
                        //第一次需要自己下发Down事件，绑定BelowHeaderBehavior的控件才能接受和处理所有的touch事件
//                        LogUtils.e("xw","mNeedDispatchDown :true");
                        mNeedDispatchDown = false;
                        mCurrentDownEvent.offsetLocation(0, offset);
                        sibling.dispatchTouchEvent(mCurrentDownEvent);
                    }else{
                        LogUtils.e("xw","mNeedDispatchDown :false");

                    }
                    ev.offsetLocation(0, offset);
                    sibling.dispatchTouchEvent(ev);

                }
            }
        }
        if (isUpOrCancel) {
            mIsBeingDragged = false;
            mNeedDispatchDown = true;
        }
        return true;
    }

    private void startRevertAnimator(final View child) {
        if (child.getTop() <= 0) {
            return;
        }
        final int targetOffset = 0;
        final int currentOffset = getTopBottomOffsetForScrollingSibling();
        if (currentOffset == targetOffset) {
            if (mRevertAnimator != null && mRevertAnimator.isRunning()) {
                mRevertAnimator.cancel();
            }
            return;
        }
        if (mRevertAnimator == null) {
            mRevertAnimator = new ValueAnimator();
            mRevertAnimator.setInterpolator(new DecelerateInterpolator());
            mRevertAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setHeaderTopBottomOffset((int) animation.getAnimatedValue(), getMaxDragOffset(child), getOverScrollOffset(child));
                }
            });
        } else {
            mRevertAnimator.cancel();
        }
        mRevertAnimator.setDuration(300);
        mRevertAnimator.setIntValues(currentOffset, targetOffset);
        mRevertAnimator.start();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        LogUtils.e("xw","onStartNestedScroll");

        boolean result = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        if (result && mRevertAnimator != null) {
            mRevertAnimator.cancel();
        }
        return result;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtils.e("header","child:"+child.getClass());

//        if (target instanceof MyRecycleView){
//
//        }
//        if (isDown){
//            ((MyRecycleView)target).stopScroll();
//            ((MyRecycleView)target).stopNestedScroll(type);
////            ((MyRelativeLayout)child).stopNestedScroll();
//            coordinatorLayout.hasNestedScrollingParent();
//            return;
//        }


        if (dy != 0) {
            if (dy > 0) {//向上滚动
                consumed[1] = scroll(coordinatorLayout, child, dy, getMaxDragOffset(child), getOverScrollOffset(child));
            } else {
                boolean canScrollDown = target.canScrollVertically(-1);
//                if (((MyRecycleView)target).computeVerticalScrollOffset()<=0||!isUpOrCancel) {
                    consumed[1] = scroll(coordinatorLayout, child, dy, getMaxDragOffset(child), type == ViewCompat.TYPE_NON_TOUCH ? 0 : getOverScrollOffset(child));
                    if (consumed[1] == 0 && type == ViewCompat.TYPE_NON_TOUCH) {
                        ((MyRecycleView) target).stopNestedScroll(type);
                    }
//                }
            }
        }
        MyRecycleView recycleView=((MyRecycleView) target);

//        if (flag){
//            if (getTopAndBottomOffset()==-600||getTopAndBottomOffset()==0) {
//                recycleView.stopScroll();
//            }
//        }
//        if (getTopAndBottomOffset()==0||getTopAndBottomOffset()==-750) {
//            ((NestedScrollingChild2) target).stopNestedScroll(type);
//        }


        LogUtils.e("xw","onNestedPreScroll consumed[1]:"+consumed[1]+",dy:"+dy+",getTopAndBottomOffset:"+getTopAndBottomOffset()+"computeVerticalScrollOffset():"+recycleView.computeVerticalScrollOffset());
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        LogUtils.e("xw","onStopNestedScroll");

        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            startRevertAnimator(child);
        }
    }

    int setHeaderTopBottomOffset(int newOffset, int minOffset, int maxOffset) {
        final int curOffset = getTopAndBottomOffset();
        int consumed = 0;

        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
            // If we have some scrolling range, and we're currently within the min and max
            // offsets, calculate a new offset
            newOffset = MathUtils.clamp(newOffset, minOffset, maxOffset);

            if (curOffset != newOffset) {
                setTopAndBottomOffset(newOffset);
                // Update how much dy we have consumed
                consumed = curOffset - newOffset;
            }
        }

        return consumed;
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    final int scroll(CoordinatorLayout coordinatorLayout, View header,
                     int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(
                getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    /**
     * Returns the maximum px offset when {@code view} is being dragged.
     */
    int getMaxDragOffset(View view) {
        return -view.getHeight() + mStickSectionHeight;
    }

}
