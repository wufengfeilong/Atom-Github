package sdwxwx.com.http;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;
import sdwxwx.com.bean.*;
import sdwxwx.com.home.bean.*;
import sdwxwx.com.home.contract.ContactBean;
import sdwxwx.com.me.bean.AboutBean;
import sdwxwx.com.me.bean.RecommendWealthBean;
import sdwxwx.com.me.bean.UpdateInfoBean;
import sdwxwx.com.me.bean.VideoWealthBean;
import sdwxwx.com.message.bean.MessageListBean;
import sdwxwx.com.play.bean.CommentBean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/8
 * time      12:43
 */
public interface APIService {

    //4.1获取广告
    @GET("ad/collection")
    Observable<HttpResult<List<BannerBean>>> getBanners(
            @Query("type") String type
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    // 4.2获取所有栏目
    @GET("category/collection")
    Observable<HttpResult<List<CategoryBean>>> getCategoryList(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.3获取城市列表
    @GET("city/collection")
    Observable<HttpResult<List<CityEntity>>> getCities(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.4查询是否存在可升级的客户端版本
    @GET("client/update")
    Observable<HttpResult<List<UpdateInfoBean>>> clientUpdate(
            @Query("version") String version
            , @Query("system_type") String system_type
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


    //4.5添加评论
    @FormUrlEncoded
    @POST("comment/add")
    Observable<HttpResult<List<String>>> addComment(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("video_id") String video_id
            , @Field("member_id") String member_id
            , @Field("comment_id") String comment_id
            , @Field("content") String content
            , @Field("at") String at);

    //4.6会员点赞评论
    @GET("comment/like")
    Observable<HttpResult<List<String>>> commentLike(
            @Query("member_id") String member_id
            , @Query("comment_id") String comment_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


    //4.7获取视频的评论列表
    @GET("comment/collection")
    Observable<HttpResult<List<CommentBean>>> getComments(
            @Query("member_id") String member_id
            , @Query("video_id") String video_id
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.8会员撤销已点赞的评论
    @GET("comment/unlike")
    Observable<HttpResult<List<String>>> commentUnlike(
            @Query("member_id") String member_id
            , @Query("comment_id") String comment_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.9添加反馈
    @FormUrlEncoded
    @POST("feedback/add")
    Observable<HttpResult<List<String>>> addFeedback(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("member_id") String member_id
            , @Field("content") String content);

    //4.10关注会员
    @GET("follow/follow")
    Observable<HttpResult<List<String>>> followUser(
            @Query("member_id") String member_id
            , @Query("followed_member_id") String followed_member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.11撤销关注会员
    @GET("follow/remove")
    Observable<HttpResult<List<String>>> removeUser(
            @Query("member_id") String member_id
            , @Query("followed_member_id") String followed_member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.12获取会员的关注列表
    @GET("follow/followed")
    Observable<HttpResult<List<RecommendUserBean>>> getFollowedUserlist(
            @Query("member_id") String member_id
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.13获取会员的粉丝列表
    @GET("follow/follower")
    Observable<HttpResult<List<RecommendUserBean>>> getFollowerUserlist(
            @Query("member_id") String member_id
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.14登录会员
    @GET("member/login")
    Observable<HttpResult<List<UserBean>>> memberLogin(
            @Query("mobile") String mobile
            , @Query("code") String code
            , @Query("wechat_id") String wechat_id
            , @Query("qq_id") String qq_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.15获取会员的详情信息
    @GET("member/profile")
    Observable<HttpResult<List<UserBean>>> getMemberInfo(
            @Query("member_id") String member_id
            , @Query("target_member_id") String target_member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


    //4.16注册会员--电话注册
    //使用手机号码进行注册时，提供手机号码和密码并保持wechat_id和qq_id为0。
    //使用手机号码注册会员时，如果用户上传头像图片，则avatar字段为上传的图片。
    @Multipart
    @POST("member/register")
    Observable<HttpResult<List<UserBean>>> phoneHasFileRegisterUser(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Part("mobile") RequestBody mobile
            , @Part("code") RequestBody code
            , @Part("wechat_id") RequestBody wechat_id
            , @Part("qq_id") RequestBody qq_id
            , @Part("nickname") RequestBody nickname
            , @Part("recommender_id") RequestBody recommender_id
            , @Part("city_id") RequestBody city_id
            , @Part MultipartBody.Part avatar
    );

    //4.16注册会员--微信或QQ注册
    //使用微信注册时，提供wechat_id并保持手机号码和qq_id为0。使用QQ注册时，提供qq_id并保持手机号码和wechat_id为0。
    //使用微信或QQ登录时，avatar字段为头像图片的URL地址。
    //4.16注册会员--电话注册
    @FormUrlEncoded
    @POST("member/register")
    Observable<HttpResult<List<UserBean>>> phoneRegisterUser(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("mobile") String mobile
            , @Field("code") String code
            , @Field("wechat_id") String wechat_id
            , @Field("qq_id") String qq_id
            , @Field("nickname") String nickname
            , @Field("avatar") String avatar
            , @Field("city_id") String city_id
            , @Field("recommender_id") String recommender_id);

    //4.17推荐会员 --废止
    //4.18获取会员的一级/二级推荐列表 --废止

    //4.19获取会员的通知列表
    @GET("notice/collection")
    Observable<HttpResult<List<MessageListBean.Result>>> getNoticeList(
            @Query("member_id") String member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.20获取会员的推荐（下级）列表
    @GET("member/subordinate")
    Observable<HttpResult<List<RecommendUserBean>>> memberSubordinate(
            @Query("member_id") String member_id
            , @Query("level") String level
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.21获取敏感词列表
    @GET("sensitive/collection")
    Observable<HttpResult<List<String>>> getSensitiveList(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.22申请发送短信验证码
    @GET("sms/send")
    Observable<HttpResult<String>> personSendPhoneCode(
            @Query("mobile") String mobile
            , @Query("debug") String debug
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.23添加视频
    @FormUrlEncoded
    @POST("video/add")
    Observable<HttpResult<List<String>>> addVideo(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("member_id") String member_id
            , @Field("ksyun_id") String ksyun_id
            , @Field("category_id") String category_id
            , @Field("topic_id") String topic_id
            , @Field("topic_title") String topic_title
            , @Field("city_id") String city_id
            , @Field("music_id") String music_id
            , @Field("title") String title
            , @Field("description") String description
            , @Field("longitude") String longitude
            , @Field("latitude") String latitude
            , @Field("duration") String duration
            , @Field("cover_time") String cover_time
            , @Field("at") String at);

    //4.24会员举报视频
    @FormUrlEncoded
    @POST("video/complain")
    Observable<HttpResult<List<String>>> videoComplain(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("member_id") String member_id
            , @Field("video_id") String video_id
            , @Field("reason") String reason);

    //4.25会员点赞视频
    @GET("video/like")
    Observable<HttpResult<List<String>>> videoLike(
            @Query("member_id") String member_id
            , @Query("video_id") String video_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


    //4.26获取视频列表
    @GET("video/collection")
    Observable<HttpResult<List<PlayVideoBean>>> getVideoCollection(
            @Query("member_id") String member_id
            , @Query("is_liked") String is_liked
            , @Query("category_id") String category_id
            , @Query("topic_id") String topic_id
            , @Query("owner_id") String owner_id
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.27获取附近视频列表
    @GET("video/nearby")
    Observable<HttpResult<List<PlayVideoBean>>> getVideoNearBy(
            @Query("member_id") String member_id
            , @Query("city_id") String city_id
            , @Query("longitude") String longitude
            , @Query("latitude") String latitude
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.28删除视频
    @GET("video/remove")
    Observable<HttpResult<List<String>>> videoRemove(
            @Query("member_id") String member_id
            , @Query("video_id") String video_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.29会员撤销已点赞的视频
    @GET("video/unlike")
    Observable<HttpResult<List<String>>> videoUnlike(
            @Query("member_id") String member_id
            , @Query("video_id") String video_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.30获取会员的推荐财富列表
    @GET("wealth/recommend")
    Observable<HttpResult<List<RecommendWealthBean>>> wealthRecommend(
            @Query("member_id") String member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.31获取会员的视频财富列表
    @GET("wealth/video")
    Observable<HttpResult<List<VideoWealthBean>>> wealthVideo(
            @Query("member_id") String member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.32搜索会员
    @GET("member/search")
    Observable<HttpResult<List<SearchUserBean.HaveUserBean>>> memberSearch(
            @Query("member_id") String member_id
            , @Query(value="keyword", encoded = true) String keyword
            , @Query("is_followed") String is_followed
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    // 4.33搜索话题
    @GET("topic/search")
    Observable<HttpResult<List<TopicInfoBean>>> getAddTopicList(
            @Query(value = "keyword", encoded = true) String keyword
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.34编辑会员的详细信息(有上传头像)
    @Multipart
    @POST("member/edit")
    Observable<HttpResult<List<String>>> memberFileEdit(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Part("member_id") RequestBody member_id
            , @Part("nickname") RequestBody nickname
            , @Part("description") RequestBody description
            , @Part("gender") RequestBody gender
            , @Part("birthday") RequestBody birthday
            , @Part MultipartBody.Part avatar);

    //4.34编辑会员的详细信息（没有上传新的头像）
    @FormUrlEncoded
    @POST("member/edit")
    Observable<HttpResult<List<String>>> memberEdit(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("member_id") String member_id
            , @Field("nickname") String nickname
            , @Field("description") String description
            , @Field("gender") String gender
            , @Field("birthday") String birthday
            , @Field("avatar") String avatar);

    // 4.35获取评论的回复列表
    @GET("reply/collection")
    Observable<HttpResult<List<CommentBean>>> replyCollection(
            @Query("member_id") String member_id
            , @Query("comment_id") String comment_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.36分享视频
    @GET("video/share")
    Observable<HttpResult<List<String>>> videoShare(
            @Query("member_id") String member_id
            , @Query("video_id") String video_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.37获取关于我们
    @GET("system/about")
    Observable<HttpResult<List<AboutBean>>> systemAbout(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
    );

    //4.38获取服务协议
    @GET("system/agreement")
    Observable<HttpResult<List<String>>> systemAgreement(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
    );

    //4.39会员的通讯录好友验证
    @FormUrlEncoded
    @POST("member/verify")
    Observable<HttpResult<List<ContactBean>>> memberVerify(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Field("mobile") String mobile
            , @Field("member_id") String member_id);

    //4.40获取系统的推荐用户
    @GET("member/recommend")
    Observable<HttpResult<List<RecommendUserBean>>> recommendMembers(
            @Query("member_id") String member_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.41获取系统的推荐话题
    @GET("topic/recommend")
    Observable<HttpResult<List<TopicInfoBean>>> getRecommendTopic(
            @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.42会员认证
    @Multipart
    @POST("member/certificate")
    Observable<HttpResult<List<String>>> memberCertificate(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Part("member_id") RequestBody member_id
            , @Part("real_name") RequestBody real_name
            , @Part("id_card") RequestBody id_card
            , @Part MultipartBody.Part front_image
            , @Part MultipartBody.Part back_image);

    // 4.43 注销会员
    @GET("member/logout")
    Observable<HttpResult<List<String>>> logout(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Query("member_id") String member_id);

    // 4.44 获取音乐类型
    @GET("music/type")
    Observable<HttpResult<List<MusicTypeBean>>> getMusicType(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    // 4.45 搜索音乐
    @GET("music/search")
    Observable<HttpResult<List<MusicBean>>> searchMusic(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Query(value="keyword", encoded = true) String keyword
            , @Query("member_id") String member_id
            , @Query("page") String page
            , @Query("size") String size);

    // 4.46 获取音乐列表
    @GET("music/collection")
    Observable<HttpResult<List<MusicBean>>> getMusicList(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Query("member_id") String member_id
            , @Query("is_favorited") String is_favorited
            , @Query("type_id") String type_id
            , @Query("page") String page
            , @Query("size") String size);

    // 4.47 获取系统推荐的音乐列表
    @GET("music/recommend")
    Observable<HttpResult<List<MusicBean>>> getRecommendMusicList(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature
            , @Query("member_id") String member_id
            , @Query("page") String page
            , @Query("size") String size);

    // 4.48 会员收藏音乐
    @GET("music/favorite")
    Observable<HttpResult<List<String>>> favoriteMusic(
            @Query("member_id") String member_id
            , @Query("music_id") String music_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    // 4.49会员撤销已收藏的音乐
    @GET("music/unfavorite")
    Observable<HttpResult<List<String>>> unfavoriteMusic(
            @Query("member_id") String member_id
            , @Query("music_id") String music_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.50获取首页视频列表
    @GET("video/recommend")
    Observable<HttpResult<List<PlayVideoBean>>> getVideoHomePage(
            @Query("member_id") String member_id
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //5 推荐人机制
    @GET("recommend/register")
    Observable<HttpResult<String>> getQRCodeUrl(
            @Query("recommender") String recommender//推荐人的会员编号
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);
}