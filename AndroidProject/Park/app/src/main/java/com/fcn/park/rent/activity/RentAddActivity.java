package com.fcn.park.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.RentActivityLayoutBinding;
import com.fcn.park.rent.bean.ImageInfo;
import com.fcn.park.rent.bean.InitItems;
import com.fcn.park.rent.bean.RentAddBean;
import com.fcn.park.rent.bean.RentHouseEditBean;
import com.fcn.park.rent.bean.RentInitBean;
import com.fcn.park.rent.contract.RentAddContract;
import com.fcn.park.rent.presenter.RentAddPresenter;
import java.util.ArrayList;
import java.util.List;

public class RentAddActivity extends BaseActivity<RentActivityLayoutBinding,
        RentAddContract.View, RentAddPresenter> implements RentAddContract.View {

    private SinglePictureSelectPopWindow mSelectPopWindow;
    private int idTmp;
    private List<ImageInfo> imageList= new ArrayList<ImageInfo>();
    private List<String> rentTypeData;
    private List<String> statusTypeData;
    private List<InitItems> statusItems;
    private List<InitItems> rentTypeItems;
    private String renttype;
    private String status;
    private String houseId;
    private RentAddBean rentAddBean;

    public RentAddActivity() {
    }

    /**
     * 房屋编辑画面初始化数据设定
     */
    @Override
    protected void initViews() {
        String id = getIntent().getStringExtra("houseId");
        //添加的场合
        if(id==null||"".equals(id)){
            rentAddBean = new RentAddBean();
            mPresenter.getInitData();
            imageList.add(new ImageInfo("","",false));
            imageList.add(new ImageInfo("","",false));
            imageList.add(new ImageInfo("","",false));
            imageList.add(new ImageInfo("","",false));
            mDataBinding.setPresenter(mPresenter);
            mDataBinding.setRentBean(rentAddBean);
        }else{
            //编辑的场合
            houseId = id;
            mPresenter.loadEditInitData();
        }
        //图片选择功能添加
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    /**
     * 编辑的场合页面初始化数据绑定
     * @param rentHouseEditBean
     */
    @Override
    public void setEditInitData(RentHouseEditBean rentHouseEditBean) {
        rentAddBean = rentHouseEditBean.getRentAddBean();

        //房屋类型下拉列表设定
        rentTypeItems = rentHouseEditBean.getRentInitBean().getHoustTypeList();
        rentTypeData = new ArrayList<>();
        for (InitItems initItem : rentTypeItems){
            rentTypeData.add(initItem.getDistinguish_name());
        }
        ArrayAdapter rentAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,rentTypeData);
        Spinner rentSpinner = (Spinner)findViewById(R.id.houstTypeEdit);
        rentSpinner.setAdapter(rentAdapter);
        //设置房屋类型显示
        int position = rentAdapter.getPosition(rentAddBean.getHouse_type());   //根据该选项获取位置
        rentSpinner.setSelection(position);    //根据该选项的位置设置该选项为spinner默认值

        rentSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                renttype = rentTypeItems.get(arg2).getId();
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //状态下拉列表设定
        statusTypeData = new ArrayList<>();
        statusItems = rentHouseEditBean.getRentInitBean().getStatusList();
        for (InitItems initItem : statusItems){
            statusTypeData.add(initItem.getDistinguish_name());
        }
        ArrayAdapter statusAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,statusTypeData);
        Spinner statusSpinner = (Spinner)findViewById(R.id.statusEdit);
        statusSpinner.setAdapter(statusAdapter);
        //设置房屋状态显示
        int positionstatus = statusAdapter.getPosition(rentAddBean.getHouse_status());   //根据该选项获取位置
        statusSpinner.setSelection(positionstatus);    //根据该选项的位置设置该选项为spinner默认值
        statusSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                status = statusItems.get(arg2).getId();
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //图片编辑显示
        List<ImageInfo> imageListload = rentHouseEditBean.getImageList();
        for(int i=0;i<4;i++){
            //图片不足的时候处理
            if(i>= imageListload.size()){
                imageList.add(new ImageInfo("","",false));
            }else{
                imageList.add(new ImageInfo(imageListload.get(i).getImageID(),imageListload.get(i).getImagePath(),false));
                switch(i+1){
                    case 1:Glide.with(this).load(ApiService.IMAGE_BASE+imageListload.get(i).getImagePath()).into(mDataBinding.picture1);
                            mDataBinding.picture2.setVisibility(View.VISIBLE);
                            break;
                    case 2:Glide.with(this).load(ApiService.IMAGE_BASE+imageListload.get(i).getImagePath()).into(mDataBinding.picture2);
                            mDataBinding.picture3.setVisibility(View.VISIBLE);
                            break;
                    case 3:Glide.with(this).load(ApiService.IMAGE_BASE+imageListload.get(i).getImagePath()).into(mDataBinding.picture3);
                            mDataBinding.picture4.setVisibility(View.VISIBLE);
                            break;
                    case 4:Glide.with(this).load(ApiService.IMAGE_BASE+imageListload.get(i).getImagePath()).into(mDataBinding.picture4);
                            break;
                }
            }
        }
        mDataBinding.setRentBean(rentAddBean);
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    public String getHouseId() {
        return houseId;
    }

    /**
     * 添加房屋的场合页面初始化数据绑定
     * @param rentInitBean
     */
    @Override
    public void setAddInitData(RentInitBean rentInitBean) {
        //房屋类型下拉列表设定
        rentTypeItems = rentInitBean.getHoustTypeList();
        rentTypeData = new ArrayList<>();
        for (InitItems initItem : rentTypeItems){
            rentTypeData.add(initItem.getDistinguish_name());
        }
        ArrayAdapter rentAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,rentTypeData);
        Spinner rentSpinner = (Spinner)findViewById(R.id.houstTypeEdit);
        rentSpinner.setAdapter(rentAdapter);
        rentSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                renttype = rentTypeItems.get(arg2).getId();
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //状态下拉列表设定
        statusTypeData = new ArrayList<>();
        statusItems = rentInitBean.getStatusList();
        for (InitItems initItem : statusItems){
            statusTypeData.add(initItem.getDistinguish_name());
        }
        ArrayAdapter statusAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,statusTypeData);
        Spinner statusSpinner = (Spinner)findViewById(R.id.statusEdit);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                status = statusItems.get(arg2).getId();
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    protected RentAddPresenter createPresenter() {
        return new RentAddPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.rent_activity_layout;
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText("房屋信息编辑");
    }


    /**
     * 图片选择后显示和存储
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
            Glide.with(this).load(imageUrl).into((ImageView)findViewById(idTmp));
            //图片存储
            switch(idTmp){
                case R.id.picture1:
                    imageList.get(0).setImageUploadID("picture1");
                    imageList.get(0).setImagePath(imageUrl);
                    imageList.get(0).changeFlg = true;
                    mDataBinding.picture2.setVisibility(View.VISIBLE);
                    break;
                case R.id.picture2:
                    imageList.get(1).setImageUploadID("picture2");
                    imageList.get(1).setImagePath(imageUrl);
                    imageList.get(1).setChangeFlg(true);
                    mDataBinding.picture3.setVisibility(View.VISIBLE);
                    break;
                case R.id.picture3:
                    imageList.get(2).setImageUploadID("picture3");
                    imageList.get(2).setImagePath(imageUrl);
                    imageList.get(2).setChangeFlg(true);
                    mDataBinding.picture4.setVisibility(View.VISIBLE);
                    break;
                case R.id.picture4:
                    imageList.get(3).setImageUploadID("picture4");
                    imageList.get(3).setImagePath(imageUrl);
                    imageList.get(3).setChangeFlg(true);
                    break;
            }
        }
    }

    /**
     * 获取页面输入数据
     * @return
     */
    @Override
    public RentAddBean getBean() {
        return rentAddBean;
    }

    /**
     * 获取图片信息
     * @return
     */
    @Override
    public List<ImageInfo> getImageMap() {
        return imageList;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 第1张图片选择
     */
    @Override
    public void imageSelect1() {
        idTmp = R.id.picture1;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());

    }

    /**
     * 第2张图片选择
     */
    @Override
    public void imageSelect2() {
        idTmp = R.id.picture2;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());

    }

    /**
     * 第3张图片选择
     */
    @Override
    public void imageSelect3() {
        idTmp = R.id.picture3;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    /**
     * 第4张图片选择
     */
    @Override
    public void imageSelect4() {
        idTmp = R.id.picture4;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    public String getHouseStatus() {
        return status;
    }

    @Override
    public String getHouseType() {
        return renttype;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
