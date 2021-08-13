
package com.song.tasty.common.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewBinder;
import com.song.tasty.common.app.R;
import com.song.tasty.common.app.music.bean.MusicBean1;
import com.song.tasty.common.core.utils.imglaoder.GlideUtils;
import com.song.tasty.common.ui.widget.MusicCoverView;


/**
 * @date : 2019-08-20 11:22
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class MusicBottomNavItemViewBinder extends ItemViewBinder<MusicBean1, MusicBottomNavItemViewBinder.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_music_bottom_nav, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MusicBean1 item) {

        holder.tvTitle.setText(item.getName() + " - " + item.getName());
        GlideUtils.loadImage(holder.itemView.getContext(), item.getCover(), holder.ivCover);


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
