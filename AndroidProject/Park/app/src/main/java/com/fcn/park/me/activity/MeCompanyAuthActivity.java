package com.fcn.park.me.activity;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.MeCompanyAuthActivityBinding;
import com.fcn.park.me.contract.MeCompanyAuthContract;
import com.fcn.park.me.presenter.MeCompanyAuthPresenter;

import java.io.File;

/**
 * 个人中心-公司企业认证
 */
public class MeCompanyAuthActivity extends
        BaseActivity<MeCompanyAuthActivityBinding, MeCompanyAuthContract.View, MeCompanyAuthPresenter> implements
        MeCompanyAuthContract.View,AdapterView.OnItemSelectedListener {

    private SinglePictureSelectPopWindow mSelectPopWindow;

    int imgId;
    //选择的营业执照路径
    String licenseImgUrl;
    //选择的logo路径
    String logoImgUrl;
    //用来判断是后台添加还是更新企业信息
    String flg;

    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText(getString(R.string.me_company_auth));
    }
    /**
     * 初期化视图
     */
    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        //获取前画面传过来的更新/添加标识
        flg = getIntent().getStringExtra("flg");
        //解决输入内容被软禁盘遮挡问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        //行业范围
        String[] industryTypes = getResources().getStringArray(R.array.group_industry_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mContext, android.R.layout.simple_spinner_item, industryTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spIndustryType.setAdapter(adapter);
        mDataBinding.spIndustryType.setOnItemSelectedListener(this);

    }
    /**
     * 设置视图布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_company_auth_activity;
    }
    /**
     * 实例化presenter
     */
    @Override
    protected MeCompanyAuthPresenter createPresenter() {
        return new MeCompanyAuthPresenter();
    }
    /**
     * 公司名
     */
    @Override
    public String getCompanyName() {
        return mDataBinding.companyInputFullName.getInputText().toString().trim();
    }
    /**
     * 公司详细地址
     */
    @Override
    public String getDetailAddr() {
        return mDataBinding.companyDetailAddr.getInputText().toString().trim();
    }
    /**
     * 公司联系电话
     */
    @Override
    public String getLandLineNum() {
        return mDataBinding.companyLandlineTel.getInputText().toString().trim();
    }
    /**
     * 公司联系人
     */
    @Override
    public String getContactPerson() {
        return mDataBinding.companyContactPerson.getInputText().toString().trim();
    }
    /**
     * 公司邮箱地址
     */
    @Override
    public String getEmailAddr() {
        return mDataBinding.companyContactEmail.getInputText().toString().trim();
    }
    /**
     * 公司所属行业
     */
    @Override
    public String getIndustryType() {
        return mDataBinding.tvIndustryType.getText().toString();
    }
    /**
     * 公司营业执照
     */
    @Override
    public String getLicenseImgUrl() {
        return licenseImgUrl;
    }
    /**
     * 公司logo
     */
    @Override
    public String getLogoImgUrl() {
        return logoImgUrl;
    }
    /**
     * 公司认证flg
     */
    @Override
    public String getFlg() {
        return flg;
    }
    /**
     * 打开营业执照选择pop
     */
    @Override
    public void openSelectLicensePhotoPop() {
        imgId = R.id.company_business_license;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }
    /**
     * 打开公司logo选择pop
     */
    @Override
    public void openSelectLogoPhotoPop() {
        imgId = R.id.company_logo;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }
    /**
     * 图片选择结果回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imgUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);
            Glide.with(mActivity).load(new File(imgUrl)).into((ImageView)findViewById(imgId));
            switch (imgId) {
                case R.id.company_business_license:
                    licenseImgUrl = imgUrl;
                break;
                case R.id.company_logo:
                    logoImgUrl = imgUrl;
                    break;
            }
        }
    }
    /**
     * 公司认证信息上传成功
     */
    @Override
    public void saveOk() {
        showToast("保存成功。");
        closeActivity();
    }
    /**
     * 设置选择的行业
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mDataBinding.tvIndustryType.setText(((TextView) view).getText());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
