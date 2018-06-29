package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface WaterFeeMapper {

    /**
     * 根据水费Id和画面设置内容更新水费
     * @param pd
     * @return
     */
    int updateByPrimaryKey(PageData pd);

    /**
     * 获取水费列表信息
     * @return
     */
    List<PageData> getWaterFeeList(PageData pd);

    /**
     * 获取水费列表总件数
     * @return
     */
    Integer getWaterFeeListCount();
}