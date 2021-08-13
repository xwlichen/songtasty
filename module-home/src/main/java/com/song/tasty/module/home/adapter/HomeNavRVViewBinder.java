package com.song.tasty.module.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drakeet.multitype.ItemViewBinder;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.HomeNavBean;
import com.song.tasty.module.home.entity.HomeNavListBean;

import java.util.List;


/**
 * @author lichen
 * @date ：2019-08-29 23:14
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeNavRVViewBinder extends ItemViewBinder<HomeNavListBean, HomeNavRVViewBinder.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.home_item_rv_line, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HomeNavListBean item) {
        holder.setData(item.getList());


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvContainer;
        HomeNavAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvContainer = itemView.findViewById(R.id.rvContainer);

            rvContainer.setLayoutManager(new GridLayoutManager(itemView.getContext(), 5));
//            rvContainer.addItemDecoration(new RecyclerLinearDecoration(LinearLayoutManager.HORIZONTAL,
//                    SMUIDisplayHelper.dp2px(itemView.getContext(), 8),
//                    SPACE_RIGHT_OR_BOTTOM,
//                    SMUIDisplayHelper.dp2px(itemView.getContext(), 16)));

            adapter = new HomeNavAdapter(itemView.getContext());
            rvContainer.setAdapter(adapter);
        }

        void setData(List<HomeNavBean> data) {
            adapter.setData(data);
        }
    }
}
