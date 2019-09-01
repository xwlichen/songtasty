package com.song.tasty.module.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.song.tasty.common.ui.widget.banner.BannerView;
import com.song.tasty.common.ui.widget.banner.holder.BannerHolderCreator;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.BannerBean;
import com.song.tasty.module.home.entity.BannerListBean;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lichen
 * @date ：2019-09-01 00:07
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeBannerViewBinder extends ItemViewBinder<BannerListBean, HomeBannerViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.home_item_home_banner, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerListBean item) {
        holder.list = item.getList();
        holder.banner.setPages(item.getList(), new BannerHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder() {
                };
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        BannerView<BannerBean> banner;
        List<BannerBean> list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
            banner.setIndicatorVisible(true);

            banner.setBannerPageClickListener(new BannerView.BannerPageClickListener() {
                @Override
                public void onPageClick(View view, int position) {
                    if (!list.isEmpty()) {

                    }

                }
            });


        }
    }
}
