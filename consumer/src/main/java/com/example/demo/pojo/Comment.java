package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


public class Comment implements Serializable {

    public Comment() {
    }


    int comment_id;

    String user_id;

    String user_name;

    String comment_detail;

    String question_id;

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
