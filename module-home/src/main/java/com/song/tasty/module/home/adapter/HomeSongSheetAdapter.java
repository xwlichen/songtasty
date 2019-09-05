package com.song.tasty.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.ui.widget.image.SMUIImageView;
import com.song.tasty.common.app.base.BaseRecyclerAdapter;
import com.song.tasty.common.core.utils.imglaoder.GlideUtils;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.SongSheetBean;

/**
 * @date : 2019-08-30 17:30
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HomeSongSheetAdapter extends BaseRecyclerAdapter<SongSheetBean, HomeSongSheetAdapter.ViewHolder> {


    public HomeSongSheetAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HomeSongSheetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.home_item_home_song_sheet, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(HomeSongSheetAdapter.ViewHolder holder, int position) {

        SongSheetBean item = data.get(position);
        if (item == null) {
            return;
        }

        GlideUtils.loadImage(holder.itemView.getContext(), item.getTopicimg(), holder.ivBg);
        holder.tvName.setText(item.getTopicname());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SMUIImageView ivBg;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBg = itemView.findViewById(R.id.ivBg);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
