package sdwxwx.com.message.presenter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.databinding.MessageMyTeamGradeFragmentBinding;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.message.contract.MessageMyTeamFragmentContract;
import sdwxwx.com.message.model.MessageTeamAndWealthModel;

/**
 * 类描述：我的团队一级二级列表
 */
public class MessageMyTeamFragmentPresenter extends BasePresenter<MessageMyTeamFragmentContract.View> implements MessageMyTeamFragmentContract.Presenter{
    MessageTeamAndWealthModel mModel;
    List<RecommendUserBean> mTeamList=new ArrayList<>();
    /** 前一画面传递的会员ID */
    private String memberId;
    private MessageMyTeamGradeFragmentBinding dataBinding;
    @Override
    public void loadListData(int type, MessageMyTeamGradeFragmentBinding mDataBinding) {
        this.dataBinding = mDataBinding;
        String level="1";
        if (type==0) {
            level ="1";
        }else{
             level="2";
        }
        memberId = getView().getMemberId();
        mModel.getMessageTeamList(memberId,level,new BaseCallback<List<RecommendUserBean>>() {
            @Override
            public void onSuccess(List<RecommendUserBean> data) {
                if (getView() == null) {
                    return;
                }
               getView().bindListData(data);
                dataBinding.teamGradeTg.setVisibility(View.VISIBLE);
                dataBinding.tempNoMember.setVisibility(View.GONE);
            }
            @Override
            public void onFail(String msg) {
                dataBinding.teamGradeTg.setVisibility(View.GONE);
                dataBinding.tempNoMember.setText("TA暂时没有下级会员");
                dataBinding.tempNoMember.setVisibility(View.VISIBLE);
            }
        });
    }


}
