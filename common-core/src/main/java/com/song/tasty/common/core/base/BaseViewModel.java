package com.song.tasty.common.core.base;

import android.app.Application;

import com.song.tasty.common.core.enums.ViewStatus;
import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.common.core.observer.ToastObserver;
import com.song.tasty.common.core.observer.ViewStatusObserver;
import com.song.tasty.common.core.utils.Preconditions;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @date : 2019-07-22 13:52
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IViewModel, Consumer<Disposable> {

    protected M model;

    /**
     * 控制界面的显示4个状态{@link ViewStatus}
     */
    protected final SingleLiveData<ViewStatus> viewStatusSource = new SingleLiveData<>();


    /**
     * 控制toast显示和内容
     */
    protected final SingleLiveData<String> toastSource = new SingleLiveData<>();

    /**
     * {@link Disposable}容器在不需要的时候取消所有订阅防止内存泄漏
     */
    private CompositeDisposable compositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        this(application, null);

    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
        compositeDisposable = new CompositeDisposable();
    }


    /**
     * 把当前的订阅放入池中，方便销毁
     *
     * @param disposable
     */
    protected void addSubcribe(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * {@link AndroidViewModel}的onClear 生命周期绑定
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (this.model != null) {
            model.onCleared();
        }
        compositeDisposable.clear();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void unregisterRxBus() {

    }

    @Override
    public void accept(Disposable disposable) throws Exception {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add((Disposable) this);
    }


    public void setViewStatusSourceObserver(@NonNull LifecycleOwner owner, @NonNull ViewStatusObserver observer) {
        Preconditions.checkNotNull(owner);
        Preconditions.checkNotNull(observer);
        viewStatusSource.observe(owner, observer);
    }


    public void setToastSource(@NonNull LifecycleOwner owner,
                               @NonNull ToastObserver toastObserver) {
        Preconditions.checkNotNull(owner);
        Preconditions.checkNotNull(toastObserver);
        toastSource.observe(owner, toastObserver);
    }


    protected void onDataRetry() {

    }
}
