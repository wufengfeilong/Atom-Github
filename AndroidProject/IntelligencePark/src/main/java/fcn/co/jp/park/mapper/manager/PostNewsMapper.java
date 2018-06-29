package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

/**
 * 管理中心的新闻发布功能用Mapper
 */
public interface PostNewsMapper {

    /**
     * 前台传入的新闻图片插入到DB
     * @param pd
     * @return
     */
    int insertImg(PageData pd);

    /**
     * 获取插入的图片的ID
     * @param fileRealPath
     * @return
     */
    int selectUploadImgId(String fileRealPath);

    /**
     * 前台传入的新闻记录插入到DB
     * @param pd
     * @return
     */
    int insertPostNews(PageData pd);

    /**
     * 根据传入的新闻news_id，更新相应的新闻内容
     * @param pd
     * @return
     */
    int updatePostNewsById(PageData pd);

    /**
     *根据新闻的news_id，删除相应的新闻条目
     * @param pd
     * @return
     */
    int deletePostNewsById(PageData pd);

    /**
     * 查询新闻列表内容
     * @return
     */
    List<PageData> getPostNewsList(PageData pd);

    /**
     * 根据新闻的news_id，获取新闻详情
     * @param pd
     * @return
     */
    PageData getPostNewsInfoById(PageData pd);

    /**
     * 查询新闻的总条目数
     * @param pd
     * @return
     */
    int selectNewsCount(PageData pd);
}
