package com.song.tasty.module.login.mvvm.ui;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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


        ImageView ivBg = findViewById(R.id.ivBg);
        RequestOptions options = new RequestOptions()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this)
                .load("file:///android_asset/login_background_video.webp")
                .apply(options).transition(new DrawableTransitionOptions().crossFade(200))
                .into(ivBg);

    }
}
