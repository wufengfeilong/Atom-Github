package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.contract.MenuLoadContract;
import com.fcn.park.me.bean.InputMenuBean;

/**
 * create by 860115039
 * date      2018/04/16
 * time      15:58
 * 个人中心-个人头像等信息编辑
 */
public interface MePersonInfoContract {
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
    }

    interface Presenter extends RecyclerViewContract.Presenter, MenuLoadContract.Presenter {
        /**
         * 保存个人信息
         */
        void onClickSave();

        /**
         * 当输入的数据点击保存后回调的数据
         *
         * @param inputContent
         */
        void itemDataChange(String inputContent);
        /**
         * 头像压缩上传
         */
        void startUploadUserAvatar(String filePat);

    }
}
