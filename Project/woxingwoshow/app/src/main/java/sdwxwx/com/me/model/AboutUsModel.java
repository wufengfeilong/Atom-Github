package sdwxwx.com.me.model;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.me.bean.AboutBean;
import sdwxwx.com.me.bean.AgreementBean;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 860115025 on 2018/06/06.
 */

public class AboutUsModel {

    /**
     * 添加反馈
     *
     * @param callback
     */
/*    public static void getAgreement(final BaseCallback<List<AgreementBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);

        // 调用接口
        APIClient.getAPIService().systemAgreement(times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<AgreementBean>>>() {
            @Override
            public void accept(HttpResult<List<AgreementBean>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess(result.getData());
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
    }*/

    public static void getAboutUs(final BaseCallback<List<AboutBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);
        // 调用接口
        APIClient.getAPIService().systemAbout(times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<AboutBean>>>() {
            @Override
            public void accept(HttpResult<List<AboutBean>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess(result.getData());
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
