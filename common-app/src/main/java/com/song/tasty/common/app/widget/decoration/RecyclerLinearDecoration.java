package com.song.tasty.common.app.widget.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lichen
 * @date ：2019-09-01 12:44
 * @email : 196003945@qq.com
 * @description :
 */
public class RecyclerLinearDecoration extends RecyclerView.ItemDecoration {

    /**
     * 左边／前面
     */
    public static final int SPACE_LEFT_OR_TOP = 0;
    /**
     * 右边／后面
     */
    public static final int SPACE_RIGHT_OR_BOTTOM = 1;

    /**
     * 上下／左右都设置相同的间隔
     */
    public static final int SPACE_ALL = 2;


    private int itemSpace;
    private int orientation;
    private int spacePosition;
    private int margin;


    public RecyclerLinearDecoration(int itemSpace, int orientation) {
        this(orientation, itemSpace, 1);
    }

    public RecyclerLinearDecoration(int orientation, int itemSpace, int spacePosition) {
        this(orientation, itemSpace, spacePosition, 0);
    }


    /**
     * @param orientation   item间隔
     * @param itemSpace     排列方向 LinearLayoutManager.HORIZONTAL LinearLayoutManager.VERTICAL
     * @param spacePosition 间隔添加位置 0 前面／左边      1 后面／右边      2 上下／左右都设置相同的间隔
     * @param margin        头部和尾部另外设置的间隔  不适用于分页加载
     */
    public RecyclerLinearDecoration(int orientation, int itemSpace, int spacePosition, int margin) {
        this.orientation = orientation;
        this.itemSpace = itemSpace;
        this.spacePosition = spacePosition;
        this.margin = margin;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            if (spacePosition == SPACE_LEFT_OR_TOP) {
                outRect.top = itemSpace;
            } else if (spacePosition == SPACE_RIGHT_OR_BOTTOM) {
                outRect.bottom = itemSpace;
            } else if (spacePosition == SPACE_ALL) {
                outRect.top = itemSpace;
                outRect.bottom = itemSpace;
            }
        } else if (orientation == LinearLayoutManager.HORIZONTAL) {

            if (margin <= 0) {
                setHorizontalSpace(outRect);
            } else {
                int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                int childCount = parent.getAdapter().getItemCount();

                if (itemPosition == 0) {
                    if (spacePosition == SPACE_LEFT_OR_TOP) {
                        outRect.left = margin;
                    } else if (spacePosition == SPACE_RIGHT_OR_BOTTOM) {
                        outRect.left = margin;
                        outRect.right = itemSpace;
                    } else if (spacePosition == SPACE_ALL) {
                        outRect.left = margin;
                        outRect.right = itemSpace;
                    }
                } else if (itemPosition == childCount - 1) {
                    if (spacePosition == SPACE_LEFT_OR_TOP) {
                        outRect.left = itemSpace;
                        outRect.right = margin;
                    } else if (spacePosition == SPACE_RIGHT_OR_BOTTOM) {
                        outRect.right = margin;
                    } else if (spacePosition == SPACE_ALL) {
                        outRect.left = itemSpace;
                        outRect.right = margin;
                    }

                } else {
                    setHorizontalSpace(outRect);
                }


            }
        }
    }


    private void setHorizontalSpace(Rect outRect) {
        if (spacePosition == SPACE_LEFT_OR_TOP) {
            outRect.left = itemSpace;
        } else if (spacePosition == SPACE_RIGHT_OR_BOTTOM) {
            outRect.right = itemSpace;
        } else if (spacePosition == SPACE_ALL) {
            outRect.left = itemSpace;
            outRect.right = itemSpace;
        }
    }
}