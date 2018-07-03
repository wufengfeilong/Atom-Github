package sdwxwx.com.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.cons.Constant;
import sdwxwx.com.message.bean.MessageListBean;

/**
 * Created by 860115025 on 2018/06/27.
 */

public class MsgDbHelper extends SQLiteOpenHelper {
    /** 数据库名称 */
    public static final String DB_NAME = "msgDatabase.db";
    /** DB版本 */
    public static final int DB_VERSION = 1;
    //创建 students 表的 sql 语句
    private static final String MESSAGE_CREATE_TABLE_SQL = "create table messageDB(id integer primary key autoincrement,msg_id varchar(20),member_id varchar(20),related_member_id varchar(20)," +
            "video_id varchar(20),ksyun_id varchar(20),type varchar(1),cover_url varchar(20),title varchar(20),content varchar(20),avatar_url varchar(20),nickname varchar(20)," +
            "create_time varchar(20),is_followed varchar(1),video_count varchar(20),follower_count varchar(20));";


    /**
     * 构造方法
     * @param context
     */
    public MsgDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //输出创建数据库的日志信息
        Log.i("创建数据表", "create Database------------->");
        //execSQL函数用于执行SQL语句
        db.execSQL(MESSAGE_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 根据信息的ID删除数据
     * @param db
     * @param msg_id
     */
    public  void deleteMsgById(SQLiteDatabase db, String msg_id) {
        //删除条件
        String whereClause = "msg_id=?";
        //删除条件参数
        String[] whereArgs = {msg_id};
        //执行删除
        db.delete("messageDB",whereClause,whereArgs);
    }

    /**
     * 根据信息的类型删除数据
     * @param db
     * @param member_id
     * @param related_member_id
     */
    public  void deleteMsgByType(SQLiteDatabase db, String type, String member_id, String related_member_id) {
        //删除条件
        String whereClause = "type=? and member_id=? and related_member_id=?";
        //删除条件参数
        String[] whereArgs = {type, member_id, related_member_id};
        //执行删除
        db.delete("messageDB",whereClause,whereArgs);
    }

    /**
     * 点暂时删除点赞信息
     * @param db
     * @param member_id
     */
    public  void deleteMsgByUnlike(SQLiteDatabase db, String type, String member_id, String video_id) {
        //删除条件
        String whereClause = "type=? and member_id=? and video_id=?";
        //删除条件参数
        String[] whereArgs = {type, member_id, video_id};
        //执行删除
        db.delete("messageDB",whereClause,whereArgs);
    }

    /**
     * 更新关注状态信息
     * @param db
     * @param member_id
     * @param is_followed
     */
    public  void updateMsgById(SQLiteDatabase db, String member_id, String related_member_id, boolean is_followed) {
        //实例化内容值
        ContentValues values = new ContentValues();
        //在values中添加内容
        values.put("is_followed",is_followed);
        //删除条件
        String whereClause = "member_id=? and related_member_id=?";
        //删除条件参数
        String[] whereArgs = {member_id, related_member_id};
        //执行删除
        db.update("messageDB", values, whereClause, whereArgs);
    }

    /**
     * 追加数据
     * @param db
     * @param list
     */
    public  void insertMsg(SQLiteDatabase db, List<MessageListBean.Result> list) {
        // 判断有无数据
        if (list == null || list.size() == 0) {
            return;
        }
        // 性能方面考虑
        if (list.size() > 50) {
            // 开启事务
            db.beginTransaction();
        }
        //实例化常量值
        ContentValues cValue = null;
        for (MessageListBean.Result bean : list) {
            // 如果是点赞或者关注，则先删除掉重复的数据
            if ("1".equals(bean.getType())) {
                deleteMsgByUnlike(db,"1",bean.getMember_id(),bean.getVideo_id());
            } else if ("2".equals(bean.getType())) {
                deleteMsgByType(db,"2",bean.getMember_id(),bean.getRelated_member_id());
            }
            cValue = new ContentValues();
            // 消息ID
            cValue.put("msg_id", bean.getId());
            //会员ID
            cValue.put("member_id", bean.getMember_id());
            //关联会员ID
            cValue.put("related_member_id",bean.getRelated_member_id());
            // 视频编号
            cValue.put("video_id",bean.getVideo_id());
            // 金山云ID
            cValue.put("ksyun_id",bean.getKsyun_id());
            // 消息类型
            cValue.put("type",bean.getType());
            // 封面
            cValue.put("cover_url",bean.getCover_url());
            // 标题
            cValue.put("title",bean.getTitle());
            // 内容
            cValue.put("content",bean.getContent());
            // 头像
            cValue.put("avatar_url",bean.getAvatar_url());
            // 昵称
            cValue.put("nickname",bean.getNickname());
            // 创建时间
            cValue.put("create_time",bean.getCreate_time());
            // 关注关系
            cValue.put("is_followed",bean.isIs_followed());
            // 视频数量
            cValue.put("video_count",bean.getVideo_count());
            // 关注数
            cValue.put("follower_count",bean.getFollower_count());
            //调用insert()方法插入数据
            db.insert("messageDB",null,cValue);
        }
        if (list.size() > 50) {
            // 结束事物
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * 分页检索
     * @param db
     * @param page
     * @return
     */
    public  List<MessageListBean.Result> getMsgList(SQLiteDatabase db, int page,String member_id) {
        // 返回结果
        List<MessageListBean.Result> resultList = new ArrayList<MessageListBean.Result>();
        // 起始位置
        String start = String.valueOf((page-1) * 20);
        // 结束位置
        String end = String.valueOf((page) * 20);
        //查询获得游标
        Cursor cursor = db.query ("messageDB",null,"member_id=?",new String[]{member_id},null,null,"create_time desc", start+","+end);
        if (cursor.getCount() > 0) {
            MessageListBean.Result result = null;
            while (cursor.moveToNext()) {
                result = new MessageListBean.Result();
                // 消息ID
                result.setId(cursor.getString(cursor.getColumnIndex("msg_id")));
                // 类型
                result.setType(cursor.getString(cursor.getColumnIndex("type")));
                // 会员ID
                result.setMember_id(cursor.getString(cursor.getColumnIndex("member_id")));
                // 关联会员ID
                result.setRelated_member_id(cursor.getString(cursor.getColumnIndex("related_member_id")));
                // 头像
                result.setAvatar_url(cursor.getString(cursor.getColumnIndex("avatar_url")));
                // 昵称
                result.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
                // 内容
                result.setContent(cursor.getString(cursor.getColumnIndex("content")));
                // 创建时间
                result.setCreate_time(cursor.getString(cursor.getColumnIndex("create_time")));
                // 关注关系
                if ("0".equals(cursor.getString(cursor.getColumnIndex("is_followed")))){
                    result.setIs_followed(false);
                } else {
                    result.setIs_followed(true);
                }
                // 视频封面
                result.setCover_url(cursor.getString(cursor.getColumnIndex("cover_url")));
                resultList.add(result);
            }
        }
        return resultList;
    }
}
