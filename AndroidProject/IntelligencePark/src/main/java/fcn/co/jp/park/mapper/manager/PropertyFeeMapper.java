package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface PropertyFeeMapper {

    /**
     * 根据物业费Id和画面设置内容更新物业费
     * @param pd
     * @return
     */
    int updateByPrimaryKey(PageData pd);

    /**
     * 获取物业费列表信息
     * @return
     */
    List<PageData> getPropertyFeeList(PageData pd);

    /**
     * 获取物业费列表总件数
     * @return
     */
    Integer getPropertyFeeListCount();
}