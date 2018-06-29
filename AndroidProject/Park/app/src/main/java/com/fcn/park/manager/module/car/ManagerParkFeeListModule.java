package com.fcn.park.manager.module.car;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.car.ParkFeeBean;

import java.util.List;

/**
 * 月租车辆待审批功能用Module
 */
public class ManagerParkFeeListModule {

    private List<ParkFeeBean.ParkFeeListBean> cListData;
    private boolean isEnd;

    /**
     * 停车付费列表数据
     * @param context
     * @param callback
     */
    public void getList(final Context context,  final OnDataGetCallback<List<ParkFeeBean.ParkFeeListBean>> callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().getParkFeelist(), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ParkFeeBean>>() {
            @Override
            public void onNext(HttpResult<ParkFeeBean> result) {
                ParkFeeBean bean = result.getData();
                if (bean != null) {
                    if (cListData == null)
                        cListData = bean.getParkFeeList();
                    else
                        cListData.addAll(bean.getParkFeeList());
                    isEnd = !bean.isNext();
                }
                callback.onSuccessResult(cListData);
            }
        }));
    }
    public boolean isEnd() {
        return isEnd;
    }
    public List<ParkFeeBean.ParkFeeListBean> getListData() {
        return cListData;
    }
}
