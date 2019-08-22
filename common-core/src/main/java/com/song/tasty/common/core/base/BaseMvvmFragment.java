package com.song.tasty.common.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.tasty.common.core.observer.ToastObserver;
import com.song.tasty.common.core.observer.ViewStatusObserver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author lichen
 * @date ：2019-07-22 20:47
 * @email : 196003945@qq.com
 * @description :
 */
public abstract class BaseMvvmFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment implements BaseView {

    protected V binding;
    protected VM viewModel;
    private int viewModelId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewModel != null) {
            viewModel.unregisterRxBus();
        }

        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    protected void initViewCreate() {
        initViewDataBinding();
        subscribeViewEvent(viewModel);
        initViewObservable();
        viewModel.registerRxBus();
    }

    public abstract int initVariableId();

    public VM initViewModel() {
        return null;
    }

    public void initViewObservable() {

    }


    private void initViewDataBinding() {
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

        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
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
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }
}
