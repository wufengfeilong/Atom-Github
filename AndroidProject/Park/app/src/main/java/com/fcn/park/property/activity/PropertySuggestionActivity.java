package com.fcn.park.property.activity;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.PropertySuggestionLayoutBinding;
import com.fcn.park.property.contract.PropertySuggestionContract;
import com.fcn.park.property.presenter.PropertySuggestionPresenter;

/**
 * 投诉意见
 */

public class PropertySuggestionActivity extends BaseActivity<PropertySuggestionLayoutBinding, PropertySuggestionContract.View, PropertySuggestionPresenter> implements PropertySuggestionContract.View {


    @Override
    protected PropertySuggestionPresenter createPresenter() {
        return new PropertySuggestionPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_suggestion_layout;
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_suggestion_title);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    public String getDescribeContent() {
        return mDataBinding.describeContent.getText().toString().trim();
    }


}
