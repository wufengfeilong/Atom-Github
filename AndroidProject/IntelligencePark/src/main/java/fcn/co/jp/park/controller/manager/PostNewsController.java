package fcn.co.jp.park.controller.manager;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.PostNewsService;
import fcn.co.jp.park.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理中心的新闻发布功能用Controller
 */
@Controller
@RequestMapping(value = "/newsInfo")
public class PostNewsController extends BaseController {

    @Autowired
    private PostNewsService postNewsService;

    @Autowired
    private FileUtil fileUtil;

    /**
     * 获取新闻列表的List内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/newsList", produces = {"application/json;charset=UTF-8"})
    public Object managerNewsList() {
        PageData pd = this.getPageData();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> newsList = null;
        Map<String, Object> returnMap = new HashMap<>();
        try {
            pd.put("category", pd.get("category"));
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
            pd.put("numE", Const.SHOWCOUNT);
            newsList = postNewsService.getPostNewsList(pd);

            int count = postNewsService.getNewsCount(pd);
            int totalPage = AppUtil.getTotalPage(count);
            data.put("isNext", true);
            if (pageNum >= totalPage) {
                data.put("isNext", false);
            }
            // 设定新闻列表的查询结果
            data.put("listNews", newsList);
            // 设定总页数
            data.put("totalPage", String.valueOf(totalPage));
            returnMap.put("data", data);
            returnMap.put("result", true);
            returnMap.put("msg", "查询成功");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
            returnMap.put("data", newsList);
        }
        return returnMap;
    }


    /**
     * 根据新闻的news_id，获取新闻的详情内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/newsDetail", produces = {"application/json;charset=UTF-8"})
    public Object getPostNewsDetailById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        try {
            returnMap = postNewsService.getPostNewsInfoById(pd);
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

    /**
     * 根据新闻的news_id，删除相应的新闻条目
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteNewsItem")
    public Object deletePostNewsById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int deleteResult = -1;
        try {
            deleteResult = postNewsService.deletePostNewsById(pd);
            returnMap.put("data", deleteResult);
            returnMap.put("result", true);
            returnMap.put("msg", "删除成功！");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", deleteResult);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 新建发布新闻
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertPostNews", produces = {"application/json;charset=UTF-8"})
    public Object insertPostNews(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> file = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
//        pd.put("category", "0"); // 发布类型为：新闻
        if (file != null) {
            String fileId = fileUtil.fileUp(Const.NEWS_PICTURE, file.get("newsThumbnail").get(0));
            pd.put("newsThumbnail", fileId);
        }
        int insertResult = postNewsService.insertPostNews(pd);
        Map<String, Object> resultMap = new HashMap<>();
        if (insertResult > 0) {
            resultMap.put("data", insertResult);
            resultMap.put("result", true);
            resultMap.put("msg", "插入成功！");
        } else {
            resultMap.put("data", insertResult);
            resultMap.put("result", false);
            resultMap.put("msg", "插入失败,请稍后再试!");
        }
        return resultMap;
    }

    /**
     * 根据新闻的news_id，更新新闻的内容
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePostNews", produces = {"application/json;charset=UTF-8"})
    public Object updatePostNews(HttpServletRequest request) {
        MultiValueMap<String, MultipartFile> file = ((StandardMultipartHttpServletRequest) request).getMultiFileMap();
        PageData pd = this.getPageData();
        if (file != null) {
            String fileId = fileUtil.fileUp(Const.NEWS_PICTURE, file.get("newsThumbnail").get(0));
            pd.put("newsThumbnail", fileId);
        }
        Map<String, Object> returnMap = new HashMap<>();
        int insertResult = -1;
        try {
            insertResult = postNewsService.updatePostNewsById(pd);
            returnMap.put("data", insertResult);
            returnMap.put("result", true);
            returnMap.put("msg", "更新成功！");
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", insertResult);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }
}
