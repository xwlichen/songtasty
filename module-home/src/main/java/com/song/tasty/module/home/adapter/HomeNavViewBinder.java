package com.song.tasty.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.HomeNavBean;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author lichen
 * @date ：2019-08-29 23:14
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeNavViewBinder extends ItemViewBinder<HomeNavBean, HomeNavViewBinder.ViewHodler> {


    @NonNull
    @Override
    protected ViewHodler onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.home_item_rv, parent, false);
        return new ViewHodler(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHodler holder, @NonNull HomeNavBean item) {
        holder.tvTitle.setText(item.getText());
        holder.title = item.getText();


    }

    class ViewHodler extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvMore;
        ImageView ivArrow;

        String title;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMore = itemView.findViewById(R.id.tvMore);
            ivArrow = itemView.findViewById(R.id.ivArrow);

            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    skipToUi(itemView.getContext(), title);
                }
            });
        }
    }


    private void skipToUi(Context context, String title) {
        if (context.getResources().getString(R.string.home_song_sheet).endsWith(title)) {
            ToastUtils.show("跳转歌单");
        }

    }
}
