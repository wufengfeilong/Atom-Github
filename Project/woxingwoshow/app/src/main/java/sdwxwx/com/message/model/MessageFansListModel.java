package sdwxwx.com.message.model;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 860117073 on 2018/6/8.
 */

public class MessageFansListModel {
    //获取粉丝列表
    public static void getMessageFansList(String member_id,String page,String size
            , final BaseCallback<List<RecommendUserBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        //获取member_id
        mHashMap.put("member_id", member_id);
        //获取page和size
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        //获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getFollowerUserlist(member_id, page,size,times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<RecommendUserBean>>>() {
            @Override
            public void accept(HttpResult<List<RecommendUserBean>> result) throws Exception {
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

    //获取关注列表
    public static void getMessageAttentionsList(String member_id,String page,String size
            , final BaseCallback<List<RecommendUserBean>> callback) {
        //获取member_id
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        //获取page和size
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        //获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        //获取签名
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getFollowedUserlist(member_id,page,size, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<RecommendUserBean>>>() {
            @Override
            public void accept(HttpResult<List<RecommendUserBean>> result) throws Exception {
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
