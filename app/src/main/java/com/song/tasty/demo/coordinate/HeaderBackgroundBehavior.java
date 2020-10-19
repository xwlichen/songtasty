package com.song.tasty.demo.coordinate;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

import com.smart.utils.LogUtils;

import java.util.List;

/**
 * Created by liyongan on 19/3/14.
 */

public class HeaderBackgroundBehavior extends ViewOffsetBehavior<View> {
//    private final Rect mTempRect1 = new Rect();
    private int mOriginTransY = -1;

    public HeaderBackgroundBehavior() {
    }

    public HeaderBackgroundBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 判断依赖关系，根据CoordinatorLayout的子view遍历，最后找到behavior是HeaderBehavior的view作为依赖
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        return behavior != null && behavior instanceof HeaderBehavior;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        LogUtils.e("xw","offset:"+(dependency.getBottom() - child.getBottom()));
        ViewCompat.offsetTopAndBottom(child, dependency.getBottom() - child.getBottom());
        for (int i = 0, c = ((ViewGroup) child).getChildCount(); i < c; i++) {
            final View view = ((ViewGroup) child).getChildAt(i);
            final int height = view.getMeasuredHeight();
            final float scale = MathUtils.clamp((dependency.getTop() + height * 1f) / height, 1, Integer.MAX_VALUE);

//            LogUtils.e("xw","dependency top:"+dependency.getTop()+ ",dependency bottom :"+dependency.getBottom()
//                    +",child top:"+child.getTop()+",child bottom:"+child.getBottom());

            view.setTranslationY(-dependency.getTop() / 2f + mOriginTransY);
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }


    /**
     * 给绑定这个behavior的控件设置大小
     * @param parent
     * @param child
     * @param parentWidthMeasureSpec
     * @param widthUsed
     * @param parentHeightMeasureSpec
     * @param heightUsed
     * @return
     */
    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child,
                                  int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec,
                                  int heightUsed) {
        final List<View> dependencies = parent.getDependencies(child);
        final View header = findFirstDependency(dependencies);
        if (header != null) {
            HeaderBehavior headerBehavior = (HeaderBehavior) ((CoordinatorLayout.LayoutParams) header.getLayoutParams()).getBehavior();
            if (headerBehavior == null) {
                return false;
            }
            final int overScrollOffset = headerBehavior.getOverScrollOffset(header);
            //给绑定这个behavior的控件设置大小=依赖的控件+最大的pull量

            child.getLayoutParams().height = header.getMeasuredHeight() + overScrollOffset;
//            LogUtils.e("xw","height:"+child.getLayoutParams().height);
            if (child instanceof ViewGroup) {
                if (mOriginTransY == -1) {
                    mOriginTransY = overScrollOffset;
                    for (int i = 0, c = ((ViewGroup) child).getChildCount(); i < c; i++) {
                        //子view设置跟headerbehavior绑定的控件一样高度
                        View view = ((ViewGroup) child).getChildAt(i);
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = header.getMeasuredHeight();
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        view.setTranslationY(overScrollOffset);
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected void layoutChild(final CoordinatorLayout parent, final View child,
                               final int layoutDirection) {
        final List<View> dependencies = parent.getDependencies(child);
        final View header = findFirstDependency(dependencies);
        if (header != null) {
            final CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//            mTempRect1.set(parent.getPaddingLeft() + lp.leftMargin,
//                    header.getBottom()- lp.bottomMargin - child.getMeasuredHeight() + lp.topMargin,
//                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
//                    header.getBottom()- lp.bottomMargin);

            int left=parent.getPaddingLeft() + lp.leftMargin;
            int top=header.getBottom()- lp.bottomMargin - child.getMeasuredHeight() + lp.topMargin;
            int right=parent.getWidth() - parent.getPaddingRight() - lp.rightMargin;
            int bottom=header.getBottom()- lp.bottomMargin;
//            LogUtils.e("xw","left:"+left+",top:"+top+",right:"+right+",bottom:"+bottom);
            child.layout(left, top, right, bottom);
        } else {
            super.layoutChild(parent, child, layoutDirection);
        }
    }

    View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).getBehavior();
            if (behavior != null && behavior instanceof HeaderBehavior) {
                return view;
            }
        }
        return null;
    }
}
