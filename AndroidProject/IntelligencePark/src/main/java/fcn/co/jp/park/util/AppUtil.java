package fcn.co.jp.park.util;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AppUtil {

    /** LOG */
    protected static Logger logger = LoggerFactory.getLogger(AppUtil.class);

    /**
     * 对方法参数进行check
     * @param method
     * @param pd
     * @return boolean
     */
    public static boolean checkParam(String method, PageData pd) {
        boolean result = false;

        int falseCount = 0;
        String[] paramArray = new String[20];
        String[] valueArray = new String[20];
        // 临时数组
        String[] tempArray = new String[20];
        // 注册
        if ("registered".equals(method)) {
            // 参数
            paramArray = Const.APP_REGISTERED_PARAM_ARRAY;
            // 参数名称
            valueArray = Const.APP_REGISTERED_VALUE_ARRAY;
            // 根据用户名获取会员信息
        } else if ("getAppuserByUsernmae".equals(method)) {
            paramArray = Const.APP_GETAPPUSER_PARAM_ARRAY;
            valueArray = Const.APP_GETAPPUSER_VALUE_ARRAY;
        }

        int size = paramArray.length;
        for (int i = 0; i < size; i++) {
            String param = paramArray[i];
            if (!pd.containsKey(param)) {
                tempArray[falseCount] = valueArray[i] + "--" + param;
                falseCount += 1;
            }
        }

        if (falseCount > 0) {
            logger.error(method + "接口，请求协议中缺少 " + falseCount + "个 参数");
            for (int j = 1; j <= falseCount; j++) {
                logger.error("   第" + j + "个：" + tempArray[j - 1]);
            }
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 设置分页的参数
     * @param pd
     * @return
     */
    public static PageData setPageParam(PageData pd) {
        String page_now_str = pd.get("page_now").toString();
        int pageNowInt = Integer.parseInt(page_now_str) - 1;
        // 每页显示记录数
        String page_size_str = pd.get("page_size").toString();
        int pageSizeInt = Integer.parseInt(page_size_str);

        String page_now = pageNowInt + "";
        String page_start = (pageNowInt * pageSizeInt) + "";

        pd.put("page_now", page_now);
        pd.put("page_start", page_start);

        return pd;
    }

    /**
     * 设置list中的distance
     * @param list
     * @param pd
     * @return
     */
    public static List<PageData> setListDistance(List<PageData> list, PageData pd) {
        List<PageData> listReturn = new ArrayList<PageData>();
        String user_longitude = "";
        String user_latitude = "";

//        try {
//            // "117.11811";
//            user_longitude = pd.get("user_longitude").toString();
//            // "36.68484";
//            user_latitude = pd.get("user_latitude").toString();
//        } catch (Exception e) {
//            logger.error("缺失参数--user_longitude和user_longitude");
//            logger.error("lost param：user_longitude and user_longitude");
//        }
//
//        PageData pdTemp = new PageData();
//        int size = list.size();
//        for (int i = 0; i < size; i++) {
//            pdTemp = list.get(i);
//            String longitude = pdTemp.get("longitude").toString();
//            String latitude = pdTemp.get("latitude").toString();
//            String distance = MapDistance.getDistance(user_longitude, user_latitude, longitude, latitude);
//            pdTemp.put("distance", distance);
//            pdTemp.put("size", distance.length());
//            listReturn.add(pdTemp);
//        }

        return listReturn;
    }

    /**
     * 返回Json
     * @param pd
     * @param map
     * @return Object
     */
    public static Object returnObject(PageData pd, Object map) {
        if (pd.containsKey("callback")) {
            String callback = pd.get("callback").toString();
            return new JSONPObject(callback, map);
        } else {
            return map;
        }
    }

    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return 验证码
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                //double dblR = Math.random() * len;
                double dblR = ThreadLocalRandom.current().nextDouble()* len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

    /***
     * 取得总页数
     * @return
     */
    public static int getTotalPage(int totalResult){
        int totalPage = 0;
        if(totalResult%Const.SHOWCOUNT==0)
            totalPage = totalResult/Const.SHOWCOUNT;
        else
            totalPage = totalResult/Const.SHOWCOUNT+1;
        return totalPage;
    }
}
