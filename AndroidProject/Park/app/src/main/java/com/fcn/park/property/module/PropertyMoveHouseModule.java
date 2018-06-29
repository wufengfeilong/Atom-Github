package com.fcn.park.property.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.property.bean.PropertyMoveHouseBean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/18.
 */

public class PropertyMoveHouseModule {
    /**
     * 获取搬家商家列表页面初始化数据
     * @param context
     * @param page
     * @param callback
     */
    private boolean isEnd;
    private List<PropertyMoveHouseBean.MoveHouseBean> mbeans;

    public void requestMoveHouseList(final Context context, int page, final OnDataGetCallback<List<PropertyMoveHouseBean.MoveHouseBean>> callback){

        RetrofitManager.toSubscribe(ApiClient.getApiService().moveHouseList(page)
                ,new ProgressSubscriber<>(context, new RequestImpl<HttpResult<PropertyMoveHouseBean>>() {
                    @Override
                    public void onNext(HttpResult<PropertyMoveHouseBean> result) {
                        PropertyMoveHouseBean bean = result.getData();
                        isEnd = !bean.isIsNext();
                        if (bean != null) {
                            if (mbeans == null) {
                                mbeans = bean.getGetMoveHouse();
                            } else {
                                mbeans.addAll(bean.getGetMoveHouse());
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