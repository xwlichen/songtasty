package com.song.tasty.module.home.mvvm.viewmodel

import android.app.Application
import com.song.tasty.common.core.base.BaseViewModel
import com.song.tasty.module.home.datasource.DataRepository
import com.song.tasty.module.home.datasource.Injection

/**
 * @date : 2020-01-13 14:43
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
class MusicPlayViewModel(application: Application) :
        BaseViewModel<DataRepository>(application, Injection.provideDataRepository()) {

}