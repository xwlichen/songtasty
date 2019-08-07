package com.song.tasty.common.app.net.interceptors;

import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.util.List;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Response;

import static com.song.tasty.common.app.KVConstants.KV_COOKIE;

/**
 * @date : 2019-08-07 14:10
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> cookies = originalResponse.headers("Set-Cookie");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < cookies.size(); j++) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(cookies.get(j));
            }
            MMKV.defaultMMKV().encode(KV_COOKIE, sb.toString());
        }
        return originalResponse;
    }
}
