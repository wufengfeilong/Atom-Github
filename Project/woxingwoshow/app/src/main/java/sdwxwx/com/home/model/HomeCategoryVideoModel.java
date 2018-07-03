package sdwxwx.com.home.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.home.bean.BannerBean;
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
public class HomeCategoryVideoModel {
    public static void getBanners(final BaseCallback<List<BannerBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("type", "banner");
        mHashMap.put("size", "5");
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getBanners("banner", "5", times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<BannerBean>>>() {
            @Override
            public void accept(HttpResult<List<BannerBean>> result) throws Exception {
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

    public static void getVideoList(String member_id,String is_liked, String category_id, String topic_id, String owner_id, String page
            , final BaseCallback<List<PlayVideoBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("is_liked", is_liked);
        mHashMap.put("category_id", category_id);
        mHashMap.put("topic_id", topic_id);
        mHashMap.put("owner_id", owner_id);
        mHashMap.put("page", page);
        mHashMap.put("size", "20");
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getVideoCollection(member_id,is_liked, category_id,topic_id,owner_id,page,"20", times, signature).subscribeOn(Schedulers.io())
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
    public static void getMyUpVideoList(String member_id,String is_liked, String category_id, String topic_id, String owner_id, String page
            , final BaseCallback<List<PlayVideoBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("is_liked", is_liked);
        mHashMap.put("category_id", category_id);
        mHashMap.put("topic_id", topic_id);
        mHashMap.put("owner_id", owner_id);
        mHashMap.put("page", page);
        mHashMap.put("size", 20);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getVideoCollection(member_id,is_liked, category_id,topic_id,owner_id,page,"20", times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<PlayVideoBean>>>() {
            @Override
            public void accept(HttpResult<List<PlayVideoBean>> result) throws Exception {
                if (result.getCode() == 0) {
                    List<PlayVideoBean> list = result.getData();
                    if (list.size()>0) {
                        callback.onSuccess(result.getData());
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
