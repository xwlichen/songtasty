package com.song.tasty.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.song.tasty.common.core.utils.GlideUtils;
import com.song.tasty.common.ui.widget.banner.holder.BannerHolderCreator;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.BannerBean;

/**
 * @author lichen
 * @date ：2019-09-01 00:23
 * @email : 196003945@qq.com
 * @description :
 */
public class BannerViewHolder implements BannerHolderCreator.ViewHoler<BannerBean> {
    ImageView ivBg;

    @Override
    public View createView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.home_item_home_banner_child, null);
        ivBg = root.findViewById(R.id.ivBg);
        return root;
    }

    @Override
    public void onBind(Context context, int position, BannerBean data) {
        GlideUtils.loadImage(context, data.getBanx(), ivBg);

    }
}
