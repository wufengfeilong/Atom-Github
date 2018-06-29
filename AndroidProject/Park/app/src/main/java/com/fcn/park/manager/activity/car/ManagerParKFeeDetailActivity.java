package com.fcn.park.manager.activity.car;

import android.content.Context;
import android.content.Intent;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerParkFeeDetailBinding;
import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;
import com.fcn.park.manager.contract.car.ManagerParkFeeDetailContract;
import com.fcn.park.manager.presenter.car.ManagerParkFeeDetailPresenter;

/**
 * 停车付费详情画面Activity.
 */
public class ManagerParKFeeDetailActivity extends BaseActivity<ManagerParkFeeDetailBinding, ManagerParkFeeDetailContract.View, ManagerParkFeeDetailPresenter>
        implements ManagerParkFeeDetailContract.View{

    private static final String ID_TAG = "ParkPay_id";
    private String ParkPay_id;

    /**
     * 更新停车付费列表信息
     * @param bean
     */
    @Override
    public void updateInfo(ParkFeeDetailInfoBean bean) {
        mDataBinding.setParkFeeDetailInfoBean(bean);
    }

    /**
     * 获取停车付费车辆id
     * @return
     */
    @Override
    public String getParkPay_id() {
        return ParkPay_id;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_parking_fee_detail));
        openTitleLeftView(true);
    }

    /**
     * 获取停车付费详情画面id
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_park_fee_detail;
    }

    /**
     * 启动当前的Activity
     * @param context
     * @param view
     * @param ParkPay_id
     */
    public static void actionStart(Context context, ManagerParkFeeDetailContract.View view, String ParkPay_id) {
        Intent intent = new Intent(context, ManagerParKFeeDetailActivity.class);
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
    }

    @Override
    protected ManagerParkFeeDetailPresenter createPresenter() {
        return new ManagerParkFeeDetailPresenter();
    }
}
