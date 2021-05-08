package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.*;

/**
 *
 */
@Document(indexName = "questiones", shards = 1,replicas = 0, refreshInterval = "-1")
public class QuestionEs implements Serializable {

    @Id
    private String id;
    String random;

    @Field(type = FieldType.Keyword)
    String question_id;

    public String getRandom() {
        return random;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    //user_id
    @Field(type = FieldType.Keyword)
    String user_id;

    @Field(type=FieldType.Text,index=false)
    String user_name;
    //question_description
    @Field(type=FieldType.Auto, analyzer="ik_smart")
    String question_description;
    //question_detail
    @Field(type=FieldType.Auto, analyzer="ik_smart")
    String question_detail;
    //question_tags
    @Field(type = FieldType.Keyword)
    String question_tags;
    //number of comment

    @Field(type=FieldType.Long,index = false)
    int number_comment;
    //多少个点赞

    @Field(type=FieldType.Long,index = false)
    int likes;

    //是否点赞
    @Field(type=FieldType.Text,index = false)
    String like_flag;

    @Field(type=FieldType.Long)
    Long create_time;


    public String getLike_flag() {
        return like_flag;
    }

    public void setLike_flag(String like_flag) {
        this.like_flag = like_flag;
    }

    private List<Comment/*评论*/> commentList;

    public QuestionEs(String id, String question_id, String user_id, String user_name,
                      String question_description, String question_detail,
                      String question_tags, int number_comment, int likes,
                      Long create_time, List<Comment> commentList) {
        this.id = id;
        this.question_id = question_id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.question_description = question_description;
        this.question_detail = question_detail;
        this.question_tags = question_tags;
        this.number_comment = number_comment;
        this.likes = likes;
        this.create_time = create_time;
        this.commentList = commentList;
    }

    public void addCommentNumber(){
        this.number_comment++;
    }
    public QuestionEs() {
       commentList = new ArrayList<>();
    }


    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public int getNumber_comment() {
        return number_comment;
    }

    public void setNumber_comment(int number_comment) {
        this.number_comment = number_comment;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }
}
