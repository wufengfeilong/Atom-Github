package fcn.co.jp.park.service.me;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface MeCarService {
    /**
     * 获取我的车辆信息
     *
     * @return
     */
    Map<String, Object> getCarList();

    /**
     * 修改我的车辆信息
     *
     * @return
     */
    Map<String, Object> getCarEditor(PageData pd);

    /**
     * 追加我的车辆信息
     *
     * @return
     */
    Map<String, Object> getAddCar(PageData pd);

    /**
     * 根据车辆列表Id删除相应的条目
     *
     * @param pd
     * @return
     */
    int deleteCarById(PageData pd);


}
