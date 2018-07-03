package sdwxwx.com.release.model;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

public class AddTopicModel {
    private static final String TAG = "AddTopicModel";

    public void getAddTopicList(String keyword, String page, String size, final BaseCallback<List<TopicInfoBean>> callback) {
        Log.d(TAG, "getAddTopicList start");
        Map<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("keyword", keyword);
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService()
                .getAddTopicList(keyword, page, size, times, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HttpResult<List<TopicInfoBean>>>() {
                    @Override
                    public void accept(HttpResult<List<TopicInfoBean>> listHttpResult) throws Exception {
                        if (listHttpResult.getCode() == 0) {
                            callback.onSuccess(listHttpResult.getData());
                        } else {
                            callback.onFail(listHttpResult.getMessage());
                        }
                    }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });

        Log.d(TAG, "getAddTopicList end");
    }

}
