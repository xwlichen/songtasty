package com.song.tasty.common.app.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.smart.ui.widget.image.SMUIImageView;

/**
 * @date : 2019-08-20 17:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicCoverView extends SMUIImageView {
    ObjectAnimator rotationAnimator;


    public MusicCoverView(Context context) {
        this(context, null);
    }

    public MusicCoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicCoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }


    private void init(Context context) {
        if (rotationAnimator == null) {
            rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360f);
        }

        rotationAnimator.setDuration(5000);
        rotationAnimator.setRepeatMode(ValueAnimator.RESTART);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimator.setInterpolator(new LinearInterpolator());

        rotationAnimator.start();

    }


}
