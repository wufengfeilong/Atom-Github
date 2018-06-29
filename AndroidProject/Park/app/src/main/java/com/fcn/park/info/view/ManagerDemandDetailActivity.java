package com.fcn.park.info.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerDemandDetailBinding;
import com.fcn.park.info.bean.DemandDetailInfoBean;
import com.fcn.park.info.contract.ManagerDemandDetailContract;
import com.fcn.park.info.presenter.ManagerDemandDetailPresenter;

/**
 * Created by liuyq on 2018/04/25.
 * 企业需求发布明细页面
 */

public class ManagerDemandDetailActivity extends BaseActivity<ManagerDemandDetailBinding, ManagerDemandDetailContract.View, ManagerDemandDetailPresenter>
        implements ManagerDemandDetailContract.View {
    private static final String ID_TAG = "demandId";
    private String demandId;
    private String title;
    private String content;
    private String source;
    private String contact;
    private String tel;
    private String address;

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, ManagerDemandDetailActivity.class);
        intent.putExtra(ID_TAG, id);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText("发布企业需求");
        openTitleLeftView(true);
        mLayoutTitleRight.setVisibility(View.VISIBLE);
        mTvTitleRight.setText(R.string.demand_edit_title);
        mLayoutTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("demandId", demandId);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("source", source);
                intent.putExtra("contact", contact);
                intent.putExtra("tel", tel);
                intent.putExtra("address", address);
                intent.setClass(getContext(), ManagerDemandEditActivity.class);
                mActivity.finish();
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    protected void initFieldBeforeMethods() {
        Intent intent = getIntent();
        demandId = intent.getStringExtra(ID_TAG);
    }

    @Override
    protected void initViews() {
        mPresenter.loadInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_demand_detail;
    }

    @Override
    protected ManagerDemandDetailPresenter createPresenter() {
        return new ManagerDemandDetailPresenter();
    }


    @Override
    public void bindData(DemandDetailInfoBean demandDetailInfoBean) {
        mDataBinding.setDemandDetailInfo(demandDetailInfoBean);
        title = demandDetailInfoBean.getTitle();
        content = demandDetailInfoBean.getContent();
        source = demandDetailInfoBean.getSource();
        contact = demandDetailInfoBean.getContact();
        tel = demandDetailInfoBean.getTel();
        address = demandDetailInfoBean.getAddress();
    }

    @Override
    public String getDemandDetailId() {
        return getIntent().getStringExtra(ID_TAG);
    }
}
