package com.fcn.park.manager.presenter;

import android.text.TextUtils;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;
import com.fcn.park.manager.bean.ManagerMessageReplyDetailInfoBean;
import com.fcn.park.manager.contract.ManagerMessageReplyDetailContract;
import com.fcn.park.manager.module.ManagerMessageReplyDetailInfoModule;

import java.util.Map;

/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心留言详情Presenter
 */

public class ManagerMessageReplyDetailPresenter extends BasePresenter<ManagerMessageReplyDetailContract.View> implements ManagerMessageReplyDetailContract.Presenter{

    private ManagerMessageReplyDetailInfoModule mManagerMessageReplyDetailInfoModule;

    private ManagerMessageReplyDetailInfoBean replyDetailInfoDataBean = null;

    @Override
    public void attach(ManagerMessageReplyDetailContract.View view) {
        super.attach(view);
        mManagerMessageReplyDetailInfoModule = new ManagerMessageReplyDetailInfoModule();
    }
    @Override
    public void loadInfo() {
        mManagerMessageReplyDetailInfoModule.requestMessageReplyDetailInfo(getView().getContext(), getView().getSuggestionId(),new OnDataGetCallback<ManagerMessageReplyDetailInfoBean>(){
                    @Override
                    public void onSuccessResult(ManagerMessageReplyDetailInfoBean data) {
                        getView().updateInfo(data);
                        replyDetailInfoDataBean = data;
                    }
                }
        );
    }

    /**
     * 点击“回复”按钮的处理
     */
    @Override
    public void replyOnClick() {

        if (TextUtils.isEmpty(getView().getInputReplyData().toString().trim())) {
            getView().showToast("请输入回复内容！！");
            return;
        }

        // 将需要更新的内容保存到数据Bean中
        ManagerMessageReplyDetailInfoBean updateDataBean = new ManagerMessageReplyDetailInfoBean();
        updateDataBean.setSuggestionId(replyDetailInfoDataBean.getSuggestionId());
        int replyStatus = 2; // 审核通过时，将审核状态更新为2（拒绝）
        updateDataBean.setAnswerContent(getView().getInputReplyData().toString().trim());
        updateDataBean.setUpdateUser(LoginHelper.getInstance().getUserToken());

        Map<String, String> updateData = ApiClient.createBodyMap(updateDataBean);
        mManagerMessageReplyDetailInfoModule.updateMessageReplyInfoByAnswer(getView().getContext(), updateData, replyStatus,new OnDataGetCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast("回复成功！！！！");
                // 关闭当前的Activity，返回列表画面
                getView().closeActivity();
            }
        });
    }
}
