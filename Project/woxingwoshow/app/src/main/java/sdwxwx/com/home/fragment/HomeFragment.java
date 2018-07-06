package sdwxwx.com.home.fragment;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.view.View;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hyphenate.chat.EMClient;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.FragmentHomeBinding;
import sdwxwx.com.home.adapter.HomeCategoryAdapter;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.home.contract.HomeFragmentContract;
import sdwxwx.com.home.model.PickCityModel;
import sdwxwx.com.home.presenter.HomeFragmentPresenter;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.util.NetworkUtils;
import sdwxwx.com.widget.LoadStatusView;
import sdwxwx.com.widget.ScrollViewPager;
import sdwxwx.com.widget.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentPresenter>
        implements HomeFragmentContract.View {
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private HomeCategoryAdapter homeCategoryAdapter;
    private LoginHelper mHelper;
    public LocationClient mLocationClient = null;
    private CityLocationListener myListener = new CityLocationListener();
    CitySelectReceiver mReceiver;
    int city_id;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        cityLocation();
//        initTabs();
        //城市选择广播
        mReceiver = new CitySelectReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.INTENT_FILTER_SELECT_CITY);
        mContext.registerReceiver(mReceiver, filter);
        if (!NetworkUtils.isNetworkAvaiable(mContext)) {
            mDataBinding.homeLsv.setViewState(LoadStatusView.VIEW_STATE_ERROR, getString(R.string.common_no_network_msg));
            mDataBinding.homeLsv.setOnStatusPageClickListener(new LoadStatusView.OnStatusPageClickListener() {
                @Override
                public void onError() {
                    if (!NetworkUtils.isNetworkAvaiable(mContext)) {
                        return;
                    }
                    hasNetInitViews();
                    mDataBinding.homeLsv.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                }
            });
            return;
        }
        hasNetInitViews();
    }

    private void hasNetInitViews() {
        mHelper = LoginHelper.getInstance();
        tabLayout = mDataBinding.homeFragmentTl;
        viewPager = mDataBinding.homeFragmentVp;

        mPresenter.loadTabData();
    }

    private void cityLocation() {
        RxPermissions permissions = new RxPermissions(getActivity());
        permissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean value) {
                        // 如果已经给予定位的使用权限
                        if (value) {
                            // 开始定位
                            startLocation();
                        } else {
                            // 定位不可用
                            showToast("定位不可用,请手动选择城市！");
                            mDataBinding.homeTitleCityTv.setText("定位失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void startLocation() {
        mLocationClient = new LocationClient(mContext);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initTabs() {
        //分类固定
        tabs = Arrays.asList(getResources().getStringArray(R.array.home_tab_menu));
    }

    @Override
    public void loadTabs(List<CategoryBean> list) {
        for (CategoryBean bean : list) {
            tabs.add(bean.getName());
        }
        //循环注入标签
        for (String tab : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        if (fragments.size() <= 0) {
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                fragments.add(HomeCategoryVideoFragment.newInstance(i, list.get(i).getId(), city_id));
            }
        }
        tabLayout.addOnTabSelectedListener(this);
        homeCategoryAdapter = new HomeCategoryAdapter(getChildFragmentManager(), tabs, fragments);
        viewPager.setAdapter(homeCategoryAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void showCategoryVideo() {
        mDataBinding.homeFragmentTabLl.setVisibility(View.GONE);
        mDataBinding.homeTitleCityTv.setVisibility(View.GONE);
        mDataBinding.homeTitleBackIv.setVisibility(View.VISIBLE);
        viewPager.setScanScroll(false);
        ((HomeCategoryVideoFragment) fragments.get(tabLayout.getSelectedTabPosition())).showVideoCategory();
    }

    @Override
    public void hideCategoryVideo(boolean flg) {
        mDataBinding.homeFragmentTabLl.setVisibility(View.VISIBLE);
        mDataBinding.homeTitleCityTv.setVisibility(View.VISIBLE);
        mDataBinding.homeTitleBackIv.setVisibility(View.GONE);
        viewPager.setScanScroll(true);
        if (flg) {
            ((HomeCategoryVideoFragment) fragments.get(tabLayout.getSelectedTabPosition())).hideVideoCategory();
        }
    }

    @Override
    public void setSelectedTab(int pos) {
//        tabLayout.getTabAt(pos).select();
//        viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
        viewPager.setCurrentItem(pos);
        hideCategoryVideo(false);
    }

    public class CitySelectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            city_id = Integer.valueOf(intent.getStringExtra("code"));
            String city = intent.getStringExtra("name");
            mDataBinding.homeTitleCityTv.setText(city);
            mHelper.setCityId(city_id + "");
            mHelper.setCityName(city);

        }
    }

    private boolean isFront = false;

    @Override
    public void onResume() {
        super.onResume();
        isFront = true;

        // 右上角显示未读消息红点
        int count = EMClient.getInstance().chatManager().getUnreadMessageCount();
        if (count > 0) {
            haveNewMsg();
        } else {
            noNewMsg();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isFront = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReceiver);
    }

    public class CityLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            final String city = location.getCity() == null ? "北京市" : location.getCity();    //获取城市
            mDataBinding.homeTitleCityTv.setText(city);
            PickCityModel model = new PickCityModel();
            model.getCities(new BaseCallback<List<CityEntity>>() {
                @Override
                public void onSuccess(List<CityEntity> data) {
                    //把城市列表放到Application里
                    LoginHelper.getInstance().setCityList(data);
                    for (CityEntity cityEntity : data) {
                        if (city.equals(cityEntity.getName())) {
                            city_id = Integer.parseInt(cityEntity.getId());
                            if (isFront) {
                                mHelper.setCityId(city_id + "");
                                mHelper.setCityName(city);
//                                Intent intent = new Intent();
//                                intent.setAction(Constant.INTENT_FILTER_PICK_CITY);
//                                intent.putExtra("code",city_id);
//                                getActivity().sendBroadcast(intent);
                            }
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    showToast(msg);
                }
            });

        }
    }

    public void haveNewMsg() {
        mDataBinding.homeTitleMsgIv.setImageResource(R.drawable.home_nav_news);
    }

    public void noNewMsg() {
        mDataBinding.homeTitleMsgIv.setImageResource(R.drawable.message);
    }
}
