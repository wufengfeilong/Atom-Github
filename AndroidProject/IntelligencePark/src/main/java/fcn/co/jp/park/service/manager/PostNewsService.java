package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

/**
 * 管理中心的新闻发布功能用Service接口
 */
public interface PostNewsService {

    /**
     * 获取新闻列表信息
     * @return
     */
    List<PageData> getPostNewsList(PageData pd);

    /**
     * 获取新闻的总条目数
     * @param pd
     * @return
     */
    int getNewsCount(PageData pd);

    /**
     * 根据新闻Id获取新闻的详细内容
     * @param pd
     * @return
     */
    Map<String, Object> getPostNewsInfoById(PageData pd);

    /**
     * 根据新闻Id删除相应的新闻条目
     * @param pd
     * @return
     */
    int deletePostNewsById(PageData pd);

    /**
     * 追加新闻
     * @param pd
     * @return
     */
    int insertPostNews(PageData pd);

    /**
     * 根据新闻Id来更新相应的新闻内容
     * @param pd
     * @return
     */
    int updatePostNewsById(PageData pd);
}
