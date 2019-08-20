package com.song.tasty.common.app.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.smart.ui.layout.SMUILayoutHelper;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @date : 2019-08-20 17:24
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicCoverView extends AppCompatImageView {
    ObjectAnimator rotationAnimator;
    private SMUILayoutHelper smuiLayoutHelper;
    private boolean isPressed;


    public MusicCoverView(Context context) {
        this(context, null);
    }

    public MusicCoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicCoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        smuiLayoutHelper = new SMUILayoutHelper();
        smuiLayoutHelper.initAttrs(context, attrs);
    }


    private void init(Context context) {
//        if (rotationAnimator == null) {
//            rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360f);
//        }
//
//        rotationAnimator.setDuration(5000);
//        rotationAnimator.setRepeatMode(ValueAnimator.RESTART);
//        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        rotationAnimator.setInterpolator(new LinearInterpolator());

//        rotationAnimator.start();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        smuiLayoutHelper.onSizeChanged(this, w, h);

    }

    @Override
    public void draw(Canvas canvas) {
        if (smuiLayoutHelper.isClipBg()) {
            canvas.save();
            canvas.clipPath(smuiLayoutHelper.getClipPath());
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(smuiLayoutHelper.getCanvasRectF(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        smuiLayoutHelper.onClipDraw(canvas, this.isPressed);
        canvas.restore();

    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        this.isPressed = pressed;
        invalidate();
    }

//    ValueAnimator valueAnimator;
//
//    public void startPlay() {
//        CountDownTimer countDownTimer = new CountDownTimer(120 * 1000, 500) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                invalidate();
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
//
//        countDownTimer.start();
//    }
}
