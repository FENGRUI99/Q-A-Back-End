package com.example.demo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class studentInfo implements Serializable {
    @ApiModelProperty(value = "user_id")
    String user_id;
    @ApiModelProperty(value = "user_mail")
    String user_mail;
    @ApiModelProperty(value = "user_name")
    String user_name;
    @ApiModelProperty(value = "user_psw")
    String user_psw;
    @ApiModelProperty(value = "user_tags")
    String user_tags;
    @ApiModelProperty(value = "code")
    String code;

    public studentInfo(String user_id, String user_mail, String user_name, String user_tags) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_name = user_name;
        this.user_tags = user_tags;
    }

    public studentInfo(String user_id, String user_mail, String user_name, String user_psw, String user_tags, String code) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_name = user_name;
        this.user_psw = user_psw;
        this.user_tags = user_tags;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public studentInfo() {
    }

    @Override
    public String toString() {
        return "studentInfo{" +
                "user_id='" + user_id + '\'' +
                ", user_mail='" + user_mail + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_psw='" + user_psw + '\'' +
                ", user_tags='" + user_tags + '\'' +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public studentInfo(String user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_psw() {
        return user_psw;
    }

    public void setUser_psw(String user_psw) {
        this.user_psw = user_psw;
    }

    public String getUser_tags() {
        return user_tags;
    }

    public void setUser_tags(String user_tags) {
        this.user_tags = user_tags;
    }

    public studentInfo(String user_id, String user_mail, String user_name, String user_psw, String user_tags) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_name = user_name;
        this.user_psw = user_psw;
        this.user_tags = user_tags;
    }
}
