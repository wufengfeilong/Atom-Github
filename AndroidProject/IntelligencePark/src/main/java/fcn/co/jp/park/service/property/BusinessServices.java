package fcn.co.jp.park.service.property;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

/**
 * 查询商家信息用Service接口
 */
public interface BusinessServices {
    /**
     * 获取送水商家信息
     *
     * @return
     */
    Map<String, Object> getDeliverWaterList(PageData pd);

    /**
     * 获取搬家商家信息
     *
     * @return
     */
    Map<String, Object> getMoveHouseList(PageData pd);

    /**
     * 获取清洗商家信息
     *
     * @return
     */
    Map<String, Object> getCleanList(PageData pd);

    /**
     * 获取植物租赁信息
     *
     * @return
     */
    Map<String, Object> getPlantLeaseList(PageData pd);

    /**
     * 获取送水列表总件数
     *
     * @return
     */
    int getDeliverWaterListCount();

    /**
     * 获取搬家列表总件数
     *
     * @return
     */
    int getMoveHouseListCount();

    /**
     * 获取清洁列表总件数
     *
     * @return
     */
    int getCleanListCount();

    /**
     * 获取绿植租赁列表总件数
     *
     * @return
     */
    int getPlantLeaseListCount();
}
