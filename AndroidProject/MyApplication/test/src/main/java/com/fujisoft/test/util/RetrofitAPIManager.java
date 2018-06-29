package com.fujisoft.test.util;

import com.fujisoft.test.api.RetrofitAPIService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public  class RetrofitAPIManager {
    String BASEURL = "http://172.29.140.35:8082/";
    RetrofitAPIService retrofitAPIService;
    private static RetrofitAPIManager apiManager;
    //获取ApiManager的单例
    public static RetrofitAPIManager getInstance() {
        if (apiManager == null) {
            synchronized (RetrofitAPIManager.class) {
                if (apiManager == null) {
                    apiManager = new RetrofitAPIManager();
                }
            }
        }
        return apiManager;
    }
    public RetrofitAPIService getRetrofitAPIService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitAPIService = retrofit.create(RetrofitAPIService.class);
        return retrofitAPIService;
    }

}
