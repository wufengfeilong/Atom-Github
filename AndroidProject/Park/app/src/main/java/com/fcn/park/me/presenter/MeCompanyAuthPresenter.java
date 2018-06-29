package com.fcn.park.me.presenter;

import android.text.TextUtils;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.RECheckUtils;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.me.bean.MeCompanyInfoBean;
import com.fcn.park.me.contract.MeCompanyAuthContract;
import com.fcn.park.me.module.MeCompanyAuthModule;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/12
 * time      9:06
 * 个人中心-企业认证presenter
 */
public class MeCompanyAuthPresenter extends BasePresenter<MeCompanyAuthContract.View> implements MeCompanyAuthContract.Presenter{
    private MeCompanyAuthModule mMeCompanyAuthModule;
    @Override
    public void attach(MeCompanyAuthContract.View view) {
        super.attach(view);
        mMeCompanyAuthModule = new MeCompanyAuthModule();
    }
    /**
     * 添加公司营业执照
     */
    @Override
    public void addLicense() {
        getView().openSelectLicensePhotoPop();
    }
    /**
     * 添加公司logo
     */
    @Override
    public void addLogo() {
        getView().openSelectLogoPhotoPop();
    }
    /**
     * 点击保存
     */
    @Override
    public void onClickSave() {
        //获取企业各种信息
        final String name = getView().getCompanyName();
        final String industryType = getView().getIndustryType();
        final String detailAddr = getView().getDetailAddr();
        final String landLineTel = getView().getLandLineNum();
        final String contactPerson = getView().getContactPerson();
        final String email = getView().getEmailAddr();

        String flg = getView().getFlg();
        String license = getView().getLicenseImgUrl();
        String logo = getView().getLogoImgUrl();
        //对企业信息进行判断
        if (TextUtils.isEmpty(name)) {
            getView().showToast("企业名不能为空");
            return;
        }
        if (TextUtils.isEmpty(industryType)) {
            getView().showToast("请选择行业");
            return;
        }
        if (TextUtils.isEmpty(detailAddr)) {
            getView().showToast("详细地址不能为空");
            return;
        }
        if (TextUtils.isEmpty(landLineTel)) {
            getView().showToast("手机号不能为空");
            return;
        } else if (!RECheckUtils.checkPhoneNum(landLineTel)
                && !RECheckUtils.checkLandline(landLineTel)) {
            getView().showToast("号码不合法，座机请在区号后加“-”");
            return;
        }
        if (TextUtils.isEmpty(contactPerson)) {
            getView().showToast("联系人不能为空");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            getView().showToast("邮箱不能为空");
            return;
        } else if (!RECheckUtils.checkEmail(email)) {
            getView().showToast("邮箱不合法,请检查");
            return;
        }
        if (TextUtils.isEmpty(license)) {
            getView().showToast("请选择营业执照图片");
            return;
        }
        //将企业信息保存到bean中
        final MeCompanyInfoBean bean = new MeCompanyInfoBean();
        bean.setUserId(LoginHelper.getInstance().getUserBean().getToken());
        bean.setCompanyName(name);
        bean.setIndustry(industryType);
        bean.setAddress(detailAddr);
        bean.setTel(landLineTel);
        bean.setContact(contactPerson);
        bean.setMail(email);
        bean.setFlg(flg);
        final Map<String ,String> reqMap = ApiClient.createBodyMap(bean);

        //将图片路径保存到List中
        ArrayList<String> pictureList = new ArrayList<String>();
        if (!TextUtils.isEmpty(license)) {
            pictureList.add(license);
        }
        if (!TextUtils.isEmpty(logo)) {
            pictureList.add(logo);
        }
        final List<MultipartBody.Part> parts = new ArrayList<>();
        //多张图片压缩
        ApiClient.compressMore(getView().getContext(), pictureList, new OnDataCallback<List<File>>() {
            @Override
            public void onSuccessResult(List<File> fileList) {
                for (int i = 0; i < fileList.size(); i++) {
                    parts.add(ApiClient.getFileBody("license" + (i + 1), fileList.get(i)));
                }
                //企业认证信息上传
                mMeCompanyAuthModule.saveCompanyInfo(getView().getContext(),parts,reqMap, new OnDataCallback<String>() {
                    @Override
                    public void onSuccessResult(String data) {
                        getView().saveOk();
                    }

                    @Override
                    public void onError(String errMsg) {
                        getView().showToast("提交信息失败，请稍后重试。");
                        getView().hindProgressDialog();
                    }
                });
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast("企业认证上传失败，请稍后重试。");
            }
        });



    }
}
