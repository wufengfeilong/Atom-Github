package com.fcn.park.me.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.bean.MeCarInfoBean;
import com.fcn.park.property.bean.PropertyMoveHouseBean;

import java.util.List;

/**
 * Created by 860117073 on 2018/4/25.
 */

public class MeCarInfoModule {
    /**请求追加车辆信息
     * @param context
     * @param page
     * @param callback
     */
    private boolean isEnd;
    private List<MeCarInfoBean.CarInfoBean> mbeans;

    public void requestCarInfoList(final Context context, int page, final OnDataCallback<List<MeCarInfoBean.CarInfoBean>> callback){

        RetrofitManager.toSubscribe(ApiClient.getApiService().carInfoList()
                ,new ProgressSubscriber<>(context, new RequestImpl<HttpResult<MeCarInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<MeCarInfoBean> result) {
                        MeCarInfoBean bean = result.getData();
                        isEnd = !bean.isIsNext();
                        if (bean != null) {
                            if (mbeans == null) {
                                mbeans = bean.getGetCarInfo();
                            } else {
                                mbeans.clear();
                                mbeans.addAll(bean.getGetCarInfo());
                            }}
                        callback.onSuccessResult(mbeans);
                    }
                })
        );
    }

    public void deleteCarInfoItem(Context context,String carId,final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().deleteCarItem(carId)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getMsg());
                    }
                }));
    }

    public List<MeCarInfoBean.CarInfoBean> getCarInfo(){
        return mbeans;
    }

    public boolean isEnd() {
        return isEnd;
    }

}
