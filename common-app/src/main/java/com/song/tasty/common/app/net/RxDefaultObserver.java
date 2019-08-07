package com.song.tasty.common.app.net;

import com.google.gson.JsonParseException;
import com.hjq.toast.ToastUtils;
import com.song.tasty.common.app.R;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

import static com.song.tasty.common.app.net.RxDefaultObserver.ExceptionReason.BAD_NETWORK;
import static com.song.tasty.common.app.net.RxDefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.song.tasty.common.app.net.RxDefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.song.tasty.common.app.net.RxDefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.song.tasty.common.app.net.RxDefaultObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * @date : 2019-08-07 15:26
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class RxDefaultObserver<T extends Object> implements Observer<T> {
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T response) {
//        if (response.OK()) {
//            onSuccess(response);
//        } else {
//            onFail(response);
//        }
        onResult(response);
    }

    @Override
    public void onError(Throwable e) {
        //HTTP错误
        if (e instanceof HttpException) {
            onException(BAD_NETWORK);
            //连接错误
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            onException(CONNECT_ERROR);
            //连接超时
        } else if (e instanceof InterruptedIOException) {
            onException(CONNECT_TIMEOUT);
            //解析错误
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    public void onSuccess(T response) {

    }

    public void onResult(T response) {

    }

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
//        String message = response.getMsg();
//        if (TextUtils.isEmpty(message)) {
        ToastUtils.show("服务器返回数据失败！");
//        } else {
//            ToastUtils.show(message);
//        }

    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.show(R.string.error_connect);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.show(R.string.error_timeout);
                break;

            case BAD_NETWORK:
                ToastUtils.show(R.string.error_bad_network);
                break;

            case PARSE_ERROR:
                ToastUtils.show(R.string.error_parse);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.show(R.string.error_unknown);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }

    private void cancelRequest() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
