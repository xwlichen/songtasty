package com.song.tasty.common.core.observer;

import com.song.tasty.common.core.base.BaseView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

/**
 * @date : 2019-07-22 18:07
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ToastObserver<T extends String> implements Observer<T> {

    public static <T extends String> ToastObserver<T> create(@NonNull BaseView view) {
        return new ToastObserver<T>(view);
    }

    private final BaseView view;

    private ToastObserver(BaseView view) {
        this.view = view;
    }

    @Override
    public void onChanged(String msg) {
        view.toast(msg);
    }
}
