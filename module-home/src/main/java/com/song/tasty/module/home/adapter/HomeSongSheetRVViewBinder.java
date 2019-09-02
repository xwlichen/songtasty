package com.song.tasty.module.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.ui.utils.SMUIDisplayHelper;
import com.song.tasty.common.app.widget.decoration.RecyclerLinearDecoration;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.entity.SongSheetBean;
import com.song.tasty.module.home.entity.SongSheetListBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.drakeet.multitype.ItemViewBinder;

import static com.song.tasty.common.app.widget.decoration.RecyclerLinearDecoration.SPACE_RIGHT_OR_BOTTOM;

/**
 * @date : 2019-08-30 17:30
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class HomeSongSheetRVViewBinder extends ItemViewBinder<SongSheetListBean, HomeSongSheetRVViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.home_item_rv, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SongSheetListBean item) {

        holder.setData(item.getList());


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvContainer;
        HomeSongSheetAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvContainer = itemView.findViewById(R.id.rvContainer);

            rvContainer.setLayoutManager(new LinearLayoutManager(itemView.getContext(), RecyclerView.HORIZONTAL, false));
            rvContainer.addItemDecoration(new RecyclerLinearDecoration(LinearLayoutManager.HORIZONTAL,
                    SMUIDisplayHelper.dp2px(itemView.getContext(), 8),
                    SPACE_RIGHT_OR_BOTTOM,
                    SMUIDisplayHelper.dp2px(itemView.getContext(), 16)));

            adapter = new HomeSongSheetAdapter(itemView.getContext());
            rvContainer.setAdapter(adapter);
        }

        void setData(List<SongSheetBean> data) {
            adapter.setData(data);
        }
    }
}
