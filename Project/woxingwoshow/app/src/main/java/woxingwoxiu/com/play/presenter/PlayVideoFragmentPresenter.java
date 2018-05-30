package woxingwoxiu.com.play.presenter;

import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.message.activity.FansHomeActivity;
import woxingwoxiu.com.play.bean.CommentBean;
import woxingwoxiu.com.play.contract.PlayVideoFragmentContract;
import woxingwoxiu.com.play.model.PlayVideoFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/17
 * time      17:25
 */
public class PlayVideoFragmentPresenter extends BasePresenter<PlayVideoFragmentContract.View> implements PlayVideoFragmentContract.Presenter {
    PlayVideoFragmentModel mModel;
    List<CommentBean> mCommentList = new ArrayList<>();
    @Override
    public void attachView(PlayVideoFragmentContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new PlayVideoFragmentModel();
    }

    @Override
    public void onBackClick() {
        getView().closeActivity();
    }

    @Override
    public void onThumbDownClick() {

    }

    @Override
    public void onMoreClick() {
        getView().showShareDialog();
    }

    @Override
    public void onHeadClick() {
        getView().actionStartActivity(FansHomeActivity.class);
    }

    @Override
    public void onFollowClick() {

    }

    @Override
    public void onMusicClick() {

    }

    @Override
    public void onSaySthClick() {
        getView().showSaySthDialog();
    }

    @Override
    public void onCommentClick() {
        getView().showCommentDialog();
    }

    @Override
    public void onThumbUpClick() {

    }

    @Override
    public void onShareClick() {
        getView().showShareDialog();
    }

    @Override
    public void loadCommentData(String videoId) {
//        CommentBean bean = new CommentBean();
//        mCommentList.add(bean);
//        mCommentList.add(bean);
//        mCommentList.add(bean);
//        mCommentList.add(bean);
//        mCommentList.add(bean);
//        mCommentList.add(bean);
//        mCommentList.add(bean);

        mModel.getComments(videoId, new BaseCallback<List<CommentBean>>() {
                    @Override
                    public void onSuccess(List<CommentBean> data) {
                        getView().loadCommentData(data);
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().showToast(msg);
                    }
                }

        );

    }

    @Override
    public void addComment(String videoId, String memberId, int commentId, String content) {
        mModel.addComment(videoId, memberId, commentId, content, new BaseCallback<List<CommentBean>>() {
            @Override
            public void onSuccess(List<CommentBean> data) {
                getView().showToast("发表评论成功");
            }

            @Override
            public void onFail(String msg) {
                getView().showToast("发表评论失败");
            }
        });
    }
}
