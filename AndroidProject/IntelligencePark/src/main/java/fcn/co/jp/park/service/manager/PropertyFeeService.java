package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface PropertyFeeService {

    /**
     * 根据物业费Id和画面设置内容更新物业费
     * @param pd
     * @return
     */
    Map<String, Object> propertyFeeEdit(PageData pd) throws Exception;

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
