package com.example.demo.configuration;


public class Message {
    String request;
    String msg;

    public Message(String request, String msg) {
        this.request = request;
        this.msg = msg;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMsg() {
        return msg;
    }

    public Message() {
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

