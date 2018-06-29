package com.fcn.park.property.presenter;
import android.text.TextUtils;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.property.contract.PropertySuggestionContract;
import com.fcn.park.property.module.PropertySuggestionModule;

/**
 * Created by 860117073 on 2018/4/24.
 */

/**
 * 意见建议用presenter
 */
public class PropertySuggestionPresenter extends BasePresenter<PropertySuggestionContract.View> implements PropertySuggestionContract.Presenter {

    private PropertySuggestionModule mPropertySuggestionModule;

    @Override
    public void attach(PropertySuggestionContract.View view) {
        super.attach(view);
        mPropertySuggestionModule = new PropertySuggestionModule();
    }

    @Override
    public void onClickSubmit() {
        final String describeContent = getView().getDescribeContent();
        final String userId = LoginHelper.getInstance().getUserToken();
        if (TextUtils.isEmpty(describeContent)) {
            getView().showToast("请输入您的意见或建议～");
        } else {
            mPropertySuggestionModule.requestSendSuggestionInformation(getView().getContext(), userId, describeContent, new OnDataCallback<String>() {
                @Override
                public void onSuccessResult(String s) {
                    getView().showToast("感谢您的意见～");
                }

                @Override
                public void onError(String errMsg) {
                    getView().showToast("出了点小问题，重新试一下吧～");
                }
            });
        }
    }
}
