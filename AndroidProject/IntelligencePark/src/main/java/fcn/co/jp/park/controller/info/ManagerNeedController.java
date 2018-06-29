package fcn.co.jp.park.controller.info;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.info.ManagerNeedService;
import fcn.co.jp.park.service.manager.PostNewsService;
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
@RequestMapping(value = "/managerdemandapp")
public class ManagerNeedController extends BaseController{
    @Autowired
    private ManagerNeedService managerNeedService;

    /**
     * 需求列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/demandlist")
    @ResponseBody
    public Object business() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> demandlist =null;
        PageData pd = this.getPageData();
        try{
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
            pd.put("numE",Const.SHOWCOUNT);
            demandlist = (List<PageData>) managerNeedService.getDemandList(pd);
            int count = managerNeedService.getDemandCount(pd);
            int totalPage = AppUtil.getTotalPage(count);
            data.put("isNext", true);
            if(pageNum >=  totalPage){
                data.put("isNext", false);
            }
            data.put("demandlist",demandlist );
            data.put("totalPage", String.valueOf(totalPage));
            returnMap.put("result", true);
            returnMap.put("msg", "企业需求");
            returnMap.put("data", data );
        }catch (Exception e){
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
            return returnMap;
        }
        return returnMap;
    }

    /**
     * 根据需求id，删除相应的条目
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletedemand")
    public Object deletePostNewsById() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int deleteResult = -1;
        try {
            deleteResult = managerNeedService.deleteDemandById(pd);
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
     * 新建发布需求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertDemand", produces = {"application/json;charset=UTF-8"})
    public Object insertDemand() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int insertResult = -1;
        try{
            insertResult = managerNeedService.insertDemand(pd);
            returnMap.put("data", insertResult);
            returnMap.put("result", true);
            returnMap.put("msg", "插入成功！");
        }catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", insertResult);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

    /**
     * 需求详情
     * @return
     */
    @RequestMapping(value = "/managerDemandDetail")
    @ResponseBody
    public Object managerDemandDetail(){
        Map<String,Object> data = new HashMap<String, Object>();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try{
            Map<String,Object> managerDemandDetail = (Map<String, Object>)managerNeedService.getDemandDetail(pd);
            //  data.put("demandInfo",demandInfo);
            returnMap.put("data",managerDemandDetail);
            returnMap.put("msg","企业需求详情");
            returnMap.put("result",true);
            return  returnMap;

        }catch (Exception e){
            returnMap.put("msg","系统异常，请稍后再试");
            returnMap.put("result",false);
        }
        return  returnMap;
    }

    /**
     * 新建发布需求
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getmanagerDemandEdit", produces = {"application/json;charset=UTF-8"})
    public Object managerDemandEdit() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<>();
        int editResult = -1;
        try{
            editResult = managerNeedService.managerDemandEdit(pd);
            returnMap.put("data", editResult);
            returnMap.put("result", true);
            returnMap.put("msg", "编辑成功！");
        }catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("data", editResult);
            returnMap.put("msg", "系统异常,请稍后再试 e = " + e);
        }
        return returnMap;
    }

}
