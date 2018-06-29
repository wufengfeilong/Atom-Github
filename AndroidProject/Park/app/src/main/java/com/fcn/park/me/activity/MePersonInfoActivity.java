package com.fcn.park.me.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.base.utils.MenuBuildUtils;
import com.fcn.park.base.widget.InputSingleDataPopWindow;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.MePersonInfoActivityBinding;
import com.fcn.park.manager.utils.DialogUtils;
import com.fcn.park.manager.utils.SpaceItemDecoration;
import com.fcn.park.me.adapter.MePersonInfoAdapter;
import com.fcn.park.me.bean.InputMenuBean;
import com.fcn.park.me.contract.MePersonInfoContract;
import com.fcn.park.me.presenter.MePersonInfoPresenter;

import java.util.List;

/**
 * 个人首页-个人信息编辑
 */
public class MePersonInfoActivity extends BaseActivity<MePersonInfoActivityBinding, MePersonInfoContract.View, MePersonInfoPresenter>
        implements MePersonInfoContract.View {
    private InputSingleDataPopWindow mInputContentPop;
    private SinglePictureSelectPopWindow mSelectPopWindow;
    private MePersonInfoAdapter mAdapter;
    private boolean isChange;

    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        setTitleText("个人信息编辑");
        openTitleLeftView(true);
    }
    /**
     * 初期化视图
     */
    @Override
    protected void initViews() {
        mPresenter.initMenu();
        mPresenter.loadListData();
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mInputContentPop = new InputSingleDataPopWindow(this);
        mInputContentPop.setOnClickSaveCallback(new InputSingleDataPopWindow.OnClickSaveCallback() {
            @Override
            public void onSaveCallback(String content) {
                mPresenter.itemDataChange(content);
            }
        });
    }
    /**
     * 设置视图布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_person_info_activity;
    }
    /**
     * 实例化presenter
     */
    @Override
    protected MePersonInfoPresenter createPresenter() {
        return new MePersonInfoPresenter();
    }
    /**
     * 更新个人信息
     */
    @Override
    public void updateSingleItem(int position, String value) {
        isChange = mAdapter.updateSingleItem(position, value);
        if (isChange && getTitleRightView().getVisibility() == View.GONE) {
            getTitleRightView().setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置弹出框建议文字
     */
    @Override
    public void setPopInputHintText(String inputHintText) {
        mInputContentPop.setInputHintText(inputHintText);
    }
    /**
     * 设置弹出框输入类型
     */
    @Override
    public void setPopInputType(int inputType) {
        mInputContentPop.setInputType(inputType);
    }
    /**
     * 设置输入最大长度
     */
    @Override
    public void setInputDataMax(int max) {
        mInputContentPop.setCheckInputMaxLength(max);
    }
    /**
     * 设置弹出框标题
     */
    @Override
    public void setPopTitleText(String titleText) {
        mInputContentPop.setTitleText(titleText);
    }
    /**
     * 打开头像选择弹出框
     */
    @Override
    public void openSelectPhotoPop() {
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }
    /**
     * 打开单个item弹出框
     */
    @Override
    public void openInputSinglePop(String value) {
        mInputContentPop.showPopupWindow(value, mDataBinding.getRoot());
    }
    /**
     * 画面右上角保存按钮初始化
     */
    @Override
    public void initRightView() {
        setTitleRightText("保存").setVisibility(View.GONE);
        getTitleRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickSave();
            }
        });
    }
    /**
     * 根据改变状态设置保存按钮显示隐藏
     */
    @Override
    public void changeSaveState(boolean isChange) {
        this.isChange = isChange;
        if (isChange) {
            getTitleRightView().setVisibility(View.VISIBLE);
        } else {
            getTitleRightView().setVisibility(View.GONE);
        }
    }
    /**
     * 获取RecyclerView
     */
    @Override
    public void initRecyclerView() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        OnItemClickListener onItemClickListener = new OnItemClickListener(mDataBinding.recyclerView);
        mDataBinding.recyclerView.addOnItemTouchListener(onItemClickListener);
        mAdapter = new MePersonInfoAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }
    /**
     * 绑定list数据
     */
    @Override
    public void bindListData(List<InputMenuBean> beanList) {
        mDataBinding.recyclerView.addItemDecoration(new SpaceItemDecoration(mContext, beanList));
        mAdapter.setupData(beanList);
    }
    /**
     * 获取RecyclerView
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerView;
    }
    /**
     * 获取菜单列表
     */
    @Override
    public List<MenuItemImpl> getMenuList() {
        return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_person_info_menu);
    }
    /**
     * 列表点击事件
     */
    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }
    }

    /**
     * 图片选择回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);
            mAdapter.updateAvatar(imageUrl);
            mPresenter.startUploadUserAvatar(imageUrl);
        }
    }
    /**
     * 画面结束处理
     */
    @Override
    public void finish() {
        if (isChange) {
            DialogUtils.builderAlertDialog(mContext, "温馨提示", "您的数据有更改，是否立即保存")
                    .setNegativeButton("暂不保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isChange = false;
                            finish();
                        }
                    })
                    .setPositiveButton("立即保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.onClickSave();
                        }
                    }).show();
        } else
            super.finish();
    }
}
