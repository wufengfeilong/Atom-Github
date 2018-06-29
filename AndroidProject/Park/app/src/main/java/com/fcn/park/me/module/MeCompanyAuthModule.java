package com.fcn.park.me.module;

import android.content.Context;
import com.fcn.park.base.http.*;
import com.fcn.park.base.interfacee.OnDataCallback;
import okhttp3.MultipartBody;
import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/12
 * time      9:05
 * 个人中心-企业认证
 */
public class MeCompanyAuthModule {
    /**
     * 企业认证信息上传
     */
    public void saveCompanyInfo(final Context context, List<MultipartBody.Part> parts, Map reqJson, final OnDataCallback<String> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().saveCompanyAuthInfo(parts,reqJson),
                 new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                }));
    }
}
