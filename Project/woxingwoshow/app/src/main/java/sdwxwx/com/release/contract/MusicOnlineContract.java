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
        void bindMusicData(List<MusicBean> beanList);
        void ShowSearchData(boolean flg,List<MusicBean> data);
//        void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
    }

    public interface Presenter {
        void loadListData();
//        void getItemOffsets(Rect outRect, android.view.View view, RecyclerView parent, RecyclerView.State state);

    }
}
