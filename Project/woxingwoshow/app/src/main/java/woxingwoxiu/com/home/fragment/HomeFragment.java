package woxingwoxiu.com.home.fragment;


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
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.databinding.FragmentHomeBinding;
import woxingwoxiu.com.home.adapter.HomeCategoryAdapter;
import woxingwoxiu.com.home.bean.CityEntity;
import woxingwoxiu.com.home.contract.HomeFragmentContract;
import woxingwoxiu.com.home.model.PickCityModel;
import woxingwoxiu.com.home.presenter.HomeFragmentPresenter;
import woxingwoxiu.com.widget.ScrollViewPager;
import woxingwoxiu.com.widget.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentPresenter>
        implements HomeFragmentContract.View{
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private HomeCategoryAdapter homeCategoryAdapter;

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

        tabLayout = mDataBinding.homeFragmentTl;
        viewPager = mDataBinding.homeFragmentVp;

        initTabs();
        //循环注入标签
        for (String tab : tabs) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        if (fragments.size() <= 0) {
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                fragments.add(HomeCategoryVideoFragment.newInstance(i));
            }
        }
        tabLayout.addOnTabSelectedListener(this);
        viewPager.setOffscreenPageLimit(-1);//设置ViewPager的缓存界面数,默认缓存为2
        homeCategoryAdapter = new HomeCategoryAdapter(getChildFragmentManager(), tabs, fragments);
        viewPager.setAdapter(homeCategoryAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //城市选择广播
        mReceiver = new CitySelectReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.INTENT_FILTER_SELECT_CITY);
        mContext.registerReceiver(mReceiver,filter);
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
                        if(value){
                            // 开始定位
                            startLocation();
                        }else {
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

    private void startLocation(){
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void initTabs() {
        //后台加载
//        tabs = loadTabs();
        //分类固定
        tabs = Arrays.asList(getResources().getStringArray(R.array.home_tab_menu));
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

    public class CitySelectReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            city_id = intent.getIntExtra("code",138);
            mDataBinding.homeTitleCityTv.setText(intent.getStringExtra("name"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext.unregisterReceiver(mReceiver);
    }

    public class CityLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            final String city = location.getCity();    //获取城市
            mDataBinding.homeTitleCityTv.setText(city);
            PickCityModel model =new PickCityModel();
            model.getCitys(new BaseCallback<List<CityEntity>>() {
                @Override
                public void onSuccess(List<CityEntity> data) {
                    for(CityEntity cityEntity:data){
                        if (city.equals(cityEntity.getName())) {
                            city_id = cityEntity.getId();
                            //TODO 查询数据
                        }
                    }
                }

                @Override
                public void onFail(String msg) {

                }
            });

        }
    }
}
