package com.song.tasty.common.app.net.interceptors;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.song.tasty.common.app.KVConstants;
import com.song.tasty.common.app.net.log.DefaultFormatPrinter;
import com.song.tasty.common.app.net.log.FormatPrinter;
import com.song.tasty.common.core.utils.CharacterHandler;
import com.song.tasty.common.core.utils.DeviceUtils;
import com.song.tasty.common.core.utils.UrlEncoderUtils;
import com.song.tasty.common.core.utils.ZipHelper;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @date : 2019-08-07 10:51
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class RequestInterceptor implements Interceptor {

    private Context context;
    private FormatPrinter printer;

    public RequestInterceptor(Context context) {
        super();
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        final Request.Builder builder = chain.request().newBuilder();
        //添加请求头
        builder.addHeader("client-method", chain.request().method());
        builder.addHeader("client-source", "app");
        builder.addHeader("client-version", DeviceUtils.getVersionName(context));
        builder.addHeader("client-platform", "android");
        builder.addHeader("client-platform_version", Build.VERSION.RELEASE);


        String deviceid = MMKV.defaultMMKV().decodeString(KVConstants.KV_DEVICEID);
        if (!TextUtils.isEmpty(deviceid)) {
            builder.addHeader("client-deviceid", deviceid);
        } else {
            deviceid = DeviceUtils.getIMEI(context);
            if (!TextUtils.isEmpty(deviceid)) {
                MMKV.defaultMMKV().putString(KVConstants.KV_DEVICEID, deviceid);
                builder.addHeader("client-deviceid", deviceid);
            }
        }

        builder.addHeader("client-deviceid", DeviceUtils.getIMEI(context));

//        MMKV kv = MMKV.defaultMMKV();
//        String clientToken = kv.decodeString(KVConstants.KV_USERID);
//        builder.addHeader("client-token", clientToken);
        String cookie = MMKV.defaultMMKV().decodeString(KVConstants.KV_COOKIE);
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("cookie", cookie);
        }


        long t1 = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        long t2 = System.nanoTime();
        log(request, response, t2 - t1);
        return response;
    }


    private void log(Request request, Response response, long costTime) {
        ResponseBody responseBody = response.body();
        if (printer == null) {
            printer = new DefaultFormatPrinter();
        }

        if (request.body() != null && isParseable(request.body().contentType())) {
            try {
                printer.printJsonRequest(request, parseParams(request));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            printer.printFileRequest(request);
        }


        //打印响应结果
        String bodyString = null;
        if (responseBody != null && isParseable(responseBody.contentType())) {
            try {
                bodyString = printResult(request, response, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final List<String> segmentList = request.url().encodedPathSegments();
        final String header = response.headers().toString();
        final int code = response.code();
        final boolean isSuccessful = response.isSuccessful();
        final String message = response.message();
        final String url = response.request().url().toString();

        if (responseBody != null && isParseable(responseBody.contentType())) {
            printer.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(costTime), isSuccessful,
                    code, header, responseBody.contentType(), bodyString, segmentList, message, url);
        } else {
            printer.printFileResponse(TimeUnit.NANOSECONDS.toMillis(costTime),
                    isSuccessful, code, header, segmentList, message, url);
        }


    }


    /**
     * 打印响应结果
     *
     * @param request     {@link Request}
     * @param response    {@link Response}
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     * @throws IOException
     */
    @Nullable
    private String printResult(Request request, Response response, boolean logResponse) throws IOException {
        try {
            //读取服务器返回的结果
            ResponseBody responseBody = response.newBuilder().build().body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            //获取content的压缩类型
            String encoding = response
                    .headers()
                    .get("Content-Encoding");

            Buffer clone = buffer.clone();

            //解析response content
            return parseContent(responseBody, encoding, clone);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    /**
     * 解析服务器响应的内容
     *
     * @param responseBody {@link ResponseBody}
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content 使用 gzip 压缩
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));//解压
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {//content 使用 zlib 压缩
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));//解压
        } else {//content 没有被压缩, 或者使用其他未知压缩方式
            return clone.readString(charset);
        }
    }

    /**
     * 解析请求服务器的请求参数
     *
     * @param request {@link Request}
     * @return 解析后的请求信息
     * @throws UnsupportedEncodingException
     */
    public static String parseParams(Request request) throws UnsupportedEncodingException {
        try {
            RequestBody body = request.newBuilder().build().body();
            if (body == null) {
                return "";
            }
            Buffer requestbuffer = new Buffer();
            body.writeTo(requestbuffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            String json = requestbuffer.readString(charset);
            if (UrlEncoderUtils.hasUrlEncoded(json)) {
                json = URLDecoder.decode(json, convertCharset(charset));
            }
            return CharacterHandler.jsonFormat(json);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }


    /**
     * 是否可以解析
     *
     * @param mediaType {@link MediaType}
     * @return {@code true} 为可以解析
     */
    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null || mediaType.type() == null) {
            return false;
        }
        return isText(mediaType) || isPlain(mediaType)
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isText(MediaType mediaType) {
        if (mediaType == null || mediaType.type() == null) {
            return false;
        }
        return mediaType.type().equals("text");
    }

    public static boolean isPlain(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("plain");
    }

    public static boolean isJson(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("json");
    }

    public static boolean isXml(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("xml");
    }

    public static boolean isHtml(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("html");
    }

    public static boolean isForm(MediaType mediaType) {
        if (mediaType == null || mediaType.subtype() == null) {
            return false;
        }
        return mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }
        return s.substring(i + 1, s.length() - 1);
    }
}
