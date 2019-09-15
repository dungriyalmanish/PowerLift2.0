package com.manish.powerlift.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "exercise")
public class Exercise {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "key")
    String key;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "type")
    int type;
    @ColumnInfo(name = "part_a")
    float part_a;
    @ColumnInfo(name = "part_b")
    float part_b;
    @ColumnInfo(name = "support")
    boolean support;
    @ColumnInfo(name = "satisfied")
    boolean satisfied;
    @ColumnInfo(name = "repeat")
    boolean repeat;

    public Exercise() {

    }

    @Ignore
    public Exercise(String date, int type, float part_a, float part_b, boolean support, boolean satisfied, boolean repeat) {
        this.key = date + "" + type;
        this.date = date;
        this.type = type;
        this.part_a = part_a;
        this.part_b = part_b;
        this.support = support;
        this.satisfied = satisfied;
        this.repeat = repeat;
    }

    @Ignore
    public Exercise(int type) {
        this.type = type;
        this.part_a = 0.0f;
        this.part_b = 0.0f;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getPart_a() {
        return part_a;
    }

    public void setPart_a(float part_a) {
        this.part_a = part_a;
    }

    public float getPart_b() {
        return part_b;
    }

    public void setPart_b(float part_b) {
        this.part_b = part_b;
    }

    public boolean isSupport() {
        return support;
    }

    public void setSupport(boolean support) {
        this.support = support;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void setKey(String date, int type) {
        this.key = date + "" + type;
    }

    @Override
    public String toString() {
        return "Date:" + date + ", Type:" + type + ", parta:" + part_a + ", partb:" + part_b;
    }
}
