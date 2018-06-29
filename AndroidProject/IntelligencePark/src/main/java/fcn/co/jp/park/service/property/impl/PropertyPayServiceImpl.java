package fcn.co.jp.park.service.property.impl;

import fcn.co.jp.park.mapper.property.PropertyPayMapper;
import fcn.co.jp.park.service.property.PropertyPayService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "propertyPayService")
public class PropertyPayServiceImpl implements PropertyPayService {

    @Autowired
    private PropertyPayMapper propertyPayMapper;

    @Override
    public Map<String, Object> getWaterPayInfo(PageData pageData) {
        Map<String, Object> resultMap = new HashMap<>();
        // 从数据库检索出未缴费项目
        PageData waterPay = propertyPayMapper.selectWaterPayInfo(pageData);
        if (null != waterPay) {
            // 水费未缴费项目：有
            waterPay.put("payStatus", false);
            resultMap.put("data", waterPay);
            resultMap.put("msg", "数据访问成功！");
            resultMap.put("result", true);
        } else {
            // 水费未缴费项目：无
            waterPay = new PageData();
            waterPay.put("payStatus", true);
            resultMap.put("data", waterPay);
            resultMap.put("msg", "您尚未产生未缴水费项目");
            resultMap.put("result", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getElectricPayInfo(PageData pageData) {
        Map<String, Object> resultMap = new HashMap<>();
        // 从数据库检索出未缴费项目
        PageData electricPay = propertyPayMapper.selectElectricPayInfo(pageData);
        if (null != electricPay) {
            // 电费未缴费项目：有
            electricPay.put("payStatus", false);
            resultMap.put("data", electricPay);
            resultMap.put("msg", "数据访问成功！");
            resultMap.put("result", true);
        } else {
            // 电费未缴费项目：无
            electricPay = new PageData();
            electricPay.put("payStatus", true);
            resultMap.put("data", electricPay);
            resultMap.put("msg", "您尚未产生未缴电费项目。");
            resultMap.put("result", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getPropertyPayInfo(PageData pageData) {
        Map<String, Object> resultMap = new HashMap<>();
        // 从数据库检索出未缴费项目
        PageData propertyPay = propertyPayMapper.selectPropertyPayInfo(pageData);
        if (null != propertyPay) {
            // 物业费未缴费项目：有
            propertyPay.put("payStatus", false);
            resultMap.put("data", propertyPay);
            resultMap.put("msg", "数据访问成功！");
            resultMap.put("result", true);
        } else {
            // 物业费未缴费项目：无
            propertyPay = new PageData();
            propertyPay.put("payStatus", true);
            resultMap.put("data", propertyPay);
            resultMap.put("msg", "您尚未产生未缴物业费项目。");
            resultMap.put("result", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getRentPayInfo(PageData pageData) {
        Map<String, Object> resultMap = new HashMap<>();
        // 从数据库检索出未缴费项目
        PageData rentPay = propertyPayMapper.selectRentPayInfo(pageData);
        if (null != rentPay) {
            // 租赁费未缴费项目：有
            rentPay.put("payStatus", false);
            resultMap.put("data", rentPay);
            resultMap.put("msg", "数据访问成功！");
            resultMap.put("result", true);
        } else {
            // 租赁费未缴费项目：无
            rentPay = new PageData();
            rentPay.put("payStatus", true);
            resultMap.put("data", rentPay);
            resultMap.put("msg", "您尚未产生未缴租赁费项目。");
            resultMap.put("result", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getTemporaryParkInfo(PageData pageData) {
        Map<String, Object> resultMap = new HashMap<>();
        // 从数据库检索出未缴费项目
        PageData temporaryParkPay = propertyPayMapper.selectTemporaryParkInfo(pageData);
        if (null != temporaryParkPay) {
            // 临时停车未缴费项目：有
            temporaryParkPay.put("payStatus", false);
            // 停车时长 单位Second
            Long timeLengthS = Long.valueOf(temporaryParkPay.get("timeLength").toString());
            // 2元/1h算
            temporaryParkPay.put("paymentmoney", String.valueOf(2 * Math.ceil(timeLengthS / 3600.00)) + ".00");

            Long days = timeLengthS / (3600 * 24);
            Long hours = timeLengthS / 3600 % 24;
            String timeLength = String.valueOf(timeLengthS / 60 % 60) + "分";
            if (days != 0) {
                timeLength = String.valueOf(days) + "天" + String.valueOf(hours) + "时" + timeLength;
            } else {
                if (hours != 0) {
                    timeLength = String.valueOf(hours)  + "时" + timeLength;
                }
            }
            temporaryParkPay.put("timeLength", timeLength);

            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = (Date) temporaryParkPay.get("enterTime");
            String enterTime = tempDate.format(time);
            temporaryParkPay.put("enterTime", enterTime);

            resultMap.put("data", temporaryParkPay);
            resultMap.put("msg", "数据访问成功！");
            resultMap.put("result", true);
        } else {
            // 临时停车未缴费项目：无
            temporaryParkPay = new PageData();
            temporaryParkPay.put("payStatus", true);
            resultMap.put("data", temporaryParkPay);
            resultMap.put("msg", "您尚未产生未缴停车费项目。");
            resultMap.put("result", true);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getRentParkInfo(PageData pageData) {
        Map<String, Object> resultMap = new HashMap<>();
        // 从数据库检索出未缴费项目
        PageData rentPark = propertyPayMapper.selectRentParkInfo(pageData);
        if (null != rentPark) {
            // 月租停车费未缴费项目：有
            rentPark.put("payStatus", false);
            int applyType = Integer.parseInt(rentPark.getString("applyType"));
            if (applyType == 0) {
                rentPark.put("user_name", "月度卡");
            } else if (applyType == 1) {
                rentPark.put("user_name", "季度卡");
            } else if (applyType == 2) {
                rentPark.put("user_name", "年度卡");
            }
            resultMap.put("data", rentPark);
            resultMap.put("msg", "数据访问成功！");
            resultMap.put("result", true);
        } else {
            // 月租停车费未缴费项目：无
            rentPark = new PageData();
            rentPark.put("payStatus", true);
            resultMap.put("data", rentPark);
            resultMap.put("msg", "您尚未产生未缴停车费项目。");
            resultMap.put("result", true);
        }
        return resultMap;
    }


    @Override
    public Map<String, Object> getWaterFeePaymentList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectWaterFeePaymentList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            for (int i = 0; i < rentParkSize; i ++) {
                returnData.get(i).put("payType", 1);
                if (Integer.parseInt(returnData.get(i).getOrDefault("paymentway", 1).toString()) == 0) {
                    returnData.get(i).put("paymentway", "支付宝");
                } else {
                    returnData.get(i).put("paymentway", "微信");
                }
            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未产生水费缴费记录。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getElectricFeePaymentList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectElectricFeePaymentList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            for (int i = 0; i < rentParkSize; i ++) {
                returnData.get(i).put("payType", 2);
                if (Integer.parseInt(returnData.get(i).getOrDefault("paymentway", 1).toString()) == 0) {
                    returnData.get(i).put("paymentway", "支付宝");
                } else {
                    returnData.get(i).put("paymentway", "微信");
                }
            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未产生电费缴费记录。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getPropertyFeePaymentList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectPropertyFeePaymentList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            for (int i = 0; i < rentParkSize; i ++) {
                returnData.get(i).put("payType", 3);
                if (Integer.parseInt(returnData.get(i).getOrDefault("paymentway", 1).toString()) == 0) {
                    returnData.get(i).put("paymentway", "支付宝");
                } else {
                    returnData.get(i).put("paymentway", "微信");
                }
            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未产生物业费缴费记录。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getRentFeePaymentList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectRentFeePaymentList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            for (int i = 0; i < rentParkSize; i ++) {
                returnData.get(i).put("payType", 4);
                if (Integer.parseInt(returnData.get(i).getOrDefault("paymentway", 1).toString()) == 0) {
                    returnData.get(i).put("paymentway", "支付宝");
                } else {
                    returnData.get(i).put("paymentway", "微信");
                }
            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未产生租赁费缴费记录。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getParkingPaymentList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectParkingPaymentList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            for (int i = 0; i < rentParkSize; i ++) {
                if (Integer.parseInt(returnData.get(i).getOrDefault("paymentway", 1).toString()) == 0) {
                    returnData.get(i).put("paymentway", "支付宝");
                } else {
                    returnData.get(i).put("paymentway", "微信");
                }
                // 临时停车的场合，将关键词匹配到月租停车
                if (Integer.parseInt(returnData.get(i).get("parkStatus").toString()) == 9) {
                    // 已缴费，已出场
                    // 入场时间 => 起始时间
                    returnData.get(i).put("startDate", returnData.get(i).get("enterTime"));
                    // 出场时间 => 起始时间
                    returnData.get(i).put("endDate", returnData.get(i).get("exitTime"));
                    // 区分临时停车和月租停车
                    returnData.get(i).put("payType", 6);
                } else if (Integer.parseInt(returnData.get(i).get("parkStatus").toString()) == 8) {
                    // 已缴费，未出场
                    // 入场时间 => 起始时间
                    returnData.get(i).put("startDate", returnData.get(i).get("enterTime"));
                    // 未出场 => 起始时间
                    returnData.get(i).put("endDate", "未出场");
                    // 区分临时停车和月租停车
                    returnData.get(i).put("payType", 6);
                } else {
                    // 区分临时停车和月租停车
                    returnData.get(i).put("payType", 5);
                }
            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未产生停车缴费记录。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getPassedRentParkList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectPassedRentParkList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            String parkStatus = "parkStatus";
            String applyType = "applyType";
            for (int i = 0; i < rentParkSize; i ++) {
                // 解析审核状态
                returnData.get(i).put(parkStatus, "待缴费");
                // 解析申请类型
                if ("0".equals(returnData.get(i).getString(applyType))) {
                    returnData.get(i).put(applyType, "月度卡");
                } else if ("1".equals(returnData.get(i).getString(applyType))) {
                    returnData.get(i).put(applyType, "季度卡");
                } else if ("2".equals(returnData.get(i).getString(applyType))) {
                    returnData.get(i).put(applyType, "年度卡");
                }

            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未申请停车卡。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getRentParkList(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<PageData> returnData = propertyPayMapper.selectRentParkList(pageData);
        if (returnData != null) {
            // 数据解析
            int rentParkSize = returnData.size();
            String parkStatus = "parkStatus";
            String applyType = "applyType";
            for (int i = 0; i < rentParkSize; i ++) {
                // 解析审核状态
                if (Integer.parseInt(returnData.get(i).get(parkStatus).toString()) == 1) {
                    returnData.get(i).put(parkStatus, "审核中");
                } else if (Integer.parseInt(returnData.get(i).get(parkStatus).toString()) == 2) {
                    returnData.get(i).put(parkStatus, "未通过");
                } else if (Integer.parseInt(returnData.get(i).get(parkStatus).toString()) == 3) {
                    returnData.get(i).put(parkStatus, "已通过");
                } else if (Integer.parseInt(returnData.get(i).get(parkStatus).toString()) == 4) {
                    returnData.get(i).put(parkStatus, "已缴费");
                }

                // 解析申请类型
                if ("0".equals(returnData.get(i).getString(applyType))) {
                    returnData.get(i).put(applyType, "月度卡");
                } else if ("1".equals(returnData.get(i).getString(applyType))) {
                    returnData.get(i).put(applyType, "季度卡");
                } else if ("2".equals(returnData.get(i).getString(applyType))) {
                    returnData.get(i).put(applyType, "年度卡");
                }

            }
            returnMap.put("result", true);
            returnMap.put("data", returnData);
        } else {
            returnMap.put("result", true);
            returnMap.put("msg", "您尚未申请停车卡。");
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> deleteRentParkItem(PageData pageData) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        int returnData = propertyPayMapper.deleteRentParkItem(pageData);
        if (returnData != 0) {

            returnMap.put("result", true);
            returnMap.put("data", "删除数据成功");
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "删除数据失败");
        }
        return returnMap;
    }

}
