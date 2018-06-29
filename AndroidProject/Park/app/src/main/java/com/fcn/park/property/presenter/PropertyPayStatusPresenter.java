package com.fcn.park.property.presenter;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.fcn.park.MainActivity;
import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.property.contract.PropertyPayStatusContract;

/**
 * Created by lily on 2017/5/19.
 */

public class PropertyPayStatusPresenter extends BasePresenter<PropertyPayStatusContract.View> implements PropertyPayStatusContract.Presenter {

    @Override
    public void onClickComplete() {
        Log.d("PayStatusPresenter", "onClickComplete()支付状态    完成按钮被按下");
        getView().actionStartActivity(MainActivity.class);
    }

    @Override
    public void onNeedInvoiceClick() {
        Log.d("PayStatusPresenter", "onClickComplete()支付状态    需要发票被按下");

        // 提交成功
        //TODO：提交操作
        getView().showToast(getView().getContext().getString(R.string.property_pay_invoice_success_toast));
    }

    @Override
    public void onClickConfirm() {
        Log.d("PayStatusPresenter", "onClickConfirm()支付状态    确定按钮被按下");
//        getView().actionStartActivity(MainActivity.class);
//        getView().closeActivity();
        Intent intent = new Intent(getView().getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getView().getContext().startActivity(intent);
    }
}
