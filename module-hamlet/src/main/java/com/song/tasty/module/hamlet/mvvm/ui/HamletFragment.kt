package com.song.tasty.module.hamlet.mvvm.ui

import com.song.tasty.common.app.base.BaseAppFragment
import com.song.tasty.module.hamlet.R
import com.song.tasty.module.hamlet.mvvm.viewmodel.HamletViewModel

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
class HamletFragment : BaseAppFragment<HamletViewModel?>() {
    override fun getLayoutResId(): Int {
        return R.layout.hamlet_fragment
    }

    override fun initObserve() {}

    companion object {
        @JvmStatic
        val instance: HamletFragment
            get() = HamletFragment()
    }
}