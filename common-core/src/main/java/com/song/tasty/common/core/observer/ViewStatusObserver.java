package com.song.tasty.common.core.observer;

import com.song.tasty.common.core.base.BaseView;
import com.song.tasty.common.core.enums.ViewStatus;
import com.song.tasty.common.core.utils.Preconditions;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

/**
 * @date : 2019-07-22 17:26
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ViewStatusObserver implements Observer<ViewStatus> {


    private final BaseView view;

    private ViewStatusObserver(BaseView view) {
        this.view = view;
    }

    public static ViewStatusObserver create(@NonNull BaseView view) {
        Preconditions.checkNotNull(view);
        return new ViewStatusObserver(view);
    }

    @Override
    public void onChanged(ViewStatus viewStatus) {

        switch (viewStatus) {
            case COMPLETE:
                view.hideLoading();
                break;
            case PROGRESS:
                view.showLoading();
            case NO_DATA:
                view.hideLoading();
                view.showNoData();
                break;
            case NO_NETWORK:
                view.hideLoading();
                view.showNoNetWork();
                break;

            default:
                break;
        }

    }

}
