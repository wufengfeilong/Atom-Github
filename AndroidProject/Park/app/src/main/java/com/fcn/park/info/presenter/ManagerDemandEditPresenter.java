package com.fcn.park.info.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.info.contract.ManagerDemandEditContract;
import com.fcn.park.info.module.ManagerDemandEditModule;
import com.fcn.park.info.view.ManagerDemandDetailActivity;
import com.fcn.park.login.LoginHelper;

/**
 * Created by liuyq on 2018/04/26.
 * 发布需求中需求详情编辑页面
 */

public class ManagerDemandEditPresenter extends BasePresenter<ManagerDemandEditContract.View> implements ManagerDemandEditContract.Presenter {

    private ManagerDemandEditModule managerDemandEditModule;
    String demandId;

    @Override
    public void attach(ManagerDemandEditContract.View view) {
        super.attach(view);
        managerDemandEditModule = new ManagerDemandEditModule();
    }


    /**
     * 点击事件
     */
    @Override
    public void onClickDemandEdit() {
        demandId = getView().getDemandId();
        String title = getView().getDemandEditTitle();
        String source = getView().getDemandEditSource();
        String contact = getView().getDemandEditContact();
        String phone = getView().getDemandEditTel();
        String address = getView().getDemandEditAddress();
        String detailed = getView().getDemandEditDetailed();
        if (getView().checkTitleEditEmpty()) {
            getView().showToast("请输入需求主题");
            return;
        }
        if (getView().checkSourceEditEmpty()) {
            getView().showToast("请选择来源");
            return;
        }
        if (getView().checkContactEditEmpty()) {
            getView().showToast("请输入联系人");
            return;
        }
        if (getView().checkTelEditEmpty()) {
            getView().showToast("请输入联系电话");
            return;
        }
        if (getView().checkAddressEditEmpty()) {
            getView().showToast("请输入地址");
            return;
        }
        if (getView().checkContentEditEmpty()) {
            getView().showToast("请输入需求明细要求");
            return;
        }

        managerDemandEditModule.managerDemandEdit(getView().getContext(), demandId, title, detailed, source, contact, phone, address, LoginHelper.getInstance().getUserBean().getUserName(), new OnDataCallback<String>() {

                    @Override
                    public void onSuccessResult(String data) {
                        getView().showToast("编辑发布成功");
                        getView().closeActivity();
                        ManagerDemandDetailActivity.actionStart(getView().getContext(), demandId);
                    }

                    @Override
                    public void onError(String errMsg) {
                        getView().showToast("编辑发布失败");
                    }
                }


        );
    }
}
