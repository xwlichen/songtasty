package com.song.tasty.common.app.widget.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lichen
 * @date ：2019-09-01 12:45
 * @email : 196003945@qq.com
 * @description :
 */
public class RecyclerGridDecoration extends RecyclerView.ItemDecoration {
    private int spanCount; //列数
    private int spacing; //中间间隔
    private int topSpacing; //上下间隔
    private boolean includeEdge; //是否包含边缘

    public RecyclerGridDecoration(int spacing, int topSpacing, int spanCount) {
        this(spacing, topSpacing, spanCount, false);
    }

    public RecyclerGridDecoration(int spacing, int topSpacing, int spanCount, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.topSpacing = topSpacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //这里是关键，需要根据你有几列来判断
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            if (spacing > 0) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
            }
            if (position < spanCount) { // top edge
                outRect.top = topSpacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            if (spacing > 0) {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            }
            if (position >= spanCount) {
                outRect.top = topSpacing; // item top
            }
        }
    }
}