package woxingwoxiu.com.cons;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:07
 */
public class Constant {

//    public static final String HTTP_BASE_HOST = "http://172.29.140.35:8080/";

    public static final String HTTP_BASE_HOST = "http://wxwx.boluomee.com/api/";

    public static final String INTENT_PARAM = "param";

    public static final String INTENT_FILTER_SELECT_CITY = "select_city";

    public static final String SP_FILE_NAME = "woxingwoxiu";

    public static final String SP_LOGIN_TOKEN = "login_token";

    //需要登录才能看得Activity放到这里
    public static final String[] mInterClsName = {""};

    // add by lily start

    /**
     * 最长拍摄时长
     */
    public static final int MAX_DURATION = 1 * 60 * 1000;

    /**
     * 最短拍摄时长
     */
    public static final int MIN_DURATION = 5 * 1000;
    /**
     * 草稿箱视频存储文件夹名称
     */
    public static final String DRAFT_BOX_NAME = "/woxingwoxiu/DraftBox";

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
     * 选择视频话题的标识Key
     */
    public final static String CHOOSE_TOPIC = "CHOOSE_TOPIC";

    /**
     * 选择视频@好友的标识Key
     */
    public final static String CHOOSE_FRIEND = "CHOOSE_FRIEND";

    /**
     * 视频合成后，画面跳转发布编辑画面标识Key
     */
    public final static String COMPOSE_PATH = "COMPOSE_PATH";

    /**
     * 视频长度标识Key
     */
    public static final String PREVIEW_LEN = "PREVIEW_LEN";

    /**
     * EventBus删除断点视频Key
     */
    public static final String DELETE_VIDEOS = "DELETE_VIDEOS";

    // add by lily end

    /**
     * 图片保存路径
     */
    public static final String SD_PATH = "/sdcard/woxingwoxiu/pic/";
    /**
     * 图片保存路径
     */
    public static final String IN_PATH = "/woxingwoxiu/pic/";
}
