package com.fcn.park.base.constant;

/**
 * Created by 860115001 on 2018/04/03.
 * 类描述：常量
 */

public class Constant {

    public static final String HTTP_HOST = "http://172.29.140.15:8080/";

    // TODO:人才的微信ID,园区的微信申请后要更改
    public static final String WX_APP_ID = "wx1a522fd90ee2cc1f";

    /**
     * 获取用户的类型
     */
    public enum UserType {
        /**
         * 管理员用户
         */
        MANAGE("0"),
        /**
         * 个人
         */
        PERSON("1"),
        /**
         * 企业
         */
        ENTERPRISE("2"),
        /**
         * 其他身份（比如：培训机构或者是学校）
         */
        OTHER("3");

        private String value;

        UserType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum AppModel {
        /**
         * 首页
         */
        HOME(0),
        /**
         * 动态
         */
        INFO(1),
        /**
         * 个人
         */
        ME(2);

        private int value;

        AppModel(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    /**
     * 本地广播Flag，头像修改的广播
     */
    public static final String LOCAL_BROADCAST_USER_AVATAR_FLAG = "user_avatar";

    /**
     * 本地广播Flag，姓名或企业名称修改的广播
     */
    public static final String LOCAL_BROADCAST_USER_NAME_FLAG = "user_name";


    /**
     * 支付费用类型
     *     1:水费缴费
     *     2:电费缴费
     *     3:物业费缴费
     *     4:租赁费缴费
     *     5:月租停车费
     *     6:临时停车缴费
     */
    public static final String PROPERTY_PAY_TYPE = "PROPERTY_PAY_TYPE";

    /**
     * 月租车辆、临时停车表的ID
     */
    public static final String PROPERTY_PAY_ID = "PROPERTY_PAY_ID";

    /**
     * 区分画面迁移来源
     */
    public static final String PROPERTY_MOVE_TYPE = "PROPERTY_MOVE_TYPE";

    /**
     * 区分画面迁移来源：缴费
     */
    public static final String PROPERTY_MOVE_TYPE_PAY = "PROPERTY_MOVE_TYPE_PAY";

    /**
     * 区分画面迁移来源：申请
     */
    public static final String PROPERTY_MOVE_TYPE_APPLY = "PROPERTY_MOVE_TYPE_APPLY";

    /**
     * 区分审核状态：KEY
     */
    public static final String PROPERTY_PARK_PAY_CHECK = "PROPERTY_PARK_PAY_CHECK";

    public static final String RENT_FROM = "RENT_FROM";

    /**
     * 支付方式
     *
     */
    public static final String PROPERTY_PAY_WAY = "PROPERTY_PAY_WAY";

    /**
     * 车牌号码
     *
     */
    public static final String PLATE_NUMBER = "PLATE_NUMBER";

    /**
     * 新闻类别：
     * 0：公告公示
     * 1：园区新闻
     * 2：园区活动
     * 3：企业列表
     * 4：人才需求
     */
    public static final String NEWS_TYPE = "NEWS_TYPE";
    public static final String NEWS_TYPE_0 = "0";
    public static final String NEWS_TYPE_1 = "1";
    public static final String NEWS_TYPE_2 = "2";
    public static final String NEWS_TYPE_3 = "3";
    public static final String NEWS_TYPE_4 = "4";
    // 打电话用ACTION
    public static final String PROPERTY_CALL_ACTION = "android.intent.action.CALL";


}
