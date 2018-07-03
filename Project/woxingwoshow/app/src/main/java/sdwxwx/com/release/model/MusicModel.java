package sdwxwx.com.release.model;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.bean.MusicTypeBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.http.APIClient;
import sdwxwx.com.http.HttpResult;
import sdwxwx.com.util.NetworkUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 860117066 on 2018/06/14.
 */

public class MusicModel {
    //获取音乐类型
    public static void getMusicType(final BaseCallback<List<MusicTypeBean>> callback){

        HashMap<String,Object> mHashMap = new HashMap<>();
        // 获取时间戳
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        // 获取签名
        String signature = APIClient.getSign(mHashMap);
        // 调用接口
        APIClient.getAPIService().getMusicType(times,signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MusicTypeBean>>>() {
            @Override
            public void accept(HttpResult<List<MusicTypeBean>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess(result.getData());
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
    //获取系统推荐的音乐列表
    public static void getRecommendMusicList(String member_id
            ,String page,String size ,final BaseCallback<List<MusicBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getRecommendMusicList( times, signature,member_id,page,size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MusicBean>>>() {
            @Override
            public void accept(HttpResult<List<MusicBean>> result) throws Exception {
                if (result.getCode() == 0 || result.getCode() ==3100) {
                    callback.onSuccess(result.getData());
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
    //获取固定类型的音乐列表
    public static void getMusicList(String member_id,String is_favorited,String type_id
            ,String page,String size ,final BaseCallback<List<MusicBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("member_id", member_id);
        mHashMap.put("is_favorited", is_favorited);
        mHashMap.put("type_id", type_id);
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().getMusicList( times, signature,member_id,is_favorited,type_id,page,size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MusicBean>>>() {
            @Override
            public void accept(HttpResult<List<MusicBean>> result) throws Exception {
                //3100为该列表无收藏音乐
                if (result.getCode() == 0||result.getCode() == 3100) {
                    callback.onSuccess(result.getData());
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
    //搜索音乐
    public static void matchUser(String keyword,String member_id,String page,String size, final BaseCallback<List<MusicBean>> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        // 对检索的字段进行编码处理
        try {
            keyword = URLEncoder.encode(keyword, Constant.ENCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHashMap.put("keyword", keyword);
        mHashMap.put("member_id", member_id);
        mHashMap.put("page", page);
        mHashMap.put("size", size);
        long times = APIClient.getTimes();
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().searchMusic(times,signature,keyword,member_id,page,size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<MusicBean>>>() {
            @Override
            public void accept(HttpResult<List<MusicBean>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess(result.getData());
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
    //会员收藏音乐
    public static void favoriteMusic(String member_id,String music_id, final BaseCallback<String> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("member_id", member_id);
        mHashMap.put("music_id", music_id);
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().favoriteMusic(member_id,music_id,times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<String>>>() {
            @Override
            public void accept(HttpResult<List<String>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess("");
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
    //会员撤销收藏音乐
    public static void unfavoriteMusic(String member_id,String music_id, final BaseCallback<String> callback) {
        HashMap<String, Object> mHashMap = new HashMap<>();
        long times = APIClient.getTimes();
        mHashMap.put("member_id", member_id);
        mHashMap.put("music_id", music_id);
        mHashMap.put("timestamp", times);
        String signature = APIClient.getSign(mHashMap);
        APIClient.getAPIService().unfavoriteMusic(member_id,music_id,times, signature).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HttpResult<List<String>>>() {
            @Override
            public void accept(HttpResult<List<String>> result) throws Exception {
                if (result.getCode() == 0) {
                    callback.onSuccess("");
                } else {
                    callback.onFail(result.getMessage());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onFail(NetworkUtils.getErrorMsg(throwable));
            }
        });
    }
}
