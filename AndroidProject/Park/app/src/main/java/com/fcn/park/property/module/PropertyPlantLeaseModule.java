package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.property.bean.PropertyPlantLeaseBean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/24.
 */

/**
 * 请求植物租赁商家信息
 */
public class PropertyPlantLeaseModule {
    /**
     * 获取植物租赁商家列表页面初始化数据
     * @param context
     * @param page
     * @param callback
     */
    private boolean isEnd;
    private List<PropertyPlantLeaseBean.PlantLeaseBean> mbeans;
    public void requestPlantLeaseList(final Context context, int page, final OnDataGetCallback<List<PropertyPlantLeaseBean.PlantLeaseBean>> callback){
        RetrofitManager.toSubscribe(ApiClient.getApiService().PlantLeaseList(page)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<PropertyPlantLeaseBean>>() {
                    @Override
                    public void onNext(HttpResult<PropertyPlantLeaseBean> result) {
                        PropertyPlantLeaseBean bean = result.getData();
                        isEnd = !bean.isIsNext();
                        if (bean != null) {
                            if (mbeans == null) {
                                mbeans = bean.getGetPlantLease();
                            } else {
                                mbeans.addAll(bean.getGetPlantLease());
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
