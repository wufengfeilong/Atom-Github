package sdwxwx.com.http;

import sdwxwx.com.cons.Constant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * create by 860115039
 * date      2018/5/8
 * time      12:45
 */
public class RetrofitManager {

    APIService apiService;
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

    public APIService getAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.HTTP_BASE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

}
