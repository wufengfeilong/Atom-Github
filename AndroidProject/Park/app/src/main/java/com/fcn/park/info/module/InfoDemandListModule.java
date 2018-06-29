package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.DemandListBean;

import java.util.List;

/**
 * Created by liuyq on 2018/04/19.
 * 企业需求列表页面使用module
 */

public class InfoDemandListModule {
    private List<DemandListBean.DemandsListBean> mDemandList;
    private boolean isEnd;

    public void requestDemandList(int pageNum, Context context, final OnDataGetCallback<List<DemandListBean.DemandsListBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getDemandList(pageNum)
                , new ProgressSubscriber<HttpResult<DemandListBean>>(context, new RequestImpl<HttpResult<DemandListBean>>() {
                    @Override
                    public void onNext(HttpResult<DemandListBean> result) {
                        DemandListBean data = result.getData();
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

    public List<DemandListBean.DemandsListBean> getDemandlist() {
        return mDemandList;
    }
}
