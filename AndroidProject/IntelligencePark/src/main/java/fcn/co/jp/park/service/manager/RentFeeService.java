package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface RentFeeService {

    /**
     * 根据租赁费Id和画面设置内容更新租赁费
     * @param pd
     * @return
     */
    Map<String, Object> rentFeeEdit(PageData pd) throws Exception;

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
