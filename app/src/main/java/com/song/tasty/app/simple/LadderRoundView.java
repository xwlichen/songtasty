package com.song.tasty.app.simple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @date : 2019-08-16 17:02
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LadderRoundView extends AppCompatImageView {


    private Paint paintBg;
    private Path pathBg;
    private int colorBg = Color.BLACK;


    private RectF rectFBigBg; //白圈框

    private int width;
    private int height;
    private int offset = 10;
    private int borderRadius; //边界圆角大小


    public LadderRoundView(Context context) {
        this(context, null);
    }

    public LadderRoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LadderRoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context) {


        paintBg = new Paint();
        paintBg.setAntiAlias(true);
        paintBg.setStyle(Paint.Style.STROKE);
        paintBg.setStrokeCap(Paint.Cap.ROUND);
        paintBg.setColor(colorBg);
        paintBg.setStrokeWidth(5);


        pathBg = new Path();

    }

    /**
     * 根据Model返回值
     *
     * @param value
     * @return
     */
    private int measure(int value) {
        int result = 0;
        int specMode = MeasureSpec.getMode(value);
        int specSize = MeasureSpec.getSize(value);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                result = specSize;
                break;
            default:
                result = specSize;
                break;
        }

        return result;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (width <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            width = measure(widthMeasureSpec);
            height = measure(heightMeasureSpec);
        } else {
            setMeasuredDimension(width, height);
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    protected void drawBigBound(Canvas canvas) {

//        offset = boundSize / 2.0f;
        if (rectFBigBg == null) {
            rectFBigBg = new RectF(offset, offset, width - offset, height - offset);
        }

        canvas.drawRoundRect(rectFBigBg, borderRadius, borderRadius, paintBg);


    }
}
