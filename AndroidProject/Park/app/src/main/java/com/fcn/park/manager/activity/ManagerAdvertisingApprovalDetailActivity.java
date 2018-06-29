package com.fcn.park.manager.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.databinding.ManagerAdvertisingApprovalDetailBinding;
import com.fcn.park.manager.bean.ManagerAdvertisingApprovalBean;
import com.fcn.park.manager.contract.ManagerAdvertisingApprovalContract;
import com.fcn.park.manager.presenter.ManagerAdvertisingApprovalPresenter;

/**
 * 广告位详情审批用Activity.
 */
public class ManagerAdvertisingApprovalDetailActivity
        extends BaseActivity<ManagerAdvertisingApprovalDetailBinding, ManagerAdvertisingApprovalContract.View, ManagerAdvertisingApprovalPresenter>
        implements ManagerAdvertisingApprovalContract.View{

    private String TAG = "=== ManagerAdvertisingApprovalDetailActivity ===";

    // 定义广告Id
    private String mAdvertisingId = "";
    // 定义申请广告位的用户的id
    private String userId = "";
    // 定义申请广告位的用户的广告套餐类型
    private String setType = "";
    // 定义申请广告位的用户应缴纳的广告费
    private String advertisingFee = "";
    // 定义申请广告位的用户被拒绝的理由
    private String rejectReason = "";
    // 定义从服务器端获取的缩略图的路径
    private String imgPath = "";
    // 持有这个动画的引用，让他可以在动画执行中途取消
    private Animator mCurrentAnimator;

    private int mShortAnimationDuration;

    /**
     * 启动广告位待审核画面
     * @param context
     * @param advertisingId
     */
    public static void actionStart(Context context, String advertisingId) {
        Intent intent = new Intent(context, ManagerAdvertisingApprovalDetailActivity.class);
        intent.putExtra("id", advertisingId);
        context.startActivity(intent);
    }

    /**
     * 从前画面传过来的Intent中取出广告Id
     */
    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        mAdvertisingId = intent.getStringExtra("id");
        Log.d(TAG, "==== initFieldBeforeMethods()  mAdvertisingId =" + mAdvertisingId);
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_advertising_approval_detail;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_advertising_position_management_check));
        openTitleLeftView(true);
    }

    /**
     * 画面初始化操作：获取当前画面要显示的数据并显示到画面上
     */
    @Override
    protected void initViews() {
        Log.d(TAG, "=== initViews() mAdvertisingId = " + mAdvertisingId);
        // 画面初始化时，禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 系统默认的短动画执行时间 200
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        // 将Presenter通过DataBinding与当前的View绑定
        mDataBinding.setAdvertisingApprovalPresenter(mPresenter);
        // 通过广告Id，获取广告的详情内容
        mPresenter.getAdvertisingInfoById(mAdvertisingId);
    }

    /**
     * 重写的方法，创建Presenter
     * @return
     */
    @Override
    protected ManagerAdvertisingApprovalPresenter createPresenter() {
        return new ManagerAdvertisingApprovalPresenter();
    }

    /**
     * 获取输入的拒绝的理由内容
     * @return
     */
    @Override
    public String getInputRefuseData() {
        rejectReason = mDataBinding.managerAdvertisingRefuseDataEt.getText().toString().trim();
        return rejectReason;
    }

    /**
     * 获取申请广告位的用户的Id
     * @return
     */
    @Override
    public String getUserId() {
        return userId;
    }

    /**
     * 获取申请广告位的用户需要缴纳广告费的标题
     * @return
     */
    @Override
    public String getMsgTitle() {
        return getResources().getString(R.string.manager_advertising_fee_msg_title);
    }

    /**
     * 获取申请广告位的用户需要缴纳广告费的说明内容
     * @return
     */
    @Override
    public String getMsgContent(boolean isOK) {
        String msgContent;
        if (isOK) {
            msgContent = getResources().getString(R.string.manager_advertising_fee_msg_content_OK);
            return String.format(msgContent, setType, advertisingFee);
        } else {
            msgContent = getResources().getString(R.string.manager_advertising_fee_msg_content_NG);
            return String.format(msgContent, rejectReason);
        }
    }

    /**
     * 将后台取到的广告详情的数据Bean，显示到画面上
     * @param bean
     */
    @Override
    public void showDataToView(ManagerAdvertisingApprovalBean bean) {
        mDataBinding.setAdvertisingApprovalBean(bean);
        String  advertisingThumbnail = bean.getAdvertisingImg();
        userId = bean.getUserId();
        setType = bean.getSetType();
        advertisingFee = bean.getAdvertisingFee();
        if (advertisingThumbnail != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + advertisingThumbnail) // 加载图片
                    .error(R.drawable.ic_vector_default_none_img) // 失败时，加载默认的图片
                    .into(mDataBinding.managerAdvertisingApprovalThumbnail);
            imgPath = ApiService.IMAGE_BASE + advertisingThumbnail;
        }
    }

    /**
     * 点击广告图片时，显示图片放大的图片
     * @param view
     */
    public void enlargeImg(View view) {
        zoomImageFromThumb(findViewById(R.id.manager_advertising_approval_thumbnail), imgPath);
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
        final ImageView expandedImageView = (ImageView) findViewById(R.id.approval_expanded_image);
        Glide.with(this)
                .load(imagePath)
                .error(R.drawable.ic_vector_default_none_img)
                .into((ImageView) findViewById(R.id.approval_expanded_image));

        // 计算初始小图的边界位置和最终大图的边界位置。
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // 小图的边界就是小ImageView的边界，大图的边界因为是铺满全屏的，所以就是整个布局的边界。
        // 然后根据偏移量得到正确的坐标。
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.manager_advertising_approval_thumbnail).getGlobalVisibleRect(finalBounds, globalOffset);
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
        findViewById(R.id.approval_advertising_data_layout).setVisibility(View.GONE);
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
                findViewById(R.id.approval_advertising_data_layout).setVisibility(View.VISIBLE);
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