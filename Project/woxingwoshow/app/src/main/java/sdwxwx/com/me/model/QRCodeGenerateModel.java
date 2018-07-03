package sdwxwx.com.me.model;

import java.util.HashMap;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

/**
 * Created by 860115025 on 2018/06/05.
 */
public class QRCodeGenerateModel {
    /**
     * 获取推荐人的URL地址
     * @param member_id 推荐人会员编号
     * @param callback 回调
     */
    public static void getQRCodeUrl(String member_id, final BaseCallback<String> callback){
        // 定义MAP
        HashMap<String,Object> mHashMap = new HashMap<>();
        // 获取时间戳并放置到MAP中
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 调用推荐人机制接口
        APIClient.getAPIService().getQRCodeUrl(member_id,times,APIClient.getSign(mHashMap)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<String>>() {
            @Override
            public void accept(HttpResult<String> result) throws Exception {
                if (result.getCode() == 0) {
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
