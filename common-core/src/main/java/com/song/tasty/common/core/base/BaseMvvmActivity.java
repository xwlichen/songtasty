package com.song.tasty.common.core.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.song.tasty.common.core.livedata.SingleLiveData;
import com.song.tasty.common.core.observer.ToastObserver;
import com.song.tasty.common.core.observer.ViewStatusObserver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @date : 2019-07-22 12:01
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class BaseMvvmActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity implements BaseView {
    protected V binding;
    protected VM viewModel;

    private int viewModelId;


    @Override
    public void createView(int layoutResId, @Nullable Bundle savedInstanceState) {
        super.createView(layoutResId, savedInstanceState);
        initViewDataBinding(savedInstanceState);
        subscribeViewEvent(viewModel);
        initViewObservable();

        //注册RxBus
        viewModel.registerRxBus();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.unregisterRxBus();
        }

        if (binding != null) {
            binding.unbind();
        }
    }

    private void initViewDataBinding(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }

        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    private <T> SingleLiveData<T> createLiveData(SingleLiveData<T> liveData) {
        if (liveData == null) {
            liveData = new SingleLiveData<T>();
        }
        return liveData;
    }



    protected <VM extends BaseViewModel> void subscribeViewEvent(@NonNull VM viewModel) {


        viewModel.getUiChange().getViewStatusSource().observe(this, ViewStatusObserver.create(this));
        viewModel.getUiChange().getToastSource().observe(this, ToastObserver.create(this));

        viewModel.getUiChange().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                launchActivity(clz, bundle);

            }
        });

        viewModel.getUiChange().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                finish();

            }
        });
    }


    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();


    /**
     * 自定义初始化ViewModel(ViewModelProvider.NewInstanceFactory)
     * 不重载的化则调用默认的初始化
     *
     * @return
     */
    public VM initViewModel() {
        return null;
    }

    public void initViewObservable() {

    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }
}
