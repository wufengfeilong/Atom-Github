package com.fujisoft.nd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.fujisoft.nd.bean.Thing;
import com.fujisoft.nd.bean.ThingTime;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper{
    private static final String TABLE_NAME = "do_thing.db";
    /**
     * thingDao ，每张表对于一个
     */
    private Dao<Thing, Integer> thingDao;
    /**
     * thingTimeDao ，每张表对于一个
     */
    private Dao<ThingTime, Integer> thingTimeDao;

    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource, Thing.class);

            TableUtils.createTable(connectionSource, ThingTime.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try
        {
            TableUtils.dropTable(connectionSource, Thing.class, true);
            initTable();
            TableUtils.dropTable(connectionSource, ThingTime.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    private static DataBaseHelper instance;

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DataBaseHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (DataBaseHelper.class)
            {
                if (instance == null)
                    instance = new DataBaseHelper(context);
            }
        }

        return instance;
    }

    private void initTable(){
        addData("起床");
        addData("做饭");
        addData("吃饭");
        addData("上下班路上");
        addData("去厕所");
        addData("上班");
        addData("学习");
        addData("午休");
        addData("娱乐");
        addData("运动");
        addData("睡觉");
        addData("看手机");
    }

    private void addData(String action){
        Thing thing = new Thing(action);
        try {
            getThingDao().create(thing);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * thingDao
     *
     * @return
     * @throws SQLException
     */
    public Dao<Thing, Integer> getThingDao() throws SQLException
    {
        if (thingDao == null)
        {
            thingDao = getDao(Thing.class);
        }
        return thingDao;
    }

    /**
     * thingTimeDao
     *
     * @return
     * @throws SQLException
     */
    public Dao<ThingTime, Integer> getThingTimeDao() throws SQLException
    {
        if (thingTimeDao == null)
        {
            thingTimeDao = getDao(ThingTime.class);
        }
        return thingTimeDao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();
        thingDao = null;
    }
}
