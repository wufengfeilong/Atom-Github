package fcn.co.jp.park.service.me.impl;

import fcn.co.jp.park.mapper.me.CompanyMapper;
import fcn.co.jp.park.model.Picture;
import fcn.co.jp.park.service.me.CompanyService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:52
 */
@Service(value = "companyService")
public class CompanyImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;


    @Override
    public int addImg(PageData pd) {
        return companyMapper.insertImge(pd);
    }

    @Override
    public int selectImgId(String fileRealPath) {
        return companyMapper.selectImgId(fileRealPath);
    }

    @Override
    public Map<String, Object> insertCompanyInfo(PageData pd) {
        int result = companyMapper.insertCompanyInfo(pd);

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(result>0){
            returnMap.put("result", true);
            returnMap.put("msg", "保存成功");
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "保存失败");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> updateCompanyInfo(PageData pd) {
        int result = companyMapper.updateCompanyInfo(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(result>0){
            returnMap.put("result", true);
            returnMap.put("msg", "保存成功");
        }else{
            returnMap.put("result", false);
            returnMap.put("msg", "保存失败");
        }
        return returnMap;
    }

    @Override
    public int selectCompanyAuthResult(String userId) {
        return companyMapper.selectCompanyAuthResult(userId);
    }

    @Override
    public int insertBanners(PageData pd) {
        return companyMapper.insertBanners(pd);
    }

    @Override
    public int updateBanners(PageData pd) {
        return companyMapper.updateBanners(pd);
    }

    @Override
    public int updateIntroduce(PageData pd) {
        return companyMapper.updateIntroduce(pd);
    }

    @Override
    public String getCompanyIntroduceInfo(String userId) {
        return companyMapper.getCompanyIntroduceInfo(userId);
    }

    @Override
    public List<Picture> getCompanyPictureInfo(String userId) {
        List<Picture> pdList = companyMapper.getCompanyPictureInfo(userId);
        System.out.println(pdList.toString());

        return pdList;
    }
}
