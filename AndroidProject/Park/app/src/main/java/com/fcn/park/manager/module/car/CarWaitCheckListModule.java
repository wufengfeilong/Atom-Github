package com.fcn.park.manager.module.car;

import android.content.Context;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;

import java.util.List;

/**
 * 管理中心的月租车辆待审批功能用Module
 */
public class CarWaitCheckListModule {

    private List<CarWaitCheckBean.CarWaitCheckListBean> cListData;
    private boolean isEnd;

    /**
     * 通过接口获取后台月租车辆待审批列表数据
     * @param context
     * @param callback
     */
    public void getList(final Context context,  final OnDataGetCallback<List<CarWaitCheckBean.CarWaitCheckListBean>> callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().getCarwaitchecklist(), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<CarWaitCheckBean>>() {
            @Override
            public void onNext(HttpResult<CarWaitCheckBean> result) {
                CarWaitCheckBean bean = result.getData();
                if (bean != null) {
                    if (cListData == null) {
                        cListData = bean.getCarWaitCheckList();
                    } else {
                        cListData.clear();
                        cListData.addAll(bean.getCarWaitCheckList());
                    }

                    isEnd = !bean.isNext();
                }
                callback.onSuccessResult(cListData);
            }
        }));
    }

    public boolean isEnd() {
        return isEnd;
    }

    /**
     * 获取月租车辆待审批列表数据
     * @return
     */
    public List<CarWaitCheckBean.CarWaitCheckListBean> getListData() {
        return cListData;
    }
}
