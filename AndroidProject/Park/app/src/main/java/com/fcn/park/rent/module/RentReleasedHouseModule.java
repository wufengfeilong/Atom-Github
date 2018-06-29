package com.fcn.park.rent.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.rent.bean.RentInitBean;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 860117066 on 2018/04/25.
 */

public class RentReleasedHouseModule {
    private boolean isEnd;
    private List<RentReleasedHouseListBean.ListReleasedHouseBean> houseReleasedList;

    public void getRentAddInitData(final Context context, final OnDataCallback<RentInitBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentAddInitData()
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RentInitBean>>() {
                    @Override
                    public void onNext(HttpResult<RentInitBean> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }


    public void rentAdd(final Context context, List<MultipartBody.Part> parts, Map reqJson, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().rentAdd(parts,reqJson)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }


//    public void rentHouseDetail(final Context context, String houseId,final OnDataGetCallback<RentHouseDetailBean> callback) {
//        RetrofitManager.toSubscribe(
//                ApiClient.getApiService().rentReleasedHouseDetail(houseId)
//                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RentHouseDetailBean>>() {
//                    @Override
//                    public void onNext(HttpResult<RentHouseDetailBean> stringHttpResult) {
//                        if (stringHttpResult.isResult()) {
//                            callback.onSuccessResult(stringHttpResult.getData());
//                        }
//                    }
//                })
//        );
//    }

    /**
     * 获取已发布的租赁信息列表
     * @param context
     * @param page
     * @param callback
     */
    public void requestRentReleasedHouseList(final Context context, int page, final OnDataGetCallback<List<RentReleasedHouseListBean.ListReleasedHouseBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().rentReleasedHouseList(page)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RentReleasedHouseListBean>>() {

                    @Override
                    public void onNext(HttpResult<RentReleasedHouseListBean> result) {
                        RentReleasedHouseListBean bean = result.getData();

                        isEnd = !bean.isNext();

                        if (houseReleasedList == null) {
                            houseReleasedList = bean.getListReleasedHouse();
                        } else {
                            houseReleasedList.addAll(bean.getListReleasedHouse());
                        }
                        callback.onSuccessResult(houseReleasedList);
                    }
                }));
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public List<RentReleasedHouseListBean.ListReleasedHouseBean> getHouseReleasedList() {
        return houseReleasedList;
    }

    public void setHouseReleasedList(List<RentReleasedHouseListBean.ListReleasedHouseBean> houseReleasedList) {
        this.houseReleasedList = houseReleasedList;
    }
}
