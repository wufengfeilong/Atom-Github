package com.fcn.park.info.presenter;


import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.HttpResult;
import com.fcn.park.base.http.ProgressSubscriber;
import com.fcn.park.base.http.RequestImpl;
import com.fcn.park.info.bean.NewsTypeBean;
import com.fcn.park.info.contract.InfoContract;
import com.fcn.park.info.module.InfoModule;
import com.fcn.park.info.module.InfoTabLayoutModel;

import java.util.List;

/**
 * Created by liuyq  2018/04/09.
 * 园区动态底层使用包含tabLayout中title类型的取值
 */

public class InfoPresenter extends BasePresenter<InfoContract.View> implements InfoContract.Presenter {

    private InfoModule mModule;
    private InfoTabLayoutModel mRequestModule;

    @Override
    public void attach(InfoContract.View view) {
        super.attach(view);
        mRequestModule = new InfoTabLayoutModel();
        mModule = new InfoModule();
    }

    /**
     * 初始化fragment
     */
    @Override
    public void initFragments() {
        mRequestModule.newsType(new ProgressSubscriber<HttpResult<List<NewsTypeBean>>>(getView().getContext(), new RequestImpl<HttpResult<List<NewsTypeBean>>>() {
            @Override
            public void onNext(HttpResult<List<NewsTypeBean>> listHttpResult) {
                mModule.setNewsTypeList(listHttpResult.getData());
                getView().bindTabFragment(mModule.getFragmentList(), mModule.getTitleList());
            }
        }));

    }


}
