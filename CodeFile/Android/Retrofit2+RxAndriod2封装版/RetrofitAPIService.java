package com.fujisoft.card;

import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.List;

public interface RetrofitAPIService {
    @GET("getCards")
    Observable<List<Card>> getCards();
//    @GET("getById/{id}")
//    Observable<Card> getByIath("id") int id);
    @FormUrlEncoded
    @POST("changeInterestById")
    Observable<String> changeInterestById(@Field("id") int id,@Field("num") int num);
}
