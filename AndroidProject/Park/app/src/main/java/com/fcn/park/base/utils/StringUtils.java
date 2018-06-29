package com.fcn.park.base.utils;

/**
 * Created by 860116021 on 2017/5/8.
 */

public class StringUtils {

    private static int counter;

    /**
     * @param str1
     * @param str2
     * @param reCount 是否重新计数
     * @return
     */
    public static int countStr(String str1, String str2, boolean reCount) {
        if (reCount) {
            counter = 0;
        }
        if (str1.indexOf(str2) == -1) {
            return 0;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            countStr(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2, false);
            return counter;
        }
        return 0;
    }
}
