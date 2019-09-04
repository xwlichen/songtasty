package com.song.tasty.module.home.mvvm.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.module.home.BR;
import com.song.tasty.module.home.R;
import com.song.tasty.module.home.adapter.HomeBannerViewBinder;
import com.song.tasty.module.home.adapter.HomeNavRVViewBinder;
import com.song.tasty.module.home.adapter.HomeSongSheetRVViewBinder;
import com.song.tasty.module.home.adapter.HomeSongViewBinder;
import com.song.tasty.module.home.adapter.HomeTitleViewBinder;
import com.song.tasty.module.home.databinding.HomeFragmentBinding;
import com.song.tasty.module.home.entity.BannerBean;
import com.song.tasty.module.home.entity.BannerListBean;
import com.song.tasty.module.home.entity.HomeNavBean;
import com.song.tasty.module.home.entity.HomeNavListBean;
import com.song.tasty.module.home.entity.HomeResult;
import com.song.tasty.module.home.entity.HomeTitleBean;
import com.song.tasty.module.home.entity.SongBean;
import com.song.tasty.module.home.entity.SongSheetBean;
import com.song.tasty.module.home.entity.SongSheetListBean;
import com.song.tasty.module.home.mvvm.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class HomeFragment extends BaseAppFragment<HomeFragmentBinding, HomeViewModel> {


    private MultiTypeAdapter adapter;
    private Items items;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.home_fragment;
    }


    public static HomeFragment getInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void initView() {


        TextView tvTitle = binding.getRoot().findViewById(R.id.titlebar).findViewById(R.id.tvTitle);
//        TextView tvTitle = rootTitleBar.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.home_home));

        adapter = new MultiTypeAdapter();
        adapter.register(BannerListBean.class, new HomeBannerViewBinder());
        adapter.register(HomeNavListBean.class, new HomeNavRVViewBinder());
        adapter.register(HomeTitleBean.class, new HomeTitleViewBinder());
        adapter.register(SongSheetListBean.class, new HomeSongSheetRVViewBinder());
        adapter.register(SongBean.class, new HomeSongViewBinder());

        items = new Items();
        adapter.setItems(items);


        binding.rvContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvContainer.setAdapter(adapter);

        binding.refresh.setEnableLoadMore(false);


    }

    @Override
    public void initData() {
        super.initData();

        binding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.getData();
            }
        });

        binding.refresh.autoRefresh();

    }

    @Override
    public void initObserve() {
        viewModel.successResult.observe(this, new Observer<HomeResult>() {
            @Override
            public void onChanged(HomeResult homeResult) {
                setData(homeResult);

            }
        });
    }


    private void setData(HomeResult result) {
        items.clear();


        //banner
        List<BannerBean> bannerList = result.getIndexad();
        bannerList.remove(bannerList.size() - 1);
        items.add(new BannerListBean(bannerList));

        //导航
        initNav();


        //歌单
        items.add(new HomeTitleBean(getResources().getString(R.string.home_offocal_song_sheet)));
        List<SongSheetBean> songSheetList = result.getGedanx();
        songSheetList.remove(songSheetList.size() - 1);
        items.add(new SongSheetListBean(songSheetList));

        //新歌推荐
        items.add(new HomeTitleBean(getResources().getString(R.string.home_recomend_new_song)));
        List<SongBean> danceist = result.getDancelist();
        danceist.remove(danceist.size() - 1);
        items.addAll(danceist);

        //每日榜单
        items.add(new HomeTitleBean(getResources().getString(R.string.home_day_list)));
        List<SongBean> musicList = result.getMusiclist();
        musicList.remove(musicList.size() - 1);
        items.addAll(musicList);

        //猜你喜欢
        items.add(new HomeTitleBean(getResources().getString(R.string.home_you_like)));
        List<SongBean> guestList = result.getGuestlist();
//        guestList.remove(guestList.size() - 1);
        items.addAll(guestList);


        adapter.notifyDataSetChanged();
        binding.refresh.finishRefresh();
    }

    private void initNav() {
        List<HomeNavBean> list = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            HomeNavBean bean = new HomeNavBean();
            list.add(bean);
        }

        list.get(0).setText(getResources().getString(R.string.home_song_sheet));
        list.get(0).setImgRes(R.mipmap.ic_home_nav_songsheet);

        list.get(1).setText(getResources().getString(R.string.home_singer));
        list.get(1).setImgRes(R.mipmap.ic_home_nav_singer);

        list.get(2).setText(getResources().getString(R.string.home_random));
        list.get(2).setImgRes(R.mipmap.ic_home_nav_random);

        list.get(3).setText(getResources().getString(R.string.home_total_list));
        list.get(3).setImgRes(R.mipmap.ic_home_nav_list);


        list.get(4).setText(getResources().getString(R.string.home_sort));
        list.get(4).setImgRes(R.mipmap.ic_home_nav_sort);


        HomeNavListBean bean = new HomeNavListBean(list);
        items.add(bean);
    }


}
