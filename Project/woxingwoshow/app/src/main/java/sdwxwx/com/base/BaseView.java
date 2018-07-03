package sdwxwx.com.base;

import android.content.Context;

/**
 * create by 860115039
 * date      2018/5/7
 * time      16:14
 */
public interface BaseView {
    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示自定义提示
     * @param msg
     */
    void showCustomToast(String msg);

    /**
     * 获取上下文
     * @return 上下文
     */
    Context getContext();

    /**
     * 页面跳转
     *
     * @param cls
     */
    void actionStartActivity(Class cls);

    /**
     * 带参数页面跳转
     * @param cls
     * @param param
     */
    void paramStartActivity(Class cls,String param);

    /**
     * 带2参数页面跳转
     * @param cls
     * @param param
     */
    void param2StartActivity(Class cls,String param,int type);

    /**
     * 关闭画面
     */
    void closeActivity();
}
