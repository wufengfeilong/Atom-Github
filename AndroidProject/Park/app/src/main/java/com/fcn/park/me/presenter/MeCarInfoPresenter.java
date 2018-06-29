package com.fcn.park.me.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.me.activity.MeCarEditorActivity;
import com.fcn.park.me.contract.MeCarInfoContract;
import com.fcn.park.me.bean.MeCarInfoBean;
import com.fcn.park.me.module.MeCarInfoModule;

import java.util.List;


/**
 * Created by 860117073 on 2018/4/25.
 * 我的车辆信息用presenter
 */

public class MeCarInfoPresenter extends BasePresenter<MeCarInfoContract.View> implements MeCarInfoContract.Presenter {
    private MeCarInfoModule mMeCarInfoModule;
    public String str;

    @Override
    public void attach(MeCarInfoContract.View view) {
        mMeCarInfoModule = new MeCarInfoModule();
        super.attach(view);
        getView().initRecyclerView();
    }

    @Override
    public void loadListData() {
        loadListData(1);
    }

    private void loadListData(int page) {
        //绑定数据
        mMeCarInfoModule.requestCarInfoList(getView().getContext(), page, new OnDataCallback<List<MeCarInfoBean.CarInfoBean>>() {
            @Override
            public void onSuccessResult(List<MeCarInfoBean.CarInfoBean> CarInfo) {
                getView().updateIsEnd(mMeCarInfoModule.isEnd());
                getView().bindListData(CarInfo);
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast("信息获取失败～");
            }
        });
    }

    //列表的点击事件
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
        View view;
        view = vh.itemView;
        TextView carid = (TextView) view.findViewById(R.id.carId);
        TextView carowner = (TextView) view.findViewById(R.id.carOwner);
        TextView platenumber = (TextView) view.findViewById(R.id.plateNumber);
        TextView carphone = (TextView) view.findViewById(R.id.carPhone);
        String carId = carid.getText().toString().trim();
        String carOwner = carowner.getText().toString().trim();
        String plateNumber = platenumber.getText().toString().trim();
        String carPhone = carphone.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra("carId", carId);
        intent.putExtra("carOwner", carOwner);
        intent.putExtra("plateNumber", plateNumber);
        intent.putExtra("carPhone", carPhone);

        intent.setClass(getView().getContext(), MeCarEditorActivity.class);
        getView().getContext().startActivity(intent);
    }

    //长按点击事件
    @Override
    public void onItemLongClick() {
        final int position = getView().getClickPosition();
        if (position > mMeCarInfoModule.getCarInfo().size() || position < 0) return;
        final MeCarInfoBean.CarInfoBean CarInfoBean = mMeCarInfoModule.getCarInfo().get(position);
        mMeCarInfoModule.deleteCarInfoItem(getView().getContext(), CarInfoBean.getCarId(), new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast(data);
                mMeCarInfoModule.getCarInfo().remove(CarInfoBean);
                getView().deleteListItem(position);
            }

            @Override
            public void onError(String data) {
                getView().showToast(data);
            }
        });

    }

}
