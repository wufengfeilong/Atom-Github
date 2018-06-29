package com.fujisoft.nd.dao;

import android.content.Context;
import com.fujisoft.nd.DataBaseHelper;
import com.fujisoft.nd.bean.Thing;
import com.fujisoft.nd.bean.ThingTime;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class ThingTimeDao {
    private Context context;
    private Dao<ThingTime, Integer> thingTimeDao;
    private DataBaseHelper helper;

    public ThingTimeDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DataBaseHelper.getHelper(context);
            thingTimeDao = helper.getDao(ThingTime.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个行为
     * @param thingTime
     */
    public void add(ThingTime thingTime)
    {
        try
        {
            thingTimeDao.create(thingTime);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
