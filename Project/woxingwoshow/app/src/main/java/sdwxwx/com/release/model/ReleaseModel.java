package sdwxwx.com.release.model;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.util.NetworkUtils;

public class ReleaseModel {
    private static final String TAG = "ReleaseModel";

    /**
     * 4.21获取敏感词列表
     *
     * @param callback
     */
    public static void getSensitiveList(final BaseCallback<List<String>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        Log.d(TAG, "getSensitiveList  times = " + times + "\n signature = " + signature);
        APIClient.getAPIService().getSensitiveList(times, signature).subscribeOn(Schedulers.io())
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

    /**
     * 4.23添加视频
     *
     * @param ksyun_id 视频的金山云编号
     * @param category_id 栏目编号
     * @param topic_id 话题编号
     * @param topic_title 话题标题
     * @param city_id 视频所在的城市编号
     * @param music_id 视频音乐编号
     * @param title 标题
     * @param description 描述
     * @param longitude 经度
     * @param latitude 纬度
     * @param cover_time 封面的截取时间点
     * @param at @通知的目标会员的编号集合
     * @param callback 回调
     */
    public static void addVideoToServer(String ksyun_id, String category_id,
                                String topic_id, String topic_title, String city_id, String music_id, String title,
                                String description, String longitude, String latitude, String duaration,
                                String cover_time, String at,
                                final BaseCallback callback) {

        String member_id = LoginHelper.getInstance().getUserId();
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);

        // 调用接口
        APIClient.getAPIService().addVideo(times, signature, member_id, ksyun_id, category_id,
                topic_id, topic_title, city_id, music_id, title,
                description, longitude, latitude, duaration, cover_time, at).subscribeOn(Schedulers.io())
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
