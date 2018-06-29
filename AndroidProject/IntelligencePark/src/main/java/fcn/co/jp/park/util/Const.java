package fcn.co.jp.park.util;

public class Const {

    // 字符数字
    /** String 0 */
    public static final String STRING_ZERO = "0";
    /** String 1 */
    public static final String STRING_ONE = "1";
    /** String 2 */
    public static final String STRING_TWO = "2";
    /** String 3 */
    public static final String STRING_THERE = "3";

    // 短信应用
    public static final String SMURL = "http://sms.253.com/msg/";// 应用地址
    public static final String SMACCOUNT = "N6036853";// 账号
    public static final String SMPSWD = "bC8S3omKfMdfbf";// 密码
    public static final String rd = "1";// 是否需要状态报告，需要1，不需要0

    /**
     * APP Constants
     */
    // app注册接口_请求协议参数)
    public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[] { "countries", "uname", "passwd", "title",
            "full_name", "company_name", "countries_code", "area_code", "telephone", "mobile" };
    public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[] { "国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称",
            "国家编号", "区号", "电话", "手机号" };
    // app根据用户名获取会员信息接口_请求协议中的参数
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[] { "USERNAME" };
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[] { "用户名" };

    // 分页条数配置路径
    public static final String PAGE = "admin/config/PAGE.txt";
    /*每页显示个数*/
    public static final int SHOWCOUNT = 10;

    // 服务器文件存储路径共享盘
    public static final String FILEPATH = "C:/ParkFile/";

    // 个人头像路径
    public static final String USER_PICTURE = "uploadFiles/user/";

    // 公共报修路径
    public static final String REPAIR_PICTURE = "uploadFiles/repair/";

    // 月租车辆申请路径
    public static final String MONTH_CAR_APPLY_PICTURE = "uploadFiles/monthCarApply/";

    // 公告活动新闻路径
    public static final String NEWS_PICTURE = "uploadFiles/news/";

    // 园区，公司简介路径
    public static final String COMPANY_PICTURE = "uploadFiles/companyInfo/";

    // 广告路径
    public static final String ADVERTISEMENT_PICTURE = "uploadFiles/advertisement/";
}
