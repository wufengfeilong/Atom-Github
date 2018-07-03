package sdwxwx.com.near.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:50
 */
public class NearFragmentModel {

    public static void getVideoNearBy(String member_id, String city_id,String longitude, String latitude, String page
            , final BaseCallback<List<PlayVideoBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("city_id", city_id);
        mHashMap.put("longitude", longitude);
        mHashMap.put("latitude", latitude);
        mHashMap.put("page", page);
        mHashMap.put("size", "20");
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getVideoNearBy(member_id, city_id,longitude, latitude,page,"20", times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<PlayVideoBean>>>() {
            @Override
            public void accept(HttpResult<List<PlayVideoBean>> result) throws Exception {
                if (result.getCode() == 0) {
                    List<PlayVideoBean> list = result.getData();
                    if (list.size()>0) {
                        callback.onSuccess(list);
                    } else {
                        callback.onFail(result.getMessage());
                    }
                } else {
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
