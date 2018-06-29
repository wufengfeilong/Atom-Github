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
import com.fcn.park.base.http.ApiService;
import com.fcn.park.base.widget.SinglePictureSelectPopWindow;
import com.fcn.park.databinding.ManagerPostNewsDetailEditBinding;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;
import com.fcn.park.manager.contract.ManagerPostNewsDetailEditContract;
import com.fcn.park.manager.presenter.ManagerPostNewsDetailEditPresenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理中心的新闻、公告、活动详情编辑功能用Activity
 */
public class ManagerPostNewsDetailEditActivity
        extends BaseActivity<ManagerPostNewsDetailEditBinding, ManagerPostNewsDetailEditContract.View, ManagerPostNewsDetailEditPresenter>
        implements ManagerPostNewsDetailEditContract.View {

    private String TAG = "=== ManagerPostNewsDetailEditActivity ===";

    // 定义新闻、公告、活动的缩略图Map，用来保存要上传服务器的图片文件
    private Map<String, String> imageMap = new HashMap<String, String>();
    private SinglePictureSelectPopWindow mSelectPopWindow;

    // 定义新闻、公告、活动的图片的资源id
    private int idTmp;

    // 定义新闻、公告、活动的Id
    private String newsId = "";
    // 定义新闻、公告、活动的标题
    private String newsTitle = "";
    // 定义新闻、公告、活动的来源
    private String newsSources = "";
    // 定义新闻、公告、活动的内容
    private String newsContent = "";
    // 定义新闻、公告、活动的缩略图
    private String newsThumbnail = "";

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_post_news_detail_edit;
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        if (getIntent().getStringExtra(Constant.NEWS_TYPE).equals(Constant.NEWS_TYPE_0)) { // 公告公示
            setTitleText(getString(R.string.manager_make_announcement_detail_edit));
        } else if (getIntent().getStringExtra(Constant.NEWS_TYPE).equals(Constant.NEWS_TYPE_1)) { // 园区新闻
            setTitleText(getString(R.string.manager_post_news_detail_edit));
        } else if (getIntent().getStringExtra(Constant.NEWS_TYPE).equals(Constant.NEWS_TYPE_2)) { // 园区活动
            setTitleText(getString(R.string.manager_publish_activities_detail_edit));
        }
        openTitleLeftView(true);
    }

    /**
     * 重写的方法，通过intent获取前画面传过来的值
     */
    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        newsId = intent.getStringExtra("newsId");
        newsTitle = intent.getStringExtra("newsTitle");
        newsSources = intent.getStringExtra("newsSources");
        newsContent = intent.getStringExtra("newsContent");
        newsThumbnail = intent.getStringExtra("newsThumbnail");
    }

    @Override
    protected void initViews() {
        mSelectPopWindow = new SinglePictureSelectPopWindow(this);
        mSelectPopWindow.createHelper();
        mSelectPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        // 初始化画面时，清空保存图片的Map
        imageMap.put("newsThumbnail", "");

        // 将前画面传过来的值保存到Bean中，通过setPostNewsDetailEditBean()方法设置到画面上
        ManagerPostNewsListBean.ListNewsBean newsDetailEditBean = new ManagerPostNewsListBean.ListNewsBean();
        newsDetailEditBean.setNewsId(newsId);
        newsDetailEditBean.setNewsTitle(newsTitle);
        newsDetailEditBean.setNewsSources(newsSources);
        newsDetailEditBean.setNewsContent(newsContent);

        if (newsThumbnail != null && !newsThumbnail.equals("")) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + newsThumbnail)
                    .error(R.drawable.ic_vector_post_news_add)
                    .into(mDataBinding.managerPostNewsDetailEditNewsImg);
        }
//        else {
//            LinearLayout newsImg = (LinearLayout) findViewById(R.id.manager_post_news_detail_edit_news_img_layout);
//            newsImg.setVisibility(View.GONE);
//        }
        mDataBinding.setPostNewsDetailEditBean(newsDetailEditBean);
        mDataBinding.setPostNewsDetailEditPresenter(mPresenter);
    }

    @Override
    protected ManagerPostNewsDetailEditPresenter createPresenter() {
        return new ManagerPostNewsDetailEditPresenter();
    }

    @Override
    public String getNewsId() {
        return newsId;
    }

    /**
     * 获取画面上输入的新闻、公告、活动的标题
     * @return
     */
    @Override
    public String getInputNewsTitle() {
        return mDataBinding.managerPostNewsDetailEditNewsTitle.getText().toString().trim();
    }

    /**
     * 获取画面上输入的新闻、公告、活动的内容
     * @return
     */
    @Override
    public String getInputNewsContent() {
        return mDataBinding.managerPostNewsDetailEditNewsContent.getText().toString().trim();
    }

    /**
     * 获取画面上输入的新闻、公告、活动的来源
     * @return
     */
    @Override
    public String getInputNewsSources() {
        return mDataBinding.managerPostNewsDetailEditNewsSource.getText().toString().trim();
    }

    /**
     * 判断画面上是否输入了新闻、公告、活动的标题
     * @return
     */
    @Override
    public boolean checkInputNewsTitleEmpty() {
        return checkInputEmpty(mDataBinding.managerPostNewsDetailEditNewsTitle.getText().toString().trim());
    }

    /**
     * 判断画面上是否输入了新闻、公告、活动的来源
     * @return
     */
    @Override
    public boolean checkInputNewsSourcesEmpty() {
        return checkInputEmpty(mDataBinding.managerPostNewsDetailEditNewsSource.getText().toString().trim());
    }

    /**
     * 判断画面上是否输入了新闻、公告、活动的内容
     * @return
     */
    @Override
    public boolean checkInputNewsContentEmpty() {
        return checkInputEmpty(mDataBinding.managerPostNewsDetailEditNewsContent.getText().toString().trim());
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

    /**
     * 初始化xml文件中定义的新闻、公告、活动的Bean
     * @return
     */
    @Override
    public ManagerPostNewsListBean.ListNewsBean getNewsDetailBean() {
        return mDataBinding.getPostNewsDetailEditBean();
    }

    @Override
    public Map<String, String> getNewsDetailThumbnail() {
        return imageMap;
    }

    /**
     * 点击显示的图片的点击事件
     *
     * @param view
     */
    public void updatePostNewsDetailImage(View view) {
        Log.d(TAG, "====== addPostNewsImg() Start =======");
        idTmp = R.id.manager_post_news_detail_edit_news_img;
        lightOff();
        mSelectPopWindow.showPopupWindow(mDataBinding.getRoot());
        Log.d(TAG, "====== addPostNewsImg() End =======");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 获取到显示在画面上的图片的路径
            String imageUrl = mSelectPopWindow.getSelectHelper().onActivityResult(requestCode, resultCode, data, null, false);
            Log.d(TAG, "====== onActivityResult() imageUrl = " + imageUrl);
            // 将图片显示到画面上
            Glide.with(mActivity).load(new File(imageUrl)).into((ImageView) findViewById(idTmp));
            // 将图片的路径值用键值对的形式保存到Map中
            imageMap.put("newsThumbnail", imageUrl);
        }
    }
}