package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.property.bean.PropertyDeliverWaterBean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/17.
 */

public class PropertyDeliverWaterModule {
    /**
     * 获取送水商家列表页面初始化数据
     * @param context
     * @param page
     * @param callback
     */
    private boolean isEnd;
    private List<PropertyDeliverWaterBean.DeliverWaterBean> mbeans;
    public void requestDeliverWaterList(final Context context,int page,final OnDataGetCallback<List<PropertyDeliverWaterBean.DeliverWaterBean>>callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().deliverWaterList(page)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<PropertyDeliverWaterBean>>() {
                    @Override
                    public void onNext(HttpResult<PropertyDeliverWaterBean> result) {
                        PropertyDeliverWaterBean bean = result.getData();
                        isEnd = !bean.isIsNext();
                        if (bean != null) {
                            if (mbeans == null) {
                                mbeans = bean.getGetDeliverWater();
                            } else {
                                mbeans.addAll(bean.getGetDeliverWater());
                            }}
                        callback.onSuccessResult(mbeans);
                    }
                })
        );
    }
    public boolean isEnd() {
        return isEnd;
    }
}
