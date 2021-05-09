package com.example.demo.pojo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UserInfo implements Serializable {
    @ApiModelProperty(value = "user_id")
    String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUser_tags() {
        return user_tags;
    }

    public void setUser_tags(String user_tags) {
        this.user_tags = user_tags;
    }

    public String getQuestion_sum() {
        return Question_sum;
    }

    public void setQuestion_sum(String question_sum) {
        Question_sum = question_sum;
    }

    public String getComment_sum() {
        return Comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        Comment_sum = comment_sum;
    }

    public String getLike_sum() {
        return Like_sum;
    }

    public void setLike_sum(String like_sum) {
        Like_sum = like_sum;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUsage_time() {
        return usage_time;
    }

    public void setUsage_time(String usage_time) {
        this.usage_time = usage_time;
    }

    public UserInfo() {
    }

    public UserInfo(String user_id, String user_mail, String user_name,
                    String user_tags, String question_sum, int sex, int age, String address,
                    String major, String degree, String introduction, String usage_time) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_name = user_name;
        this.user_tags = user_tags;
        Question_sum = question_sum;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.major = major;
        this.degree = degree;
        this.introduction = introduction;
        this.usage_time = usage_time;
    }

    public UserInfo(String user_id, String user_mail, String user_name, String user_tags, String question_sum, String comment_sum, String like_sum) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_name = user_name;
        this.user_tags = user_tags;
        Question_sum = question_sum;
        Comment_sum = comment_sum;
        Like_sum = like_sum;
    }

    @ApiModelProperty(value = "user_mail")
    String user_mail;
    @ApiModelProperty(value = "user_name")
    String user_name;
    @ApiModelProperty(value = "user_tags")
    String user_tags;
    @ApiModelProperty(value = "sum of the questions")
    String Question_sum;
    @ApiModelProperty(value = "sex")
    int sex;
    @ApiModelProperty(value = "age")
    int age;
    @ApiModelProperty(value = "address")
    String address;
    @ApiModelProperty(value = "major")
    String major;
    @ApiModelProperty(value = "degree")
    String degree;
    @ApiModelProperty(value = "introduction")
    String introduction;
    @ApiModelProperty(value = "usage_time")
    String usage_time;

    public UserInfo(String user_id, String user_mail, String user_name, String user_tags, String question_sum, String comment_sum) {
        this.user_id = user_id;
        this.user_mail = user_mail;
        this.user_name = user_name;
        this.user_tags = user_tags;
        Question_sum = question_sum;
        Comment_sum = comment_sum;
    }

    @ApiModelProperty(value = "sum of the comments")
    String Comment_sum;
    @ApiModelProperty(value = "sum of the likess")
    String Like_sum;
}
