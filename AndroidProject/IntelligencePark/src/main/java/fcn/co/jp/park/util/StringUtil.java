package fcn.co.jp.park.util;

public class StringUtil {

    /**
     * 将以逗号分隔的字符串转换成字符串数组
     * @param valStr
     * @return String[]
     */
    public static String[] StrList(String valStr) {
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

            i++;
        }
        return returnStr;
    }

    /**
     * 把null转化为空白
     * @param value
     * @return
     */
    public static String nullToBlank(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * 判断字符串是否为空串或NULL
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        if (text == null || text.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 替换html标签
     * @param st
     * @return
     */
    public static String change(String st) {
        String Strs = st.replaceAll("<( )*span( )*>", "").replaceAll("&nbsp;", "").replaceAll("<( )*p([^>])*>", "")
                .replaceAll("<( )*style([^>])*>", "").replaceAll("<( )*br( )*>", "").replaceAll("<( )*li( )*>", "")
                .replaceAll("<( )*tr([^>])*>", "").replaceAll("<( )*td([^>])*>", "")
                .replaceAll("(<head>).*(</head>)", "").replaceAll("<( )*script([^>])*>", "").replaceAll("\r", "")
                .replaceAll("\n", "").replaceAll("\t", " ").replaceAll("<[^>]*>", "");
        return Strs;
    }
}
