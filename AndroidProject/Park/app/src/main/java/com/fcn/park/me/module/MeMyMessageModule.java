package com.fcn.park.me.module;

import android.content.Context;
import com.fcn.park.base.http.*;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.bean.MeMessageRecordBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:06
 * 个人中心-消息列表
 */
public class MeMyMessageModule {
    private List<MeMessageRecordBean.ListMessageBean> cListData;
    private boolean isEnd;
    /**
     * 获取消息列表
     */
    public void getList(final Context context,String userId,int page, final OnDataGetCallback<List<MeMessageRecordBean.ListMessageBean>> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().messageRecordList(userId,page), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<MeMessageRecordBean>>() {
            @Override
            public void onNext(HttpResult<MeMessageRecordBean> result) {
                MeMessageRecordBean bean = result.getData();
                if (bean != null) {
                    if (cListData == null)
                        cListData = bean.getListMessageBean();
                    else
                        cListData.addAll(bean.getListMessageBean());
                    isEnd = !bean.isNext();
                }
                callback.onSuccessResult(cListData);
            }
        }));

    }
    /**
     * 是否加载完
     */
    public boolean isEnd() {
        return isEnd;
    }
    /**
     * 获取消息数据
     */
    public List<MeMessageRecordBean.ListMessageBean> getListData() {
        return cListData;
    }


}
