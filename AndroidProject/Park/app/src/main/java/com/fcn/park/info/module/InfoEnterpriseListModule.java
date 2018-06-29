package com.fcn.park.info.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.info.bean.EnterpriseListBean;
import com.fcn.park.info.bean.NewsListBean;

import java.util.List;

import rx.Subscriber;


/**
 * Created by liuyq on 2018/04/16.
 * 类描述：企业列表使用module
 */

public class InfoEnterpriseListModule {

    private List<EnterpriseListBean.BusinesslistBean> mEnterpriseList;
    private boolean isEnd;

    public void requestEnterpriseList(int pageNum, Context context, final OnDataGetCallback<List<EnterpriseListBean.BusinesslistBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getEnterpriseList(pageNum)
                , new ProgressSubscriber<HttpResult<EnterpriseListBean>>(context, new RequestImpl<HttpResult<EnterpriseListBean>>() {
                    @Override
                    public void onNext(HttpResult<EnterpriseListBean> result) {
                        EnterpriseListBean data = result.getData();
                        isEnd = !data.isIsNext();
                        if (mEnterpriseList == null) {
                            mEnterpriseList = data.getBusinesslist();
                        } else {
                            mEnterpriseList.addAll(data.getBusinesslist());
                        }
                        callback.onSuccessResult(mEnterpriseList);
                    }
                })
        );
    }

    public boolean isEnd() {
        return isEnd;
    }

    public List<EnterpriseListBean.BusinesslistBean> getEnterpriseList() {
        return mEnterpriseList;
    }
}
