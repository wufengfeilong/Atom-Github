package com.fcn.park.me.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.base.utils.MenuBuildUtils;
import com.fcn.park.base.widget.InputSingleDataPopWindow;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.manager.utils.DialogUtils;
import com.fcn.park.manager.utils.SpaceItemDecoration;
import com.fcn.park.me.adapter.MePersonInfoAdapter;
import com.fcn.park.me.bean.InputMenuBean;
import com.fcn.park.me.bean.PictureBean;
import com.fcn.park.me.contract.MeCompanyInfoContract;
import com.fcn.park.me.presenter.MeCompanyInfoPresenter;
import com.fcn.park.databinding.MeCompanyInfoActivityBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心-公司信息编辑
 */
public class MeCompanyInfoActivity extends BaseActivity<MeCompanyInfoActivityBinding, MeCompanyInfoContract.View, MeCompanyInfoPresenter>
        implements MeCompanyInfoContract.View {
    private InputSingleDataPopWindow mInputContentPop;
    private SinglePictureSelectPopWindow mSelectPopWindow;
    private MePersonInfoAdapter mAdapter;
    private boolean isChange;
    //图片展示变化flg
    private boolean isPictureChange;
    //公司详情变化flg
    private boolean isIntroduceChange;
    //选择图片的控件id
    int imgId;
    //图片展示list
    List<PictureBean> pictureBeanList;
    //需要上传更新图片list
    List<PictureBean> updateList;
    //公司详情
    String introduce;

    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        setTitleText("公司信息编辑");
        openTitleLeftView(true);
    }

    /**
     * 初期化视图
     */
    @Override
    protected void initViews() {
        mPresenter.initMenu();
        //初始化临时list
        updateList = new ArrayList<>();

        mPresenter.loadListData();
        //加载公司详情
        mPresenter.loadIntroduceText();
        //加载展示图片
        mPresenter.loadPictures();
        mDataBinding.setPresenter(mPresenter);
        //解决输入内容被软禁盘遮挡问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //监听软键盘显示状态
        setKeyboardStateListener();
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

        mDataBinding.companyInfoLl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDataBinding.companyInfoLl.setFocusable(true);
                mDataBinding.companyInfoLl.setFocusableInTouchMode(true);
                mDataBinding.companyInfoLl.requestFocus();
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (isIntroduceChange) {
                    getTitleRightView().setVisibility(View.VISIBLE);
                } else if (!isChange&&!isPictureChange) {
                    getTitleRightView().setVisibility(View.GONE);
                }
                return false;
            }
        });
        //监听公司详情变化
        mDataBinding.meCompanyDetailIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(introduce)) {
                    isIntroduceChange = true;
                } else {
                    isIntroduceChange =false;
                }
            }
        });
    }

    /**
     * 软键盘显示隐藏状态监听器
     */
    @Override
    public void setKeyboardStateListener() {
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取View可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                if(bottom!=0 && oldBottom!=0 && bottom - rect.bottom <= 0){
                    if (isIntroduceChange) {
                        getTitleRightView().setVisibility(View.VISIBLE);
                    } else if (!isChange&&!isPictureChange) {
                        getTitleRightView().setVisibility(View.GONE);
                    }
                }else {
                    //Log.d("软键盘", "开启");
                }
            }
        });
    }
    /**
     * 设置视图布局
     */
    @Override
    protected int getLayoutId() {
        return R.layout.me_company_info_activity;
    }
    /**
     * 实例化presenter
     */
    @Override
    protected MeCompanyInfoPresenter createPresenter() {
        return new MeCompanyInfoPresenter();
    }

    /**
     * 更新企业信息
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
        imgId = 0;
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
     * 获取公司信息介绍
     */
    @Override
    public String getCompanyIntroduce() {
        return mDataBinding.meCompanyDetailIntroduce.getText().toString();
    }
    /**
     * 获取上传图片list
     */
    @Override
    public List<PictureBean> getImgUrls() {
        return updateList;
    }
    /**
     * 展示图片变化flg
     */
    @Override
    public boolean isPictureChange() {
        return isPictureChange;
    }
    /**
     * 设置展示图片变化flg
     */
    @Override
    public void setIsPictureChange(boolean isChange) {
        isPictureChange = isChange;
    }
    /**
     * 公司详情变化flg
     */
    @Override
    public boolean isIntroduceChange() {
        return isIntroduceChange;
    }
    /**
     * 设置公司详情变化flg
     */
    @Override
    public void setIsIntroduceChange(boolean isChange) {
        isIntroduceChange = isChange;
    }
    /**
     * 公司信息变化flg
     */
    @Override
    public boolean isChange() {
        return isChange;
    }
    /**
     * 打开展示图片pop
     */
    @Override
    public void openSelectBannerPop(int resID) {
        imgId = resID;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }
    /**
     * 设置公司详情
     */
    @Override
    public void setIntroduceText(String introduce) {
        this.introduce = introduce;
        mDataBinding.meCompanyDetailIntroduce.setText(introduce);
    }
    /**
     * 设置展示图片
     */
    @Override
    public void setPictures(List<PictureBean> list) {
        pictureBeanList = list;
        for (int i=0;i<5;i++) {
            PictureBean bean = new PictureBean();
            if (i<pictureBeanList.size()) {
                bean.setId(pictureBeanList.get(i).getId());
            }
            else{
                bean.setId("0");
            }
            updateList.add(bean);
        }
        for (int i=0; i<list.size();i++) {
            switch (i) {
                case 0:
                    Glide.with(getContext()).load(ApiService.IMAGE_BASE + list.get(0).getPath()).into(mDataBinding.meCompanyBanner1);
                    mDataBinding.meCompanyBanner2.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    Glide.with(getContext()).load(ApiService.IMAGE_BASE + list.get(1).getPath()).into(mDataBinding.meCompanyBanner2);
                    mDataBinding.meCompanyBanner3.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    Glide.with(getContext()).load(ApiService.IMAGE_BASE + list.get(2).getPath()).into(mDataBinding.meCompanyBanner3);
                    mDataBinding.meCompanyBanner4.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    Glide.with(getContext()).load(ApiService.IMAGE_BASE + list.get(3).getPath()).into(mDataBinding.meCompanyBanner4);
                    mDataBinding.meCompanyBanner5.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    Glide.with(getContext()).load(ApiService.IMAGE_BASE + list.get(4).getPath()).into(mDataBinding.meCompanyBanner5);
                    break;
            }
        }
    }
    /**
     * 初始化RecyclerView
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
    public List<MenuItemImpl> getMenuList() {
        return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_company_info_menu);
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
            //头像选择
            if (imgId == 0) {
                mAdapter.updateAvatar(imageUrl);
                mPresenter.startUploadUserAvatar(imageUrl);
            } else {
                //公司展示图片
                switch (imgId) {
                    case R.id.me_company_banner1:
                        updateList.get(0).setPath(imageUrl);
                        updateList.get(0).setFlg(true);
                        mDataBinding.meCompanyBanner2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.me_company_banner2:
                        updateList.get(1).setPath(imageUrl);
                        updateList.get(1).setFlg(true);
                        mDataBinding.meCompanyBanner3.setVisibility(View.VISIBLE);
                        break;
                    case R.id.me_company_banner3:
                        updateList.get(2).setPath(imageUrl);
                        updateList.get(2).setFlg(true);
                        mDataBinding.meCompanyBanner4.setVisibility(View.VISIBLE);
                        break;
                    case R.id.me_company_banner4:
                        updateList.get(3).setPath(imageUrl);
                        updateList.get(3).setFlg(true);
                        mDataBinding.meCompanyBanner5.setVisibility(View.VISIBLE);
                        break;
                    case R.id.me_company_banner5:
                        updateList.get(4).setPath(imageUrl);
                        updateList.get(4).setFlg(true);
                        break;
                }
                isPictureChange = true;
                getTitleRightView().setVisibility(View.VISIBLE);
                Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(imgId));
            }
        }
    }

    /**
     * 画面结束处理
     */
    @Override
    public void finish() {
        if (isChange || isPictureChange || isIntroduceChange) {
            DialogUtils.builderAlertDialog(mContext, "温馨提示", "您的数据有更改，是否立即保存")
                    .setNegativeButton("暂不保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isChange = false;
                            isPictureChange = false;
                            isIntroduceChange = false;
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
