package com.song.tasty.demo.coordinate;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

import java.util.List;

/**
 * Created by liyongan on 19/3/14.
 */

public class HeaderBackgroundBehavior extends ViewOffsetBehavior<View> {
    private final Rect mTempRect1 = new Rect();
    private int mOriginTransY = -1;

    public HeaderBackgroundBehavior() {
    }

    public HeaderBackgroundBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        return behavior != null && behavior instanceof HeaderBehavior;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        ViewCompat.offsetTopAndBottom(child, dependency.getBottom() - child.getBottom());
        for (int i = 0, c = ((ViewGroup) child).getChildCount(); i < c; i++) {
            final View view = ((ViewGroup) child).getChildAt(i);
            final int height = view.getMeasuredHeight();
            final float scale = MathUtils.clamp((dependency.getTop() + height * 1f) / height, 1, Integer.MAX_VALUE);

            view.setTranslationY(-dependency.getTop() / 2f + mOriginTransY);
            view.setScaleX(scale);
            view.setScaleY(scale);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }

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
            child.getLayoutParams().height = header.getMeasuredHeight() + overScrollOffset;
            if (child instanceof ViewGroup) {
                if (mOriginTransY == -1) {
                    mOriginTransY = overScrollOffset;
                    for (int i = 0, c = ((ViewGroup) child).getChildCount(); i < c; i++) {
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
            mTempRect1.set(parent.getPaddingLeft() + lp.leftMargin,
                    header.getBottom()- lp.bottomMargin - child.getMeasuredHeight() + lp.topMargin,
                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                    header.getBottom()- lp.bottomMargin);

            child.layout(mTempRect1.left, mTempRect1.top, mTempRect1.right, mTempRect1.bottom);
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
