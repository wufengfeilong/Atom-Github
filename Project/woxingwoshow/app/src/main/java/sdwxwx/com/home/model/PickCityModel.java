package sdwxwx.com.home.model;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * create by 860115039
 * date      2018/5/29
 * time      14:36
 */
public class PickCityModel {

    public static void getCities(final BaseCallback<List<CityEntity>> callback){
        HashMap<String,Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getCities(times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<CityEntity>>>() {
            @Override
            public void accept(HttpResult<List<CityEntity>> result) throws Exception {
                if (result.getCode()==0) {
                    callback.onSuccess(result.getData());
                }else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
}
