package fcn.co.jp.park.service.rent.impl;


import fcn.co.jp.park.mapper.rent.RentMapper;
import fcn.co.jp.park.model.rent.ImageInfo;
import fcn.co.jp.park.model.rent.RentInfo;
import fcn.co.jp.park.service.rent.RentService;
import fcn.co.jp.park.util.AppUtil;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.FileUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(value = "rentService")
public class RentServiceImpl implements RentService {

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private FileUtil fileUtil;


    /**
     * 房屋添加初始化数据获取
     * @return
     */
    @Override
    public Map<String, Object> getRentAddInitData() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //获取房屋类型
        List<PageData> houseTypeList = rentMapper.getHouseTypeList("HOUSE_TYPE");
        //获取状态列表
        List<PageData> statusList = rentMapper.getStatusList("RENT_STATUS");
        if(houseTypeList!=null&&statusList!=null&&houseTypeList.size()>0&&statusList.size()>0){
            RentInfo rentInfo=new RentInfo();
            rentInfo.setHoustTypeList(houseTypeList);
            rentInfo.setStatusList(statusList);
            returnMap.put("result", true);
            returnMap.put("data",rentInfo);
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "获取数据失败");
        }
        return returnMap;
    }

    /**
     * 房屋信息添加
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> rentInfoAdd(PageData pd) {
        //添加到DB
        int result = rentMapper.rentInfoAdd(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(result>0){
            returnMap.put("result", true);
            returnMap.put("msg", "提交成功");
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "提交失败");
        }
        return returnMap;
    }

    /**
     * 房屋信息编辑
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> rentInfoEdit(PageData pd) {
        int result = rentMapper.rentInfoEdit(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(result>0){
            returnMap.put("result", true);
            returnMap.put("msg", "编辑内容提交成功");
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "编辑内容提交失败");
        }
        return returnMap;
    }

    /**
     * 房屋列表信息查询
     * @return
     */
    @Override
    public Map<String, Object> rentHouseList(PageData pd) {

        int pageNum = Integer.valueOf(pd.getString("pageNum"));
        pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
        pd.put("numE",Const.SHOWCOUNT);
        int count = rentMapper.getHouseCount(pd);
        //房屋列表信息查询
        List<PageData> result = rentMapper.rentHouseList(pd);
        //获取房屋图片，取第一张作为图片，没有的话就默认返回null
        for(PageData pageData : result){
            String imageId = String.valueOf(pageData.get("house_img"));
            if(imageId!=null&&!"".equals(imageId)){
                String[] images=imageId.split(",");
                pageData.put("house_img",fileUtil.getFilePath(Integer.parseInt(images[0])));
            }
        }

        Map<String,Object> re = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        re.put("listHouse",result);
        int totalPage = AppUtil.getTotalPage(count);
        if(pageNum >=  totalPage){
            re.put("isNext", false);
        }else{
            re.put("isNext", true);
        }
        if(result.size()>0){
            returnMap.put("result", true);
            returnMap.put("data",re);


            returnMap.put("totalPage", String.valueOf(totalPage));
            returnMap.put("msg", "获取房屋列表数据成功");
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "暂时没有数据");
        }
        return returnMap;
    }

    /**
     * 获取房屋详情
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> rentHouseDetail(PageData pd) {
        //根据房屋ID查询DB
        PageData resultData = rentMapper.rentHouseDetail(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> returnData = new HashMap<String, Object>();
        List<String> imageList = new ArrayList<String>();

        //房屋图片处理
        if(resultData!=null){
            String imageId = String.valueOf(resultData.get("house_img"));
            if(imageId!=null&&!"".equals(imageId)){
                String[] images=imageId.split(",");
                for(String picId:images){
                    imageList.add(fileUtil.getFilePath(Integer.parseInt(picId)));
                }
            }
            returnData.put("rentAddBean",resultData);
            returnData.put("imageList",imageList);
            returnMap.put("result", true);
            returnMap.put("data",returnData);
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "获取数据失败");
        }
        return returnMap;
    }

    /**
     * 获取房屋编辑初始化数据
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> rentHouseEditInit(PageData pd) {
        //根据房屋ID查询DB
        PageData resultData = rentMapper.rentHouseDetail(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> returnData = new HashMap<String, Object>();
        List<ImageInfo> imageList = new ArrayList<ImageInfo>();

        //获取房屋类型
        List<PageData> houseTypeList = rentMapper.getHouseTypeList("HOUSE_TYPE");
        //获取状态列表
        List<PageData> statusList = rentMapper.getStatusList("RENT_STATUS");
        RentInfo rentInfo=new RentInfo();
        if(houseTypeList!=null&&statusList!=null&&houseTypeList.size()>0&&statusList.size()>0){
            rentInfo.setHoustTypeList(houseTypeList);
            rentInfo.setStatusList(statusList);
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "获取数据失败");
            return returnMap;
        }

        //房屋图片处理
        if(resultData!=null){
            String imageId = String.valueOf(resultData.get("house_img"));
            if(imageId!=null&&!"".equals(imageId)){
                String[] images=imageId.split(",");
                for(String picId:images){
                    ImageInfo imageInfo = new ImageInfo(picId,fileUtil.getFilePath(Integer.parseInt(picId)),false);
                    imageList.add(imageInfo);
                }
            }
            returnData.put("rentAddBean",resultData);
            returnData.put("imageList",imageList);
            returnData.put("rentInitBean",rentInfo);
            returnMap.put("result", true);
            returnMap.put("data",returnData);
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "获取数据失败");
        }
        return returnMap;
    }

    /**
     * 获取已发布房屋信息
     * @param pd
     * @return
     */
    @Override
    public Map<String, Object> rentReleasedHouse(PageData pd) {

        int pageNum = Integer.valueOf(pd.getString("pageNum"));
        pd.put("numS", (pageNum - 1) * Const.SHOWCOUNT);
        pd.put("numE",Const.SHOWCOUNT);
        int count = rentMapper.getHouseCount(pd);
        List<PageData> result = rentMapper.rentReleasedHouseList(pd);

        //获取房屋图片，取第一张作为图片，没有的话就默认返回null
        String imageId;
        for(PageData pageData : result){
            imageId = String.valueOf(pageData.get("house_img"));
            if(imageId !=null && !"".equals(imageId)){
                String[] images=imageId.split(",");
                pageData.put("house_img",fileUtil.getFilePath(Integer.parseInt(images[0])));
            }
        }

        Map<String,Object> houseList = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        houseList.put("listReleasedHouse",result);
        int totalPage = AppUtil.getTotalPage(count);
        if(pageNum >=  totalPage){
            houseList.put("isNext", false);
        }else{
            houseList.put("isNext", true);
        }
        if (houseList.size() > 0) {
            returnMap.put("result", true);
            returnMap.put("data",houseList);
            returnMap.put("totalPage", String.valueOf(totalPage));
            returnMap.put("msg", "获取房屋列表数据成功");
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "暂时没有租赁信息");
        }
        return returnMap;

    }
}
