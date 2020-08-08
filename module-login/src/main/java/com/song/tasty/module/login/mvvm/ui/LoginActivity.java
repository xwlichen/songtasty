package com.song.tasty.module.login.mvvm.ui;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;

import com.smart.ui.utils.SMUIDisplayHelper;
import com.smart.ui.utils.SMUIStatusBarHelper;
import com.smart.ui.widget.dialog.SMUITipDialog;
import com.song.tasty.common.app.base.BaseAppActivity;
import com.song.tasty.common.core.utils.KeyBoardUtils;
import com.song.tasty.common.core.utils.LogUtils;
import com.song.tasty.common.core.utils.imglaoder.GlideUtils;
import com.song.tasty.module.login.BR;
import com.song.tasty.module.login.R;
import com.song.tasty.module.login.databinding.LoginActivityLoginBinding;
import com.song.tasty.module.login.mvvm.viewmodel.LoginViewModel;

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
public class LoginActivity extends BaseAppActivity<LoginActivityLoginBinding, LoginViewModel> {

    private int bottomHeight;

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.login_activity_login;
    }

    @Override
    public void initView() {

        KeyBoardUtils.getInstance().setOnKeyboardStatusChangeListener(this, onKeyBoardStatusChangeListener);
        GlideUtils.loadImage(this
                , "file:///android_asset/login_background_video.webp"
                , binding.ivBg);


        viewModel.pwdSwitchData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.ivPwdSwitch.setBackgroundResource(R.mipmap.ic_pwd_visible);
                    binding.etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    binding.ivPwdSwitch.setBackgroundResource(R.mipmap.ic_pwd_gone);
                    binding.etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    @Override
    public void showLoading() {
        if (smuiTipDialog == null) {
            smuiTipDialog = new SMUITipDialog(this);
        }
        if (smuiTipDialog.isShowing()) {
            hideLoading();
        }
        smuiTipDialog.show();
    }


    @Override
    public void hideLoading() {
        if (smuiTipDialog != null) {
            smuiTipDialog.show();
        }
    }

    private KeyBoardUtils.OnKeyboardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardUtils.OnKeyboardStatusChangeListener() {

        @Override
        public void onKeyboardPop(int keyboardHeight) {

            final int height = keyboardHeight;
            binding.tvTitle.setVisibility(View.GONE);


            if (bottomHeight > height) {
                return;
            }
            int offset = bottomHeight - height;
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) binding.clOffsent
                    .getLayoutParams();
            lp.topMargin = offset;
            binding.clOffsent.setLayoutParams(lp);
            binding.clOffsent.setPadding(0, SMUIStatusBarHelper.getStatusbarHeight(LoginActivity.this), 0, 0);


        }

        @Override
        public void onKeyboardClose(int keyboardHeight) {

            binding.tvTitle.setVisibility(View.VISIBLE);
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) binding.clOffsent
                    .getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                binding.clOffsent.setLayoutParams(lp);
            }

        }
    };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            bottomHeight = binding.clOffsent.getBottom()
                    - binding.tvLogin.getBottom()
                    + binding.tvTitle.getHeight()
                    + SMUIDisplayHelper.dp2px(LoginActivity.this, 30);


        }
    }


}
