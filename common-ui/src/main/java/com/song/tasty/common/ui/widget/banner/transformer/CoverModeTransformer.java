package com.song.tasty.common.ui.widget.banner.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author lichen
 * @date ：2019-08-31 23:37
 * @email : 196003945@qq.com
 * @description :
 */
public class CoverModeTransformer implements ViewPager.PageTransformer {

    private float reduceX = 0.0f;
    private float itemWidth = 0;
    private float offsetPosition = 0f;
    private int coverWidth;
    private float scaleMax = 1.0f;
    private float scaleMin = 0.9f;
    private ViewPager viewPager;

    public CoverModeTransformer(ViewPager pager) {
        this.viewPager = pager;
    }

    @Override
    public void transformPage(View view, float position) {
        if (offsetPosition == 0f) {
            float paddingLeft = viewPager.getPaddingLeft();
            float paddingRight = viewPager.getPaddingRight();
            float width = viewPager.getMeasuredWidth();
            offsetPosition = paddingLeft / (width - paddingLeft - paddingRight);
        }
        float currentPos = position - offsetPosition;
        if (itemWidth == 0) {
            itemWidth = view.getWidth();
            //由于左右边的缩小而减小的x的大小的一半
            reduceX = (2.0f - scaleMax - scaleMin) * itemWidth / 2.0f;
        }
        if (currentPos <= -1.0f) {
            view.setTranslationX(reduceX + coverWidth);
            view.setScaleX(scaleMin);
            view.setScaleY(scaleMin);
        } else if (currentPos <= 1.0) {
            float scale = (scaleMax - scaleMin) * Math.abs(1.0f - Math.abs(currentPos));
            float translationX = currentPos * -reduceX;
            if (currentPos <= -0.5) {//两个view中间的临界，这时两个view在同一层，左侧View需要往X轴正方向移动覆盖的值()
                view.setTranslationX(translationX + coverWidth * Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f);
            } else if (currentPos <= 0.0f) {
                view.setTranslationX(translationX);
            } else if (currentPos >= 0.5) {//两个view中间的临界，这时两个view在同一层
                view.setTranslationX(translationX - coverWidth * Math.abs(Math.abs(currentPos) - 0.5f) / 0.5f);
            } else {
                view.setTranslationX(translationX);
            }
            view.setScaleX(scale + scaleMin);
            view.setScaleY(scale + scaleMin);
        } else {
            view.setScaleX(scaleMin);
            view.setScaleY(scaleMin);
            view.setTranslationX(-reduceX - coverWidth);
        }

    }
}
