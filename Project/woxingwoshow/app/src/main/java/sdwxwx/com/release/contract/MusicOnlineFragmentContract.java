package sdwxwx.com.release.contract;

import com.liaoinstan.springview.widget.SpringView;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.databinding.MusicOnlineFragmentBinding;

/**
 * Created by 860117066 on 2018/06/05.
 */

public interface MusicOnlineFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<MusicBean>,SpringView.OnFreshListener {
        void playMusic(MusicBean bean);
    }

    interface Presenter {
        void loadPopularMusicData(String page,MusicOnlineFragmentBinding mDataBinding);
        void loadCollectMusicData(String page,MusicOnlineFragmentBinding mDataBinding);
        void downloadOnlineMusic(MusicBean bean);
    }
}
