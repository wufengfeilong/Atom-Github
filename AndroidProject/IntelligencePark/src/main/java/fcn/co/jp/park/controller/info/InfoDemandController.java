package fcn.co.jp.park.controller.info;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.info.InfoDemandService;
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
@RequestMapping(value = "/demandapp")
public class InfoDemandController extends BaseController {
    @Autowired
    private InfoDemandService demandAppService;
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
            demandlist = (List<PageData>) demandAppService.getDemandList(pd);
            int count = demandAppService.getDemandCount(pd);
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
     * 需求详情
     * @return
     */
    @RequestMapping(value = "/demandinfo")
    @ResponseBody
    public Object demandInfo(){
        //Map<String,Object> data = new HashMap<String, Object>();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try{
            Map<String,Object> demandInfo = (Map<String, Object>)demandAppService.getDemandInfo(pd);
          //  data.put("demandInfo",demandInfo);
            returnMap.put("data",demandInfo);
            returnMap.put("msg","企业需求详情");
            returnMap.put("result",true);
            return  returnMap;

        }catch (Exception e){
            returnMap.put("msg","系统异常，请稍后再试");
            returnMap.put("result",false);
        }
        return  returnMap;
    }



}
