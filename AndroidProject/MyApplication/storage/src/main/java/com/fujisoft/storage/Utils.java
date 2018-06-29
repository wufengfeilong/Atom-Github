package com.fujisoft.storage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    /**
     * 正則表現の検証
     *
     * @param str
     * @return
     */
    public static boolean regValid(String str) {
        String regEx = "^[1-9]\\d*$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
