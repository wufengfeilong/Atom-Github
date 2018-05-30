package woxingwoxiu.com.message.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.message.bean.MessageFriendFansListBean;
import woxingwoxiu.com.message.contract.MessageFriendFansListContract;

/**
 *
 * 类描述：好友粉丝列表
 */

public class MessageFriendFansListPresenter
        extends BasePresenter<MessageFriendFansListContract.View> implements MessageFriendFansListContract.Presenter{
     List<MessageFriendFansListBean> mList=new ArrayList<>();

    @Override
    public void loadListData() {
        mList.clear();
        MessageFriendFansListBean bean =new MessageFriendFansListBean();
        bean.setAvatar_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527683113740&di=23a3a33c2a00ffd4ef2912d87c28d661&imgtype=0&src=http%3A%2F%2Fkibey-fair.b0.upaiyun.com%2F2013%2F02%2F16%2F0d0a9f2bdeae151b3c52e982422cf641.jpg");
        bean.setSignature("我就是雷神，没错");
        bean.setIs_followed(true);
        mList.add(bean);
        bean =new MessageFriendFansListBean();
        bean.setAvatar_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527683222708&di=3fecc2eb0d944a53cf1b1a07cb6d85e1&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-10-10%2F014020559.jpg");
        bean.setNickname("洛基");
        bean.setSignature("我就是洛基，哈哈");
        bean.setIs_followed(false);
        mList.add(bean);
        bean =new MessageFriendFansListBean();
        bean.setAvatar_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527683222707&di=546ebce3bc387ff842e3715b0f3d0af3&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F500fd9f9d72a6059dcc8813c2a34349b033bba38.jpg");
        bean.setNickname("幻世");
        bean.setSignature("幻世就是我，恩额");
        bean.setIs_followed(true);
        mList.add(bean);
        bean =new MessageFriendFansListBean();
        bean.setAvatar_url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527683222707&di=26d9cdc607b0fa090b854cd3fb907a59&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201608%2F14%2F20160814183229_ueiUB.thumb.224_0.jpeg");
        bean.setNickname("超鲜水果庄园");
        bean.setSignature("芒果、苹果、香蕉大量销售");
        bean.setIs_followed(false);
        bean.setIs_certified(true);
        mList.add(bean);
        getView().bindListData(mList);
    }
    //返回按钮的点击事件
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }
}
