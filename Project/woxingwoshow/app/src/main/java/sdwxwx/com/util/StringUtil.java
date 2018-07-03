package sdwxwx.com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 860115025 on 2018/06/06.
 */

public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 把NULL转变为空字符串
     *
     * @param str
     * @return
     */
    public static String nullToBlank(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str;
    }

    /**
     * 将长时间转换为短时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String dataFormat(String long_time) {
        return long_time.substring(0,10);
    }

    /**
     * 把NULL转变为0字符串
     *
     * @param str
     * @return
     */
    public static String nullToZero(String str) {
        if (isEmpty(str)) {
            return "0";
        }
        return str;
    }
    /**
     * 对秒数进行变换处理
     * @param num
     * @return
     */
    public static String formatSecond(String num) {
        try {
            // 进行数据转换
            int sec = Integer.parseInt(num);
            // 计算秒数
            int second = (sec) % 60;
            // 计算分钟数
            int minutes = (sec) % 3600 / 60;
            // 计算小时数
            int hours = (sec) % (24 * 3600) / 3600;
            // 计算天数
            int days = (sec) / (24 * 3600);
            if (days != 0) {
                return String.format("%02d", days) + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second);
            } else if (hours != 0) {
                return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", second);
            } else if (minutes != 0) {
                return String.format("%02d", minutes) + ":" + String.format("%02d", second);
            } else {
                return "00:" + String.format("%02d", second);
            }
        } catch (Exception e) {
            return num;
        }
    }

    /**
     * String型时间戳格式化
     *
     * @return
     */
    public static String LongFormatTime(long time) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换为日期
        Date date = new Date();
        date.setTime(time);
        if (isThisYear(date)) {//今年
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            if (isToday(date)) { //今天
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内
                    if (minute <= 1) {//一分钟之内，显示刚刚
                        return "刚刚";
                    } else {
                        return minute + "分钟前";
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYestYesterday(date)) {//昨天，显示昨天
                    return "昨天 " + simpleDateFormat.format(date);
                } else if (isThisWeek(date)) {//本周,显示周几
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
                    return sdf.format(date);
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        }
    }

    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }

    private static boolean isToday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() == now.getDate());
    }

    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now.getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() - date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }

}
