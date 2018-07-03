package sdwxwx.com.base;

import android.support.v7.widget.RecyclerView;
import sdwxwx.com.recycler_listener.OnRecyclerItemClickListener;

/**
 * create by 860115039
 * date      2018/5/7
 * time      16:16
 */
public class BasePresenter<V extends BaseView> {
    protected OnRecyclerItemClickListener mOnItemClickListener;
    /**
     * 绑定的view
     */
    private V mvpView;

    /**
     * 绑定view，一般在初始化中调用该方法
     */

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */

    public void detachView() {
        this.mvpView = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return mvpView != null;
    }

    /**
     * 获取连接的view
     */
    public V getView(){
        return mvpView;
}

    public void createItemClickListener(RecyclerView recyclerView) {
        mOnItemClickListener = new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                BasePresenter.this.onItemClick(vh, vh.getLayoutPosition());
            }
        };
    }
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
    }
}
