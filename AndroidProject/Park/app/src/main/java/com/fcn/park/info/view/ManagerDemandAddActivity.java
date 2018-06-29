package com.fcn.park.info.view;

import android.text.TextUtils;
import android.widget.RadioGroup;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerDemandAddBinding;
import com.fcn.park.info.contract.ManagerDemandAddContract;
import com.fcn.park.info.presenter.ManagerDemandAddPresenter;

/**
 * Created by liuyq on 2018/04/24.
 * 企业需求发布新增画面
 */

public class ManagerDemandAddActivity extends BaseActivity<ManagerDemandAddBinding, ManagerDemandAddContract.View, ManagerDemandAddPresenter> implements ManagerDemandAddContract.View, RadioGroup.OnCheckedChangeListener {

    private int category = 0;

    @Override
    public String getDemandTitle() {
        return mDataBinding.demandTitle.getText().toString().trim();
    }

    @Override
    public int getDemandCategory() {
        return category;
    }

    @Override
    public String getDemandSource() {
        return mDataBinding.demandSource.getText().toString().trim();
    }

    @Override
    public String getDemandContact() {
        return mDataBinding.demandContact.getText().toString().trim();
    }


    @Override
    public String getDemandTel() {
        return mDataBinding.demandTel.getText().toString().trim();
    }

    @Override
    public String getDemandAddress() {
        return mDataBinding.demandAdd.getText().toString().trim();
    }

    @Override
    public String getDemandDetailed() {
        return mDataBinding.demandDetailed.getText().toString().trim();
    }


    /**
     * 检查输入的内容是否为空
     *
     * @param text
     * @return
     */
    private boolean checkInputEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    @Override
    public boolean checkTitleEmpty() {
        return checkInputEmpty(mDataBinding.demandTitle.getText().toString().trim());
    }

    @Override
    public boolean checkSourceEmpty() {
        return checkInputEmpty(mDataBinding.demandSource.getText().toString().trim());
    }

    @Override
    public boolean checkContactEmpty() {
        return checkInputEmpty(mDataBinding.demandContact.getText().toString().trim());
    }

    @Override
    public boolean checkTelEmpty() {
        return checkInputEmpty(mDataBinding.demandTel.getText().toString().trim());
    }

    @Override
    public boolean checkAddressEmpty() {
        return checkInputEmpty(mDataBinding.demandAdd.getText().toString().trim());
    }

    @Override
    public boolean checkContentEmpty() {
        return checkInputEmpty(mDataBinding.demandDetailed.getText().toString().trim());
    }

    @Override
    protected void setupTitle() {
        setTitleText("发布企业需求");
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setManagerDemandAddPresenter(mPresenter);
        mDataBinding.category.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_demand_add;
    }

    @Override
    protected ManagerDemandAddPresenter createPresenter() {
        return new ManagerDemandAddPresenter();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.category_person:
                category = 0;
                break;
            case R.id.category_project:
                category = 1;
                break;

        }
    }
}
