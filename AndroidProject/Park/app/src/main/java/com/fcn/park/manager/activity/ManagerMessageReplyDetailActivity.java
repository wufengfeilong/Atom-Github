package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerMessageReplyDetailBinding;
import com.fcn.park.manager.bean.ManagerMessageReplyDetailInfoBean;
import com.fcn.park.manager.contract.ManagerMessageReplyDetailContract;
import com.fcn.park.manager.presenter.ManagerMessageReplyDetailPresenter;

/**
 * Created by 丁胜胜 on 2018/04/25
 * 类处理：管理中心留言回复详情用Activity
 */
public class ManagerMessageReplyDetailActivity extends BaseActivity<ManagerMessageReplyDetailBinding,ManagerMessageReplyDetailContract.View,ManagerMessageReplyDetailPresenter>
                implements ManagerMessageReplyDetailContract.View{

    private static final String ID_TAG = "suggestionId";

    private String suggestionId;

    /**
     * 将从后台取到的内容显示到画面上
     * @param bean
     */
    @Override
    public void updateInfo(ManagerMessageReplyDetailInfoBean bean) {
        mDataBinding.setInfoBean(bean);
    }

    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_reply_message_detail));
        openTitleLeftView(true);
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadInfo();
        // 画面初始化时，禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 将Presenter通过DataBinding与当前的View绑定
        mDataBinding.setMessageReplyDetailPresenter(mPresenter);

    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_message_reply_detail;
    }

    @Override
    protected ManagerMessageReplyDetailPresenter createPresenter() {
        return new ManagerMessageReplyDetailPresenter();
    }

    /**
     * 获取SuggestionId
     * @return
     */
    @Override
    public String getSuggestionId() {
        return suggestionId;
    }

    /**
     * 重写的方法，通过intent获取前画面传过来的值
     * 用来初始化一些成员变量，这个方法调用的时间在所有方法调用之前
     */
    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        suggestionId = intent.getStringExtra(ID_TAG);
    }

    /**
     * 启动当前的Activity
     * @param context
     * @param suggestionId
     */
    public static void actionStart(Context context, String suggestionId) {
        Intent intent = new Intent(context, ManagerMessageReplyDetailActivity.class);
        intent.putExtra(ID_TAG, suggestionId);
        context.startActivity(intent);
    }

    /**
     * 获取输入的留言回复的内容
     * @return
     */
    @Override
    public String getInputReplyData() {
        return mDataBinding.managerReplyAnswer.getText().toString().trim();
    }
}
