package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

/**
 * 广告套餐类型数据的取得与更新
 */
public interface AdvertisingFeeEditService {

    /**
     * 查询套餐类型的内容
     * @return
     */
    Map<String, Object> getAdvertisingFeeEditData();

    /**
     * 更新广告套餐一费用的信息
     * @param pd
     * @return
     */
    int updateAdvertisingSet1FeeData(PageData pd);

    /**
     * 更新广告套餐二费用的信息
     * @param pd
     * @return
     */
    int updateAdvertisingSet2FeeData(PageData pd);

    /**
     * 更新广告套餐三费用的信息
     * @param pd
     * @return
     */
    int updateAdvertisingSet3FeeData(PageData pd);
}
