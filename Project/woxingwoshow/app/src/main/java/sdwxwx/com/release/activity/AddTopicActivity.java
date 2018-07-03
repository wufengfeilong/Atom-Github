package sdwxwx.com.release.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.BR;
import sdwxwx.com.R;
import sdwxwx.com.adapter.SimpleBindingAdapter;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.AddTopicActivityBinding;
import sdwxwx.com.recycler_listener.OnRecyclerItemClickListener;
import sdwxwx.com.release.contract.AddTopicContract;
import sdwxwx.com.release.presenter.AddTopicPresenter;

public class AddTopicActivity extends BaseActivity<AddTopicActivityBinding, AddTopicPresenter>
        implements AddTopicContract.View {

    private final String TAG = "AddTopicActivity";

    // 数据源
    private List<TopicInfoBean> mTopicListData = new ArrayList<>();
    // 记录请求批次
    private String mPageFlag = Constant.REQUEST_PAGE;

    private SimpleBindingAdapter<TopicInfoBean> mListAdapter;

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mDataBinding.setTitle(getString(R.string.release_add_topic_search_hint));

        // 初始化搜索编辑框领域
        initEditView();
        mListAdapter = new SimpleBindingAdapter<>(R.layout.add_topic_item, BR.bean);
        mDataBinding.topicList.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.topicList.setAdapter(mListAdapter);
        mListAdapter.setupData(mTopicListData);
        mDataBinding.topicList.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.topicList) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getPosition();
                Intent intent = new Intent();
                // 将选择的话题数据返回回去
                intent.putExtra(Constant.CHOOSE_TOPIC, true);
                intent.putExtra(Constant.RELEASE_ADD_TOPIC_CONTENT, (Serializable)mTopicListData.get(position));
                setResult(-1, intent);
                closeActivity();
            }
        });
    }

    @Override
    protected AddTopicPresenter createPresenter() {
        return new AddTopicPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_topic_activity;
    }

    @Override
    public void refreshListData(List<TopicInfoBean> refreshData) {
        Log.d(TAG, "refreshListData start");

        if (refreshData != null && refreshData.size() > 0) {
            Log.d(TAG, "refreshListData refreshData.size() = " + refreshData.size());
            // 加载成功
            mPageFlag = String.valueOf(Integer.parseInt(mPageFlag) + 1);
            mTopicListData.clear();
            mTopicListData.addAll(refreshData);
            mListAdapter.setupData(mTopicListData);
        } else {
//            mDataBinding.topicSearchEdit.setText();
            // 没有更多了
            showToast("没有更多了");
        }
        Log.d(TAG, "refreshListData end");
    }

    /**
     * 初始化搜索编辑框领域
     */
    private void initEditView() {
        Log.d(TAG, "initEditView start");

        mDataBinding.toolbar.topicSearchEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = mDataBinding.toolbar.topicSearchEdit.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > mDataBinding.toolbar.topicSearchEdit.getWidth()
                        - mDataBinding.toolbar.topicSearchEdit.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    Log.d(TAG, "Edit onTouch 点击位置 右侧按钮");
                    // 清空输入内容
                    mDataBinding.toolbar.topicSearchEdit.setText("");
                }
                return false;

            }
        });

        // 设置输入内容的监听事件
        mDataBinding.toolbar.topicSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "Edit afterTextChanged 输入内容变化");

                if (TextUtils.isEmpty(editable)) {
                    Log.d(TAG, "Edit afterTextChanged 输入内容变化 editable = 空");
                    mDataBinding.notUseTopicLl.setVisibility(View.VISIBLE);
                    mDataBinding.topicList.setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "Edit afterTextChanged 输入内容变化 editable = " + editable.toString());
                    mDataBinding.notUseTopicLl.setVisibility(View.GONE);
                    mDataBinding.topicList.setVisibility(View.VISIBLE);
                }
            }
        });

        // 搜索
        mDataBinding.toolbar.topicSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 恢复搜索页
                    mPageFlag = Constant.REQUEST_PAGE;

                    String keyWords = v.getText().toString();
                    // utf-8编码进行URL编码（RFC 3986）
                    try {
                        keyWords = URLEncoder.encode(v.getText().toString(), Constant.ENCODE);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    mPresenter.getAddTopicList(keyWords, mPageFlag);
                    return true;
                }

                return false;
            }
        });

        Log.d(TAG, "initEditView end");
    }

    /**
     * 取消按钮点击事件
     * @param view 取消按钮
     */
    public void onCancelClick(View view) {
        Log.d(TAG, "toolbar onCancelClick 取消按钮点击事件");
        mPresenter.onCancelClick();
    }

    /**
     * 设置不使用话题
     */
    @Override
    public void setNoTopic() {
        Log.d(TAG, "选中不使用话题 设置不使用话题");
        Intent intent = new Intent();
        intent.putExtra(Constant.CHOOSE_TOPIC, false);
        setResult(-1, intent);
        finish();
    }

}