package sdwxwx.com.cons;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:07
 */
public class Constant {

//    public static final String HTTP_BASE_HOST = "http://172.29.140.35:8080/";

    public static final String HTTP_BASE_HOST = "http://wxwx.boluomee.com/api/";

    public static final String INTENT_PARAM = "param";

    public static final String INTENT_PARAM_ONE = "param1";

    public static final String INTENT_PARAM_TWO = "param2";

    public static final String INTENT_FILTER_SELECT_CITY = "select_city";

    public static final String INTENT_FILTER_DOWNLOAD_VIDEO = "download_video";

    public static final String INTENT_FILTER_EDIT_INFO = "edit_info";

    public static final String SP_FILE_NAME = "woxingwoxiu";

    public static final String SP_LOGIN_TOKEN = "login_token";
    /** 会员编号 */
    public static final String SP_MEMBER_ID = "member_id";
    /** 推荐人编号 */
    public static final String SP_RECOMMENDER_ID = "recommender_id";
    /** 城市编号 */
    public static final String SP_CITY_ID = "city_id";
    /** 环信账户的名称 */
    public static final String SP_EASEMOB_USERNAME = "easemob_username";
    /** 环信账户的密码 */
    public static final String SP_EASEMOB_PASSWORD = "easemob_password";
    /** 手机号 */
    public static final String SP_MOBILE = "mobile";
    /** 昵称 */
    public static final String SP_NICK_NAME = "nickname";
    /** 个性签名 */
    public static final String SP_SIGNATURE = "signature";
    /** 性别 */
    public static final String SP_GENDER = "gender";
    /** 生日 */
    public static final String SP_BIRTHDAY = "birthday";
    /** 头像地址 */
    public static final String SP_AVATAR_URL = "avatar_url";
    /** 关注数 */
    public static final String SP_FOLLOW_COUNT = "follow_count";
    /** 粉丝数 */
    public static final String SP_FOLLOWED_COUNT = "followed_count";
    /** 评论数 */
    public static final String SP_COMMENT_COUNT = "comment_count";
    /** 推荐数 */
    public static final String SP_RECOMMEND_COUNT = "recommend_count";
    /** 视频数 */
    public static final String SP_VIDEO_COUNT = "video_count";
    /** 会员点赞的视频数。 */
    public static final String SP_FAVORITE_VIDEO_COUNT = "favorite_video_count";
    /** 会员收藏的音乐数。 */
    public static final String SP_FAVORITE_MUSIC_COUNT = "favorite_music_count";
    /** 初始财富 */
    public static final String SP_INITIAL_WEALTH = "initial_wealth";
    /** 视频财富 */
    public static final String SP_VIDEO_WEALTH = "video_wealth";
    /** 推荐财富 */
    public static final String SP_RECOMMEND_WEALTH = "recommend_wealth";
    /** 是否认证 */
    public static final String SP_IS_CERTIFIED = "is_certified";
    /** 注册时间 */
    public static final String SP_CREATE_TIME = "create_time";

    //需要登录才能看得Activity放到这里
    public static final String[] mInterClsName = {"QRCodeGenerateActivity","QRCodeScanActivity"};

    // add by lily start

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * "0"
     */
    public static final String ZERO_STRING = "0";

    /**
     * "1":请求页码起始No.
     */
    public static final String REQUEST_PAGE = "1";

    /**
     * "20":请求每页size
     */
    public static final String DEFAULT_SIZE = "20";

    /**
     * URL编码（RFC 3986）
     */
    public static final String ENCODE = "UTF-8";

    /**
     * 最长拍摄时长
     */
    public static final int MAX_DURATION = 1 * 60 * 1000;

    /**
     * 最短拍摄时长
     */
    public static final int MIN_DURATION = 5 * 1000;

    /**
     * 录制的视频和相册裁剪的视频存储文件夹名称
     */
    public static final String RECORD_BOX_NAME = "/woxingwoxiu/Record";

    /**
     * 编辑合成后的视频存储文件夹名称
     */
    public static final String COMPOSE_BOX_NAME = "/woxingwoxiu/Compose";

    /**
     * 草稿箱视频存储文件夹名称
     */
    public static final String DRAFT_BOX_NAME = "/woxingwoxiu/DraftBox";

    /**
     * 视频文件拓展名
     */
    public static final String VIDEO_TYPE_NAME = ".mp4";

    /**
     * 草稿箱视频存储文件名称中存入MusicId
     */
    public static final String DRAFT_BOX_MUSIC_ID = "musicId";

    /**
     * 录制的视频音乐存储文件夹名称
     */
    public static final String MUSIC_BOX_NAME = "/woxingwoxiu/Music";

    /**
     * 画面跳转HOME画面的KEY
     * 存储到草稿箱后，显示ME
     * 发布完视频后，显示首页
     */
    public static final String BACK_HOME_KEY = "DRAFT_BOX_TO_ME";

    /**
     * 选择视频封面的标识Key
     */
    public final static String CHOOSE_COVER_BITMAP = "CHOOSE_COVER_BITMAP";

    /**
     * 选择视频封面的标识Key
     */
    public final static String CHOOSE_COVER_TITLE = "CHOOSE_COVER_TITLE";

    /**
     * 选择视频话题的标识Key
     */
    public final static String CHOOSE_TOPIC = "CHOOSE_TOPIC";

    /**
     * 发布视频时选择话题内容传递Key
     */
    public static final String RELEASE_ADD_TOPIC_CONTENT = "RELEASE_ADD_TOPIC_CONTENT";

    /**
     * 选择视频@好友的标识Key
     */
    public final static String CHOOSE_FRIEND = "CHOOSE_FRIEND";

    /**
     * 视频合成后，画面跳转发布编辑画面标识Key
     */
    public final static String COMPOSE_PATH = "COMPOSE_PATH";

    /**
     * 金山云URL
     */
    public static final String KSY_URL_BASE = "http://ks3-cn-beijing.ksyun.com/woxingwoxiu/";

    /**
     * 在线音乐选择音乐路径
     */
    public static final String MUSIC_ONLINE_PATH = "MUSIC_ONLINE_PATH";

    /**
     * 在线音乐选择音乐编号
     */
    public static final String MUSIC_ONLINE_ID = "MUSIC_ONLINE_ID";
    // add by lily end

    /**
     * 图片保存路径
     */
    public static final String SD_PATH = "/sdcard/woxingwoxiu/pic/";
    /**
     * 图片保存路径
     */
    public static final String IN_PATH = "/woxingwoxiu/pic/";

    /**
     * 图片保存路径
     */
    public static final String NO_MORE_MSG = "已经没有更多了";
}
