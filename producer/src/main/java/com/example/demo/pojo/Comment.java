package com.example.demo.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@ApiModel
public class Comment implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return comment_id == comment.comment_id && Objects.equals(user_id, comment.user_id) && Objects.equals(user_name, comment.user_name) && Objects.equals(comment_detail, comment.comment_detail) && Objects.equals(question_id, comment.question_id) && Objects.equals(create_time, comment.create_time);
    }

    public Comment() {
    }

    @ApiModelProperty(value = "comment_id")
    int comment_id;
    @ApiModelProperty(value = "user_id")
    String user_id;
    @ApiModelProperty(value = "user_name")
    String user_name;
    @ApiModelProperty(value = "comment_detail")
    String comment_detail;
    @ApiModelProperty(value = "question_id")
    String question_id;
    @ApiModelProperty(value = "create_time")
    Long create_time;

    public Comment(String user_id, String user_name, String comment_detail, String question_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.comment_detail = comment_detail;
        this.question_id = question_id;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Comment(int comment_id, String user_id, String user_name, String comment_detail, String question_id, Long create_time) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.comment_detail = comment_detail;
        this.question_id = question_id;
        this.create_time = create_time;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id){
        this.comment_id = comment_id;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getComment_detail(){
        return comment_detail;
    }

    public void setComment_detail(String comment_detail){
        this.comment_detail = comment_detail;
    }


    public  String getQuestion_id(){
        return question_id;
    }

    public void setQuestion_id(String question_id){
        this.question_id = question_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
