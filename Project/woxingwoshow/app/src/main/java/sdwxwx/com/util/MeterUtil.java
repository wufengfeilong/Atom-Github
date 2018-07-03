package sdwxwx.com.util;

import java.math.BigDecimal;

/**
 * create by 860115039
 * date      2018/5/16
 * time      13:58
 */
public class MeterUtil {
    private static final double EARTH_RADIUS = 6378.137;

    //
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // 返回单位是:千米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        //有小数的情况;注意这里的10000d中的“d”
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;//单位：米
        s = Math.round(s/10d) /100d   ;//单位：千米 保留两位小数
//        s = Math.round(s / 100d) / 10d;//单位：千米 保留一位小数
        return s;
    }
    public static int algorithm(double longitude1, double latitude1, double longitude2, double latitude2) {

        double Lat1 = rad(latitude1); // 纬度

        double Lat2 = rad(latitude2);

        double a = Lat1 - Lat2;//两点纬度之差

        double b = rad(longitude1) - rad(longitude2); //经度之差

        double s = 2 * Math.asin(Math

                .sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));//计算两点距离的公式

        s = s * 6378137.0;//弧长乘地球半径（半径为米）

        s = Math.round(s * 10000d) / 10000d;//精确距离的数值
        BigDecimal bd=new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP);
        return Integer.parseInt(bd.toString());

    }

    public static String numToWan(int number) {
        String str = "";
        if (number <= 0) {
            str = "0";
        } else if (number < 10000) {
            str = number + "";
        } else {
            double d = (double) number;
            double num = d / 10000;//1.将数字转换成以万为单位的数字

            BigDecimal b = new BigDecimal(num);
            double f1 = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();//2.转换后的数字四舍五入保留小数点后一位;
            str = f1 + "万";
        }
        return str;
    }

}
