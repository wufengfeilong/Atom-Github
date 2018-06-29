package com.fcn.park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.utils.AppBottomLayoutManager;
import com.fcn.park.base.widget.TabItem;
import com.fcn.park.databinding.ActivityMainBinding;
import com.fcn.park.home.HomeFragment;
import com.fcn.park.info.view.InfoFragment;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.login.activity.LoginActivity;
import com.fcn.park.me.MeFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding,
        MainContract.View, MainPresenter> implements MainContract.View {

    private static final int LOGIN_REQUEST_CODE = 0x0000321;
    private AppBottomLayoutManager mBottomLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initFieldBeforeMethods() {
        isUseDefaultTitle = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            changeCurrentItem(Constant.AppModel.HOME.getValue());
        }
    }

    @Override
    protected void initViews() {
        ViewCompat.setElevation(mDataBinding.llActivityMainBottom, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));

        mBottomLayoutManager = new AppBottomLayoutManager(mContext, mDataBinding.llActivityMainBottom);

        mBottomLayoutManager.setContainerViewId(R.id.fl_activity_main_content);

        mBottomLayoutManager.addFragment(Constant.AppModel.HOME.getValue(), HomeFragment.newInstance());
        mBottomLayoutManager.addFragment(Constant.AppModel.INFO.getValue(), InfoFragment.newInstance());
        mBottomLayoutManager.addFragment(Constant.AppModel.ME.getValue(), MeFragment.newInstance());

        mBottomLayoutManager.setTabItem(Constant.AppModel.HOME.getValue(), R.drawable.ic_vector_main_home, R.drawable.ic_vector_main_home_unselected, getString(R.string.top_page));
        mBottomLayoutManager.setTabItem(Constant.AppModel.INFO.getValue(), R.drawable.ic_vector_main_info, R.drawable.ic_vector_main_info_unselected, getString(R.string.activity_page));
        mBottomLayoutManager.setTabItem(Constant.AppModel.ME.getValue(), R.drawable.ic_vector_main_me, R.drawable.ic_vector_main_me_unselected, getString(R.string.my_page));

        mBottomLayoutManager.setTabImageSize(20);

        mBottomLayoutManager.setOnItemClickChangeListener(new AppBottomLayoutManager.OnItemClickChangeListener() {
            @Override
            public boolean onItemClick(ViewGroup bottomLayout, View item, int position) {
                if (position == Constant.AppModel.ME.getValue()) {
                    if (LoginHelper.getInstance().isOnline())
                        return false;
                    else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(intent, LOGIN_REQUEST_CODE);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void onItemClickChanger(ViewGroup bottomLayout, View item, int position) {
                setTitleText(item instanceof TabItem ? ((TabItem) item).getTitleText() : "");
            }

            @Override
            public void onItemReselected(ViewGroup bottomLayout, View item, int position) {

            }
        });
        changeCurrentItem(Constant.AppModel.HOME.getValue());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void changeCurrentItem(int position) {
        if (position >= 0 && position < mBottomLayoutManager.getFragmentCount() && position != mBottomLayoutManager.getCurrentPosition()) {
            mBottomLayoutManager.setCurrentItem(position);
        }
    }

    @Override
    protected void setupTitle() {

    }

    //退出时的时间
    private long mExitTime;

    /**
     * 对返回键进行监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(mContext, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
