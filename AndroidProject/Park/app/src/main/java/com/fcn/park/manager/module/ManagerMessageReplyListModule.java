package com.fcn.park.manager.module;

import android.content.Context;

import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerMessageReplyListBean;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心的留言回复列表用Module
 */

public class ManagerMessageReplyListModule {

    private List<ManagerMessageReplyListBean.ManagerReplyBean> mListData;
    private boolean isEnd;

    /**
     * 获取留言列表的处理
     *
     * @param context
     * @param pageNum
     * @param callback
     */
    public void requestMessageReplyList(Context context, int pageNum, final OnDataGetCallback<List<ManagerMessageReplyListBean.ManagerReplyBean>> callback)
    {
        RetrofitManager.toSubscribe(ApiClient.getApiService().getMessageReplyList(pageNum),
        new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerMessageReplyListBean>>() {
            @Override
            public void onNext(HttpResult<ManagerMessageReplyListBean> result) {
                ManagerMessageReplyListBean  data = result.getData();
                isEnd =!data.isNext();
                if(data!=null) {
                    if (mListData == null) {
                        mListData = data.getGetListMessageReply();
                    } else {
                        mListData.addAll(data.getGetListMessageReply());
                    }
                }
                callback.onSuccessResult(mListData);
             }
        }));

    }
    public boolean isEnd() {
        return isEnd;
    }

    public List<ManagerMessageReplyListBean.ManagerReplyBean> getListData(){
        return mListData;
    }
}
