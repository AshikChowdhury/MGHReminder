package com.ashik.mghreminder.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    @NonNull
    private String date;
    @NonNull
    private String time;
    @Nullable
    private String address;
    @Nullable
    private double lat;
    @Nullable
    private double lon;
    @Nullable
    private String active;

    public Reminder(@NonNull String title, @NonNull String date, @NonNull String time, @Nullable String address, double lat, double lon, @Nullable String active) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Nullable
    public String getActive() {
        return active;
    }

    public void setActive(@Nullable String active) {
        this.active = active;
    }
}
