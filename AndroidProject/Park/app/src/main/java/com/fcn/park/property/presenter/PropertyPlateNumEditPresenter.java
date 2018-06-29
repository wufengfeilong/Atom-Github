package com.fcn.park.property.presenter;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.property.activity.PropertyPayActivity;
import com.fcn.park.property.contract.PropertyPlateNumEditContract;

/**
 * Created by 860115032 on 2018/04/19.
 */

public class PropertyPlateNumEditPresenter extends BasePresenter<PropertyPlateNumEditContract.View> implements PropertyPlateNumEditContract.Presenter {
    @Override
    public void attach(PropertyPlateNumEditContract.View view) {
        super.attach(view);
    }

    @Override
    public void onCancelClick() {
        getView().closeActivity();
    }

    @Override
    public void onPositiveClick() {
        String carNumber = getView().getPlateNum();
        // 车牌号的正则表达式
        String carNumRegex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
        // 检测填写内容是否合格
        if (TextUtils.isEmpty(carNumber)) {
            getView().showToast(getView().getContext().getString(R.string.property_empty_plate_num_again));
        } else if (!carNumber.matches(carNumRegex)) {
            // 填写的车牌号不符合规则
            getView().showToast(getView().getContext().getString(R.string.property_mistake_plate_num_again));
        } else {
            // 跳转至临时停车缴费画面
            Intent intent = new Intent(getView().getContext(), PropertyPayActivity.class);
            intent.putExtra(Constant.PROPERTY_PAY_TYPE, 6);
            intent.putExtra("PLATE_NUMBER", getView().getPlateNum());
            getView().getContext().startActivity(intent);
        }
    }

    @Override
    public void onEditTextChanged() {
        // TODO:提示选择输入内容
    }

}
