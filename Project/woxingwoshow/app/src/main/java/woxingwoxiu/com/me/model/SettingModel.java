package woxingwoxiu.com.me.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.http.APIClient;
import woxingwoxiu.com.http.HttpResult;
import woxingwoxiu.com.me.bean.UpdateInfoBean;
import woxingwoxiu.com.util.NetworkUtils;

import java.util.HashMap;

/**
 * create by 860115039
 * date      2018/5/24
 * time      14:33
 */
public class SettingModel {

    public static void checkUpdate(String version, final BaseCallback<UpdateInfoBean> callback){
        HashMap<String,Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("version",version);
        mHashMap.put("system_type","android");
        mHashMap.put("timestamp",times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().clientUpdate(version,"android",times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<UpdateInfoBean>>() {
            @Override
            public void accept(HttpResult<UpdateInfoBean> result) throws Exception {
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
