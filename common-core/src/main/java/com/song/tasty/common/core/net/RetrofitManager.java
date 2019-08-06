package com.song.tasty.common.core.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.song.tasty.common.core.AppManager;
import com.song.tasty.common.core.Constants;
import com.song.tasty.common.core.cache.Cache;
import com.song.tasty.common.core.cache.CacheType;
import com.song.tasty.common.core.cache.IntelligentCache;
import com.song.tasty.common.core.cache.LruCache;
import com.song.tasty.common.core.utils.FileUtils;
import com.song.tasty.common.core.utils.Preconditions;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @date : 2019-08-06 15:52
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class RetrofitManager {

    private final static int CONNECT_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;
    private final static int WRITE_TIMEOUT = 30;


    private static Retrofit retrofit;
    private static Retrofit.Builder retrofiitBuilder;
    private static OkHttpClient.Builder okHttpClientBuilder;


    private RxCache rxCache;
    private Cache.Factory cacheFactory;
    private Cache<String, Object> retrofitServiceCache;
    private Cache<String, Object> cacheServiceCache;


    private Gson gson;


    Retrofit getRetrofitBuilder(String baseUrl) {
        if (retrofiitBuilder == null) {
            synchronized (RetrofitManager.class) {
                OkHttpClient okHttpClient = RetrofitUrlManager
                        .getInstance()
                        .with(getOkHttpClientBuilder(true))
                        .build();
                retrofiitBuilder = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(provideGson()))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            }
        }
        return retrofiitBuilder.build();
    }


    OkHttpClient.Builder getOkHttpClientBuilder(boolean isResponseParamInterceptor) {
//
//        RequestParamInterceptor requestParamInterceptor = new RequestParamInterceptor();
//
//        ResponseParamInterceptor responseParamInterceptor = new ResponseParamInterceptor();
//
//


        if (okHttpClientBuilder == null) {
            synchronized (RetrofitManager.class) {
                okHttpClientBuilder = new OkHttpClient
                        .Builder()
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        //.cookieJar(cookieJar)
                        //.authenticator(new TokenAuth())
                        //.addInterceptor(headerInterceptor)
                        // https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
                        //.sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())
                        //.hostnameVerifier(new SafeHostnameVerifier())
                        .addInterceptor(requestParamInterceptor);

                if (isResponseParamInterceptor) {
                    okHttpClientBuilder.addInterceptor(responseParamInterceptor);
                }
            }
        }
        return okHttpClientBuilder;
    }


    public synchronized <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass);
    }


    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    private <T> T createWrapperService(Class<T> serviceClass) {
        Preconditions.checkNotNull(serviceClass, "serviceClass == null");

        // 二次代理
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                            throws Throwable {
                        // 此处在调用 serviceClass 中的方法时触发

                        if (method.getReturnType() == Observable.class) {
                            // 如果方法返回值是 Observable 的话，则包一层再返回，
                            // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
                            // 如此对原项目的影响为 0，且更可控。
                            return Observable.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Observable) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        } else if (method.getReturnType() == Single.class) {
                            // 如果方法返回值是 Single 的话，则包一层再返回。
                            return Single.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                // 执行真正的 Retrofit 动态代理的方法
                                return ((Single) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        }

                        // 返回值不是 Observable 或 Single 的话不处理。
                        final T service = getRetrofitService(serviceClass);
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }


    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (retrofitServiceCache == null) {
            retrofitServiceCache = provideCacheFactory().build(CacheType.RETROFIT_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(retrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T retrofitService = (T) retrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = retrofit.create(serviceClass);
            retrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }


    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cacheClass Cache class
     * @param <T>        Cache class
     * @return Cache
     */
    @NonNull
    public synchronized <T> T obtainCacheService(@NonNull Class<T> cacheClass) {
        Preconditions.checkNotNull(cacheClass, "cacheClass == null");
        if (cacheServiceCache == null) {
            cacheServiceCache = provideCacheFactory().build(CacheType.CACHE_SERVICE_CACHE);
        }
        Preconditions.checkNotNull(cacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) cacheServiceCache.get(cacheClass.getCanonicalName());
        if (cacheService == null) {
            cacheService = provideRxCache().using(cacheClass);
            cacheServiceCache.put(cacheClass.getCanonicalName(), cacheService);
        }
        return cacheService;
    }

    /**
     * 清理所有缓存
     */
    public void clearAllCache() {
        provideRxCache().evictAll().subscribe();
    }


    Cache.Factory provideCacheFactory() {
        return cacheFactory == null ? new Cache.Factory() {
            @NonNull
            @Override
            public Cache build(CacheType type) {
                //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
                //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展
                switch (type.getCacheTypeId()) {
                    //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                    case CacheType.EXTRAS_TYPE_ID:
                    case CacheType.ACTIVITY_CACHE_TYPE_ID:
                    case CacheType.FRAGMENT_CACHE_TYPE_ID:
                        return new IntelligentCache(type.calculateCacheSize(AppManager.getAppManager().getApplication()));
                    //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                    default:
                        return new LruCache(type.calculateCacheSize(AppManager.getAppManager().getApplication()));
                }
            }
        } : cacheFactory;
    }


    RxCache provideRxCache() {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
//        if (configuration != null) {
//            rxCache = configuration.configRxCache(application, builder);
//        }
        if (rxCache != null) {
            return rxCache;
        }
        return builder
                .persistence(provideRxCacheDirectory(Constants.CACHE_DIR + "RxCache"), new GsonSpeaker(provideGson()));
    }


    Gson provideGson() {
        return this.gson == null ? new GsonBuilder().create() : gson;
    }


    File provideRxCacheDirectory(String path) {
        File cacheDirectory = new File(path);
        return FileUtils.makeDirs(cacheDirectory);
    }


}
