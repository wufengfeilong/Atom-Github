package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;

import java.util.List;
import java.util.Map;

/**
 * 广告位待审核管理用Module.
 */
public class ManagerAdvertisingApprovalListModule {

    private String TAG = "=== ManagerAdvertisingApprovalModule ===";
    private List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> mAdvertisingApprovalList;
    private boolean isEnd;

    /**
     * 获取待审批广告位的列表内容
     *
     * @param context
     * @param callback
     */
    public void getAdvertisingApprovalList(Context context,  int page, final OnDataGetCallback<List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>> callback) {
        Log.d(TAG, "========= 获取待审批广告位的列表内容 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().getAdvertisingApprovalList(page), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerAdvertisingApprovalListBean>>() {
            @Override
            public void onNext(HttpResult<ManagerAdvertisingApprovalListBean> result) {
                Log.d(TAG, "======= result = " + result.getData());
                ManagerAdvertisingApprovalListBean advertisingApprovalListBean = result.getData();
                isEnd = !advertisingApprovalListBean.isNext();
                if (mAdvertisingApprovalList == null) {
                    mAdvertisingApprovalList = advertisingApprovalListBean.getListAdvertisingApproval();
                } else {
                    // OnResume()重写请求数据时，清空原来的List中的数据，再将后台查询返回的数据追加到List中
                    // 注意：如果列表画面使用了分页请求数据、分页显示数据的话，就不能这么做了，否则前边的数据被清除掉，画面会异常
                    mAdvertisingApprovalList.clear();
                    mAdvertisingApprovalList.addAll(advertisingApprovalListBean.getListAdvertisingApproval());
                }
                callback.onSuccessResult(mAdvertisingApprovalList);
            }
        }));
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> getAdvertisingApprovalList() {
        return mAdvertisingApprovalList;
    }

    /**
     * 获取待审批广告位的详情内容
     *
     * @param context
     * @param advertisingId
     * @param callback
     */
    public void getAdvertisingApprovalInfo(Context context, String advertisingId, final OnDataGetCallback<ManagerAdvertisingApprovalBean> callback) {
        Log.d(TAG, "========= 获取待审批广告位的详情内容 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().getAdvertisingApprovalDetailInfo(advertisingId), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerAdvertisingApprovalBean>>() {
            @Override
            public void onNext(HttpResult<ManagerAdvertisingApprovalBean> result) {
                callback.onSuccessResult(result.getData());
            }
        }));
    }

    /**
     * 点击“通过”按钮后，更新广告的信息
     * @param context
     * @param updateData
     * @param callback
     */
    public void updateAdvertisingInfoByPassOn(Context context, Map<String, String> updateData, int approvalStatus, final OnDataGetCallback<String> callback) {
        Log.d(TAG, "========= 点击“通过”按钮后，更新广告的信息 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().updateAdvertisingInfoByPassOn(updateData, approvalStatus), new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                callback.onSuccessResult(result.getMsg());
            }
        }));
    }

    /**
     * 点击“拒绝”按钮后，更新广告的信息
     * @param context
     * @param updateData
     * @param callback
     */
    public void updateAdvertisingInfoByRefuse(Context context, Map<String, String> updateData, int approvalStatus, final OnDataGetCallback<String> callback) {
        Log.d(TAG, "========= 点击“拒绝”按钮后，更新广告的信息 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().updateAdvertisingInfoByRefuse(updateData, approvalStatus), new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
            @Override
            public void onNext(HttpResult<String> result) {
                callback.onSuccessResult(result.getMsg());
            }
        }));
    }
}