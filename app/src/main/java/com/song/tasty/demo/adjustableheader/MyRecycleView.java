package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.utils.LogUtils;

/**
 * Created by liyongan on 19/2/12.
 */

public class MyRecycleView extends RecyclerView {
    private static final String TAG = "MyRecycleView";

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "RecycleView onLayout: ");
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        Log.i(TAG, "RecycleView onMeasure: ");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.e(TAG, "RecycleView invalidate: ");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        if (isTouchPointInView(this, x, y)) {

            Log.e(TAG, "RecycleView dispatchTouchEvent");
            return super.dispatchTouchEvent(ev);
        } else{
            stopScroll();
            Log.e(TAG, "RecycleView dispatchTouchEvent");
            return super.dispatchTouchEvent(ev);
//            stopNestedScroll(ViewCompat.TYPE_NON_TOUCH);
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        Log.e(TAG, "RecycleView onInterceptTouchEvent");
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

//        int x = (int) e.getRawX();
//        int y = (int) e.getRawY();
//        if (isTouchPointInView(this, x, y)) {
//            Log.e(TAG, "RecycleView onTouchEvent: " + e.getY() + ": " + e.getActionMasked());
//            return super.onTouchEvent(e);
//        }else{
//            if (computeVerticalScrollOffset()>0){
//                return false;
//            }else{
//                Log.e(TAG, "RecycleView onTouchEvent: " + e.getY() + ": " + e.getActionMasked());
                return super.onTouchEvent(e);
//            }
//
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
