package woxingwoxiu.com.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;


import woxingwoxiu.com.BR;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.SimpleBindingAdapter;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivitySearchUserBinding;
import woxingwoxiu.com.home.bean.SearchUserBean;
import woxingwoxiu.com.home.contract.SearchUserContract;
import woxingwoxiu.com.home.presenter.SearchUserPresenter;
import woxingwoxiu.com.login.activity.GetPhoneActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchUserActivity extends BaseActivity<ActivitySearchUserBinding, SearchUserPresenter>
        implements SearchUserContract.View, GroupedRecyclerViewAdapter.OnChildClickListener {
    List<SearchUserBean> mBeanList = new ArrayList<>();
    List<SearchUserBean.HaveUserBean> haveUserBeanList = new ArrayList<>();
    GroupedListAdapter mGroupedListAdapter;
    SimpleBindingAdapter<SearchUserBean.HaveUserBean> mSimpleBindingAdapter;
    int flag_a = 1;

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        initRecyclerView();
        mPresenter.loadListData();

        /*设置搜索结果adapter*/
        mDataBinding.searchLayoutTwo.setLayoutManager(new LinearLayoutManager(SearchUserActivity.this));
        mDataBinding.searchLayoutTwo.addItemDecoration(new DividerItemDecoration(SearchUserActivity.this, DividerItemDecoration.VERTICAL));
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.have_user_item, BR.haveUserBean);
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

                } else if (mPresenter.isUserHave()) {
                    //匹配到用户
                    LinearLayout searchLayoutOne = (LinearLayout) mDataBinding.searchLayoutOne;
                    searchLayoutOne.setVisibility(View.GONE);
                    LinearLayout noAnswer = (LinearLayout) mDataBinding.noAnswer;
                    noAnswer.setVisibility(View.GONE);
                    RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchLayoutTwo;
                    searchLayoutTwo.setVisibility(View.VISIBLE);
                    initUserData();
                    mDataBinding.searchLayoutTwo.setAdapter(mSimpleBindingAdapter);
                    if (mDataBinding.searchLayoutTwo != null) {
                        Log.d("afterTextChanged", "getListHeight=" + mDataBinding.searchLayoutTwo.getHeight());
                    }
                } else {
                    //没有匹配到用户
                    LinearLayout searchLayoutOne = (LinearLayout) mDataBinding.searchLayoutOne;
                    searchLayoutOne.setVisibility(View.GONE);
                    LinearLayout noAnswer = (LinearLayout) mDataBinding.noAnswer;
                    noAnswer.setVisibility(View.VISIBLE);
                    RecyclerView searchLayoutTwo = (RecyclerView) mDataBinding.searchLayoutTwo;
                    searchLayoutTwo.setVisibility(View.GONE);
                }
            }
        });
    }

    public void initUserData() {
        haveUserBeanList.clear();
        SearchUserBean.HaveUserBean bean1 = new SearchUserBean.HaveUserBean();
        bean1.setUserName("钢铁侠");
        haveUserBeanList.add(bean1);
        SearchUserBean.HaveUserBean bean2 = new SearchUserBean.HaveUserBean();
        bean2.setUserName("绿巨人");
        haveUserBeanList.add(bean2);
        SearchUserBean.HaveUserBean bean3 = new SearchUserBean.HaveUserBean();
        bean3.setUserName("绯红女巫");
        haveUserBeanList.add(bean3);
        mSimpleBindingAdapter.setupData(haveUserBeanList);
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
//        mPresenter.loadListData();
    }

    @Override
    public void onLoadmore() {

    }

    @Override
    public void initRecyclerView() {
        mDataBinding.searchUserSpringView.setListener(this);
//        mDataBinding.searchUserSpringView.setHeader(new DefaultHeader(this));
//        mDataBinding.searchUserSpringView.setFooter(new DefaultFooter(this));
        mDataBinding.searchUserRv.setLayoutManager(new LinearLayoutManager(this));
        mGroupedListAdapter = new GroupedListAdapter(this);
        mDataBinding.searchUserRv.setAdapter(mGroupedListAdapter);
        mGroupedListAdapter.setOnChildClickListener(this);
    }

    @Override
    public void bindListData(List<SearchUserBean> beanList) {
        mBeanList = beanList;

//        size = mBeanList.get(2).getRecommendUserBeans().size();
//        mDataBinding.searchUserRv.setAdapter(mGroupedListAdapter);
        mGroupedListAdapter.changeDataSet();
//        mDataBinding.searchUserSpringView.onFinishFreshAndLoad();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.searchUserRv;
    }

    @Override
    public void onChildClick(GroupedRecyclerViewAdapter groupedRecyclerViewAdapter, BaseViewHolder baseViewHolder, int i, int i1) {
        //TODO
        showToast("group:" + i + "child:" + i1);
        if(i==1) {
            Intent intent = new Intent(SearchUserActivity.this, GetPhoneActivity.class);
            startActivity(intent);
        }
    }


    public class GroupedListAdapter extends GroupedRecyclerViewAdapter {
        private static final int TYPE_1 = 0;
        private static final int TYPE_2 = 1;
        private static final int TYPE_3 = 2;

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
                case TYPE_1:
                    count = searchUserBean.getRecommendTopicBeans().size();
                    break;
                case TYPE_2:
                    count = 1;
                    break;
                case TYPE_3:
                    count = searchUserBean.getRecommendUserBeans().size();
                    break;
            }
            return count;
        }

        @Override
        public boolean hasHeader(int i) {
            if (i == TYPE_1) {
                return true;
            } else if (i == TYPE_2) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public boolean hasFooter(int i) {
            if (i == TYPE_1) {
                return true;
            } else if (i == TYPE_2) {
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
            if (i == TYPE_1) {
                return R.layout.multi_recyclerview_type1;
            } else if (i == TYPE_2) {
                return R.layout.multi_recyclerview_type2;
            } else {
                return R.layout.multi_recyclerview_type3;
            }
        }

        @Override
        public void onBindHeaderViewHolder(BaseViewHolder holder, int i) {

            if (flag_a <= 2) {
                switch (i) {
                    case TYPE_1:
                        holder.setText(R.id.multi_type_list_title, getString(R.string.recommend_topic));
                        break;
                    case TYPE_2:
                        break;
                    case TYPE_3:
                        holder.setText(R.id.multi_type_list_title, getString(R.string.recommend_user));
                        break;
                }
            }
            flag_a++;

        }

        @Override
        public void onBindFooterViewHolder(BaseViewHolder baseViewHolder, int i) {

        }

        @Override
        public void onBindChildViewHolder(BaseViewHolder holder, int i, int childPosition) {
            SearchUserBean searchUserBean = mBeanList.get(i);


            switch (i) {
                case TYPE_1:
                    SearchUserBean.RecommendTopicBean topicBean = searchUserBean.getRecommendTopicBeans().get(childPosition);
                    holder.setText(R.id.multi_list_type1_title, topicBean.getTitle());
                    holder.setText(R.id.multi_list_type1_count, topicBean.getCount());
                    break;
                case TYPE_2:
                    holder.setText(R.id.multi_list_type2_count, searchUserBean.getPhoneCount() + "");
                    break;
                case TYPE_3:
                    SearchUserBean.RecommendUserBean userBean = searchUserBean.getRecommendUserBeans().get(childPosition);
//                    holder.setImageBitmap(R.id.multi_list_type3_head, null);
//                    Glide.with(SearchUserActivity.this).load(userBean.getHeadUrl()).into((ImageView) holder.get(R.id.multi_list_type3_head));

                    holder.setText(R.id.multi_list_type3_name, userBean.getNickName());
                    holder.setText(R.id.multi_list_type3_introduce, userBean.getIntroduce());
                    if (!userBean.isFollow()) {
                        holder.setImageBitmap(R.id.multi_list_type3_follow, null);
                    } else {
                        holder.setImageResource(R.id.multi_list_type3_follow, R.drawable.follow_red);
                    }
                    break;
            }
        }

        @Override
        public int getHeaderViewType(int groupPosition) {
            return mBeanList.get(groupPosition).getType();
        }

        @Override
        public int getChildViewType(int groupPosition, int childPosition) {
            return mBeanList.get(groupPosition).getType();
        }
    }
}
