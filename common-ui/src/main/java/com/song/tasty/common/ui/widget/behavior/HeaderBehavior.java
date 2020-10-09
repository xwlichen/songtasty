package com.song.tasty.common.ui.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.ui.LogUtils;
import com.song.tasty.common.ui.R;
import com.song.tasty.common.ui.widget.NestedLinearLayout;

import java.lang.ref.WeakReference;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
    private OnHeaderStateListener mHeaderStateListener;

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

    /**
     * 滑动的最大速度
     */
    private int maximumVelocity;
    /**
     * 滑动的最小速度
     */
    private int minimumVelocity;

    /**
     * 滑动线程
     */
//    private HeaderFlingRunnable fling;


    /**
     * 滑动速度追踪
     */
    private VelocityTracker velocityTracker;

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
        maximumVelocity = ViewConfiguration.get(mContext).getScaledMaximumFlingVelocity();
        minimumVelocity = ViewConfiguration.get(mContext).getScaledMinimumFlingVelocity();
//        fling = new FlingRunnable(mContext);
    }

    //可以重写这个方法对子View 进行重新布局
    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        mParent = new WeakReference<>(parent);
        mChild = new WeakReference<>(child);
    }


//    @Override
//    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int axes) {
//
////    @Override
////    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//
//        if (tabSuspension) {
//            return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !isClosed();
//        }
//        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//    }


    /**
     * 当coordinatorLayout 的子View试图开始嵌套滑动的时候被调用。当返回值为true的时候表明
     * coordinatorLayout 充当nested scroll parent 处理这次滑动，需要注意的是只有当返回值为true
     * 的时候，Behavior 才能收到后面的一些nested scroll 事件回调（如：onNestedPreScroll、onNestedScroll等）
     * 这个方法有个重要的参数nestedScrollAxes，表明处理的滑动的方向。
     *
     * @param coordinatorLayout 和Behavior 绑定的View的父CoordinatorLayout
     * @param child             和Behavior 绑定的View
     * @param directTargetChild
     * @param target
     * @param axes              嵌套滑动 应用的滑动方向，看 {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                          {@link ViewCompat#SCROLL_AXIS_VERTICAL}
     * @return
     */

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        if (tabSuspension) {
            return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !isClosed();
        }

        boolean flag = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        if (flag) {
            LogUtils.e("xw", "flag true");
        } else {
            LogUtils.e("xw", "flag false");

        }

        return true;
    }


    /**
     * 用户松开手指并且会发生惯性动作之前调用，参数提供了速度信息，可以根据这些速度信息
     * 决定最终状态，比如滚动Header，是让Header处于展开状态还是折叠状态。返回true 表
     * 示消费了fling.
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX         x 方向的速度
     * @param velocityY         y 方向的速度
     * @return
     */
    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        lastPosition = -1;
        boolean flag = !isClosed();
//        if (flag) {
//            LogUtils.e("xw", "flag true");
//        }else{
//            LogUtils.e("xw", "flag false");
//
//        }
        LogUtils.e("xw", "onNestedPreFling velocityY:" + velocityY);
//        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        return true;
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        LogUtils.e("xw", "onNestedFling velocityY："+velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, final View child, MotionEvent ev) {
        LogUtils.e("xw", "onInterceptTouchEvent");
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downReach = false;
                upReach = false;
                mOverScroller.abortAnimation();
                break;
//            case MotionEvent.ACTION_UP:
////                mParent = new WeakReference<CoordinatorLayout>(parent);
////                handleActionUp(child);
//                LogUtils.e("xw", "onInterceptTouchEvent ACTION_UP");
//                // 计算当前速度， 1000表示每秒像素数等
//                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
//
//                // 获取横向速度
//                int velocityY = (int) velocityTracker.getYVelocity();
//
//                // 速度要大于最小的速度值，才开始滑动
//                if (Math.abs(velocityY) > minimumVelocity) {
//
//                    int initY = child.getScrollY();
//
//                    int maxY = (int) (child.getHeight());
//
////                    if (maxX > 0) {
//                        fling(initY,velocityY,0,maxY);
////                    }
//                }
//
//                if (velocityTracker != null) {
//                    velocityTracker.recycle();
//                    velocityTracker = null;
//                }
//                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
//        return true;
    }


    /**
     * @param coordinatorLayout
     * @param child             代表header
     * @param target            代表RecyclerView
     * @param dx
     * @param dy                上滑 dy>0， 下滑dy<0
     *                          //     * @param consumed
     */
//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

    /**
     * 嵌套滚动发生之前被调用
     * 在nested scroll child 消费掉自己的滚动距离之前，嵌套滚动每次被nested scroll child
     * 更新都会调用onNestedPreScroll。注意有个重要的参数consumed，可以修改这个数组表示你消费
     * 了多少距离。假设用户滑动了100px,child 做了90px 的位移，你需要把 consumed［1］的值改成90，
     * 这样coordinatorLayout就能知道只处理剩下的10px的滚动。
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx                用户水平方向的滚动距离
     * @param dy                用户竖直方向的滚动距离
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        LogUtils.e("xw","onNestedPreScroll");
//        LogUtils.e("xw","type:"+type);

        //制造滑动视察，使header的移动比手指滑动慢
        float scrollY = dy / 4.0f;

        if (target instanceof NestedLinearLayout) {//处理header滑动
            float finalY = child.getTranslationY() - scrollY;
            if (finalY < getHeaderOffset()) {
                finalY = getHeaderOffset();
            } else if (finalY > 0) {
                finalY = 0;
            }
            child.setTranslationY(finalY);
            consumed[1] = dy;
        } else if (target instanceof ScrollView) {//处理header滑动
            float finalY = child.getTranslationY() - scrollY;
            if (finalY < getHeaderOffset()) {
                finalY = getHeaderOffset();
            } else if (finalY > 0) {
                finalY = 0;
            }
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
                child.setTranslationY(finalY);
                consumed[1] = dy;//让CoordinatorLayout消费掉事件，实现整体滑动
            }
//            if (pos==0){
//                consumed[0] = dy;
//            }
            lastPosition = pos;
        }
    }

    /**
     * 进行嵌套滚动时被调用
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed        target 已经消费的x方向的距离
     * @param dyConsumed        target 已经消费的y方向的距离
     * @param dxUnconsumed      x 方向剩下的滚动距离
     * @param dyUnconsumed      y 方向剩下的滚动距离
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }


    /**
     * onStartNestedScroll返回true才会触发这个方法，接受滚动处理后回调，可以在这个
     * 方法里做一些准备工作，如一些状态的重置等。
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {
        LogUtils.e("xw","onTouchEvent");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mParent = new WeakReference<CoordinatorLayout>(parent);
                LogUtils.e("xw", "ACTION_UP");
//                handleActionUp(child);
                break;
            default:
                break;
        }
//        return super.onTouchEvent(parent, child, ev);

        return true;
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

        if (scrollY < 0 && !downReach) {
            return true;
        }

        return false;
    }

    private int getHeaderOffset() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.dp_030_negative);
    }

    private void onFlingFinished(View layout) {
        changeState(isClosed(layout) ? STATE_CLOSED : STATE_OPENED);
    }

    private void handleActionUp(View child) {
        mChild = new WeakReference<View>(child);
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

            if (mHeaderStateListener == null) {
                return;
            }

            if (mCurState == STATE_OPENED) {
                mHeaderStateListener.onHeaderOpened();
            } else {
                mHeaderStateListener.onHeaderClosed();
            }
        }
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

    private int mInitY;
    private int mMinY;
    private int mMaxY;
    private int mVelocityY;
    private void fling(int initY,
                       int velocityY,
                       int minY,
                       int maxY ){
        this.mInitY= initY;
        this.mVelocityY= velocityY;
        this.mMinY = minY;
        this.mMaxY = maxY;
        float curTranslationY = mChild.get().getTranslationY();
        mOverScroller.fling(0, (int) curTranslationY, 0,velocityY,0,0,0,maxY);
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
        mHeaderStateListener = headerStateListener;
    }

    public interface OnHeaderStateListener {
        void onHeaderClosed();

        void onHeaderOpened();
    }




    /**
     * 滚动线程
     */
//    private class HeaderFlingRunnable implements Runnable {
//
////        private Scroller mScroller;
//
//        private int mInitX;
//        private int mMinX;
//        private int mMaxX;
//        private int mVelocityX;
//        private final CoordinatorLayout mParent;
//        private final View mLayout;
//
//        HeaderFlingRunnable(CoordinatorLayout parent, View layout) {
////            this.mScroller = new Scroller(context, null, false);
//            mParent = parent;
//            mLayout = layout;
//        }
//
//        HeaderFlingRunnable(Context context) {
////            this.mScroller = new Scroller(context, null, false);
//        }
//
//        void start(int initX,
//                   int velocityX,
//                   int minX,
//                   int maxX) {
//            this.mInitX = initX;
//            this.mVelocityX = velocityX;
//            this.mMinX = minX;
//            this.mMaxX = maxX;
//
//            // 先停止上一次的滚动
//            if (!mScroller.isFinished()) {
//                mScroller.abortAnimation();
//            }
//
//            // 开始 fling
//            mScroller.fling(initX, 0, velocityX,
//                    0, 0, maxX, 0, 0);
//            post(this);
//        }
//
//        @Override
//        public void run() {
//            if (mLayout==null||mOverScroller==null){
//                return;
//            }
//            if (mLayout != null && mOverScroller != null) {
//                if (mOverScroller.computeScrollOffset()) {
//                    mLayout.setTranslationY(mOverScroller.getCurrY());
//                    ViewCompat.postOnAnimation(mLayout, this);
//                } else {
//                    onFlingFinished(mLayout);
//                }
//            }
//
//
//            // 如果已经结束，就不再进行
//            if (!mOverScroller.computeScrollOffset()) {
//                return;
//            }
//
//            // 计算偏移量
//            int currX = mOverScroller.getCurrX();
//            int diffX = mInitX - currX;
//
//            Log.i("xw", "run: [currX: " + currX + "]\n"
//                    + "[diffX: " + diffX + "]\n"
//                    + "[initX: " + mInitX + "]\n"
//                    + "[minX: " + mMinX + "]\n"
//                    + "[maxX: " + mMaxX + "]\n"
//                    + "[velocityX: " + mVelocityX + "]\n"
//            );
//
//            // 用于记录是否超出边界，如果已经超出边界，则不再进行回调，即使滚动还没有完成
//            boolean isEnd = false;
//
//            if (diffX != 0) {
//
//                // 超出右边界，进行修正
//                if (mLayout.getScrollX() + diffX >= mCanvasWidth - mViewWidth) {
//                    diffX = (int) (mCanvasWidth - mViewWidth - getScrollX());
//                    isEnd = true;
//                }
//
//                // 超出左边界，进行修正
//                if (getScrollX() <= 0) {
//                    diffX = -getScrollX();
//                    isEnd = true;
//                }
//
//                if (!mScroller.isFinished()) {
//                    scrollBy(diffX, 0);
//                }
//                mInitX = currX;
//            }
//
//            if (!isEnd) {
//                post(this);
//            }
//        }
//
//        /**
//         * 进行停止
//         */
//        void stop() {
//            if (!mOverScroller.isFinished()) {
//                mOverScroller.abortAnimation();
//            }
//        }
//    }
}
