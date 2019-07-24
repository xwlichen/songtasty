package com.song.tasty.module.login.mvvm.ui;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.module.login.R;
import com.song.tasty.module.login.databinding.ActivityLoginBinding;
import com.song.tasty.module.login.mvvm.viewmodel.LoginViewModel;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class LoginActivity extends BaseAppActivity<ActivityLoginBinding, LoginViewModel> {
    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        RequestOptions options = new RequestOptions()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
//        Glide.with(this).load(url).apply(options).into(imageView);
//                .as

    }
}
