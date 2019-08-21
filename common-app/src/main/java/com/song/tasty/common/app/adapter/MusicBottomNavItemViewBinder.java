package com.song.tasty.common.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.song.tasty.common.app.R;
import com.song.tasty.common.app.entity.MusicBean;
import com.song.tasty.common.app.widget.MusicCoverView;
import com.song.tasty.common.core.utils.GlideUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @date : 2019-08-20 11:22
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicBottomNavItemViewBinder extends ItemViewBinder<MusicBean, MusicBottomNavItemViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_music_bottom_nav, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MusicBean item) {

        holder.tvTitle.setText(item.getName() + " - " + item.getSonger());
        GlideUtils.loadImage(holder.itemView.getContext(), item.getCoverSmall(), holder.ivCover);


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MusicCoverView ivCover;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}
