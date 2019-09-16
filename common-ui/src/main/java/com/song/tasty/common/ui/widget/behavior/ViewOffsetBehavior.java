package com.song.tasty.common.ui.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @date : 2019-09-05 17:18
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private ViewOffsetHelper behaviorHelper;

    private int tempTopBottomOffset = 0;
    private int tempLeftRightOffset = 0;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        // First let lay the child out
        layoutChild(parent, child, layoutDirection);

        if (behaviorHelper == null) {
            behaviorHelper = new ViewOffsetHelper(child);
        }
        behaviorHelper.onViewLayout();

        if (tempTopBottomOffset != 0) {
            behaviorHelper.setTopAndBottomOffset(tempTopBottomOffset);
            tempTopBottomOffset = 0;
        }
        if (tempLeftRightOffset != 0) {
            behaviorHelper.setLeftAndRightOffset(tempLeftRightOffset);
            tempLeftRightOffset = 0;
        }

        return true;
    }

    protected void layoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        // Let the parent lay it out by default
        parent.onLayoutChild(child, layoutDirection);
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (behaviorHelper != null) {
            return behaviorHelper.setTopAndBottomOffset(offset);
        } else {
            tempTopBottomOffset = offset;
        }
        return false;
    }

    public boolean setLeftAndRightOffset(int offset) {
        if (behaviorHelper != null) {
            return behaviorHelper.setLeftAndRightOffset(offset);
        } else {
            tempLeftRightOffset = offset;
        }
        return false;
    }

    public int getTopAndBottomOffset() {
        return behaviorHelper != null ? behaviorHelper.getTopAndBottomOffset() : 0;
    }

    public int getLeftAndRightOffset() {
        return behaviorHelper != null ? behaviorHelper.getLeftAndRightOffset() : 0;
    }
}
