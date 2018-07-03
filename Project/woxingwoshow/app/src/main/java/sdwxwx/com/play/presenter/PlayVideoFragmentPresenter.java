package sdwxwx.com.play.presenter;

import android.content.Intent;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.activity.FansHomeActivity;
import sdwxwx.com.play.bean.CommentBean;
import sdwxwx.com.play.contract.PlayVideoFragmentContract;
import sdwxwx.com.play.model.PlayVideoFragmentModel;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/17
 * time      17:25
 */
public class PlayVideoFragmentPresenter extends BasePresenter<PlayVideoFragmentContract.View> implements PlayVideoFragmentContract.Presenter {
    PlayVideoFragmentModel mModel;
//    List<CommentBean> mCommentList;
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
    public void onMoreClick() {
        getView().showShareDialog();
    }

    @Override
    public void onHeadClick() {
        if (!LoginHelper.getInstance().isOnline()) {
            getView().actionStartActivity(LoginActivity.class);
            return;
        }
        getView().paramStartActivity(FansHomeActivity.class,getView().getFollowId()+"");
    }

    @Override
    public void onFollowClick() {
        if (!LoginHelper.getInstance().isOnline()) {
            getView().actionStartActivity(LoginActivity.class);
            return;
        }
        mModel.followUser(getView().getContext(),LoginHelper.getInstance().getUserId(), getView().getFollowId()+"", new BaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (getView() == null) {
                    return;
                }
                getView().isFollowed();
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
            }
        });
    }

    @Override
    public void onMusicClick() {

    }

    @Override
    public void onSaySthClick() {
        if (!LoginHelper.getInstance().isOnline()) {
            getView().actionStartActivity(LoginActivity.class);
            return;
        }
        getView().showSaySthDialog();
    }

    @Override
    public void onCommentClick() {
        getView().showCommentDialog();
    }

    @Override
    public void onThumbUpClick() {
        if (!LoginHelper.getInstance().isOnline()) {
            getView().actionStartActivity(LoginActivity.class);
            return;
        }
        if (getView().getIsLiked()) {
            mModel.videoUnlike(LoginHelper.getInstance().getUserId(), getView().getVideoId(), new BaseCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    if (getView() == null) {
                        return;
                    }
                    sendFreshListBroad();
                    getView().showToast("取消点赞成功！");
                    getView().getThumbIv().setImageResource(R.drawable.thumb_up_no_selected);
                    getView().setIsLiked("0");
                    getView().setLikeCount(-1);
                    getView().getThumbTv().setText((Integer.valueOf(getView().getThumbTv().getText().toString())-1)+"");
                }

                @Override
                public void onFail(String msg) {
                    if (getView() == null) {
                        return;
                    }
                    getView().showToast(msg);
                }
            });
        } else {
            mModel.videoLike(LoginHelper.getInstance().getUserId(), getView().getVideoId(), new BaseCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    if (getView() == null) {
                        return;
                    }
                    sendFreshListBroad();
                    getView().showToast("点赞成功！");
                    getView().getThumbIv().setImageResource(R.drawable.video_liked);
                    getView().setIsLiked("1");
                    getView().setLikeCount(1);
                    getView().getThumbTv().setText((Integer.valueOf(getView().getThumbTv().getText().toString())+1)+"");
                }

                @Override
                public void onFail(String msg) {
                    if (getView() == null) {
                        return;
                    }
                    getView().showToast(msg);
                }
            });
        }
    }

    private void sendFreshListBroad() {
        int type = getView().getVideoType();
        Intent intent = new Intent();
        if (type == 0) {
            intent.setAction("com.sdwxwx.thumb.play");
        } else if (type == 1) {
            intent.setAction("com.sdwxwx.thumb.near");
        } else if (type == 2) {
            intent.setAction("com.sdwxwx.thumb.topic");
        } else if (type == 3) {
            intent.setAction("com.sdwxwx.thumb.owner");
        } else if (type == 4) {
            intent.setAction("com.sdwxwx.thumb.like");
        } else if (type == 5) {
            intent.setAction("com.sdwxwx.thumb.fans");
        }

        getView().getContext().sendBroadcast(intent);
    }

    @Override
    public void onShareClick() {
        getView().showShareDialog();
    }

    @Override
    public void loadCommentData(String videoId,String page) {

        mModel.getComments(LoginHelper.getInstance().getUserId(),videoId,page, new BaseCallback<List<CommentBean>>() {
                    @Override
                    public void onSuccess(List<CommentBean> data) {
                        if (getView() == null) {
                            return;
                        }
                        getView().loadCommentData(data);
                    }

                    @Override
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showToast(msg);
                    }
                }

        );

    }

    @Override
    public void addComment(String videoId, String memberId, String commentId, String content,String at) {
        mModel.addComment(videoId, memberId, commentId, content,at, new BaseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (getView() == null) {
                    return;
                }
                if (getView().getReplyDialog() != null) {
                    if (getView().getReplyDialog().isShowing()) {
                        getView().getReplyDialog().dismiss();
                    }
                }
                getView().getCommentTv().setText((Integer.valueOf(getView().getCommentTv().getText().toString())+1)+"");
                getView().showToast("发表评论成功");
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast("发表评论失败");
            }
        });
    }
}
