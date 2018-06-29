package com.fcn.park.me.activity;


import android.content.Intent;
import android.view.View;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.databinding.MeMyMsgDetailActivityBinding;
import com.fcn.park.me.bean.MeMsgDetailBean;
import com.fcn.park.me.contract.MeMyMsgDetailContract;
import com.fcn.park.me.presenter.MeMyMsgDetailPresenter;

/**
 * 个人中心-我的消息-消息详情
 */
public class MeMyMsgDetailActivity extends
        BaseActivity<MeMyMsgDetailActivityBinding, MeMyMsgDetailContract.View, MeMyMsgDetailPresenter> implements
        MeMyMsgDetailContract.View {

    private String msgId;
    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText("消息详情");
    }
    /**
     * 初期化视图
     */
    @Override
    protected void initViews() {
        msgId = getIntent().getStringExtra("msgId");
        mDataBinding.setPresenter(mPresenter);
        mPresenter.loadMsgDetailById();
    }
    /**
     * 设置视图布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_my_msg_detail_activity;
    }
    /**
     * 实例化presenter
     */
    @Override
    protected MeMyMsgDetailPresenter createPresenter() {
        return new MeMyMsgDetailPresenter();
    }

    /**
     * 获取消息Id
     */
    @Override
    public String getMsgId() {
        return msgId;
    }
    /**
     * 获取消息详情
     */
    @Override
    public void setMsgDetailInfo(MeMsgDetailBean bean) {
        mDataBinding.setBean(bean);
        doVisible(bean.getActivityType());
    }

    /**
     * 根据消息类型按钮显示不同文字
     */
    private void doVisible(String type) {
        switch (type) {
            //各种缴费
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
                //广告费申请同意
            case "10":
                setButtonVisible(true);
                setButtonText("去缴费");
                break;
                //企业认证失败
            case "7":
                setButtonVisible(true);
                setButtonText("重新认证");
                break;
            //企业认证成功
            case "8":
                //全体通知
            case "9":
                //广告费申请被拒绝
            case "11":
                setButtonVisible(false);
                break;
        }
    }

    /**
     * 设置按钮显示
     */
    private void setButtonVisible(boolean visible) {
        if (visible) {
            mDataBinding.bottomBtnGo.setVisibility(View.VISIBLE);
        } else {
            mDataBinding.bottomBtnGo.setVisibility(View.GONE);
        }
    }
    /**
     * 设置按钮文字
     */
    private void setButtonText(String text) {
        mDataBinding.bottomBtnGo.setText(text);
    }
    /**
     * 带参数跳转画面--交各种费用
     */
    @Override
    public void jumpActivity(Class cl,String flg,String id){
        Intent i = new Intent(getContext(),cl);
        i.putExtra(Constant.PROPERTY_PAY_TYPE,flg);
        i.putExtra(Constant.PROPERTY_PAY_ID,id);
        startActivity(i);
    }
    /**
     * 带参数跳转画面--企业认证
     */
    @Override
    public void jumpActivity(Class cl,String flg){
        Intent i = new Intent(getContext(),cl);
        i.putExtra("flg",flg);
        startActivity(i);
    }
}
