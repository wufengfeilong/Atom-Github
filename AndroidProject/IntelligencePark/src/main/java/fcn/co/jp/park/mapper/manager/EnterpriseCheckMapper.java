package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface EnterpriseCheckMapper {

    /**
     * 企业审核列表
     * @return
     */

    List<PageData> selectEnterpriseCheckList();

    /**
     *根据企业的id，获取企业详情
     * @param pd
     * @return
     */
    PageData getEnterpriseCheckDetailById(PageData pd);

    /**
     * 企业审核通过
     * @param pd
     * @return
     */
    int updateEnterpriseCheckParkStatus(PageData pd);

    /**
     * 审核通过,信息表插入数据
     * @param pd
     * @return
     */
    int insertEnterpriseCheckPassMessage(PageData pd);

    /**
     * 把个人角色改为企业角色
     * @param pd
     * @return
     */
    int UpdateEnterpriseCheckUserRole(PageData pd);

    /**
     * 企业审核驳回,信息表插入数据
     * @param pd
     * @return
     */
    int insertEnterpriseCheckTurnMessage(PageData pd);

    /**
     * 企业驳回
     * @param pd
     * @return
     */
    int enterpriseCheckTurnClick(PageData pd);
}
