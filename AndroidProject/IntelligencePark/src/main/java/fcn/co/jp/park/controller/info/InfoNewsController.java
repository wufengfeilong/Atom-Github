package fcn.co.jp.park.controller.info;


import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.info.InfoNewsService;
import fcn.co.jp.park.util.AppUtil;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/newsapp")
public class InfoNewsController extends BaseController{

    @Autowired
    private InfoNewsService newsAppService;
    /**
     * 新闻类型标题
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="/newstype")
    public Object newsType() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try{
            pd.put("distinguish_key", "NEWS_TYPE");
            List<PageData> data = newsAppService.findListByKey(pd);
            returnMap.put("result", true);
            returnMap.put("msg", "新闻类别");
            returnMap.put("data", data );
        }catch (Exception e){
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
            return returnMap;
        }
        return returnMap;
    }

    /**
     * 新闻列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/newslist")
    @ResponseBody
    public Object terracenewslist() {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try{
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1)* Const.SHOWCOUNT);
            pd.put("numE",Const.SHOWCOUNT);
            List<PageData> getlistNews = newsAppService.getListAllNews(pd);
            int count = newsAppService.getNewsCount(pd);
            int totalPage = AppUtil.getTotalPage(count);
            data.put("isNext", true);
            if(pageNum >=  totalPage){
                data.put("isNext", false);
            }
            data.put("getlistNews", getlistNews);
            data.put("totalPage", String.valueOf(totalPage));
            returnMap.put("result", true);
            returnMap.put("data", data );
            returnMap.put("msg", "新闻列表");
        }catch (Exception e){
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
            return returnMap;
        }
        return returnMap;
    }
    /**
     * 新闻内容详细
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/newsdetail")
    @ResponseBody
    public Object newsdetail() {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try{
            data = (Map<String, Object>) newsAppService.findByNewsId(pd);
            returnMap.put("result", true);
            returnMap.put("msg", "新闻内容详细");
            returnMap.put("data", data );
        }catch (Exception e){
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
            return returnMap;
        }
        return returnMap;
    }

}

