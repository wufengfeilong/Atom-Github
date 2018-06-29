package com.fcn.park.base.http;

import android.content.Context;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.fcn.park.R;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 860115001 on 2018/04/03.
 */

public class RetrofitManager {

    private static final int DEFAULT_TIMEOUT = 500;

    private Retrofit mRetrofit2Object;

    private volatile static RetrofitManager sRetrofitManager;
    private OkHttpClient.Builder mClientBuilder;
    private Gson gson;

    private static WeakReference<Context> mContext;


    private RetrofitManager() {

    }

    public OkHttpClient.Builder getHttpClient() {
        return mClientBuilder;
    }

    public static RetrofitManager getInstance() {
        if (sRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (sRetrofitManager == null) {
                    sRetrofitManager = new RetrofitManager();
                }
            }
        }
        return sRetrofitManager;
    }

    public void init(Context context) {
        mContext = new WeakReference<Context>(context);
        //手动创建一个OkHttpClient并设置超时时间
        File cacheFile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSize);
        mClientBuilder = new OkHttpClient.Builder();
        mClientBuilder.addInterceptor(new LogInterceptor());
        mClientBuilder.cache(cache);
        mClientBuilder.readTimeout(10, TimeUnit.SECONDS);
        mClientBuilder.connectTimeout(7, TimeUnit.SECONDS);
        mClientBuilder.writeTimeout(10, TimeUnit.SECONDS);

        mRetrofit2Object = initRetrofit2Object();
    }

    public Retrofit getRetrofit() {
        if (mRetrofit2Object == null) {
            mRetrofit2Object = initRetrofit2Object();
        }
        return mRetrofit2Object;
    }


    private Retrofit initRetrofit2Object() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(mClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }


    /**
     * 用于将Observable和Subscriber的绑定
     *
     * @param <T>
     * @param observable
     * @param subscriber
     */
    public static <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        if (mContext.get() != null) {
            if (!NetworkUtils.isConnected(mContext.get())) {
                Toast.makeText(mContext.get(), R.string.no_internet, Toast.LENGTH_SHORT).show();
                return;
            }
        }
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(chain.request());
            try {
                String parmas = "";
                if (request.method().equals("GET")) {
                    parmas = request.url().encodedQuery();
                } else {
                    if (request.body() instanceof FormBody) {
                        FormBody body = (FormBody) request.body();
                        for (int i = 0; i < body.size(); i++) {
                            parmas += (body.encodedName(i) + " = " + body.encodedValue(i));
                            if (i + 1 < body.size()) {
                                parmas += " & ";
                            }
                        }
                    }
                }
                LogUtils.v("request:" + request.toString() + "\n参数 = " + parmas);
                long t1 = System.nanoTime();

                long t2 = System.nanoTime();
                LogUtils.v(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                okhttp3.MediaType mediaType = response.body().contentType();
                String content = response.body().string();
                LogUtils.json(content);
                return response.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.newBuilder()
                    .build();
        }
    }
}
