package com.fcn.park.home.parkinfo;


import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;
import com.fcn.park.manager.module.ManagerParkIntroductionModule;

/**
 * Created by 860115001 on 2018/04/24.
 */

public class ParkInfoPresenter extends BasePresenter<ParkInfoContract.View> implements ParkInfoContract.Presenter {

    private ManagerParkIntroductionModule mManagerParkIntroductionModule;

    @Override
    public void attach(ParkInfoContract.View view) {
        super.attach(view);
        mManagerParkIntroductionModule = new ManagerParkIntroductionModule();
    }

    @Override
    public void loadInfo(String park_id) {

        mManagerParkIntroductionModule.getParkIntroductionInfo(getView().getContext(), park_id, new OnDataGetCallback<ManagerParkIntroductionBean>() {
            @Override
            public void onSuccessResult(ManagerParkIntroductionBean data) {
                getView().updateInfo(data);
            }
        });
    }
}
