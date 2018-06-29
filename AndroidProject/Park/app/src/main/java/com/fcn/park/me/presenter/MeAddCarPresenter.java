package com.fcn.park.me.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.me.contract.MeAddCarContract;
import com.fcn.park.me.module.MeAddCarModule;

/**
 * Created by 860117073 on 2018/4/27.
 * 追加我的车辆信息用presenter
 */


public class MeAddCarPresenter extends BasePresenter<MeAddCarContract.View> implements MeAddCarContract.Presenter {

    private MeAddCarModule meAddCarModule;

    @Override
    public void attach(MeAddCarContract.View view) {
        super.attach(view);
        meAddCarModule =new MeAddCarModule();
    }
    @Override
    public void onClickSubmit() {
        final String CarOwner =getView().getCarOwner();
        final String PlateNumber=getView().getPlateNumber();
        final String Phone=getView().getPhone();
        if(isCarNo(PlateNumber)) {
            meAddCarModule.requestSendAddCar(getView().getContext(),CarOwner,PlateNumber,Phone,new OnDataCallback<String>() {
                @Override
                public void onSuccessResult(String s) {
                    getView().showToast("追加成功");
                    getView().closeActivity();
                }

                @Override
                public void onError(String errMsg) {
                    getView().showToast("追加失败");
                }
            });
        }
        else {
            getView().showToast("请输入正确的车牌号格式");
        }
    }
    /**
     * 判断是否是车牌号
     */
    public static boolean isCarNo(String CarNum) {
        //匹配第一位汉字
        String str = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼甲乙丙己庚辛壬寅辰戍午未申";
        if (!(CarNum == null || CarNum.equals(""))) {
            String s1 = CarNum.substring(0, 1);//获取字符串的第一个字符
            if (str.contains(s1)) {
                String s2 = CarNum.substring(1, CarNum.length());
                //不包含I O i o的判断
                if (s2.contains("I") || s2.contains("i") || s2.contains("O") || s2.contains("o")) {
                    return false;
                } else {
                    if (!CarNum.matches("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$")) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }
}
