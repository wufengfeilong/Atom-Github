package com.fcn.park.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseFragment;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.databinding.FragmentHomeBinding;
import com.fcn.park.home.parkinfo.ParkInfoActivity;
import com.fcn.park.info.view.InfoNewsDetailActivity;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalListBean;
import com.fcn.park.rent.bean.RentReleasedHouseListBean;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 860115001 on 2018/04/03.
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeContract.View, HomePresenter>
        implements HomeContract.View, OnBannerListener {

    private ArrayList<Integer> list_path;
    private ArrayList<String> list_title;
    private SimpleBindingAdapter<RentReleasedHouseListBean.ListReleasedHouseBean> mSimpleBindingAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        setTitleText("首页");
        //初始化轮播
        initBanner();
        //加载数据
        mPresenter.loadListData();
        //关于我们
        mHelpView.setVisibility(View.VISIBLE);
        mHelpView.setImageResource(R.drawable.ic_vector_help);
        mHelpView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                actionStartActivity(ParkInfoActivity.class);
            }
        });
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    /**
     * 初始化轮播
     */
    private void initBanner() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        list_path.add(R.drawable.company);
        list_path.add(R.drawable.company);
        list_title.add("好好学习");
        list_title.add("天天向上");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        mDataBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        mDataBinding.banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        mDataBinding.banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mDataBinding.banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        mDataBinding.banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        mDataBinding.banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        mDataBinding.banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        mDataBinding.banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    /**
     * 顶部轮播点击事件
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(),"你点了第"+position+"张轮播图",Toast.LENGTH_SHORT).show();
    }

    /**
     * 绑定公告公示View
     * @param views
     */
    @Override
    public void bindAfficheViews(View... views) {
        for (View view : views) {
            mDataBinding.viewFlipper.addView(view);
        }
    }

    /**
     * 获取公告公示轮播View
     * @param affiche 公告公示
     * @param afficheId 公告公示Id
     * @return
     */
    @Override
    public View getAfficheView(String affiche, final String afficheId) {
        TextView afficheView = new TextView(mContext);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        afficheView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextBlack));
        afficheView.setGravity(Gravity.CENTER_VERTICAL);
        afficheView.setText(affiche);
        afficheView.setLayoutParams(lp);
        afficheView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoNewsDetailActivity.actionStart(mContext, afficheId);
            }
        });
        return afficheView;
    }

    /**
     * Fragment是否隐藏
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mDataBinding.viewFlipper.stopFlipping();
            mDataBinding.banner.stopAutoPlay();
        } else {
            mDataBinding.viewFlipper.startFlipping();
            mDataBinding.banner.startAutoPlay();
        }
    }

    /**
     * 获取停车缴费View
     * @return
     */
    @Override
    public View getParkingFeeView() {
        return mDataBinding.homeParkingFee;
    }

    /**
     * 获取便利服务View
     * @return
     */
    @Override
    public View getServiceView(){
        return mDataBinding.homeService;
    }

    /**
     * 显示强力推荐的广告
     */
    @Override
    public void showTopAdvertisement(List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> list) {
        //获取已缴费的广告
        List<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean> newList = new ArrayList<ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean>();
        if (list != null && list.size() > 0) {
            for (ManagerAdvertisingApprovalListBean.ListAdvertisingApprovalBean bean : list) {
                if (bean.getPayStatus() == 1) {
                    newList.add(bean);
                }
            }
        }

        //随机显示一张广告
        if (newList != null && newList.size() > 0) {
            Random rand = new Random();
            int random = rand.nextInt(newList.size());
            Glide.with(HomeFragment.this)
                    .load(ApiService.IMAGE_BASE + newList.get(random).getAdvertisingImg())
                    .into(mDataBinding.bestAdvertisement);
        }
    }

    /**
     * 初始化RecyclerView
     */
    @Override
    public void initRecyclerView() {
        mDataBinding.recyclerRent.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerRent.addOnItemTouchListener(new HomeFragment.OnRentItemClickListener(mDataBinding.recyclerRent));
        mDataBinding.recyclerRent.setEmptyView(mDataBinding.getRoot().findViewById(R.id.empty_view));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_layout_recommend_rentinfo, BR.houseReleasedListBean);
        mDataBinding.recyclerRent.setAdapter(mSimpleBindingAdapter);
    }

    /**
     * 绑定数据
     * @param beanList
     */
    @Override
    public void bindListData(List<RentReleasedHouseListBean.ListReleasedHouseBean> beanList) {
        setListData(beanList);
        mSimpleBindingAdapter.setupData(beanList);
    }

    /**
     * 获取RecyclerView
     * @return
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerRent;
    }

    /**
     * 轮播加载类
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    /**
     * 最新租赁点击事件
     */
    private class OnRentItemClickListener extends OnRecyclerItemClickListener {

        public OnRentItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onRentItemClick(vh, vh.getLayoutPosition());
        }
    }
}
