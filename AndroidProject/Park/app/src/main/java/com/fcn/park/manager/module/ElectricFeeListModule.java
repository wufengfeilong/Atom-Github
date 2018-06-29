package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ElectricFeeListBean;

import java.util.List;

import rx.Subscriber;

/**
 * 电费列表用Module.
 */

public class ElectricFeeListModule {


    private List<ElectricFeeListBean.ListElectricBean> mListData;
    private boolean isEnd;

    public void requestElectricFeeList(Context context, int pageNum, final OnDataGetCallback<List<ElectricFeeListBean.ListElectricBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getElectricFeeList( pageNum)
                , new ProgressSubscriber<HttpResult<ElectricFeeListBean>>(context, new RequestImpl<HttpResult<ElectricFeeListBean>>() {
                    @Override
                    public void onNext(HttpResult<ElectricFeeListBean> result) {
                        ElectricFeeListBean data = result.getData();
                        isEnd = !data.isIsNext();
                        if (data != null) {
                            if (mListData == null) {
                                mListData = data.getListElectric();
                            } else {
                                mListData.addAll(data.getListElectric());
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

    public List<ElectricFeeListBean.ListElectricBean> getListData() {
        return mListData;
    }

    public void sendElectricFeeMail(String email, String recordDate, String fee, Subscriber<HttpResult<String>> subscriber){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().sendElectricFeeMail(email, recordDate, fee)
                , subscriber
        );
    }
}
