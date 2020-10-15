package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by liyongan on 19/3/13.
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("lya", "dispatchTouchEvent: " + ev.getX() + ": " + ev.getY() + ": " + ev.getRawX() + " : " + ev.getRawY());
        return super.dispatchTouchEvent(ev);
    }
}
