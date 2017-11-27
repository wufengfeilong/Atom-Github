package com.fushi.warehouse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    /**
     * 判断字符串是否为空
     *
     * @param text
     *            被判断的字符串
     * @return true:字符串为null或者字符串为""
     */
    public static boolean isEmpty(String text) {
        if (text == null || text.trim().equals("")) {
            return true;
        }
        return false;

    }
    /**
     * 根据给定的格式，返回时间字符串。
     * <p>
     * 格式参照类描绘中说明.
     *
     * @param format
     *            日期格式字符串
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatCurrentTime(String format) {
        return getFormatDateTime(new Date(), format);
    }

    /**
     * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
     *
     * @param date
     *            指定的日期
     * @param format
     *            日期格式字符串
     * @return String 指定格式的日期字符串.
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     *
     *
     * @param timeMillis
     *            时间的毫秒数
     * @param formateType
     *            1：
     * @return
     */
    public static String getTime(long timeMillis, int formateType) {
        SimpleDateFormat mFormat;
        String result = "";
        switch (formateType) {
        case 1:
            mFormat = new SimpleDateFormat("HH:mm");
            result = mFormat.format(System.currentTimeMillis());
            break;

        default:
            break;
        }
        return result;
    }

    /**
     *
     *
     * @param timeStr
     *            时间的字符串形式
     * @param formateType
     *            1：
     * @return
     */
    public static Date getTime(String timeStr, int formateType) {
        SimpleDateFormat mFormat;
        Date result = new Date();
        try {
            switch (formateType) {
            case 1:
                mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                result = mFormat.parse(timeStr);
                break;
            default:
                break;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
