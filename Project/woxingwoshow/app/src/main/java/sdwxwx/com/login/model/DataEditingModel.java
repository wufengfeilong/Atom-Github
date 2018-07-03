package sdwxwx.com.login.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 丁胜胜 on 2018/05/14.
 */

public class DataEditingModel {

    //后台：手机号注册接口
    public static void phoneRegister(String mobile,String code,String wechat_id, String qq_id,String nickname, String avatar,String city_id, final BaseCallback<UserBean> callback){

        HashMap<String,Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);

        APIClient.getAPIService().phoneRegisterUser(times,signature,mobile,code, wechat_id, qq_id, nickname, avatar, city_id,"0").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<UserBean>>>() {
            @Override
            public void accept(HttpResult<List<UserBean>> result) throws Exception {
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

    //后台：手机号注册接口
    public static void phoneHasFileRegister(String mobile,String code,String wechat_id, String qq_id,String nickname, File avatar,  String city_id,final BaseCallback<UserBean> callback){

        HashMap<String,Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);

        APIClient.getAPIService().phoneHasFileRegisterUser(times,signature
                ,APIClient.getRequestBody(mobile)
                ,APIClient.getRequestBody(code),
                APIClient.getRequestBody(wechat_id),
                APIClient.getRequestBody(qq_id),
                APIClient.getRequestBody(nickname),
                APIClient.getRequestBody("0"),
                APIClient.getRequestBody(city_id)
                ,APIClient.getFileBody("avatar",avatar)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<UserBean>>>() {
            @Override
            public void accept(HttpResult<List<UserBean>> result) throws Exception {
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
