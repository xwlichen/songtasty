package com.song.tasty.module.home.mvvm.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.common.app.base.BaseRecyclerAdapter;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.databinding.HomeActivitySongSheetActivityBinding;
import com.song.tasty.module.home.mvvm.viewmodel.SongSheetDetailViewModel;

import java.util.ArrayList;

/**
 * @date : 2019-09-05 15:14
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SongSheetDetailActivity extends BaseAppActivity<HomeActivitySongSheetActivityBinding, SongSheetDetailViewModel> {
    @Override
    public int initVariableId() {
        return 0;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.home_activity_song_sheet_activity;
    }

    @Override
    public void initView() {

        binding.rvContainer.setLayoutManager(new LinearLayoutManager(this));
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter(this) {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View root = LayoutInflater.from(context).inflate(R.layout.home_item_song, parent, false);
                return new ViewHolder(root);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.textView.setText("测试" + position);
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                TextView textView;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                    textView = itemView.findViewById(R.id.tvName);
                }
            }
        };
        binding.rvContainer.setAdapter(adapter);
//        List list=new
//        for (int i = 0; i <30 ; i++) {
//
//        }
        adapter.setData(new ArrayList(30));

    }


    //    @Override
//    public void onBackPressed() {
//        if (mHeaderBehavior != null && mHeaderBehavior.isClosed()) {
//            mHeaderBehavior.openHeader();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
