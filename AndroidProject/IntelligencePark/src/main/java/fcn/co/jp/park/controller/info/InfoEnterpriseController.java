package fcn.co.jp.park.controller.info;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.info.InfoEnterpriseService;
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
@RequestMapping(value = "/businessapp")
public class InfoEnterpriseController extends BaseController{
    @Autowired
    private InfoEnterpriseService enterpriseAppService;

    /**
     * 企业列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/business")
    @ResponseBody
    public Object business() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        List<PageData> businesslist =null;
        PageData pd = this.getPageData();
        try{
            int pageNum = Integer.valueOf(pd.getString("pageNum"));
            pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
            pd.put("numE",Const.SHOWCOUNT);
            businesslist = (List<PageData>) enterpriseAppService.getBusinessList(pd);
            int count = enterpriseAppService.getBusinesCount(pd);
            int totalPage = AppUtil.getTotalPage(count);
            data.put("isNext", true);
            if(pageNum >=  totalPage){
                data.put("isNext", false);
            }
            data.put("businesslist",businesslist );
            data.put("totalPage", String.valueOf(totalPage));
            returnMap.put("result", true);
            returnMap.put("msg", "企业列表");
            returnMap.put("data", data );
        }catch (Exception e){
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
            return returnMap;
        }
        return returnMap;
    }

    /**
     * 企业详细信息
     * @return
     */
    @RequestMapping(value="businessinfo")
    @ResponseBody
    public Object businessinfo() {
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try{
            pd.put("USERID", pd.get("companyId"));
            Map<String, Object> businessinfo =(Map<String, Object>) enterpriseAppService.getBusinessInfo(pd);
            List<PageData> comPictUploadList = (List<PageData>)enterpriseAppService.getComPictUpload(pd);
            data.put("comPictUploadList", comPictUploadList);
            data.put("businessinfo", businessinfo);
            returnMap.put("data", data);
            returnMap.put("msg", "企业信息详情");
            returnMap.put("result", true);
            return returnMap;
        }catch (Exception e){
            returnMap.put("msg", "系统异常,请稍后再试");
            returnMap.put("result", false);
        }
        return returnMap;
    }




}
