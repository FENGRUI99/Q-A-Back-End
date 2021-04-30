package com.example.demo.pojo.es;

import com.example.demo.pojo.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Document(indexName = "questiones", shards = 1,replicas = 0, refreshInterval = "-1")
public class QuestionEs implements Serializable {
    @Id
    //question_id
    private String id;

    String question_id;

    public String getQuestion_id() {
        return question_id;
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
    @Field(type=FieldType.Text, analyzer="ik_max_word")
    String question_description;
    //question_detail
    @Field(type=FieldType.Text, analyzer="ik_max_word")
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

    @Field(type=FieldType.Keyword)
    String create_time;


    List<String> pics ;

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    private Map<String/*comment_id*/,Comment/*评论*/> comments;

    public void addCommentNumber(){
        this.number_comment++;
    }
    public QuestionEs() {
       pics = new ArrayList<>();
        comments = new HashMap<>();
    }
    public boolean putComment(Comment comment){
        if (comment !=null){
            comments.put(String.valueOf(comment.getComment_id()),comment);
            return true;
        }
        return false;
    }

    public Map<String, Comment> getComments() {
        return comments;
    }

    public void setComments(Map<String, Comment> comments) {
        this.comments = comments;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
