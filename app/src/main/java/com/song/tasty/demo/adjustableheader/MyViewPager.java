package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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
        Log.e("xw", "MyViewPager dispatchTouchEvent: " + ev.getX() + ": " + ev.getY() + ": " + ev.getRawX() + " : " + ev.getRawY());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getRawX();
//        int y = (int) ev.getRawY();
//        if (isTouchPointInView(this,x,y)) {
            return super.onInterceptTouchEvent(ev);
//        }else{
//            return true;
//        }
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
}
