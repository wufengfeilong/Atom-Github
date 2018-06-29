package fcn.co.jp.park.service.property;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface PropertyPayService {

    Map<String, Object> getWaterPayInfo(PageData pageData);

    Map<String, Object> getElectricPayInfo(PageData pageData);

    Map<String, Object> getPropertyPayInfo(PageData pageData);

    Map<String, Object> getRentPayInfo(PageData pageData);

    Map<String, Object> getTemporaryParkInfo(PageData pageData);

    Map<String, Object> getRentParkInfo(PageData pageData);

    /**
     * 获取水费缴费列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getWaterFeePaymentList(PageData pageData);

    /**
     * 获取电费缴费列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getElectricFeePaymentList(PageData pageData);

    /**
     * 获取物业费缴费列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getPropertyFeePaymentList(PageData pageData);

    /**
     * 获取租赁费缴费列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getRentFeePaymentList(PageData pageData);

    /**
     * 获取停车缴费列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getParkingPaymentList(PageData pageData);

    /**
     * 获取有效的月租车辆申请列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getRentParkList(PageData pageData);

    /**
     * 获取已通过审核待缴费的月租车辆申请列表
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> getPassedRentParkList(PageData pageData);


    /**
     * 删除某条月租车辆申请数据
     * @param pageData
     *        参数合集
     * @return 结果集合
     */
    Map<String, Object> deleteRentParkItem(PageData pageData);

}
