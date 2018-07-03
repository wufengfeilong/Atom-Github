package sdwxwx.com.me.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.databinding.ActivityFindFriendsBinding;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.me.adapter.FindFriendsAdapter;
import sdwxwx.com.me.contract.FindFriendsContract;
import sdwxwx.com.me.presenter.FindFriendsPresenter;
import sdwxwx.com.message.activity.FansHomeActivity;

public class FindFriendsActivity extends BaseActivity<ActivityFindFriendsBinding,FindFriendsPresenter> implements FindFriendsContract.View, BaseAdapter.OnItemClickListener {

    /** 定义适配器 */
    FindFriendsAdapter adapter = null;
    /** 推荐列表 */
    List<RecommendUserBean> recommendList = new ArrayList<RecommendUserBean>();
    @Override
    protected FindFriendsPresenter createPresenter() {
        return new FindFriendsPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        mPresenter.loadListData();
    }

    @Override
    public void bindListData(List<RecommendUserBean> beanList) {
        recommendList.addAll(beanList);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initRecyclerView() {
        mDataBinding.recommendList.setLayoutManager(new LinearLayoutManager(FindFriendsActivity.this));
        adapter= new FindFriendsAdapter(recommendList, getContext());
        adapter.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        mDataBinding.recommendList.setAdapter(adapter);
    }

    // 定义元素的点击事件
    @Override
    public void onItemClick(View view, int postion) {
        // 跳转到好友资料画面
        paramStartActivity(FansHomeActivity.class, String.valueOf(recommendList.get(postion).getId()));
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recommendList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_friends;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasContactPermission()) {
            mDataBinding.phoneBookIcon.setImageResource(R.drawable.phone_book_yellow);
            mDataBinding.phoneBookBindTv.setText(getString(R.string.has_bind));
        } else {
            mDataBinding.phoneBookIcon.setImageResource(R.drawable.phone_book_gray);
            mDataBinding.phoneBookBindTv.setText(getString(R.string.no_bind));
        }
    }

    private boolean hasContactPermission(){
        int perm = this.checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
