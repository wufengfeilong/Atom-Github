package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.RentFeeListBean;

import java.util.List;

import rx.Subscriber;

/**
 * 租赁费列表用Module.
 */

public class RentFeeListModule {


    private List<RentFeeListBean.ListRentBean> mListData;
    private boolean isEnd;

    public void requestRentFeeList(Context context, int pageNum, final OnDataGetCallback<List<RentFeeListBean.ListRentBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentFeeList( pageNum)
                , new ProgressSubscriber<HttpResult<RentFeeListBean>>(context, new RequestImpl<HttpResult<RentFeeListBean>>() {
                    @Override
                    public void onNext(HttpResult<RentFeeListBean> result) {
                        RentFeeListBean data = result.getData();
                        isEnd = !data.isIsNext();
                        if (data != null) {
                            if (mListData == null) {
                                mListData = data.getListRent();
                            } else {
                                mListData.addAll(data.getListRent());
                            }
                        }
                        callback.onSuccessResult(mListData);
                    }
                })
        );
    }

    public boolean isEnd() {
        return isEnd;
    }

    public List<RentFeeListBean.ListRentBean> getListData() {
        return mListData;
    }

    public void sendRentFeeMail(String email, String startDate, String endDate, String fee, Subscriber<HttpResult<String>> subscriber){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().sendRentFeeMail(email, startDate, endDate, fee)
                , subscriber
        );
    }
}
