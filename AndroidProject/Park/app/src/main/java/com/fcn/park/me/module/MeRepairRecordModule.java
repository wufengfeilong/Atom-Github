package com.fcn.park.me.module;

import android.content.Context;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.bean.RepairRecordBean;

import java.util.List;
/**
 * 类描述：查看个人报修列表用Module.
 */
public class MeRepairRecordModule {
    private List<RepairRecordBean.ListRecordBean> cListData;
    private boolean isEnd;

    public void getList(final Context context,int pageNum,String userId,final OnDataGetCallback<List<RepairRecordBean.ListRecordBean>> callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().getRepairRecordlist(userId,pageNum), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<RepairRecordBean>>() {
            @Override
            public void onNext(HttpResult<RepairRecordBean> result) {
                RepairRecordBean data = result.getData();
                isEnd =!data.isNext();
                if (data != null) {
                    if (cListData == null)
                        cListData = data.getListRecordBean();
                    else
                        cListData.addAll(data.getListRecordBean());
                    isEnd = !data.isNext();
                }
                callback.onSuccessResult(cListData);
            }
        }));

    }
    public boolean isEnd() {
        return isEnd;
    }
    public List<RepairRecordBean.ListRecordBean> getListData() {
        return cListData;
    }
}
