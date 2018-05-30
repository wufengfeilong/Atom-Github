package woxingwoxiu.com.near.fragment;

import android.Manifest;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.databinding.NearVideoBinding;
import woxingwoxiu.com.home.bean.CategoryVideoListBean;
import woxingwoxiu.com.near.contract.NearFragmentContract;
import woxingwoxiu.com.near.presenter.NearFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class NearFragment extends BaseFragment<NearVideoBinding,NearFragmentPresenter>
        implements NearFragmentContract.View, BaseAdapter.OnItemClickListener  {
    NearVideoAdapter nearVideoAdapter;
    List<CategoryVideoListBean> mList = new ArrayList<>();
    double latitude,longitude ;  //获取纬度,精度

    public LocationClient mLocationClient = null;
    private PositionLocationListener myListener = new PositionLocationListener();

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
        positionLocation();
        initRecyclerView();
        mPresenter.loadListData();
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
                        if(value){
                            // 开始定位
                            startLocation();
                        }else {
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
        mPresenter.loadListData();
    }

    @Override
    public void onLoadmore() {
        mPresenter.loadListData();
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
    public void bindListData(List<CategoryVideoListBean> beanList) {
        mList.addAll(beanList);
        nearVideoAdapter.notifyDataSetChanged();
        mDataBinding.nearVideoSpringView.onFinishFreshAndLoad();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.nearVideoRv;
    }

    @Override
    public void onItemClick(View view, int postion) {
        showToast(""+postion);
    }

    public class NearVideoAdapter extends BaseAdapter<CategoryVideoListBean> {
        public NearVideoAdapter(List<CategoryVideoListBean> list) {
            super(R.layout.near_video_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, CategoryVideoListBean item) {
            holder.setText(R.id.near_video_item_title, item.getTitle());
            holder.setText(R.id.near_video_item_name, item.getNickName());
            holder.setText(R.id.near_video_item_date, item.getCreateTime());
            holder.setText(R.id.near_video_item_count, item.getTunmbupCount());
            holder.setText(R.id.near_video_item_distance, item.getDistance());
            holder.setImageResource(R.id.near_video_item_head, R.drawable.temp);
            holder.setImageResource(R.id.near_video_item_thumbnail, R.drawable.temp);
        }
    }

    public class PositionLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            latitude = location.getLatitude();    //获取纬度信息
            longitude = location.getLongitude();    //获取经度信息
            //TODO 查询数据

        }
    }
}
