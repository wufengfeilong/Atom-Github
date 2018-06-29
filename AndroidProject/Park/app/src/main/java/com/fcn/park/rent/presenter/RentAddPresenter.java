package com.fcn.park.rent.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.rent.activity.RentHouseListActivity;
import com.fcn.park.rent.bean.ImageInfo;
import com.fcn.park.rent.bean.RentAddBean;
import com.fcn.park.rent.bean.RentHouseEditBean;
import com.fcn.park.rent.bean.RentInitBean;
import com.fcn.park.rent.contract.RentAddContract;
import com.fcn.park.rent.module.RentModule;
import org.jsoup.helper.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;

/**
 * 房屋信息编辑用RentAddPresenter
 */
public class RentAddPresenter extends BasePresenter<RentAddContract.View> implements RentAddContract.Presenter {

    private RentModule rentModule;

    @Override
    public void attach(RentAddContract.View view) {

        super.attach(view);
        rentModule = new RentModule();
    }

    /**
     * 房屋添加初始化数据获取
     */
    @Override
    public void getInitData() {
        rentModule.getRentAddInitData(getView().getContext(),new OnDataCallback<RentInitBean>(){
            @Override
            public void onSuccessResult(RentInitBean data) {
                getView().setAddInitData(data);
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
            }
        });

    }

    /**
     * 房屋编辑数据初始化获取
     */
    @Override
    public void loadEditInitData() {
        rentModule.getRentEditInitData(getView().getContext(),getView().getHouseId(),new OnDataCallback<RentHouseEditBean>(){
            @Override
            public void onSuccessResult(RentHouseEditBean data) {
                getView().setEditInitData(data);
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
            }
        });
    }

    @Override
    public void aaa(int asd) {

    }

    /**
     * 添加房屋信息
     *
     * @param appFlg
     */
    @Override
    public void addRentInfo(int appFlg) {
        //获取页面数据
        RentAddBean rentAddBean = getView().getBean();
        //获取房屋类型
        rentAddBean.setHouse_type(getView().getHouseType());
        //获取房屋状态
        rentAddBean.setHouse_status(getView().getHouseStatus());
        //TODO
        rentAddBean.setCreate_user("DYJ");//LoginHelper.getInstance().getUserToken();

        //发布的场合，强制更改状态为发布
        if(1==appFlg){
            rentAddBean.setHouse_status("17");
        }

        //获取上传的图片
        List<ImageInfo> imageList = getView().getImageMap();

        final List<MultipartBody.Part> parts = new ArrayList<>();
        final List<String> imageIdList = new ArrayList<>();
        List<String> pictureList = new ArrayList<>();

        //图片存储内容编辑
        String imageStr="";
        for (ImageInfo imageInfo : imageList){
            if(imageInfo.changeFlg){
                if("".equals(imageStr)){
                    imageStr =imageStr+imageInfo.getImageUploadID();
                }else{
                    imageStr =imageStr+","+imageInfo.getImageUploadID();
                }
                pictureList.add(imageInfo.getImagePath());
                imageIdList.add(imageInfo.getImageUploadID());

            }else{
                if(!"".equals(imageInfo.getImageID())){
                    if("".equals(imageStr)){
                        imageStr=imageStr+imageInfo.getImageID();

                    }else{
                        imageStr=imageStr+","+imageInfo.getImageID();
                    }
                }
            }
        }
        rentAddBean.setHouse_img(imageStr);

        //获取houseId
        final String houseId = getView().getHouseId();
        if(houseId!=null&& !StringUtil.isBlank(houseId)) {
            rentAddBean.setHouseId(houseId);
        }else {
            rentAddBean.setHouseId("none");
        }
        //有图片的场合，提交有图片的请求
        if(pictureList.size()>0){
            final Map<String ,String> reqMap = ApiClient.createBodyMap(rentAddBean);

            //图片压缩
            ApiClient.compressMore(getView().getContext(), pictureList, new OnDataCallback<List<File>>() {
                @Override
                public void onSuccessResult(List<File> fileList) {

                    for(int i=0;i<fileList.size();i++){
                        parts.add(ApiClient.getFileBody(imageIdList.get(i),fileList.get(i)));
                    }

                    rentModule.rentAdd(getView().getContext(),parts,reqMap, new OnDataCallback<String>() {
                        @Override
                        public void onSuccessResult(String Msg) {
                            getView().showToast(Msg);
                            Intent intent = new Intent(getView().getContext(), RentHouseListActivity.class);
                            getView().getContext().startActivity(intent);
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
            //没有图片的场合，提交没有图片的请求
        }else{
            Map<String ,String> reqMap = ApiClient.createBodyMap(rentAddBean);
            rentModule.rentAddNoImage(getView().getContext(),reqMap, new OnDataCallback<String>() {
                @Override
                public void onSuccessResult(String Msg) {
                    getView().showToast(Msg);
                    Intent intent = new Intent(getView().getContext(), RentHouseListActivity.class);
                    getView().getContext().startActivity(intent);
                    getView().closeActivity();
                }
                @Override
                public void onError(String errMsg) {
                    getView().showToast(errMsg);
                }
            });
        }
    }

    @Override
    public void createItemClickListener(RecyclerView recyclerView) {
        super.createItemClickListener(recyclerView);
    }

    /**
     * 第1张图片选择
     */
    @Override
    public void imageSelect1() {
        getView().imageSelect1();
    }

    /**
     * 第2张图片选择
     */
    @Override
    public void imageSelect2() {
        getView().imageSelect2();
    }

    /**
     * 第3张图片选择
     */
    @Override
    public void imageSelect3() {
        getView().imageSelect3();
    }

    /**
     * 第4张图片选择
     */
    @Override
    public void imageSelect4() {
        getView().imageSelect4();
    }
}





