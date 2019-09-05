package com.song.tasty.common.ui.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.song.tasty.common.ui.R;

import java.util.List;

/**
 * @date : 2019-09-05 17:47
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ContentBehavior extends HeaderScrollingViewBehavior {
    private Context mContext;

    public ContentBehavior() {
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float contentScrollY = dependency.getTranslationY() / getHeaderOffset() * (dependency.getHeight() - getFinalHeight());
        float y = dependency.getHeight() - contentScrollY;
        child.setY(y);
        return true;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY((-dependency.getTranslationY() / getHeaderOffset() * getScrollRange(dependency)));

    }


    @Override
    protected View findFirstDependency(List<View> views) {
        for (int i = 0, z = views.size(); i < z; i++) {
            View view = views.get(i);
            if (isDependOn(view)) {
                return view;
            }
        }
        return null;
    }

    /**
     * 计算content的最大向上偏移距离
     *
     * @param v
     * @return
     */
    @Override
    protected int getScrollRange(View v) {
        if (isDependOn(v)) {
            return Math.max(0, v.getMeasuredHeight() - getFinalHeight());
        } else {
            return super.getScrollRange(v);
        }
    }

    private int getHeaderOffset() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.dp_030_negative);
    }

    private int getFinalHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.dp_045)
                + mContext.getResources().getDimensionPixelOffset(R.dimen.dp_045);
    }


    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.header;
    }


}
