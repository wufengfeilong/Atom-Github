package sdwxwx.com.me.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.me.bean.UpdateInfoBean;
import sdwxwx.com.util.NetworkUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/24
 * time      14:33
 */
public class SettingModel {

    /**
     * 检查更新
     * @param version
     * @param callback
     */
    public static void checkUpdate(String version, final BaseCallback<UpdateInfoBean> callback){
        HashMap<String,Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("version",version);
        mHashMap.put("system_type","android");
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().clientUpdate(version,"android",times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<UpdateInfoBean>>>() {
            @Override
            public void accept(HttpResult<List<UpdateInfoBean>> result) throws Exception {
                if (result.getCode()==0) {
                    callback.onSuccess(result.getData().get(0));
                }else {
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
     * 实名认证
     * @param member_id
     * @param real_name
     * @param id_card
     * @param front_image
     * @param back_image
     */
    public static void certificate(String member_id, String real_name, String id_card,
                                   String front_image, String back_image, final BaseCallback<List<String>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long timestamp = APIClient.getTimes();
        mHashMap.put("timestamp", timestamp);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);

        // 调用实名认证接口
        APIClient.getAPIService().memberCertificate(timestamp, signature,
                APIClient.getRequestBody(member_id),
                APIClient.getRequestBody(real_name),
                APIClient.getRequestBody(id_card),
                APIClient.getFileBody("front_image",new File(front_image)),
                APIClient.getFileBody("back_image",new File(back_image))).subscribeOn(Schedulers.io()).subscribeOn(Schedulers.io())
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
     * 注销会员
     * @param member_id
     * @param callback
     */
    public static void logout(String member_id, final BaseCallback<String> callback){
        HashMap<String,Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("member_id",member_id);
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().logout(times,signature,member_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<String>>>() {
            @Override
            public void accept(HttpResult<List<String>> result) throws Exception {
                if (result.getCode()==0) {
                    callback.onSuccess("");
                }else {
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
