package sdwxwx.com.me.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 添加反馈
 * Created by 860115025 on 2018/06/06.
 */
public class FeedBackModel {

    /**
     * 添加反馈
     * @param member_id 会员编号
     * @param content 反馈内容
     * @param callback
     */
    public static void addFeedback(String member_id, String content, final BaseCallback<List<String>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);

        // 调用接口
        APIClient.getAPIService().addFeedback(times,signature,member_id,content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<String>>>() {
            @Override
            public void accept(HttpResult<List<String>> result) throws Exception {
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
