package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface RentFeeMapper {

    /**
     * 根据租赁费Id和画面设置内容更新租赁费
     * @param pd
     * @return
     */
    int updateByPrimaryKey(PageData pd);

    /**
     * 获取租赁费列表信息
     * @return
     */
    List<PageData> getRentFeeList(PageData pd);

    /**
     * 获取租赁费列表总件数
     * @return
     */
    Integer getRentFeeListCount();
}