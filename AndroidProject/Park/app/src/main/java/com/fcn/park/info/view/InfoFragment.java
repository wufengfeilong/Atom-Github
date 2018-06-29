package com.fcn.park.info.view;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcn.park.R;
import com.fcn.park.base.BaseFragment;
import com.fcn.park.base.adapter.FragmentAdapter;
import com.fcn.park.databinding.InfoFragmentBinding;
import com.fcn.park.info.contract.InfoContract;
import com.fcn.park.info.presenter.InfoPresenter;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/03.
 * 园区动态页面底层fragment
 */

public class InfoFragment extends BaseFragment<InfoFragmentBinding, InfoContract.View, InfoPresenter>
        implements InfoContract.View {

    private FragmentAdapter mAdapter;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.info_fragment;
    }

    @Override
    protected void initViews() {
        setTitleText("园区动态");
        mPresenter.initFragments();
//        mDataBinding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mDataBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    }

    @Override
    protected InfoPresenter createPresenter() {
        return new InfoPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void bindTabFragment(List<Fragment> fragmentList, List<String> titleList) {

        if (fragmentList.isEmpty() || titleList.isEmpty()) return;
        mAdapter = new FragmentAdapter(getChildFragmentManager());

        mAdapter.addFragmentWithTitle(fragmentList, titleList);
        mDataBinding.viewPager.setOffscreenPageLimit(1);
        mDataBinding.viewPager.setAdapter(mAdapter);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);
    }

}
