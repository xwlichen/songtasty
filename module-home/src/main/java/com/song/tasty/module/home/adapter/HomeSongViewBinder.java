package com.song.tasty.module.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.ui.widget.image.SMUIImageView;
import com.song.tasty.common.core.utils.GlideUtils;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.SongBean;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lichen
 * @date ：2019-09-02 22:59
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeSongViewBinder extends ItemViewBinder<SongBean, HomeSongViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.home_item_song, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SongBean item) {
        holder.tvName.setText(item.getName());
        holder.tvSinger.setText(item.getUp_user());
        GlideUtils.loadImage(holder.itemView.getContext(),
                item.getUp_user_logo(),
                holder.ivCover);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SMUIImageView ivCover;
        TextView tvName;
        TextView tvSinger;
        ImageView ivOption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvName = itemView.findViewById(R.id.tvName);
            tvSinger = itemView.findViewById(R.id.tvSinger);
            ivOption = itemView.findViewById(R.id.ivOption);
        }
    }
}
