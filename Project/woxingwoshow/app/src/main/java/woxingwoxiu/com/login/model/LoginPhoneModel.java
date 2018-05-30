package woxingwoxiu.com.login.model;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.bean.UserBean;
import woxingwoxiu.com.http.APIClient;
import woxingwoxiu.com.http.HttpResult;
import woxingwoxiu.com.util.NetworkUtils;

import java.io.File;

/**
 * Created by 丁胜胜 on 2018/05/09.
 */

public class LoginPhoneModel {

    public static void phoneRegister(String mobile, String pwd, String nickname, File avatar,String recommendId, final BaseCallback<UserBean> callback){
        APIClient.getAPIService().phoneRegisterUser(mobile,pwd,"0","0",nickname,APIClient.getFileBody("avatar", avatar),recommendId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<UserBean>>() {
            @Override
            public void accept(HttpResult<UserBean> result) throws Exception {
                if (result.getCode()==0) {
                    callback.onSuccess(result.getData());
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
