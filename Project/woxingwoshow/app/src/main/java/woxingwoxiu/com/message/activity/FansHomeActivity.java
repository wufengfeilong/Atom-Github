package woxingwoxiu.com.message.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityFansHomeBinding;
import woxingwoxiu.com.message.bean.FansBean;
import woxingwoxiu.com.message.contract.FansHomeContract;
import woxingwoxiu.com.message.presenter.FansHomePresenter;

/**
 * 粉丝主页
 */

public class FansHomeActivity extends BaseActivity<ActivityFansHomeBinding, FansHomePresenter> implements FansHomeContract.View {

    FansVideoAdapter fansVideoAdapter;
    List<FansBean> mList =new ArrayList<>();

    @Override
    protected FansHomePresenter createPresenter() {
        return new FansHomePresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mDataBinding.fansVideoList.setLayoutManager(new GridLayoutManager(FansHomeActivity.this, 3));
        mPresenter.loadListData();
        mDataBinding.fansVideoList.setAdapter(fansVideoAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fans_home;
    }

    @Override
    public ImageView getAttention() {
        return mDataBinding.getAttention;
    }

    @Override
    public LinearLayout cancelAttention() {
        return mDataBinding.cancelAttention;
    }

    @Override
    public void bindListData(List<FansBean> beanList) {
        mList.addAll(beanList);
        fansVideoAdapter = new FansVideoAdapter(mList);
        fansVideoAdapter.notifyDataSetChanged();
    }

    @Override
    public void initRecyclerView() {
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.fansVideoList;
    }

    public class FansVideoAdapter extends BaseAdapter<FansBean> {
        public FansVideoAdapter(List<FansBean> list) {
            super(R.layout.fans_video_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, FansBean item) {
            holder.setText(R.id.commentNumber,item.getCommentNumber());
            holder.setImageResource(R.id.coverUrl,R.drawable.temp);
        }
    }
}
