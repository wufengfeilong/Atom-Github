package sdwxwx.com.release.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityFriendsListBinding;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.release.contract.FriendsListContract;
import sdwxwx.com.release.presenter.FriendsListPresenter;

public class FriendsListActivity extends BaseActivity<ActivityFriendsListBinding, FriendsListPresenter>
        implements FriendsListContract.View {
    private final String TAG = "FriendsListActivity";
    private final int HEADER = 1;
    // 数据源
    private List<SearchUserBean.HaveUserBean> mFriendListData = new ArrayList<>();
    // 记录请求批次
    private String mPageFlag = Constant.REQUEST_PAGE;

    private FriendAdapter mListAdapter;

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);

        // 初始化搜索编辑框领域
        initEditView();

        mListAdapter = new FriendAdapter(R.layout.search_friend_item, mFriendListData);
        mDataBinding.friendsList.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.friendsList.setAdapter(mListAdapter);

        mListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                // 将选择的好友ID数据返回回去
                intent.putExtra(Constant.CHOOSE_FRIEND,
                        (Serializable) mFriendListData.get(position - 1));
                setResult(-1, intent);
                closeActivity();
            }
        });
        // 显示所有的好友
        mPresenter.getFriendList(mDataBinding.friendSearchEdit.getText().toString(), mPageFlag);
    }

    private class FriendAdapter extends BaseAdapter<SearchUserBean.HaveUserBean> {

        public FriendAdapter(int layoutId, List<SearchUserBean.HaveUserBean> list) {
            super(layoutId, list);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEADER;
            } else {
                return super.getItemViewType(position);
            }
        }

        @Override
        public int getItemCount() {
            int count = super.getItemCount();
            if (count != 0) {
                count += 1;
            }
            return count;
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {
            if (HEADER == holder.getItemViewType()) {
                holder.setText(R.id.tv_header, getString(R.string.release_friend_search_follow));
            } else {
                super.onBindViewHolder(holder, position - 1);
            }
        }

        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == HEADER) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_header, parent, false);
                return new BaseHolder(view, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        // nothing to do.
                    }
                });
            } else {
                return super.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        protected void convert(BaseHolder holder, SearchUserBean.HaveUserBean item) {
            holder.setText(R.id.user_name_tx, item.getNickname());
            // 用户头像
            RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
            Glide.with(getContext())
                    .load(item.getAvatar_url())
                    .apply(options)
                    .into((ImageView) holder.getView(R.id.user_icon_img));
        }
    }

    @Override
    protected FriendsListPresenter createPresenter() {
        return new FriendsListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_list;
    }

    /**
     * 初始化搜索编辑框领域
     */
    private void initEditView() {
        Log.d(TAG, "initEditView start");

        mDataBinding.friendSearchEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mDataBinding.friendSearchEdit.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > mDataBinding.friendSearchEdit.getWidth()
                        - mDataBinding.friendSearchEdit.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    Log.d(TAG, "Edit onTouch 点击位置 右侧按钮");
                    // 清空输入内容
                    mDataBinding.friendSearchEdit.setText("");
                }
                return false;

            }
        });

        // 设置输入内容的监听事件
        mDataBinding.friendSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "Edit afterTextChanged 输入内容变化");

                if (TextUtils.isEmpty(editable)) {
                    Log.d(TAG, "Edit afterTextChanged 输入内容变化 editable = 空");
                } else {
                    Log.d(TAG, "Edit afterTextChanged 输入内容变化 editable = " + editable.toString());
                }
            }
        });

        // 搜索
        mDataBinding.friendSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 恢复搜索页
                    mPageFlag = Constant.REQUEST_PAGE;
                    String keyWords = v.getText().toString();
                    mPresenter.getFriendList(keyWords, mPageFlag);
                    return true;
                }

                return false;
            }
        });

        Log.d(TAG, "initEditView end");
    }

    @Override
    public void refreshListData(List<SearchUserBean.HaveUserBean> list) {
        Log.d(TAG, "refreshListData start");

        if (list != null && list.size() > 0) {
            Log.d(TAG, "refreshListData refreshData.size() = " + list.size());
            mFriendListData.clear();
            // 加载成功
            mPageFlag = String.valueOf(Integer.parseInt(mPageFlag) + 1);
            mFriendListData.addAll(list);
            mListAdapter.notifyDataSetChanged();
            mDataBinding.friendsList.setVisibility(View.VISIBLE);
            mDataBinding.friendsListEmpty.setVisibility(View.GONE);
        } else {
            mDataBinding.friendsList.setVisibility(View.GONE);
            mDataBinding.friendsListEmpty.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "refreshListData end");
    }
}
