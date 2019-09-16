package com.song.tasty.common.ui.widget.behavior;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.ui.LogUtils;
import com.song.tasty.common.ui.R;
import com.song.tasty.common.ui.widget.NestedLinearLayout;

import java.lang.ref.WeakReference;

/**
 * @date : 2019-09-05 17:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HeaderBehavior extends ViewOffsetBehavior<View> {
    private static final int STATE_OPENED = 0;
    private static final int STATE_CLOSED = 1;
    private static final int DURATION_SHORT = 300;
    private static final int DURATION_LONG = 600;

    private int mCurState = STATE_OPENED;
    private OnHeaderStateListener headerStateListener;

    private OverScroller mOverScroller;

    private WeakReference<CoordinatorLayout> mParent;//CoordinatorLayout
    private WeakReference<View> mChild;//CoordinatorLayout的子View，即header

    //界面整体向上滑动，达到列表可滑动的临界点
    private boolean upReach;
    //列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private boolean downReach;
    //列表上一个全部可见的item位置
    private int lastPosition = -1;

    private FlingRunnable mFlingRunnable;

    private Context mContext;

    //tab上移结束后是否悬浮在固定位置
    private boolean tabSuspension = false;


    private static final String TAG = "overScroll";
    private static final String TAG_TOOLBAR = "toolbar";
    private static final String TAG_MIDDLE = "middle";
    private static final float TARGET_HEIGHT = 1500;
    private View mTargetView;
    private int mParentHeight;
    private int mTargetViewHeight;
    private float mTotalDy;
    private float mLastScale;
    private int mLastBottom;
    private boolean isAnimate;
    private View mToolBar;
    private ViewGroup middleLayout;//个人信息布局
    private int mMiddleHeight;
    private boolean isRecovering = false;//是否正在自动回弹中

    private final float MAX_REFRESH_LIMIT = 0.3f;//达到这个下拉临界值就开始刷新动画


    public HeaderBehavior() {
        init();
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mOverScroller = new OverScroller(mContext);

//        mGestureDetector = new GestureDetector(mContext, mSimpleOnGestureListener);

        float density = mContext.getResources().getDisplayMetrics().density;
        float dpi = density * 160.0f;
        mPhysicalCoeff = SensorManager.GRAVITY_EARTH * 39.37f * dpi * 0.84f;
        // 新增部分 start
        ViewConfiguration viewConfiguration = ViewConfiguration.get(mContext);
        mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();


//        mGestureDetector.setIsLongpressEnabled(false);
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        mParent = new WeakReference<>(parent);
        mChild = new WeakReference<>(child);


        if (mToolBar == null) {
            mToolBar = parent.findViewWithTag(TAG_TOOLBAR);
        }
        if (middleLayout == null) {
            middleLayout = (ViewGroup) parent.findViewWithTag(TAG_MIDDLE);
        }
        // 需要在调用过super.onLayoutChild()方法之后获取
        if (mTargetView == null) {
            mTargetView = parent.findViewWithTag(TAG);
            if (mTargetView != null) {
                initial((ViewGroup) child);
            }
        }


    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes) {
        isAnimate = true;
        //这个布局就是middleLayout
        if (tabSuspension) {
            return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !isClosed();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        if (velocityY > 100) {//当y速度>100,就秒弹回
            isAnimate = false;
        }
//        LogUtils.e("xw", "onNestedPreFling velocityY :" + velocityY);

        lastPosition = -1;
        return !isClosed();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull final View child, @NonNull MotionEvent ev) {
//        obtainVelocityTracker();
//        mGestureDetector.onTouchEvent(ev);
//        mVelocityTracker.computeCurrentVelocity(500, mMaximumVelocity);
//        if (mVelocityTracker != null) {
//            mVelocityTracker.addMovement(ev);
//        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downReach = false;
                upReach = false;
                break;
//            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                handleActionUp(child);
//                int initialVelocity = (int) mVelocityTracker.getYVelocity();
//                if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
//                        flingY(-initialVelocity);
//                startFling(0 * 1.2f, initialVelocity * 1.2f);
//                }


//                releaseVelocityTracker();

                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

//    @Override
//    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
//        mGestureDetector.onTouchEvent(ev);
//
//        return super.onTouchEvent(parent, child, ev);
//    }

    /**
     * @param coordinatorLayout
     * @param child             代表header
     * @param target            代表RecyclerView
     * @param dx
     * @param dy                上滑 dy>0， 下滑dy<0
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {


        if (!isRecovering) {
            if (mTargetView != null && ((dy < 0 && child.getBottom() >= mParentHeight)
                    || (dy > 0 && child.getBottom() > mParentHeight))) {
                scale((ViewGroup) child, target, dy);
//                LogUtils.e("xw", "dy  :" + dy);
//
//
//                LogUtils.e("xw", "child.getBottom() :" + child.getBottom());
//                LogUtils.e("xw", "mParentHeight  :" + mParentHeight);

//                return;
            }
        }

//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);


        //制造滑动视察，使header的移动比手指滑动慢
        float scrollY = dy / 4.0f;

        if (target instanceof NestedLinearLayout) {//处理header滑动
            float finalY = child.getTranslationY() - scrollY;
            if (finalY < getHeaderOffset()) {
                finalY = getHeaderOffset();
            } else if (finalY > 0) {
                finalY = 0;
            }
            LogUtils.e("xw", " NestedLinearLayout finalY :" + finalY);

            child.setTranslationY(finalY);
            consumed[1] = dy;
        } else if (target instanceof RecyclerView) {//处理列表滑动
            RecyclerView list = (RecyclerView) target;
            int pos = ((LinearLayoutManager) list.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

            //header closed状态下，列表上滑再下滑到第一个item全部显示，此时不让CoordinatorLayout整体下滑，
            //手指重新抬起再下滑才可以整体滑动
            if (pos == 0 && pos < lastPosition) {
                downReach = true;
            }

            if (pos == 0 && canScroll(child, scrollY)) {
                //如果列表第一个item全部可见、或者header已展开，则让CoordinatorLayout消费掉事件
                float finalY = child.getTranslationY() - scrollY;
                //header已经closed，整体不能继续上滑，手指抬起重新上滑列表开始滚动
                if (finalY < getHeaderOffset()) {
                    finalY = getHeaderOffset();
                    upReach = true;
                } else if (finalY > 0) {//header已经opened，整体不能继续下滑
                    finalY = 0;
                }
//                LogUtils.e("xw", " RecyclerView finalY :" + finalY);
                LogUtils.e("xw", " child.getTranslationY() :" + child.getTranslationY());
                LogUtils.e("xw", " scrollY :" + scrollY);

                child.setTranslationY(finalY);
                consumed[1] = dy;//让CoordinatorLayout消费掉事件，实现整体滑动
            }
            lastPosition = pos;
        }


    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        LogUtils.e("xw", "onNestedFling velocityY :" + velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        recovery((ViewGroup) child);
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    /**
     * 是否可以整体滑动
     *
     * @return
     */
    private boolean canScroll(View child, float scrollY) {
        if (scrollY > 0 && child.getTranslationY() > getHeaderOffset()) {
            return true;
        }

        if (child.getTranslationY() == getHeaderOffset() && upReach) {
            return true;
        }

        return (scrollY < 0 && !downReach);
    }

    private int getHeaderOffset() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.dp_030_negative);
//        return -;

    }

    private void handleActionUp(View child) {
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        //手指抬起时，header上滑距离超过总距离三分之一，则整体自动上滑到关闭状态
        if (child.getTranslationY() < getHeaderOffset() / 3.0f) {
            scrollToClose(DURATION_SHORT);
        } else {
            scrollToOpen(DURATION_SHORT);
        }
    }

    private void onFlingFinished(View layout) {
        changeState(isClosed(layout) ? STATE_CLOSED : STATE_OPENED);
    }

    /**
     * 直接展开
     */
    public void openHeader() {
        openHeader(DURATION_LONG);
    }

    private void openHeader(int duration) {
        if (isClosed() && mChild.get() != null) {
            if (mFlingRunnable != null) {
                mChild.get().removeCallbacks(mFlingRunnable);
                mFlingRunnable = null;
            }
            scrollToOpen(duration);
        }
    }

    public void closeHeader() {
        closeHeader(DURATION_LONG);
    }

    private void closeHeader(int duration) {
        if (!isClosed() && mChild.get() != null) {
            if (mFlingRunnable != null) {
                mChild.get().removeCallbacks(mFlingRunnable);
                mFlingRunnable = null;
            }
            scrollToClose(duration);
        }
    }

    private boolean isClosed(View child) {
        return child.getTranslationY() == getHeaderOffset();
    }

    public boolean isClosed() {
        return mCurState == STATE_CLOSED;
    }

    private void changeState(int newState) {

        if (mCurState != newState) {
            mCurState = newState;

            if (this.headerStateListener == null) {
                return;
            }

            if (mCurState == STATE_OPENED) {
                this.headerStateListener.onHeaderOpened();
            } else {
                this.headerStateListener.onHeaderClosed();
            }
        }
    }

    private void animScroll(int duration) {
        int curTranslationY = (int) mChild.get().getTranslationY();
//        mOverScroller.startScroll(0, curTranslationY, 0, dy, duration);
    }

    private void scrollToClose(int duration) {
        int curTranslationY = (int) mChild.get().getTranslationY();
        int dy = getHeaderOffset() - curTranslationY;
        mOverScroller.startScroll(0, curTranslationY, 0, dy, duration);
        start();
    }

    private void scrollToOpen(int duration) {
        float curTranslationY = mChild.get().getTranslationY();
        mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
        start();
    }

    private void start() {
        if (mOverScroller.computeScrollOffset()) {
            mFlingRunnable = new FlingRunnable(mParent.get(), mChild.get());
            ViewCompat.postOnAnimation(mChild.get(), mFlingRunnable);
        } else {
            onFlingFinished(mChild.get());
        }
    }

    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout mParent;
        private final View mLayout;

        FlingRunnable(CoordinatorLayout parent, View layout) {
            mParent = parent;
            mLayout = layout;
        }

        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    mLayout.setTranslationY(mOverScroller.getCurrY());
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
                    onFlingFinished(mLayout);
                }
            }
        }
    }

    public void setTabSuspension(boolean tabSuspension) {
        this.tabSuspension = tabSuspension;
    }

    public void setHeaderStateListener(OnHeaderStateListener headerStateListener) {
        this.headerStateListener = headerStateListener;
    }

    public interface OnHeaderStateListener {
        void onHeaderClosed();

        void onHeaderOpened();
    }


    private void initial(ViewGroup child) {
        child.setClipChildren(false);
        mParentHeight = child.getHeight();
        mTargetViewHeight = mTargetView.getHeight();
        if (middleLayout != null) {
            mMiddleHeight = middleLayout.getHeight();
        }
    }

    private void scale(ViewGroup child, View target, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, TARGET_HEIGHT);
        mLastScale = Math.max(1f, 1f + mTotalDy / TARGET_HEIGHT);
        ViewCompat.setScaleX(mTargetView, mLastScale);
        ViewCompat.setScaleY(mTargetView, mLastScale);
        mLastBottom = mParentHeight + (int) (mTargetViewHeight / 2 * (mLastScale - 1));
        child.setBottom(mLastBottom);
        target.setScrollY(0);

        if (middleLayout != null) {
            middleLayout.setTop(mLastBottom - mMiddleHeight);
            middleLayout.setBottom(mLastBottom);
        }

        if (onProgressChangeListener != null) {
            float progress = Math.min((mLastScale - 1) / MAX_REFRESH_LIMIT, 1);//计算0~1的进度
            onProgressChangeListener.onProgressChange(progress, false);
        }

    }

    public interface onProgressChangeListener {
        /**
         * 范围 0~1
         *
         * @param progress
         * @param isRelease 是否是释放状态
         */
        void onProgressChange(float progress, boolean isRelease);
    }

    public void setOnProgressChangeListener(AppBarLayoutOverScrollViewBehavior.onProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    AppBarLayoutOverScrollViewBehavior.onProgressChangeListener onProgressChangeListener;

    private void recovery(final ViewGroup child) {
        if (isRecovering) {
            return;
        }

        if (mTotalDy > 0) {

            isRecovering = true;
            mTotalDy = 0;
            if (isAnimate) {
                ValueAnimator anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200);
                anim.addUpdateListener(
                        new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {

                                float value = (float) animation.getAnimatedValue();
                                ViewCompat.setScaleX(mTargetView, value);
                                ViewCompat.setScaleY(mTargetView, value);
                                child.setBottom((int) (mLastBottom - (mLastBottom - mParentHeight) * animation.getAnimatedFraction()));
                                if (middleLayout != null) {
                                    middleLayout.setTop((int) (mLastBottom -
                                            (mLastBottom - mParentHeight) * animation.getAnimatedFraction() - mMiddleHeight));
                                }

                                if (onProgressChangeListener != null) {
                                    float progress = Math.min((value - 1) / MAX_REFRESH_LIMIT, 1);//计算0~1的进度
                                    onProgressChangeListener.onProgressChange(progress, true);
                                }
                            }
                        }
                );
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isRecovering = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
                anim.start();
            } else {
                ViewCompat.setScaleX(mTargetView, 1f);
                ViewCompat.setScaleY(mTargetView, 1f);
                child.setBottom(mParentHeight);
                if (middleLayout != null) {
                    middleLayout.setTop(mParentHeight - mMiddleHeight);
                }
                isRecovering = false;

                if (onProgressChangeListener != null) {
                    onProgressChangeListener.onProgressChange(0, true);
                }
            }
        }
    }


    /**********************************滑动惯性*******************************/

    GestureDetector mGestureDetector;

    private float mPhysicalCoeff;
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    private final static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private final static float INFLEXION = 0.35f;

    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private ValueAnimator mValueAnimator = null;

    private void stopFling() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    private void startFling(final float velocityX, final float velocityY) {
        stopFling();

        final float fx = (velocityX < 0 ? 1 : -1);
        final float fy = (velocityY < 0 ? 1 : -1);

        final float velocity = (float) Math.hypot(velocityX, velocityY);
        final long duration = getSplineFlingDuration(velocity);

        mValueAnimator = ValueAnimator.ofFloat(1f, 0);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(duration);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private Double mLastDisX = Double.NaN;
            private Double mLastDisY = Double.NaN;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                double curDisX = getSplineFlingDistance(value * velocityX) * fx;
                double curDisY = getSplineFlingDistance(value * velocityY) * fy;

                if (mLastDisX.isNaN() || mLastDisY.isNaN()) {
                    mLastDisX = curDisX;
                    mLastDisY = curDisY;
                    return;
                }

                int dx = (int) (curDisX - mLastDisX);
                int offsetY = (int) (curDisY - mLastDisY);


                int curTranslationY = (int) mChild.get().getTranslationY();
                int dy = mChild.get().getScrollY() - offsetY;

//                if (dy >= 0) {
//                    dy = 0;
//                }

                LogUtils.e("xw", "dy:" + dy);
                LogUtils.e("xw", "curTranslationY:" + curTranslationY);
                mChild.get().scrollBy(0, dy);
//                mOverScroller.sc(0, curTranslationY, 0, dy, DURATION_SHORT);

//                if (dy)
//
//
//                mOverScroller.startScroll(0, curTranslationY, 0, dy, DURATION_SHORT);
//                start();
//
//                mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, DURATION_SHORT);


                mLastDisX = curDisX;
                mLastDisY = curDisY;
            }
        });

        mValueAnimator.start();
    }

    private double getSplineDeceleration(float velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    private int getSplineFlingDuration(float velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return (int) (1000.0 * Math.exp(l / decelMinusOne));
    }

    private double getSplineFlingDistance(float velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * mPhysicalCoeff * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }


    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocitX, float velocityY) {
            startFling(velocitX * 1.2f, velocityY * 1.2f);

            return true;
        }


    };


    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    /**
     * 初始化 速度追踪器
     */
    private void obtainVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    /**
     * 释放 速度追踪器
     */
    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


}
