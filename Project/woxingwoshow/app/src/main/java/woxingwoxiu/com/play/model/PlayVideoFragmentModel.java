package woxingwoxiu.com.play.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.http.APIClient;
import woxingwoxiu.com.http.HttpResult;
import woxingwoxiu.com.play.bean.CommentBean;
import woxingwoxiu.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/17
 * time      17:25
 */
public class PlayVideoFragmentModel {

    public static void commentLike(String member_id, String comment_id, final BaseCallback<String> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("member_id", member_id);
        mHashMap.put("comment_id", comment_id);
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().commentLike(member_id, comment_id, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<String>>() {
            @Override
            public void accept(HttpResult<String> result) throws Exception {
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


    public static void commentUnlike(String member_id, String comment_id, final BaseCallback<String> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("member_id", member_id);
        mHashMap.put("comment_id", comment_id);
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().commentUnlike(member_id, comment_id, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<String>>() {
            @Override
            public void accept(HttpResult<String> result) throws Exception {
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


    public static void getComments(String video_id, final BaseCallback<List<CommentBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("video_id", video_id);
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getComments(video_id, times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<CommentBean>>>() {
            @Override
            public void accept(HttpResult<List<CommentBean>> result) throws Exception {
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

    public static void addComment(String video_id, String member_id, int comment_id, String content, final BaseCallback<List<CommentBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        String url = APIClient.getPostUrl("comment/add", times, signature);

        HashMap<String, Object> map = new HashMap<>();
        map.put("video_id", video_id);
        map.put("member_id", member_id);
        map.put("comment_id", comment_id);
        map.put("content", content);

        APIClient.getAPIService().addComment(url, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<CommentBean>>>() {
            @Override
            public void accept(HttpResult<List<CommentBean>> result) throws Exception {
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
