package com.fcn.park.manager.module;

import android.content.Context;
import android.util.Log;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerRepairsListBean;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/12.
 * 类描述：管理中心的报修一览用Module
 */

public class ManagerRepairsListModule {

    private List<ManagerRepairsListBean.RepairsListBean> mListData;

    private boolean isEnd;

    /**
     * 获取报修列表的处理
     *
     * @param context
     * @param pageNum
     * @param callback
     */
    public void requestRepairsList(Context context, int pageNum, final OnDataGetCallback<List<ManagerRepairsListBean.RepairsListBean>> callback)
    {
        RetrofitManager.toSubscribe(ApiClient.getApiService().getRepairsListMessage(pageNum),
        new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerRepairsListBean>>() {
            @Override
            public void onNext(HttpResult<ManagerRepairsListBean> result) {
                ManagerRepairsListBean data = result.getData();
                isEnd =!data.isNext();
                if (data!=null){
                    if (mListData==null){
                        mListData = data.getGetlistRepairs();
                    }else {
                        mListData.addAll(data.getGetlistRepairs());
                    }
                }
                callback.onSuccessResult(mListData);
            }
        }));
    }
    public boolean isEnd() {
        return isEnd;
    }

    public List<ManagerRepairsListBean.RepairsListBean> getListData(){
        return mListData;
    }

}
