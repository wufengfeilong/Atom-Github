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
import sdwxwx.com.me.bean.RecommendWealthBean;
import sdwxwx.com.me.bean.VideoWealthBean;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 860117073 on 2018/6/8.
 */

public class MessageTeamAndWealthModel {
    public static void getMessageTeamList(String member_id
            , String level,final BaseCallback<List<RecommendUserBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("level", level);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().memberSubordinate(member_id,level,times, signature).subscribeOn(Schedulers.io())
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
//获取推荐财富列表
    public static void getWealthRecommend(String member_id
            , final BaseCallback<RecommendWealthBean> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().wealthRecommend(member_id, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<RecommendWealthBean>>>() {
            @Override
            public void accept(HttpResult<List<RecommendWealthBean>> result) throws Exception {
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
    //获取视频财富列表
    public static void getWealthVideo(String member_id
            , final BaseCallback<VideoWealthBean> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().wealthVideo(member_id, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<VideoWealthBean>>>() {
            @Override
            public void accept(HttpResult<List<VideoWealthBean>> result) throws Exception {
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
