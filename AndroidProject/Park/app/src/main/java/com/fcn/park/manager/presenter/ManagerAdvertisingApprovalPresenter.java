package com.fcn.park.manager.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;
import com.fcn.park.manager.contract.ManagerAdvertisingApprovalContract;
import com.fcn.park.manager.module.ManagerAdvertisingApprovalListModule;

import java.util.Map;

/**
 * 广告位详情审批用Presenter.
 */
public class ManagerAdvertisingApprovalPresenter
        extends BasePresenter<ManagerAdvertisingApprovalContract.View>
        implements ManagerAdvertisingApprovalContract.Presenter {

    private String TAG = "=== ManagerAdvertisingApprovalPresenter ===";

    // 定义Module，用来实现调用接口，连接后台处理
    private ManagerAdvertisingApprovalListModule mManagerAdvertisingApprovalModule;

    // 定义广告的链接字段
    private ManagerAdvertisingApprovalBean advertisingDataBean = null;

    @Override
    public void attach(ManagerAdvertisingApprovalContract.View view) {
        Log.d(TAG, "==== attach() ====");
        super.attach(view);
        mManagerAdvertisingApprovalModule = new ManagerAdvertisingApprovalListModule();
    }

    /**
     * 通过广告Id，获取广告的详情内容
     * @param advertisingId
     */
    @Override
    public void getAdvertisingInfoById(String advertisingId) {
        Log.d(TAG, "==== getAdvertisingInfoById() ====");
        mManagerAdvertisingApprovalModule.getAdvertisingApprovalInfo(getView().getContext(), advertisingId, new OnDataGetCallback<ManagerAdvertisingApprovalBean>() {
            @Override
            public void onSuccessResult(ManagerAdvertisingApprovalBean data) {
                // 调用Contract中定义的接口，将后台返回的数据data，显示到画面上
                getView().showDataToView(data);
                advertisingDataBean = data;
            }
        });
    }

    /**
     * 点击“通过”按钮的处理
     */
    @Override
    public void passOnClick() {

        // 将需要更新的内容保存到数据Bean中
        ManagerAdvertisingApprovalBean updateDataBean = new ManagerAdvertisingApprovalBean();
        updateDataBean.setAdvertisingId(advertisingDataBean.getAdvertisingId());
        int approvalStatus = 1; // 审核通过时，将审核状态更新为１（已审核）
        updateDataBean.setUpdateUser(LoginHelper.getInstance().getUserToken());
        updateDataBean.setInsertUser(LoginHelper.getInstance().getUserToken());
        updateDataBean.setUserId(getView().getUserId());
        updateDataBean.setMsgTitle(getView().getMsgTitle());
        updateDataBean.setMsgContent(getView().getMsgContent(true));

        Map<String, String> updateData = ApiClient.createBodyMap(updateDataBean);

        mManagerAdvertisingApprovalModule.updateAdvertisingInfoByPassOn(getView().getContext(), updateData, approvalStatus, new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                // 显示“通过”的Toast
                getView().showToast(data);
                // 关闭当前的Activity，返回列表画面
                getView().closeActivity();
            }
        });
    }

    /**
     * 点击“拒绝”按钮的处理
     */
    @Override
    public void refuseOnClick() {

        if (TextUtils.isEmpty(getView().getInputRefuseData().toString().trim())) {
            getView().showToast("请输入拒绝的理由");
            return;
        }

        // 将需要更新的内容保存到数据Bean中
        ManagerAdvertisingApprovalBean updateDataBean = new ManagerAdvertisingApprovalBean();
        updateDataBean.setAdvertisingId(advertisingDataBean.getAdvertisingId());
        int approvalStatus = 2; // 审核通过时，将审核状态更新为2（拒绝）
        updateDataBean.setRejectReason(getView().getInputRefuseData().toString().trim());
        updateDataBean.setUpdateUser(LoginHelper.getInstance().getUserToken());
        updateDataBean.setInsertUser(LoginHelper.getInstance().getUserToken());
        updateDataBean.setUserId(getView().getUserId());
        updateDataBean.setMsgTitle(getView().getMsgTitle());
        updateDataBean.setMsgContent(getView().getMsgContent(false));

        Map<String, String> updateData = ApiClient.createBodyMap(updateDataBean);
        mManagerAdvertisingApprovalModule.updateAdvertisingInfoByRefuse(getView().getContext(), updateData, approvalStatus, new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                // 显示“通过”的Toast
                getView().showToast(data);
                // 关闭当前的Activity，返回列表画面
                getView().closeActivity();
            }
        });
    }
}