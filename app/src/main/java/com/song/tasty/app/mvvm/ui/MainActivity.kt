package com.song.tasty.app.mvvm.ui

import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.IComponentCallback
import com.hjq.toast.ToastUtils
import com.smart.ui.widget.bottomnav.lottie.ILottieBottomNavViewCallback
import com.smart.ui.widget.bottomnav.lottie.NavItem
import com.smart.ui.widget.bottomnav.lottie.NavItemBuilder
import com.song.tasty.app.R
import com.song.tasty.app.mvvm.viewmodel.MainViewModel
import com.song.tasty.common.app.AppRouters
import com.song.tasty.common.app.base.BaseAppActivity
import com.song.tasty.common.app.flutter.ui.FlutterCommonFragment
import kotlinx.android.synthetic.main.app_activity_main.*
import java.util.*

class MainActivity : BaseAppActivity<MainViewModel?>() {
    private val fragments = HashMap<String, Fragment>()
    override fun getLayoutResId(): Int {
        return R.layout.app_activity_main
    }

    override fun initView() {
        initNav()
        CC.obtainBuilder(AppRouters.HOME_COMP_MAIN)
                .setActionName(AppRouters.GET_FRAGMENT)
                .build()
                .callAsyncCallbackOnMainThread(fragmentCallback)

//        EventBus.getDefault().register(this);
//        EventBus.getDefault().post("");
    }

    private fun initNav() {
        val item1 = NavItem("发现",
                Color.BLACK,
                Color.BLACK,
                "lotties/tab_select_discovery_anim.json",
                R.mipmap.ic_tab_discovery_normal,
                NavItem.Source.Assets,
                100.0f,
                false,
                false,
                null
        )
        val item2 = NavItemBuilder.createFrom(item1)
                .navTitle("音视")
                .selectedLottieName("lotties/tab_select_live_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_live_normal)
                .build()
        val item3 = NavItemBuilder.createFrom(item1)
                .navTitle("我的")
                .selectedLottieName("lotties/tab_select_me_anim.json")
                .unSelectedIcon(R.mipmap.ic_tab_me_normal)
                .build()
        val list: MutableList<NavItem>
        list = ArrayList(3)
        list.add(item1)
        list.add(item2)
        list.add(item3)
        musicNav.bringToFront()
        bottomNav.setCallback(object : ILottieBottomNavViewCallback {
            override fun onNavSelected(oldIndex: Int, newIndex: Int, menuItem: NavItem) {
                changeFragment(newIndex)

//                startActivity(
//                        FlutterActivity
//                                .withNewEngine()
//                                .initialRoute("route1")
//                                .build(MainActivity.this));
            }

            override fun onAnimationStart(index: Int, menuItem: NavItem) {}
            override fun onAnimationEnd(index: Int, menuItem: NavItem) {}
            override fun onAnimationCancel(index: Int, menuItem: NavItem) {}
        })
        bottomNav.setNavItemList(list)
    }

    var fragmentCallback = IComponentCallback { cc, result ->
        if (result.isSuccess) {
            val tag = cc.componentName + cc.actionName
            if (cc.getParamItem<Any?>("pContext") != null) {
            }
            val fragment = fragments[tag]
            if (fragment == null) {
                val newFragment = result.getDataItemWithNoKey<Fragment>()
                fragments[tag] = newFragment
                showFragment(tag, newFragment, true)
            } else {
                showFragment(tag, fragment, false)
            }
            musicNav.bringToFront()
        } else {
            ToastUtils.show("显示fragment失败")
        }
    }

    private fun showFragment(tag: String, fragment: Fragment?, isNew: Boolean) {
        if (fragment != null) {
            val trans = supportFragmentManager.beginTransaction()
            //            trans.setCustomAnimations(com.song.tasty.common.app.R.anim.slide_in_right, com.song.tasty.common.app.R.anim.slide_out_left);
            val iterator: Iterator<Map.Entry<String, Fragment>> = fragments.entries.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (entry.value !== fragment) {
                    trans.hide(entry.value)
                }
            }
            if (isNew) {
                trans.add(R.id.fragmentContainer, fragment, tag).show(fragment)
            } else {
                trans.show(fragment)
            }

//            trans.replace(R.id.fragmentContainer, fragment);
            trans.commitAllowingStateLoss()
        }
    }

    private fun changeFragment(posotion: Int) {
        val currentFragment: Fragment?
        val tag: String
        when (posotion) {
            0 -> {
                currentFragment = fragments[AppRouters.HOME_COMP_MAIN + AppRouters.GET_FRAGMENT]
                tag = AppRouters.GET_FRAGMENT
                if (currentFragment == null) {
                    CC.obtainBuilder(AppRouters.HOME_COMP_MAIN)
                            .setActionName(tag)
                            .build()
                            .callAsyncCallbackOnMainThread(fragmentCallback)
                } else {
                    showFragment(tag, currentFragment, false)
                }
            }
            1 -> {
                currentFragment = fragments[AppRouters.VIDEO_COMP_MAIN + AppRouters.GET_FRAGMENT]
                tag = AppRouters.GET_FRAGMENT
                if (currentFragment == null) {
                    CC.obtainBuilder(AppRouters.VIDEO_COMP_MAIN)
                            .setActionName(tag)
                            .build()
                            .callAsyncCallbackOnMainThread(fragmentCallback)
                } else {
                    showFragment(tag, currentFragment, false)
                }
            }
            2 -> {
                currentFragment = fragments[AppRouters.MINE_COMP_MAIN + AppRouters.GET_FRAGMENT]
                if (currentFragment != null && currentFragment is FlutterCommonFragment) {
                    flutterFragment = currentFragment
                }
                tag = AppRouters.GET_FRAGMENT
                if (currentFragment == null) {
                    CC.obtainBuilder(AppRouters.MINE_COMP_MAIN)
                            .setActionName(AppRouters.GET_FRAGMENT)
                            .addParam("pContext", this)
                            .build()
                            .callAsyncCallbackOnMainThread(fragmentCallback)
                } else {
                    showFragment(tag, currentFragment, false)
                }
            }
            else -> {
            }
        }
    }

    var flutterFragment: FlutterCommonFragment? = null
    public override fun onPostResume() {
        super.onPostResume()
        if (flutterFragment == null) {
            return
        }
        flutterFragment!!.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (flutterFragment == null) {
            return
        }
        flutterFragment!!.onNewIntent(intent)
    }

    override fun onBackPressed() {
        if (flutterFragment == null) {
            return
        }
        flutterFragment!!.onBackPressed()
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        if (flutterFragment == null) {
            return
        }
        flutterFragment!!.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        )
    }

    public override fun onUserLeaveHint() {
        if (flutterFragment == null) {
            return
        }
        flutterFragment!!.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (flutterFragment == null) {
            return
        }
        flutterFragment!!.onTrimMemory(level)
    }

    override fun onDestroy() {
        super.onDestroy()
    } //    getMain
}