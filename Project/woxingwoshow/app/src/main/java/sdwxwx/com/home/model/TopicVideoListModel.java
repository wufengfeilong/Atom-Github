package sdwxwx.com.home.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 860117073 on 2018/6/8.
 */

public class TopicVideoListModel {
    public static void getTopicVideo(String member_id, String owner_id, String is_liked, String category_id, String topic_id, String page
            , final BaseCallback<List<PlayVideoBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("owner_id", owner_id);
        mHashMap.put("is_liked", is_liked);
        mHashMap.put("category_id", category_id);
        mHashMap.put("topic_id", topic_id);
        mHashMap.put("page", page);
        mHashMap.put("size", Constant.DEFAULT_SIZE);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getVideoCollection(member_id,is_liked, category_id,topic_id,owner_id,page,Constant.DEFAULT_SIZE, times, signature).subscribeOn(Schedulers.io())
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
