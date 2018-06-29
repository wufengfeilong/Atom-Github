package fcn.co.jp.park.service.info.impl;

import fcn.co.jp.park.mapper.info.InfoNewsMapper;
import fcn.co.jp.park.service.info.InfoNewsService;
import fcn.co.jp.park.util.PageData;
import fcn.co.jp.park.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service(value = "newsAppService")
public class InfoNewsServiceImpl implements InfoNewsService {

    @Autowired
    private InfoNewsMapper infoNewsMapper;//这里会报错，但是并不会影响
    /**
     * 获取新闻数量
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public int getNewsCount(PageData pd) throws Exception {
        Integer returnMap = 1;
        if(StringUtil.isEmpty(pd.getString("category"))){
            pd.put("category", "0");
            returnMap =infoNewsMapper.findNewsCount(pd);
        }else {
            returnMap =infoNewsMapper.findNewsCount(pd);
        }
        return returnMap;
    }

    /**
     * 获取新闻列表
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public List<PageData> getListAllNews(PageData pd) throws Exception {
        List<PageData> returnMap = null;
        if(StringUtil.isEmpty(pd.getString("category"))){
            pd.put("category", "0");
            returnMap=(List<PageData>) infoNewsMapper.findNewslist(pd);
        }else {
            returnMap=(List<PageData>) infoNewsMapper.findNewslist(pd);
        }
        return returnMap;
    }

    /**
     * 通过id获取新闻数据
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findByNewsId(PageData pd) throws Exception {
        return infoNewsMapper.findByNewsId(pd);
    }

    /**
     * 通过区分key获取项目区分表中的id，value
     * @param pd
     * @return
     * @throws Exception
     */

    @Override
    public List<PageData> findListByKey(PageData pd) throws Exception {
        return (List<PageData>) infoNewsMapper.findListByKey(pd);
    }
}
