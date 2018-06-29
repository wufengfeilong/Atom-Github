package com.fujisoft.test.api;

import com.fujisoft.test.bean.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface RetrofitAPIService {
    @GET("getUsers")
    Observable<List<User>> getUsers();
    @GET("getById/{id}")
    Observable<User> getById(@Path("id") int id);
}
