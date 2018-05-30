package woxingwoxiu.com.play.contract;

import com.liaoinstan.springview.widget.SpringView;
import woxingwoxiu.com.adapter.BaseAdapter;
import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.play.bean.CommentBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/17
 * time      17:24
 */
public interface PlayVideoFragmentContract {

    interface View extends BaseView,SpringView.OnFreshListener,BaseAdapter.OnItemClickListener  {
       void showShareDialog();
        void showCommentDialog();
        void showSaySthDialog();
        void loadCommentData(List<CommentBean> list);
    }

    interface Presenter{

        void onBackClick();

        void onThumbDownClick();

        void onMoreClick();

        void onHeadClick();

        void onFollowClick();

        void onMusicClick();

        void onSaySthClick();

        void onCommentClick();

        void onThumbUpClick();

        void onShareClick();

        void loadCommentData(String videoId);

        void addComment(String videoId,String memberId,int commentId,String content);

    }
}
