package sdwxwx.com.release.contract;

import java.util.List;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.bean.MusicTypeBean;
import sdwxwx.com.contract.RecyclerViewContract;

/**
 * Created by 860117066 on 2018/06/05.
 */

public interface MusicOnlineContract {
    public interface View extends BaseView,RecyclerViewContract.View<MusicTypeBean> {
        void playMusic(MusicBean bean);
        void ShowSearchData(boolean flg,List<MusicBean> data);
    }

    public interface Presenter {
        void loadListData();
        void downloadOnlineMusic(MusicBean bean);

    }
}
