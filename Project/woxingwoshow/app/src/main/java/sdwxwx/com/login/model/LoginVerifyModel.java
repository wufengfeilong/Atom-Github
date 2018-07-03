package sdwxwx.com.login.model;

import android.util.Log;
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
 * Created by 丁胜胜 on 2018/05/11.
 */

public class LoginVerifyModel {

    String TAG = "=== LoginVerifyModel ===";

    //后台：获取手机验证码
    public static void getVerify(String mobile, final BaseCallback<String> callback){

        HashMap<String,Object> mHashMap = new HashMap<>();

        long times = APIClient.getTimes();
        mHashMap.put("timestamp",times);
        mHashMap.put("mobile",mobile);
        mHashMap.put("debug","0");
        String signature = APIClient.getSign(mHashMap);

        APIClient.getAPIService().personSendPhoneCode(mobile,"0",times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<String>>() {

            String TAG = "=== LoginVerifyModel ===";

            @Override
            public void accept(HttpResult<String> result) throws Exception {

                if (result.getCode() == 0) {
                    callback.onSuccess(result.getData());
                    Log.d(TAG, "=====onSuccess->Verify====" + result.getData());
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

    //后台：用户登录接口
    public static void userLogin(String mobile, String code,final LoginCallback<UserBean> callback){

        HashMap<String,Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("mobile",mobile);
        mHashMap.put("code",code);
        mHashMap.put("wechat_id","0");
        mHashMap.put("qq_id","0");
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);

        APIClient.getAPIService().memberLogin(mobile,code,"0","0",times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<UserBean>>>() {
            @Override
            public void accept(HttpResult<List<UserBean>> result) throws Exception {
                if (result.getCode()==0) {
                    callback.onSuccess(result.getData().get(0));
                }else {
                    callback.onRegister();
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
