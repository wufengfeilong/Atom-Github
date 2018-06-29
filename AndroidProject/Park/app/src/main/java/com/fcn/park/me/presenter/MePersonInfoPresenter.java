package com.fcn.park.me.presenter;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import com.fcn.park.R;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.base.widget.InputDataPopWindow;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.login.bean.User;
import com.fcn.park.login.util.UserUtils;
import com.fcn.park.manager.bean.MenuBean;
import com.fcn.park.me.bean.MePersonInfoBean;
import com.fcn.park.me.contract.MePersonInfoContract;
import com.fcn.park.me.module.MePersonInfoModule;
import top.zibin.luban.OnCompressListener;

import java.io.File;
import java.util.List;

/**
 * create by 860115039
 * date      2018/04/16
 * time      16:00
 * 个人中心-个人信息编辑presenter
 */
public class MePersonInfoPresenter extends BasePresenter<MePersonInfoContract.View> implements MePersonInfoContract.Presenter{
    private MePersonInfoModule mModule;
    private boolean isChangeName;


    @Override
    public void attach(MePersonInfoContract.View view) {
        super.attach(view);
        mModule = new MePersonInfoModule();
    }
    /**
     * 保存个人信息
     */
    @Override
    public void onClickSave() {
        final MePersonInfoBean updateInfoBean = new MePersonInfoBean();

        List<String> strings = ApiClient.checkMenuListIsEmpty(mModule.getMenuBeanList());

        if (!strings.isEmpty()) {
            getView().showToast("请完善您的" + strings.get(0));
            return;
        }

        ApiClient.menuListToData(mModule.getMenuBeanList(), updateInfoBean);
        User userBean = LoginHelper.getInstance().getUserBean();

        if (userBean.getUserName() != null && !userBean.getUserName().equals(updateInfoBean.getName())) {
            isChangeName = true;
        }

        mModule.updatePersonInfo(getView().getContext(), userBean.getToken(), updateInfoBean, new OnDataGetCallback<Boolean>() {
            @Override
            public void onSuccessResult(Boolean s) {
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
     * 上传头像
     */
    private void uploadUserAvatar(File avatarFile) {
        mModule.uploadUserAvatar(getView().getContext(),LoginHelper.getInstance().getUserToken(), avatarFile, new OnDataGetCallback<String>() {
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
        mModule.requestPersonInfo(getView().getContext(), LoginHelper.getInstance().getUserToken(), new OnDataGetCallback<MePersonInfoBean>() {
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
            case R.id.person_menu_item_avatar:
                getView().openSelectPhotoPop();
                break;
            case R.id.person_menu_item_name:
            case R.id.person_menu_item_phone:
                if (bean.getId() == R.id.person_menu_item_phone) {
                    hintText = "请输入您的手机号";
                    titleText = "联系电话";
                    inputType = InputDataPopWindow.INPUT_TYPE_PHONE;
                    getView().setInputDataMax(14);
                } else if (bean.getId() == R.id.person_menu_item_name) {
                    hintText = "请输入您的姓名";
                    titleText = "姓名";
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
