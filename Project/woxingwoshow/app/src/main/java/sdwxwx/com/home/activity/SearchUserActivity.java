package sdwxwx.com.home.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.databinding.ActivitySearchUserBinding;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.home.contract.SearchUserContract;
import sdwxwx.com.home.presenter.SearchUserPresenter;
import sdwxwx.com.login.activity.GetPhoneActivity;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.adapter.FindFriendsAdapter;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.message.model.FansHomeModel;
import sdwxwx.com.play.model.PlayVideoFragmentModel;
import sdwxwx.com.util.NetworkUtils;
import sdwxwx.com.widget.LoadStatusView;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends BaseActivity<ActivitySearchUserBinding, SearchUserPresenter>
        implements SearchUserContract.View, GroupedRecyclerViewAdapter.OnChildClickListener {
    List<SearchUserBean> mBeanList = new ArrayList<>();
    GroupedListAdapter mGroupedListAdapter;
    /** 定义检索用户的适配器 */
    FindFriendsAdapter searchUserAdapter = null;
    /** 检索到的用户列表 */
    List<RecommendUserBean> haveUserBeanList = new ArrayList<RecommendUserBean>();
    private static final int SUCCESSCODE = 100;
    /** 检索的页数 */
    private int page = 1;
    /** 检索的关键字 */
    private String keyWord = "";

    @Override
    protected void initViews() {
//        ShowApplication app = ShowApplication.getApplication();
//        String id = app.getUserBean().getId();
//        showToast(id+"");
        mDataBinding.setPresenter(mPresenter);
        if (!NetworkUtils.isNetworkAvaiable(this)) {
            mDataBinding.searchLsv.setViewState(LoadStatusView.VIEW_STATE_ERROR,getString(R.string.common_no_network_msg));
            mDataBinding.searchLsv.setOnStatusPageClickListener(new LoadStatusView.OnStatusPageClickListener() {
                @Override
                public void onError() {
                    if (!NetworkUtils.isNetworkAvaiable(SearchUserActivity.this)) {
                        return;
                    }
                    hasNetInitViews();
                    mDataBinding.searchLsv.setViewState(LoadStatusView.VIEW_STATE_CONTENT);
                }


            });
            return;
        }
        hasNetInitViews();
        ActivityCollector.addActivity(this);
    }

    private void hasNetInitViews() {
        initRecyclerView();
        mPresenter.loadListData();
        /*设置搜索结果adapter*/
        mDataBinding.searchLayoutTwo.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
        mDataBinding.searchUserTitleEt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDataBinding.searchUserTitleEt.setCursorVisible(true);
            }
        });
        editTextChange();//监听edittext输入
    }

    public void editTextChange() {


        mDataBinding.searchUserTitleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 如果文字改变，对页码进行清空操作
                page = 1;
                //文字输入后的状态
                String afterText = mDataBinding.searchUserTitleEt.getText().toString().trim();
                if (afterText.isEmpty()) {
                    //输入框为空的状态
                    LinearLayout searchLayoutOne = (LinearLayout) mDataBinding.searchLayoutOne;
                    searchLayoutOne.setVisibility(View.VISIBLE);
                    LinearLayout noAnswer = (LinearLayout) mDataBinding.noAnswer;
                    noAnswer.setVisibility(View.GONE);
                    RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchLayoutTwo;
                    searchLayoutTwo.setVisibility(View.GONE);
                } else {
                    keyWord = afterText;
                    haveUserBeanList.clear();
                    // 进行用户检索
                    mPresenter.searchUser(afterText, String.valueOf(page));
                }
            }
        });
    }

    /**
     * 当检索到数据的时候
     */
    public void haveUser(List<SearchUserBean.HaveUserBean> list) {
        //匹配到用户
        LinearLayout searchLayoutOne = (LinearLayout) mDataBinding.searchLayoutOne;
        searchLayoutOne.setVisibility(View.GONE);
        LinearLayout noAnswer = (LinearLayout) mDataBinding.noAnswer;
        noAnswer.setVisibility(View.GONE);
        mDataBinding.searchUserSpringView.setVisibility(View.VISIBLE);
        mDataBinding.searchUserSpringView.setListener(this);
        mDataBinding.searchUserSpringView.setHeader(new DefaultHeader(this));
        mDataBinding.searchUserSpringView.setFooter(new DefaultFooter(this));
        RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchLayoutTwo;
        searchLayoutTwo.setVisibility(View.VISIBLE);
        RecommendUserBean userBean;
        // 暂定方法，对性能存在影响，后期变更预定
        for (SearchUserBean.HaveUserBean bean:list) {
            userBean = new RecommendUserBean();
            userBean.setId(Integer.parseInt(bean.getId()));
            userBean.setNickname(bean.getNickname());
            userBean.setSignature(bean.getSignature());
            userBean.setAvatar_url(bean.getAvatar_url());
            userBean.setIs_followed(bean.getIs_followed());
            haveUserBeanList.add(userBean);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchUserAdapter.notifyDataSetChanged();
                mDataBinding.searchUserSpringView.onFinishFreshAndLoad();
            }
        });
    }

    /**
     * 没有检索到数据时的画面处理
     */
    public void haveNoUser() {
        if (haveUserBeanList == null || haveUserBeanList.size() == 0) {
            //没有匹配到用户
            LinearLayout searchLayoutOne = (LinearLayout) mDataBinding.searchLayoutOne;
            searchLayoutOne.setVisibility(View.GONE);
            mDataBinding.searchUserSpringView.setVisibility(View.GONE);
            LinearLayout noAnswer = (LinearLayout) mDataBinding.noAnswer;
            noAnswer.setVisibility(View.VISIBLE);
            RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchLayoutTwo;
            searchLayoutTwo.setVisibility(View.GONE);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchUserAdapter.notifyDataSetChanged();
                mDataBinding.searchUserSpringView.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    protected SearchUserPresenter createPresenter() {
        return new SearchUserPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_user;
    }


    @Override
    public void onRefresh() {
        haveUserBeanList.clear();
        page = 1;
        mPresenter.searchUser(keyWord,String.valueOf(page));
    }

    @Override
    public void onLoadmore() {
        mPresenter.searchUser(keyWord,String.valueOf(++page));
    }

    @Override
    public void initRecyclerView() {

        mDataBinding.searchUserRv.setLayoutManager(new LinearLayoutManager(this));
        mGroupedListAdapter = new GroupedListAdapter(this);
        mDataBinding.searchUserRv.setAdapter(mGroupedListAdapter);
        mGroupedListAdapter.setOnChildClickListener(this);

        // 定义检索用户的adapter
        searchUserAdapter = new FindFriendsAdapter(haveUserBeanList, getContext());
        searchUserAdapter.setAdapter(searchUserAdapter);
        // 定义点击事件，点击用户检索中的某行，则跳转到相应的好友主页
        searchUserAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                // 跳转到好友资料画面
                if (isLogin()) {
                    paramStartActivity(FansHomeActivity.class, String.valueOf(haveUserBeanList.get(postion).getId()));
                } else {
                    actionStartActivity(LoginActivity.class);
                }
            }
        });
        // 把适配器添加到layout中
        mDataBinding.searchLayoutTwo.setAdapter(searchUserAdapter);
    }

    @Override
    public void bindListData(List<SearchUserBean> beanList) {
        mBeanList = beanList;
        mGroupedListAdapter.changeDataSet();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.searchUserRv;
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter groupedRecyclerViewAdapter, BaseViewHolder baseViewHolder, int i, int i1) {

        if (i == 0) {
            param2StartActivity(TopicVideoListActivity.class
                    , mBeanList.get(0).getRecommendTopicBeans().get(i1).getTitle()
                    , Integer.parseInt(mBeanList.get(0).getRecommendTopicBeans().get(i1).getId()));
        }
        if (i == 1) {
            if (isLogin()) {
                actionStartActivity(GetPhoneActivity.class);
            } else {
                actionStartActivity(LoginActivity.class);
            }
        }
        if (i == 2) {
            if (isLogin()) {
                paramStartActivity(FansHomeActivity.class, mBeanList.get(2).getRecommendUserBeans().get(i1).getId() + "");
            } else {
                actionStartActivity(LoginActivity.class);
            }
        }
    }


    public class GroupedListAdapter extends GroupedRecyclerViewAdapter {
        private static final int TYPE_HEAD_1 = 0;
        private static final int TYPE_HEAD_2 = 1;
        private static final int TYPE_HEAD_3 = 2;
        private static final int TYPE_CHILD_1 = 3;
        private static final int TYPE_CHILD_2 = 4;
        private static final int TYPE_CHILD_3 = 5;
        private static final int TYPE_FOOT_1 = 6;
        private static final int TYPE_FOOT_2 = 7;
        private static final int TYPE_FOOT_3 = 8;

        public GroupedListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getGroupCount() {
            return mBeanList == null ? 0 : mBeanList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            SearchUserBean searchUserBean = mBeanList.get(i);
            int count = 0;
            switch (i) {
                case 0:
                    count = searchUserBean.getRecommendTopicBeans().size();
                    break;
                case 1:
                    count = 1;
                    break;
                case 2:
                    count = searchUserBean.getRecommendUserBeans().size();
                    break;
            }
            return count;
        }

        @Override
        public boolean hasHeader(int i) {
            int headerViewType = getHeaderViewType(i);
            if (headerViewType == TYPE_HEAD_1) {
                return true;
            } else if (headerViewType == TYPE_HEAD_2) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean hasFooter(int i) {
            int footerViewType = getFooterViewType(i);
            if (footerViewType == TYPE_FOOT_1) {
                return true;
            } else if (footerViewType == TYPE_FOOT_2) {
                return false;
            } else {
                return false;
            }
        }

        @Override
        public int getHeaderLayout(int i) {
            return R.layout.multi_recyclerview_header;
        }

        @Override
        public int getFooterLayout(int i) {
            return R.layout.multi_recyclerview_footer;
        }

        @Override
        public int getChildLayout(int i) {
            if (i == TYPE_CHILD_1) {
                return R.layout.multi_recyclerview_type1;
            } else if (i == TYPE_CHILD_2) {
                return R.layout.multi_recyclerview_type2;
            } else {
                return R.layout.item_message_friend_fans;
            }
        }

        @Override
        public void onBindHeaderViewHolder(BaseViewHolder holder, int i) {

            int headerViewType = getHeaderViewType(i);
            switch (headerViewType) {
                case TYPE_HEAD_1:
                    holder.setText(R.id.multi_type_list_title, getString(R.string.recommend_topic));
                    break;
                case TYPE_HEAD_2:
                    break;
                case TYPE_HEAD_3:
                    holder.setText(R.id.multi_type_list_title, getString(R.string.recommend_user));
                    break;
            }


        }

        @Override
        public void onBindFooterViewHolder(BaseViewHolder baseViewHolder, int i) {

        }

        @Override
        public void onBindChildViewHolder(BaseViewHolder holder, int i, int childPosition) {
            SearchUserBean searchUserBean = mBeanList.get(i);

            int childViewType = getChildViewType(i, childPosition);
            switch (childViewType) {
                case TYPE_CHILD_1:
                    TopicInfoBean topicBean = searchUserBean.getRecommendTopicBeans().get(childPosition);
                    holder.setText(R.id.multi_list_type1_title, topicBean.getTitle());
                    holder.setText(R.id.multi_list_type1_count,
                            topicBean.getVideo_count() + getString(R.string.release_topic_count));
                    break;
                case TYPE_CHILD_2:
                    if (hasContactPermission()) {
                        holder.setImageResource(R.id.phone_book_icon, R.drawable.phone_book_yellow);
                        holder.setText(R.id.phone_book_bing_tv, getString(R.string.has_bind));
                    } else {
                        holder.setImageResource(R.id.phone_book_icon, R.drawable.phone_book_gray);
                        holder.setText(R.id.phone_book_bing_tv, getString(R.string.no_bind));
                    }
                    break;
                case TYPE_CHILD_3:
                    final RecommendUserBean userBean = searchUserBean.getRecommendUserBeans().get(childPosition);
//                    holder.setImageBitmap(R.id.multi_list_type3_head, null);
                    RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
                    Glide.with(SearchUserActivity.this).load(userBean.getAvatar_url()).apply(options).into((ImageView) holder.get(R.id.fans_avatar_url));

                    holder.setText(R.id.nickname, userBean.getNickname());
                    if (TextUtils.isEmpty(userBean.getSignature())) {
                        holder.setVisible(R.id.signature,View.GONE);
                    } else {
                        // 设置签名
                        holder.setText(R.id.signature, userBean.getSignature());
                        holder.setVisible(R.id.signature,View.VISIBLE);
                    }
                    // 如果是本人则不设置任何图片
                    if (!LoginHelper.getInstance().getUserId().equals(String.valueOf(userBean.getId()))) {
                        if ("1".equals(userBean.getIs_followed())) {
                            holder.setImageResource(R.id.is_followed, R.drawable.already_follow);
                        } else {
                            holder.setImageResource(R.id.is_followed, R.drawable.message_attention);
                        }
                    } else {
                        // 不显示
                        holder.get(R.id.is_followed).setVisibility(View.GONE);
                    }
                    // 判断显示加V图片
                    if ("1".equals(userBean.getIs_certified())) {
                        holder.setVisible(R.id.fans_avatar_vip_logo,View.VISIBLE);
                    } else {
                        holder.setVisible(R.id.fans_avatar_vip_logo,View.GONE);
                    }

                    ((ImageView) holder.get(R.id.is_followed)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isLogin()) {
                                // 更新画面的关注状态
                                if ("1".equals(userBean.getIs_followed())) {
                                    final AlertDialog.Builder normalDialog =
                                            new AlertDialog.Builder(getContext());
                                    normalDialog.setMessage("确定不再关注此人?");
                                    normalDialog.setPositiveButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    FansHomeModel.cancelFollow(mContext,LoginHelper.getInstance().getUserId()
                                                            , userBean.getId()+"", new BaseCallback<String>() {
                                                                @Override
                                                                public void onSuccess(String data) {
                                                                    userBean.setIs_followed("0");
                                                                    mGroupedListAdapter.changeDataSet();
                                                                }

                                                                @Override
                                                                public void onFail(String msg) {
                                                                    showToast(msg);
                                                                }
                                                            });

                                                }
                                            });
                                    normalDialog.setNegativeButton("取消",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // 不做任何处理
                                                }
                                            });
                                    // 显示
                                    normalDialog.show();
                                } else {
                                    PlayVideoFragmentModel.followUser(mContext,LoginHelper.getInstance().getUserId()
                                            , userBean.getId() + "", new BaseCallback<String>() {
                                        @Override
                                        public void onSuccess(String data) {
                                            userBean.setIs_followed("1");
                                            mGroupedListAdapter.changeDataSet();
                                        }

                                        @Override
                                        public void onFail(String msg) {
                                            showToast(msg);
                                        }
                                    });
                                }
                            } else {
                                actionStartActivity(LoginActivity.class);
                            }
                        }
                    });
                    break;
            }
        }

        @Override
        public int getFooterViewType(int groupPosition) {
            if (groupPosition % 3 == 0) {
                return TYPE_FOOT_1;
            } else if (groupPosition % 3 == 1) {
                return TYPE_FOOT_2;
            } else {
                return TYPE_FOOT_3;
            }
        }

        @Override
        public int getHeaderViewType(int groupPosition) {
            if (groupPosition % 3 == 0) {
                return TYPE_HEAD_1;
            } else if (groupPosition % 3 == 1) {
                return TYPE_HEAD_2;
            } else {
                return TYPE_HEAD_3;
            }
        }

        @Override
        public int getChildViewType(int groupPosition, int childPosition) {
            if (groupPosition % 3 == 0) {
                return TYPE_CHILD_1;
            } else if (groupPosition % 3 == 1) {
                return TYPE_CHILD_2;
            } else {
                return TYPE_CHILD_3;
            }
        }
    }

    private boolean hasContactPermission(){
        int perm = this.checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
