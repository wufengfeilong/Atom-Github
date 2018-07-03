package sdwxwx.com.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.HomeActivity;
import sdwxwx.com.login.utils.LoginHelper;

/**
 * 登录用共通
 * Created by 860115025 on 2018/06/05.
 */
public class LoginUtil {

    /**
     * 保存登录用户的信息
     * @param context
     * @param bean
     */
    public static void saveLoginInfo(Context context, UserBean bean) {
        // 保存用户登录信息
        setUserBean(context, bean);
        // 跳转到HOME画面
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    /**
     * 保存登录用户信息
     * @param context
     * @param bean
     */
    public static void setUserBean(Context context, UserBean bean) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        // 设置登录成功的标识
        editor.putString(Constant.SP_LOGIN_TOKEN, "01");
        // 会员编号
        editor.putString(Constant.SP_MEMBER_ID, bean.getId());
        // 推荐人编号
        editor.putString(Constant.SP_RECOMMENDER_ID, bean.getRecommender_id());
        // 城市编号
        editor.putString(Constant.SP_CITY_ID, bean.getCity_id());
        // 环信账户的名称
        editor.putString(Constant.SP_EASEMOB_USERNAME, bean.getEasemob_username());
        // 环信账户的密码
        editor.putString(Constant.SP_EASEMOB_PASSWORD, bean.getEasemob_password());
        /** 手机号 */
        editor.putString(Constant.SP_MOBILE, bean.getMobile());
        /** 昵称 */
        editor.putString(Constant.SP_NICK_NAME, bean.getNickname());
        /** 个性签名 */
        editor.putString(Constant.SP_SIGNATURE, bean.getSignature());
        /** 性别 */
        editor.putString(Constant.SP_GENDER, bean.getGender());
        /** 生日 */
        editor.putString(Constant.SP_BIRTHDAY, bean.getBirthday());
        /** 头像地址 */
        editor.putString(Constant.SP_AVATAR_URL, bean.getAvatar_url());
        /** 关注数 */
        editor.putString(Constant.SP_FOLLOW_COUNT, bean.getFollow_count());
        /** 粉丝数 */
        editor.putString(Constant.SP_FOLLOWED_COUNT, bean.getFollowed_count());
        /** 评论数 */
        editor.putString(Constant.SP_COMMENT_COUNT, bean.getComment_count());
        /** 推荐数 */
        editor.putString(Constant.SP_RECOMMEND_COUNT, bean.getRecommend_count());
        /** 视频数 */
        editor.putString(Constant.SP_VIDEO_COUNT, bean.getVideo_count());
        /** 会员点赞的视频数。 */
        editor.putString(Constant.SP_FAVORITE_VIDEO_COUNT, bean.getFavorite_video_count());
        /** 会员收藏的音乐数 */
        editor.putString(Constant.SP_FAVORITE_MUSIC_COUNT, bean.getFavorite_music_count());
        /** 初始财富值 */
        editor.putString(Constant.SP_INITIAL_WEALTH, bean.getInitial_wealth());
        /** 视频财富 */
        editor.putString(Constant.SP_VIDEO_WEALTH, bean.getVideo_wealth());
        /** 推荐财富 */
        editor.putString(Constant.SP_RECOMMEND_WEALTH, bean.getRecommend_wealth());
        /** 是否认证 */
        editor.putString(Constant.SP_IS_CERTIFIED, bean.isCertified());
        /** 注册时间 */
        editor.putString(Constant.SP_CREATE_TIME, bean.getCreate_time());
        editor.commit();

        LoginHelper.getInstance().setOnline(true);
    }
    /**
     * 获取登录用户的状态
     * @param context
     */
    public static boolean isOnlineInfo(Context context) {
        boolean isOnline = false;
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        String onlineFlag = sp.getString(Constant.SP_LOGIN_TOKEN, "00");
        if ("01".equals(onlineFlag)) {
            isOnline = true;
        }
        return isOnline;
    }

    /**
     * 获取登录用户的信息
     * @param context
     */
    public static UserBean getLoginInfo(Context context) {
        UserBean userBean = new UserBean();
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);

        // 数据分析
        String emptyStr = "";
        // 会员编号
        userBean.setId(sp.getString(Constant.SP_MEMBER_ID, emptyStr));
        // 推荐人编号
        userBean.setRecommender_id(sp.getString(Constant.SP_RECOMMENDER_ID, emptyStr));
        // 城市编号
        userBean.setCity_id(sp.getString(Constant.SP_CITY_ID, emptyStr));
        // 推荐人编号
        userBean.setRecommender_id(sp.getString(Constant.SP_RECOMMENDER_ID, emptyStr));
        // 环信账户的名称
        userBean.setEasemob_username(sp.getString(Constant.SP_EASEMOB_USERNAME, emptyStr));
        // 环信账户的密码
        userBean.setEasemob_password(sp.getString(Constant.SP_EASEMOB_PASSWORD, emptyStr));
        // 手机号
        userBean.setMobile(sp.getString(Constant.SP_MOBILE, emptyStr));
        //  昵称
        userBean.setNickname(sp.getString(Constant.SP_NICK_NAME, emptyStr));
        //  个性签名
        userBean.setSignature(sp.getString(Constant.SP_SIGNATURE, emptyStr));
        //  性别
        userBean.setGender(sp.getString(Constant.SP_GENDER, emptyStr));
        //  生日
        userBean.setBirthday(sp.getString(Constant.SP_BIRTHDAY, emptyStr));
        //  头像地址setAvatar_url
        userBean.setAvatar_url(sp.getString(Constant.SP_AVATAR_URL, emptyStr));
        //  关注数
        userBean.setFollow_count(sp.getString(Constant.SP_FOLLOW_COUNT, emptyStr));
        //  粉丝数
        userBean.setFollowed_count(sp.getString(Constant.SP_FOLLOWED_COUNT, emptyStr));
        //  评论数
        userBean.setComment_count(sp.getString(Constant.SP_COMMENT_COUNT, emptyStr));
        //  推荐数
        userBean.setRecommend_count(sp.getString(Constant.SP_RECOMMEND_COUNT, emptyStr));
        //  视频数
        userBean.setVideo_count(sp.getString(Constant.SP_VIDEO_COUNT, emptyStr));
        //  会员点赞的视频数
        userBean.setFavorite_video_count(sp.getString(Constant.SP_FAVORITE_VIDEO_COUNT, emptyStr));
        //  会员收藏的音乐数
        userBean.setFavorite_music_count(sp.getString(Constant.SP_FAVORITE_MUSIC_COUNT, emptyStr));
        //  初始财富
        userBean.setInitial_wealth(sp.getString(Constant.SP_INITIAL_WEALTH, emptyStr));
        //  视频财富
        userBean.setVideo_wealth(sp.getString(Constant.SP_VIDEO_WEALTH, emptyStr));
        // 推荐财富
        userBean.setRecommend_wealth(sp.getString(Constant.SP_RECOMMEND_WEALTH, emptyStr));
        // 是否认证
        userBean.setCertified(sp.getString(Constant.SP_IS_CERTIFIED, emptyStr));
        // 注册时间
        userBean.setCreate_time(sp.getString(Constant.SP_CREATE_TIME, emptyStr));

        return userBean;
    }

    /**
     * 清空登录用户的信息
     * @param context
     */
    public static boolean clearLoginInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        return editor.commit();
    }

    /**
     * 获取存储内容(字符串型)
     * @param key
     * @param context
     * @return
     */
    public static String getStrUserInfo(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    /**
     * 获取存储内容（数值型）
     * @param key
     * @param context
     * @return
     */
    public static int getIntUserInfo(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * 获取存储内容（布尔型）
     * @param key
     * @param context
     * @return
     */
    public static boolean getBlnUserInfo(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SP_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
