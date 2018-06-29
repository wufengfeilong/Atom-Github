package com.fujisoft.nd.dao;

import android.content.Context;
import com.fujisoft.nd.DataBaseHelper;
import com.fujisoft.nd.bean.Thing;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class ThingDao {
    private Context context;
    private Dao<Thing, Integer> thingDao;
    private DataBaseHelper helper;

    public ThingDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DataBaseHelper.getHelper(context);
            thingDao = helper.getDao(Thing.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个行为
     * @param thing
     */
    public void add(Thing thing)
    {
        try
        {
            thingDao.create(thing);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
