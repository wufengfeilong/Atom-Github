package com.fujisoft.nd.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "thing_time")
public class ThingTime {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "action_id")
    private int actionId;
    @DatabaseField(columnName = "s_time")
    private long sTime;
    @DatabaseField(columnName = "e_time")
    private long eTime;

    public ThingTime() {
    }

    public ThingTime(int actionId, long sTime, long eTime) {
        this.actionId = actionId;
        this.sTime = sTime;
        this.eTime = eTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public long getsTime() {
        return sTime;
    }

    public void setsTime(long sTime) {
        this.sTime = sTime;
    }

    public long geteTime() {
        return eTime;
    }

    public void seteTime(long eTime) {
        this.eTime = eTime;
    }
}
