package com.fcn.park.property.presenter;

import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.DateUtils;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.bean.PropertyParkPayTypeBean;
import com.fcn.park.property.contract.PropertyParkPayContract;
import com.fcn.park.property.module.ParkPayModule;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;

/**
 * Created by 860115032 on 2018/04/08.
 */
public class PropertyParkPayPresenter extends BasePresenter<PropertyParkPayContract.View> implements PropertyParkPayContract.Presenter {

    private String TAG = "PropertyParkPayPresenter";
    private ParkPayModule parkPayModule;

    @Override
    public void attach(PropertyParkPayContract.View view) {
        super.attach(view);
        parkPayModule = new ParkPayModule();
    }

    /**
     * 日历控件的值变更触发的方法
     * @param inputContent
     */
    @Override
    public void itemDataChange(String inputContent) {
        getView().itemDataChangeSet(inputContent);
    }

    /**
     * 初始化数据获取并设定
     */
    @Override
    public void getInitData() {
        parkPayModule.getParkPayInitData(getView().getContext(),new OnDataCallback<List<PropertyParkPayTypeBean>>() {
            @Override
            public void onSuccessResult(List<PropertyParkPayTypeBean> data) {
                //数据设定
                getView().setInitData(data);
            }

            @Override
            public void onError(String msg) {
                Log.e(TAG, "onError msg = " + msg);
            }

    });
    }

    /**
     * 提交申请按钮事件
     */
    @Override
    public void onClickApplyCommit() {

        final PropertyParkPayBean bean = getView().getBean();
        final Map<String,String> imageMap = getView().getImageMap();

        //页面输入值check
        if(bean.getApplicantName()==""||bean.getApplicantName()==null){
            getView().showToast("申请人姓名必须填写");
            return;
        }
        if(bean.getCompany()==""||bean.getCompany()==null){
            getView().showToast("公司必须填写");
            return;
        }
        if(bean.getPhone()==""||bean.getPhone()==null){
            getView().showToast("电话必须填写");
            return;
        }
        if(bean.getCarNumber()==""||bean.getCarNumber()==null){
            getView().showToast("车牌号必须填写");
            return;
        }
        if(bean.getApplicantName()==""||bean.getApplicantName()==null){
            getView().showToast("申请人姓名必须填写");
            return;
        }
        if("".equals(imageMap.get("onJobImage"))){
            getView().showToast("在职证明必须上传");
            return;
        }
        if("".equals(imageMap.get("driver"))){
            getView().showToast("驾驶证必须上传");
            return;
        }
        if("".equals(imageMap.get("drivering"))){
            getView().showToast("行驶证必须上传");
            return;
        }
        if(bean.getStartDate()==""||bean.getStartDate()==null){
            getView().showToast("生效日期必须填写");
            return;
        }

        PropertyParkPayTypeBean  typeBean = getView().getRadioSelected();
        bean.setApplyType(String.valueOf(typeBean.getId()));
        bean.setEndDate(DateUtils.monthChange(bean.getStartDate(),Integer.parseInt(typeBean.getDistinguishId())));
        bean.setMoney(typeBean.getDistinguishValue());
        bean.setParkStatus("1");
        bean.setUserId(LoginHelper.getInstance().getUserToken());
        final Map<String ,String> reqMap = ApiClient.createBodyMap(bean);
        File fileTmp= new File(imageMap.get("onJobImage"));




        //将图片路径保存到List中
        ArrayList<String> pictureList = new ArrayList<String>();
        pictureList.add(imageMap.get("onJobImage"));
        pictureList.add(imageMap.get("driver"));
        pictureList.add(imageMap.get("drivering"));

        final List<MultipartBody.Part> parts = new ArrayList<>();

        //图片压缩
        ApiClient.compressMore(getView().getContext(), pictureList, new OnDataCallback<List<File>>() {
            @Override
            public void onSuccessResult(List<File> fileList) {
                for(int i =0;i<fileList.size();i++){
                    switch(i){
                        case 0: parts.add(ApiClient.getFileBody("onJob",fileList.get(0)));
                        break;
                        case 1: parts.add(ApiClient.getFileBody("driver",fileList.get(1)));
                        break;
                        case 2: parts.add(ApiClient.getFileBody("drivering",fileList.get(2)));
                        break;
                        default:break;
                    }
                }
                //提交添加请求
                parkPayModule.parkPayApply(getView().getContext(),parts,reqMap, new OnDataCallback<String>() {
                    @Override
                    public void onSuccessResult(String data) {
                        getView().showToast("申请提交成功,请等待审核");
                        getView().closeActivity();
                    }
                    @Override
                    public void onError(String errMsg) {
                        getView().showToast(errMsg);
                    }
                });
            }
            @Override
            public void onError(String errMsg) {

            }
        });
    }
}





