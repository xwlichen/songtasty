package com.song.tasty.common.ui.widget.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * @author lichen
 * @date ：2019-08-31 23:36
 * @email : 196003945@qq.com
 * @description :
 */
public interface BannerHolderCreator<VH extends BannerHolderCreator.ViewHoler> {

    /**
     * 创建ViewHolder
     *
     * @return
     */
    public VH createViewHolder();


    public interface ViewHoler<T> {

        /**
         * 创建View
         *
         * @param context
         * @return
         */
        View createView(Context context);

        /**
         * 绑定数据
         *
         * @param context
         * @param position
         * @param data
         */
        void onBind(Context context, int position, T data);
    }
}
