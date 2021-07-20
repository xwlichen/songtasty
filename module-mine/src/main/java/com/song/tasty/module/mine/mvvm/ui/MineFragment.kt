package com.song.tasty.module.mine.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.song.tasty.common.app.base.BaseAppFragment
import com.song.tasty.module.mine.R
import com.song.tasty.module.mine.mvvm.viewmodel.MineViewModel

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
class MineFragment : BaseAppFragment<MineViewModel>() {
    override fun getLayoutResId(): Int {
        return R.layout.mine_fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initObserve() {}

    companion object {
        val instance: MineFragment
            get() = MineFragment()
    }
}