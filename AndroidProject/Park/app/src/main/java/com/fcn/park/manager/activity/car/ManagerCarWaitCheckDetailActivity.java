package com.fcn.park.manager.activity.car;

import android.content.Context;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.adapter.SimpleBindingAdapter;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.utils.GlideCircleTransform;
import com.fcn.park.databinding.ManagerCarWaitcheckdetailBinding;
import com.fcn.park.manager.bean.car.CarWaitCheckDetailInfoBean;
import com.fcn.park.manager.contract.car.CarWaitCheckListContract;
import com.fcn.park.manager.contract.car.ManagerCarWaitCheckDetailContract;
import com.fcn.park.manager.presenter.car.ManagerCarWaitCheckDetailPresenter;

/**
 * 管理中心的月租车辆审批详情画面Activity.
 */
public class ManagerCarWaitCheckDetailActivity extends BaseActivity<ManagerCarWaitcheckdetailBinding, ManagerCarWaitCheckDetailContract.View, ManagerCarWaitCheckDetailPresenter>
        implements ManagerCarWaitCheckDetailContract.View{

    private static final String ID_TAG = "ParkPay_id";
    private static final String C_TAG = "checkinfo";
    private String ParkPay_id;
    private String UserId;
    private SimpleBindingAdapter<CarWaitCheckDetailInfoBean> mAdapter;
    private int mClickPosition;

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_monthly_vehicle_check_detail));
        openTitleLeftView(true);
    }

    /**
     * 重写的方法获取画面的id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_car_waitcheckdetail;
    }

    /**
     * 启动当前的Activity
     * @param context
     * @param view
     * @param ParkPay_id
     */
    public static void actionStart(Context context, CarWaitCheckListContract.View view, String ParkPay_id) {
        Intent intent = new Intent(context, ManagerCarWaitCheckDetailActivity.class);
        intent.putExtra(ID_TAG, ParkPay_id);
        context.startActivity(intent);
    }

    /**
     * 调用该类中其他方法之前调用此方法
     */
    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        ParkPay_id = intent.getStringExtra(ID_TAG);
    }

    /**
     * 画面初始化时，调用此方法，向后台请求画面要显示的数据
     */
    @Override
    protected void initViews() {
          mPresenter.loadInfo();
          mDataBinding.setManagerCarWaitCheckDetailPresenter(mPresenter);
    }

    //工作证件
    String onJobProImage=null;
    //驾驶证
    String driverCardImage=null;
    //行驶证
    String driveringCardImage=null;
    @Override
    public void updateInfo(CarWaitCheckDetailInfoBean bean) {
        //显示工作证件图片
        onJobProImage=bean.getOnJobProImage();
        if (onJobProImage !=null){
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + onJobProImage)
                    .into(mDataBinding.managerCarOnJobProImage);

        }
        //显示驾驶证图片
        driverCardImage=bean.getDriverCardImage();
        if (driverCardImage !=null){
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + driverCardImage)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into(mDataBinding.managerCarDriverCardImage);

        }
        //显示行驶证图片
        driveringCardImage=bean.getDriveringCardImage();
        if (driveringCardImage !=null){
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + driveringCardImage)
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .into(mDataBinding.managerCarDriveringCardImage);

        }
        mDataBinding.setCarWaitCheckDetailInfoBean(bean);
    }

    /**
     * 获取ParkPay_id
     * @return
     */
    @Override
    public String getParkPay_id() {
        return ParkPay_id;
    }

    /**
     * 获取UserId
     * @return
     */
    @Override
    public String getUserId() {
        return UserId;
    }

    @Override
    protected ManagerCarWaitCheckDetailPresenter createPresenter() {
        return new ManagerCarWaitCheckDetailPresenter();
    }

    /**
     * 获取驳回理由
     * @return
     */
    @Override
    public String getCheckInfo() {
        return mDataBinding.managerCarCheckInfoEt.getText().toString();
    }
}
