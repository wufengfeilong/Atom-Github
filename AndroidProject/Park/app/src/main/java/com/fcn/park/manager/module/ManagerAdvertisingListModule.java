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

/**
 * 广告位已审核完了用Module.
 */
public class ManagerAdvertisingListModule {

    private String TAG = "=== ManagerAdvertisingListModule ===";
    private List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> mAdvertisingList;
    private boolean isEnd;
    /**
     * 获取已审批完了广告位的列表内容
     * @param context
     * @param page
     * @param callback
     */
    public void getAdvertisingList(Context context, int page,  final OnDataGetCallback<List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>> callback) {
        Log.d(TAG, "========= 获取已审批完了的广告位的列表内容 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().getAdvertisingList(page), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerAdvertisingApprovalListBean>>() {
            @Override
            public void onNext(HttpResult<ManagerAdvertisingApprovalListBean> result) {
                Log.d(TAG, "======= result = " + result.getData());

                ManagerAdvertisingApprovalListBean advertisingApprovalListBean = result.getData();
                isEnd = !advertisingApprovalListBean.isNext();
                if (mAdvertisingList == null) {
                    mAdvertisingList = advertisingApprovalListBean.getListAdvertisingApproval();
                } else {
                    // OnResume()重写请求数据时，清空原来的List中的数据，再将后台查询返回的数据追加到List中
                    // 注意：如果列表画面使用了分页请求数据、分页显示数据的话，就不能这么做了，否则前边的数据被清除掉，画面会异常
                    mAdvertisingList.clear();
                    mAdvertisingList.addAll(advertisingApprovalListBean.getListAdvertisingApproval());
                }
                callback.onSuccessResult(mAdvertisingList);
            }
        }));
    }

    public List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> getAdvertisingList() {
        return mAdvertisingList;
    }

    /**
     * 获取已审批完了广告位的详情内容
     *
     * @param context
     * @param advertisingId
     * @param callback
     */
    public void getAdvertisingInfo(Context context, String advertisingId, final OnDataGetCallback<ManagerAdvertisingApprovalBean> callback) {
        Log.d(TAG, "========= 获取已审批广告位的详情内容 =========");
        RetrofitManager.toSubscribe(ApiClient.getApiService().getAdvertisingDetailInfo(advertisingId), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerAdvertisingApprovalBean>>() {
            @Override
            public void onNext(HttpResult<ManagerAdvertisingApprovalBean> result) {
                Log.d(TAG, "========= result = " + result.getData());
                callback.onSuccessResult(result.getData());
            }
        }));
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}