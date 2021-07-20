package com.song.tasty.module.home.mvvm.viewmodel

import android.app.Application
import com.song.tasty.common.core.base.BaseViewModel
import com.song.tasty.module.home.datasource.DataRepository
import com.song.tasty.module.home.datasource.Injection

class PersonRadioViewModel(application: Application): BaseViewModel<DataRepository>(application,Injection.provideDataRepository()) {

}