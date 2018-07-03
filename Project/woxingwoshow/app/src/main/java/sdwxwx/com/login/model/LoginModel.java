package sdwxwx.com.login.model;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.LoginCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public class LoginModel {
    /**
     * 用户登录
     * @param hashMap 登录用参数
     * @param callback
     */
    public static void loginUser(HashMap<String,Object> hashMap, final LoginCallback<UserBean> callback) {
        // 获取时间戳
        long times = APIClient.getTimes();
        hashMap.put("timestamp",times);
        // 取得签名
        String signature = APIClient.getSign(hashMap);
        // 调用登陆接口
        APIClient.getAPIService().memberLogin(hashMap.get("mobile").toString(), hashMap.get("code").toString(),
                hashMap.get("wechat_id").toString(),hashMap.get("qq_id").toString(),times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<UserBean>>>() {
            @Override
            public void accept(HttpResult<List<UserBean>> result) throws Exception {
                // 如果登陆成功，则返回取得的用户数据，否则，直接返回错误信息
                if (result.getCode()==0) {
                    callback.onSuccess(result.getData().get(0));
                    //会员禁用
                } else if(result.getCode()==2040){
//                    callback.onFail(result.getMessage());
                    callback.onRegister();
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

    public static void getMemberInfo(String member_id, String target_member_id, final BaseCallback<UserBean> callback) {
        HashMap<String,Object> hashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        hashMap.put("target_member_id",target_member_id);
        hashMap.put("member_id",member_id);
        hashMap.put("timestamp",times);
        // 取得签名
        String signature = APIClient.getSign(hashMap);
        // 调用登陆接口
        APIClient.getAPIService().getMemberInfo(member_id,target_member_id,times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<UserBean>>>() {
            @Override
            public void accept(HttpResult<List<UserBean>> result) throws Exception {
                // 如果登陆成功，则返回取得的用户数据，否则，直接返回错误信息
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

}
