package lohas.crypto.api;



import io.reactivex.Observable;
import lohas.crypto.Bean.Result;
import lohas.crypto.Bean.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface RetrofitAPIService {
    @GET("testStr")
    Observable<String> ctyptoTest();

//    @GET("testObj")
//    Observable<Result> ctyptoTest();

    @POST("login")
    Observable<Result> login(@Body User user);
}
