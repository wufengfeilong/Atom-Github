package fcn.co.jp.park.mapper.property;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface PropertyPayMapper {

    /**
     * 取得水费缴费信息
     * @param pageData
     * @return
     */
    PageData selectWaterPayInfo(PageData pageData);

    /**
     * 取得电费缴费信息
     * @param pageData
     * @return
     */
    PageData selectElectricPayInfo(PageData pageData);

    /**
     * 取得物业费缴费信息
     * @param pageData
     * @return
     */
    PageData selectPropertyPayInfo(PageData pageData);

    /**
     * 取得租赁费缴费信息
     * @param pageData
     * @return
     */
    PageData selectRentPayInfo(PageData pageData);

    /**
     * 取得临时停车费缴费信息
     * @param pageData
     * @return
     */
    PageData selectTemporaryParkInfo(PageData pageData);

    /**
     * 取得月租停车费缴费信息
     * @param pageData
     * @return
     */
    PageData selectRentParkInfo(PageData pageData);

    /**
     * 从数据库中检索出水费缴费信息列表
     * @param pageData
     *         参数合集
     * @return 水费缴费信息列表
     */
    List<PageData> selectWaterFeePaymentList(PageData pageData);

    /**
     * 从数据库中检索出电费缴费信息列表
     * @param pageData
     *         参数合集
     * @return 电费缴费信息列表
     */
    List<PageData> selectElectricFeePaymentList(PageData pageData);

    /**
     * 从数据库中检索出物业费缴费信息列表
     * @param pageData
     *         参数合集
     * @return 物业费缴费信息列表
     */
    List<PageData> selectPropertyFeePaymentList(PageData pageData);

    /**
     * 从数据库中检索出租赁费缴费信息列表
     * @param pageData
     *         参数合集
     * @return 租赁费缴费信息列表
     */
    List<PageData> selectRentFeePaymentList(PageData pageData);

    /**
     * 从数据库中检索出停车费缴费信息列表
     * @param pageData
     *         参数合集
     * @return 停车费缴费信息列表
     */
    List<PageData> selectParkingPaymentList(PageData pageData);

    /**
     * 从数据库中检索出符合条件的月租车辆申请信息列表
     * @param pageData
     *         转换后的参数合集
     * @return 转换后的结果
     */
    List<PageData> selectRentParkList(PageData pageData);

    /**
     * 从数据库中检索出已通过申请待缴费的月租车辆申请信息列表
     * @param pageData
     *         转换后的参数合集
     * @return 转换后的结果
     */
    List<PageData> selectPassedRentParkList(PageData pageData);


    /**
     * 从数据库中删除符合条件的月租车辆申请信息
     * @param pageData
     *         转换后的参数合集
     * @return 转换后的结果
     */
    int deleteRentParkItem(PageData pageData);

}
