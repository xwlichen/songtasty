package com.song.tasty.module.home.mvvm.ui;

import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.module.home.R;

/**
 * @date : 2019-09-05 15:14
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SongSheetDetailActivity extends BaseAppActivity {
    @Override
    public int initVariableId() {
        return 0;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.home_activity_song_sheet_activity;
    }

//    @Override
//    public void onBackPressed() {
//        if (mHeaderBehavior != null && mHeaderBehavior.isClosed()) {
//            mHeaderBehavior.openHeader();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
