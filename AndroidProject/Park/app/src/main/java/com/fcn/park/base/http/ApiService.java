package com.fcn.park.base.http;

import com.fcn.park.info.bean.*;
import com.fcn.park.login.bean.CodeBean;
import com.fcn.park.login.bean.RegisterClauseBean;
import com.fcn.park.login.bean.User;
import com.fcn.park.manager.bean.*;
import com.fcn.park.manager.bean.car.CarWaitCheckBean;
import com.fcn.park.manager.bean.car.CarWaitCheckDetailInfoBean;
import com.fcn.park.manager.bean.car.ParkFeeBean;
import com.fcn.park.manager.bean.car.ParkFeeDetailInfoBean;
import com.fcn.park.me.bean.*;
import com.fcn.park.property.bean.*;
import com.fcn.park.rent.bean.*;
import okhttp3.MultipartBody;
import retrofit2.http.*;
import rx.Observable;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 与Java后台交互用接口定义类.
 */
public interface ApiService {

    String HTTP_HOST = "http://172.29.140.35:8080/";
    String BASE_URL = HTTP_HOST + "PARK/";
    String IMAGE_BASE = BASE_URL + "images/";

    /**
     * 个人注册的接口
     *
     * @param account 账户
     * @param pwd     密码
     * @return
     */
    @POST("userReg/userReg")
    @FormUrlEncoded
    Observable<HttpResult<String>> userReg(@Field("account") String account, @Field("pwd") String pwd,
                                           @Field("phone") String phone, @Field("random") String verify);

    /**
     * 登录
     *
     * @param account 帐号
     * @param pwd     密码
     * @return
     */
    @POST("userLogin/login")
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@Field("account") String account, @Field("pwd") String pwd);

    /**
     * 根据手机号重置密码-发送验证码
     *
     * @param phoneNum 手机号
     * @return
     */
    @POST("userLogin/sendCodeByTel")
    @FormUrlEncoded
    Observable<HttpResult<CodeBean>> sendCodeByPhone(@Field("phone") String phoneNum);

    /**
     * 根据手机号重置密码-修改密码
     *
     * @param phoneNum 手机号码
     * @param password 新密码
     * @return
     */
    @POST("userLogin/editPwdByTel")
    @FormUrlEncoded
    Observable<HttpResult<String>> editPwdByPhone(@Field("phone") String phoneNum, @Field("pwd") String password);

    /**
     * 个人注册-获取手机验证码
     *
     * @param phoneNum 手机号码
     * @return
     */
    @GET("userReg/sendMsgByTel")
    Observable<HttpResult<CodeBean>> personSendPhoneCode(@Query("phone") String phoneNum);

    /**
     * 注册条款
     *
     * @return
     */
    @GET("userReg/goRegistration")
    Observable<HttpResult<RegisterClauseBean>> registerClause();

    /**
     * 获取微信的支付订单信息
     *
     * @return
     */
    @POST("courseinterfaceapp/wxpayPlaceOrder.app")
    @FormUrlEncoded
    Observable<HttpResult<WxPayBean>> getWeixinOrderInfo(@Field("user_id") String user_id
            , @Field("course_name") String course_name
            , @Field("body") String body
            , @Field("course_id") String course_id
            , @Field("total_fee") String total_fee
            , @Field("ip") String ip
            , @Field("commodity_id") String commodity_id
            , @Field("subject_id") String subject_id
            , @Field("teaching_style") String teaching_style
            , @Field("subject") String subject
            , @Field("order_number") String order_number);


    /**
     * 获取支付宝的支付订单信息
     *
     * @return
     */
    @POST("courseinterfaceapp/alipayPerOrder.app")
    @FormUrlEncoded
    Observable<HttpResult<AlipayBean>> getAlipayOrderInfo(@Field("user_id") String user_id
            , @Field("course_name") String course_name
            , @Field("commodity_id") String commodity_id
            , @Field("body") String body
            , @Field("course_id") String course_id
            , @Field("subject_id") String subject_id
            , @Field("teaching_style") String teaching_style
            , @Field("subject") String subject
            , @Field("total_amount") String total_amount
            , @Field("order_number") String order_number);

    /**
     * 修改密码
     */
    @POST("managerInfoapp/updatePassword.app")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatePassword(@Field("userId") String userId, @Field("oldPwd") String oldPwd, @Field("newPwd") String newPwd);


    /**
     * 新闻列表
     * liu 测试使用
     *
     * @param category
     * @return
     */
    @GET("newsapp/newslist")
    Observable<HttpResult<NewsListBean>> newsList(@Query("category") String category, @Query("pageNum") int page);

    /**
     * 新闻类型
     *
     * liu 测试使用
     * @return
     */
    @GET("newsapp/newstype")
    Observable<HttpResult<List<NewsTypeBean>>> newsType();

    /**
     * 新闻详情
     * liu 测试使用
     * @param id
     * @return
     */
    @GET("newsapp/newsdetail")
    Observable<HttpResult<InfoNewsBean>> newsDetail(@Query("id") String id);

    /**
     * 获取企业列表
     *
     * @param
     * @param pageNum
     * @return
     */
    @GET("businessapp/business")
    Observable<HttpResult<EnterpriseListBean>> getEnterpriseList(@Query("pageNum") int pageNum);
    /**
     * 获取企业详情
     *
     * @return
     */
    @GET("businessapp/businessinfo")
    Observable<HttpResult<EnterpriseInfoBean>> getEnterpriseInfo(@Query("companyId") String companyId);

    /**
     * 获取企业需求列表
     *
     * @param
     * @param pageNum
     * @return
     */
    @GET("demandapp/demandlist")
    Observable<HttpResult<DemandListBean>> getDemandList(@Query("pageNum") int pageNum);

    /**
     * 获取企业需求详情
     *
     * @return
     */
    @GET("demandapp/demandinfo")
    Observable<HttpResult<NeedInfoBean>> getNeedInfo(@Query("demandId") String demandId);

    /**
     * 获取企业需求管理列表
     *
     * @param
     * @param pageNum
     * @return
     */
    @GET("managerdemandapp/demandlist")
    Observable<HttpResult<ManagerDemandListBean>> getManagerDemandList(@Query("pageNum") int pageNum);

    /**
     * 删除需求
     * @param id
     * @return
     */
    @GET("managerdemandapp/deletedemand")
    Observable<HttpResult<String>> deleteDemandDetail(@Query("demandId") String id);

    /**
     * 发布需求
     *
     * @param title
     * @param content
     * @param source
     * @param contact
     * @param tel
     * @param address
     * @param category
     * @return
     */
    @POST("managerdemandapp/insertDemand")
    @FormUrlEncoded
    Observable<HttpResult<String>> insertDemandInfo(@Field("title") String title,
                                                    @Field("content") String content,
                                                    @Field("source") String source,
                                                    @Field("contact") String contact,
                                                    @Field("tel") String tel,
                                                    @Field("address") String address,
                                                    @Field("category") int category,
                                                    @Field("insert_user") String insertUser,
                                                    @Field("update_user") String updateUser
    );

    /**
     * 发布业需求详情
     *
     * @return
     */
    @GET("managerdemandapp/managerDemandDetail")
    Observable<HttpResult<DemandDetailInfoBean>> getDemandDetail(@Query("demandId") String demandId);

    /**
     * 更新企业需求详情
     */
    @POST("managerdemandapp/getmanagerDemandEdit")
    @FormUrlEncoded
    Observable<HttpResult<String>> managerDemandEdit(@Field("demandId") String demandId,
                                                     @Field("title") String title,
                                                     @Field("content") String content,
                                                     @Field("source") String source,
                                                     @Field("contact") String contact,
                                                     @Field("tel") String tel,
                                                     @Field("address") String address,
                                                     @Field("update_user") String updateUser
                                                     );

    /**
     * 用户查看消息列表
     *
     * @param user_type
     * @param pageNum
     * @return
     */
    @GET("messageapp/list")
    Observable<HttpResult<ManagerPublishActivitiesListBean>> getMessageList(@Query("user_type") String user_type, @Query("pageNum") int pageNum);

    /**
     * 更新物业费
     */
    @POST("propertyFee/edit")
    @FormUrlEncoded
    Observable<HttpResult<String>> editPropertyFee(@Field("id") String id,
                                                   @Field("companyspace") String companyspace,
                                                   @Field("unitprice") String unitprice,
                                                   @Field("startdate") String startdate,
                                                   @Field("enddate") String enddate,
                                                   @Field("discount") String discount,
                                                   @Field("fee") String fee,
                                                   @Field("comment") String comment);

    /**
     * 更新租赁费
     */
    @POST("rentFee/edit")
    @FormUrlEncoded
    Observable<HttpResult<String>> editRentFee(@Field("id") String id,
                                               @Field("companyspace") String companyspace,
                                               @Field("unitprice") String unitprice,
                                               @Field("startdate") String startdate,
                                               @Field("enddate") String enddate,
                                               @Field("discount") String discount,
                                               @Field("fee") String fee,
                                               @Field("comment") String comment);

    /**
     * 更新水费
     */
    @POST("waterFee/edit")
    @FormUrlEncoded
    Observable<HttpResult<String>> editWaterFee(@Field("id") String id,
                                                @Field("startnum") String startnum,
                                                @Field("endnum") String endnum,
                                                @Field("costnum") String costnum,
                                                @Field("recorddate") String recorddate,
                                                @Field("unitprice") String unitprice,
                                                @Field("fee") String fee);

    /**
     * 更新电费
     */
    @POST("electricFee/edit")
    @FormUrlEncoded
    Observable<HttpResult<String>> editElectricFee(@Field("id") String id,
                                                   @Field("startnum") String startnum,
                                                   @Field("endnum") String endnum,
                                                   @Field("costnum") String costnum,
                                                   @Field("recorddate") String recorddate,
                                                   @Field("unitprice") String unitprice,
                                                   @Field("fee") String fee);

    /**
     * 个人中心-企业认证
     *
     * @param parts
     * @param reqJson
     * @return
     */
    @POST("company/saveCompanyAuthInfo")
    @Multipart
    Observable<HttpResult<CodeBean>> saveCompanyAuthInfo(@Part() List<MultipartBody.Part> parts,
                                                         @QueryMap() Map<String,String> reqJson);

    /**
     * 企业信息编辑-企业轮播图片上传
     *
     * @param parts
     * @param reqJson
     * @return
     */
    @POST("company/saveBannersInfo")
    @Multipart
    Observable<HttpResult<CodeBean>> saveBannersInfo(@Part() List<MultipartBody.Part> parts,
                                                         @QueryMap() Map<String,String> reqJson);

    /**
     * 更新企业介绍
     */
    @POST("company/updateCompanyIntroduce")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateCompanyIntroduce(@Field("userId") String id,
                                                   @Field("introduce") String introduce);
    /**
     * 修改个人头像
     *
     * @param photo
     * @param userId
     * @return
     */
    @POST("person/updateUserPicture")
    @Multipart
    Observable<HttpResult<String>> uploadAvatar(@Part MultipartBody.Part photo, @Query("userId") String userId);

    /**
     * 修改个人信息
     *
     * @param userId
     * @param name
     * @param contactInfo
     * @return
     */
    @POST("person/updatePersonalInfo")
    @FormUrlEncoded
    Observable<HttpResult<String>> updatePersonInfo(
            @Field("userId") String userId//用户id
            , @Field("name") String name//用户名
            , @Field("contactInfo") String contactInfo//联系电话
    );

    /**
     * 查询认证结果
     *
     * @param userId
     * @return
     */
    @POST("company/selectAuthResult")
    @FormUrlEncoded
    Observable<HttpResult<String>> selectAuthResult(@Field("userId") String userId);

    /**
     * 查询有无未读消息
     *
     * @param userId
     * @return
     */
    @POST("myMessage/selectHasMsg")
    @FormUrlEncoded
    Observable<HttpResult<String>> selectHasMsg(@Field("userId") String userId);

    /**
     * 我的消息列表
     * @return
     */
    @GET("myMessage/messageRecordList")
    Observable<HttpResult<MeMessageRecordBean>> messageRecordList(@Query("userId") String userId,@Query("pageNum") int page);

    /**
     * 我的消息列表
     * @return
     */
    @POST("myMessage/messageRecordDetail")
    @FormUrlEncoded
    Observable<HttpResult<MeMsgDetailBean>> getMeMyMsgDetail(@Field("id") String id);
    /**
     * 获取版本信息
     * @return
     */
    @GET("images/version.json")
    Observable<HttpResult<VersionInfoBean>> getVersionInfo();
    /**
     * 获取APK
     * @return
     */
    @Streaming
    @GET("images/{name}")
    Observable<HttpResult<File>> getVersionAPK(@Path("apkname") String apkname);

    /**
     * 获取个人信息
     *
     * @param userId
     * @return
     */
    @GET("person/getPersonalInfo")
    Observable<HttpResult<MePersonInfoBean>> getPersonInfo(@Query("userId") String userId);
    /**
     * 获取公司详情信息
     *
     * @param userId
     * @return
     */
    @GET("company/getCompanyIntroduceInfo")
    Observable<HttpResult<String>> getCompanyIntroduceInfo(@Query("userId") String userId);

    /**
     * 获取公司图片介绍信息
     *
     * @param userId
     * @return
     */
    @GET("company/getCompanyPictureInfo")
    Observable<HttpResult<List<PictureBean>>> getCompanyPictureInfo(@Query("userId") String userId);


    /**
     * 获取公司信息
     *
     * @param userId
     * @return
     */
    @GET("company/getCompanyInfo")
    Observable<HttpResult<MeCompanyInfoBean>> getCompanyInfo(@Query("userId") String userId);

    /**
     * 管理中心新闻列表
     * @param newsType
     * @param page
     * @return
     */
    @GET("newsInfo/newsList")
    Observable<HttpResult<ManagerPostNewsListBean>> managerNewsList(@Query("category") String newsType, @Query("pageNum") int page);

    /**
     * 管理中心新闻详情
     *
     * @param id
     * @return
     */
    @GET("newsInfo/newsDetail")
    Observable<HttpResult<DetailInfoBean>> managerNewsDetail(@Query("news_id") String id);

    /**
     * 管理中心新闻详情
     *
     * @param id
     * @return
     */
    @GET("newsInfo/deleteNews")
    Observable<HttpResult<String>> deleteNewsDetail(@Query("news_id") String id);

    /**
     * 管理中心发布新闻条目
     * @param newsThumbnail
     * @param reqNewsBean
     * @return
     */
    @POST("newsInfo/insertPostNews")
    @Multipart
    Observable<HttpResult<String>> insertNewsInfo(@QueryMap() Map<String,String> reqNewsBean, @Part() MultipartBody.Part newsThumbnail);

    /**
     * 管理中心编辑新闻条目
     * @param newsThumbnail
     * @param reqNewsBean
     * @return
     */
    @POST("newsInfo/updatePostNews")
    @Multipart
    Observable<HttpResult<String>> updateNewsDetailInfo(@QueryMap() Map<String,String> reqNewsBean, @Part() MultipartBody.Part newsThumbnail);

    /**
     * 修改密码
     */
    @POST("managerInfo/resetManagerPassword")
    @FormUrlEncoded
    Observable<HttpResult<String>> resetManagerPassword(@Field("userId") String userId, @Field("oldPwd") String oldPwd, @Field("newPwd") String newPwd);

    /**
     * 提出园区简介
     */
    @GET("parkInfo/getParkIntroductionInfo")
    Observable<HttpResult<ManagerParkIntroductionBean>> getParkIntroductionInfo(@Query("parkId") String parkId);

    /**
     * 上传园区简介的图片
     */
    @POST("parkInfo/uploadParkInfoImg")
    @Multipart
    Observable<HttpResult<String>> uploadParkInfoImg(@Part() List<MultipartBody.Part> parts, @QueryMap() Map<String,String> reqJson);

    /**
     * 获取广告位待审批列表
     * @return
     */
    @POST("advertisingInfo/getAdvertisingApprovalList")
    @FormUrlEncoded
    Observable<HttpResult<ManagerAdvertisingApprovalListBean>> getAdvertisingApprovalList(@Field("pageNum") int pageNum);

    /**
     * 获取广告位列表(已审批完了的列表)
     * @return
     */
    @POST("advertisingInfo/getAdvertisingList")
    @FormUrlEncoded
    Observable<HttpResult<ManagerAdvertisingApprovalListBean>> getAdvertisingList(@Field("pageNum") int pageNum);

    /**
     * 获取广告位待审批详情内容
     * @param advertisingId
     * @return
     */
    @POST("advertisingInfo/getAdvertisingInfo")
    @FormUrlEncoded
    Observable<HttpResult<ManagerAdvertisingApprovalBean>> getAdvertisingApprovalDetailInfo(@Field("advertisingId") String advertisingId);

    /**
     * 获取广告位详情内容(已审批完了的详情内容)
     * @param advertisingId
     * @return
     */
    @POST("advertisingInfo/getAdvertisingInfo")
    @FormUrlEncoded
    Observable<HttpResult<ManagerAdvertisingApprovalBean>> getAdvertisingDetailInfo(@Field("advertisingId") String advertisingId);

    /**
     * 点击“通过”按钮后，更新广告的信息
     * @param updateData
     * @return
     */
    @POST("advertisingInfo/updateAdvertisingInfoByPassOn")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateAdvertisingInfoByPassOn(@QueryMap() Map<String,String> updateData, @Field("approvalStatus") int approvalStatus);

    /**
     * 点击“通过”按钮后，更新广告的信息
     * @param updateData
     * @return
     */
    @POST("advertisingInfo/updateAdvertisingInfoByRefuse")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateAdvertisingInfoByRefuse(@QueryMap() Map<String,String> updateData, @Field("approvalStatus") int approvalStatus);


    /**
     * 获取广告套餐类型的费用的数据
     * @return
     */
    @GET("advertisingInfo/getAdvertisingSetFeeData")
    Observable<HttpResult<ManagerAdvertisingFeeEditBean>> getAdvertisingSetFeeData();

    /**
     * 更新广告套餐类型的费用的数据
     * @param set1Fee：套餐类型一（一个月）的费用值
     * @param set2Fee：套餐类型二（三个月）的费用值
     * @param set3Fee：套餐类型三（一年）的费用值
     * @return
     */
    @POST("advertisingInfo/updateAdvertisingSetFeeData")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateAdvertisingSetFeeData(@Field("advertiseSet1Fee") String set1Fee,
                                                            @Field("advertiseSet2Fee") String set2Fee,
                                                            @Field("advertiseSet3Fee") String set3Fee);

    /**
     * 月租车辆待审批列表
     * @return
     */

    @GET("carWaitCheck/getCarWaitCheckList")
    Observable<HttpResult<CarWaitCheckBean>> getCarwaitchecklist();
    /**
     * 审批通过
     * @param parkPay_id
     * @return
     */
    @GET("carWaitCheck/updateParkStatus")
    Observable<HttpResult<String>> updateCheckStatus(@Query("parkPay_id") String parkPay_id  );
    /**
     * 驳回
     * @param parkPay_id
     * @return
     */
    @GET("carWaitCheck/onTurnClick")
    Observable<HttpResult<String>> onTurnClick(@Query("parkPay_id") String parkPay_id, @Query("checkinfo") String checkinfo );
    /**
     * 停车费用列表
     * @return
     */
    @GET("parkFee/parkFeeList")
    Observable<HttpResult<ParkFeeBean>> getParkFeelist();

    /**
     * 月租车辆详情
     * @param parkPay_id
     * @return
     */
    @GET("carWaitCheck/carWaitCheckDetail")
    Observable<HttpResult<CarWaitCheckDetailInfoBean>> carWaitCheckDetail(@Query("parkPay_id") String parkPay_id);

    /**
     * 停车费用详情
     * @param parkPay_id
     * @return
     */
    @GET("parkFee/getParkFeeListDetail")
    Observable<HttpResult<ParkFeeDetailInfoBean>> parkFeeDetailInfo(@Query("parkPay_id") String parkPay_id);

    /**
     * 个人浏览报修列表
     * @return
     */
    @POST("RepairRecord/RepairRecordList")
    @FormUrlEncoded
    Observable<HttpResult<RepairRecordBean>> getRepairRecordlist(@Field("userId") String userId,@Query("pageNum") int pageNum);
    /**
     * 个人浏览报修列表详情
     * @return
     */
    @POST("RepairRecord/RepairRecordDetail")
    @FormUrlEncoded
    Observable<HttpResult<RepairRecordDetailBean>> getRepairDetailById(@Field("repairId") String repairId);

    @POST("carWaitCheck/deleteCarWaitCheck")
    Observable<HttpResult<String>> deleteCarwaitcheck(@Query("userId") String userId, @Query("id") String id);

    /**
     * 已发布房屋信息列表取得
     * @return
     */
    @GET("rentReleased/rentReleasedHouseList")
    Observable<HttpResult<RentReleasedHouseListBean>> rentReleasedHouseList(@Query("pageNum") int page);

    /**
     * 已发布房屋信息详情取得
     * @return
     */
    @GET("rentReleased/rentReleasedHouseDetail")
    Observable<HttpResult<RentReleasedHouseListBean>> rentReleasedHouseDetail(@Query("houseId") String houseId);


    /**
     * 管理中心企业新闻详情
     *
     * @return
     */
    @GET("newsInfo/companynewsdetail")
    Observable<HttpResult<DetailInfoBean>> companyNewsDetail(@Query("id") String id);

    /**
     * 获取绿色物管水费缴费信息
     *
     * @return
     */
    @POST("PropertyPay/waterPayInfo")
    @FormUrlEncoded
    Observable<HttpResult<PropertyMainBean>> getPropertyWaterPayInfo(
            @Field("companyId") String companyId
            , @Field("payStatus") int payStatus);

    /**
     * 获取绿色物管电费缴费信息
     *
     * @return
     */
    @POST("PropertyPay/electricPayInfo")
    @FormUrlEncoded
    Observable<HttpResult<PropertyMainBean>> getPropertyElectricPayInfo(
            @Field("companyId") String companyId
            , @Field("payStatus") int payStatus);

    /**
     * 获取绿色物管物业费缴费信息
     *
     * @return
     */
    @POST("PropertyPay/propertyPayInfo")
    @FormUrlEncoded
    Observable<HttpResult<PropertyMainBean>> getPropertyFeePayInfo(
            @Field("companyId") String companyId
            , @Field("payStatus") int payStatus);

    /**
     * 获取绿色物管租赁费缴费信息
     *
     * @return
     */
    @POST("PropertyPay/rentPayInfo")
    @FormUrlEncoded
    Observable<HttpResult<PropertyMainBean>> getRentFeePayInfo(
            @Field("companyId") String companyId
            , @Field("payStatus") int payStatus);

    /**
     * 获取绿色物管临时停车费缴费信息
     *
     * @return
     */
    @POST("PropertyPay/temporaryParkInfo")
    @FormUrlEncoded
    Observable<HttpResult<PropertyMainBean>> getTemporaryParkInfo(
            @Field("carNumber") String carNumber
            , @Field("payStatus") int payStatus);

    /**
     * 获取绿色物管月租停车费缴费信息
     *
     * @return
     */
    @POST("PropertyPay/rentParkInfo")
    @FormUrlEncoded
    Observable<HttpResult<PropertyMainBean>> getRentParkInfo(
            @Field("userId") String userId
            , @Field("parkPayId") int parkPayId
            , @Field("payStatus") int payStatus);

    /**
     * 获取绿色物管水费缴费列表
     *
     * @return
     */
    @POST("PropertyPay/getWaterFeePaymentList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getWaterFeePaymentList(
            @Field("userId") String userId);

    /**
     * 获取绿色物管电费缴费列表
     *
     * @return
     */
    @POST("PropertyPay/getElectricFeePaymentList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getElectricFeePaymentList(
            @Field("userId") String userId);

    /**
     * 获取绿色物管物业费缴费列表
     *
     * @return
     */
    @POST("PropertyPay/getPropertyFeePaymentList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getPropertyFeePaymentList(
            @Field("userId") String userId);

    /**
     * 获取绿色物管租赁费缴费列表
     *
     * @return
     */
    @POST("PropertyPay/getRentFeePaymentList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getRentFeePaymentList(
            @Field("userId") String userId);

    /**
     * 获取绿色物管停车缴费列表
     *
     * @return
     */
    @POST("PropertyPay/getParkingPaymentList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getParkingPaymentList(
            @Field("userId") String userId);

    /**
     * 获取绿色物管月租车辆申请列表
     *
     * @return
     */
    @POST("PropertyPay/getRentParkList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getRentParkList(
            @Field("userId") String userId);

    /**
     * 获取绿色物管需要缴费的月租车辆申请列表
     *
     * @return
     */
    @POST("PropertyPay/getPassedRentParkList")
    @FormUrlEncoded
    Observable<HttpResult<List<PropertyMainBean>>> getPassedRentParkList(
            @Field("userId") String userId);


    /**
     * 删除绿色物管月租停车申请信息
     *
     * @return
     */
    @POST("PropertyPay/deleteRentParkItem")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteRentParkItem(
            @Field("parkPayId") int parkPayId);

    /**
     * 发送投诉建议信息
     *
     * @return
     */
    @POST("SuggestionInfo/sendSuggestion")
    @FormUrlEncoded
    Observable<HttpResult<String>> suggestion(@Field("userId") String userId,@Field("describeContent") String describeContent);

    /**
     * 发送报修信息
     * @return
     */
    @POST("RepairInfo/sendRepair")
    @Multipart
    Observable<HttpResult<String>>repair(@Part() List<MultipartBody.Part> parts,@QueryMap() Map<String,String> reqJson );
    /**
     * 查询车辆信息
     * @return
     */
    @GET("CarInfo/CarList")
    Observable<HttpResult<MeCarInfoBean>>carInfoList();
    /**
     * 根据车辆id删除车辆信息
     * @return
     */
    @POST("CarInfo/deleteCar")
    @FormUrlEncoded
    Observable<HttpResult<String>> deleteCarItem(@Field("carId") String carId);
    /**
     * 追加车辆信息
     * @return
     */
    @POST("CarInfo/AddCar")
    @FormUrlEncoded
    Observable<HttpResult<String>> AddCar(@Field("CarOwner") String CarOwner,@Field("PlateNumber") String PlateNumber,@Field("Phone") String Phone);
    /**
     * 修改车辆信息
     * @return
     */
    @POST("CarInfo/CarEditor")
    @FormUrlEncoded
    Observable<HttpResult<String>> carEditor(@Field("carId") String carId,@Field("CarOwner") String CarOwner,@Field("PlateNumber") String PlateNumber,@Field("Phone") String Phone);


    /*
     *获取送水商家信息
     *
     * @return
     * @param page
     */
    @GET("BusinessServices/DeliverWaterList")
    Observable<HttpResult<PropertyDeliverWaterBean>> deliverWaterList(@Query("page") int page);

    /*
     *获取绿植租赁商家信息
     *
     * @return
     * @param page
     */
    @GET("BusinessServices/PlantLeaseList")
    Observable<HttpResult<PropertyPlantLeaseBean>> PlantLeaseList(@Query("page") int page);

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @POST("file/fileUpload")
    @Multipart
    Observable<HttpResult<String>> fileUpload(@Part MultipartBody.Part file);


    /**
     * 月租车辆申请
     * @return
     */
    @Multipart
    @POST("parkPay/parkPayApply")
    Observable<HttpResult<String>> parkPayApply(@Part() List<MultipartBody.Part> parts,
                                                @QueryMap() Map<String,String> reqJson);
    /**
     * 月租车辆申请初始化数据取得
     * @return
     */
    @GET("parkPay/getParkPayInitData")
    Observable<HttpResult<List<PropertyParkPayTypeBean>>> getParkPayInitData();

    /**
     * 月租车辆审核失败初始化数据取得
     * @return
     */
    @GET("parkPay/getParkPayRejectInitData")
    Observable<HttpResult<PropertyParkPayBean>> getParkPayRejectInitData(@Query("parkPayId") int parkPayId);

    /**
     * 房屋信息编辑初始化数据取得
     * @return
     */
    @GET("rent/getRentAddInitData")
    Observable<HttpResult<RentInitBean>> getRentAddInitData();

    /**
     * 房屋信息添加数据存储
     * @return
     */
    @Multipart
    @POST("rent/rentInfoAdd")
    Observable<HttpResult<String>> rentAdd(@Part() List<MultipartBody.Part> parts,
                                           @QueryMap() Map<String,String> reqJson);


    /**
     * 房屋信息添加没有图片
     * @return
     */
    @POST("rent/rentAddNoImage")
    Observable<HttpResult<String>> rentAddNoImage(@QueryMap() Map<String,String> reqJson);

    /**
     * 房屋信息列表取得
     * @return
     */
    @GET("rent/rentHouseList")
    Observable<HttpResult<RentHouseListBean>> rentHouseList(@Query("goRent") String goRent, @Query("pageNum") int page);

    /**
     * 房屋信息详情取得
     * @return
     */
    @GET("rent/rentHouseDetail")
    Observable<HttpResult<RentHouseDetailBean>> rentHouseDetail(@Query("houseId") String houseId);

    /**
     * 房屋信息编辑初始化数据取得
     * @return
     */
    @GET("rent/rentHouseEditInit")
    Observable<HttpResult<RentHouseEditBean>> getRentEditInitData(@Query("houseId") String houseId);


    /**
     * 管理员查看物业费列表
     *
     * @param pageNum
     * @return
     */
    @GET("propertyFee/propetyList")
    Observable<HttpResult<PropertyFeeListBean>> getPropertyFeeList( @Query("pageNum") int pageNum);

    /**
     * 管理员查看租赁费列表
     *
     * @param pageNum
     * @return
     */
    @GET("rentFee/rentList")
    Observable<HttpResult<RentFeeListBean>> getRentFeeList(@Query("pageNum") int pageNum);

    /**
     * 管理员查看水费列表
     *
     * @param pageNum
     * @return
     */
    @GET("waterFee/waterList")
    Observable<HttpResult<WaterFeeListBean>> getWaterFeeList(@Query("pageNum") int pageNum);

    /**
     * 管理员查看电费列表
     *
     * @param pageNum
     * @return
     */
    @GET("electricFee/electricList")
    Observable<HttpResult<ElectricFeeListBean>> getElectricFeeList(@Query("pageNum") int pageNum);

    /**
     * 物业费催缴费送信
     *
     * @param email
     * @return
     */
    @GET("propertyFee/sendMail")
    Observable<HttpResult<String>> sendPropertyFeeMail( @Query("email") String email,
                                                        @Query("startDate") String startDate,
                                                        @Query("endDate") String endDate,
                                                        @Query("fee") String fee);

    /**
     * 租赁费催缴费送信
     *
     * @param email
     * @return
     */
    @GET("rentFee/sendMail")
    Observable<HttpResult<String>> sendRentFeeMail( @Query("email") String email,
                                                    @Query("startDate") String startDate,
                                                    @Query("endDate") String endDate,
                                                    @Query("fee") String fee);

    /**
     * 水费催缴费送信
     *
     * @param email
     * @return
     */
    @GET("waterFee/sendMail")
    Observable<HttpResult<String>> sendWaterFeeMail( @Query("email") String email,
                                                     @Query("recordDate") String recordDate,
                                                     @Query("fee") String fee);

    /**
     * 电费催缴费送信
     *
     * @param email
     * @return
     */
    @GET("electricFee/sendMail")
    Observable<HttpResult<String>> sendElectricFeeMail( @Query("email") String email,
                                                        @Query("recordDate") String recordDate,
                                                        @Query("fee") String fee);

    /*
     *获取搬家商家信息
     *
     * @return
     * @param page
     */
    @GET("BusinessServices/MoveHouseList")
    Observable<HttpResult<PropertyMoveHouseBean>> moveHouseList(@Query("page") int page);

    /*
    *获取清洗商家信息
    *
    * @return
    * @param page
    */
    @GET("BusinessServices/CleanList")
    Observable<HttpResult<PropertyCleanBean>> CleanList(@Query("page") int page);


    /**
     * 管理中心报修一览列表
     * Created  by 丁胜胜
     */
    @GET("repairsInfo/repairsList")
    Observable<HttpResult<ManagerRepairsListBean>> getRepairsListMessage(@Query("pageNum") int pageNum);

    /**
     * 管理中心报修详情
     * Created  by 丁胜胜
     * @param repairId
     * @return
     */
    @GET("repairsInfo/repairsDetail")
    Observable<HttpResult<ManagerRepairsDetailInfoBean>> managerRepairsDetail(@Query("repairId") String repairId,@Query("repairPhone") String repairPhone);

    /**
     * 管理中心留言列表
     * Created  by 丁胜胜
     */
    @GET("messageReplyInfo/messageReplyList")
    Observable<HttpResult<ManagerMessageReplyListBean>> getMessageReplyList(@Query("pageNum") int pageNum);

    /**
     * 管理中心留言详情
     * Created  by 丁胜胜
     * @param suggestionId
     * @return
     */
    @GET("messageReplyInfo/messageReplyDetail")
    Observable<HttpResult<ManagerMessageReplyDetailInfoBean>> messageReplyDetail(@Query("suggestionId") String suggestionId);

    /**
     * 点击“回复”按钮后，更新留言的信息
     * Created by 丁胜胜
     * @param updateData
     * @return
     */
    @POST("messageReplyInfo/updateMessageReplyInfoByAnswer")
    @FormUrlEncoded
    Observable<HttpResult<String>> updateMessageReplyInfoByAnswer(@QueryMap() Map<String,String> updateData,@Field("replyStatus") int replyStatus);

    /**
     * 追加广告
     * @param part
     * @param content
     * @return
     */
    @POST("advertisingInfo/addAdvertisement")
    @Multipart
    Observable<HttpResult<String>> addAdvertisement(@Part() MultipartBody.Part part, @Query("content") String content, @Query("setType") int payType, @Query("userName") String userName);
}
