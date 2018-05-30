package woxingwoxiu.com.home.activity;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityTopicVideoListBinding;
import woxingwoxiu.com.home.bean.TopicVideoBean;
import woxingwoxiu.com.home.contract.TopicVideoListContract;
import woxingwoxiu.com.home.presenter.TopicVideoListPresenter;

/**
 *话题视频列表
 */


public class TopicVideoListActivity extends BaseActivity<ActivityTopicVideoListBinding,TopicVideoListPresenter> implements TopicVideoListContract.View {
    List<TopicVideoBean> mList= new ArrayList<>();
    TopicVideoAdapter topicVideoAdapter;

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mDataBinding.topicVideoList.setLayoutManager(new GridLayoutManager(TopicVideoListActivity.this, 2));
        mPresenter.loadListData();
        mDataBinding.topicVideoList.setAdapter(topicVideoAdapter);
    }

    @Override
    public void bindListData(List<TopicVideoBean> beanList) {
        mList.addAll(beanList);
        topicVideoAdapter =new TopicVideoAdapter(mList);
        topicVideoAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_video_list;
    }

    @Override
    protected TopicVideoListPresenter createPresenter() {
        return new TopicVideoListPresenter();
    }

    @Override
    public void finishThis() {
        finish();
    }

    @Override
    public void initRecyclerView() {
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.topicVideoList;
    }

    public class TopicVideoAdapter extends BaseAdapter<TopicVideoBean>{
        public TopicVideoAdapter(List<TopicVideoBean> list) {
            super(R.layout.topic_video_list_item,list);
        }
        @Override
        protected void convert(BaseHolder holder, TopicVideoBean item) {
            super.convert(holder, item);
            holder.setText(R.id.topic_video_title,item.getTitle());
            holder.setText(R.id.topic_video_user_name,item.getNickname());
            holder.setImageResource(R.id.topic_video_cover,R.drawable.temp);
        }
    }
}
