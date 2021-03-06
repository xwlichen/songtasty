package com.song.tasty.common.ui.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.song.tasty.common.ui.R;

/**
 * @date : 2019-09-05 17:46
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class TabBehavior extends CoordinatorLayout.Behavior<View> {

    private Context mContext;

    public TabBehavior() {
    }

    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    /**
     * * 表示是否给应用了Behavior 的View 指定一个依赖的布局，通常，当依赖的View 布局发生变化时
     * * 不管被被依赖View 的顺序怎样，被依赖的View也会重新布局
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    /**
     * 当被依赖的View 状态（如：位置、大小）发生变化时，这个方法被调用
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float tabScrollY = (dependency.getTranslationY() / getHeaderOffset()) * (dependency.getHeight() - 2 * getTitleHeight());
        float y = dependency.getHeight() - tabScrollY - getTitleHeight();
        child.setY(y);
//        float tabScrollY = dependency.getTranslationY() / getHeaderOffset() * (dependency.getHeight() - getTitleHeight());
//        float y = dependency.getHeight() - tabScrollY;
//        child.setY(y);
        return true;
    }

    private int getHeaderOffset() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.dp_030_negative);
    }

    private int getTitleHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.dp_045);
    }


    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.header;
    }
}
