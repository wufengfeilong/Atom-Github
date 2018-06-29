package com.fcn.park.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fcn.park.R;
import com.fcn.park.base.utils.RefreshHandler;
import com.fcn.park.base.widget.TitleCompatLayout;
import com.liaoinstan.springview.widget.SpringView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by 860115001 on 2018/04/03.
 * 类描述：Fragment的基类
 */

public abstract class BaseFragment<D extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends Fragment {
    protected static int DEFAULT_TITLE_TEXT_COLOR = -4;
    private ProgressDialog mProgressDialog;
    protected TitleCompatLayout mTitleCompatLayout;

    public static void actionStartActivity(Context currentActivity, Class activityClass) {
        Intent intent = new Intent(currentActivity, activityClass);
        currentActivity.startActivity(intent);
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initViews();

    protected abstract P createPresenter();

    protected D mDataBinding;

    protected P mPresenter;

    protected Context mContext;

    //标题栏的具体控件
    protected CheckBox mCbxTitleRightView;
    protected LinearLayout mLayoutTitleCenterView;
    protected LinearLayout mLayoutTitleLeft;
    protected ImageView mHelpView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        mPresenter = createPresenter();
        mContext = getContext();

        mPresenter.attach((V) this);
        initTitle();
        initViews();
        rootView =
        return mDataBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.deAttach();
        if (mListData != null) {
            mListData.clear();
            mListData = null;
        }
        if (mRefreshHandler != null) {
            mRefreshHandler.onActivityFinish();
        }
    }

    public void addViewOnClickListener(View.OnClickListener listener, View... views) {
        if (views != null && listener != null) {
            for (View view : views) {
                view.setOnClickListener(listener);
            }
        }
    }

    private void initTitle() {
//        mIvTitleBackIcon = (ImageView) mDataBinding.getRoot().findViewById(R.id.iv_app_title_layout_back);
//        mTvTitleLeftHintView = (TextView) mDataBinding.getRoot().findViewById(R.id.tv_app_title_layout_left_hint);
//        mCbxTitleRightView = (CheckBox) mDataBinding.getRoot().findViewById(R.id.cbx_app_title_layout_right);
        mLayoutTitleCenterView = (LinearLayout) mDataBinding.getRoot().findViewById(R.id.ll_app_title_layout_center);
//        mLayoutTitleLeft = (LinearLayout) mDataBinding.getRoot().findViewById(R.id.ll_app_title_layout_left);
        mTitleCompatLayout = (TitleCompatLayout) mDataBinding.getRoot().findViewById(R.id.title_compat_app_title_layout);
        mHelpView = (ImageView) mDataBinding.getRoot().findViewById(R.id.iv_app_title_layout_search);
    }

    protected void setTitleText(String titleText) {
        setTitleText(titleText, DEFAULT_TITLE_TEXT_COLOR);
    }

    protected void setTitleText(String text, int textColor) {

        TextView titleView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleView.setLayoutParams(params);
        titleView.setGravity(Gravity.CENTER);
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

    public Context getContext() {
        return super.getContext();
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

    public void closeActivity() {
        getActivity().finish();
    }

    public int getResColor(@ColorRes int color) {
        return ContextCompat.getColor(mContext, color);
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
            mProgressDialog = new ProgressDialog(getContext());
        }
        return mProgressDialog;
    }
    private List mListData;
    private SRefreshHandler mRefreshHandler;

    public <T> void setListData(List<T> listData) {
        mListData = listData;
    }

    public void initLoadData() {
        initRefreshHandler();
        mRefreshHandler.onPullDownToRefresh(null);
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

    protected static class SRefreshHandler extends RefreshHandler {
        private WeakReference<BaseFragment> mActivity;

        SRefreshHandler(BaseFragment activity) {
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
        if (getRefreshView() != null) {
            if (getRefreshView() instanceof SpringView) {
                ((SpringView) getRefreshView()).onFinishFreshAndLoad();
            }
        }
    }
}
