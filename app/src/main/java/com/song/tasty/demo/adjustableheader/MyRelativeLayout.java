package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by liyongan on 19/2/12.
 */

public class MyRelativeLayout extends RelativeLayout {
    private static final String TAG = "RelativeLayout";
    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "onLayout: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: ");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.i(TAG, "invalidate: ");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.i(TAG, "dispatchDraw: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
