package com.fcn.park.me.activity;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.databinding.MeRepairRecordDetailActivityBinding;
import com.fcn.park.manager.activity.ManagerRepairsDetailActivity;
import com.fcn.park.me.bean.RepairRecordDetailBean;
import com.fcn.park.me.contract.MeRepairRecordDetailContract;
import com.fcn.park.me.presenter.MeRepairRecordDetailPresenter;


/**
 * 类描述：报修详情用Activity
 */
public class MeRepairRecordDetailActivity extends BaseActivity<MeRepairRecordDetailActivityBinding, MeRepairRecordDetailContract.View, MeRepairRecordDetailPresenter>
        implements MeRepairRecordDetailContract.View{

    private static final String ID_TAG = "repairId";
    private String repairId;
    String repairPic1 = null;
    String repairPic2 = null;
    String repairPic3 = null;
    //设置标题为"报修详情"
    @Override
    protected void setupTitle() {
        setTitleText("报修详情");
        openTitleLeftView(true);
    }

    @Override
    public void updateInfo(RepairRecordDetailBean bean) {
        mDataBinding.setInfoBean(bean);
        repairPic1 = bean.getRepairPic1();
        if (repairPic1 != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + repairPic1)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.repairLoadPicture11);
        }
        repairPic2 = bean.getRepairPic2();
        if (repairPic2 != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + repairPic2)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.repairLoadPicture22);
        }
        repairPic3 = bean.getRepairPic3();
        if (repairPic3 != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + repairPic3)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.repairLoadPicture33);
        }
    }
    @Override
    protected MeRepairRecordDetailPresenter createPresenter(){
        return new MeRepairRecordDetailPresenter();
    }

    @Override
    public String getRepairId() {
        return repairId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.me_repair_record_detail_activity;
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        repairId = intent.getStringExtra(ID_TAG);
    }
    public static void actionStart(Context context, String repairId) {
        Intent intent = new Intent(context, MeRepairRecordDetailActivity.class);
        intent.putExtra(ID_TAG, repairId);
        context.startActivity(intent);
    }
    @Override
    protected void initViews() {
        mPresenter.loadInfo();
        mDataBinding.setRepairRecordDetailPresenter(mPresenter);
    }


}
