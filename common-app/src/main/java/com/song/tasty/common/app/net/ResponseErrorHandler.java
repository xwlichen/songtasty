package com.song.tasty.common.app.net;

import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.hjq.toast.ToastUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * @author lichen
 * @date ：2019-08-08 22:05
 * @email : 196003945@qq.com
 * @description :
 */
public class ResponseErrorHandler implements Consumer<Throwable> {
    @Override
    public void accept(Throwable throwable) throws Exception {

        String msg = "未知错误";
        if (throwable instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (throwable instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            msg = convertStatusCode(httpException);
        } else if (throwable instanceof JsonParseException ||
                throwable instanceof ParseException ||
                throwable instanceof JSONException ||
                throwable instanceof JsonIOException) {
            msg = "数据解析错误";
        }
        ToastUtils.show(msg);

    }


    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}