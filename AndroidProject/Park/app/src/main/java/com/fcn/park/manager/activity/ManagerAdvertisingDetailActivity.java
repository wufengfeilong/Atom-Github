package com.fcn.park.manager.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.databinding.ManagerAdvertisingDetailBinding;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;
import com.fcn.park.manager.contract.ManagerAdvertisingContract;
import com.fcn.park.manager.presenter.ManagerAdvertisingPresenter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 已审核完了的广告位的详情信息
 */
public class ManagerAdvertisingDetailActivity
        extends BaseActivity<ManagerAdvertisingDetailBinding, ManagerAdvertisingContract.View, ManagerAdvertisingPresenter>
        implements ManagerAdvertisingContract.View {

    private String TAG = "=== ManagerAdvertisingDetailActivity ===";
    private String advertisingId = "";

    // 定义从服务器端获取的缩略图的路径
    private String imgPath = "";
    // 持有这个动画的引用，让他可以在动画执行中途取消
    private Animator mCurrentAnimator;

    private int mShortAnimationDuration;

    // 套餐一的名字与天数
    private String SET_TYPE1 = "套餐一";
    private int SET_TYPE1_DAYS = 30;// 一个月的天数
    // 套餐二的名字与天数
    private String SET_TYPE2 = "套餐二";
    private int SET_TYPE2_DAYS = 90;// 三个月的天数
    // 套餐三的名字与天数
    private String SET_TYPE3 = "套餐三";
    private int SET_TYPE3_DAYS = 365;// 一年的天数

    /**
     * 启动当前Activity
     *
     * @param context
     * @param id
     */
    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, ManagerAdvertisingDetailActivity.class);
        intent.putExtra("advertisingId", id);
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Log.d(TAG, "==== initFieldBeforeMethods() ====");
        Intent intent = getIntent();
        advertisingId = intent.getStringExtra("advertisingId");
        Log.d(TAG, "==== initFieldBeforeMethods()  advertisingId =" + advertisingId);
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_advertising_detail;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_advertising_position_management_check));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        Log.d(TAG, "=== initViews() mAdvertisingId = " + advertisingId);

        // 系统默认的短动画执行时间 200
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mDataBinding.setAdvertisingPresenter(mPresenter);
        mPresenter.getAdvertisingInfoById(advertisingId);
    }

    @Override
    protected ManagerAdvertisingPresenter createPresenter() {
        return new ManagerAdvertisingPresenter();
    }

    /**
     * 将后台取到的广告详情的数据Bean，显示到画面上
     *
     * @param bean
     */
    @Override
    public void showDataToView(ManagerAdvertisingApprovalBean bean) {

        // 如果审批状态为1（已审批）且付费状态为0（未付费）时，画面的“审批状况”显示“待付费”
        if (bean.getApprovalStatus().equals("1") && bean.getPayStatus() == 0) {
            bean.setApprovalStatus("待付费");
            // “开始日期”与“终止日期”都显示“-”
            bean.setPayTime("-");
            bean.setEndDays("-");
            // 不显示“拒绝理由”
            bean.setRefuseFlg(1);

            // 如果审批状态为1（已审批）且付费状态为1（已付费）时，画面的“审批状况”显示“已付费”
        } else if (bean.getApprovalStatus().equals("1") && bean.getPayStatus() == 1) {
            bean.setApprovalStatus("已付费");
            // 不显示“拒绝理由”
            bean.setRefuseFlg(1);
            // 计算剩余天数，并显示到“终止日期”项目中
            if (bean.getSetType().contains(SET_TYPE1)) {// 套餐一时，终止日期加上30天
                bean.setEndDays(getEndData(bean.getPayTime(), SET_TYPE1_DAYS));
            } else if (bean.getSetType().contains(SET_TYPE2)) {// 套餐二时，终止日期加上90天
                bean.setEndDays(getEndData(bean.getPayTime(), SET_TYPE2_DAYS));
            } else if (bean.getSetType().contains(SET_TYPE3)) {// 套餐三时，终止日期加上365天
                bean.setEndDays(getEndData(bean.getPayTime(), SET_TYPE3_DAYS));
            }

            // 如果审批状态为2（拒绝）时，画面的“审批状况”显示“拒绝”
        } else if (bean.getApprovalStatus().equals("2")) {
            bean.setRefuseFlg(0);
            bean.setApprovalStatus(getString(R.string.manager_advertising_check_refuse));
            // “开始日期”与“终止日期”都显示“-”
            bean.setPayTime("-");
            bean.setEndDays("-");
        }

        String advertisingThumbnail = bean.getAdvertisingImg();
        if (advertisingThumbnail != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + advertisingThumbnail)
                    .error(R.drawable.ic_vector_default_none_img)
                    .into(mDataBinding.managerAdvertisingThumbnail);
            imgPath = ApiService.IMAGE_BASE + advertisingThumbnail;
        }
        mDataBinding.setAdvertisingBean(bean);
    }

    /**
     * 在一个已经日期的基础上加上一个天数，得到相应的日期
     *
     * @param startDay：已知的日期
     * @param addDays：需要加上的天数
     * @return
     */
    private String getEndData(String startDay, int addDays) {
        String endDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 得到开始的日期
            Date date = sdf.parse(startDay);
            Calendar cl = Calendar.getInstance();
            cl.setTime(date);
            // 开始的日期基础上加指定的天数
            cl.add(Calendar.DATE, addDays);
            // 将加完后的天数格式化成日期
            endDay = sdf.format(cl.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDay;
    }

    /**
     * 点击广告图片时，显示图片放大的图片
     * @param view
     */
    public void enlargeImg(View view) {
        zoomImageFromThumb(findViewById(R.id.manager_advertising_thumbnail), imgPath);
    }

    /**
     * 图片放大缩小的处理
     *
     * @param thumbView：点击的图片的View
     * @param imagePath：图片的路径
     */
    private void zoomImageFromThumb(final View thumbView, String imagePath) {
        // 如果有动画正在运行，取消这个动画
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // 加载显示大图的ImageView
        final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
        Glide.with(this)
                .load(imagePath)
                .error(R.drawable.ic_vector_default_none_img)
                .into((ImageView) findViewById(R.id.expanded_image));

        // 计算初始小图的边界位置和最终大图的边界位置。
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // 小图的边界就是小ImageView的边界，大图的边界因为是铺满全屏的，所以就是整个布局的边界。
        // 然后根据偏移量得到正确的坐标。
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.manager_advertising_thumbnail).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // 计算初始的缩放比例。最终的缩放比例为1。并调整缩放方向，使看着协调。
        float startScale = 0;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // 横向缩放
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // 竖向缩放
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // 隐藏画面上所有的内容，只显示大图
        findViewById(R.id.manager_advertising_data_layout).setVisibility(View.GONE);
        expandedImageView.setVisibility(View.VISIBLE);

        // 将大图的缩放中心点移到左上角。默认是从中心缩放
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        //对大图进行缩放动画
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // 点击大图时，反向缩放大图，然后隐藏大图，显示画面上的内容
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.manager_advertising_data_layout).setVisibility(View.VISIBLE);
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }
}