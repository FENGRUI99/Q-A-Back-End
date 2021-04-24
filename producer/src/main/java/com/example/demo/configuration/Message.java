package com.example.demo.configuration;

public class Message {
    String request;
    String msg;

    public Message(String request, String msg) {
        this.request = request;
        this.msg = msg;
    }

    public Message() {
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

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
