package woxingwoxiu.com.home.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.home.bean.CityEntity;
import woxingwoxiu.com.http.APIClient;
import woxingwoxiu.com.http.HttpResult;
import woxingwoxiu.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/29
 * time      14:36
 */
public class PickCityModel {

    public static void getCitys(final BaseCallback<List<CityEntity>> callback){
        HashMap<String,Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getCitys(times,signature).subscribeOn(Schedulers.io())
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
