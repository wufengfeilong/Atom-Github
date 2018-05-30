package woxingwoxiu.com.login.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.adapter.BaseHolder;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityNotLoginInterfaceBinding;
import woxingwoxiu.com.login.bean.NotLoginInterfaceBean;
import woxingwoxiu.com.login.contract.NotLoginInterfaceContract;
import woxingwoxiu.com.login.presenter.NotLoginInterfacePresenter;

/**
 * Created by 丁胜胜 on 2018/05/11.
 * 类描述：未登陆界面
 */
public class NotLoginInterfaceActivity extends BaseActivity<ActivityNotLoginInterfaceBinding,NotLoginInterfacePresenter>
            implements NotLoginInterfaceContract.View, BaseAdapter.OnItemClickListener {

    NearVideoAdapter nearVideoAdapter;
    List<NotLoginInterfaceBean> mList = new ArrayList<>();

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
        initRecyclerView();
        mPresenter.loadListData();
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
        mDataBinding.notLoginVideoSpringView.setListener(this);
        mDataBinding.notLoginVideoSpringView.setHeader(new DefaultHeader(this));
        mDataBinding.notLoginVideoSpringView.setFooter(new DefaultFooter(this));

        mDataBinding.notLoginVideoRv.setLayoutManager(new GridLayoutManager(this, 2));
        nearVideoAdapter = new NearVideoAdapter(mList);
        mDataBinding.notLoginVideoRv.setAdapter(nearVideoAdapter);
        nearVideoAdapter.setOnItemClickListener(this);
    }

    @Override
    public void bindListData(List<NotLoginInterfaceBean> beanList) {
        mList.addAll(beanList);
        nearVideoAdapter.notifyDataSetChanged();
        mDataBinding.notLoginVideoSpringView.onFinishFreshAndLoad();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.notLoginVideoRv;
    }

    @Override
    public void onItemClick(View view, int postion) {
        showToast(""+postion);
    }

    public class NearVideoAdapter extends BaseAdapter<NotLoginInterfaceBean> {
        public NearVideoAdapter(List<NotLoginInterfaceBean> list) {
            super(R.layout.not_login_vedio_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, NotLoginInterfaceBean item) {
            holder.setText(R.id.not_login_video_item_title, item.getTitle());
            holder.setText(R.id.not_login_video_item_name, item.getNickName());
            holder.setText(R.id.not_login_video_item_date, item.getCreateTime());
            holder.setText(R.id.not_login_video_item_count, item.getTunmbupCount());
            holder.setText(R.id.not_login_video_item_distance, item.getDistance());
            holder.setImageResource(R.id.not_login_video_item_head, R.drawable.temp);
            holder.setImageResource(R.id.not_login_video_item_thumbnail, R.drawable.temp);
        }
    }


}
