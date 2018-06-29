package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.PostNewsMapper;
import fcn.co.jp.park.service.manager.PostNewsService;
import fcn.co.jp.park.util.PageData;
import fcn.co.jp.park.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理中心的新闻发布功能用Service接口的实现类
 */
@Service(value = "postNewsService")
public class PostNewsServiceImpl implements PostNewsService {

    @Autowired
    private PostNewsMapper mPostNewsMapper;

    /**
     * 获取新闻列表所有的List内容
     * @return
     */
    @Override
    public List<PageData> getPostNewsList(PageData pd) {
        return mPostNewsMapper.getPostNewsList(pd);
    }

    /**
     * 获取新闻的总条目数
     * @param pd
     * @return
     */
    @Override
    public int getNewsCount(PageData pd) {
        Integer returnMap = 0;
        if(StringUtil.isEmpty(pd.getString("category"))){
            pd.put("category", pd.get("category"));
            returnMap =mPostNewsMapper.selectNewsCount(pd);
        }else {
            returnMap =mPostNewsMapper.selectNewsCount(pd);
        }
        return returnMap;
    }

    /**
     * 根据新闻的news_id，获取新闻的详情内容
     * @param pd：新闻ID
     * @return
     */
    @Override
    public Map<String, Object> getPostNewsInfoById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        PageData param = new PageData();
        String newsId = pd.getString("news_id");
        param.put("newsId", newsId); // 此处的newsId要和给Mapper.xml中传值的字段名保持一致
        PageData result = mPostNewsMapper.getPostNewsInfoById(param);
        if (result != null) {
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
            returnMap.put("data", result);
            return returnMap;
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "查询失败");
            returnMap.put("data", result);
            return returnMap;
        }
    }

    /**
     * 根据新闻的news_id，删除相应的新闻条目
     * @param pd
     * @return
     */
    @Override
    public int deletePostNewsById(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        PageData param = new PageData();
        String newsId = pd.getString("news_id");
        param.put("newsId", newsId);
        int result = mPostNewsMapper.deletePostNewsById(param);
        return result;
    }

    /**
     * 发布新闻、公告、活动内容
     * @return
     */
    @Override
    public int insertPostNews(PageData pd) {
        int result = mPostNewsMapper.insertPostNews(pd);
        return result;
    }

    /**
     * 更新新闻、公告、活动的内容
     * @return
     */
    @Override
    public int updatePostNewsById(PageData pd) {
        int result = mPostNewsMapper.updatePostNewsById(pd);
        return result;
    }
}
