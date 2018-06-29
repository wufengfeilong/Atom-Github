package com.fcn.park.property.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.utils.DateUtils;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.PropertyParkpayActivityLayoutBinding;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.bean.PropertyParkPayTypeBean;
import com.fcn.park.property.contract.PropertyParkPayContract;
import com.fcn.park.property.presenter.PropertyParkPayPresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyParkPayActivity extends BaseActivity<PropertyParkpayActivityLayoutBinding,
        PropertyParkPayContract.View, PropertyParkPayPresenter> implements PropertyParkPayContract.View {

    private List<PropertyParkPayTypeBean> typeList;
    private SinglePictureSelectPopWindow mSelectPopWindow;
    private Calendar mCalendar;
    private int idTmp;
    private Map<String, String> imageMap = new HashMap<String, String>();
    private DatePickerDialog mDateDialog;

    public PropertyParkPayActivity() {
    }

    /**
     * 选择图片的回调函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //获取选择的画像的路径
            String imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);
            //把图片加载到对应的位置
            Glide.with(this).load(imageUrl).into((ImageView) findViewById(idTmp));
            switch (idTmp) {
                //在职证明临时存储
                case R.id.onJobProImage:
                    imageMap.put("onJobImage", imageUrl);
                    break;
                //驾驶证临时存储
                case R.id.driverCardImage:
                    imageMap.put("driver", imageUrl);
                    break;
                //行驶证临时存储
                case R.id.driveringCardImage:
                    imageMap.put("drivering", imageUrl);
                    break;
            }
        }
    }

    /**
     * 申请类型选择方法
     *
     * @return
     */
    @Override
    public PropertyParkPayTypeBean getRadioSelected() {
        if (mDataBinding.applyTypeFirst.isSelected()) {
            return typeList.get(0);
        } else if (mDataBinding.applyTypeSecond.isSelected()) {
            return typeList.get(1);
        } else if (mDataBinding.applyTypeThird.isSelected()) {
            return typeList.get(2);
        }
        return null;
    }

    /**
     * 在职证明选择
     *
     * @param view
     */
    @Override
    public void openSelectPhotoPop1(View view) {
        idTmp = R.id.onJobProImage;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    /**
     * 驾驶证选择
     *
     * @param view
     */
    @Override
    public void openSelectPhotoPop2(View view) {
        idTmp = R.id.driverCardImage;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    /**
     * 行驶证选择
     *
     * @param view
     */
    @Override
    public void openSelectPhotoPop3(View view) {
        idTmp = R.id.driveringCardImage;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    protected PropertyParkPayPresenter createPresenter() {
        return new PropertyParkPayPresenter();
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText("月租车辆申请");
    }

    /**
     * 日期选择变化方法
     *
     * @param inputContent
     */
    @Override
    public void itemDataChangeSet(String inputContent) {
        mDataBinding.propertyRentParkCalendarText.setText(inputContent);
    }

    /**
     * 页面初始化数据设置
     */
    @Override
    protected void initViews() {
        PropertyParkPayBean bean = new PropertyParkPayBean();
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
        mDataBinding.setPresenter(mPresenter);
        mDataBinding.setParkPayBean(bean);

        imageMap.put("onJobImage", "");
        imageMap.put("driver", "");
        imageMap.put("drivering", "");

        //日历控件
        mCalendar = Calendar.getInstance();
        View.OnClickListener calendarListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new java.util.Date());
                long dataTime = DateUtils.stringToDate(date);
                mCalendar.setTimeInMillis(dataTime);
                mDateDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mPresenter.itemDataChange(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                mDateDialog.show();
                // 返回键，Dialog消失
                mDateDialog.setCancelable(true);
                // 点击Dialog以外区域，Dialog消失
                mDateDialog.setCanceledOnTouchOutside(true);
            }
        };
        mDataBinding.propertyRentParkCalendarImg.setOnClickListener(calendarListener);
        mDataBinding.propertyRentParkCalendarText.setOnClickListener(calendarListener);

        mDataBinding.applyTypeFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                // 单选控制
                if (view.isSelected()) {
                    mDataBinding.applyTypeSecond.setSelected(false);
                    mDataBinding.applyTypeThird.setSelected(false);
                }
            }
        });
        mDataBinding.applyTypeSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                // 单选控制
                if (view.isSelected()) {
                    mDataBinding.applyTypeFirst.setSelected(false);
                    mDataBinding.applyTypeThird.setSelected(false);
                }
            }
        });
        mDataBinding.applyTypeThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                // 单选控制
                if (view.isSelected()) {
                    mDataBinding.applyTypeFirst.setSelected(false);
                    mDataBinding.applyTypeSecond.setSelected(false);
                }
            }
        });
        mPresenter.getInitData();

    }

    /**
     * 设置申请类型
     *
     * @param typeList
     */
    @Override
    public void setInitData(List<PropertyParkPayTypeBean> typeList) {
        this.typeList = typeList;
        mDataBinding.applyTypeFirst.setText(typeList.get(0).getDistinguishName());
        mDataBinding.applyTypeSecond.setText(typeList.get(1).getDistinguishName());
        mDataBinding.applyTypeThird.setText(typeList.get(2).getDistinguishName());
    }

    @Override
    public PropertyParkPayBean getBean() {
        return mDataBinding.getParkPayBean();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_parkpay_activity_layout;
    }

    @Override
    public Map<String, String> getImageMap() {
        return imageMap;
    }
}
