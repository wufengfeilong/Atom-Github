package util;

import com.alibaba.fastjson.JSON;
/**
 * Json工具类
 * @author Kang
 *
 */
public class JsonUtils {
	/**
	 * 将对象转化为json字符串
	 * @param obj
	 * @return
	 */
	public  static String obj2Json(Object obj){
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 将json字符串转化为java对象
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json,Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}
}

