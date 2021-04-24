package com.example.demo.service.service;


import com.example.demo.configuration.ResponseMessage;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;

public interface PublishService {

    ResponseMessage publishQuestion(Question publish);
    ResponseMessage publishComment(Comment comment);

}
