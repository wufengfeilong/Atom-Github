package sdwxwx.com.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.FragmentHomeCategoryVideoBinding;
import sdwxwx.com.home.bean.BannerBean;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.contract.HomeCategoryVideoContract;
import sdwxwx.com.home.presenter.HomeCategoryVideoPresenter;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.util.GlideImageLoader;
import sdwxwx.com.util.MeterUtil;
import sdwxwx.com.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      14:29
 */
public class HomeCategoryVideoFragment extends BaseFragment<FragmentHomeCategoryVideoBinding, HomeCategoryVideoPresenter>
        implements HomeCategoryVideoContract.View, BaseAdapter.OnItemClickListener {
    int type;
    String categoryId;
    int cityId;
    int page = 1;
    boolean isFirstIn = true;
    VideoAdapter mVideoAdapter;
    CategoryAdapter mCategoryAdapter;
    List<PlayVideoBean> mList = new ArrayList<>();
    List<CategoryBean> categoryList;
    View categoryView;
    View bannerView;
    LayoutInflater mInflater;
    LinearLayout mVideoCategoryLl;
    RecyclerView mCategoryVideoRv;
    TextView mCategoryVideoTv;
    ImageView mCategoryVideoIv;
    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    List<BannerBean> mBannerList = new ArrayList<>();
    Banner mBanner;
    LoginHelper mHelper;
    boolean isVisble;
    LoadMorePlayListReceiver mReceiver;
    boolean isFresh;
    public static HomeCategoryVideoFragment newInstance(int type, String category_id, int city_id) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("category_id", category_id);
        args.putInt("city_id", city_id);
        HomeCategoryVideoFragment fragment = new HomeCategoryVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            categoryId = getArguments().getString("category_id");
            cityId = getArguments().getInt("city_id");
        }
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home_category_video;
    }

    @Override
    protected HomeCategoryVideoPresenter createPresenter() {
        return new HomeCategoryVideoPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mInflater = LayoutInflater.from(mContext);
        mHelper = LoginHelper.getInstance();
        initBanner();
        mPresenter.loadCategoryData();
        initRecyclerView();

        if (isFirstIn) {
            mPresenter.loadListData(categoryId, Constant.REQUEST_PAGE);
            isFirstIn = false;
        }
        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.load.play.video.list");
        intentFilter.addAction("com.sdwxwx.thumb.play");

        //注册我的广播
        mReceiver = new LoadMorePlayListReceiver();
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    public class LoadMorePlayListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sdwxwx.load.play.video.list")) {
                onLoadMore(intent.getIntExtra("load_type", -1));
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

    @Override
    public void initRecyclerView() {

        categoryView = mInflater.inflate(R.layout.home_video_category, null);
        mVideoCategoryLl = categoryView.findViewById(R.id.home_video_category_ll);
        mCategoryVideoTv = categoryView.findViewById(R.id.video_category_tv);
        mCategoryVideoIv = categoryView.findViewById(R.id.video_category_iv);
        mCategoryVideoRv = categoryView.findViewById(R.id.video_category_rv);


        mDataBinding.categoryVideoSpringView.setListener(this);
        mDataBinding.categoryVideoSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.categoryVideoSpringView.setFooter(new DefaultFooter(mContext));
        mDataBinding.categoryVideoRv.setLayoutManager(new GridLayoutManager(mContext, 2));
//        mDataBinding.categoryVideoRv.setRefrshView(mDataBinding.categoryVideoSpringView);
//        mDataBinding.categoryVideoRv.setEmptyView(mDataBinding.categoryVideoEmptyView);
//        mmAdapter  = new SimpleBindingAdapter<>(R.layout.home_video_item, BR.categoryVideoListBean);
//        mDataBinding.categoryVideoRv.setAdapter(mmAdapter);
        mVideoAdapter = new VideoAdapter(mList);
//        Banner banner = new Banner(mContext);
//        initBanner(banner);
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mVideoAdapter);
        mHeaderAndFooterWrapper.addHeaderView(bannerView);
        mHeaderAndFooterWrapper.addHeaderView(categoryView);
        mDataBinding.categoryVideoRv.setAdapter(mHeaderAndFooterWrapper);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        mVideoAdapter.setOnItemClickListener(this);


    }

    public void setItems(List<PlayVideoBean> newItems) {

        int startPosition = 0;
        int preSize = mList.size();
        if (preSize > 0) {
            mList.clear();
            mVideoAdapter.notifyItemRangeRemoved(startPosition, preSize);
        }
        mList.addAll(newItems);
        mVideoAdapter.notifyItemRangeChanged(startPosition, newItems.size());
    }

    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.addAll(beanList);
        if (isVisble) {
            if (mList.size() <= 0) {
                showToast("此栏目下还没有视频。");
            }
            mHelper.setPlayVideoList(mList);
        }
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        mDataBinding.categoryVideoSpringView.onFinishFreshAndLoad();
        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
        getContext().sendBroadcast(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisble = true;
            if (mHelper != null) {
                mHelper.setPlayVideoList(mList);
            }
        } else {
            isVisble = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFresh) {
            mHeaderAndFooterWrapper.notifyDataSetChanged();
            isFresh = false;
        }
    }

    @Override
    public void onItemClick(View view, int postion) {
        Intent i = new Intent(mContext, PlayVideoActivity.class);
        i.putExtra(Constant.INTENT_PARAM, (postion - 2) + "");
        i.putExtra(Constant.INTENT_PARAM_ONE, 0);
        i.putExtra(Constant.INTENT_PARAM_TWO, type);
        startActivity(i);
    }

    @Override
    public void bindCategoryData(List<CategoryBean> list) {
        categoryList = new ArrayList<>();
        categoryList.addAll(list);
        mCategoryAdapter = new CategoryAdapter(categoryList);
        mCategoryVideoRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        mCategoryVideoRv.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
//                showToast("" + postion);
                hideVideoCategory();
                ((HomeFragment) getParentFragment()).setSelectedTab(postion);
            }
        });
    }

    @Override
    public void showVideoCategory() {
        int size = categoryList.size();
        setVideoCategoryHeight(100 + (size % 4 == 0 ? size / 4 : size / 4 + 1) * 70);
        setViewGroupHeight();
    }

    @Override
    public void hideVideoCategory() {
        setVideoCategoryHeight(0);
    }

    @Override
    public void bindBannerData(List<BannerBean> list) {
        ViewGroup.LayoutParams lp;
        lp = bannerView.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        if (list.size() <= 0) {
            lp.height = 1;
            bannerView.setLayoutParams(lp);
            return;
        } else {
            lp.height = dp2px(150);
            bannerView.setLayoutParams(lp);
        }
        mBannerList = list;
        List<String> titles = new ArrayList<>();
        for (BannerBean bean : list) {
            titles.add(bean.getTitle());
        }
        mBanner.setBannerTitles(titles);
        mBanner.setImages(list).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    public SpringView getSpringView() {
        return mDataBinding.categoryVideoSpringView;
    }

    private void setVideoCategoryHeight(int height) {
        ViewGroup.LayoutParams lp;
        lp = mVideoCategoryLl.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = dp2px(height);
        mVideoCategoryLl.setLayoutParams(lp);
    }

    private void setViewGroupHeight() {
        ViewGroup.LayoutParams lp;

        lp = mCategoryVideoIv.getLayoutParams();
        lp.width = dp2px(100);
        lp.height = dp2px(50);
        mCategoryVideoIv.setLayoutParams(lp);
        int size = categoryList.size();
        lp = mCategoryVideoRv.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = dp2px((size % 4 == 0 ? size / 4 : size / 4 + 1) * 70);
        mCategoryVideoRv.setLayoutParams(lp);

        lp = mCategoryVideoTv.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = dp2px(30);
        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(mCategoryVideoTv.getLayoutParams());
        //设置它的上下左右的margin：4个参数按顺序分别是左上右下
        lllp.setMargins(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
        mCategoryVideoTv.setLayoutParams(lllp);
    }

    public class VideoAdapter extends BaseAdapter<PlayVideoBean> {
        public VideoAdapter(List<PlayVideoBean> list) {
            super(R.layout.home_video_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, PlayVideoBean item) {
            holder.setText(R.id.home_video_item_title, item.getTitle());
            holder.setText(R.id.home_video_item_name, item.getNickname());
            holder.setText(R.id.home_video_item_date, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.home_video_item_count, MeterUtil.numToWan(item.getLike_count()));
            if ("0".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.home_video_item_like, R.drawable.thumb_up_no_selected);
            } else {
                holder.setImageResource(R.id.home_video_item_like, R.drawable.video_liked);
            }
            Glide.with(mContext).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.home_video_item_head));
            Glide.with(mContext).load(item.getCover_url()).into((ImageView) holder.getView(R.id.home_video_item_thumbnail));

//            holder.setImageResource(R.id.home_video_item_head, item.getAvatar_url());
//            holder.setImageResource(R.id.home_video_item_thumbnail, R.drawable.temp);
        }
    }

    public class CategoryAdapter extends BaseAdapter<CategoryBean> {

        public CategoryAdapter(List<CategoryBean> list) {
            super(R.layout.home_video_category_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, CategoryBean item) {
            Glide.with(mContext).load(item.getIcon_url()).into((ImageView) holder.getView(R.id.home_video_item_category));
            holder.setText(R.id.home_video_item_tv, item.getName());
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.categoryVideoRv;
    }


    private void initBanner() {
        bannerView = mInflater.inflate(R.layout.home_fragment_banner, null);
        mBanner = bannerView.findViewById(R.id.category_video_banner);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String url = mBannerList.get(position).getTarget_url();
                if (url == null || url == "") {
                    return;
                }
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mPresenter.loadBannerData();
    }


    /**
     * dp转换成px
     */
    private int dp2px(float dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onRefresh() {
//        setItems(mList);
        mList.clear();
        page = 1;
        mPresenter.loadListData(categoryId, Constant.REQUEST_PAGE);
    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        mPresenter.loadListData(categoryId, page + "");
    }

    public void onLoadMore(int load_type) {
        if (load_type == type) {
            page = page + 1;
            mPresenter.loadListData(categoryId, page + "");
        }
    }
}
