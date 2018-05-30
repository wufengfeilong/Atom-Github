package woxingwoxiu.com.http;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.*;
import woxingwoxiu.com.bean.UserBean;
import woxingwoxiu.com.home.bean.BannerBean;
import woxingwoxiu.com.home.bean.CityEntity;
import woxingwoxiu.com.home.bean.PlayVideoBean;
import woxingwoxiu.com.me.bean.UpdateInfoBean;
import woxingwoxiu.com.play.bean.CommentBean;

import java.util.List;
import java.util.Map;

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
            , @Query("size") int size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.3获取城市列表
    @GET("city/collection")
    Observable<HttpResult<List<CityEntity>>> getCitys(
            @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.4查询是否存在可升级的客户端版本
    @GET("client/update")
    Observable<HttpResult<UpdateInfoBean>> clientUpdate(
            @Query("version") String version
            , @Query("system_type") String system_type
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


    //4.5添加评论
//    @FormUrlEncoded
//    @POST("comment/add/{timestamp}/{signature}")
//    Observable<HttpResult<List<CommentBean>>> addComment(
//            @Field("video_id") String video_id
//            , @Field("member_id") String member_id
//            , @Field("comment_id") int comment_id
//            , @Field("content") String content
//            , @Path("timestamp") long timestamp
//            , @Path("signature") String signature);

    //4.5添加评论
    @POST
    Observable<HttpResult<List<CommentBean>>> addComment(
            @Url String url
            , @QueryMap Map<String, Object> maps);

    //4.6会员点赞评论
    @GET("comment/like")
    Observable<HttpResult<String>> commentLike(
            @Query("member_id") String member_id
            , @Query("comment_id") String comment_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


    //4.7获取视频的评论列表
    @GET("comment/collection")
    Observable<HttpResult<List<CommentBean>>> getComments(
            @Query("video_id") String video_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);

    //4.8会员撤销已点赞的评论
    @GET("comment/unlike")
    Observable<HttpResult<String>> commentUnlike(
            @Query("member_id") String member_id
            , @Query("comment_id") String comment_id
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);




    //4.16注册会员--电话注册
    //使用手机号码进行注册时，提供手机号码和密码并保持wechat_id和qq_id为0。
    //使用手机号码注册会员时，如果用户上传头像图片，则avatar字段为上传的图片。
    @Multipart
    @POST("member/register")
    Observable<HttpResult<UserBean>> phoneRegisterUser(
            @Field("mobile") String mobile
            , @Field("password") String password
            , @Field("wechat_id") String wechat_id
            , @Field("qq_id") String qq_id
            , @Field("nickname") String nickname
            , @Part MultipartBody.Part avatar
            , @Field("recommender_id") String recommender_id);

    //4.16注册会员--微信或QQ注册
    //使用微信注册时，提供wechat_id并保持手机号码和qq_id为0。使用QQ注册时，提供qq_id并保持手机号码和wechat_id为0。
    //使用微信或QQ登录时，avatar字段为头像图片的URL地址。
    @Multipart
    @POST("member/register")
    Observable<HttpResult<UserBean>> appRegisterUser(
            @Field("mobile") String mobile
            , @Field("password") String password
            , @Field("wechat_id") String wechat_id
            , @Field("qq_id") String qq_id
            , @Field("nickname") String nickname
            , @Field("avatar") String avatar
            , @Field("recommender_id") String recommender_id);

    //4.26获取视频列表
    @GET("video/collection")
    Observable<HttpResult<List<PlayVideoBean>>> getVideoCollection(
            @Query("member_id") String member_id
            , @Query("category_id") String category_id
            , @Query("topic_id") String topic_id
            , @Query("city_id") String city_id
            , @Query("page") String page
            , @Query("size") String size
            , @Query("timestamp") long timestamp
            , @Query("signature") String signature);


}
