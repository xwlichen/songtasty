package com.song.tasty.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.tasty.common.app.base.BaseRecyclerAdapter;
import com.song.tasty.common.app.listeners.OnAntiDoubleClickListener;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.HomeNavBean;

/**
 * @date : 2019-08-30 17:30
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HomeNavAdapter extends BaseRecyclerAdapter<HomeNavBean, HomeNavAdapter.ViewHolder> {


    public HomeNavAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HomeNavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.home_item_home_nav, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(HomeNavAdapter.ViewHolder holder, int position) {

        HomeNavBean item = data.get(position);
        if (item == null) {
            return;
        }

//        GlideUtils.loadImage(holder.itemView.getContext(), item.getTopicimg(), holder.ivBg);
        holder.ivNav.setImageResource(item.getImgRes());
        holder.tvNav.setText(item.getText());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNav;
        TextView tvNav;
        HomeNavBean bean;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNav = itemView.findViewById(R.id.ivNav);
            tvNav = itemView.findViewById(R.id.tvNav);

            ivNav.setOnClickListener(new OnAntiDoubleClickListener() {
                @Override
                public void onAntiDoubleClick(View v) {

                }
            });
        }


    }


}
