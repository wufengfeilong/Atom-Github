package sdwxwx.com.play.contract;

import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;
import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.base.BaseView;
import sdwxwx.com.play.bean.CommentBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/17
 * time      17:24
 */
public interface PlayVideoFragmentContract {

    interface View extends BaseView,SpringView.OnFreshListener {
       void showShareDialog();
        void showCommentDialog();
        void showSaySthDialog();
        void loadCommentData(List<CommentBean> list);
        void isFollowed();
        int getFollowId();
        String getVideoId();
        ImageView getThumbIv();
        TextView getCommentTv();
        TextView getThumbTv();
        boolean getIsLiked();
        void setIsLiked(String isLike);
        Dialog getReplyDialog();
        int getVideoType();
        void setLikeCount(int add);
    }

    interface Presenter{

        void onBackClick();

        void onMoreClick();

        void onHeadClick();

        void onFollowClick();

        void onMusicClick();

        void onSaySthClick();

        void onCommentClick();

        void onThumbUpClick();

        void onShareClick();

        void loadCommentData(String videoId,String page);

        void addComment(String videoId,String memberId,String commentId,String content,String at);

    }
}
