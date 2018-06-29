package com.fcn.park.property.activity;

import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.PropertyRepairActivityLayoutBinding;
import com.fcn.park.property.contract.PropertyRepairContract;
import com.fcn.park.property.presenter.PropertyRepairPresenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 报修追加
 */
public class PropertyRepairActivity extends BaseActivity<PropertyRepairActivityLayoutBinding, PropertyRepairContract.View, PropertyRepairPresenter> implements PropertyRepairContract.View {
    private SinglePictureSelectPopWindow mSelectPopWindow;
    private int addpic;
    public String upImageUrl;
    private Map<String, String> imageMap = new HashMap<String, String>();


    @Override
    protected PropertyRepairPresenter createPresenter() {
        return new PropertyRepairPresenter();
    }

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.property_repair_title);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);
            upImageUrl = imageUrl;
            Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(addpic));

            switch (addpic) {
                case R.id.repair_add_picture1:
                    imageMap.put("repairPicture1", imageUrl);
                    break;
                case R.id.repair_add_picture2:
                    imageMap.put("repairPicture2", imageUrl);
                    break;
                case R.id.repair_add_picture3:
                    imageMap.put("repairPicture3", imageUrl);
                    break;
            }
        }
    }

    @Override
    public void openSelectPhotoPop1(View view) {
        addpic = R.id.repair_add_picture1;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    public void openSelectPhotoPop2(View view) {
        addpic = R.id.repair_add_picture2;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    public void openSelectPhotoPop3(View view) {
        addpic = R.id.repair_add_picture3;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
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
        imageMap.put("repairPicture1", null);
        imageMap.put("repairPicture2", null);
        imageMap.put("repairPicture3", null);
        saveCamera();
    }

    @Override
    public Map<String, String> getImageMap() {
        return imageMap;
    }

    public void saveCamera() {
        //解决调用摄像机闪退
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_repair_activity_layout;
    }

    @Override
    public String getRepairName() {
        return mDataBinding.repairName.getInputText().toString().trim();
    }

    @Override
    public String getRepairPhone() {
        return mDataBinding.repairPhone.getInputText().toString().trim();
    }

    ;

    @Override
    public String getRepairAddress() {
        return mDataBinding.repairAddress.getInputText().toString().trim();
    }

    ;

    @Override
    public String getRepairContent() {
        return mDataBinding.repairContent.getText().toString().trim();
    }

    ;
}
