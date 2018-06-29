package com.fcn.park.info.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.info.contract.ManagerDemandAddContract;
import com.fcn.park.info.module.ManagerDemandAddModule;
import com.fcn.park.info.view.ManagerDemandListActivity;
import com.fcn.park.login.LoginHelper;

/**
 * Created by liuyq on 2018/04/24.
 * 发布需求新增画面
 */

public class ManagerDemandAddPresenter extends BasePresenter<ManagerDemandAddContract.View> implements ManagerDemandAddContract.Presenter {

    private ManagerDemandAddModule mModule;

    @Override
    public void attach(ManagerDemandAddContract.View view) {
        super.attach(view);
        mModule = new ManagerDemandAddModule();
    }

    /**
     * 发布需求使用点击事件
     */
    @Override
    public void onClickDemandPublish() {

        String title = getView().getDemandTitle();
        int category = getView().getDemandCategory();
        String source = getView().getDemandSource();
        String contact = getView().getDemandContact();
        String phone = getView().getDemandTel();
        String address = getView().getDemandAddress();
        String detailed = getView().getDemandDetailed();

        if (getView().checkTitleEmpty()) {
            getView().showToast("请输入需求主题");
            return;
        }
        if (getView().checkSourceEmpty()) {
            getView().showToast("请选择来源");
            return;
        }
        if (getView().checkContactEmpty()) {
            getView().showToast("请输入联系人");
            return;
        }
        if (getView().checkTelEmpty()) {
            getView().showToast("请输入联系电话");
            return;
        }
        if (getView().checkAddressEmpty()) {
            getView().showToast("请输入地址");
            return;
        }
        if (getView().checkContentEmpty()) {
            getView().showToast("请输入需求明细要求");
            return;
        }

        mModule.managerDemandAdd(getView().getContext(), title, detailed, source, contact, phone, address, category, LoginHelper.getInstance().getUserBean().getUserName(), LoginHelper.getInstance().getUserBean().getUserName(), new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast("发布成功");
                getView().actionStartActivity(ManagerDemandListActivity.class);
                getView().closeActivity();
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast("发布失败");
            }
        });


    }


}
