package com.fcn.park.me.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.DetailInfoBean;
import com.fcn.park.manager.contract.ManagerPostNewsDetailContract;
import com.fcn.park.manager.module.ManagerPostNewsDetailInfoModule;
import com.fcn.park.me.bean.RepairRecordDetailBean;
import com.fcn.park.me.contract.MeRepairRecordDetailContract;
import com.fcn.park.me.module.MeRepairRecordDetailInfoModule;

/**
 * 类描述：报修详情用Presenter.
 */

public class MeRepairRecordDetailPresenter extends BasePresenter<MeRepairRecordDetailContract.View>
        implements MeRepairRecordDetailContract.Presenter {

    private MeRepairRecordDetailInfoModule mMeRepairRecordDetailInfoModule;

    @Override
    public void attach(MeRepairRecordDetailContract.View view) {
        super.attach(view);
        mMeRepairRecordDetailInfoModule = new MeRepairRecordDetailInfoModule();
    }

    @Override
    public void loadInfo() {
        mMeRepairRecordDetailInfoModule.MeRepairRecordDetailInfo(getView().getContext(), getView().getRepairId(),new OnDataGetCallback<RepairRecordDetailBean>() {
            @Override
            public void onSuccessResult(RepairRecordDetailBean data) {
                getView().updateInfo(data);
            }
        });
    }
}
