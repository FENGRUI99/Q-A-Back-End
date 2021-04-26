package com.example.demo.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;



public class Question implements Serializable {


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Question(String question_description, String question_detail) {
        this.question_description = question_description;
        this.question_detail = question_detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Question(int question_id, String user_id, String user_name, String question_description,
                    String question_detail, String question_tags,
                    int number_comment, int likes, String time, List<Comment> commentList) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.question_description = question_description;
        this.question_detail = question_detail;
        this.question_tags = question_tags;
        this.number_comment = number_comment;
        this.likes = likes;
        this.time = time;
        this.commentList = commentList;
    }

    int question_id;
    String user_id;
    String user_name;
    String question_description;
    String question_detail;
    String question_tags;
    int number_comment;
    int likes;
    String time;
    List<Comment> commentList;



    public int getQuestion_id() { return question_id; }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQuestion_description() {
        return question_description;
    }

    public void setQuestion_description(String question_description) {
        this.question_description = question_description;
    }


    public String getQuestion_detail() {
        return question_detail;
    }

    public void setQuestion_detail(String question_detail) {
        this.question_detail = question_detail;
    }

    public String getQuestion_tags() {
        return question_tags;
    }

    public void setQuestion_tags(String question_tags) {
        this.question_tags = question_tags;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public int getNumber_comment() {
        return number_comment;
    }

    public void setNumber_comment(int number_comment) {
        this.number_comment = number_comment;
    }

    public Question(int question_id, String user_id, String question_description,
                    String question_detail, String question_tags, int number_comment, List<Comment> commentList, int likes) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.question_description = question_description;
        this.question_detail = question_detail;
        this.question_tags = question_tags;
        this.number_comment = number_comment;
        this.likes = likes;
        this.commentList = commentList;

    }

    public Question() {

    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
