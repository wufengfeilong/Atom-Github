package sdwxwx.com.home.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import sdwxwx.com.databinding.ActivityTopicVideoListBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.contract.TopicVideoListContract;
import sdwxwx.com.home.presenter.TopicVideoListPresenter;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.play.activity.PlayVideoActivity;
import sdwxwx.com.release.utils.ViewUtils;
import sdwxwx.com.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 话题视频列表
 */


public class TopicVideoListActivity extends BaseActivity<ActivityTopicVideoListBinding, TopicVideoListPresenter> implements TopicVideoListContract.View, BaseAdapter.OnItemClickListener {
    List<PlayVideoBean> mList = new ArrayList<>();
    TopicVideoAdapter topicVideoAdapter;
    int page = 1;
    LoadMoreTopicListReceiver mReceiver;
    boolean isFresh;
    @Override
    protected void initViews() {
        ViewUtils.setImgTransparent(this);
        mDataBinding.setPresenter(mPresenter);
        // 获取话题名称
        String topic_name = getIntent().getStringExtra(Constant.INTENT_PARAM);
        if (!TextUtils.isEmpty(topic_name)) {
            mDataBinding.txtCatagoryVideo.setText(topic_name);
        }
        mDataBinding.topicVideoSpringView.setListener(this);
        mDataBinding.topicVideoSpringView.setHeader(new DefaultHeader(this));
        mDataBinding.topicVideoSpringView.setFooter(new DefaultFooter(this));
        mDataBinding.topicVideoList.setLayoutManager(new GridLayoutManager(TopicVideoListActivity.this, 2));
        topicVideoAdapter = new TopicVideoAdapter(mList);
        topicVideoAdapter.setOnItemClickListener(this);
        mDataBinding.topicVideoList.setAdapter(topicVideoAdapter);
        mPresenter.loadListData(Constant.REQUEST_PAGE);

        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.load.topic.video.list");
        intentFilter.addAction("com.sdwxwx.thumb.topic");
        //注册我的广播
        mReceiver = new LoadMoreTopicListReceiver();
        registerReceiver(mReceiver, intentFilter);

    }

    public class LoadMoreTopicListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sdwxwx.load.topic.video.list")) {
                onLoadmore();
            } else {
                isFresh = true;

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.addAll(beanList);
        LoginHelper.getInstance().setTopicList(mList);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                topicVideoAdapter.notifyDataSetChanged();
            }
        });
        mDataBinding.topicVideoSpringView.onFinishFreshAndLoad();
        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
        sendBroadcast(intent);
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
    public SpringView getSpringView() {
        return mDataBinding.topicVideoSpringView;
    }

    @Override
    public void initRecyclerView() {
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.topicVideoList;
    }

    @Override
    public String getTopicId() {
        /*获取传过来的话题编号*/
        int topic_id = getIntent().getIntExtra(Constant.INTENT_PARAM_ONE,0);
        String topic = String.valueOf(topic_id) ;
        return topic;
    }

    @Override
    public void onItemClick(View view, int postion) {
        param2StartActivity(PlayVideoActivity.class, postion + "", 2);
    }

    @Override
    public void onRefresh() {
        mList.clear();
        page = 1;
        mPresenter.loadListData(Constant.REQUEST_PAGE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFresh) {
            topicVideoAdapter.notifyDataSetChanged();
            isFresh = false;
        }
    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        mPresenter.loadListData(page+"");

    }

    public class TopicVideoAdapter extends BaseAdapter<PlayVideoBean> {
        public TopicVideoAdapter(List<PlayVideoBean> list) {
            super(R.layout.topic_video_list_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, PlayVideoBean item) {
            super.convert(holder, item);
            holder.setText(R.id.topic_video_title, item.getTitle());
            holder.setText(R.id.topic_video_user_name, item.getNickname());
            holder.setText(R.id.topic_video_date, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.topic_video_up_count, String.valueOf(item.getLike_count()));
            Glide.with(TopicVideoListActivity.this).load(item.getCover_url()).into((ImageView) holder.getView(R.id.topic_video_cover));
            Glide.with(TopicVideoListActivity.this).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.topic_video_head));
            if ("0".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.topic_video_item_like, R.drawable.thumb_up_no_selected);
            } else {
                holder.setImageResource(R.id.topic_video_item_like, R.drawable.video_liked);
            }

        }
    }
}
