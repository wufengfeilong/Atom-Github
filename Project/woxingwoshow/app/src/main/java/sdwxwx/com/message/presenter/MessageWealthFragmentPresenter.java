package sdwxwx.com.message.presenter;

import android.view.View;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.databinding.MessageFragmentWealthBinding;
import sdwxwx.com.me.bean.VideoWealthBean;
import sdwxwx.com.message.contract.MessageWealthFragmentContract;
import sdwxwx.com.message.model.MessageTeamAndWealthModel;

/**
 * Created by 860117066 on 2018/05/17.
 * 类描述：视频财富presenter
 */

public class MessageWealthFragmentPresenter
        extends BasePresenter<MessageWealthFragmentContract.View> implements MessageWealthFragmentContract.Presenter {
    MessageTeamAndWealthModel mModel;
    VideoWealthBean mVideoWealthBean;
    /** 前一画面传递的会员ID */
    private String memberId;
    private String FansHeadUrl;
    private MessageFragmentWealthBinding dataBinding;

    @Override
    public void loadListData(MessageFragmentWealthBinding mDataBinding) {
        this.dataBinding = mDataBinding;
        // 取得前一画面的会员ID
        memberId = getView().getMemberId();
        mModel.getWealthVideo(memberId, new BaseCallback<VideoWealthBean>() {
            @Override
            public void onSuccess(VideoWealthBean data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
                // 如果没有数据，则提示没有视频财富
                if (data.getHistory() == null || data.getHistory().size() == 0) {
                    dataBinding.videoNoWealth.setVisibility(View.VISIBLE);
                    dataBinding.videoNoWealth.setText("TA暂时没有视频财富");
                    dataBinding.wealthGradeTg.setVisibility(View.GONE);
                } else {
                    dataBinding.videoNoWealth.setVisibility(View.GONE);
                    dataBinding.wealthGradeTg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {
                dataBinding.videoNoWealth.setVisibility(View.VISIBLE);
                dataBinding.videoNoWealth.setText("TA暂时没有视频财富");
                dataBinding.wealthGradeTg.setVisibility(View.GONE);
            }
        });
    }


}
