package com.song.tasty.common.core.binding.view;

import android.view.View;

import com.jakewharton.rxbinding3.view.RxView;
import com.song.tasty.common.core.binding.command.BindingCommand;

import java.util.concurrent.TimeUnit;

import androidx.databinding.BindingAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @date : 2019-07-23 16:59
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ViewAdapter {
    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;


    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        if (isThrottleFirst) {
            Disposable disposable = RxView.clicks(view)
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        } else {
            Disposable disposable = RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object object) throws Exception {
                            if (clickCommand != null) {
                                clickCommand.execute();
                            }
                        }
                    });
        }
    }

}
