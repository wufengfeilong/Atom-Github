package sdwxwx.com.login.model;

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
 * Created by 丁胜胜 on 2018/05/23.
 */

public class NotLoginInterfaceModel {

    public static void getVideoList(String member_id, String page
            , final BaseCallback<List<PlayVideoBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("page", page);
        mHashMap.put("size", "20");
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getVideoHomePage(member_id,page,"20", times, signature).subscribeOn(Schedulers.io())
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
