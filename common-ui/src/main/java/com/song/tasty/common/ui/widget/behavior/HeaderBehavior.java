package com.song.tasty.common.ui.widget.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private ImageView mImageView;
    private int mAppbarHeight;//记录AppbarLayout原始高度
    private int mImageViewHeight;//记录ImageView原始高度

    private static final float MAX_ZOOM_HEIGHT = 500;//放大最大高度
    private float mTotalDy;//手指在Y轴滑动的总距离
    private float mScaleValue;//图片缩放比例
    private int mLastBottom;//Appbar的变化高度

    private boolean isAnimate;//是否做动画标志


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
    }

    /**
     * 进行初始化操作，在这里获取到ImageView的引用，和Appbar的原始高度
     *
     * @param view
     */
    private void initView(View view) {
        ((ViewGroup) view).setClipChildren(false);
        mAppbarHeight = view.getHeight();
        mImageView = (ImageView) view.findViewById(R.id.ivheader);
        if (mImageView != null) {
            mImageViewHeight = mImageView.getHeight();
        }
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        mParent = new WeakReference<>(parent);
        mChild = new WeakReference<>(child);
        initView(child);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes) {
        isAnimate = true;
        if (tabSuspension) {
            return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && !isClosed();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    /**
     * 在这里做具体的滑动处理
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {


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
            lastPosition = pos;
        }

        if (mImageView != null && child.getBottom() >= mAppbarHeight && dy < 0 && type == ViewCompat.TYPE_TOUCH) {
            zoomHeaderImageView(child, dy);
        } else {
            if (mImageView != null && child.getBottom() > mAppbarHeight && dy > 0 && type == ViewCompat.TYPE_TOUCH) {
                consumed[1] = dy;
                zoomHeaderImageView(child, dy);
            } else {
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
                }

            }
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if (mImageView != null && child.getBottom() >= mAppbarHeight && dyConsumed < 0 && type == ViewCompat.TYPE_TOUCH) {
            zoomHeaderImageView(child, dyConsumed);
        } else {
            if (mImageView != null && child.getBottom() > mAppbarHeight && dyConsumed > 0 && type == ViewCompat.TYPE_TOUCH) {
//                consumed[1] = dy;
                zoomHeaderImageView(child, dyConsumed);
            } else {
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

                }

            }
        }
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        lastPosition = -1;
        if (velocityY > 100) {
            isAnimate = false;
        }
        return !isClosed();
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        recovery(child);
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    /**
     * @param coordinatorLayout
     * @param child             代表header
     * @param target            代表RecyclerView
     * @param dx
     * @param dy                上滑 dy>0， 下滑dy<0
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

//        //制造滑动视察，使header的移动比手指滑动慢
//        float scrollY = dy / 4.0f;
//
//        if (target instanceof NestedLinearLayout) {//处理header滑动
//            float finalY = child.getTranslationY() - scrollY;
//            if (finalY < getHeaderOffset()) {
//                finalY = getHeaderOffset();
//            } else if (finalY > 0) {
//                finalY = 0;
//            }
//            child.setTranslationY(finalY);
//            consumed[1] = dy;
//        } else if (target instanceof RecyclerView) {//处理列表滑动
//            RecyclerView list = (RecyclerView) target;
//            int pos = ((LinearLayoutManager) list.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
//
//            //header closed状态下，列表上滑再下滑到第一个item全部显示，此时不让CoordinatorLayout整体下滑，
//            //手指重新抬起再下滑才可以整体滑动
//            if (pos == 0 && pos < lastPosition) {
//                downReach = true;
//            }
//
//            if (pos == 0 && canScroll(child, scrollY)) {
//                //如果列表第一个item全部可见、或者header已展开，则让CoordinatorLayout消费掉事件
//                float finalY = child.getTranslationY() - scrollY;
//                //header已经closed，整体不能继续上滑，手指抬起重新上滑列表开始滚动
//                if (finalY < getHeaderOffset()) {
//                    finalY = getHeaderOffset();
//                    upReach = true;
//                } else if (finalY > 0) {//header已经opened，整体不能继续下滑
//                    finalY = 0;
//                }
//                child.setTranslationY(finalY);
//                consumed[1] = dy;//让CoordinatorLayout消费掉事件，实现整体滑动
//            }
//            lastPosition = pos;
//        }
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull final View child, @NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downReach = false;
                upReach = false;
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(child);
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
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


    /**
     * 对ImageView进行缩放处理，对AppbarLayout进行高度的设置
     *
     * @param child
     * @param dy
     */
    private void zoomHeaderImageView(View child, int dy) {
        mTotalDy += -dy;
        mTotalDy = Math.min(mTotalDy, MAX_ZOOM_HEIGHT);
        mScaleValue = Math.max(1f, 1f + mTotalDy / MAX_ZOOM_HEIGHT);
        ViewCompat.setScaleX(mImageView, mScaleValue);
        ViewCompat.setScaleY(mImageView, mScaleValue);
        mLastBottom = mAppbarHeight + (int) (mImageViewHeight / 2 * (mScaleValue - 1));
        child.setBottom(mLastBottom);
    }


    ValueAnimator valueAnimator;

    /**
     * 通过属性动画的形式，恢复AppbarLayout、ImageView的原始状态
     *
     * @param view
     */
    private void recovery(final View view) {
        if (mTotalDy > 0) {
            mTotalDy = 0;
            if (isAnimate) {
                valueAnimator = ValueAnimator.ofFloat(mScaleValue, 1f).setDuration(220);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        ViewCompat.setScaleX(mImageView, value);
                        ViewCompat.setScaleY(mImageView, value);
                        view.setBottom((int) (mLastBottom - (mLastBottom - mAppbarHeight) * animation.getAnimatedFraction()));
                    }
                });
                valueAnimator.start();
            } else {
                ViewCompat.setScaleX(mImageView, 1f);
                ViewCompat.setScaleY(mImageView, 1f);
                view.setBottom(mAppbarHeight);
            }
        }
    }
}
