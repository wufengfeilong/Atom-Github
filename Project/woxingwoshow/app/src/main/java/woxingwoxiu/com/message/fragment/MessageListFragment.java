package woxingwoxiu.com.message.fragment;

import android.widget.ListView;
import com.liaoinstan.springview.container.DefaultHeader;
import java.util.ArrayList;
import java.util.List;
import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.databinding.FragmentMessageListBinding;
import woxingwoxiu.com.message.activity.FansHomeActivity;
import woxingwoxiu.com.message.adapter.MessageListAdapter;
import woxingwoxiu.com.message.bean.MessageListBean;
import woxingwoxiu.com.message.contract.MessageListContract;
import woxingwoxiu.com.message.presenter.MessageListFragmentPresenter;

/**
 */
public class MessageListFragment extends BaseFragment<FragmentMessageListBinding, MessageListFragmentPresenter> implements MessageListContract.View {
    private ListView listView;
    public List<MessageListBean.Result> resultList = new ArrayList<>();


    @Override
    protected MessageListFragmentPresenter createPresenter() {
        return new MessageListFragmentPresenter();
    }


    @Override
    public void onRefresh() {
       resultList.clear();
        initData2();
        MessageListAdapter adapter = new MessageListAdapter(mContext, resultList);
       listView = mDataBinding.messageList;
        listView.setAdapter(adapter);
        mDataBinding.messageListSpringView.onFinishFreshAndLoad();
    }

    @Override
    public void onLoadmore() {

    }

    @Override
    protected void initViews() {
            resultList.clear();
            mDataBinding.messageListSpringView.setListener(this);
            mDataBinding.messageListSpringView.setHeader(new DefaultHeader(mContext));
            initData();
            MessageListAdapter adapter = new MessageListAdapter(mContext, resultList);
            listView = mDataBinding.messageList;
            listView.setAdapter(adapter);
    }
    public void initData2(){
        MessageListBean.Result bean2 = new MessageListBean.Result();
        bean2.setAssistantContent("我型我秀小助手就是我恩");
        bean2.setPassTime("1小時前");
        bean2.setType(0);
        resultList.add(bean2);
        MessageListBean.Result bean1 = new MessageListBean.Result();
        bean1.setType(2);
        bean1.setUserName("小马");
        bean1.setPassTime("100小时前");
        bean1.setActionNumber("2131");
        bean1.setFansNumber("3221");
        resultList.add(bean1);

    }

    public void initData() {
        MessageListBean.Result bean0 = new MessageListBean.Result();
        bean0.setType(1);
        bean0.setUserName("库克");
        bean0.setPassTime("2小时前");
        resultList.add(bean0);
        MessageListBean.Result bean = new MessageListBean.Result();
        bean.setType(1);
        bean.setUserName("马云");
        bean.setPassTime("1天前");
        resultList.add(bean);
        MessageListBean.Result bean1 = new MessageListBean.Result();
        bean1.setType(2);
        bean1.setUserName("马化腾");
        bean1.setPassTime("10小时前");
        bean1.setActionNumber("120");
        bean1.setFansNumber("700");
        resultList.add(bean1);
        MessageListBean.Result bean3 = new MessageListBean.Result();
        bean3.setType(2);
        bean3.setUserName("雷军");
        bean3.setPassTime("3分钟前");
        bean3.setActionNumber("300");
        bean3.setFansNumber("800");
        resultList.add(bean3);
        MessageListBean.Result bean2 = new MessageListBean.Result();
        bean2.setAssistantContent("我是一个小助手，哈哈哈哈");
        bean2.setPassTime("3小時前");
        bean2.setType(0);
        resultList.add(bean2);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_message_list;
    }
}
