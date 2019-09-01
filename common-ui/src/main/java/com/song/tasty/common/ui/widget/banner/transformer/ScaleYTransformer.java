package com.song.tasty.common.ui.widget.banner.transformer;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author lichen
 * @date ：2019-08-31 23:40
 * @email : 196003945@qq.com
 * @description :
 */
public class ScaleYTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9F;

    @Override
    public void transformPage(View page, float position) {

        if (position < -1) {
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) {
            //
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            page.setScaleY(scale);
            /*page.setScaleX(scale);

            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/

        } else {
            page.setScaleY(MIN_SCALE);
        }
    }

}
