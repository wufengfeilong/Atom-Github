package com.fcn.park.info.module;

import android.support.v4.app.Fragment;

import com.fcn.park.info.view.InfoEnterpriseListFragment;
import com.fcn.park.info.view.InfoDemandListFragment;
import com.fcn.park.info.view.InfoNewsListFragment;
import com.fcn.park.info.bean.NewsTypeBean;
import com.fcn.park.info.contract.ViewPagerWithTabContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyq on 2018/04/10.
 * 园区动态底层使用module
 */

public class InfoModule implements ViewPagerWithTabContract.Module {

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private List<NewsTypeBean> mNewsTypeList;


    public void setNewsTypeList(List<NewsTypeBean> newsTypeList) {
        mNewsTypeList = newsTypeList;
        addTitle();
        createFragment();
    }

    private void addTitle() {
        if (mTitleList == null) {
            mTitleList = new ArrayList<>();
        } else {
            mTitleList.clear();
        }
        for (NewsTypeBean newsTypeBean : mNewsTypeList) {
            mTitleList.add(newsTypeBean.getValue());
        }
    }

    private void createFragment() {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        } else {
            mFragmentList.clear();
        }
        for (NewsTypeBean newsTypeBean : mNewsTypeList) {
            if ("0".equals(newsTypeBean.getDistinguishId()) || "1".equals(newsTypeBean.getDistinguishId()) || "2".equals(newsTypeBean.getDistinguishId())) {//新闻公告
                mFragmentList.add(InfoNewsListFragment.newInstance(newsTypeBean.getDistinguishId()));
            } else if ("3".equals(newsTypeBean.getDistinguishId())) {//企业
                mFragmentList.add(InfoEnterpriseListFragment.newInstance());
            } else if ("4".equals(newsTypeBean.getDistinguishId())) {//招聘
                mFragmentList.add(InfoDemandListFragment.newInstance());
            }
        }
    }


    @Override
    public List<Fragment> getFragmentList() {
        return mFragmentList;
    }

    @Override
    public List<String> getTitleList() {
        return mTitleList;
    }
}
