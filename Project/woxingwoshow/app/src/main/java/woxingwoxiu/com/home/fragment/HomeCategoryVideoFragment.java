package woxingwoxiu.com.home.fragment;

import android.content.Intent;
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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.databinding.FragmentHomeCategoryVideoBinding;
import woxingwoxiu.com.home.bean.BannerBean;
import woxingwoxiu.com.home.bean.PlayVideoBean;
import woxingwoxiu.com.home.contract.HomeCategoryVideoContract;
import woxingwoxiu.com.home.presenter.HomeCategoryVideoPresenter;
import woxingwoxiu.com.play.activity.PlayVideoActivity;
import woxingwoxiu.com.util.GlideImageLoader;

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
    VideoAdapter mVideoAdapter;
    CategoryAdapter mCategoryAdapter;
    List<PlayVideoBean> mList = new ArrayList<>();
    List<Integer> categoryList = new ArrayList<>();
    View categoryView;
    View bannerView;
    LayoutInflater mInflater;
    LinearLayout mVideoCategoryLl;
    RecyclerView mCategoryVideoRv;
    TextView  mCategoryVideoTv;
    ImageView mCategoryVideoIv;
    HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    List<BannerBean> mBannerList = new ArrayList<>();
    Banner mBanner;

    public static HomeCategoryVideoFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        HomeCategoryVideoFragment fragment = new HomeCategoryVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
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
        initBanner();
        initCategory();
        initRecyclerView();
        mPresenter.loadListData("0","138","1");
    }

    private void initCategory() {
        categoryList.add(R.drawable.news);
        categoryList.add(R.drawable.children);
        categoryList.add(R.drawable.spoil);
        categoryList.add(R.drawable.education);
        categoryList.add(R.drawable.work_place);
        categoryList.add(R.drawable.talent);
        categoryList.add(R.drawable.entertainment);
        categoryList.add(R.drawable.tourism);
        categoryList.add(R.drawable.game);
        categoryList.add(R.drawable.sport);
        categoryList.add(R.drawable.health);
        categoryList.add(R.drawable.fine_food);
        categoryList.add(R.drawable.beauty_aisle);
        categoryList.add(R.drawable.wear_show);
        categoryList.add(R.drawable.life_service);

        categoryView = mInflater.inflate(R.layout.home_video_category, null);
        mVideoCategoryLl = categoryView.findViewById(R.id.home_video_category_ll);
        mCategoryVideoTv = categoryView.findViewById(R.id.video_category_tv);
        mCategoryVideoIv = categoryView.findViewById(R.id.video_category_iv);
        mCategoryVideoRv = categoryView.findViewById(R.id.video_category_rv);
        mCategoryAdapter = new CategoryAdapter(categoryList);
        mCategoryVideoRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        mCategoryVideoRv.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                showToast("" + postion);
                hideVideoCategory();
                ((HomeFragment) getParentFragment()).setSelectedTab(postion);
            }
        });
    }

    @Override
    public void initRecyclerView() {
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
//        mDataBinding.categoryVideoRv.setAdapter(mVideoAdapter);
//        mDataBinding.categoryVideoNsv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY > oldScrollY) {
//                    // 向下滑动
//                }
//
//                if (scrollY < oldScrollY) {
//                    // 向上滑动
//                }
//
//                if (scrollY == 0) {
//                    // 顶部
//                }
//
//                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    // 底部
//                    showToast("滑动到底不");
//                    mPresenter.loadListData();
//                }
//            }
//        });

    }

    public void setItems(List<PlayVideoBean> newItems) {

        int startPosition = 0;
        int preSize = mList.size();
        if(preSize > 0) {
            mList.clear();
            mVideoAdapter.notifyItemRangeRemoved(startPosition, preSize);
        }
        mList.addAll(newItems);
        mVideoAdapter.notifyItemRangeChanged(startPosition, newItems.size());
    }

    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.addAll(beanList);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mVideoAdapter.notifyDataSetChanged();
//            }
//        });

        mHeaderAndFooterWrapper.notifyDataSetChanged();
        mDataBinding.categoryVideoSpringView.onFinishFreshAndLoad();
//        mmAdapter.setupData(beanList);
    }

    @Override
    public void onItemClick(View view, int postion) {
        actionStartActivity(PlayVideoActivity.class);
        showToast("" + postion);
    }

    @Override
    public void showVideoCategory() {
        setVideoCategoryHeight(380);
        setViewGroupHeight();
    }

    @Override
    public void hideVideoCategory() {
        setVideoCategoryHeight(0);
    }

    @Override
    public void bindBannerData(List<BannerBean> list) {
        mBannerList = list;
        List<String> titles = new ArrayList<>();
        for (BannerBean bean:list){
            titles.add(bean.getTitle());
        }
        mBanner.setBannerTitles(titles);
        mBanner.setImages(list).setImageLoader(new GlideImageLoader()).start();
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

        lp = mCategoryVideoRv.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = dp2px(280);
        mCategoryVideoRv.setLayoutParams(lp);

        lp = mCategoryVideoTv.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = dp2px(30);
        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(mCategoryVideoTv.getLayoutParams());
        //设置它的上下左右的margin：4个参数按顺序分别是左上右下
        lllp.setMargins(dp2px(10),dp2px(10),dp2px(10),dp2px(10));
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
            holder.setText(R.id.home_video_item_date, item.getCreate_time());
            holder.setText(R.id.home_video_item_count, item.getLike_count()+"");
            Glide.with(mContext).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.home_video_item_head));
//            Glide.with(mContext).load(item.getCover_url()).into((ImageView) holder.getView(R.id.home_video_item_thumbnail));
            Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527683325503&di=9cd50e1a205efa2bd970ecbac5b1241a&imgtype=0&src=http%3A%2F%2Fimg.25pp.com%2Fuploadfile%2Fapp%2Fcapture%2Fiphone6p%2F20180423%2F1524468231826184.jpg").into((ImageView) holder.getView(R.id.home_video_item_thumbnail));

//            holder.setImageResource(R.id.home_video_item_head, item.getAvatar_url());
//            holder.setImageResource(R.id.home_video_item_thumbnail, R.drawable.temp);
        }
    }

    public class CategoryAdapter extends BaseAdapter<Integer> {

        public CategoryAdapter(List<Integer> list) {
            super(R.layout.home_video_category_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, Integer item) {
            holder.setImageResource(R.id.home_video_item_category, item);
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
                Uri uri = Uri.parse(mBannerList.get(position).getTarget_url());
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
        mPresenter.loadListData();
    }

    @Override
    public void onLoadmore() {
        mPresenter.loadListData();
    }


}
