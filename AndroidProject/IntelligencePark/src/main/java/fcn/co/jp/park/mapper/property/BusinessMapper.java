package fcn.co.jp.park.mapper.property;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface BusinessMapper {

    /**
     * 查询送水商家列表内容
     *
     * @return
     */
    List<PageData> getDeliverWaterList(PageData pd);

    /**
     * 查询搬家商家列表内容
     *
     * @return
     */
    List<PageData> getMoveHouseList(PageData pd);

    /**
     * 查询送水商家列表内容
     *
     * @return
     */
    List<PageData> getCleanList(PageData pd);

    /**
     * 查询绿植租赁商家列表内容
     *
     * @return
     */
    List<PageData> getPlantLeaseList(PageData pd);

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
