package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.PropertyFeeListBean;

import java.util.List;

import rx.Subscriber;

/**
 * 物业费列表用Module.
 */
public class PropertyFeeListModule {

    private List<PropertyFeeListBean.ListPropertyBean> mListData;
    private boolean isEnd;

    /**
     * 获取物业费用列表的内容
     * @param context
     * @param pageNum
     * @param callback
     */
    public void requestPropertyFeeList(Context context, int pageNum, final OnDataGetCallback<List<PropertyFeeListBean.ListPropertyBean>> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPropertyFeeList( pageNum)
                , new ProgressSubscriber<HttpResult<PropertyFeeListBean>>(context, new RequestImpl<HttpResult<PropertyFeeListBean>>() {
                    @Override
                    public void onNext(HttpResult<PropertyFeeListBean> result) {
                        PropertyFeeListBean data = result.getData();
                        isEnd = !data.isIsNext();
                        if (data != null) {
                            if (mListData == null) {
                                mListData = data.getListProperty();
                            } else {
                                mListData.addAll(data.getListProperty());
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

    public List<PropertyFeeListBean.ListPropertyBean> getListData() {
        return mListData;
    }

    /**
     * 管理员催缴费时，向企业管理员发送邮件
     * @param email
     * @param startDate
     * @param endDate
     * @param fee
     * @param subscriber
     */
    public void sendPropertyFeeMail(String email, String startDate, String endDate, String fee, Subscriber<HttpResult<String>> subscriber){
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().sendPropertyFeeMail(email, startDate, endDate, fee)
                , subscriber
        );
    }
}
