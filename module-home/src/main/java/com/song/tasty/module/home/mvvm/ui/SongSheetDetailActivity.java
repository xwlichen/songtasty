package com.song.tasty.module.home.mvvm.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.adapter.HomeSongViewBinder;
import com.song.tasty.module.home.databinding.HomeActivitySongSheetActivityBinding;
import com.song.tasty.module.home.entity.SongBean;
import com.song.tasty.module.home.mvvm.viewmodel.SongSheetDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @date : 2019-09-05 15:14
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SongSheetDetailActivity extends BaseAppActivity<HomeActivitySongSheetActivityBinding, SongSheetDetailViewModel> {


    private MultiTypeAdapter adapter;
    private Items items;

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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image3);
        Palette palette = Palette.from(bitmap).generate();
        int colorMain = palette.getDominantColor(ContextCompat.getColor(this, R.color.color_transparent));
        binding.scaleBehaviorView.setColorList(new int[]{colorMain, Color.BLACK});

        adapter = new MultiTypeAdapter();
        adapter.register(SongBean.class, new HomeSongViewBinder());
        items = new Items();


        binding.rvContainer.setLayoutManager(new LinearLayoutManager(this));

        binding.rvContainer.setAdapter(adapter);
        List<SongBean> list = new ArrayList(30);
        for (int i = 0; i < 30; i++) {
            SongBean songBean = new SongBean();
            songBean.setName("测试" + i);
            items.add(songBean);
        }
        adapter.setItems(items);

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
