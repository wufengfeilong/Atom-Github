package com.fcn.park.manager.module;

import com.fcn.park.manager.bean.ManagerEnterpriseCheckBean;

import java.util.List;

/**
 * 管理中心的企业审核管理功能用Module
 */
public class ManagerEnterpriseCheckListModule {

    private List<ManagerEnterpriseCheckBean.EnterpriseCheckListBean> cListData;
    private boolean isEnd;

    /*public void getList(final Context context,  final OnDataGetCallback<List<ManagerEnterpriseCheckBean.EnterpriseCheckListBean>> callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().getEnterpriseCheckList(), new ProgressSubscriber<>(context, new RequestImpl<HttpResult<ManagerEnterpriseCheckBean>>() {
            @Override
            public void onNext(HttpResult<ManagerEnterpriseCheckBean> result) {
                ManagerEnterpriseCheckBean bean = result.getData();
                if (bean != null) {
                    if (cListData == null) {
                        cListData = bean.getEnterpriseCheckList();
                    } else {
                        cListData.clear();
                        cListData.addAll(bean.getEnterpriseCheckList());
                    }

                    isEnd = !bean.isNext();
                }
                callback.onSuccessResult(cListData);
            }
        }));
    }*/
    public boolean isEnd() {
        return isEnd;
    }
    public List<ManagerEnterpriseCheckBean.EnterpriseCheckListBean> getListData() {
        return cListData;
    }
}
