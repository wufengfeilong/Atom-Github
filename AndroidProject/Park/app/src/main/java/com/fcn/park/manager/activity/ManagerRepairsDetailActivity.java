package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.databinding.ManagerRepairsDetailBinding;
import com.fcn.park.manager.bean.ManagerRepairsDetailInfoBean;
import com.fcn.park.manager.contract.ManagerRepairsDetailContract;
import com.fcn.park.manager.presenter.ManagerRepairsDetailPresenter;

/**
 * Created by 丁胜胜 on 2018/04/24
 * 类描述：管理中心的报修详情用Activity
 */

public class ManagerRepairsDetailActivity extends BaseActivity<ManagerRepairsDetailBinding, ManagerRepairsDetailContract.View, ManagerRepairsDetailPresenter>
        implements ManagerRepairsDetailContract.View{

    private static final String ID_TAG = "repairId";
    private static final String Ph_TAG = "repairPhone";

    private String repairId;
    private String repairPhone;
    String repairPic1 = null;
    String repairPic2 = null;
    String repairPic3 = null;

    /**
     * 将从后台取到的内容显示到画面上
     * @param bean
     */
    @Override
    public void updateInfo(ManagerRepairsDetailInfoBean bean) {
        mDataBinding.setInfoBean(bean);
        //显示图片
        repairPic1 = bean.getRepairPic1();
        if (repairPic1 != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + repairPic1)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.repairLoadPicture1);
        }
        repairPic2 = bean.getRepairPic2();
        if (repairPic2 != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + repairPic2)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.repairLoadPicture2);
        }
        repairPic3 = bean.getRepairPic3();
        if (repairPic3 != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + repairPic3)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.repairLoadPicture3);
        }
    }

    /**
     * 获取报修列表id
     * @return
     */
    @Override
    public String getRepairId() {
        return repairId;
    }

    /**
     * 获取RepairPhone
     * @return
     */
    @Override
    public String getRepairPhone() {
        return repairPhone;
    }

    /**
     * 设置标题以及返回按钮显示
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_publish_repairs_detail));
        openTitleLeftView(true);
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
        mPresenter.loadInfo();
        // 将Presenter通过DataBinding与当前的View绑定
        mDataBinding.setRepairsDetailPresenter(mPresenter);
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_repairs_detail;
    }

    /**
     * 重写的方法，通过intent获取前画面传过来的值
     * 用来初始化一些成员变量，这个方法调用的时间在所有方法调用之前
     */
    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        repairId = intent.getStringExtra(ID_TAG);
        repairPhone = intent.getStringExtra(Ph_TAG);
    }

    /**
     * 启动当前的Activity
     * @param context
     * @param repairId
     * @param repairPhone
     */
    public static void actionStart(Context context, String repairId,String repairPhone) {
        Intent intent = new Intent(context, ManagerRepairsDetailActivity.class);
        intent.putExtra(ID_TAG, repairId);
        intent.putExtra(Ph_TAG, repairPhone);
        context.startActivity(intent);
    }

    @Override
    protected ManagerRepairsDetailPresenter createPresenter() {
        return new ManagerRepairsDetailPresenter();
    }

}
