package com.song.tasty.module.home.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.song.tasty.common.app.base.BaseAppFragment
import com.song.tasty.common.core.utils.SmartUtils
import com.song.tasty.module.home.R
import com.song.tasty.module.home.adapter.*
import com.song.tasty.module.home.entity.*
import com.song.tasty.module.home.mvvm.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_layout_loading.view.*
import kotlinx.android.synthetic.main.layout_nav_titlebar.view.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import java.util.*

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
class HomeFragment : BaseAppFragment<HomeViewModel>() {
    private var adapter: MultiTypeAdapter? = null
    private var items: Items? = null
    override fun getLayoutResId(): Int {
        return R.layout.home_fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        val titleBar: ConstraintLayout = rootView.findViewById(R.id.titleBar)
        val tvTitle: TextView =titleBar.findViewById(R.id.tvTitle)
        //        TextView tvTitle = rootTitleBar.findViewById(R.id.tvTitle);
        tvTitle.text=resources.getString(R.string.home_home);
        //titleBar.tvTitle.text=resources.getString(R.string.home_home);
        adapter = MultiTypeAdapter();
        adapter!!.register(BannerListBean::class.java, HomeBannerViewBinder())
        adapter!!.register(HomeNavListBean::class.java, HomeNavRVViewBinder())
        adapter!!.register(HomeTitleBean::class.java, HomeTitleViewBinder());
        adapter!!.register(SongSheetListBean::class.java, HomeSongSheetRVViewBinder())
        adapter!!.register(SongBean::class.java, HomeSongViewBinder())
        items = Items()
        adapter!!.items = items!!
        rvContainer.setLayoutManager(LinearLayoutManager(activity))
        rvContainer.setAdapter(adapter)
        refresh.setEnableLoadMore(false)
        tvTitle.setOnClickListener {
            SmartUtils.startActivity(PersonRadioActivity::class.java)
            //                CC cc = CC.obtainBuilder(APP_COMP_FLUTTER)
//                        .setActionName(START_ACTIVITY)
//                        .addParam("pContext",getActivity())
//                        .build();
//                cc.call();

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
    }

    override fun initData() {
        super.initData()
        refresh.setOnRefreshListener(OnRefreshListener { viewModel!!.getData() })
        refresh.autoRefresh()
    }

    override fun initObserve() {
        viewModel!!.successResult.observe(this, Observer { homeResult -> setData(homeResult) })
    }

    private fun setData(result: HomeResult) {
        items!!.clear()


        //banner
        val bannerList = result.indexad
        bannerList.removeAt(bannerList.size - 1)
        items!!.add(BannerListBean(bannerList))

        //导航
        initNav()


        //官方歌单
        items!!.add(HomeTitleBean(resources.getString(R.string.home_offocal_song_sheet)))
        val songSheetList = result.gedanx
        songSheetList.removeAt(songSheetList.size - 1)
        items!!.add(SongSheetListBean(songSheetList))

        //好音乐
        items!!.add(HomeTitleBean(resources.getString(R.string.home_good_song)))
        val goodList = result.goodlist[0]
        goodList.removeAt(goodList.size - 1)
        items!!.addAll(goodList)

        //日推
        items!!.add(HomeTitleBean(resources.getString(R.string.home_day_song)))
        val daySongList = result.newmlist[0]
        daySongList.removeAt(daySongList.size - 1)
        items!!.addAll(daySongList)

        //文章
        items!!.add(HomeTitleBean(resources.getString(R.string.home_essay)))
        val newsList = result.newslist[0]
        newsList.removeAt(newsList.size - 1)
        items!!.addAll(newsList)

        //周榜
        items!!.add(HomeTitleBean(resources.getString(R.string.home_week_board)))
        val guestList = result.weeklist[0]
        guestList.removeAt(guestList.size - 1)
        items!!.addAll(guestList)
        adapter!!.notifyDataSetChanged()
        refresh.finishRefresh()
    }

    private fun initNav() {
        val list: MutableList<HomeNavBean> = ArrayList(5)
        for (i in 0..4) {
            val bean = HomeNavBean()
            list.add(bean)
        }
        list[0].text = resources.getString(R.string.home_song_sheet)
        list[0].imgRes = R.mipmap.ic_home_nav_songsheet
        list[1].text = resources.getString(R.string.home_singer)
        list[1].imgRes = R.mipmap.ic_home_nav_singer
        list[2].text = resources.getString(R.string.home_billborad)
        list[2].imgRes = R.mipmap.ic_home_nav_list
        list[3].text = resources.getString(R.string.home_sort)
        list[4].imgRes = R.mipmap.ic_home_nav_sort
        list[4].text = resources.getString(R.string.home_news)
        list[4].imgRes = R.mipmap.ic_home_nav_random
        val bean = HomeNavListBean(list)
        items!!.add(bean)
    }

    companion object {
        @JvmStatic
        val instance: HomeFragment
            get() = HomeFragment()
    }
}