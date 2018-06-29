package com.fcn.park.me.presenter;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.base.widget.InputDataPopWindow;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.login.bean.User;
import com.fcn.park.login.util.UserUtils;
import com.fcn.park.manager.bean.MenuBean;
import com.fcn.park.me.bean.MePersonInfoBean;
import com.fcn.park.me.bean.PictureBean;
import com.fcn.park.me.contract.MeCompanyInfoContract;
import com.fcn.park.me.module.MeCompanyInfoModule;
import okhttp3.MultipartBody;
import top.zibin.luban.OnCompressListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/16
 * time      16:00
 * 个人中心-企业信息编辑presenter
 */
public class MeCompanyInfoPresenter extends BasePresenter<MeCompanyInfoContract.View> implements MeCompanyInfoContract.Presenter {
    private MeCompanyInfoModule mModule;
    private boolean isChangeName;
    private List<PictureBean> pictureBeanList;


    @Override
    public void attach(MeCompanyInfoContract.View view) {
        super.attach(view);
        mModule = new MeCompanyInfoModule();
    }
    /**
     * 保存公司信息
     */
    @Override
    public void onClickSave() {
        final User userBean = LoginHelper.getInstance().getUserBean();

        //保存公司基本信息
        if (getView().isChange()) {
            final MePersonInfoBean updateInfoBean = new MePersonInfoBean();

            List<String> strings = ApiClient.checkMenuListIsEmpty(mModule.getMenuBeanList());

            if (!strings.isEmpty()) {
                getView().showToast("请完善您的" + strings.get(0));
                return;
            }
            ApiClient.menuListToData(mModule.getMenuBeanList(), updateInfoBean);

            if (userBean.getUserName() != null && !userBean.getUserName().equals(updateInfoBean.getName())) {
                isChangeName = true;
            }
            mModule.updateCompanyInfo(getView().getContext(), userBean.getToken(), updateInfoBean, new OnDataGetCallback<Boolean>() {
                @Override
                public void onSuccessResult(final Boolean s) {
                    if (isChangeName) {
                        updateUserData(updateInfoBean.getName(), true);
                    }
                    getView().changeSaveState(!s);
                    if (s) {
                        getView().showToast("修改成功");
                    } else {
                        getView().showToast("修改失败");
                    }

                }
            });
        }
        //保存公司详情
        if (getView().isIntroduceChange()) {

            String introduce = getView().getCompanyIntroduce();
            mModule.updateCompanyIntroduce(getView().getContext(), userBean.getToken(), introduce, new OnDataCallback<String>() {
                @Override
                public void onSuccessResult(String data) {

                    getView().setIsIntroduceChange(false);
                    getView().changeSaveState(false);
                    getView().showToast("公司详情修改保存成功");
                    getView().hindProgressDialog();

                }

                @Override
                public void onError(String errMsg) {
                    getView().changeSaveState(true);
                    getView().showToast("公司详情修改保存失败");
                    getView().hindProgressDialog();
                }
            });

        }
        //保存公司轮播图
        if (getView().isPictureChange()) {
            final PictureBean bean = new PictureBean();
            bean.setUserId(LoginHelper.getInstance().getUserBean().getToken());

            final Map<String, String> reqMap = ApiClient.createBodyMap(bean);
            final List<MultipartBody.Part> parts = new ArrayList<>();
            pictureBeanList = getView().getImgUrls();
            //将图片路径保存到List中
            ArrayList<String> pictureList = new ArrayList<String>();
            final ArrayList<String> pictureIdList = new ArrayList<String>();
            for (int i=0; i<pictureBeanList.size();i++) {
                if (pictureBeanList.get(i).isFlg()) {
                    pictureList.add(pictureBeanList.get(i).getPath());
                    pictureIdList.add(pictureBeanList.get(i).getId());
                }
            }

            ApiClient.compressMore(getView().getContext(),pictureList , new OnDataCallback<List<File>>() {
                @Override
                public void onSuccessResult(List<File> fileList) {
                    for (int i = 0; i < fileList.size(); i++) {
                        parts.add(ApiClient.getFileBody(pictureIdList.get(i), fileList.get(i)));
                    }

                    mModule.saveBannersInfo(getView().getContext(), parts, reqMap, new OnDataCallback<String>() {
                        @Override
                        public void onSuccessResult(String data) {
                            getView().setIsPictureChange(false);
                            getView().changeSaveState(false);
                            getView().showToast("公司展示图片上传成功");
                            getView().hindProgressDialog();
                        }

                        @Override
                        public void onError(String errMsg) {
                            getView().changeSaveState(true);
                            getView().showToast("公司展示图片上传失败");
                            getView().hindProgressDialog();
                        }
                    });
                }

                @Override
                public void onError(String errMsg) {
                    getView().changeSaveState(true);
                    getView().showToast("图片在压缩时出现问题，没能保存成功。");
                    getView().hindProgressDialog();
                }
            });


        }

    }
    /**
     * 头像压缩
     */
    @Override
    public void startUploadUserAvatar(String filePat) {
        final File uploadFile = new File(filePat);
        if (uploadFile.length() >= 1024 * 1024 * 2) {
            ApiClient.pictureCompression(getView().getContext(), uploadFile, new OnCompressListener() {
                @Override
                public void onStart() {
                    getView().showProgressDialog("图片上传排队中...");
                }

                @Override
                public void onSuccess(File file) {
                    getView().hindProgressDialog();
                    uploadUserAvatar(file);
                }

                @Override
                public void onError(Throwable e) {
                    getView().hindProgressDialog();
                    uploadUserAvatar(uploadFile);
                }
            });
        } else {
            uploadUserAvatar(uploadFile);
        }
    }
    /**
     * 添加第一张轮播图
     */
    @Override
    public void addBanner1() {
        getView().openSelectBannerPop(R.id.me_company_banner1);
    }
    /**
     * 添加第二张轮播图
     */
    @Override
    public void addBanner2() {
        getView().openSelectBannerPop(R.id.me_company_banner2);
    }
    /**
     * 添加第三张轮播图
     */
    @Override
    public void addBanner3() {
        getView().openSelectBannerPop(R.id.me_company_banner3);
    }
    /**
     * 添加第四张轮播图
     */
    @Override
    public void addBanner4() {
        getView().openSelectBannerPop(R.id.me_company_banner4);
    }
    /**
     * 添加第五张轮播图
     */
    @Override
    public void addBanner5() {
        getView().openSelectBannerPop(R.id.me_company_banner5);
    }
    /**
     * 加载公司详情
     */
    @Override
    public void loadIntroduceText() {
        mModule.loadIntroduceInfo(getView().getContext(), LoginHelper.getInstance().getUserBean().getToken(), new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().setIntroduceText(data);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    /**
     * 加载公司轮播图
     */
    @Override
    public void loadPictures() {
        mModule.loadPictures(getView().getContext(), LoginHelper.getInstance().getUserBean().getToken(), new OnDataGetCallback<List<PictureBean>>() {
            @Override
            public void onSuccessResult(List<PictureBean> data) {
                getView().setPictures(data);
            }
        });
    }
    /**
     * 上传用户头像
     */
    private void uploadUserAvatar(File avatarFile) {
        mModule.uploadUserAvatar(getView().getContext(), LoginHelper.getInstance().getUserToken(), avatarFile, new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast("修改头像成功");
                updateUserData(data, false);
            }
        });
    }
    /**
     * 更新用户数据
     */
    private void updateUserData(String value, boolean isName) {
        User userBean = LoginHelper.getInstance().getUserBean();
        if (isName)
            userBean.setUserName(value);
        else
            userBean.setAvatar(value);
        UserUtils.saveUser(getView().getContext(), userBean);
        Intent intent = new Intent();
        if (isName)
            intent.setAction(Constant.LOCAL_BROADCAST_USER_NAME_FLAG);
        else
            intent.setAction(Constant.LOCAL_BROADCAST_USER_AVATAR_FLAG);
        LocalBroadcastManager.getInstance(getView().getContext()).sendBroadcast(intent);
    }
    /**
     * 加载用户数据
     */
    @Override
    public void loadListData() {
        mModule.requestCompanyInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), new OnDataGetCallback<MePersonInfoBean>() {
            @Override
            public void onSuccessResult(MePersonInfoBean personInfoBean) {

                ApiClient.menuDataToList(personInfoBean, mModule.getMenuBeanList());
                if (personInfoBean != null) {
                    String userPicture = personInfoBean.getUserPicture();
                    String avatar = LoginHelper.getInstance().getUserBean().getAvatar();
                    if (avatar != null && !userPicture.equals(avatar)) {
                        updateUserData(userPicture, false);
                    }
                }
                getView().bindListData(mModule.getMenuBeanList());
                getView().initRightView();
            }
        });
    }
    /**
     * 初始化menu
     */
    @Override
    public void initMenu() {
        getView().initRecyclerView();
        mModule.attachMenuList(getView().getMenuList());
    }

    /**
     * 当Item的数据更新时执行这个方法
     *
     * @param inputContent
     */
    @Override
    public void itemDataChange(String inputContent) {
        //更改单行item的数据
        getView().updateSingleItem(mModule.getCurrentClickPosition(), inputContent);
    }
    /**
     * item点击
     */
    @Override
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {

        final MenuBean bean = mModule.getItemBeanForPosition(position);
        String hintText = "";
        String titleText = "";
        int inputType = -1;
        switch (bean.getId()) {
            case R.id.company_menu_item_avatar:
                getView().openSelectPhotoPop();
                break;
            case R.id.company_menu_item_name:
            case R.id.company_menu_item_phone:
                if (bean.getId() == R.id.company_menu_item_phone) {
                    hintText = "请输入您的手机号";
                    titleText = "联系电话";
                    inputType = InputDataPopWindow.INPUT_TYPE_PHONE;
                    getView().setInputDataMax(14);
                } else if (bean.getId() == R.id.company_menu_item_name) {
                    hintText = "请输入公司名";
                    titleText = "公司名";
                    getView().setInputDataMax(50);
                }
                getView().setPopInputType(inputType);
                getView().setPopInputHintText(hintText);
                getView().setPopTitleText(titleText);
                getView().openInputSinglePop(bean.getMenuValue());
                break;
        }
    }


}
