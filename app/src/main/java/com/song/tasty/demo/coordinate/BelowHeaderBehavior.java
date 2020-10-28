package com.song.tasty.demo.coordinate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.smart.utils.LogUtils;

import java.util.List;

/**
 * Created by liyongan on 19/3/13.
 */

public class BelowHeaderBehavior extends HeaderScrollingViewBehavior {

    public BelowHeaderBehavior() {
    }

    public BelowHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        return behavior != null && behavior instanceof HeaderBehavior;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        LogUtils.e("below","child:"+child.getClass());

        ViewCompat.offsetTopAndBottom(child, dependency.getBottom() - child.getTop());
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
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

    @Override
    int getScrollRange(View v) {
        HeaderBehavior headerBehavior = (HeaderBehavior) ((CoordinatorLayout.LayoutParams) v.getLayoutParams()).getBehavior();
        return super.getScrollRange(v) - (headerBehavior != null ? headerBehavior.getStickSectionHeight() : 0);
    }
}
