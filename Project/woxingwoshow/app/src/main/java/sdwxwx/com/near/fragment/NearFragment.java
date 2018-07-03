package sdwxwx.com.near.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.NearVideoBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.near.contract.NearFragmentContract;
import sdwxwx.com.near.presenter.NearFragmentPresenter;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.util.MeterUtil;
import sdwxwx.com.util.NetworkUtils;
import sdwxwx.com.util.StringUtil;
import sdwxwx.com.widget.LoadStatusView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NearFragment extends BaseFragment<NearVideoBinding, NearFragmentPresenter>
        implements NearFragmentContract.View, BaseAdapter.OnItemClickListener {
    NearVideoAdapter nearVideoAdapter;
    List<PlayVideoBean> mList = new ArrayList<>();
    double latitude = 40;
    double  longitude = 116;  //获取纬度,精度,默认北京市经纬度
    int page = 1;
    boolean isFresh;
    public LocationClient mLocationClient = null;
    private PositionLocationListener myListener = new PositionLocationListener();
    LoadMoreNearListReceiver mReceiver;
    @Override
    protected int getLayoutRes() {
        return R.layout.near_video;
    }

    @Override
    protected NearFragmentPresenter createPresenter() {
        return new NearFragmentPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        if (!NetworkUtils.isNetworkAvaiable(mContext)) {
            mDataBinding.nearLsv.setViewState(LoadStatusView.VIEW_STATE_ERROR,getString(R.string.common_no_network_msg));
            mDataBinding.nearLsv.setOnStatusPageClickListener(new LoadStatusView.OnStatusPageClickListener() {
                @Override
                public void onError() {
                    if (!NetworkUtils.isNetworkAvaiable(mContext)) {
                        return;
                    }
                    positionLocation();
                    initRecyclerView();
                    mPresenter.loadListData(longitude, latitude, Constant.REQUEST_PAGE);
                    mDataBinding.nearLsv.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                }
            });
            return;
        }
        positionLocation();
        initRecyclerView();

        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.load.near.video.list");
        intentFilter.addAction("com.sdwxwx.thumb.near");
        //注册我的广播
        mReceiver = new LoadMoreNearListReceiver();
        getContext().registerReceiver(mReceiver, intentFilter);
    }


    public class LoadMoreNearListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sdwxwx.load.near.video.list")) {
                onLoadmore();
            } else {
                isFresh = true;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mReceiver);
    }

    private void positionLocation() {
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
                            showToast("定位不可用,请允许使用手机定位！");
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
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onRefresh() {
        mList.clear();
        page = 1;
        mPresenter.loadListData(longitude, latitude, Constant.REQUEST_PAGE);
    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        mPresenter.loadListData(longitude, latitude, page+"");
    }

    private void onLoadCurPageData(){
        mPresenter.loadListData(longitude, latitude, page+"");
    }


    @Override
    public void initRecyclerView() {
        mDataBinding.nearVideoSpringView.setListener(this);
        mDataBinding.nearVideoSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.nearVideoSpringView.setFooter(new DefaultFooter(mContext));
        mDataBinding.nearVideoRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        nearVideoAdapter = new NearVideoAdapter(mList);
        mDataBinding.nearVideoRv.setAdapter(nearVideoAdapter);
        nearVideoAdapter.setOnItemClickListener(this);
    }

    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.addAll(beanList);
        LoginHelper.getInstance().setNearList(mList);
        nearVideoAdapter.notifyDataSetChanged();
        mDataBinding.nearVideoSpringView.onFinishFreshAndLoad();
        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
        getContext().sendBroadcast(intent);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.nearVideoRv;
    }

    @Override
    public void onItemClick(View view, int postion) {
        param2StartActivity(PlayVideoActivity.class, postion + "", 1);
//        showToast(""+postion);
    }

    @Override
    public SpringView getSpringView() {
        return mDataBinding.nearVideoSpringView;
    }

    public class NearVideoAdapter extends BaseAdapter<PlayVideoBean> {
        public NearVideoAdapter(List<PlayVideoBean> list) {
            super(R.layout.near_video_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, PlayVideoBean item) {
            holder.setText(R.id.near_video_item_title, item.getTitle());
            holder.setText(R.id.near_video_item_name, item.getNickname());
            holder.setText(R.id.near_video_item_date, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.near_video_item_count, MeterUtil.numToWan(item.getLike_count()));
            holder.setText(R.id.near_video_item_distance
                    , MeterUtil.getDistance(longitude, latitude
                            , floatToDouble(item.getLongitude())
                            , floatToDouble(item.getLatitude())) + "");
            Glide.with(mContext).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.near_video_item_head));
            Glide.with(mContext).load(item.getCover_url()).into((ImageView) holder.getView(R.id.near_video_item_thumbnail));

            if ("0".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.near_video_item_like, R.drawable.thumb_up_no_selected);
            } else {
                holder.setImageResource(R.id.near_video_item_like, R.drawable.video_liked);
            }
        }
    }

    public double floatToDouble(float f) {
        BigDecimal b = new BigDecimal(String.valueOf(f));
        return b.doubleValue();
    }

    public class PositionLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息

            mPresenter.loadListData(longitude, latitude, Constant.REQUEST_PAGE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFresh) {
            nearVideoAdapter.notifyDataSetChanged();
            isFresh = false;
        }
    }
}
