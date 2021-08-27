package com.song.tasty.module.home.mvvm.ui


import android.content.ContextWrapper
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.OrientationHelper
import com.drakeet.multitype.MultiTypeAdapter
import com.smart.ui.utils.SMUIDisplayHelper
import com.smart.ui.utils.SMUINotchHelper
import com.smart.ui.widget.SMUITopBar
import com.smart.utils.LogUtils
import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.common.ui.widget.recycler.OnPagerListener
import com.song.tasty.common.ui.widget.recycler.PagerLayoutManager
import com.song.tasty.module.home.Constants
import com.song.tasty.module.home.R
import com.song.tasty.module.home.adapter.PersonRadioViewHolder
import com.song.tasty.module.home.entity.SongBean
import com.song.tasty.module.home.mvvm.viewmodel.PersonRadioViewModel
import kotlinx.android.synthetic.main.home_activity_person_radio.*

class PersonRadioActivity : BaseAppActivity<PersonRadioViewModel>() {

    private val mPagerLayoutManager by lazy {
        PagerLayoutManager(this,OrientationHelper.VERTICAL)
    }

    private val mAdapter by lazy {
        MultiTypeAdapter()
    }

    private val mItems by lazy {
        ArrayList<Any>()
    }

    private var mPagerListener : OnPagerListener ? = null


    override fun getLayoutResId(): Int {
        //return 0;
        return R.layout.home_activity_person_radio;
    }

    override fun initView() {
        super.initView()
        titleBar.setTitle("test").setTextColor(ContextCompat.getColor(this,R.color.color_white))
        titleBar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_transparent))
        mAdapter.register(PersonRadioViewHolder());
        rvContainer.apply {
            layoutManager = mPagerLayoutManager
            adapter = mAdapter
        }
        if (mPagerListener ==null) mPagerListener = object : OnPagerListener {
            override fun onInitComplete() {
                LogUtils.e("onInitComplete")
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                LogUtils.e("onPageRelease---"+position+"-----"+isNext)
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                LogUtils.e("onPageSelected---"+position+"-----"+isBottom)
            }

        }

        mPagerListener.let { mPagerLayoutManager.setOnViewPagerListener(it) }

    }

    override fun initData() {
        super.initData()
        LogUtils.e("initData---")
        mItems.addAll(Constants.getVideoList())
        mAdapter.items = mItems
        mAdapter.notifyDataSetChanged()

    }
}