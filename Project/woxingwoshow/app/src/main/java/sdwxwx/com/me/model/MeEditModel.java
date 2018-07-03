package sdwxwx.com.me.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 860115025 on 2018/06/11.
 */

public class MeEditModel {


    /**
     * 编辑会员信息
     * @param member_id 会员ID
     * @param nickname 昵称
     * @param signature 个性签名
     * @param gender 性别
     * @param birthday 生日
     * @param avatar 头像地址
     * @param callback 回调
     */
    public static void editMemberInfo(String member_id, String nickname,String signature,
                                      String gender,String birthday,String avatar, final BaseCallback<List<String>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long timestamp = APIClient.getTimes();
        mHashMap.put("timestamp", timestamp);
        // 获取签名
        String sign = APIClient.getSign(mHashMap);

        // 如果上传的本地文件
        if (!avatar.startsWith("http:") && !avatar.startsWith("https:")) {
            File file = new File(avatar);
            // 调用接口
            APIClient.getAPIService().memberFileEdit(timestamp,sign,
                    APIClient.getRequestBody(member_id),
                    APIClient.getRequestBody(nickname),
                    APIClient.getRequestBody(signature),
                    APIClient.getRequestBody(gender),
                    APIClient.getRequestBody(birthday),
                    APIClient.getFileBody("avatar",file)).subscribeOn(Schedulers.io()).subscribeOn(Schedulers.io())
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
        } else {
            // 调用接口
            APIClient.getAPIService().memberEdit(timestamp,sign,member_id,nickname,signature,gender,birthday,
                    avatar).subscribeOn(Schedulers.io()).subscribeOn(Schedulers.io())
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
}
