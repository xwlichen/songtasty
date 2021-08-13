package com.song.tasty.common.core.livedata;


import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.smart.utils.LogUtils;


/**
 * @date : 2019-07-22 17:29
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SingleLiveData<T> extends MutableLiveData<T> {

    private static final String TAG = SingleLiveData.class.getSimpleName();

    private final AtomicBoolean pending = new AtomicBoolean(false);


    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if (hasActiveObservers()) {
            LogUtils.w(TAG, "multiple observers registered but only one will be notified of changes.");
        }
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }


    @MainThread
    @Override
    public void setValue(@Nullable T t) {
        pending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }
}
