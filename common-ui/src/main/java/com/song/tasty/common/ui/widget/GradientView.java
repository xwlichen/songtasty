package com.song.tasty.common.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.song.tasty.common.ui.R;

/**
 * @date : 2019-09-11 16:34
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class GradientView extends View {
    private static final int MODEL_LINEAR = 0;//线性渐变


    private int[] colorList;
    private int model;
    private Paint paint;

    private int width;
    private int height;

    private LinearGradient linearGradient;


    public GradientView(Context context) {
        this(context, null);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        model = typedArray.getInt(R.styleable.GradientView_gradientModel, MODEL_LINEAR);

        paint = new Paint();
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
        width = measure(widthMeasureSpec);
        height = measure(heightMeasureSpec);
        initGradient();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, width, height, paint);
    }


    private void initGradient() {
        if (width <= 0) {
            return;
        }
        if (colorList == null || colorList.length <= 0) {
            return;
        }
        switch (model) {
            case MODEL_LINEAR:
                if (linearGradient == null) {
                    linearGradient = new LinearGradient(0, 0, 0, height, colorList, null, Shader.TileMode.CLAMP);
                }
                paint.setShader(linearGradient);
                break;
            default:
                break;
        }
    }

    public int[] getColorList() {
        return colorList;
    }

    public void setColorList(int[] colorList) {
        this.colorList = colorList;
        invalidate();
    }
}
