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

import com.billy.cc.core.component.CC;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.song.tasty.common.app.base.BaseAppFragment;
import com.song.tasty.common.core.utils.SmartUtils;
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
import com.song.tasty.module.home.entity.NewsBean;
import com.song.tasty.module.home.entity.SongBean;
import com.song.tasty.module.home.entity.SongSheetBean;
import com.song.tasty.module.home.entity.SongSheetListBean;
import com.song.tasty.module.home.mvvm.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import io.flutter.embedding.android.FlutterActivity;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.song.tasty.common.app.AppRouters.APP_COMP_FLUTTER;
import static com.song.tasty.common.app.AppRouters.HOME_COMP_MUSICPLAY;
import static com.song.tasty.common.app.AppRouters.LOGIN_COMP_MAIN;
import static com.song.tasty.common.app.AppRouters.START_ACTIVITY;

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
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC cc = CC.obtainBuilder(APP_COMP_FLUTTER)
                        .setActionName(START_ACTIVITY)
                        .addParam("pContext",getActivity())
                        .build();
                cc.call();

//                CC cc = CC.obtainBuilder(LOGIN_COMP_MAIN)
//                        .setActionName(START_ACTIVITY)
//                        .build();
//                cc.call();

//                                startActivity(
//                        FlutterActivity
//                                .withNewEngine()
//                                .initialRoute("route1")
//                                .build(getActivity()));
            }
        });


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


        //官方歌单
        items.add(new HomeTitleBean(getResources().getString(R.string.home_offocal_song_sheet)));
        List<SongSheetBean> songSheetList = result.getGedanx();
        songSheetList.remove(songSheetList.size() - 1);
        items.add(new SongSheetListBean(songSheetList));

        //好音乐
        items.add(new HomeTitleBean(getResources().getString(R.string.home_good_song)));
        List<SongBean> goodList = result.getGoodlist().get(0);
        goodList.remove(goodList.size() - 1);
        items.addAll(goodList);

        //日推
        items.add(new HomeTitleBean(getResources().getString(R.string.home_day_song)));
        List<SongBean> daySongList = result.getNewmlist().get(0);
        daySongList.remove(daySongList.size() - 1);
        items.addAll(daySongList);

        //文章
        items.add(new HomeTitleBean(getResources().getString(R.string.home_essay)));
        List<NewsBean> newsList = result.getNewslist().get(0);
        newsList.remove(newsList.size() - 1);
        items.addAll(newsList);

        //周榜
        items.add(new HomeTitleBean(getResources().getString(R.string.home_week_board)));
        List<SongBean> guestList = result.getWeeklist().get(0);
        guestList.remove(guestList.size() - 1);
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


        list.get(2).setText(getResources().getString(R.string.home_billborad));
        list.get(2).setImgRes(R.mipmap.ic_home_nav_list);


        list.get(3).setText(getResources().getString(R.string.home_sort));
        list.get(4).setImgRes(R.mipmap.ic_home_nav_sort);

        list.get(4).setText(getResources().getString(R.string.home_news));
        list.get(4).setImgRes(R.mipmap.ic_home_nav_random);


        HomeNavListBean bean = new HomeNavListBean(list);
        items.add(bean);
    }


}
