package com.fcn.park.me;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.adapter.MeMenuAdapter;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.fragment.LazyFragment;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;
import com.fcn.park.base.utils.GlideCircleTransform;
import com.fcn.park.base.utils.MenuBuildUtils;
import com.fcn.park.databinding.FragmentMeBinding;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.login.bean.User;
import com.fcn.park.manager.bean.MenuBean;
import com.fcn.park.manager.utils.SpaceItemDecoration;

import java.util.List;

/**
 * 类描述：“我的”Tab页要显示内容
 */

public class MeFragment extends LazyFragment<FragmentMeBinding, MeContract.View, MePresenter>
        implements MeContract.View {

    private String Tag = "=== MeFragment ===";
    private MeMenuAdapter mMenuAdapter;
    private LocalBroadcastManager mBraoadcastManager;
    private BroadcastReceiver broadcastReceiver;
    List<MenuBean> mMenuBeanList;
    /**
     * 实例化我的fragment
     */
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }
    /**
     * 画面被创建时运行
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    /**
     * 设置布局文件
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_me;
    }
    /**
     * 初始化视图
     */
    @Override
    protected void initViews() {
        //消息过滤
        mBraoadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.LOCAL_BROADCAST_USER_AVATAR_FLAG);
        filter.addAction(Constant.LOCAL_BROADCAST_USER_NAME_FLAG);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                User userBean = LoginHelper.getInstance().getUserBean();
                switch (action) {
                    case Constant.LOCAL_BROADCAST_USER_AVATAR_FLAG:
                        String avatarPath = userBean.getAvatar();
                        Glide.with(MeFragment.this)
                                .load(ApiService.IMAGE_BASE + avatarPath)
                                .error(R.drawable.ic_vector_default_avatar)
                                .bitmapTransform(new GlideCircleTransform(mContext))
                                .into(mDataBinding.ivAvatar);
                        break;
                    case Constant.LOCAL_BROADCAST_USER_NAME_FLAG:
                        String name = userBean.getUserName();
                        mDataBinding.tvUserName.setText(name);
                        break;
                }

            }
        };
        //注册消息
        mBraoadcastManager.registerReceiver(broadcastReceiver, filter);
    }
    /**
     * 获取菜单列表
     */
    @Override
    public List<MenuItemImpl> getMenuList() {
        User user = LoginHelper.getInstance().getUserBean();
        mDataBinding.setUser(user);
        Log.d(Tag, "========= user = " + user);
        Log.d(Tag, "========= userName = " + user.getUserName());
        Log.d(Tag, "========= userType = " + user.getUserType());
        Log.d(Tag, "========= name = " + user.getUserName());
        Log.d(Tag, "========= avatar = " + user.getAvatar());
        if (user != null) {

            //如果是管理员用户登录进入相应的样式
            if (Constant.UserType.MANAGE.getValue().equals(user.getUserType()) || Constant.UserType.OTHER.getValue().equals(user.getUserType())) {
                // new SupportMenuInflater(mContext).inflate(R.menu.me_company_menu, menu);
                mDataBinding.llUserInfo.setBackgroundColor(Color.WHITE);
                mDataBinding.tvUserName.setTextColor(Color.BLACK);
                mDataBinding.llSpace.setVisibility(View.VISIBLE);
                mDataBinding.ivGo.setImageResource(R.drawable.ic_vector_go_gray);
                return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_manager_menu);
            } else if (Constant.UserType.PERSON.getValue().equals(user.getUserType())) {
                //new SupportMenuInflater(mContext).inflate(R.menu.me_person_menu, menu);
//                return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_person_menu);
                return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_personal_menu);
            } else if (Constant.UserType.ENTERPRISE.getValue().equals(user.getUserType())) {
                //new SupportMenuInflater(mContext).inflate(R.menu.me_person_menu, menu);
//                return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_person_menu);
                return MenuBuildUtils.buildMenuItemList(mContext, R.menu.me_company_menu);
            }
        }
        return null;
    }
    /**
     * 初始化RecyclerView
     */
    @Override
    public void initRecyclerView() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDataBinding.recyclerView.addOnItemTouchListener(new OnItemClickListener(getRecyclerView()));
        mMenuAdapter = new MeMenuAdapter(R.layout.item_layout_me_menu);
        mDataBinding.recyclerView.setAdapter(mMenuAdapter);

    }
    /**
     * item被点击
     */
    private class OnItemClickListener extends OnRecyclerItemClickListener {

        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }
    }
    /**
     * 获取RecyclerView
     */
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.recyclerView;
    }
    /**
     * 绑定list数据
     */
    @Override
    public void bindListData(List<MenuBean> beanList) {
        mMenuBeanList = beanList;
        Log.d("aixq", "------设置进来了数据-----" + beanList.size());
        mDataBinding.recyclerView.addItemDecoration(new SpaceItemDecoration(mContext, beanList));
        mMenuAdapter.setupData(beanList);

    }
    /**
     * 实例化presenter
     */
    @Override
    protected MePresenter createPresenter() {
        return new MePresenter();
    }
    /**
     * 创建视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * 加载数据设置标题
     */
    @Override
    protected void loadData() {

        String userType = LoginHelper.getInstance().getUserBean().getUserType();
        String title;
        if (Constant.UserType.MANAGE.getValue().equals(userType)) {
            title = getString(R.string.manager_name);
        } else if (Constant.UserType.ENTERPRISE.getValue().equals(userType)) {
            title = getString(R.string.me_company_name);
        } else if (Constant.UserType.PERSON.getValue().equals(userType)) {
            title = getString(R.string.me_person_name);
        } else {
            title = "我的";
        }
        setTitleText(title);
//        StatusBarCompat.setStatusBarColor(getActivity(), mTitleCompatLayout, true);
        mPresenter.initMenu();
        mDataBinding.setPresenter(mPresenter);
    }

    /**
     * 画面销毁时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mBraoadcastManager.unregisterReceiver(broadcastReceiver);
    }
    /**
     * 是否有未读信息
     */
    @Override
    public void isMsg(boolean flg) {
        if (flg) {
            mMenuBeanList.get(1).setMenuIcon(getContext().getResources().getDrawable(R.drawable.ic_vector_has_red_circle));
        } else {
            mMenuBeanList.get(1).setMenuIcon(getContext().getResources().getDrawable(R.drawable.ic_vector_no_red_circle));
        }
        mMenuAdapter.notifyDataSetChanged();
    }
    /**
     * 带参数跳转画面
     */
    @Override
    public void jumpActivity(Class cl,String flg){
        Intent i = new Intent(getContext(),cl);
        i.putExtra("flg",flg);
        startActivity(i);
    }
    /**
     * 画面重新载入时调用
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.hasMsg();
    }
}
