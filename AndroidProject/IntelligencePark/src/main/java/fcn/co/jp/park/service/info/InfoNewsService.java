package fcn.co.jp.park.service.info;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface InfoNewsService {
    /**
     * 获取新闻数量
     * @param pd
     * @return
     * @throws Exception
     */
    int getNewsCount(PageData pd) throws Exception;

    /**
     * 获取新闻列表
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> getListAllNews(PageData pd) throws Exception;


    /**
     * 通过id获取新闻数据
     * @param pd
     * @return
     * @throws Exception
     */
    Map<String, Object> findByNewsId(PageData pd) throws Exception;

    /**
     * 通过区分key获取项目区分表中的id，value
     * @param pd
     * @return
     * @throws Exception
     */
    List<PageData> findListByKey(PageData pd) throws Exception;


}
