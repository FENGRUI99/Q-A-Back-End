package com.example.demo.pojo;

import java.io.Serializable;

public class ChatBoxMessage implements Serializable {
    String id;
    String img;
    String name;
    String dept;
    String readNum;

    public ChatBoxMessage(String id, String img, String name, String dept, String readNum) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.dept = dept;
        this.readNum = readNum;
    }

    public ChatBoxMessage(String id, String img, String name) {
        this.id = id;
        this.img = img;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }
}
