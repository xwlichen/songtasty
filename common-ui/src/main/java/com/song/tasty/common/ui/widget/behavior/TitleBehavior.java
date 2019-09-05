package com.song.tasty.common.ui.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.song.tasty.common.ui.R;

/**
 * @date : 2019-09-05 17:41
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

    private Context mContext;

    public TitleBehavior() {

    }

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float y = -(1 - dependency.getTranslationY() / getHeaderOffset()) * getTitleHeight();
        child.setY(y);
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
