package com.song.tasty.module.home.mvvm.ui


import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.module.home.R
import com.song.tasty.module.home.mvvm.viewmodel.PersonRadioViewModel

class PersonRadioActivity : BaseAppActivity<PersonRadioViewModel>() {


    override fun getLayoutResId(): Int {
        //return 0;
        return R.layout.home_activity_person_radio;
    }
}