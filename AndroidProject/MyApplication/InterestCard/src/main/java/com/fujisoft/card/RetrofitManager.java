package com.fujisoft.card;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class RetrofitManager {
    String BASEURL = "http://172.29.140.35:5004/Card/";
    RetrofitAPIService retrofitAPIService;
    private static RetrofitManager apiManager;

    //    private Result result;
    public interface Result<T> {
        void getResult(T t);
    }

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
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitAPIService = retrofit.create(RetrofitAPIService.class);
        return retrofitAPIService;
    }

    public void getCards(final Result result) {
        RetrofitManager
                .getInstance()
                .getRetrofitAPIService()
                .getCards()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Card>>() {
                    @Override
                    public void accept(List<Card> cards) throws Exception {
                        if (result != null) {
                            result.getResult(cards);
                        }
                    }
                });
    }

    public void changeInterestById(int id, int num, final Result result) {
        RetrofitManager
                .getInstance()
                .getRetrofitAPIService()
                .changeInterestById(id,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (result != null) {
                            result.getResult(s);
                        }
                    }
                });
    }
}
