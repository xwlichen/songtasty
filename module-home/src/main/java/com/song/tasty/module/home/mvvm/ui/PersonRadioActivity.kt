package com.song.tasty.module.home.mvvm.ui


import android.content.ContextWrapper
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.OrientationHelper
import com.smart.ui.utils.SMUIDisplayHelper
import com.smart.ui.utils.SMUINotchHelper
import com.smart.ui.widget.SMUITopBar
import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.common.ui.widget.recycler.PagerLayoutManager
import com.song.tasty.module.home.R
import com.song.tasty.module.home.mvvm.viewmodel.PersonRadioViewModel
import kotlinx.android.synthetic.main.home_activity_person_radio.*
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

class PersonRadioActivity : BaseAppActivity<PersonRadioViewModel>() {

    private val mPagerLayoutManager by lazy {
        PagerLayoutManager(this,OrientationHelper.VERTICAL)
    }

    private val mAdapter by lazy {
        MultiTypeAdapter()
    }

    private val mItems by lazy {
        Items()
    }


    override fun getLayoutResId(): Int {
        //return 0;
        return R.layout.home_activity_person_radio;
    }

    override fun initView() {
        mTitleBar = titleBar

        titleBar.setTitle("test")
        titleBar.setBackgroundColor(ContextCompat.getColor(this,R.color.color_primary_normal))
        super.initView()
//        rvContainer.apply {
//            layoutManager = mPagerLayoutManager
//            adapter = mAdapter
//        }
//
    }
}