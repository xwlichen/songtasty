package com.song.tasty.module.login.mvvm.ui

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.smart.ui.utils.SMUIDisplayHelper
import com.smart.ui.utils.SMUIStatusBarHelper
import com.smart.ui.widget.dialog.SMUITipDialog
import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.common.core.utils.KeyBoardUtils
import com.song.tasty.common.core.utils.KeyBoardUtils.OnKeyboardStatusChangeListener
import com.song.tasty.common.core.utils.imglaoder.GlideUtils
import com.song.tasty.module.login.R
import com.song.tasty.module.login.mvvm.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity_login.*

/**
 * @author lichen
 * @date ：2019-07-24 20:34
 * @email : 196003945@qq.com
 * @description :
 */
class LoginActivity : BaseAppActivity<LoginViewModel>() {
    private var bottomHeight = 0


    override fun getLayoutResId(): Int {
        return R.layout.login_activity_login
    }

    override fun initView() {
        KeyBoardUtils.getInstance().setOnKeyboardStatusChangeListener(this, onKeyBoardStatusChangeListener)
        GlideUtils.loadImage(this
                , "file:///android_asset/login_background_video.webp"
                , ivBg)
        viewModel!!.pwdSwitchData.observe(this, Observer { aBoolean ->
            if (aBoolean) {
                ivPwdSwitch.setBackgroundResource(R.mipmap.ic_pwd_visible)
                etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
            } else {
                ivPwdSwitch.setBackgroundResource(R.mipmap.ic_pwd_gone)
                etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
        })
    }

    override fun showLoading() {
        if (smuiTipDialog == null) {
            smuiTipDialog = SMUITipDialog(this)
        }
        if (smuiTipDialog.isShowing) {
            hideLoading()
        }
        smuiTipDialog.show()
    }

    override fun hideLoading() {
        if (smuiTipDialog != null) {
            smuiTipDialog.show()
        }
    }

    private val onKeyBoardStatusChangeListener: OnKeyboardStatusChangeListener = object : OnKeyboardStatusChangeListener {
        override fun onKeyboardPop(keyboardHeight: Int) {
            tvTitle.setVisibility(View.GONE)
            if (bottomHeight > keyboardHeight) {
                return
            }
            val offset = bottomHeight - keyboardHeight
            val lp = clOffsent
                    .getLayoutParams() as ViewGroup.MarginLayoutParams
            lp.topMargin = offset
            clOffsent.setLayoutParams(lp)
            clOffsent.setPadding(0, SMUIStatusBarHelper.getStatusbarHeight(this@LoginActivity), 0, 0)
        }

        override fun onKeyboardClose(keyboardHeight: Int) {
            tvTitle.setVisibility(View.VISIBLE)
            val lp = clOffsent
                    .getLayoutParams() as ViewGroup.MarginLayoutParams
            if (lp.topMargin != 0) {
                lp.topMargin = 0
                clOffsent.setLayoutParams(lp)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            bottomHeight = ((clOffsent.getBottom()
                    - tvLogin.getBottom()) + tvTitle.getHeight()
                    + SMUIDisplayHelper.dp2px(this@LoginActivity, 30))
        }
    }
}