package com.song.tasty.common.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.core.widget.NestedScrollView;

/**
 * @date : 2019-09-12 11:43
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : 用于子View防止父View拦截子View的事件
 */
public class DisInterceptNestedScrollView extends NestedScrollView {
    public DisInterceptNestedScrollView(Context context) {
        super(context);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        requestDisallowInterceptTouchEvent(true);
    }

    public DisInterceptNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        requestDisallowInterceptTouchEvent(true);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowInterceptTouchEvent(true);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
