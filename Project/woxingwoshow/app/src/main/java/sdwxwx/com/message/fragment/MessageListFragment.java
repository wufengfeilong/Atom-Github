package sdwxwx.com.message.fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.emay.sdk.util.json.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;

import java.util.ArrayList;
import java.util.List;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.FragmentMessageListBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.adapter.MessageListAdapter;
import sdwxwx.com.message.bean.MessageListBean;
import sdwxwx.com.message.contract.MessageListContract;
import sdwxwx.com.message.presenter.MessageListFragmentPresenter;
import sdwxwx.com.util.MsgDbHelper;

/**
 */
public class MessageListFragment extends BaseFragment<FragmentMessageListBinding, MessageListFragmentPresenter> implements MessageListContract.View {
    private ListView listView;
    public List<MessageListBean.Result> resultList = new ArrayList<>();
    public List<MessageListBean.Result> newList = new ArrayList<>();
    public MessageListAdapter adapter;
    /** 会员信息是否已经取得 */
    private boolean flag = false;
    /** 当前加载的页数 */
    private int page = 0;


    @Override
    protected MessageListFragmentPresenter createPresenter() {
        return new MessageListFragmentPresenter();
    }


    @Override
    public void onRefresh() {
        // 对页码进行清空
        page = 0;
        // 清空结果集
        resultList.clear();
        //加载最新的通知数据
        mPresenter.loadData();
    }

    @Override
    public void onLoadmore() {
        //加载更多的通知数据
        mPresenter.loadData();
    }
    /**
     * 画面初期化
     */
    @Override
    protected void initViews() {
        resultList.clear();
        mDataBinding.messageListSpringView.setListener(this);
        mDataBinding.messageListSpringView.setHeader(new DefaultHeader(mContext));
        mDataBinding.messageListSpringView.setFooter(new DefaultFooter(mContext));
        //加载最新的通知数据
        mPresenter.loadData();
    }

    /**
     * 把最新消息存储到文件中
     * 并且把存储的消息取出放到list中
     */
    @Override
    public void messageSaveIntoFile() {
        // 对页数进行自增操作
        page = page + 1;
        MsgDbHelper dbHelper = new MsgDbHelper(getView().getContext());
        //得到一个可读的数据库
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        // 读取数据库中的数据
        newList = dbHelper.getMsgList(db, page, LoginHelper.getInstance().getUserId());
        if (newList != null && newList.size() > 0) {
            resultList.addAll(newList);
        } else {
            // 提示已经没有更多了
            if (page > 1) {
                showToast(Constant.NO_MORE_MSG);
            }
        }
        if (resultList != null && resultList.size() > 0) {
            mDataBinding.noMessageLayout.setVisibility(View.GONE);
            mDataBinding.messageListSpringView.setVisibility(View.VISIBLE);
            adapter = new MessageListAdapter(mContext, this, resultList);
            listView = mDataBinding.messageList;
            listView.setAdapter(adapter);
            listItemListener();
        } else {
            mDataBinding.noMessageLayout.setVisibility(View.VISIBLE);
            mDataBinding.messageListSpringView.setVisibility(View.GONE);
        }
        hideLoading();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                    mDataBinding.messageListSpringView.onFinishFreshAndLoad();
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            // 对页码进行清空
            page = 0;
            resultList.clear();
            mPresenter.loadData();
        }
    }

    /**
     * 长押删除当前记录
     */
    public void listItemListener(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                builder.setMessage("确定删除?");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 更新数据库
                        MsgDbHelper dbHelper = new MsgDbHelper(getContext());
                        // 得到一个可写的数据库
                        SQLiteDatabase db =dbHelper.getWritableDatabase();
                        // 从数据库中删除此数据
                        dbHelper.deleteMsgById(db, resultList.get(position).getId());
                        // 从结果集中把数据删除
                        resultList.remove(position);
                        if (resultList == null || resultList.size() == 0) {
                            mDataBinding.noMessageLayout.setVisibility(View.VISIBLE);
                            mDataBinding.messageListSpringView.setVisibility(View.GONE);
                        } else {
                            mDataBinding.noMessageLayout.setVisibility(View.GONE);
                            mDataBinding.messageListSpringView.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );

                builder.create().show();
                return false;
            }
        });
    }
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_message_list;
    }
}
