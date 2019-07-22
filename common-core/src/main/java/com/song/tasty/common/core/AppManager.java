package com.song.tasty.common.core;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.song.tasty.common.core.utils.LogUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lichen
 * @date ：2019-07-22 22:34
 * @email : 196003945@qq.com
 * @description :
 */
public final class AppManager {


    protected final String TAG = this.getClass().getSimpleName();
    /**
     * true 为不需要加入到 Activity 容器进行统一管理,默认为 false
     */
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_not_add_activity_list";
    private static volatile AppManager appManager;
    private Application application;
    /**
     * 管理所有存活的 Activity, 容器中的顺序仅仅是 Activity 的创建顺序, 并不能保证和 Activity 任务栈顺序一致
     */
    private List<Activity> activityList;
    /**
     * 当前在前台的 Activity
     */
    private Activity currentActivity;


    public static AppManager getAppManager() {
        if (appManager == null) {
            synchronized (AppManager.class) {
                if (appManager == null) {
                    appManager = new AppManager();
                }
            }
        }
        return appManager;
    }

    public AppManager init(Application application) {
        this.application = application;
        return appManager;
    }


    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getTopActivity() == null) {
            LogUtils.w(TAG, "currentActivity == null when startActivity(Intent)");
            //如果没有前台的activity就使用new_task模式启动activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
            return;
        }
        getTopActivity().startActivity(intent);
    }

    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        startActivity(new Intent(application, activityClass));
    }

    /**
     * 释放资源
     */
    public void release() {
        activityList.clear();
        activityList = null;
        currentActivity = null;
        application = null;
    }


    @Nullable
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * 获取最近启动的一个 {@link Activity}, 此方法不保证获取到的 {@link Activity} 正处于前台可见状态
     * 即使 App 进入后台或在这个 {@link Activity} 中打开一个之前已经存在的 {@link Activity}, 这时调用此方法
     * 还是会返回这个最近启动的 {@link Activity}, 因此基本不会出现 {@code null} 的情况
     *
     * @return
     */
    @Nullable
    public Activity getTopActivity() {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when getTopActivity()");
            return null;
        }
        return activityList.size() > 0 ? activityList.get(activityList.size() - 1) : null;
    }


    /**
     * 返回一个存储所有未销毁的 {@link Activity} 的集合
     *
     * @return
     */
    public List<Activity> getActivityList() {
        if (activityList == null) {
            activityList = new LinkedList<>();
        }
        return activityList;
    }

    /**
     * 添加 {@link Activity} 到集合
     */
    public void addActivity(Activity activity) {
        synchronized (AppManager.class) {
            List<Activity> activities = getActivityList();
            if (!activities.contains(activity)) {
                activities.add(activity);
            }
        }
    }

    /**
     * 删除集合里的指定的 {@link Activity} 实例
     *
     * @param {@link Activity}
     */
    public void removeActivity(Activity activity) {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when removeActivity(Activity)");
            return;
        }
        synchronized (AppManager.class) {
            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
        }
    }

    /**
     * 删除集合里的指定位置的 {@link Activity}
     *
     * @param location
     */
    public Activity removeActivity(int location) {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when removeActivity(int)");
            return null;
        }
        synchronized (AppManager.class) {
            if (location > 0 && location < activityList.size()) {
                return activityList.remove(location);
            }
        }
        return null;
    }

    /**
     * 关闭指定的 {@link Activity} class 的所有的实例
     *
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass) {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when killActivity(Class)");
            return;
        }
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = getActivityList().iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();

                if (next.getClass().equals(activityClass)) {
                    iterator.remove();
                    next.finish();
                }
            }
        }
    }

    /**
     * 指定的 {@link Activity} 实例是否存活
     *
     * @param {@link Activity}
     * @return
     */
    public boolean activityInstanceIsLive(Activity activity) {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when activityInstanceIsLive(Activity)");
            return false;
        }
        return activityList.contains(activity);
    }

    /**
     * 指定的 {@link Activity} class 是否存活(同一个 {@link Activity} class 可能有多个实例)
     *
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when activityClassIsLive(Class)");
            return false;
        }
        for (Activity activity : activityList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定 {@link Activity} class 的实例,没有则返回 null(同一个 {@link Activity} class 有多个实例,则返回最早创建的实例)
     *
     * @param activityClass
     * @return
     */
    public Activity findActivity(Class<?> activityClass) {
        if (activityList == null) {
            LogUtils.w(TAG, "activityList == null when findActivity(Class)");
            return null;
        }
        for (Activity activity : activityList) {
            if (activity.getClass().equals(activityClass)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 关闭所有 {@link Activity}
     */
    public void killAll() {
//        while (getActivityList().size() != 0) { //此方法只能兼容LinkedList
//            getActivityList().remove(0).finish();
//        }
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = getActivityList().iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                iterator.remove();
                next.finish();
            }
        }
    }

    /**
     * 关闭所有 {@link Activity},排除指定的 {@link Activity}
     *
     * @param excludeActivityClasses activity class
     */
    public void killAll(Class<?>... excludeActivityClasses) {
        List<Class<?>> excludeList = Arrays.asList(excludeActivityClasses);
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = getActivityList().iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();

                if (excludeList.contains(next.getClass())) {
                    continue;
                }

                iterator.remove();
                next.finish();
            }
        }
    }

    /**
     * 关闭所有 {@link Activity},排除指定的 {@link Activity}
     *
     * @param excludeActivityName {@link Activity} 的完整全路径
     */
    public void killAll(String... excludeActivityName) {
        List<String> excludeList = Arrays.asList(excludeActivityName);
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = getActivityList().iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();

                if (excludeList.contains(next.getClass().getName())) {
                    continue;
                }

                iterator.remove();
                next.finish();
            }
        }
    }

    /**
     * 退出应用程序
     * <p>
     * 此方法经测试在某些机型上并不能完全杀死 App 进程, 几乎试过市面上大部分杀死进程的方式, 但都发现没卵用, 所以此
     * 方法如果不能百分之百保证能杀死进程, 就不能贸然调用 {@link #release()} 释放资源, 否则会造成其他问题, 如果您
     * 有测试通过的并能适用于绝大多数机型的杀死进程的方式, 望告知
     */
    public void appExit() {
        try {
            killAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
