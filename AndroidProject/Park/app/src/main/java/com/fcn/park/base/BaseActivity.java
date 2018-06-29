package com.fcn.park.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.utils.RefreshHandler;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 *
 * @param <D> ViewDataBinding 数据绑定
 * @param <V> BaseView View接口
 * @param <P> BasePresenter 业务逻辑基盘
 */
public abstract class BaseActivity<D extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    private static void actionStartActivity(Context currentActivity, Class activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
    }

    protected static int DEFAULT_TITLE_TEXT_COLOR = -2;

    //设置标题的属性
    protected abstract void setupTitle();

    protected abstract void initViews();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    /**
     * Activity的DataBinding对象
     */
    protected D mDataBinding;

    protected P mPresenter;

    protected Context mContext;
    protected Activity mActivity;

    //标题栏的具体控件
    private TextView mTvTitleLeftHintView;
    private CheckBox mCbxTitleRightView;
    private LinearLayout mLayoutTitleCenterView;
    private LinearLayout mLayoutTitleLeft;
    private ImageView mIvTitleBackIcon;
    protected RelativeLayout mRlTitleLayoutParent;
    protected ImageView mSearchView;

    protected LinearLayout mLayoutTitleRight;
    protected TextView mTvTitleRight;

    //控制是否使用默认的标题栏
    protected boolean isUseDefaultTitle = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = setContentView();
        mContext = this;
        mActivity = this;

        //在调用所有的方法之前，用来初始化一些成员变量
        initFieldBeforeMethods();

        mPresenter = createPresenter();
        mPresenter.attach((V) this);

        if (isUseDefaultTitle) {
            initTitle();
        }
        setupTitle();
        initViews();

    }

    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.deAttach();
        if (mListData != null) {
            mListData.clear();
            mListData = null;
        }
        if (mRefreshHandler != null)
            mRefreshHandler.onActivityFinish();
    }

    /**
     * 页面跳转
     *
     * @param cls
     */
    public void actionStartActivity(Class cls) {
        actionStartActivity(mContext, cls);
    }

    public void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    public int getResColor(@ColorRes int color) {
        return ContextCompat.getColor(mContext, color);
    }

    public void closeActivity() {
        finish();
    }

    /**
     * 用来初始化一些成员变量，这个方法调用的时间在所有方法调用之前
     * 因为不一定所有的子类都需要设置这个方法，所以不写成为抽象的方法了
     */
    protected void initFieldBeforeMethods() {
    }

    /**
     * 通过DataBindingUtils填充页面布局并返回数据绑定对象
     *
     * @return 当前页面数据绑定对象
     */
    protected D setContentView() {
        if (mDataBinding == null)
            return DataBindingUtil.setContentView(this, getLayoutId());
        return mDataBinding;
    }

    /**
     * 显示ToolBar左侧的按钮，并将点击事件激活
     */
    protected LinearLayout openTitleLeftView(boolean isDefault) {
        if (mLayoutTitleLeft == null) return null;
        if (isDefault) {
            mLayoutTitleLeft.setVisibility(View.VISIBLE);
        }
        mLayoutTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        return mLayoutTitleLeft;
    }

    /**
     * 设置左侧图标
     *
     * @param leftDrawable
     */
    protected void setTitleLeftIcon(Drawable leftDrawable) {
        ViewCompat.setBackground(mIvTitleBackIcon, leftDrawable);
    }

    /**
     * 设置左侧图标
     *
     * @param leftIconRes
     */
    protected void setTitleLeftIconRes(@DrawableRes int leftIconRes) {
        mIvTitleBackIcon.setImageResource(leftIconRes);
    }

    /**
     * 设置左侧提示文字
     *
     * @param text
     */
    protected void setTitleLeftHintText(String text) {
        mTvTitleLeftHintView.setText(text);
    }

    protected CheckBox setTitleRightText(String text) {
        return setTitleRightText(text, DEFAULT_TITLE_TEXT_COLOR);
    }

    protected CheckBox setTitleRightText(String text, int textColor) {
        if (mCbxTitleRightView.getVisibility() == View.GONE)
            mCbxTitleRightView.setVisibility(View.VISIBLE);
        mCbxTitleRightView.setText(text);
        if (textColor != DEFAULT_TITLE_TEXT_COLOR)
            mCbxTitleRightView.setTextColor(textColor);
        return mCbxTitleRightView;
    }
    protected CheckBox getTitleRightView() {
        return mCbxTitleRightView;
    }
    protected void setTitleText(String titleText) {
        setTitleText(titleText, DEFAULT_TITLE_TEXT_COLOR);
    }

    /**
     * @param menuText
     *        右侧显示内容
     */
    protected void setRightMenuText(String menuText) {
        mLayoutTitleRight.setVisibility(View.VISIBLE);
        mTvTitleRight.setText(menuText);
        mTvTitleRight.setTextColor(DEFAULT_TITLE_TEXT_COLOR);
    }
    protected void setTitleText(String text, int textColor) {

        TextView titleView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,35,getResources().getDisplayMetrics());
        params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,35,getResources().getDisplayMetrics());
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setLayoutParams(params);
        titleView.setGravity(Gravity.CENTER);
        titleView.setSingleLine();
        titleView.setText(text == null ? "" : text);
        if (textColor != DEFAULT_TITLE_TEXT_COLOR)
            titleView.setTextColor(textColor);
        else
            titleView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitleColor));
        titleView.setTextSize(16);
        setTitleCenterView(titleView, false);
    }

    protected View setTitleCenterViewRes(int layoutRes, boolean isToLeftOrRightOf) {
        View titleCenterView = LayoutInflater.from(mContext).inflate(layoutRes, null);
        setTitleCenterView(titleCenterView, isToLeftOrRightOf);
        return titleCenterView;
    }

    /**
     * 设置中间的控件
     *
     * @param view              具体填充的控件
     * @param isToLeftOrRightOf 设置控件是否按照在某个控件的左边或者右边
     */
    protected void setTitleCenterView(View view, boolean isToLeftOrRightOf) {
        if (mLayoutTitleCenterView == null) return;
        if (view.getLayoutParams() == null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
        }
        mLayoutTitleCenterView.removeAllViews();
        mLayoutTitleCenterView.addView(view);

        //设置控件是否按照在某个控件的左边或者右边
        if (isToLeftOrRightOf) {
            //获取CenterView的布局参数
            RelativeLayout.LayoutParams layoutParams = null;
            //判断如果它的布局参数不为空的时候，就直接取出赋值,如果为空就新建一个
            if (mLayoutTitleCenterView.getLayoutParams() != null) {
                layoutParams = (RelativeLayout.LayoutParams) mLayoutTitleCenterView.getLayoutParams();
            } else {
                layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
            //设置CenterView在布局中需要依附到哪些控件的旁边
            layoutParams.addRule(RelativeLayout.LEFT_OF, mCbxTitleRightView.getId());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.addRule(RelativeLayout.START_OF, mCbxTitleRightView.getId());
            }
            layoutParams.addRule(RelativeLayout.RIGHT_OF, mLayoutTitleLeft.getId());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.addRule(RelativeLayout.END_OF, mLayoutTitleLeft.getId());
            }
        }
    }


    private void initTitle() {
//        mIvTitleBackIcon = (ImageView) findViewById(R.id.iv_app_title_layout_back);
//        mTvTitleLeftHintView = (TextView) findViewById(R.id.tv_app_title_layout_left_hint);
        mCbxTitleRightView = (CheckBox) findViewById(R.id.cbx_app_title_layout_right);
        mLayoutTitleCenterView = (LinearLayout) findViewById(R.id.ll_app_title_layout_center);
        mLayoutTitleLeft = (LinearLayout) findViewById(R.id.ll_app_title_layout_left);
//        mRlTitleLayoutParent = (RelativeLayout) findViewById(R.id.rl_app_title_layout_parent);
        mSearchView = (ImageView) findViewById(R.id.iv_app_title_layout_search);

        mLayoutTitleRight = (LinearLayout) findViewById(R.id.ll_app_title_layout_right);
        mTvTitleRight = (TextView) findViewById(R.id.tv_app_title_layout_right);

    }

    /**
     * 屏幕变亮
     */
    public void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 屏幕变暗
     */
    public void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    public void showProgressDialog(String message) {
        getProgressDialog().setMessage(message);
        getProgressDialog().show();
    }

    public void hindProgressDialog() {
        getProgressDialog().hide();
    }

    private ProgressDialog getProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        return mProgressDialog;
    }

    private List mListData;
    private SRefreshHandler mRefreshHandler;

    public <T> void setListData(List<T> listData) {
        mListData = listData;
    }

    public void onRefresh(boolean isEnd) {
        initRefreshHandler();
        mRefreshHandler.onPullDownToRefresh(isEnd);
    }


    public void onLoadMore(boolean isEnd) {
        initRefreshHandler();
        mRefreshHandler.onPullUpToRefresh(isEnd);
    }

    private void initRefreshHandler() {
        if (mRefreshHandler == null) {
            mRefreshHandler = new SRefreshHandler(this);
        }
    }

    private static class SRefreshHandler extends RefreshHandler {
        private WeakReference<BaseActivity> mActivity;

        SRefreshHandler(BaseActivity activity) {
            super(activity.getRefreshView());
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void getListDatas(int page) {
            if (page == 1) {
                if (mActivity.get().mListData != null) {
                    mActivity.get().mListData.clear();
                }
            }
            mActivity.get().loadListData(page);
        }
    }

    protected ViewGroup getRefreshView() {
        return null;
    }

    protected void loadListData(int page) {
    }

}
