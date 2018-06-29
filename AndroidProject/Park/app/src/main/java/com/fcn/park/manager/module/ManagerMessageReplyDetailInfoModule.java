package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerMessageReplyDetailInfoBean;

import java.util.Map;

/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心留言详情Module
 */

public class ManagerMessageReplyDetailInfoModule {

    private String TAG = "=== ManagerMessageReplyDetailInfoModule ===";
    /**
     * 获取报修详情的处理
     *
     * @param context
     * @param suggestionId
     * @param callback
     */
    public void requestMessageReplyDetailInfo(Context context, String suggestionId, final OnDataGetCallback<ManagerMessageReplyDetailInfoBean> callback){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().messageReplyDetail(suggestionId),
                new ProgressSubscriber<HttpResult<ManagerMessageReplyDetailInfoBean>>(context, new RequestImpl<HttpResult<ManagerMessageReplyDetailInfoBean>>(){
                    @Override
                    public void onNext(HttpResult<ManagerMessageReplyDetailInfoBean> result) {
                        callback.onSuccessResult(result.getData());
                    }
                })
        );
    }

    /**
     * 点击“回复”按钮后，更新留言的信息
     * @param context
     * @param callback
     */
    public void updateMessageReplyInfoByAnswer(Context context, Map<String, String> updateData, int replyStatus, final OnDataGetCallback<String> callback) {
        Log.d(TAG, "========= 点击“回复”按钮后，更新留言的信息 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().updateMessageReplyInfoByAnswer(updateData,replyStatus), new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                Log.d(TAG, "======== result = " + result.getData());
                Log.d(TAG, "======== msg = " + result.getMsg());
                callback.onSuccessResult(result.getMsg());
            }
        }));
    }

}
