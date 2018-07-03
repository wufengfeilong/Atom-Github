package sdwxwx.com.home.model;

import android.util.Log;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.home.contract.ContactBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * create by 860115039
 * date      2018/6/1
 * time      14:57
 */
public class SearchUserModel {

    public static void getRecommendTopic(String page,String size,final BaseCallback<List<TopicInfoBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getRecommendTopic(page, size, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<TopicInfoBean>>>() {
            @Override
            public void accept(HttpResult<List<TopicInfoBean>> result) throws Exception {
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

    public static void recommendUsers(String member_id, final BaseCallback<List<RecommendUserBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().recommendMembers(member_id, times, signature).subscribeOn(Schedulers.io())
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

    public static void memberVerify(String mobile, String member_id, final BaseCallback<List<ContactBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().memberVerify(times, signature, mobile, member_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<ContactBean>>>() {
            @Override
            public void accept(HttpResult<List<ContactBean>> result) throws Exception {
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


    /*匹配用户*/
    public static void matchUser(String member_id, String keyword, String is_followed,String page, String size, final BaseCallback<List<SearchUserBean.HaveUserBean>> callback) {
        Log.d("@@@@@@", "keyword = " + keyword);
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 对检索的字段进行编码处理
        try {
            keyword = URLEncoder.encode(keyword, Constant.ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHashMap.put("member_id", member_id);
        mHashMap.put("keyword", keyword);
        mHashMap.put("is_followed", is_followed);
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);

        APIClient.getAPIService().memberSearch(member_id, keyword, is_followed, page, size, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<SearchUserBean.HaveUserBean>>>() {
            @Override
            public void accept(HttpResult<List<SearchUserBean.HaveUserBean>> result) throws Exception {
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
