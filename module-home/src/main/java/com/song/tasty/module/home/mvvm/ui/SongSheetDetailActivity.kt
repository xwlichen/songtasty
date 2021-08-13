package com.song.tasty.module.home.mvvm.ui

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.module.home.R
import com.song.tasty.module.home.adapter.HomeSongViewBinder
import com.song.tasty.module.home.entity.SongBean
import com.song.tasty.module.home.mvvm.viewmodel.SongSheetDetailViewModel
import kotlinx.android.synthetic.main.home_activity_song_sheet_activity.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @date : 2019-09-05 15:14
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class SongSheetDetailActivity : BaseAppActivity<SongSheetDetailViewModel?>() {
    private var adapter: MultiTypeAdapter? = null
    private var items = ArrayList<Any>()
    override fun getLayoutResId(): Int {
        return R.layout.home_activity_song_sheet_activity
    }

    override fun initView() {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_logo)
        val palette = Palette.from(bitmap).generate()
        val colorMain = palette.getDominantColor(ContextCompat.getColor(this, R.color.color_transparent))
        scaleBehaviorView.setColorList(intArrayOf(colorMain, Color.BLACK))
        adapter = MultiTypeAdapter()
        adapter!!.register(SongBean::class.java, HomeSongViewBinder())
        rvContainer.setLayoutManager(LinearLayoutManager(this))
        rvContainer.setAdapter(adapter)
        val list: ArrayList<Any?> = ArrayList<Any?>(30)
        for (i in 0..29) {
            val songBean = SongBean()
            songBean.name = "测试$i"
            items!!.add(songBean)
        }
        adapter!!.items = items!!
    } //    @Override
    //    public void onBackPressed() {
    //        if (mHeaderBehavior != null && mHeaderBehavior.isClosed()) {
    //            mHeaderBehavior.openHeader();
    //        } else {
    //            super.onBackPressed();
    //        }
    //    }
}