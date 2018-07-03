package sdwxwx.com.util;

import java.util.List;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.home.bean.CityEntity;
import sdwxwx.com.home.model.PickCityModel;

/**
 * Created by 860115025 on 2018/06/20.
 */
public class CityUtil {
    /** 城市名称 */
    private static String name;

    /** 城市ID */
    private static String id;

    /**
     * 通过城市ID获取城市名称
     * @param cityId
     * @return
     */
    public static String getCityName(final String cityId) {
        // 调用之前进行清空操作
        name = "未知城市";
        // 获取城市列表
        PickCityModel.getCities(new BaseCallback<List<CityEntity>>(){
            @Override
            public void onSuccess(List<CityEntity> data) {
                // 循环查找ID一致的城市
                for (CityEntity cityEntity : data) {
                    if (cityEntity.getId().equals(cityId)) {
                        name = cityEntity.getName();
                        break;
                    }
                }
            }
            // 如果失败，则返回未知城市
            @Override
            public void onFail(String msg) {
            }
        });
        return name;
    }

    /**
     * 通过城市名称获取城市ID
     * @param cityName
     * @return
     */
    public static String getCityId(final String cityName) {
        // 调用之前先进行初期化
        id = "0";
        // 获取城市列表
        PickCityModel.getCities(new BaseCallback<List<CityEntity>>(){
            @Override
            public void onSuccess(List<CityEntity> data) {
                // 循环查找ID一致的城市
                for (CityEntity cityEntity : data) {
                    if (cityEntity.getName().equals(cityName)) {
                        id = cityEntity.getId();
                        break;
                    }
                }
            }
            // 如果失败，则返回未知城市
            @Override
            public void onFail(String msg) {
            }
        });
        return id;
    }
}
