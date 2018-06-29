package com.fcn.park.info.module;


import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.ManagerDemandListBean;

import java.util.List;

/**
 * Created by liuyq on 2018/04/23.
 * 管理需求列表使用module
 */

public class ManagerDemandListModule {
    private List<ManagerDemandListBean.DemandListBean> mDemandList;
    private boolean isEnd;

    public void requestDemandList(int pageNum, Context context, final OnDataGetCallback<List<ManagerDemandListBean.DemandListBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getManagerDemandList(pageNum)
                , new ProgressSubscriber<HttpResult<ManagerDemandListBean>>(context, new RequestImpl<HttpResult<ManagerDemandListBean>>() {
                    @Override
                    public void onNext(HttpResult<ManagerDemandListBean> result) {
                        ManagerDemandListBean data = result.getData();
                        isEnd = !data.isIsNext();
                        if (mDemandList == null) {
                            mDemandList = data.getDemandlist();
                        } else {
                            mDemandList.addAll(data.getDemandlist());
                        }
                        callback.onSuccessResult(mDemandList);
                    }
                })
        );
    }

    public boolean isEnd() {
        return isEnd;
    }

    public List<ManagerDemandListBean.DemandListBean> getDemandlist() {
        return mDemandList;
    }

    /**
     * 长按删除
     *
     * @param context
     * @param demandId
     * @param callback
     */

    public void deleteDemandListItem(Context context, String demandId, final OnDataGetCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().deleteDemandDetail(demandId)
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }
}
