package com.example.demo.pojo;

import java.io.Serializable;

//返回的聊天记录
public class ChatInfo implements Serializable {

    public String text;

    public String name;

    public String date;

    public String img;

    public boolean mine;

    public ChatInfo(String text, String name, String date, String img, boolean mine) {
        this.text = text;
        this.name = name;
        this.date = date;
        this.img = img;
        this.mine = mine;
    }

    public ChatInfo() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
