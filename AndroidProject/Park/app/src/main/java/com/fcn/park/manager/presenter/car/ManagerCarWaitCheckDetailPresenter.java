package com.fcn.park.manager.presenter.car;

import android.text.TextUtils;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.car.CarWaitCheckDetailInfoBean;
import com.fcn.park.manager.contract.car.ManagerCarWaitCheckDetailContract;
import com.fcn.park.manager.module.car.ManagerCarWaitCheckDetailInfoModule;

/**
 * 管理中心的月租车辆审批功能用Presenter.
 */
public class ManagerCarWaitCheckDetailPresenter extends BasePresenter<ManagerCarWaitCheckDetailContract.View> implements ManagerCarWaitCheckDetailContract.Presenter {

    private ManagerCarWaitCheckDetailInfoModule managerCarWaitCheckDetailInfoModule;
    //月租车辆审批驳回理由
    private String checkinfo;

    @Override
    public void attach(ManagerCarWaitCheckDetailContract.View view) {
        super.attach(view);
        managerCarWaitCheckDetailInfoModule = new ManagerCarWaitCheckDetailInfoModule();
    }

    /**
     * 加载信息
     */
    @Override
    public void loadInfo() {
        managerCarWaitCheckDetailInfoModule.requestCarWaitCheckDetailInfo(getView().getContext(), getView().getParkPay_id(), new OnDataGetCallback<CarWaitCheckDetailInfoBean>() {
            @Override
            public void onSuccessResult(CarWaitCheckDetailInfoBean data) {
                getView().updateInfo(data);
            }
        });
    }

    /**
     * 点击通过按钮
     */
    @Override
    public void onPassClick() {
        managerCarWaitCheckDetailInfoModule.updateCheckStatusInfo(getView().getContext(),getView().getParkPay_id(),  getView().getUserId(),new OnDataGetCallback<String>() {
               @Override
               public void onSuccessResult(String data) {
                   getView().showToast("审核通过");
                   getView().closeActivity();
               }

        });
    }

    /**
     * 月租车辆审批驳回
     */
    @Override
    public void onTurnClick() {
        String checkInfo = getView().getCheckInfo().trim();
        if (TextUtils.isEmpty(checkInfo)) {
            Toast.makeText(getView().getContext(), getView().getContext().getString(R.string.manager_monthly_vehicle_check_checkinfo_no), Toast.LENGTH_SHORT).show();
        }else {
            managerCarWaitCheckDetailInfoModule.onTurnClick(getView().getContext(),getView().getParkPay_id(), getView().getUserId(), getView().getCheckInfo(), new OnDataGetCallback<String>() {
                @Override
                public void onSuccessResult(String data) {
                    getView().showToast("驳回成功");
                    getView().closeActivity();
                }
            });
        }
    }
}
