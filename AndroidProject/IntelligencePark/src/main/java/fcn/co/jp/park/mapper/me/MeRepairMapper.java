package fcn.co.jp.park.mapper.me;

import fcn.co.jp.park.util.PageData;
import java.util.List;

public interface MeRepairMapper {
    /**
     * 查看个人报修列表
     */
    List<PageData> selectRepairRecordList (PageData pd);

    /**
     * 查看报修列表详情
     */
    PageData  getRepairDetailById(PageData pd);
}
