package com.fcn.park.info.view;

import android.content.Context;
import android.content.Intent;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.InfoDemandDetailBinding;
import com.fcn.park.info.bean.NeedInfoBean;
import com.fcn.park.info.contract.InfoDemandDetailContract;
import com.fcn.park.info.presenter.InfoNeedPresenter;

/**
 * Created by liuyq on 2018/04/20.
 * 企业需求详情页面
 */

public class InfoDemandDetailActivity extends BaseActivity<InfoDemandDetailBinding, InfoDemandDetailContract.View, InfoNeedPresenter>
        implements InfoDemandDetailContract.View {
    private static final String ID_TAG = "demandId";

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, InfoDemandDetailActivity.class);
        intent.putExtra(ID_TAG, id);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText("企业需求详情");
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mPresenter.loadInfo();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_demand_detail;
    }

    @Override
    protected InfoNeedPresenter createPresenter() {
        return new InfoNeedPresenter();
    }

    @Override
    public void bindData(NeedInfoBean needInfoBean) {
        mDataBinding.setNeedInfo(needInfoBean);
    }

    @Override
    public String getNeedId() {
        return getIntent().getStringExtra(ID_TAG);
    }
}
