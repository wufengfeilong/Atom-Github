package fcn.co.jp.park.mapper.info;

import fcn.co.jp.park.util.PageData;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public interface InfoNewsMapper {

    /**
     * 通过区分key获取项目区分表中的id，value
     * @param pd
     * @return
     */
    List<PageData> findListByKey(PageData pd);
    /**
     * 新闻数量
     * @param pd
     * @return
     */
    int findNewsCount(PageData pd);

    /**
     * 新闻列表
     * @param pd
     * @return
     */
    List<PageData> findNewslist(PageData pd);

    /**
     * 通过新闻ID获取数据
     * @param pd
     * @return
     */
    Map<String, Object> findByNewsId(PageData pd);

}
