package com.fcn.park.rent.module;

import android.content.Context;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.rent.bean.RentHouseDetailBean;
import com.fcn.park.rent.bean.RentHouseEditBean;
import com.fcn.park.rent.bean.RentHouseListBean;
import com.fcn.park.rent.bean.RentInitBean;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;

/**
 * 房屋租赁机能Module
 */
public class RentModule {

    private boolean isEnd;
    private List<RentHouseListBean.ListHouseBean> houseList;

    /**
     * 获取房屋添加页面初始化数据
     * @param context
     * @param callback
     */
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

    /**
     * 房屋添加
     * @param context
     * @param parts
     * @param reqJson
     * @param callback
     */
    public void rentAdd(final Context context, List<MultipartBody.Part> parts, Map reqJson,final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().rentAdd(parts,reqJson)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getMsg());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }

    /**
     * 房屋添加没有图片
     * @param context
     * @param reqJson
     * @param callback
     */
    public void rentAddNoImage(final Context context,Map reqJson,final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().rentAddNoImage(reqJson)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getMsg());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }

    /**
     * 房屋详情获取
     * @param context
     * @param houseId
     * @param callback
     */
    public void rentHouseDetail(final Context context, String houseId,final OnDataGetCallback<RentHouseDetailBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().rentHouseDetail(houseId)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RentHouseDetailBean>>() {
                    @Override
                    public void onNext(HttpResult<RentHouseDetailBean> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        }
                    }
                })
        );
    }

    /**
     * 房屋编辑初始化数据取得
     * @param context
     * @param houseId
     * @param callback
     */
    public void getRentEditInitData(final Context context, String houseId,final OnDataCallback<RentHouseEditBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getRentEditInitData(houseId)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RentHouseEditBean>>() {
                    @Override
                    public void onNext(HttpResult<RentHouseEditBean> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        }else{
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }

    /**
     * 房屋列表获取
     * @param context
     * @param newsType
     * @param page
     * @param callback
     */
    public void requestRentHouseList(final Context context, String newsType, int page, final OnDataGetCallback<List<RentHouseListBean.ListHouseBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().rentHouseList(newsType, page)
                , new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RentHouseListBean>>() {

                    @Override
                    public void onNext(HttpResult<RentHouseListBean> result) {
                        RentHouseListBean bean = result.getData();
                        isEnd = !bean.isNext();
                        if (houseList == null) {
                            houseList = bean.getListHouse();
                        } else {
                            houseList.addAll(bean.getListHouse());
                        }
                        callback.onSuccessResult(houseList);
                    }
                }));
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public List<RentHouseListBean.ListHouseBean> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<RentHouseListBean.ListHouseBean> houseList) {
        this.houseList = houseList;
    }
}
