package com.fcn.park.home.advertisement.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.ActivityAdvertisementBinding;
import com.fcn.park.home.advertisement.contract.AdvertisementContract;
import com.fcn.park.home.advertisement.presenter.AdvertisementPresenter;

import java.io.File;

public class AdvertisementActivity extends BaseActivity<ActivityAdvertisementBinding, AdvertisementContract.View, AdvertisementPresenter>
        implements AdvertisementContract.View, AdapterView.OnItemSelectedListener {

    //拍照或选择图片的PopUp
    private SinglePictureSelectPopWindow mSelectPopWindow;

    //图片路径
    private String imageUrl;

    //套餐类型
    private int advertisementPayType;

    @Override
    protected void setupTitle() {
        setTitleText("租广告位");
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        //套餐类型选择
        String[] payTypes = getResources().getStringArray(R.array.advertisement_pay_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mContext, android.R.layout.simple_spinner_item, payTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDataBinding.spAdvertisementPayType.setAdapter(adapter);
        mDataBinding.spAdvertisementPayType.setOnItemSelectedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertisement;
    }

    @Override
    protected AdvertisementPresenter createPresenter() {
        return new AdvertisementPresenter();
    }

    /**
     * 打开选择图片或拍照的PopUp
     */
    @Override
    public void openSelectPhotoPop() {
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    /**
     * 获取上传图片路径
     * @return
     */
    @Override
    public String getPictureUrl() {
        return imageUrl;
    }

    /**
     * 获取广告内容
     */
    @Override
    public String getContent() {
        return mDataBinding.advertisementContent.getText().toString();
    }

    /**
     * 获取套餐类型
     * @return
     */
    @Override
    public int getAdvertisementPayType() {
        return advertisementPayType;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);

            Glide.with(mActivity).load(new File(imageUrl)).into(mDataBinding.advertisementPicture);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        mDataBinding.tvAdvertisementPayType.setText(((TextView) view).getText());
        advertisementPayType = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
