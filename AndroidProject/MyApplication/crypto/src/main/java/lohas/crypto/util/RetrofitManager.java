package lohas.crypto.util;

import lohas.crypto.api.RetrofitAPIService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    public static final String BASE_URL = "http://172.29.140.35:5006/crypto/";
    RetrofitAPIService retrofitAPIService;
    private static RetrofitManager apiManager;

    //获取ApiManager的单例
    public static RetrofitManager getInstance() {
        if (apiManager == null) {
            synchronized (RetrofitManager.class) {
                if (apiManager == null) {
                    apiManager = new RetrofitManager();
                }
            }
        }
        return apiManager;
    }

    public RetrofitAPIService getRetrofitAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitAPIService = retrofit.create(RetrofitAPIService.class);
        return retrofitAPIService;
    }
}
