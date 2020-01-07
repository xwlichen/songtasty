package com.song.tasty.common.core.observer;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.song.tasty.common.core.base.BaseView;
import com.song.tasty.common.core.enums.ViewStatus;
import com.song.tasty.common.core.utils.Preconditions;

/**
 * @date : 2019-07-22 17:26
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ViewStatusObserver<V extends ViewStatus> implements Observer<V> {


    private final BaseView view;

    public ViewStatusObserver(BaseView view) {
        this.view = view;
    }

    public static <V extends ViewStatus> ViewStatusObserver<V> create(@NonNull BaseView view) {
        Preconditions.checkNotNull(view);
        return new ViewStatusObserver<V>(view);
    }

    @Override
    public void onChanged(ViewStatus viewStatus) {

        switch (viewStatus) {
            case COMPLETE:
                view.hideLoading();
                break;
            case LOADING:
                view.showLoading();
            case NO_DATA:
                view.hideLoading();
                view.showNoData();
                break;
            case NO_NETWORK:
                view.hideLoading();
                view.showNoNetWork();
                break;

            case ERROR:
                view.hideLoading();
                view.showError();
                break;

            default:
                break;
        }

    }

}
