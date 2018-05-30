package woxingwoxiu.com.message.presenter;

        import woxingwoxiu.com.base.BasePresenter;
        import woxingwoxiu.com.message.activity.FansHomeActivity;
        import woxingwoxiu.com.message.contract.MessageListContract;

/**
 * Created by 860117073 on 2018/5/11.
 */

public class MessageListFragmentPresenter extends BasePresenter<MessageListContract.View> implements MessageListContract.Presenter {
        public void toFansHome(){
                getView().actionStartActivity(FansHomeActivity.class);
        }


}
