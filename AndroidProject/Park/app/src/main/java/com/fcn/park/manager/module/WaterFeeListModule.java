package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.WaterFeeListBean;

import java.util.List;

import rx.Subscriber;

/**
 * 水费列表用Module.
 */

public class WaterFeeListModule {


    private List<WaterFeeListBean.ListWaterBean> mListData;
    private boolean isEnd;

    public void requestWaterFeeList(Context context, int pageNum, final OnDataGetCallback<List<WaterFeeListBean.ListWaterBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getWaterFeeList( pageNum)
                , new ProgressSubscriber<HttpResult<WaterFeeListBean>>(context, new RequestImpl<HttpResult<WaterFeeListBean>>() {
                    @Override
                    public void onNext(HttpResult<WaterFeeListBean> result) {
                        WaterFeeListBean data = result.getData();
                        isEnd = !data.isIsNext();
                        if (data != null) {
                            if (mListData == null) {
                                mListData = data.getListWater();
                            } else {
                                mListData.addAll(data.getListWater());
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

    public List<WaterFeeListBean.ListWaterBean> getListData() {
        return mListData;
    }

    public void sendWaterFeeMail(String email, String recordDate, String fee, Subscriber<HttpResult<String>> subscriber){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().sendWaterFeeMail(email, recordDate, fee)
                , subscriber
        );
    }
}
