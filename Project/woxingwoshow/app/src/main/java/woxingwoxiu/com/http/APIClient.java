package woxingwoxiu.com.http;

import android.util.Log;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import woxingwoxiu.com.cons.Constant;
import woxingwoxiu.com.util.MD5Util;

import java.io.File;
import java.util.*;

/**
 * create by 860115039
 * date      2018/5/8
 * time      12:44
 */
public class APIClient {
    private static final String TAG = "APIClient";
    /**
     * 获取请求请求网络用的Service
     *
     * @return
     */
    public static APIService getAPIService() {
        return RetrofitManager.getInstance().getAPIService();
    }


    /**
     * 单图片上传的Body
     * 在{@link APIService}中需要如下定义接口
     * ----@POST("Api/Hx/AddHx")
     * ----@Multipart
     * Observable<> uploadGroupCreate(@Part MultipartBody.Part photo);
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part getFileBody(String part, File file) {
        Log.d("需要上传图片的大小和路径", "图片大小：" + file.length() + "图片路径：" + file.getPath());
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        String fileName = file.getName();
        return MultipartBody.Part.createFormData(part, fileName, photoRequestBody);
    }

    /**
     * 生成签名
     * @param map
     * @return
     */
    public static String getSign(Map<String, Object> map) {

        String result = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = String.valueOf(item.getValue());
                    if (!(val == "" || val == null)) {
                        sb.append(key+"=" + val + "&");
                    }
                }

            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("#woxingwoxiu");
            result = sb.toString();
            Log.d(TAG, "getSign: result="+result);
            //进行MD5加密
            result = MD5Util.encrypt(result);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static long getTimes(){
        return System.currentTimeMillis();
    }

    public static String getPostUrl(String address,long times,String signature){
        return Constant.HTTP_BASE_HOST+"address?timestamp="+times+"&signature="+signature;
    }

}
