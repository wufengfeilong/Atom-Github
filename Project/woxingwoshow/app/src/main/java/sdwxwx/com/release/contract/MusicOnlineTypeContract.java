package sdwxwx.com.release.contract;

import com.liaoinstan.springview.widget.SpringView;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.databinding.OnlineMusicTypeListBinding;

/**
 * Created by 860117066 on 2018/06/13.
 * 类描述：音乐分类别列表
 */

public interface MusicOnlineTypeContract {
    interface View extends BaseView,RecyclerViewContract.View<MusicBean>,SpringView.OnFreshListener {
        void playMusic(MusicBean bean);
    }

    public interface Presenter {
        void loadListData(String i,OnlineMusicTypeListBinding mDataBinding);
        void getMusicTypeList();
        void onClickBack();
        void downloadOnlineMusic(MusicBean bean);
    }
}
