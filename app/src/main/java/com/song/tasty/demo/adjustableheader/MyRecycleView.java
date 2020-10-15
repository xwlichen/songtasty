package com.song.tasty.demo.adjustableheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by liyongan on 19/2/12.
 */

public class MyRecycleView extends RecyclerView {
    private static final String TAG = "RecyclerView";

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "onLayout: ");
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        Log.i(TAG, "onMeasure: ");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.i(TAG, "invalidate: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        Log.i(TAG, "onTouchEvent: " + e.getY() + ": " + e.getActionMasked());
        return super.onTouchEvent(e);
    }


}
