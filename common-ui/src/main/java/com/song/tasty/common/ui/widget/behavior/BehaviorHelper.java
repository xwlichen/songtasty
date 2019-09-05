package com.song.tasty.common.ui.widget.behavior;

import android.view.View;

import androidx.core.view.ViewCompat;

/**
 * @date : 2019-09-05 17:19
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class BehaviorHelper {
    private final View view;

    private int layoutTop;
    private int layoutLeft;
    private int offsetTop;
    private int offsetLeft;

    public BehaviorHelper(View view) {
        this.view = view;
    }

    public void onViewLayout() {
        // Now grab the intended top
        layoutTop = this.view.getTop();
        layoutLeft = this.view.getLeft();

        // And offset it as needed
        updateOffsets();
    }

    private void updateOffsets() {
        ViewCompat.offsetTopAndBottom(this.view, offsetTop - (this.view.getTop() - layoutTop));
        ViewCompat.offsetLeftAndRight(this.view, offsetLeft - (this.view.getLeft() - layoutLeft));
    }

    /**
     * Set the top and bottom offset for this
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setTopAndBottomOffset(int offset) {
        if (offsetTop != offset) {
            offsetTop = offset;
            updateOffsets();
            return true;
        }
        return false;
    }

    /**
     * Set the left and right offset for this
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    public boolean setLeftAndRightOffset(int offset) {
        if (offsetLeft != offset) {
            offsetLeft = offset;
            updateOffsets();
            return true;
        }
        return false;
    }

    public int getTopAndBottomOffset() {
        return offsetTop;
    }

    public int getLeftAndRightOffset() {
        return offsetLeft;
    }

    public int getLayoutTop() {
        return layoutTop;
    }

    public int getLayoutLeft() {
        return layoutLeft;
    }
}
