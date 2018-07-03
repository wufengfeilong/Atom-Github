package sdwxwx.com.login.model;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public class UserAgreementModel {



    //请求注册条款
    public static void requestRegisterClause(final BaseCallback<String> callback){

        HashMap<String,Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);
        // 调用接口
        APIClient.getAPIService().systemAgreement(times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<String>>>() {
            @Override
            public void accept(HttpResult<List<String>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess(result.getData().get(0));
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
