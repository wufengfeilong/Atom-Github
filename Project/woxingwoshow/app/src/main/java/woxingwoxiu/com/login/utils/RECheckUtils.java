package woxingwoxiu.com.login.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 丁胜胜 on 2018/05/10.
 * 类描述：正则表达式验证手机、邮箱是否合法
 */

public class RECheckUtils {


    /**
     * 正则表达式校验邮箱-企业
     * @param email     邮箱账号
     * @return          true:符合规则   false:不符合规则
     */
    public static boolean checkEmail(String email){
        String regularExpression = "(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(email);
        return m.matches();
    }

    /**
     * 正则表达式校验邮箱-个人
     * @param email
     * @return
     */
    public static boolean checkPersionEmail(String email){
        String regularExpression = "([a-zA-Z0-9_.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(email);
        return m.matches();
    }

    /**
     * 验证密码是否合乎规范
     * @param password 密码
     * @return 返回1，代表密码不合规范。返回2，代表密码中含有中文字符（要求密码中不能有中文字符）。返回0代表验证通过
     */
    public static int checkPassword(String password){
        String regularExpression = "(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,16}";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(password);
        boolean matches1 = m.matches();
        String regular = "^[\\x21-\\x7e]*$";
        Pattern rr = Pattern.compile(regular);
        Matcher mm = rr.matcher(password);
        boolean matches2 = mm.matches();
        if (!matches1){
            return 1;
        }else if (!matches2){
            return 2;
        }else {
            return 0;
        }
    }

    /**
     * 正则表达式校验用户名
     * @param username  用户名
     * @return          true:符合规则   false:不符合规则
     */
    public static boolean checkUsername(String username){
        String regularExpression = "[A-Za-z0-9@.]+";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(username);
        return m.matches();
    }

    /**
     * 正则表达式校验手机号码
     * @param phoneNum  手机号码
     * @return          true:符合规则   false:不符合规则
     */
    public static boolean checkPhoneNum(String phoneNum){
        String pattern = "(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 正则表达式校验网址
     * @param url   网址
     * @return      true:符合规则   false:不符合规则
     */
    public static boolean checkUrl(String url){
        String regularExpression = "((https|http|ftp|rtsp|mms):\\/\\/)?(([0-9a-z_!~*'().&=+$%-]+:)?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((\\/?)|(\\/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+\\/?)";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(url);
        return m.matches();
    }

    /**
     * 正则表达式校验邮编
     * @param postCode  邮编
     * @return          true:符合规则   false:不符合规则
     */
    public static boolean checkPostCode(String postCode){
        String regularExpression = "[0-9]{6}";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(postCode);
        return m.matches();
    }

    /**
     * 正则表达式校验座机
     * @param landline  座机号
     * @return          true:符合规则   false:不符合规则
     */
    public static boolean checkLandline(String landline){
        String regularExpression = "((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$";
        Pattern r = Pattern.compile(regularExpression);
        Matcher m = r.matcher(landline);
        return m.matches();
    }

    /**
     * 给定一个日期，与当前时间比较。传入的日期比当前日期早，返回true。否则返回false
     *
     * @param supplyDate yyyy-MM-dd格式的时间
     * @return supplyDate为null或空时，返回false。
     */
    public static boolean checkTimeWithCurrent(String supplyDate) {
        if (supplyDate == null || supplyDate.trim().equals(""))
            return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long supplyTime = sdf.parse(supplyDate).getTime();
            long currentTime = System.currentTimeMillis();
            if (supplyTime >= currentTime) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 比较开始时间是否早于结束时间
     * @param startTime yyyy-MM-dd格式的开始时间
     * @param endTime yyyy-MM-dd格式的结束时间
     * @return 开始时间早于结束时间返回true
     */
    public static boolean checkTwoTime(String startTime,String endTime) {
        if (startTime == null || startTime.trim().equals(""))
            return false;
        if (endTime == null || endTime.trim().equals(""))
            return false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long startTimeSec = sdf.parse(startTime).getTime();
            long endTimeSec = sdf.parse(endTime).getTime();
            if (startTimeSec >= endTimeSec) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
