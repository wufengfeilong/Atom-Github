package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.contract.MenuLoadContract;
import com.fcn.park.me.bean.InputMenuBean;
import com.fcn.park.me.bean.PictureBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/04/16
 * time      15:58
 * 个人中心-企业信息编辑Contract
 */
public interface MeCompanyInfoContract {
    interface View extends BaseView, RecyclerViewContract.View<InputMenuBean>, MenuLoadContract.View {

        /**
         * 修改单行数据
         *
         * @param position
         * @param value
         */
        void updateSingleItem(int position, String value);

        /**
         * 设置PopWindow中提示的文字
         *
         * @param inputHintText
         */
        void setPopInputHintText(String inputHintText);

        /**
         * 设置PopWindow输入框中的输入类型
         *
         * @param inputType
         */
        void setPopInputType(int inputType);

        /**
         * 设置输入的内容最大值
         */
        void setInputDataMax(int max);
        /**
         * 设置PopWindow中的标题
         *
         * @param titleText
         */
        void setPopTitleText(String titleText);

        /**
         * 打开选择图片的PopWindow
         */
        void openSelectPhotoPop();

        /**
         * 打开单行输入的PopWindow
         */
        void openInputSinglePop(String value);;

        /**
         * 初始化块右侧的控件
         */
        void initRightView();
        /**
         * 根据改变状态设置保存按钮显示隐藏
         */
        void changeSaveState(boolean isChange);
        /**
         * 获取公司信息介绍
         */
        String getCompanyIntroduce();
        /**
         * 获取上传图片list
         */
        List<PictureBean> getImgUrls();
        /**
         * 展示图片变化flg
         */
        boolean isPictureChange();
        /**
         * 设置展示图片变化flg
         */
        void setIsPictureChange(boolean isChange);
        /**
         * 公司详情变化flg
         */
        boolean isIntroduceChange();
        /**
         * 设置公司详情变化flg
         */
        void setIsIntroduceChange(boolean isChange);
        /**
         * 公司信息变化flg
         */
        boolean isChange();
        /**
         * 打开展示图片pop
         */
        void openSelectBannerPop(int resID);
        /**
         * 设置公司详情
         */
        void setIntroduceText(String introduce);
        /**
         * 设置展示图片
         */
        void setPictures(List<PictureBean> list);
        /**
         * 软键盘显示隐藏状态监听器
         */
        void setKeyboardStateListener();
    }

    interface Presenter extends RecyclerViewContract.Presenter, MenuLoadContract.Presenter {
        /**
         * 保存公司信息
         */
        void onClickSave();

        /**
         * 当输入的数据点击保存后回调的数据
         *
         * @param inputContent
         */
        void itemDataChange(String inputContent);
        /**
         * 头像压缩
         */
        void startUploadUserAvatar(String filePat);
        /**
         * 添加第一张轮播图
         */
        void addBanner1();
        /**
         * 添加第二张轮播图
         */
        void addBanner2();
        /**
         * 添加第三张轮播图
         */
        void addBanner3();
        /**
         * 添加第四张轮播图
         */
        void addBanner4();
        /**
         * 添加第五张轮播图
         */
        void addBanner5();
        /**
         * 加载公司详情
         */
        void loadIntroduceText();
        /**
         * 加载公司轮播图
         */
        void loadPictures();

    }
}
