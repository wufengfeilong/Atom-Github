package com.fujisoft.nd.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "thing")
public class Thing {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "action")
    private String action;

    public Thing() {
    }

    public Thing(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
