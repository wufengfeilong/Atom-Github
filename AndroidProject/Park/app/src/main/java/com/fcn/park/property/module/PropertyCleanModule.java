package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.property.bean.PropertyCleanBean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/19.
 */


public class PropertyCleanModule {
    /**
     * 获取清洗商家列表页面初始化数据
     * @param context
     * @param page
     * @param callback
     */
    private boolean isEnd;
    private List<PropertyCleanBean.CleanBean> mbeans;

    public void requestCleanList(final Context context, int page, final OnDataGetCallback<List<PropertyCleanBean.CleanBean>> callback){

        RetrofitManager.toSubscribe(ApiClient.getApiService().CleanList(page)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<PropertyCleanBean>>() {

                    @Override
                    public void onNext(HttpResult<PropertyCleanBean> result) {
                        PropertyCleanBean bean = result.getData();
                        isEnd = !bean.isIsNext();
                        if (bean != null) {
                            if (mbeans == null) {
                                mbeans = bean.getGetClean();
                            } else {
                                mbeans.addAll(bean.getGetClean());
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
