package com.song.tasty.common.app.base;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author lichen
 * @date ：2019-09-01 12:51
 * @email : 196003945@qq.com
 * @description :
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<T> data;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }


    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (data != null && data.size() > 0) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

}

