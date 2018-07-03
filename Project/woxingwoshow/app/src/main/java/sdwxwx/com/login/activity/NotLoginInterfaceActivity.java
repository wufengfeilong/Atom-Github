package sdwxwx.com.login.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityNotLoginInterfaceBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.contract.NotLoginInterfaceContract;
import sdwxwx.com.login.presenter.NotLoginInterfacePresenter;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.util.NetworkUtils;
import sdwxwx.com.util.StringUtil;
import sdwxwx.com.widget.LoadStatusView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丁胜胜 on 2018/05/11.
 * 类描述：未登陆界面
 */
public class NotLoginInterfaceActivity extends BaseActivity<ActivityNotLoginInterfaceBinding,NotLoginInterfacePresenter>
        implements NotLoginInterfaceContract.View, BaseAdapter.OnItemClickListener {

    int type;
    int categoryId;
    int cityId;
    int page = 1;
    boolean isFirstIn = true;
    boolean isCityChanged = false;
    NearVideoAdapter mVideoAdapter;
    List<PlayVideoBean> mList = new ArrayList<>();
    LoginHelper mHelper;
    LoadMoreNotLoginListReceiver mReceiver;
    boolean isFresh;
    @Override
    protected NotLoginInterfacePresenter createPresenter() {
        return new NotLoginInterfacePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_login_interface;
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mDataBinding.setPresenter(mPresenter);

        if (!NetworkUtils.isNetworkAvaiable(getContext())) {
            mDataBinding.notLoginLsv.setViewState(LoadStatusView.VIEW_STATE_ERROR,getString(R.string.common_no_network_msg));
            mDataBinding.notLoginLsv.setOnStatusPageClickListener(new LoadStatusView.OnStatusPageClickListener() {
                @Override
                public void onError() {
                    if (!NetworkUtils.isNetworkAvaiable(getContext())) {
                        return;
                    }
                    mHelper = LoginHelper.getInstance();
                    initRecyclerView();
                    if (isFirstIn) {
                        mPresenter.loadListData( Constant.REQUEST_PAGE);
                        isFirstIn = false;
                    }
                    mDataBinding.notLoginLsv.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                }
            });
            return;
        }

        mHelper = LoginHelper.getInstance();
        initRecyclerView();
        if (isFirstIn) {
            mPresenter.loadListData( Constant.REQUEST_PAGE);
            isFirstIn = false;
        }

        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.load.play.video.list");
        intentFilter.addAction("com.sdwxwx.thumb.play");
        //注册我的广播
        mReceiver = new LoadMoreNotLoginListReceiver();
        registerReceiver(mReceiver, intentFilter);

        ActivityCollector.finishAll();
        ActivityCollector.addActivity(this);//将活动添加到活动收集器
    }

    @Override
    public SpringView getSpringView() {
        return mDataBinding.notLoginVideoSpringView;
    }

    public class LoadMoreNotLoginListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sdwxwx.load.play.video.list")) {
                onLoadmore();
            } else {
                isFresh = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFresh) {
            mVideoAdapter.notifyDataSetChanged();
            isFresh = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onRefresh() {
        mList.clear();
        page = 1;
        mPresenter.loadListData(Constant.REQUEST_PAGE);
    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        mPresenter.loadListData(page+"");
    }
    public void onLoadCurPageData() {
        mPresenter.loadListData(page+"");
    }


    @Override
    public void initRecyclerView() {
        mDataBinding.notLoginVideoSpringView.setListener(this);
        mDataBinding.notLoginVideoSpringView.setHeader(new DefaultHeader(this));
        mDataBinding.notLoginVideoSpringView.setFooter(new DefaultFooter(this));

        mDataBinding.notLoginVideoRv.setLayoutManager(new GridLayoutManager(this, 2));
        mVideoAdapter = new NearVideoAdapter(mList);
        mDataBinding.notLoginVideoRv.setAdapter(mVideoAdapter);
        mVideoAdapter.setOnItemClickListener(this);
    }


    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.addAll(beanList);
        mHelper.setPlayVideoList(mList);
        mVideoAdapter.notifyDataSetChanged();
        mDataBinding.notLoginVideoSpringView.onFinishFreshAndLoad();
        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
        sendBroadcast(intent);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.notLoginVideoRv;
    }

    @Override
    public void onItemClick(View view, int postion) {
//        param2StartActivity(PlayVideoActivity.class, (postion - 2) + "",0);
        param2StartActivity(PlayVideoActivity.class,postion+"",0);
    }

    public class NearVideoAdapter extends BaseAdapter<PlayVideoBean> {
        public NearVideoAdapter(List<PlayVideoBean> list) {
            super(R.layout.not_login_vedio_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, PlayVideoBean item) {
            holder.setText(R.id.not_login_video_item_title, item.getTitle());
            holder.setText(R.id.not_login_video_item_name, item.getNickname());
            holder.setText(R.id.not_login_video_item_date, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.not_login_video_item_count, item.getLike_count() + "");
            Glide.with(getContext()).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.not_login_home_video_item_head));
            Glide.with(getContext()).load(item.getCover_url()).into((ImageView) holder.getView(R.id.not_login_home_video_item_thumbnail));
        }
    }

//    public class VideoAdapter extends BaseAdapter<PlayVideoBean> {
//        public VideoAdapter(List<PlayVideoBean> list) {
//            super(R.layout.home_video_item, list);
//        }
//
//        @Override
//        protected void convert(BaseHolder holder, PlayVideoBean item) {
//            holder.setText(R.id.home_video_item_title, item.getTitle());
//            holder.setText(R.id.home_video_item_name, item.getNickname());
//            holder.setText(R.id.home_video_item_date, item.getCreate_time());
//            holder.setText(R.id.home_video_item_count, item.getLike_count() + "");
////            Glide.with(getContext()).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.home_video_item_head));
////            Glide.with(getContext()).load(item.getCover_url()).into((ImageView) holder.getView(R.id.home_video_item_thumbnail));
//
//        }
//    }

}
