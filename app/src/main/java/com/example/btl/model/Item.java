package com.example.btl.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String name, des, time, date;

    public Item() {
    }

    public Item(int id, String name, String des, String time, String date) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.time = time;
        this.date = date;
    }

    public Item(String name, String des, String time, String date) {
        this.name = name;
        this.des = des;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
