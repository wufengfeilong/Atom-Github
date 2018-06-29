package com.fcn.park.manager.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.ManagerPostNewsAddBinding;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;
import com.fcn.park.manager.contract.ManagerPostNewsAddContract;
import com.fcn.park.manager.presenter.ManagerPostNewsAddPresenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 新闻、公告、活动发布功能用Activity
 */
public class ManagerPostNewsAddActivity
        extends BaseActivity<ManagerPostNewsAddBinding, ManagerPostNewsAddContract.View, ManagerPostNewsAddPresenter>
        implements ManagerPostNewsAddContract.View {

    private String TAG = "=== ManagerPostNewsAddActivity ===";
    private SinglePictureSelectPopWindow mSelectPopWindow;
    private String newsType = "";

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_post_news_add;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        newsType = getIntent().getStringExtra(Constant.NEWS_TYPE);
        if (newsType.equals(Constant.NEWS_TYPE_0)) {// 公告公示
            setTitleText(getString(R.string.manager_make_announcement));
        } else if (newsType.equals(Constant.NEWS_TYPE_1)) {// 园区新闻
            setTitleText(getString(R.string.manager_post_news));
        } else if (newsType.equals(Constant.NEWS_TYPE_2)) {// 园区活动
            setTitleText(getString(R.string.manager_publish_activities));
        }
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {

        ManagerPostNewsListBean.ListNewsBean newsListBean = new ManagerPostNewsListBean.ListNewsBean();
        mDataBinding.setPostNewsAddBean(newsListBean);
        mDataBinding.setPostNewsAddPresenter(mPresenter);

        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        imageMap.put("newsThumbnail", "");
    }

    @Override
    protected ManagerPostNewsAddPresenter createPresenter() {
        return new ManagerPostNewsAddPresenter();
    }

    @Override
    public String getInputNewsTitle() {
        return mDataBinding.managerPostNewsAddNewsTitle.getText().toString().trim();
    }

    @Override
    public String getInputNewsContent() {
        return mDataBinding.managerPostNewsAddNewsContent.getText().toString().trim();
    }

    @Override
    public String getInputNewsSources() {
        return mDataBinding.managerPostNewsAddNewsSource.getText().toString().trim();
    }

    @Override
    public String getNewType() {
        return newsType;
    }

    @Override
    public boolean checkInputNewsTitleEmpty() {
        return checkInputEmpty(mDataBinding.managerPostNewsAddNewsTitle.getText().toString().trim());
    }

    @Override
    public boolean checkInputNewsSourcesEmpty() {
        return checkInputEmpty(mDataBinding.managerPostNewsAddNewsSource.getText().toString().trim());
    }

    @Override
    public boolean checkInputNewsContentEmpty() {
        return checkInputEmpty(mDataBinding.managerPostNewsAddNewsContent.getText().toString().trim());
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
    public ManagerPostNewsListBean.ListNewsBean getNewsBean() {
        return mDataBinding.getPostNewsAddBean();
    }

    private Map<String, String> imageMap = new HashMap<String, String>();

    @Override
    public Map<String, String> getNewsThumbnail() {
        return imageMap;
    }

    private int idTmp;

    /**
     * 添加图片按钮的点击事件
     *
     * @param view
     */
    public void addPostNewsImg(View view) {
        idTmp = R.id.manager_post_news_add_news_img;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);
            Log.d(TAG, "====== onActivityResult() imageUrl = " + imageUrl);
            Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(idTmp));
            imageMap.put("newsThumbnail", imageUrl);
        }
    }
}
