package com.fcn.park.me.presenter;


import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.me.activity.MeCompanyAuthActivity;
import com.fcn.park.me.bean.MeMsgDetailBean;
import com.fcn.park.me.contract.MeMyMsgDetailContract;
import com.fcn.park.me.module.MeMyMsgDetailModule;
import com.fcn.park.property.activity.PropertyPayActivity;


/**
 * create by 860115039
 * date      2018/04/23
 * time      13:07
 * 个人中心-消息详情presenter
 */
public class MeMyMsgDetailPresenter extends BasePresenter<MeMyMsgDetailContract.View> implements MeMyMsgDetailContract.Presenter{

    private MeMyMsgDetailModule mMeMyMsgDetailModule;
    private MeMsgDetailBean bean;
    @Override
    public void attach(MeMyMsgDetailContract.View view) {
        super.attach(view);
        mMeMyMsgDetailModule = new MeMyMsgDetailModule();
    }
    /**
     * 通过用户id加载消息详情
     */
    @Override
    public void loadMsgDetailById() {
        mMeMyMsgDetailModule.getMsgDetail(getView().getContext(), getView().getMsgId(), new OnDataGetCallback<MeMsgDetailBean>() {
            @Override
            public void onSuccessResult(MeMsgDetailBean data) {
                getView().setMsgDetailInfo(data);
                bean = data;
            }
        });
    }
    /**
     * 更具不用消息类型跳转相应页面
     */
    @Override
    public void toPayFee() {
        String type = bean.getActivityType();
        switch (type) {
            case "1":
            case "2":
            case "3":
            case "4":
                getView().jumpActivity(PropertyPayActivity.class,type,null);
                break;
            case "5":
                //停车费
                getView().jumpActivity(PropertyPayActivity.class,type,bean.getPayId());
                break;
            case "7":
                //企业认证
                getView().jumpActivity(MeCompanyAuthActivity.class,"2");
                break;
            case "10":
                //广告费申请通过
                getView().jumpActivity(PropertyPayActivity.class,null,bean.getPayId());
                break;
            case "11":
                //广告费申请被拒绝
                getView().jumpActivity(PropertyPayActivity.class,null,bean.getPayId());
                break;
        }
    }
}
