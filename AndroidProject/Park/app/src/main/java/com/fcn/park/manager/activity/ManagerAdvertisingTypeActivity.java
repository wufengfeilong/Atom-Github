package com.fcn.park.manager.activity;

import android.view.View;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerAdvertisingTypeBinding;
import com.fcn.park.manager.contract.ManagerAdvertisingTypeContract;
import com.fcn.park.manager.presenter.ManagerAdvertisingTypePresenter;

/**
 * 广告位审核画面
 * １.广告位待审核
 * ２.广告位已审核
 */
public class ManagerAdvertisingTypeActivity
        extends BaseActivity<ManagerAdvertisingTypeBinding, ManagerAdvertisingTypeContract.View, ManagerAdvertisingTypePresenter>
        implements ManagerAdvertisingTypeContract.View {

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_advertising_type;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_advertising_type));
        openTitleLeftView(true);
        mLayoutTitleRight.setVisibility(View.VISIBLE);
        mTvTitleRight.setText(R.string.manager_advertising_fee_edit_title);
        mLayoutTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionStartActivity(ManagerAdvertisingFeeEditActivity.class);
            }
        });
    }

    @Override
    protected void initViews() {
        mDataBinding.setAdvertisingTypePresenter(mPresenter);
    }

    @Override
    protected ManagerAdvertisingTypePresenter createPresenter() {
        return new ManagerAdvertisingTypePresenter();
    }
}