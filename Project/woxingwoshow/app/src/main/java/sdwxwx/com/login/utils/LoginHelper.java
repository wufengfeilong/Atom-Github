package sdwxwx.com.login.utils;

import android.content.Context;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.util.LoginUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录帮助类
 */

public class LoginHelper {

    private static LoginHelper sInstance;
    private WeakReference<Context> mContext;
    private UserBean mUserBean;
    // 是否已登录
    private boolean isOnline;

    private List<PlayVideoBean> mList;
    private List<PlayVideoBean> mNearList;
    private List<PlayVideoBean> mTopicList;
    private List<PlayVideoBean> mOwnerList;
    private List<PlayVideoBean> mLikeList;
    private List<PlayVideoBean> mFansList;
    private List<CityEntity> mCityList;
    private String cityId;
    private String cityName;


    private LoginHelper() {
    }

    /**
     * 获取类实例
     *
     * @return
     */
    public static LoginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new LoginHelper();
        }
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param context
     * @return
     */
    public LoginHelper init(Context context) {
        mContext = new WeakReference<>(context);

        // 获取用户的登录信息
        isOnline = checkIsOnline();
        return this;
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    private boolean checkIsOnline() {
        boolean isOnline = LoginUtil.isOnlineInfo(mContext.get());
        // 已登录
        if (isOnline) {
            mUserBean = LoginUtil.getLoginInfo(mContext.get());
        }
        return isOnline;
    }

    /**
     * 用户退出
     *
     * @return
     */
    public boolean userExit() {
        mUserBean = null;
        isOnline = false;
        return LoginUtil.clearLoginInfo(mContext.get());
    }

    /**
     * 获取UserBean
     *
     * @return
     */
    public UserBean getUserBean() {
        return mUserBean;
    }

    public void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }

    /**
     * 获取用户Id
     *
     * @return 用户Id
     */
    public String getUserId() {
        if (mUserBean != null) {
            return mUserBean.getId();
        } else {
            return "0";
        }
    }


    /**
     * 获取用户登录状态
     *
     * @return 登录状态
     */
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        if (online) {
            mUserBean = LoginUtil.getLoginInfo(mContext.get());
        } else {
            userExit();
        }
        isOnline = online;
    }


    public List<PlayVideoBean> getPlayVideoList() {
        return mList;
    }
    //list赋值
    public void setPlayVideoList(List<PlayVideoBean> list) {
        this.mList = new ArrayList<>();
        this.mList.addAll(list);
    }
    //list添加
    public void addPlayVideoList(List<PlayVideoBean> list){
        this.mList.addAll(list);
    }

    //list大小
    public int getPlayVideoListSize(){
        return this.mList.size();
    }

    public List<PlayVideoBean> getNearList() {
        return mNearList;
    }

    public void setNearList(List<PlayVideoBean> nearList) {
        mNearList = new ArrayList<>();
        mNearList.addAll(nearList);
    }

    //list大小
    public int getNearVideoListSize(){
        return mNearList.size();
    }

    //list大小
    public int getTopicVideoListSize(){
        return mTopicList.size();
    }

    public List<PlayVideoBean> getTopicList() {
        return mTopicList;
    }

    public void setTopicList(List<PlayVideoBean> topicList) {
        mTopicList = new ArrayList<>();
        mTopicList.addAll(topicList);
    }

    public List<PlayVideoBean> getOwnerList() {
        return mOwnerList;
    }

    public void setOwnerList(List<PlayVideoBean> ownerList) {
        mOwnerList = new ArrayList<>();
        mOwnerList.addAll(ownerList);
    }

    //list大小
    public int getOwnerVideoListSize(){
        return mOwnerList.size();
    }

    public List<CityEntity> getCityList() {
        return mCityList;
    }

    public void setCityList(List<CityEntity> cityList) {
        mCityList = new ArrayList<>();
        mCityList.addAll(cityList);
    }

    public List<PlayVideoBean> getLikeList() {
        return mLikeList;
    }

    public void setLikeList(List<PlayVideoBean> likeList) {
        mLikeList = new ArrayList<>();
        mLikeList.addAll(likeList);
    }

    //list大小
    public int getLikeVideoListSize(){
        return mLikeList.size();
    }

    public List<PlayVideoBean> getFansList() {
        return mFansList;
    }

    public void setFansList(List<PlayVideoBean> fansList) {
        mFansList = new ArrayList<>();
        mFansList.addAll(fansList);
    }

    //list大小
    public int getFansListSize(){
        return mFansList.size();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
