package com.fcn.park.info.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerDemandDetailEditBinding;
import com.fcn.park.info.bean.DemandDetailInfoBean;
import com.fcn.park.info.contract.ManagerDemandEditContract;
import com.fcn.park.info.presenter.ManagerDemandEditPresenter;

/**
 * Created by liuyq on 2018/04/26.
 * 企业需求编辑画面
 */

public class ManagerDemandEditActivity extends BaseActivity<ManagerDemandDetailEditBinding, ManagerDemandEditContract.View, ManagerDemandEditPresenter>
        implements ManagerDemandEditContract.View {
    private String demandId;
    private String title;
    private String content;
    private String source;
    private String contact;
    private String tel;
    private String address;
    protected LinearLayout mLayoutTitleLeft;


    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        demandId = intent.getStringExtra("demandId");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        source = intent.getStringExtra("source");
        contact = intent.getStringExtra("contact");
        tel = intent.getStringExtra("tel");
        address = intent.getStringExtra("address");

    }

    @Override
    public String getDemandId() {
        return demandId;
    }

    @Override
    public String getDemandEditTitle() {
        return mDataBinding.demandTitle.getText().toString();
    }

    @Override
    public String getDemandEditSource() {
        return mDataBinding.demandSource.getText().toString();
    }

    @Override
    public String getDemandEditContact() {
        return mDataBinding.demandContact.getText().toString();
    }

    @Override
    public String getDemandEditTel() {
        return mDataBinding.demandTel.getText().toString();
    }

    @Override
    public String getDemandEditAddress() {
        return mDataBinding.demandAddress.getText().toString();
    }

    @Override
    public String getDemandEditDetailed() {
        return mDataBinding.demandDetailed.getText().toString();
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
    public boolean checkTitleEditEmpty() {
        return checkInputEmpty(mDataBinding.demandTitle.getText().toString().trim());
    }

    @Override
    public boolean checkSourceEditEmpty() {
        return checkInputEmpty(mDataBinding.demandSource.getText().toString().trim());
    }

    @Override
    public boolean checkContactEditEmpty() {
        return checkInputEmpty(mDataBinding.demandContact.getText().toString().trim());
    }

    @Override
    public boolean checkTelEditEmpty() {
        return checkInputEmpty(mDataBinding.demandTel.getText().toString().trim());
    }

    @Override
    public boolean checkAddressEditEmpty() {
        return checkInputEmpty(mDataBinding.demandAddress.getText().toString().trim());
    }

    @Override
    public boolean checkContentEditEmpty() {
        return checkInputEmpty(mDataBinding.demandContact.getText().toString().trim());
    }

    @Override
    protected void setupTitle() {
        setTitleText("发布企业需求编辑");
        openTitleLeftView(true);

    }

    @Override
    protected void initViews() {
        DemandDetailInfoBean demandDetailInfoBean = new DemandDetailInfoBean();
        demandDetailInfoBean.setDemandId(demandId);
        demandDetailInfoBean.setTitle(title);
        demandDetailInfoBean.setSource(source);
        demandDetailInfoBean.setContact(contact);
        demandDetailInfoBean.setTel(tel);
        demandDetailInfoBean.setAddress(address);
        demandDetailInfoBean.setContent(content);
        mDataBinding.setDemandDetailInfoBean(demandDetailInfoBean);
        mDataBinding.setManagerDemandEditPresenter(mPresenter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_demand_detail_edit;
    }

    @Override
    protected ManagerDemandEditPresenter createPresenter() {
        return new ManagerDemandEditPresenter();
    }

}
