package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.ManagerParkIntroductionBinding;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;
import com.fcn.park.manager.contract.ManagerParkIntroductionContract;
import com.fcn.park.manager.presenter.ManagerParkIntroductionPresenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理中心的园区简介功能画面
 */
public class ManagerParkIntroductionActivity
        extends BaseActivity<ManagerParkIntroductionBinding, ManagerParkIntroductionContract.View, ManagerParkIntroductionPresenter>
        implements ManagerParkIntroductionContract.View {

    private String TAG = "=== ManagerParkIntroductionActivity ===";

    private int idImg;
    private Map<String, String> imageMap = new HashMap<String, String>();
    private SinglePictureSelectPopWindow mSelectPopWindow;

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_park_introduction_detail_edit));
        openTitleLeftView(true);
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_park_introduction;
    }

    @Override
    protected void initViews() {

        ManagerParkIntroductionBean parkBean = new ManagerParkIntroductionBean();
        mDataBinding.setParkIntroductionBean(parkBean);
        mDataBinding.setParkIntroductionPresenter(mPresenter);
        SharedPreferences preferences= getSharedPreferences("park_info", Context.MODE_PRIVATE);
        String park_id = preferences.getString("park_id", "");//第二个参数表示如果没有找到，则使用该默认值
        mPresenter.loadInfo(park_id);
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        imageMap.put("img1", "");
        imageMap.put("img2", "");
        imageMap.put("img3", "");
    }

    @Override
    protected ManagerParkIntroductionPresenter createPresenter() {
        return new ManagerParkIntroductionPresenter();
    }

    String parkId = null;
    String parkName = null;
    String parkContent = null;
    String parkThumbnail1 = null;
    String parkThumbnail2 = null;
    String parkThumbnail3 = null;
    String parkAddress = null;
    String parkTelephone = null;

    @Override
    public void updateInfo(ManagerParkIntroductionBean bean) {
        if (bean != null) {
            parkId = bean.getParkId();
            parkName = bean.getParkName();
            parkContent = bean.getParkContent();
            parkThumbnail1 = bean.getParkThumbnail1();
            if (parkThumbnail1 != null) {
                Glide.with(this)
                        .load(ApiService.IMAGE_BASE + parkThumbnail1)
                        .error(R.drawable.ic_vector_post_news_add)
                        .into(mDataBinding.managerParkIntroductionThumbnail1);
            }
            parkThumbnail2 = bean.getParkThumbnail2();
            if (parkThumbnail2 != null) {
                if (mDataBinding.managerParkIntroductionThumbnail2.getVisibility() == View.GONE) {
                    mDataBinding.managerParkIntroductionThumbnail2.setVisibility(View.VISIBLE);
                }
                Glide.with(this)
                        .load(ApiService.IMAGE_BASE + parkThumbnail2)
                        .error(R.drawable.ic_vector_post_news_add)
                        .into(mDataBinding.managerParkIntroductionThumbnail2);
            }
            parkThumbnail3 = bean.getParkThumbnail3();
            if (parkThumbnail3 != null) {
                if (mDataBinding.managerParkIntroductionThumbnail3.getVisibility() == View.GONE) {
                    mDataBinding.managerParkIntroductionThumbnail3.setVisibility(View.VISIBLE);
                }
                Glide.with(this)
                        .load(ApiService.IMAGE_BASE + parkThumbnail3)
                        .error(R.drawable.ic_vector_post_news_add)
                        .into(mDataBinding.managerParkIntroductionThumbnail3);
            }
            parkAddress = bean.getParkAddress();
            parkTelephone = bean.getParkTelephone();
            Log.d(TAG, "======= updateInfo:" + "parkId = " + parkId + ", parkName = " + parkName);
            Log.d(TAG, "======= updateInfo:" + "parkThumbnail1 = " + parkThumbnail1 + ", parkThumbnail2 = " + parkThumbnail2 + ", parkThumbnail3 = " + parkThumbnail3);
            Log.d(TAG, "======= updateInfo:" + "parkContent = " + parkContent);
            Log.d(TAG, "======= updateInfo:" + "parkAddress = " + parkAddress);
            Log.d(TAG, "======= updateInfo:" + "parkTelephone = " + parkTelephone);
            mDataBinding.setParkIntroductionBean(bean);
        }
    }

    @Override
    public String getInputParkId() {
        return parkId;
    }

    @Override
    public String getInputParkName() {
        return mDataBinding.managerParkIntroductionAddTitle.getText().toString().trim();
    }

    @Override
    public String getInputParkContent() {
        return mDataBinding.managerParkIntroductionAddContent.getText().toString().trim();
    }

    @Override
    public String getInputParkAddress() {
        return mDataBinding.managerParkIntroductionAddress.getText().toString().trim();
    }

    @Override
    public String getInputParkTelephone() {
        return mDataBinding.managerParkIntroductionTelephone.getText().toString().trim();
    }

    @Override
    public boolean checkInputParkNameEmpty() {
        return checkInputEmpty(mDataBinding.managerParkIntroductionAddTitle.getText().toString().trim());
    }

    @Override
    public boolean checkInputParkContentEmpty() {
        return checkInputEmpty(mDataBinding.managerParkIntroductionAddContent.getText().toString().trim());
    }

    @Override
    public boolean checkInputParkAddressEmpty() {
        return checkInputEmpty(mDataBinding.managerParkIntroductionAddress.getText().toString().trim());
    }

    @Override
    public boolean checkInputParkTelephoneEmpty() {
        return checkInputEmpty(mDataBinding.managerParkIntroductionTelephone.getText().toString().trim());
    }

    /**
     * 检查输入的内容是否为空
     *
     * @param text
     * @return
     */
    private boolean checkInputEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    @Override
    public void addParkImg1(View view) {
        Log.d(TAG, "==== addParkImg1() ===");
        idImg = R.id.manager_park_introduction_thumbnail1;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    public void addParkImg2(View view) {
        Log.d(TAG, "==== addParkImg2() ===");
        idImg = R.id.manager_park_introduction_thumbnail2;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    public void addParkImg3(View view) {
        Log.d(TAG, "==== addParkImg3() ===");
        idImg = R.id.manager_park_introduction_thumbnail3;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    public ManagerParkIntroductionBean getParkBean() {
        return mDataBinding.getParkIntroductionBean();
    }

    @Override
    public Map<String, String> getImageMap() {
        return imageMap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);

            switch (idImg) {
                case R.id.manager_park_introduction_thumbnail1:
                    imageMap.put("img1", imageUrl);
                    if (mDataBinding.managerParkIntroductionThumbnail2.getVisibility() == View.GONE) {
                        mDataBinding.managerParkIntroductionThumbnail2.setVisibility(View.VISIBLE);
                    }
                    Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(idImg));
                    break;
                case R.id.manager_park_introduction_thumbnail2:
                    imageMap.put("img2", imageUrl);
                    if (mDataBinding.managerParkIntroductionThumbnail3.getVisibility() == View.GONE) {
                        mDataBinding.managerParkIntroductionThumbnail3.setVisibility(View.VISIBLE);
                    }
                    Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(idImg));
                    break;
                case R.id.manager_park_introduction_thumbnail3:
                    imageMap.put("img3", imageUrl);
                    Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(idImg));
                    break;
            }
        }
    }
}
