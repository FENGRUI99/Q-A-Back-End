package com.example.demo.pojo;

import java.io.Serializable;
import java.util.ArrayList;


//返回的聊天记录格式
public class chatRecords implements Serializable {
    String senduser_id;
    ArrayList<ChatInfo> list;

    public chatRecords() {
    }

    public chatRecords(String senduser_id, ArrayList<ChatInfo> list) {
        this.senduser_id = senduser_id;
        this.list = list;
    }

    public String getSenduser_id() {
        return senduser_id;
    }

    public void setSenduser_id(String senduser_id) {
        this.senduser_id = senduser_id;
    }

    public ArrayList<ChatInfo> getList() {
        return list;
    }

    public void setList(ArrayList<ChatInfo> list) {
        this.list = list;
    }
}
